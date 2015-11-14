package com.bng.core.controller;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bng.core.bean.StatusBean;
import com.bng.core.util.LogValues;
import com.bng.core.util.Logger;
import com.google.gson.JsonObject;

@Controller
public class StatusController {
	
	@Autowired
	private JmsTemplate jmsTemplate;
	

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	private MessageCreator getMessageCreator(final String message) {
		return new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		};
	}
	String njson;
	
	
	public String getNjson() {
		return njson;
	}
	public void setNjson(String njson) {
		this.njson = njson;
	}

	ConcurrentMap map = new ConcurrentHashMap();
	@RequestMapping(value= "/status", method = RequestMethod.GET)
	public @ResponseBody String call(HttpServletRequest request,HttpServletResponse response,@RequestParam("vid") Integer vid, @RequestParam("ivrcode") String IVRcode, @RequestParam("DTMF") Integer dtmf, @RequestParam("aparty") String aparty, @RequestParam("callstate") String callstate, @RequestParam("calltype") String calltype)
	{
		Logger.sysLog(LogValues.info, this.getClass().getName(),"values coming from Telephony: VID"+vid+":: IVRCODE"+IVRcode+":: DTMF"+dtmf+":: APARTY"+aparty+":: CALLSTATE"+callstate+":: CALLTYPE"+calltype);
		StatusBean value = new StatusBean();
		value.setIvrcode(IVRcode);
		value.setDtmf(dtmf);
		value.setAparty(aparty);
		value.setCallstate(callstate);
		value.setCalltype(calltype);
		map.put(vid, value);
		//String query = "insert into eone_status (vid, aparty, IVRcode, callstate, calltype, dtmf) values ("+vid+",'"+aparty+"','"+IVRcode+"','"+callstate+"','"+calltype+"',"+dtmf+")";
		//jmsTemplate.send("dbdirectQuery", getMessageCreator(query));
		//jmsTemplate.send(destinationName, messageCreator);
		/*HashMap hashmap = new HashMap<>();
		hashmap.put(id,value);*/
		//Logger.sysLog(LogValues.info, this.getClass().getName(),"query: "+query);
		Logger.sysLog(LogValues.info, this.getClass().getName(),map.toString()+":"+ map.size());
		String json = "";
		try {
			 
			ObjectMapper mapper = new ObjectMapper();
			//convert map to JSON string
			json = mapper.writeValueAsString(map);
			
	 
		} catch (Exception e) {
			e.printStackTrace();
		}
		njson = json;
		return "true";
		
	}
	
	@RequestMapping(value= "/show.htm")
	public @ResponseBody String showstatus()
	{
		Logger.sysLog(LogValues.info, this.getClass().getName(),njson);
		return njson;
	}
	
	

}
