//Pankaj
package com.bng.core.xmlparser.unmarshal;

import java.util.ArrayList;

public class ConverterSCP {

	public String callType;
	public String shortCode;
	public String serviceName;
	
	
	public MxGraphModel convert(MxGraphModel mxGraphModel) {

		convert(mxGraphModel.getMxCellList());

		return mxGraphModel;
	}

	private void convert(ArrayList<MxCellSCP> mxCellList) {
		for (MxCellSCP mxCellSCP : mxCellList) {
			if(mxCellSCP.getMxparams() != null)
				if(mxCellSCP.getType().equalsIgnoreCase("start"))
					convert(mxCellSCP.getMxparams(),true);
				else
					convert(mxCellSCP.getMxparams(),false);
		}
		
	}

	private void convert(MxParamsSCP mxparams,Boolean bool) {
		ArrayList<Rec> recList = mxparams.getRecList();
		for (Rec rec : recList) {
			if(rec.getId().equalsIgnoreCase("calltype"))
				callType = rec.getValueList().get(0).getValue();
			if(rec.getId().equalsIgnoreCase("shortcode"))
				shortCode = rec.getValueList().get(0).getValue();
			if(rec.getId().equalsIgnoreCase("service"))
				serviceName = rec.getValueList().get(0).getValue();
			if(rec.getType().equalsIgnoreCase("selectmul"))
			{
				ArrayList<Value> values = new ArrayList<Value>();
				for (Value value : rec.getValueList()) {
					if(!value.getDependency().equalsIgnoreCase("discard"))
						values.add(value);
				}
				rec.setValueList(values);
			}
		}
	}


}
