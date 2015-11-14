package com.bng.core.activemqListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.bng.core.util.LogValues;
import com.bng.core.util.Logger;
import com.bng.core.util.coreException;
import com.bng.core.xmlparser.marshal.ConverterCoreEngine;
import com.bng.core.xmlparser.parser.Controller;


public class XmlParserMessageListener implements MessageListener {

	private Controller controller;
	
	/**
	 * Implementation of <code>MessageListener</code>.
	 */
	public void onMessage(Message message) {
		String textMessage = null;
		Logger.sysLog(LogValues.info, this.getClass().getName(),"xml parser message received");
		if (message instanceof TextMessage)
			try {
				textMessage = ((TextMessage) message).getText();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(), coreException.GetStack(e));
			}

		if (textMessage == null)
			Logger.sysLog(LogValues.info, this.getClass().getName(),"textmessage null");
		controller.Unmarshaller(textMessage);
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

}
