package com.bng.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bng.core.entity.Service;
import com.bng.core.service.SchedulerBo;
import com.bng.core.util.LogValues;
import com.bng.core.util.Logger;

 
@Controller
public class NumberInsert {
	
	@Autowired
	private SchedulerBo schedulerBo;
	
	@RequestMapping(value="/service/{servicename}/{msisdn}")
	public @ResponseBody String insertNumber(@PathVariable("servicename") String servicename, @PathVariable("msisdn") String msisdn)
	{
		Logger.sysLog(LogValues.info, this.getClass().getName(),"url values: " +servicename+"::"+ msisdn);
		String result="";
		if(msisdn.length()>10)
			msisdn = msisdn.substring(msisdn.length() - 10);
		if(!msisdn.matches("[0-9]+")){
			result="invalid number";
		 return result;
		}
		Service ser = schedulerBo.getService(servicename);
		Logger.sysLog(LogValues.info, this.getClass().getName(),"value of service Object"+ser);
		if(ser == null)
		{
			Logger.sysLog(LogValues.info, this.getClass().getName(),"service does not exist in table");
			return "service does not exist in the table";
		}
		else if(ser.getStatus().equalsIgnoreCase("scheduled")||ser.getStatus().equalsIgnoreCase("running")||ser.getStatus().equalsIgnoreCase("paused")){
			Logger.sysLog(LogValues.info, this.getClass().getName(),"service exists in table, Status= "+ser.getStatus());
			result=schedulerBo.insertMsisdn(servicename,msisdn);
		Logger.sysLog(LogValues.info, this.getClass().getName(),"RESULT :"+result);
		return result;
		}
		else
			return "This service is not running currently";
	}
	
	
}
