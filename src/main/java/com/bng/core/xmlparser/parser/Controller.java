/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bng.core.xmlparser.parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.bng.core.memCache.MemCache;
import com.bng.core.memCache.MemCacheJSon;
import com.bng.core.util.LogValues;
import com.bng.core.util.Logger;
import com.bng.core.util.coreException;
import com.bng.core.xmlparser.marshal.BnGModel;
import com.bng.core.xmlparser.marshal.ConverterCoreEngine;
import com.bng.core.xmlparser.marshal.MxCell;
import com.bng.core.xmlparser.marshal.MxParam;
import com.bng.core.xmlparser.unmarshal.MxGraphModel;

/**
 * 
 * @author richa
 */
public class Controller {

	private MemCacheJSon memCacheJSon = null;
	private MemCache memCache = null;

	public void setMemCacheJSon(MemCacheJSon memCacheJSon) {
		this.memCacheJSon = memCacheJSon;
	}
	public void setMemCache(MemCache memCache) {
		this.memCache = memCache;
	}
	
	/**
	 * UnMarshaller method converts the xml file into class objects using JAXB
	 */
	public void Unmarshaller(String xml) {
		Logger.sysLog(LogValues.info, this.getClass().getName(),"Inside Unmarshaller ");
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(MxGraphModel.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			InputStream is = new ByteArrayInputStream(xml.getBytes());
			MxGraphModel mxGraphModel = (MxGraphModel) jaxbUnmarshaller.unmarshal(is);
			BnGModel bngModel = ConverterCoreEngine.convert(mxGraphModel);
			parseMxCell(bngModel);
		} catch (JAXBException e) {
			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(), coreException.GetStack(e));
		}


	}

	/*
	 * parseMxCell method stores each mxCell in a HashMap
	 */
	public void parseMxCell(BnGModel bngModel) {
		boolean found = false;
		String shortCode = null;
		String serviceName = null;
		String calltype = null;
		int startNode = 0;
		String key = "";
		Map<Integer, MxCell> objMap = new HashMap<Integer, MxCell>();
		ArrayList<Pair<Integer, ArrayList<Pair<String, Integer>>>> mainlist = new ArrayList<Pair<Integer, ArrayList<Pair<String, Integer>>>>();
		try {
			List<MxCell> listOfMxcell = bngModel.getMxCellList();

			for (int i = 0; i < listOfMxcell.size(); i++) {
				MxCell mxCell = bngModel.getMxCellList().get(i);
				if (mxCell.getType() != null) {
					if (mxCell.getType().equals("Start")) {
						startNode = mxCell.getId();

						ArrayList<MxParam> mxParamList = mxCell
								.getListOfMxParam();
						for (MxParam mxParam : mxParamList) {
							shortCode = mxParam.getShortcode();
							serviceName = mxParam.getService();
							calltype = mxParam.getCalltype();
						}
						// Logger.sysLog(LogValues.info, this.getClass().getName(),"serviceName = "+serviceName+"\tstartNode = "+startNode+"\tshortCode = "+shortCode+"\tcalltype = "+calltype);
						if (calltype.equalsIgnoreCase("obd"))
							key = shortCode + "_" + calltype + "_"
									+ serviceName;
						else
							key = shortCode + "_" + calltype;
					}
				}
				objMap.put(mxCell.getId(), mxCell);
				Integer id = mxCell.getId();
				// Logger.sysLog(LogValues.info, this.getClass().getName(),"1. id = "+id+"\t Type = "+mxCell.getType());
				if (mxCell.getType() != null) {
					if ((!(mxCell.getType()).equalsIgnoreCase("Normal"))
							|| (!(mxCell.getType()).equalsIgnoreCase("DB"))
							|| (!(mxCell.getType()).equalsIgnoreCase("DTMF"))
							|| (!(mxCell.getType()).equalsIgnoreCase("Check"))) {
						ArrayList<Pair<String, Integer>> adjcentlist = new ArrayList<Pair<String, Integer>>();
						for (int j = 0; j < listOfMxcell.size(); j++) {
							MxCell internalCell = bngModel
									.getMxCellList().get(j);
							// Logger.sysLog(LogValues.info, this.getClass().getName(),"2. id = "+mxCell.getId()+"\t internal cell source = "+internalCell.getSource()+"\tinternal cell id = "+internalCell.getId());
							if (mxCell.getId() == internalCell.getSource()) {
								found = true;
								Integer target = internalCell.getTarget();
								String value = internalCell.getValue();
								Pair<String, Integer> p = new Pair<String, Integer>(value, target);
								// Logger.sysLog(LogValues.info,
								// this.getClass().getName(),
								// "@#@ value = "+value+"\ttarget = "+target);
								adjcentlist.add(p);
								// put the cell and the internal cell in a map
							}
							if (found) {
								// now put this in to another array list
								Pair<Integer, ArrayList<Pair<String, Integer>>> pPrime = new Pair<Integer, ArrayList<Pair<String, Integer>>>(
										id, adjcentlist);
								mainlist.add(pPrime);
								// System.out.printf("Mainlist id=%d\n",id);
								found = false;
							}
						}// end for internal loop

					} // find out which type of object it is
				} // if type not null
			} // external loop

			// adding objMap and mainList to memCache.
			memCacheJSon.set(key + "_startNode", startNode);
			Logger.sysLog(LogValues.info, this.getClass().getName(),"@#@ key = "+key+"_startNode : "+memCacheJSon.get(key + "_startNode"));

			// memCacheJSonImpl.set(service+"_hm", objMap);
			memCache.set(key + "_hm",9600 , objMap);
			Logger.sysLog(LogValues.info, this.getClass().getName(),"fetched from memcache "+ memCacheJSon.get(key+"_hm"));
			Logger.sysLog(LogValues.info, this.getClass().getName(),"@#@ key = "+key + "_hm : "+memCacheJSon.get(key + "_hm"));
			// memCacheJSonImpl.set(service+"_ml",mainlist);
			Logger.sysLog(LogValues.info, this.getClass().getName(),"mainlist size = " + mainlist.size());
			// Logger.sysLog(LogValues.info,this.getClass().getName(),"mainlist object size = "+ObjectSizeFetcher.getObjectSize(mainlist));
			memCache.set(key + "_ml", 9600, mainlist);
		} catch (Exception e) {
			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(), coreException.GetStack(e));
		}
	}
}
