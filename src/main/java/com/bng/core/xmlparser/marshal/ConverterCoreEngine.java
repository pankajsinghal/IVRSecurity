package com.bng.core.xmlparser.marshal;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bng.core.bean.ContentPlaylistBean;
import com.bng.core.entity.Content;
import com.bng.core.service.FileUploadImp;
import com.bng.core.service.UserService;
import com.bng.core.util.LogValues;
import com.bng.core.util.Logger;
import com.bng.core.util.coreException;
import com.bng.core.xmlparser.unmarshal.MxCellSCP;
import com.bng.core.xmlparser.unmarshal.MxGraphModel;
import com.bng.core.xmlparser.unmarshal.MxParamsSCP;
import com.bng.core.xmlparser.unmarshal.Rec;
import com.bng.core.xmlparser.unmarshal.Value;

public class ConverterCoreEngine 
{
	@Autowired
	private static UserService userService;
	private static HashMap contentHashMap=new HashMap();
	private static boolean isFileMissing=false;
	
	public static BnGModel convert(MxGraphModel mxGraphModel,boolean isFileMissing) 
	{
		ConverterCoreEngine.isFileMissing=isFileMissing;
		return convert(mxGraphModel);
	}
	public static BnGModel convert(MxGraphModel mxGraphModel) 
	{
		createHashMap();
		BnGModel bnGModel = new BnGModel();
		bnGModel.setConnect(mxGraphModel.getConnect());
		bnGModel.setFold(mxGraphModel.getFold());
		bnGModel.setGrid(mxGraphModel.getGrid());
		bnGModel.setGuides(mxGraphModel.getGuides());
		bnGModel.setPage(mxGraphModel.getPage());
		bnGModel.setPageHeight(mxGraphModel.getPageHeight());
		bnGModel.setPageScale(mxGraphModel.getPageScale());
		bnGModel.setPageWidth(mxGraphModel.getPageWidth());
		bnGModel.setTooltips(mxGraphModel.getTooltips());
		bnGModel.setMxCellList(convert(mxGraphModel.getMxCellList()));
		destroyHashMap();
		return bnGModel;
	}

	private static ArrayList<MxCell> convert(ArrayList<MxCellSCP> mxCellList) 
	{

		ArrayList<MxCell> mxCellCoreEngineList = new ArrayList<MxCell>();
		try 
		{
			for (MxCellSCP mxCellSCP : mxCellList) 
			{
				MxCell mxCell = new MxCell();
				try
				{
					mxCell.setId(Integer.parseInt(mxCellSCP.getId()));
				}
				catch (Exception e)
				{
					mxCell.setId(null);
				}
				try
				{
					mxCell.setSource(Integer.parseInt(mxCellSCP.getSource()));
				}
				catch (Exception e)
				{
					mxCell.setSource(null);
				}
				try
				{
					mxCell.setTarget(Integer.parseInt(mxCellSCP.getTarget()));
				}
				catch (Exception e)
				{
					mxCell.setTarget(null);
				}
				mxCell.setType(mxCellSCP.getType());
				mxCell.setValue(mxCellSCP.getValue()==null?null:mxCellSCP.getValue().toLowerCase().split("\\|")[mxCellSCP.getValue().toLowerCase().split("\\|").length-1]);
				// try catch because sometimes mxCellSCP.getType() is null and
				// throws exception while performing @equalsIgnoreCase on that
				// object
				try 
				{
					if (mxCellSCP.getType().equalsIgnoreCase("db")|| mxCellSCP.getType().equalsIgnoreCase("dtmf")|| mxCellSCP.getType().equalsIgnoreCase("normal")|| mxCellSCP.getType().equalsIgnoreCase("check"))
					{
						throw new Exception();
					}
					else if (mxCellSCP.getType().equalsIgnoreCase("Processing"))
					{
						mxCell.setListOfMxParam(convert(mxCellSCP.getMxparams(), mxCellSCP.getValue().toLowerCase().split("\\|")));
					}
					else
					{
						mxCell.setListOfMxParam(convert(mxCellSCP.getMxparams()));
					}
				} 
				catch (Exception e) 
				{
					// Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(), coreException.GetStack(e));
					mxCell.setListOfMxParam(null);
				}

				// mxCellCoreEngine
				// .setMxParamList(convert(mxCellSCP.getMxparams()));
				mxCellCoreEngineList.add(mxCell);
			}
		} 
		catch (NullPointerException e) 
		{
			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(),"error: " + e.getMessage());
		}
		return mxCellCoreEngineList;
	}

	private static ArrayList<MxParam> convert(MxParamsSCP mxParamsSCP, String... strings) 
	{
		int size = 1;
		String mulId = "";
		List<ContentPlaylistBean> contentPlaylistBeanlist = null;
		ArrayList<MxParam> mxParamsList = new ArrayList<MxParam>();
		ArrayList<Rec> recList;
		try 
		{
			recList = mxParamsSCP.getRecList();
		} 
		catch (Exception e) 
		{
			return null;
		}

		// recList = mxParamsSCP.getRecList();

		for (Rec rec : recList) 
		{
			if (rec.getType().equalsIgnoreCase("selectmul")) 
			{
				if(rec.getId().equalsIgnoreCase("playlist"))
				{
					ArrayList<Integer> ids = new ArrayList<Integer>();
					for(Value value : rec.getValueList())
					{
						Integer integer=new Integer(userService.changeContentPlaylistId(value.getValue().split("-")[1]));
						Logger.sysLog(LogValues.debug, ConverterCoreEngine.class.getName(), "Integer:"+integer);
						if(integer!=null)
							ids.add(integer);
						//ids.add(Integer.parseInt(value.getValue().split("-")[0]));						
					}
					Logger.sysLog(LogValues.debug, ConverterCoreEngine.class.getName(), "Integer:"+ids.toString());
					contentPlaylistBeanlist = userService.getContentForPlaylistIds(ids);
					size = contentPlaylistBeanlist.size();
					mulId = rec.getId();
				}
				else
				{
					size = rec.getValueList().size();
					mulId = rec.getId();
				}
			}
			if(rec.getId().equalsIgnoreCase("dialbg")||rec.getId().equalsIgnoreCase("setbackground"))
			{
				for(Value value : rec.getValueList())
				{
					value.setValue(userService.changeContentPath(value.getValue()));
				}
			}
		}

		if(size<1)
		{
			size = 1;
			mulId = "";
		}

		outerloop:
		for (int i = 0; i < size; i++) 
		{
			MxParam mxParam = new MxParam();
			for (Rec rec : recList) 
			{
				if(rec.getValueList().size()<1) continue;
				try 
				{
					int pos = rec.getId().equalsIgnoreCase(mulId) ? i : 0;
					//	uncomment if dependency attribute of "values" have to be read
					if (!rec.getId().equalsIgnoreCase("playlist") && rec.getValueList().get(pos).getDependency().equalsIgnoreCase("discard"))
						continue outerloop;
					if (strings.length > 0	&& rec.getId().equalsIgnoreCase(strings[strings.length-1])) 
					{
						mxParam.setValue(rec.getValueList().get(pos).getValue());						
					}
					else if (strings.length <= 0)
					{
						if(rec.getId().equalsIgnoreCase("playlist"))
						{
							Logger.sysLog(LogValues.debug, ConverterCoreEngine.class.getName(), "Path:"+contentPlaylistBeanlist.get(pos).getContent().getId()+"-"+contentPlaylistBeanlist.get(pos).getContent().getPath()+"PlayID:"+contentPlaylistBeanlist.get(pos).getPlaylistNumber());
							mxParam.setPlaylist(contentPlaylistBeanlist.get(pos).getContent().getId()+"-"+contentPlaylistBeanlist.get(pos).getContent().getPath());
							//mxParam.setPlaylistnumber(contentPlaylistBeanlist.get(pos).getContentPlaylistMappers().iterator().next().getContentPlaylist().getId()+"");
							mxParam.setPlaylistnumber(contentPlaylistBeanlist.get(pos).getPlaylistNumber()+"");
						}
						else setparams(rec, mxParam, pos);
					}
				}
				catch (Exception e) 
				{
					Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(), coreException.GetStack(e));
				}
			}
			mxParamsList.add(mxParam);
		}
		if(mulId.equalsIgnoreCase("playlist") && mxParamsList.size()>0)
		{
			int i=1,prev=Integer.parseInt(mxParamsList.get(0).getPlaylistnumber());
			for(MxParam mxParam : mxParamsList)
			{
				if(Integer.parseInt(mxParam.getPlaylistnumber())==prev)
				{
					mxParam.setPlaylistnumber(i+"");
				}
				else
				{
					prev=Integer.parseInt(mxParam.getPlaylistnumber());
					i++;
					mxParam.setPlaylistnumber(i+"");
				}
			}
		}
		return mxParamsList;
	}

	private static void setparams(Rec rec,MxParam mxParam, int pos) 
	{
		String methodName="";
		try 
		{
			//			if(field.getType() == String.class)
			methodName = "set"	+ Character.toUpperCase(rec.getId().charAt(0))+ rec.getId().substring(1);
			//			if(field.getType() == boolean.class)
			//				methodName = "set"
			//						+ Character.toUpperCase(rec.getId().charAt(0))
			//						+ rec.getId().substring(1);
			if (!methodName.equalsIgnoreCase("setNull") && !methodName.equalsIgnoreCase("setServicedescription")) 
			{
				Field field = mxParam.getClass().getDeclaredField(rec.getId());
				Method method=null;
				if(field.getType() == String.class)
				{
					method = mxParam.getClass().getDeclaredMethod(methodName, String.class);
					String value= rec.getValueList().get(pos).getValue();
					if(methodName.equalsIgnoreCase("setContentlist")||methodName.equalsIgnoreCase("setConfirmlist")||methodName.equalsIgnoreCase("setPromptfile"))
					{
						value=changeContentId(value);
					}
					method.invoke(mxParam,value);
				}
				else if(field.getType() == boolean.class)
				{
					method = mxParam.getClass().getDeclaredMethod(methodName, boolean.class);
					method.invoke(mxParam, Boolean.parseBoolean(rec.getValueList().get(pos).getValue()));
				}
				else if(field.getType() == int.class)
				{
					method = mxParam.getClass().getDeclaredMethod(methodName, int.class);
					method.invoke(mxParam, Integer.parseInt(rec.getValueList().get(pos).getValue()));
				}
			}
		} 
		catch (NoSuchMethodException | SecurityException| IllegalAccessException | IllegalArgumentException	| InvocationTargetException e) 
		{
			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(),"error while setting "+methodName+"\n"+ coreException.GetStack(e));
			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(),"error while setting "+methodName+"\n"+ coreException.GetStack(e));
		} 
		catch (Exception e) 
		{
			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(), "error while setting "+methodName+"\n"+coreException.GetStack(e));
		}
	}

	public static void setUserService(UserService userService)
	{
		ConverterCoreEngine.userService = userService;
	}
	public static void createHashMap()
	{	
		List<Content> contentList=userService.getContentList();
		Iterator<Content> contentIterator=contentList.iterator();
		while(contentIterator.hasNext())
		{
			try
			{
				Content content=(Content)contentIterator.next();
				String keyString=content.getPath().replace(FileUploadImp.filestoreFolder,"");
				String valueString=content.getId()+"-"+content.getPath();			
				contentHashMap.put(keyString,valueString);	
				contentHashMap.put(content.getPath(), valueString);
			}
			catch(Exception e)
			{
				Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(), "Content ID change Error:"+coreException.GetStack(e));
			}
		}		
	}
	public static String changeContentId(String value)
	{
		String idPath[]=value.split("-");
		String result=(String)contentHashMap.get(idPath[1]);
		if (result==null)
		{
			isFileMissing=true;
			Logger.sysLog(LogValues.info, ConverterCoreEngine.class.getName(), "File Missing:"+idPath[1]);
			return "";
		}
		return result;		
	}	
	public static void destroyHashMap()
	{
		contentHashMap.clear();		
	}
}