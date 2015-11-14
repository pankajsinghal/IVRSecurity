package com.bng.core.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.validation.Valid;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bng.core.bean.UserBean;
import com.bng.core.entity.Country;
import com.bng.core.entity.Mxgraph;
import com.bng.core.entity.MxgraphKeyMapping;
import com.bng.core.entity.MxgraphVersion;
import com.bng.core.entity.Operator;
import com.bng.core.entity.Users;
import com.bng.core.service.CountryBo;
import com.bng.core.service.SchedulerBo;
import com.bng.core.service.UserService;
import com.bng.core.util.LogValues;
import com.bng.core.util.Logger;
import com.bng.core.util.coreException;
import com.bng.core.xmlparser.marshal.BnGModel;
import com.bng.core.xmlparser.marshal.ConverterCoreEngine;
import com.bng.core.xmlparser.unmarshal.ConverterSCP;
import com.bng.core.xmlparser.unmarshal.MxGraphModel;



@Controller
public class WelcomeController {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private SchedulerBo schedulerBo;

	@Autowired
	private UserService userService;

	@Autowired
	private CountryBo CountryBo;

	@RequestMapping("/404")
	public String denied()
	{
		return "404";
	}

	@RequestMapping("/welcome.htm")
	public String redirect(){
//		Enumeration e = request.getAttributeNames();
//		while(e.hasMoreElements()){
//			String a = (String) e.nextElement();
//			Logger.sysLog(LogValues.info, this.getClass().getName(), " request attributes ["+a+"] request value ["+request.getAttribute(a)+"]");
//		}
//		Enumeration e1 = request.getHeaderNames();
//		while(e1.hasMoreElements()){
//			String a = (String) e1.nextElement();
//			Logger.sysLog(LogValues.info, this.getClass().getName(), " request header name["+a+"] header value["+request.getHeader(a)+"]");
//		}
		return "welcome";
	}

	@RequestMapping(value="/newuser.htm", method = RequestMethod.GET)
	public String newuser(ModelMap model)
	{
		UserBean user = new UserBean();
		model.addAttribute("user",user);
		List<Country> getCountry = CountryBo.getCountry();
		model.addAttribute("servicelist", getCountry);
		List<Operator> getOperator = CountryBo.getOperator();
		model.addAttribute("service", getOperator);
		return "newuser";
	}

	@RequestMapping(value = "/newuser.htm" , method = RequestMethod.POST)
	public String adduser(@Valid @ModelAttribute("user") UserBean nuser, BindingResult result, SessionStatus status)
	{
		if(result.hasErrors())
			return "newuser";
		else
		{
			Logger.sysLog(LogValues.info,this.getClass().getName(), "test user name "+nuser.getUserName());
			Logger.sysLog(LogValues.info, this.getClass().getName(),"test user name "+nuser.getUserName());
			Logger.sysLog(LogValues.info, this.getClass().getName(),"User Country:"+nuser.getCountry());
			Logger.sysLog(LogValues.info, this.getClass().getName(),"User Operator:"+nuser.getOperator().length);
			String countryName = nuser.getCountry();
			String[] operatorList = nuser.getOperator();
			Country country=userService.getCountry(countryName);
			Users user=userService.addUser(nuser);
			for(int i=0; i<operatorList.length; i++)
			{
				Operator operator=userService.getOperator(operatorList[i]);
				Logger.sysLog(LogValues.info, this.getClass().getName(),"country = "+country+" , operator = "+operator);
				userService.addmapper(country, operator, user);
			}
			// userService.addUser(nuser);
			return "adduser";
		}
	}

	@ModelAttribute("userList")
	public List<Users> populateUserList() {
		List<Users> list = userService.listUser();
		return list;
	}

	@RequestMapping(value="/listuser.htm" )
	public String listuser()
	{
		return "showuser";
	}

	@RequestMapping(value="/listusers.htm")
	public String listusers()
	{
		return "modifyuser";
	}

	@RequestMapping(value="/deleteuser.htm")
	public String deleteuser(@RequestParam("id") int id)
	{
		userService.removeUser(id);
		return "adduser";
	}

	@RequestMapping(value="/updateuser.htm" ,method = RequestMethod.GET)
	public String updateuser(@RequestParam("id") int id ,ModelMap model)
	{
		UserBean users = userService.showUser(id);
		model.addAttribute("user", users);
		return "nameuser";
	}

	@RequestMapping(value = "/updateuser.htm" , method = RequestMethod.POST)
	public String updateusers(@ModelAttribute("user") UserBean nuser,BindingResult result, SessionStatus status)
	{
		if(result.hasErrors())
			return "error";
		else {
			userService.updateUser(nuser);
			return "newuser";
		}
	}

	@RequestMapping(value="/viewserviceOBD.htm", method =  RequestMethod.GET)
	public String viewserviceOBD(ModelMap model, Principal principal)throws IOException
	{
		
		//List<Mxgraph> serviceNameList = new ArrayList();
		List<MxgraphVersion> mxgraphversionlist = new ArrayList();
		Integer userid = null;
		String username=principal.getName();
		Users currentuser = userService.getUser(username);
		userid = currentuser.getId();
		Logger.sysLog(LogValues.info, this.getClass().getName(),"current user"+userid);
		//serviceNameList = userService.viewservice(userid);
		mxgraphversionlist = userService.createdate(userid);
		Iterator iter = mxgraphversionlist.iterator();
		List<MxgraphVersion> mxGraphList = new ArrayList<MxgraphVersion>(); 
		while(iter.hasNext()){
			MxgraphVersion mxGraphVersion = (MxgraphVersion)iter.next();
			if(mxGraphVersion.getMxgraph().getCallType().equalsIgnoreCase("obd"))
				mxGraphList.add(mxGraphVersion);
		}
		/*List<MxgraphKeyMapping> list = new ArrayList<MxgraphKeyMapping>();
//		List<MxgraphKeyMapping> keys = new ArrayList<>();
		for(int i=0;i<mxGraphList.size();i++)
		{
			list.addAll(userService.getkeys(mxGraphList.get(i).getMxgraph()));
		}
		Logger.sysLog(LogValues.info, this.getClass().getName(),"size of longcode list"+list.size());
		if(list.size()>0)
			for(int i=0;i<list.size();i++)
			{
				
				Logger.sysLog(LogValues.info, this.getClass().getName(),"longcodes"+list.get(i).getShortcode());
				
			}
		*/
		
		if(mxGraphList.size()>0)
			Logger.sysLog(LogValues.info, this.getClass().getName(),"mxgraphlist size: "+ mxGraphList.size());
		model.addAttribute("mxgraphversionlist", mxGraphList);
		//model.addAttribute("servicelist", serviceNameList);
		model.addAttribute("username", username);
		//model.addAttribute("longcodes", list);
		//MxGraphVersion graphVersion = new MxGraphVersion(currentuser, createdDate);

		/*MxGraph mxGraph = graphVersion.getMxgraph();
		Logger.sysLog(LogValues.info, this.getClass().getName(),mxGraph.toString());
		serviceName = mxGraph.getServiceName();
		Logger.sysLog(LogValues.info, this.getClass().getName(),"service name"+ serviceName);*/

		return "viewservice";
	}
	
	@RequestMapping(value="/viewserviceIVR.htm", method =  RequestMethod.GET)
	public String viewserviceIVR(ModelMap model, Principal principal)throws IOException
	{
		List<MxgraphVersion> mxgraphversionlist = new ArrayList();
		Integer userid = null;
		String username=principal.getName();
		Users currentuser = userService.getUser(username);
		userid = currentuser.getId();
		Logger.sysLog(LogValues.info, this.getClass().getName(),"current user"+userid);
		mxgraphversionlist = userService.createdate(userid);
		Iterator iter = mxgraphversionlist.iterator();
		List<MxgraphVersion> mxGraphList = new ArrayList<MxgraphVersion>(); 
		while(iter.hasNext()){
			MxgraphVersion mxGraphVersion = (MxgraphVersion)iter.next();
			if(mxGraphVersion.getMxgraph().getCallType().equalsIgnoreCase("ivr"))
				mxGraphList.add(mxGraphVersion);
		}
		/*List<MxgraphKeyMapping> list = new ArrayList<MxgraphKeyMapping>();
//		List<MxgraphKeyMapping> keys = new ArrayList<>();
		for(int i=0;i<mxGraphList.size();i++)
		{
			list.addAll(userService.getkeys(mxGraphList.get(i).getMxgraph()));
		}
		Logger.sysLog(LogValues.info, this.getClass().getName(),"size of longcode list"+list.size());
		if(list.size()>0)
			for(int i=0;i<list.size();i++)
			{
				
				Logger.sysLog(LogValues.info, this.getClass().getName(),"longcodes"+list.get(i).getShortcode());
				
			}
		*/
		if(mxGraphList.size()>0)
			Logger.sysLog(LogValues.info, this.getClass().getName(),"mxgraphlist size: "+ mxGraphList.size());
		model.addAttribute("mxgraphversionlist", mxGraphList);
		model.addAttribute("username", username);
		//model.addAttribute("longcodes", list);
		return "viewservice2";
	}
	
	@RequestMapping("/managekeys.htm")
	public String getpage(@RequestParam("servicename") String servicename,@RequestParam("shortcode") String shortcode,@RequestParam("calltype") String calltype, ModelMap model)
	{
		Logger.sysLog(LogValues.info, this.getClass().getName(),"inside managekeys:"+servicename+shortcode+calltype);
		Mxgraph mxgraph = schedulerBo.getmxgraph(servicename,shortcode);
		List<MxgraphKeyMapping> list = userService.getkeys(mxgraph);
		model.addAttribute("longcodes", list);
		if(mxgraph.getCallType().equalsIgnoreCase("OBD"))
		return "managekeys";
		else
			return "managekeysivr";
		}
		
	
	@RequestMapping("/popup.htm")
	public @ResponseBody String getpopup(@RequestParam("servicename") String servicename,@RequestParam("shortcode") String shortcode)
	{
		Logger.sysLog(LogValues.info, this.getClass().getName(),"inside popup:"+servicename+shortcode);
		Mxgraph mxgraph = schedulerBo.getmxgraph(servicename,shortcode);
		List<MxgraphKeyMapping> list = userService.getkeys(mxgraph);
		
			ArrayList<String> slist = new ArrayList<String>();
			String listString = "";
			for(int i=0;i<list.size();i++)
			{
				slist.add(list.get(i).getShortcode());
			}
			Logger.sysLog(LogValues.info, this.getClass().getName(),"size of list: "+slist.size());
			for (String s : slist)
			{
			    listString += s + ",";
			}

			Logger.sysLog(LogValues.info, this.getClass().getName(),"listString: "+listString);
			return listString;
		}

	@RequestMapping(value="/remove.htm", method =  RequestMethod.POST)
	public @ResponseBody String removefromproduction(@RequestParam("servicename") String servicename,@RequestParam("shortcode") String shortcode,@RequestParam("calltype") String calltype,@RequestParam("longcode") String[] longcode,@RequestParam("Service") String service)
	{
		/*String Key= null;
		System.out.println("object: "+ undeployservice);
		
		if(calltype.equalsIgnoreCase("OBD"))
		Key = shortcode+"_"+calltype+"_"+servicename;
		else
		Key= shortcode+"_"+calltype;	
		undeployservice.undeployServicefromMemcache(Key);
		System.out.println("in remove");*/
		
		//removing longcodes
		Mxgraph mxgraph = schedulerBo.getmxgraph(servicename,shortcode);
		Logger.sysLog(LogValues.info, this.getClass().getName(),"--removing longcode--");
			for(int i=0;i<longcode.length;i++)
			{
				userService.removeKey(mxgraph,longcode[i]);
			}

			if(mxgraph.isproductionFlag()==true){
				Logger.sysLog(LogValues.info, this.getClass().getName(),"--service already deployed, UPDATING--"+ servicename+ shortcode);
			JSONObject obj = new JSONObject();
			JSONArray list = new JSONArray();
			list.add(shortcode);
			for(int i=0;i<longcode.length;i++){
				String key = longcode[i];
				list.add(key);
			}
			obj.put("servicename", servicename);
			obj.put("calltype", calltype);
			obj.put("classname", "serviceInfo");
			obj.put("keys", list);
			obj.put("value", shortcode);
			obj.put("operation", "DELETE");
			obj.put("service", service);
			Logger.sysLog(LogValues.info, this.getClass().getName(),"Json object :"+obj.toJSONString());
			jmsTemplate.send(getMessageCreator(obj.toJSONString()));
			}
			
			
			//undeploy from production
		if(mxgraph.isproductionFlag()==true && service.equalsIgnoreCase("true")){
			Logger.sysLog(LogValues.info, this.getClass().getName(),"--Removing from production--"+servicename+"-"+shortcode);
		userService.removefromProduction(mxgraph);
		}
		
		 //
		
		Logger.sysLog(LogValues.info, this.getClass().getName(),"shortcode :"+shortcode);
		return shortcode;
	}
	
	@RequestMapping(value= "/release.htm", method = RequestMethod.POST)
	public @ResponseBody String  releaseforproduction(@RequestParam("servicename") String servicename,@RequestParam("shortcode") String shortcode,@RequestParam("calltype") String calltype,@RequestParam("longcode") String[] longcode,@RequestParam("Service") String service)
	{
		Logger.sysLog(LogValues.info, this.getClass().getName(),"in release");
		Logger.sysLog(LogValues.info, this.getClass().getName(),"service name: "+ servicename);
		Logger.sysLog(LogValues.info, this.getClass().getName(),"shortcode: "+ shortcode);
		Logger.sysLog(LogValues.info, this.getClass().getName(),"calltype: "+calltype);
		for(int i=0;i<longcode.length;i++)
		Logger.sysLog(LogValues.info, this.getClass().getName(),"longcode: "+longcode[i]);
		Logger.sysLog(LogValues.info, this.getClass().getName(),"service: "+service);
		
		Mxgraph mxgraph = schedulerBo.getmxgraph(servicename,shortcode);
		//adding longcode
		Logger.sysLog(LogValues.info, this.getClass().getName(),"--adding longcode--");
		if(service.equalsIgnoreCase("false")){
		for(int i=0;i<longcode.length;i++)
			userService.releasetoProduction(mxgraph,longcode[i]);
		}
		
		//already deployed update key
				if(mxgraph.isproductionFlag()==true)
				{
					Logger.sysLog(LogValues.info, this.getClass().getName(),"--service already deployed, UPDATING--"+ servicename+ shortcode);
					JSONObject obj = new JSONObject();
					JSONArray list = new JSONArray();
					list.add(shortcode);
					for(int i=0;i<longcode.length;i++){
						String key = longcode[i];
						list.add(key);
					}
					obj.put("servicename", servicename);
					obj.put("calltype", calltype);
					obj.put("classname", "serviceInfo");
					obj.put("keys", list);
					obj.put("value", shortcode);
					obj.put("operation", "ADD");
					obj.put("service", service);
					Logger.sysLog(LogValues.info, this.getClass().getName(),"Json object :"+obj.toJSONString());
					jmsTemplate.send(getMessageCreator(obj.toJSONString()));

				}
				
		//service deploy to production
		if(mxgraph.isproductionFlag()==false && service.equalsIgnoreCase("true"))
		{
			Logger.sysLog(LogValues.info, this.getClass().getName(),"--deploying to production--"+ servicename + shortcode);
			int id = mxgraph.getMxdata().getId();
			Logger.sysLog(LogValues.info, this.getClass().getName(),"id of mxdata :"+ id);
			byte mxdata[] = mxgraph.getMxdata().getData();
			String xml = new String(mxdata);
			xml.replace("\n", "");
			
			//Logger.sysLog(LogValues.info, this.getClass().getName(),"xml: "+ xml);
			xml = convert(xml);
			Logger.sysLog(LogValues.info, this.getClass().getName(),"im back!");
			// send keys to json then to queue
		
			
			JSONObject obj = new JSONObject();
			JSONArray list = new JSONArray();
			list.add(shortcode);
			for(int i=0;i<longcode.length;i++){
				String key = longcode[i];
				list.add(key);
			}
			
			obj.put("servicename", servicename);
			obj.put("calltype", calltype);
			obj.put("classname", "serviceInfo");
			obj.put("keys", list);
			obj.put("value", shortcode);
			obj.put("operation", "ADD");
			obj.put("service", service);
			Logger.sysLog(LogValues.info, this.getClass().getName(),"Json object :"+obj.toJSONString());
			jmsTemplate.send(getMessageCreator(obj.toJSONString()));
			
			userService.releasetoProduction(mxgraph);
		}
		
		
		/*List<Mxgraph> inproduction = userService.getReleasedservice(shortcode);
		int count=0;
		String serviceName= "";
		if(calltype.equalsIgnoreCase("IVR")){
		for(int i=0;i<inproduction.size();i++)
		{
			if(inproduction.get(i).isproductionFlag()==true && inproduction.get(i).getCallType().equalsIgnoreCase("IVR")){
				count++;
				serviceName= inproduction.get(i).getServiceName();
			}
			else
				continue;
		}
		}
		if(count!=0)
		Logger.sysLog(LogValues.info, this.getClass().getName(),"--a service for this shortcode exists--"+serviceName+"-"+shortcode);
		else{
		Logger.sysLog(LogValues.info, this.getClass().getName(),"--Deploy to production--"+servicename+"-"+shortcode);
		Mxgraph mxgraph = schedulerBo.getmxgraph(servicename,shortcode);
		int id = mxgraph.getMxdata().getId();
		Logger.sysLog(LogValues.info, this.getClass().getName(),"id of mxdata :"+ id);
		byte mxdata[] = mxgraph.getMxdata().getData();
		String xml = new String(mxdata);
		//Logger.sysLog(LogValues.info, this.getClass().getName(),"xml: "+ xml);
		xml = convert(xml);
		Logger.sysLog(LogValues.info, this.getClass().getName(),"im back!");
		userService.releasetoProduction(mxgraph);
		}	
		Logger.sysLog(LogValues.info, this.getClass().getName(),"shortcode :"+shortcode);
*/
		// send xml to queue
		/*
		int id = mxgraph.getMxdata().getId();
		Logger.sysLog(LogValues.info, this.getClass().getName(),"id of mxdata :"+ id);
		byte mxdata[] = mxgraph.getMxdata().getData();
		String xml = new String(mxdata);
		//Logger.sysLog(LogValues.info, this.getClass().getName(),"xml: "+ xml);
		xml = convert(xml);
		Logger.sysLog(LogValues.info, this.getClass().getName(),"im back!");
		// send keys to json then to queue
	
		
		JSONObject obj = new JSONObject();
		JSONArray list = new JSONArray();
		for(int i=0;i<longcode.length;i++){
			String key = longcode[i];
			list.add(key);
		}
		obj.put("keys", list);
		obj.put("value", shortcode);
		obj.put("operation", "ADD");
		obj.put("service", service);
		
		jmsTemplate.send(getMessageCreator(obj.toJSONString()));
		
		
		*/
		
		Logger.sysLog(LogValues.info, this.getClass().getName(),"shortcode return value"+ shortcode);
		return shortcode;
	}

	private String convert(String xml) {
		Logger.sysLog(LogValues.debug, this.getClass().getName(), "xml recevied for deployment"+xml);
		String newXml="";
		JAXBContext jaxbContext;
		ConverterSCP converterScp = new ConverterSCP();
		try {
			jaxbContext = JAXBContext.newInstance(MxGraphModel.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			InputStream is = new ByteArrayInputStream(xml.getBytes());
			MxGraphModel mxGraphModel = (MxGraphModel) jaxbUnmarshaller
					.unmarshal(is);

//			mxGraphModel = converterScp.convert(mxGraphModel);

			
			Marshaller m = jaxbContext.createMarshaller();
//			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//			m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			m.marshal(mxGraphModel,baos);
//			newXml = baos.toString();
			BnGModel bnGModel = ConverterCoreEngine.convert(mxGraphModel);
//			baos.close();
			jaxbContext = JAXBContext.newInstance(BnGModel.class);
			m = jaxbContext.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			m.marshal(bnGModel,baos);
			jmsTemplate.send(getMessageCreator(baos.toString()));
			baos.close();
		} catch (Exception e) {
			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(), coreException.GetStack(e));
		}
		return newXml;
	}
	
	@RequestMapping(value="/check.htm", method =  RequestMethod.POST)
	public @ResponseBody String  checkexisting(@RequestParam("servicename") String servicename,@RequestParam("shortcode") String shortcode,@RequestParam("calltype") String calltype,@RequestParam("longcode") String longcode)
	{
		Logger.sysLog(LogValues.info, this.getClass().getName(),"values"+servicename+shortcode+calltype+longcode);
		//List<Mxgraph> inproduction = userService.getReleasedservice(shortcode);
		int count=0;
		int temp=0;
		String returntext="";
		String serviceName= "";
		/*if(calltype.equalsIgnoreCase("IVR")){
		for(int i=0;i<inproduction.size();i++)
		{
			if(inproduction.get(i).isproductionFlag()==true && inproduction.get(i).getCallType().equalsIgnoreCase("IVR")){
				count++;
				serviceName= inproduction.get(i).getServiceName();
				System.out.println("in 1st if");
			}
			else{ 
				System.out.println("in 1st else");
				continue;
			}
		}
		}*/
		if(count==0 && calltype.equalsIgnoreCase("IVR")){

			List<MxgraphKeyMapping> keys = userService.getexistingkeys(longcode);
			for(int i=0;i<keys.size();i++)
			{
				if(keys.get(i).getMxgraph().isproductionFlag()==true && keys.get(i).getMxgraph().getCallType().equalsIgnoreCase("IVR"))
				{
					temp++;
					serviceName = keys.get(i).getMxgraph().getServiceName();
					Logger.sysLog(LogValues.debug, this.getClass().getName(),"in 2nd if");
				}
				else
					continue;
			}
			Logger.sysLog(LogValues.info, this.getClass().getName(),"temp value"+temp);
		}
		/*if(count!=0 && calltype.equalsIgnoreCase("IVR"))
		{
			Logger.sysLog(LogValues.info, this.getClass().getName(),"--a service for this shortcode exists--"+serviceName+"-"+shortcode);
			//returntext= serviceName+shortcode;
		}*/
		 if(temp!=0)
		{
			Logger.sysLog(LogValues.info, this.getClass().getName(),"--a service for this longcode exists--"+serviceName+"-"+longcode);
			returntext= serviceName+longcode;
		}
		else
		{
			returntext="ok";
		}
			
		return returntext;
		
	}
	

	private MessageCreator getMessageCreator(final String message) {
		return new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		};
	}

	@RequestMapping(value= "/pending")
	public @ResponseBody String pendingRequest(@RequestParam("servicename") String servicename,@RequestParam("shortcode") String shortcode,@RequestParam("calltype") String calltype)
	{
		Logger.sysLog(LogValues.info, this.getClass().getName(),"--inside pending-- SERVICENAME:"+servicename+"SHORTCODE:"+shortcode+"CALLTYPE:"+calltype);
		List<Mxgraph> inproduction = userService.getReleasedservice(shortcode);
		String msg = "";
		int c=0;
		if(calltype.equalsIgnoreCase("0"))
		{
			for(int i=0;i<inproduction.size();i++)
			{
				if(inproduction.get(i).getCallType().equalsIgnoreCase("IVR") && inproduction.get(i).isproductionFlag()==true){
				byte data[] = inproduction.get(i).getMxdata().getData();
				String xml = new String(data);
				//Logger.sysLog(LogValues.info, this.getClass().getName(),"xml: "+ xml);
				xml = convert(xml);
				Logger.sysLog(LogValues.info, this.getClass().getName(),"im back!");
				c++;
				}
				else
				continue;
			}
			if(c==1)
				msg="success";
			else
				msg="error";
		}
		if(calltype.equalsIgnoreCase("1"))
		{
			Mxgraph mxgraph = schedulerBo.getmxgraph(servicename,shortcode);
			if(mxgraph.getCallType().equalsIgnoreCase("OBD")&&mxgraph.isproductionFlag()==true){
				byte data[] = mxgraph.getMxdata().getData();
				String xml = new String(data);
				//Logger.sysLog(LogValues.info, this.getClass().getName(),"xml: "+ xml);
				xml = convert(xml);
				Logger.sysLog(LogValues.info, this.getClass().getName(),"im back!");
				msg = "success";
			}
			else
				msg="error";
		}
		
		return msg;
	}
	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	@RequestMapping(value="/hi")
	public @ResponseBody String hello(@RequestParam("name") String name)
	{
		return name;
	}
	
	
}