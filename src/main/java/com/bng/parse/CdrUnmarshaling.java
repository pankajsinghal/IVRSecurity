package com.bng.parse;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/*import parser.CallCdr;
import com.bng.cdr.bo.HourlyCounterBo;
import com.bng.cdr.bo.IvrMasterCalllogsBo;
import com.bng.cdr.bo.DtmfMasterCalllogsBo;
import com.bng.cdr.bo.MediaMasterCalllogsBo;
import com.bng.cdr.bo.UrlMasterCalllogsBo;
import com.bng.cdr.bo.ConfMasterCalllogsBo;
import com.bng.cdr.entity.IvrMastercalllogs;
import com.bng.cdr.entity.DtmfMastercalllogs;
import com.bng.cdr.entity.MediaMastercalllogs;
import com.bng.cdr.entity.UrlMastercalllogs;
import com.bng.cdr.entity.IvrConfcalllogs;
import com.bng.cdr.entity.CounterSummary;
import com.bng.cdr.util.Logger;
import com.bng.cdr.util.LogValues;*/

public class CdrUnmarshaling {

//	private IvrMasterCalllogsBo ivrmasterBo;
//	private DtmfMasterCalllogsBo dtmfmasterBo;
//	private MediaMasterCalllogsBo mediamasterBo;
//	private UrlMasterCalllogsBo urlmasterBo;
//	private ConfMasterCalllogsBo confmasterBo;
//	private HourlyCounterBo hourcounterBo;
	
//	private enum UrlType {
//		sm, billing, cg, sms, ussd, crbt, wap, general;
//	}
//	private enum CallFailReason {
//		networkbusy, releasingcall, noanswer, callrejection, unavailable, switchedoff, 
//		userabsent, numberformatinvalid, circuitchannelunavailable, s_outgoingfailed, misc;
//	}
	
//	public void setIvrmasterBo(IvrMasterCalllogsBo ivrmasterBo) {	
//		this.ivrmasterBo = ivrmasterBo;
//	}
//
//	public void setHourcounterBo(HourlyCounterBo hourcounterBo) {
//		this.hourcounterBo = hourcounterBo;
//	}
//
//	public void setDtmfmasterBo(DtmfMasterCalllogsBo dtmfmasterBo) {
//		this.dtmfmasterBo = dtmfmasterBo;
//	}
//
//	public void setMediamasterBo(MediaMasterCalllogsBo mediamasterBo) {
//		this.mediamasterBo = mediamasterBo;
//	}
//
//	public void setUrlmasterBo(UrlMasterCalllogsBo urlmasterBo) {
//		this.urlmasterBo = urlmasterBo;
//	}
//
//	public void setConfmasterBo(ConfMasterCalllogsBo confmasterBo) {
//		this.confmasterBo = confmasterBo;
//	}
	public String[] getRecord(String xmlText){

            try {
            xmlText = xmlText.toLowerCase();
            JAXBContext context = JAXBContext.newInstance(CallCdr.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            CallCdr outcdr = (CallCdr)unmarshaller.unmarshal(new StringReader(xmlText));
            String[] strRec=new String[21];
            strRec[0]=outcdr.getAparty();
            strRec[1]=outcdr.getBparty();
            strRec[2]=outcdr.getCallid();
            strRec[3]=outcdr.getCallstatus();
            strRec[4]=outcdr.getCalltype();
            strRec[5]=outcdr.getCalltype();
            strRec[6]=outcdr.getCesystemip();
            strRec[7]=outcdr.getCic();
            strRec[8]=outcdr.getCircle();
            strRec[9]=outcdr.getCountry();
            strRec[10]=outcdr.getEnddatetime();
            strRec[11]=outcdr.getHardware();
            strRec[12]=outcdr.getOperator();
            strRec[13]=outcdr.getPickupdatetime();
            strRec[14]=outcdr.getProtocol();
            strRec[15]=outcdr.getReleasereason();
            strRec[16]=outcdr.getServicename();
            strRec[17]=outcdr.getShortcode();
            strRec[18]=outcdr.getStartdatetime();
            strRec[19]=outcdr.getTesystemip();
            strRec[20]=outcdr.getTimeZone();
            
           /* if(outcdr.getCallConference() != null){
				for(CallConf confparam : outcdr.getCallConference().getCallConference()){
	
					String confFailReason = confparam.getReason();
					CallFailReason confFailCause = CallFailReason.valueOf(confFailReason);
					
					int confDur = getDuration(confparam.getStartdatetime(),confparam.getEnddatetime());
					int confPulse = (confDur/60)+1;
				}
            }
            strRec[21]=outcdr.getCallConference().toString();
            strRec[22]=outcdr.getDtmfs().toString();
            strRec[23]=outcdr.getPlays().toString();
            strRec[24]=outcdr.getUrls().toString();*/
            
            return strRec;
		}
		catch(Exception ex)
		{
			return null;
		}
		
	}
	/*public void sendDb(String xmlText){

		try {
			xmlText = xmlText.toLowerCase();
			JAXBContext context = JAXBContext.newInstance(CallCdr.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			CallCdr outcdr = (CallCdr)unmarshaller.unmarshal(new StringReader(xmlText));
           
			int masterId = getMasterId(outcdr.getAparty());
			// Cdr Data manipulation 
			/*  Get IVR OBD Call Durations and Pulses functions  */
	/*		int ivrCall0to5 = 0;
			int ivrCall5to10 = 0;
			int ivrCall10to15 = 0;
			int ivrCall15to20 = 0;
			int ivrCall20to25 = 0;
			int ivrCall25to30 = 0;
			int ivrCall30to35 = 0;
			int ivrCall35to40 = 0;
			int ivrCallMore40 = 0;
			int obdCall0to5 = 0;
			int obdCall5to10 = 0;
			int obdCall10to15 = 0;
			int obdCall15to20 = 0;
			int obdCall20to25 = 0;
			int obdCall25to30 = 0;
			int obdCall30to35 = 0;
			int obdCall35to40 = 0;
			int obdCallMore40 = 0;
			int ivrCalls = 0;
			int ivrDuration = 0;
			int ivrPulses = 0;
			int obdCallAttempt = 0;
			int obdCallSuccess = 0;
			int obdCallDuration = 0;
			int obdCallPulses = 0;
			int obdFailReason1 = 0;
			int obdFailReason2 = 0; 
			int obdFailReason3 = 0;
			int obdFailReason4 = 0; 
			int obdFailReasonx = 0;
			int ivrRemoteHangup = 0;
			int ivrServiceHangup = 0;
			
			if(outcdr.getCalltype().equalsIgnoreCase("Incoming")){

				ivrDuration = getDuration(outcdr.getStartdatetime(),outcdr.getEnddatetime());
				ivrPulses = (ivrDuration/60)+1;
				
				String callStatus = outcdr.getCallstatus();
				if(callStatus.equalsIgnoreCase("success"))
					ivrCalls = 1;

				String hangupCause = outcdr.getReleasereason();
				if(hangupCause.equalsIgnoreCase("remotehangupfirst")){
					ivrRemoteHangup = 1;
					ivrServiceHangup = 0;
				}else if(hangupCause.equalsIgnoreCase("rlcsent")){
					ivrRemoteHangup = 0;
					ivrServiceHangup = 1;
				}else{
					ivrRemoteHangup = 1;
					ivrServiceHangup = 0;
				}

				if(ivrDuration >= 0 && ivrDuration < 5){
					ivrCall0to5 = 1;
				}else if(ivrDuration >= 5 && ivrDuration < 10){
					ivrCall5to10 = 1;
				}else if(ivrDuration >= 10 && ivrDuration < 15){
					ivrCall10to15 = 1;
				}else if(ivrDuration >= 15 && ivrDuration < 20){
					ivrCall15to20 = 1;
				}else if(ivrDuration >= 20 && ivrDuration < 25){
					ivrCall20to25 = 1;
				}else if(ivrDuration >= 25 && ivrDuration < 30){
					ivrCall25to30 = 1;
				}else if(ivrDuration >= 30 && ivrDuration < 35){
					ivrCall30to35 = 1;
				}else if(ivrDuration >= 35 && ivrDuration < 40){
					ivrCall35to40 = 1;
				}else if(ivrDuration > 40){
					ivrCallMore40 = 1;
				}else {

				}
			}else{

				obdCallDuration = getDuration(outcdr.getPickupdatetime(),outcdr.getEnddatetime());
				obdCallPulses = (ivrDuration/60)+1;
				
				String callStatus = outcdr.getCallstatus();
				if(callStatus.equalsIgnoreCase("success")){
					
					obdCallSuccess = obdCallSuccess +1;
					
					if(obdCallDuration >= 0 && obdCallDuration < 5){
						obdCall0to5 = 1;
					}else if(obdCallDuration >= 5 && obdCallDuration < 10){
						obdCall5to10 = 1;
					}else if(obdCallDuration >= 10 && obdCallDuration < 15){
						obdCall10to15 = 1;
					}else if(obdCallDuration >= 15 && obdCallDuration < 20){
						obdCall15to20 = 1;
					}else if(obdCallDuration >= 20 && obdCallDuration < 25){
						obdCall20to25 = 1;
					}else if(obdCallDuration >= 25 && obdCallDuration < 30){
						obdCall25to30 = 1;
					}else if(obdCallDuration >= 30 && obdCallDuration < 35){
						obdCall30to35 = 1;
					}else if(obdCallDuration >= 35 && obdCallDuration < 40){
						obdCall35to40 = 1;
					}else if(obdCallDuration > 40){
						obdCallMore40 = 1;
					}else {

					}
					
				}
				else{
					
					String callFailReason = outcdr.getCallstatus();
					CallFailReason obdFailCause = CallFailReason.valueOf(callFailReason);
					switch(obdFailCause){
					
					case networkbusy:
						obdFailReason1 = obdFailReason1 +1;
						break;
					case noanswer:
						obdFailReason2 = obdFailReason2 +1;
						break;
					case callrejection:
						obdFailReason3 = obdFailReason3 +1;
						break;
					case switchedoff:
						obdFailReason4 = obdFailReason4 +1;
						break;
					default:
						obdFailReasonx = obdFailReasonx +1;
						break;
					}
				}
			
			}

			/*  Get Dtmf Inputs and Duration Bucket functions  */
	/*		int ivrDtmfPress = 0;
			int obdDtmfkey0 = 0;
			int obdDtmfkey1 = 0;
			int obdDtmfkey2 = 0;
			int obdDtmfkey3 = 0;
			int obdDtmfkey4 = 0;
			int obdDtmfkey5 = 0;
			int obdDtmfkey6 = 0;
			int obdDtmfkey7 = 0;
			int obdDtmfkey8 = 0;
			int obdDtmfkey9 = 0;
			int obdDtmfkeyStar = 0;
			int obdDtmfkeyHash = 0;
			int obdDtmfkeyx = 0;
			int obdNoDtmf = 0;
			int obdDtmfrcv0to5 = 0; 
			int obdDtmfrcv5to10 = 0;
			int obdDtmfrcv10to15 = 0; 
			int obdDtmfrcv15to20 = 0;
			int obdDtmfrcv20to25 = 0; 
			int obdDtmfrcvMore25 = 0;
			
			if(outcdr.getDtmfs() != null){
	
				for(Dtmf dtmfparam : outcdr.getDtmfs().getDtmfs()){
					 
					String dtmfKeyInput = dtmfparam.getDtmfinput();
					/*if(dtmfKeyInput.equals(null)){
						
						if(outcdr.getCalltype().equalsIgnoreCase("Incoming")){
						}else{
							obdNoDtmf = obdNoDtmf + 1;
						}
					}else{*/
						
	/*					int dtmfDur = getDuration(outcdr.getStartdatetime(),dtmfparam.getDtmftime());
						int dtmfPulse = (dtmfDur/60)+1;
						if(outcdr.getCalltype().equalsIgnoreCase("Incoming")){
							
							if(dtmfparam.getDtmfinput().equalsIgnoreCase("noinput")){
							}else{
								ivrDtmfPress = ivrDtmfPress + 1;
							}
								
						}else{
							
							switch (dtmfKeyInput) {
					            case "0":
					            	obdDtmfkey0 = obdDtmfkey0 +1;
					                break;
					            case "1":
					            	obdDtmfkey1 = obdDtmfkey1 +1;
					                break;
					            case "2":
					            	obdDtmfkey2 = obdDtmfkey2 +1;
					                break;
					            case "3":
					            	obdDtmfkey3 = obdDtmfkey3 +1;
					                break;
					            case "4":
					            	obdDtmfkey4 = obdDtmfkey4 +1;
					                break;
					            case "5":
					            	obdDtmfkey5 = obdDtmfkey5 +1;
					                break;
					            case "6":
					            	obdDtmfkey6 = obdDtmfkey6 +1;
					                break;
					            case "7":
					            	obdDtmfkey7 = obdDtmfkey7 +1;
					                break;
					            case "8":
					            	obdDtmfkey8 = obdDtmfkey8 +1;
					                break;
					            case "9":
					            	obdDtmfkey9 = obdDtmfkey9 +1;
					                break;
					            case "*":
					            	obdDtmfkeyStar = obdDtmfkeyStar +1;
					                break;
					            case "#":
					            	obdDtmfkeyHash = obdDtmfkeyHash +1;
					                break;
					            case "noinput":
					            	obdNoDtmf = obdNoDtmf + 1;
					            	break;
					            default:
					            	obdDtmfkeyx = obdDtmfkeyx +1;
					            	break;
					        }
							
							if(dtmfDur >= 0 && dtmfDur < 5){
								obdDtmfrcv0to5 = 1;
							}else if(dtmfDur >= 5 && dtmfDur < 10){
								obdDtmfrcv5to10 = 1;
							}else if(dtmfDur >= 10 && dtmfDur < 15){
								obdDtmfrcv10to15 = 1;
							}else if(dtmfDur >= 15 && dtmfDur < 20){
								obdDtmfrcv15to20 = 1;
							}else if(dtmfDur >= 20 && dtmfDur < 25){
								obdDtmfrcv20to25 = 1;
							}else if(dtmfDur > 25){
								obdDtmfrcvMore25 = 1;
							}else {
		
							}
						}
						
						updateDtmfMasterCalllogs(masterId, outcdr.getStartdatetime(), dtmfparam.getDtmftime(), 
								dtmfDur, dtmfPulse, dtmfparam.getDtmfinput(), outcdr.getCountry());
					//}		
				}
			}else{
				
				if(outcdr.getCalltype().equalsIgnoreCase("Incoming")){
				}else{
					obdNoDtmf = obdNoDtmf + 1;
				}
			}

			/*  Get Media Navigation and Content Count Function  */
	/*		int mediaNavigation = 0;
			int mediaContent = 0;
			if(outcdr.getPlays() != null){
				for(Media mediaparam : outcdr.getPlays().getPlays()){
	                
					int mediaDur = getDuration(mediaparam.getStarttime(), mediaparam.getEndtime());
					int mediaPulse = (mediaDur/60)+1;
					
					String mediaType = mediaparam.getType();
					if(mediaType.equalsIgnoreCase("navigation")){
						mediaNavigation = mediaNavigation + 1;
						mediaContent = mediaContent + 0;
					}else if(mediaType.equalsIgnoreCase("playcontent")){
						mediaNavigation = mediaNavigation + 0;
						mediaContent = mediaContent + 1;
					}else{
						mediaNavigation = mediaNavigation + 1;
						mediaContent = mediaContent + 0;
					}
					
					updateMediaMasterCalllogs(masterId, mediaparam.getStarttime(), mediaparam.getEndtime(), mediaDur, mediaPulse, 
							mediaparam.getType(), mediaparam.getFilename(), mediaparam.getCode(), outcdr.getCountry());
				}
			}

			/*  Get URL Type and Count Function  */
	/*		int ivrSmurlSuccess = 0; 
			int ivrSmurlFail = 0;
			int ivrBillurlSuccess = 0; 
			int ivrBillurlFail = 0;
			int ivrCgurlSuccess = 0;
			int ivrCgurlFail = 0;
			int ivrSmsurlSuccess = 0; 
			int ivrSmsurlFail = 0;
			int ivrCrbturlSuccess = 0; 
			int ivrCrbturlFail = 0;
			int ivrUssdurlSuccess = 0;
			int ivrUssdurlFail = 0;
			int ivrWapurlSuccess = 0;
			int ivrWapurlFail = 0;
			int ivrGenurlSuccess = 0;
			int ivrGenurlFail = 0;
			int obdSmurlSuccess = 0; 
			int obdSmurlFail = 0;
			int obdBillurlSuccess = 0; 
			int obdBillurlFail = 0;
			int obdCgurlSuccess = 0;
			int obdCgurlFail = 0;
			int obdSmsurlSuccess = 0; 
			int obdSmsurlFail = 0;
			int obdCrbturlSuccess = 0; 
			int obdCrbturlFail = 0;
			int obdUssdurlSuccess = 0;
			int obdUssdurlFail = 0;
			int obdWapurlSuccess = 0;
			int obdWapurlFail = 0;
			int obdGenurlSuccess = 0;
			int obdGenurlFail = 0;

			if(outcdr.getUrls() != null){
				for(Url urlparam : outcdr.getUrls().getUrls()){
					int respVal = 0;
	 
					String urlType = urlparam.getType();
					String urlResp = urlparam.getResponse();
					UrlType urlVal = UrlType.valueOf(urlType);
					
					int urlDur = getDuration(outcdr.getStartdatetime(), urlparam.getTime());
					int urlPulse = (urlDur/60)+1;
	
					if(urlResp.equalsIgnoreCase("success"))
						respVal = 1;
					else
						respVal = 0;
	
					if(outcdr.getCalltype().equalsIgnoreCase("Incoming")){
	
						switch(urlVal){
						case sm:
							ivrSmurlSuccess = ivrSmurlSuccess + respVal;
							ivrSmurlFail = ivrSmurlFail + respVal;
							break;
						case billing:
							ivrSmurlSuccess = ivrSmurlSuccess + respVal;
							ivrSmurlFail = ivrSmurlFail + respVal;
							break;
						case cg:
							ivrSmurlSuccess = ivrSmurlSuccess + respVal;
							ivrSmurlFail = ivrSmurlFail + respVal;
							break;
						case sms:
							ivrSmurlSuccess = ivrSmurlSuccess + respVal;
							ivrSmurlFail = ivrSmurlFail + respVal;
							break;
						case crbt:
							ivrSmurlSuccess = ivrSmurlSuccess + respVal;
							ivrSmurlFail = ivrSmurlFail + respVal;
							break;
						case ussd:
							ivrSmurlSuccess = ivrSmurlSuccess + respVal;
							ivrSmurlFail = ivrSmurlFail + respVal;
							break;
						case wap:
							ivrSmurlSuccess = ivrSmurlSuccess + respVal;
							ivrSmurlFail = ivrSmurlFail + respVal;
							break;
						case general:
							ivrSmurlSuccess = ivrSmurlSuccess + respVal;
							ivrSmurlFail = ivrSmurlFail + respVal;
							break;
						}
	
					}else{
	
						switch(urlVal){
						case sm:
							obdSmurlSuccess = obdSmurlSuccess + respVal;
							obdSmurlFail = obdSmurlFail + respVal;
							break;
						case billing:
							obdSmurlSuccess = obdSmurlSuccess + respVal;
							obdSmurlFail = obdSmurlFail + respVal;
							break;
						case cg:
							obdSmurlSuccess = obdSmurlSuccess + respVal;
							obdSmurlFail = obdSmurlFail + respVal;
							break;
						case sms:
							obdSmurlSuccess = obdSmurlSuccess + respVal;
							obdSmurlFail = obdSmurlFail + respVal;
							break;
						case crbt:
							obdSmurlSuccess = obdSmurlSuccess + respVal;
							obdSmurlFail = obdSmurlFail + respVal;
							break;
						case ussd:
							obdSmurlSuccess = obdSmurlSuccess + respVal;
							obdSmurlFail = obdSmurlFail + respVal;
							break;
						case wap:
							obdSmurlSuccess = obdSmurlSuccess + respVal;
							obdSmurlFail = obdSmurlFail + respVal;
							break;
						case general:
							obdSmurlSuccess = obdSmurlSuccess + respVal;
							obdSmurlFail = obdSmurlFail + respVal;
							break;
						}
					}
					
					updateUrlMasterCalllogs(masterId, outcdr.getStartdatetime(), urlparam.getTime(), urlDur, urlPulse, urlparam.getResponse(), 
							urlparam.getMethod(), urlparam.getType(), urlparam.getUri(), urlparam.getOption(), outcdr.getCountry());
				}
			}

			/*  Get Conference Inputs and Duration Bucket functions  */
	/*		int ivrConfSuccess = 0;
			int ivrConfDuration = 0;
			int ivrConfPulses = 0;
			int ivrConffailReason1 = 0;
			int ivrConffailReason2 = 0;
			int ivrConffailReason3 = 0;
			int ivrConffailReason4 = 0;
			int ivrConffailReasonx = 0;
			int ivrConf0to5 = 0;
			int ivrConf5to10 = 0;
			int ivrConf10to15 = 0;
			int ivrConf15to20 = 0;
			int ivrConf20to25 = 0;
			int ivrConf25to30 = 0;
			int ivrConf30to35 = 0;
			int ivrConf35to40 = 0;
			int ivrConfMore40 = 0;

//		//	*///if(outcdr.getCallConference() != null){
//				for(CallConf confparam : outcdr.getCallConference().getCallConference()){
//	
//					String confFailReason = confparam.getReason();
//					CallFailReason confFailCause = CallFailReason.valueOf(confFailReason);
//					
//					int confDur = getDuration(confparam.getStartdatetime(),confparam.getEnddatetime());
//					int confPulse = (confDur/60)+1;
//	
//					String confStatus = confparam.getStatus();
//					if(confStatus.equalsIgnoreCase("success")){
//	
//						ivrConfSuccess = ivrConfSuccess +1;
//						
//						if(confDur >= 0 && confDur < 5){
//							ivrConf0to5 = 1;
//						}else if(confDur >= 5 && confDur < 10){
//							ivrConf5to10 = 1;
//						}else if(confDur >= 10 && confDur < 15){
//							ivrConf10to15 = 1;
//						}else if(confDur >= 15 && confDur < 20){
//							ivrConf15to20 = 1;
//						}else if(confDur >= 20 && confDur < 25){
//							ivrConf20to25 = 1;
//						}else if(confDur >= 25 && confDur < 30){
//							ivrConf25to30 = 1;
//						}else if(confDur >= 30 && confDur < 35){
//							ivrConf30to35 = 1;
//						}else if(confDur >= 35 && confDur < 40){
//							ivrConf35to40 = 1;
//						}else if(confDur > 40){
//							ivrConfMore40 = 1;
//						}else {
//	
//						}
//					}else{
//	
//						switch(confFailCause){
//						case networkbusy:
//							ivrConffailReason1 = ivrConffailReason1 +1;
//							break;
//						case noanswer:
//							ivrConffailReason2 = ivrConffailReason2 +1;
//							break;
//						case callrejection:
//							ivrConffailReason3 = ivrConffailReason3 +1;
//							break;
//						case switchedoff:
//							ivrConffailReason4 = ivrConffailReason4 +1;
//							break;
//						default:
//							ivrConffailReasonx = ivrConffailReasonx +1;
//							break;
//						}
//					}
//					
//					updateConfMasterCalllogs(masterId, confparam.getConfid(), confparam.getSystemip(), confparam.getHardware(), 
//							confparam.getProtocol(), confparam.getCalltype(), confparam.getAparty(), confparam.getBparty(), confparam.getStartdatetime(),
//							confparam.getEnddatetime(), confDur, confPulse, outcdr.getServicename(), confparam.getCircle(), outcdr.getCountry(), 
//							confparam.getStatus(), confparam.getReason());
//				}
//			}
//
//			updateIvrMasterCalllogs(masterId, outcdr.getTesystemip(),outcdr.getCesystemip(), outcdr.getHardware(),
//					outcdr.getProtocol(), outcdr.getCalltype(), outcdr.getCic(), outcdr.getCallid(), outcdr.getAparty(), 
//					outcdr.getBparty(), outcdr.getShortcode(), outcdr.getStartdatetime(), outcdr.getPickupdatetime(), outcdr.getEnddatetime(), 
//					ivrDuration, ivrPulses, outcdr.getServicename(), outcdr.getCircle(), outcdr.getOperator(), outcdr.getTimeZone(), 
//					outcdr.getCountry(), outcdr.getCallstatus(), outcdr.getReleasereason());
//			
//			updateHourlyCounter(outcdr.getStartdatetime(), outcdr.getCountry(), 
//					outcdr.getServicename(), outcdr.getOperator(), outcdr.getCircle(), ivrCalls,
//					ivrDuration, ivrPulses, ivrRemoteHangup,
//					ivrServiceHangup, ivrDtmfPress,
//					mediaNavigation, mediaContent,
//					ivrSmurlSuccess, ivrSmurlFail,
//					ivrBillurlSuccess, ivrBillurlFail,
//					ivrCgurlSuccess, ivrCgurlFail,
//					ivrSmsurlSuccess, ivrSmsurlFail,
//					ivrCrbturlSuccess, ivrCrbturlFail,
//					ivrUssdurlSuccess, ivrUssdurlFail,
//					ivrWapurlSuccess, ivrWapurlFail,
//					ivrGenurlSuccess, ivrGenurlFail,
//					ivrConfSuccess, ivrConfDuration,
//					ivrConfPulses, ivrConffailReason1,
//					ivrConffailReason2, ivrConffailReason3,
//					ivrConffailReason4, ivrConffailReasonx,
//					ivrConf0to5, ivrConf5to10, ivrConf10to15,
//					ivrConf15to20, ivrConf20to25,
//					ivrConf25to30, ivrConf30to35,
//					ivrConf35to40, ivrConfMore40, ivrCall0to5,
//					ivrCall5to10, ivrCall10to15, ivrCall15to20,
//					ivrCall20to25, ivrCall25to30,
//					ivrCall30to35, ivrCall35to40,
//					ivrCallMore40, obdCallAttempt,
//					obdCallSuccess, obdCallDuration,
//					obdCallPulses, obdFailReason1,
//					obdFailReason2, obdFailReason3,
//					obdFailReason4, obdFailReasonx,
//					obdDtmfkey0, obdDtmfkey1, 
//					obdDtmfkey2, obdDtmfkey3, 
//					obdDtmfkey4, obdDtmfkey5,
//					obdDtmfkey6, obdDtmfkey7, 
//					obdDtmfkey8, obdDtmfkey9, 
//					obdDtmfkeyStar, obdDtmfkeyHash, obdDtmfkeyx,
//					obdDtmfrcv0to5, obdDtmfrcv5to10,
//					obdDtmfrcv10to15, obdDtmfrcv15to20,
//					obdDtmfrcv20to25, obdDtmfrcvMore25,
//					obdNoDtmf, obdSmurlSuccess, obdSmurlFail,
//					obdBillurlSuccess, obdBillurlFail,
//					obdCgurlSuccess, obdCgurlFail,
//					obdSmsurlSuccess, obdSmsurlFail,
//					obdCrbturlSuccess, obdCrbturlFail,
//					obdUssdurlSuccess, obdUssdurlFail,
//					obdWapurlSuccess, obdWapurlFail,
//					obdGenurlSuccess, obdGenurlFail,
//					obdCall0to5, obdCall5to10, obdCall10to15,
//					obdCall15to20, obdCall20to25,
//					obdCall25to30, obdCall30to35,
//					obdCall35to40, obdCallMore40);
//
//		} catch (JAXBException e) {
//			// TODO Auto-generated catch block
//			Logger.sysLog(LogValues.error, CdrUnmarshaling.class.getName(), "CDR :: Unmarshaling failed "+e.getCause());
//			e.printStackTrace();
//		}
//	}
//
//	public void updateIvrMasterCalllogs(int masterId, String tpsysIp, String cesysIp, 
//			String plugin, String protocol, String callType, String cic, String callId,
//			String aParty, String bParty, String shortCode, String startDateTime, String pickupDateTime,String endDateTime, 
//			int duration, int pulses, String service, String circle, String operator, 
//			String timeZone, String country, String callStatus, String releaseReason) {
//		try {
//			IvrMastercalllogs ivrmastercalllogs = null;
//			int cic_val = Integer.parseInt(cic);
//			int cid_val = Integer.parseInt(callId);
//
//			DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
//			Date sdt_val = df.parse(startDateTime);
//			Date pdt_val = df.parse(pickupDateTime);
//			Date edt_val = df.parse(endDateTime);		
//
//			ivrmastercalllogs = new IvrMastercalllogs();
//			ivrmastercalllogs.setMasterId(masterId);
//			ivrmastercalllogs.setTpsysIp(tpsysIp);
//			ivrmastercalllogs.setCesysIp(cesysIp);
//			ivrmastercalllogs.setPlugin(plugin);
//			ivrmastercalllogs.setProtocol(protocol);
//			ivrmastercalllogs.setCallType(callType);
//			ivrmastercalllogs.setCic(cic_val);
//			ivrmastercalllogs.setCallId(cid_val);
//			ivrmastercalllogs.setAParty(aParty);
//			ivrmastercalllogs.setBParty(bParty);
//			ivrmastercalllogs.setShortCode(shortCode);
//			ivrmastercalllogs.setStartDatetime(sdt_val);
//			ivrmastercalllogs.setPickupDatetime(pdt_val);
//			ivrmastercalllogs.setEndDatetime(edt_val);
//			ivrmastercalllogs.setDuration(duration);
//			ivrmastercalllogs.setPulses(pulses);
//			ivrmastercalllogs.setService(service);
//			ivrmastercalllogs.setCircle(circle);
//			ivrmastercalllogs.setOperator(operator);
//			ivrmastercalllogs.setTimezone(timeZone);
//			ivrmastercalllogs.setCountry(country);
//			ivrmastercalllogs.setCallStatus(callStatus);
//			ivrmastercalllogs.setRlcReason(releaseReason);
//			ivrmasterBo.insertIvrMasterCalllogs(ivrmastercalllogs, country);
//
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			Logger.sysLog(LogValues.error, CdrUnmarshaling.class.getName(), "CDR :: IVR MasterCalllogs Parsing failed "+e.getCause());
//			e.printStackTrace();
//		}
//	}
//
//	public void updateHourlyCounter(String callDate, String country,
//			String service, String operator, String circle, int ivrCalls,
//			int ivrDuration, int ivrPulses, int ivrRemoteHangup,
//			int ivrServiceHangup, int ivrDtmfPress,
//			int ivrMediaNavigation, int ivrMediaContent,
//			int ivrSmurlSuccess, int ivrSmurlFail,
//			int ivrBillurlSuccess, int ivrBillurlFail,
//			int ivrCgurlSuccess, int ivrCgurlFail,
//			int ivrSmsurlSuccess, int ivrSmsurlFail,
//			int ivrCrbturlSuccess, int ivrCrbturlFail,
//			int ivrUssdurlSuccess, int ivrUssdurlFail,
//			int ivrWapurlSuccess, int ivrWapurlFail,
//			int ivrGenurlSuccess, int ivrGenurlFail,
//			int ivrConfSuccess, int ivrConfDuration,
//			int ivrConfPulses, int ivrConffailReason1,
//			int ivrConffailReason2, int ivrConffailReason3,
//			int ivrConffailReason4, int ivrConffailReasonx,
//			int ivrConf0to5, int ivrConf5to10, int ivrConf10to15,
//			int ivrConf15to20, int ivrConf20to25,
//			int ivrConf25to30, int ivrConf30to35,
//			int ivrConf35to40, int ivrConfMore40, int ivrCall0to5,
//			int ivrCall5to10, int ivrCall10to15, int ivrCall15to20,
//			int ivrCall20to25, int ivrCall25to30,
//			int ivrCall30to35, int ivrCall35to40,
//			int ivrCallMore40, int obdCallAttempt,
//			int obdCallSuccess, int obdCallDuration,
//			int obdCallPulses, int obdFailReason1,
//			int obdFailReason2, int obdFailReason3,
//			int obdFailReason4, int obdFailReasonx,
//			int obdDtmfkey0, int obdDtmfkey1, int obdDtmfkey2,
//			int obdDtmfkey3, int obdDtmfkey4, int obdDtmfkey5,
//			int obdDtmfkey6, int obdDtmfkey7, int obdDtmfkey8,
//			int obdDtmfkey9, int obdDtmfkeyStar,
//			int obdDtmfkeyHash, int obdDtmfkeyx,
//			int obdDtmfrcv0to5, int obdDtmfrcv5to10,
//			int obdDtmfrcv10to15, int obdDtmfrcv15to20,
//			int obdDtmfrcv20to25, int obdDtmfrcvMore25,
//			int obdNoDtmf, int obdSmurlSuccess, int obdSmurlFail,
//			int obdBillurlSuccess, int obdBillurlFail,
//			int obdCgurlSuccess, int obdCgurlFail,
//			int obdSmsurlSuccess, int obdSmsurlFail,
//			int obdCrbturlSuccess, int obdCrbturlFail,
//			int obdUssdurlSuccess, int obdUssdurlFail,
//			int obdWapurlSuccess, int obdWapurlFail,
//			int obdGenurlSuccess, int obdGenurlFail,
//			int obdCall0to5, int obdCall5to10, int obdCall10to15,
//			int obdCall15to20, int obdCall20to25,
//			int obdCall25to30, int obdCall30to35,
//			int obdCall35to40, int obdCallMore40){
//
//		try {
//			CounterSummary hourlycallcount = null;
//
//			String dtFormat = callDate.substring(0, 10);
//			String tsFormat = callDate.substring(11, 13);	
//			DateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
//			Date dt_val = dt.parse(dtFormat);
//			int ts_val = Integer.parseInt(tsFormat);
//
//			hourlycallcount = hourcounterBo.checkHourlyCounter(dt_val, ts_val, service, operator, circle, country);
//
//			if(hourlycallcount != null){
//
//				hourlycallcount.setId(hourlycallcount.getId());
//				hourlycallcount.setIvrCalls(hourlycallcount.getIvrCalls() + ivrCalls);
//				hourlycallcount.setIvrDuration(hourlycallcount.getIvrDuration() + ivrDuration);
//				hourlycallcount.setIvrPulses(hourlycallcount.getIvrPulses() + ivrPulses);
//				hourlycallcount.setIvrRemoteHangup(hourlycallcount.getIvrRemoteHangup() + ivrRemoteHangup);
//				hourlycallcount.setIvrServiceHangup(hourlycallcount.getIvrServiceHangup() + ivrServiceHangup);
//				hourlycallcount.setIvrDtmfPress(hourlycallcount.getIvrDtmfPress() + ivrDtmfPress);
//				hourlycallcount.setIvrMediaNavigation(hourlycallcount.getIvrMediaNavigation() + ivrMediaNavigation);
//				hourlycallcount.setIvrMediaContent(hourlycallcount.getIvrMediaContent() + ivrMediaContent);
//				hourlycallcount.setIvrSmurlSuccess(hourlycallcount.getIvrSmurlSuccess() + ivrSmurlSuccess);
//				hourlycallcount.setIvrSmurlFail(hourlycallcount.getIvrSmurlFail() + ivrSmurlFail);
//				hourlycallcount.setIvrBillurlSuccess(hourlycallcount.getIvrBillurlSuccess() + ivrBillurlSuccess);
//				hourlycallcount.setIvrBillurlFail(hourlycallcount.getIvrBillurlFail() + ivrBillurlFail);
//				hourlycallcount.setIvrCgurlSuccess(hourlycallcount.getIvrCgurlSuccess() + ivrCgurlSuccess);
//				hourlycallcount.setIvrCgurlFail(hourlycallcount.getIvrCgurlFail() + ivrCgurlFail);
//				hourlycallcount.setIvrSmsurlSuccess(hourlycallcount.getIvrSmsurlSuccess() + ivrSmsurlSuccess);
//				hourlycallcount.setIvrSmsurlFail(hourlycallcount.getIvrSmsurlFail() + ivrSmsurlFail);
//				hourlycallcount.setIvrCrbturlSuccess(hourlycallcount.getIvrCrbturlSuccess() + ivrCrbturlSuccess);
//				hourlycallcount.setIvrCrbturlFail(hourlycallcount.getIvrCrbturlFail() + ivrCrbturlFail);
//				hourlycallcount.setIvrUssdurlSuccess(hourlycallcount.getIvrUssdurlSuccess() + ivrUssdurlSuccess);
//				hourlycallcount.setIvrUssdurlFail(hourlycallcount.getIvrUssdurlFail() + ivrUssdurlFail);
//				hourlycallcount.setIvrWapurlSuccess(hourlycallcount.getIvrWapurlSuccess() + ivrWapurlSuccess);
//				hourlycallcount.setIvrWapurlFail(hourlycallcount.getIvrWapurlFail() + ivrWapurlFail);
//				hourlycallcount.setIvrGenurlSuccess(hourlycallcount.getIvrGenurlSuccess() + ivrGenurlSuccess);
//				hourlycallcount.setIvrGenurlFail(hourlycallcount.getIvrGenurlFail() + ivrGenurlFail);
//				hourlycallcount.setIvrConfSuccess(hourlycallcount.getIvrConfSuccess() + ivrConfSuccess);
//				hourlycallcount.setIvrConfDuration(hourlycallcount.getIvrConfDuration() + ivrConfDuration);
//				hourlycallcount.setIvrConfPulses(hourlycallcount.getIvrConfPulses() + ivrConfPulses);
//				hourlycallcount.setIvrConffailReason1(hourlycallcount.getIvrConffailReason1() + ivrConffailReason1);
//				hourlycallcount.setIvrConffailReason2(hourlycallcount.getIvrConffailReason2() + ivrConffailReason2);
//				hourlycallcount.setIvrConffailReason3(hourlycallcount.getIvrConffailReason3() + ivrConffailReason3);
//				hourlycallcount.setIvrConffailReason4(hourlycallcount.getIvrConffailReason4() + ivrConffailReason4);
//				hourlycallcount.setIvrConffailReasonx(hourlycallcount.getIvrConffailReasonx() + ivrConffailReasonx);
//				hourlycallcount.setObdDtmfkey0(hourlycallcount.getObdDtmfkey0() + obdDtmfkey0);
//				hourlycallcount.setObdDtmfkey1(hourlycallcount.getObdDtmfkey1() + obdDtmfkey1);
//				hourlycallcount.setObdDtmfkey2(hourlycallcount.getObdDtmfkey2() + obdDtmfkey2);
//				hourlycallcount.setObdDtmfkey3(hourlycallcount.getObdDtmfkey3() + obdDtmfkey3);
//				hourlycallcount.setObdDtmfkey4(hourlycallcount.getObdDtmfkey4() + obdDtmfkey4);
//				hourlycallcount.setObdDtmfkey5(hourlycallcount.getObdDtmfkey5() + obdDtmfkey5);
//				hourlycallcount.setObdDtmfkey6(hourlycallcount.getObdDtmfkey6() + obdDtmfkey6);
//				hourlycallcount.setObdDtmfkey7(hourlycallcount.getObdDtmfkey7() + obdDtmfkey7);
//				hourlycallcount.setObdDtmfkey8(hourlycallcount.getObdDtmfkey8() + obdDtmfkey8);
//				hourlycallcount.setObdDtmfkey9(hourlycallcount.getObdDtmfkey9() + obdDtmfkey9);
//				hourlycallcount.setObdDtmfkeyStar(hourlycallcount.getObdDtmfkeyStar() + obdDtmfkeyStar);
//				hourlycallcount.setObdDtmfkeyHash(hourlycallcount.getObdDtmfkeyHash() + obdDtmfkeyHash);
//				hourlycallcount.setObdDtmfkeyX(hourlycallcount.getObdDtmfkeyX() + obdDtmfkeyx);
//				hourlycallcount.setIvrConf0to5(hourlycallcount.getIvrConf0to5() + ivrConf0to5);
//				hourlycallcount.setIvrConf5to10(hourlycallcount.getIvrConf5to10() + ivrConf5to10);
//				hourlycallcount.setIvrConf10to15(hourlycallcount.getIvrConf10to15() + ivrConf10to15);
//				hourlycallcount.setIvrConf15to20(hourlycallcount.getIvrConf15to20() + ivrConf15to20);
//				hourlycallcount.setIvrConf20to25(hourlycallcount.getIvrConf20to25() + ivrConf20to25);
//				hourlycallcount.setIvrConf25to30(hourlycallcount.getIvrConf25to30() + ivrConf25to30);
//				hourlycallcount.setIvrConf30to35(hourlycallcount.getIvrConf30to35() + ivrConf30to35);
//				hourlycallcount.setIvrConf35to40(hourlycallcount.getIvrConf35to40() + ivrConf35to40);
//				hourlycallcount.setIvrConfMore40(hourlycallcount.getIvrConfMore40() + ivrConfMore40);
//				hourlycallcount.setIvrCall0to5(hourlycallcount.getIvrCall0to5() + ivrCall0to5);
//				hourlycallcount.setIvrCall5to10(hourlycallcount.getIvrCall5to10() + ivrCall5to10);
//				hourlycallcount.setIvrCall10to15(hourlycallcount.getIvrCall10to15() + ivrCall10to15);
//				hourlycallcount.setIvrCall15to20(hourlycallcount.getIvrCall15to20() + ivrCall15to20);
//				hourlycallcount.setIvrCall20to25(hourlycallcount.getIvrCall20to25() + ivrCall20to25);
//				hourlycallcount.setIvrCall25to30(hourlycallcount.getIvrCall25to30() + ivrCall25to30);
//				hourlycallcount.setIvrCall30to35(hourlycallcount.getIvrCall30to35() + ivrCall30to35);
//				hourlycallcount.setIvrCall35to40(hourlycallcount.getIvrCall35to40() + ivrCall35to40);
//				hourlycallcount.setIvrCallMore40(hourlycallcount.getIvrCallMore40() + ivrCallMore40);
//				hourlycallcount.setObdCallAttempt(hourlycallcount.getObdCallAttempt() + obdCallAttempt);
//				hourlycallcount.setObdCallSuccess(hourlycallcount.getObdCallSuccess() + obdCallSuccess);
//				hourlycallcount.setObdCallDuration(hourlycallcount.getObdCallDuration() + obdCallDuration);
//				hourlycallcount.setObdCallPulses(hourlycallcount.getObdCallPulses() + obdCallPulses);
//				hourlycallcount.setObdFailReason1(hourlycallcount.getObdFailReason1() + obdFailReason1);
//				hourlycallcount.setObdFailReason2(hourlycallcount.getObdFailReason2() + obdFailReason2);
//				hourlycallcount.setObdFailReason3(hourlycallcount.getObdFailReason3() + obdFailReason3);
//				hourlycallcount.setObdFailReason4(hourlycallcount.getObdFailReason4() + obdFailReason4);
//				hourlycallcount.setObdFailReasonx(hourlycallcount.getObdFailReasonx() + obdFailReasonx);
//				hourlycallcount.setObdDtmfrcv0to5(hourlycallcount.getObdDtmfrcv0to5() + obdDtmfrcv0to5);
//				hourlycallcount.setObdDtmfrcv5to10(hourlycallcount.getObdDtmfrcv5to10() + obdDtmfrcv5to10);
//				hourlycallcount.setObdDtmfrcv10to15(hourlycallcount.getObdDtmfrcv10to15() + obdDtmfrcv10to15);
//				hourlycallcount.setObdDtmfrcv15to20(hourlycallcount.getObdDtmfrcv15to20() + obdDtmfrcv15to20);
//				hourlycallcount.setObdDtmfrcv20to25(hourlycallcount.getObdDtmfrcv20to25() + obdDtmfrcv20to25);
//				hourlycallcount.setObdDtmfrcvMore25(hourlycallcount.getObdDtmfrcvMore25() + obdDtmfrcvMore25);
//				hourlycallcount.setObdNoDtmf(hourlycallcount.getObdNoDtmf() + obdNoDtmf);
//				hourlycallcount.setObdSmurlSuccess(hourlycallcount.getObdSmurlSuccess() + obdSmurlSuccess);
//				hourlycallcount.setObdSmurlFail(hourlycallcount.getObdSmurlFail() + obdSmurlFail);
//				hourlycallcount.setObdBillurlSuccess(hourlycallcount.getObdBillurlSuccess() + obdBillurlSuccess);
//				hourlycallcount.setObdBillurlFail(hourlycallcount.getObdBillurlFail() + obdBillurlFail);
//				hourlycallcount.setObdCgurlSuccess(hourlycallcount.getObdCgurlSuccess() + obdCgurlSuccess);
//				hourlycallcount.setObdCgurlFail(hourlycallcount.getObdCgurlFail() + obdCgurlFail);
//				hourlycallcount.setObdSmsurlSuccess(hourlycallcount.getObdSmsurlSuccess() + obdSmsurlSuccess);
//				hourlycallcount.setObdSmsurlFail(hourlycallcount.getObdSmsurlFail() + obdSmsurlFail);
//				hourlycallcount.setObdCrbturlSuccess(hourlycallcount.getObdCrbturlSuccess() + obdCrbturlSuccess);
//				hourlycallcount.setObdCrbturlFail(hourlycallcount.getObdCrbturlFail() + obdCrbturlFail);
//				hourlycallcount.setObdUssdurlSuccess(hourlycallcount.getObdUssdurlSuccess() + obdUssdurlSuccess);
//				hourlycallcount.setObdUssdurlFail(hourlycallcount.getObdUssdurlFail() + obdUssdurlFail);
//				hourlycallcount.setObdWapurlSuccess(hourlycallcount.getObdWapurlSuccess() + obdWapurlSuccess);
//				hourlycallcount.setObdWapurlFail(hourlycallcount.getObdWapurlFail() + obdWapurlFail);
//				hourlycallcount.setObdGenurlSuccess(hourlycallcount.getObdGenurlSuccess() + obdGenurlSuccess);
//				hourlycallcount.setObdGenurlFail(hourlycallcount.getObdGenurlFail() + obdGenurlFail);
//				hourlycallcount.setObdCall0to5(hourlycallcount.getObdCall0to5() + obdCall0to5);
//				hourlycallcount.setObdCall5to10(hourlycallcount.getObdCall5to10() + obdCall5to10);
//				hourlycallcount.setObdCall10to15(hourlycallcount.getObdCall10to15() + obdCall10to15);
//				hourlycallcount.setObdCall15to20(hourlycallcount.getObdCall15to20() + obdCall15to20);
//				hourlycallcount.setObdCall20to25(hourlycallcount.getObdCall20to25() + obdCall20to25);
//				hourlycallcount.setObdCall25to30(hourlycallcount.getObdCall25to30() + obdCall25to30);
//				hourlycallcount.setObdCall30to35(hourlycallcount.getObdCall30to35() + obdCall30to35);
//				hourlycallcount.setObdCall35to40(hourlycallcount.getObdCall35to40() + obdCall35to40);
//				hourlycallcount.setObdCallMore40(hourlycallcount.getObdCallMore40() + obdCallMore40);
//
//			}else{
//
//				hourlycallcount = new CounterSummary();
//				hourlycallcount.setIvrCalls(ivrCalls);
//				hourlycallcount.setIvrDuration(ivrDuration);
//				hourlycallcount.setIvrPulses(ivrPulses);
//				hourlycallcount.setIvrRemoteHangup(ivrRemoteHangup);
//				hourlycallcount.setIvrServiceHangup(ivrServiceHangup);
//				hourlycallcount.setIvrDtmfPress(ivrDtmfPress);
//				hourlycallcount.setIvrMediaNavigation(ivrMediaNavigation);
//				hourlycallcount.setIvrMediaContent(ivrMediaContent);
//				hourlycallcount.setIvrSmurlSuccess(ivrSmurlSuccess);
//				hourlycallcount.setIvrSmurlFail(ivrSmurlFail);
//				hourlycallcount.setIvrBillurlSuccess(ivrBillurlSuccess);
//				hourlycallcount.setIvrBillurlFail(ivrBillurlFail);
//				hourlycallcount.setIvrCgurlSuccess(ivrCgurlSuccess);
//				hourlycallcount.setIvrCgurlFail(ivrCgurlFail);
//				hourlycallcount.setIvrSmsurlSuccess(ivrSmsurlSuccess);
//				hourlycallcount.setIvrSmsurlFail(ivrSmsurlFail);
//				hourlycallcount.setIvrCrbturlSuccess(ivrCrbturlSuccess);
//				hourlycallcount.setIvrCrbturlFail(ivrCrbturlFail);
//				hourlycallcount.setIvrUssdurlSuccess(ivrUssdurlSuccess);
//				hourlycallcount.setIvrUssdurlFail(ivrUssdurlFail);
//				hourlycallcount.setIvrWapurlSuccess(ivrWapurlSuccess);
//				hourlycallcount.setIvrWapurlFail(ivrWapurlFail);
//				hourlycallcount.setIvrGenurlSuccess(ivrGenurlSuccess);
//				hourlycallcount.setIvrGenurlFail(ivrGenurlFail);
//				hourlycallcount.setIvrConfSuccess(ivrConfSuccess);
//				hourlycallcount.setIvrConfDuration(ivrConfDuration);
//				hourlycallcount.setIvrConfPulses(ivrConfPulses);
//				hourlycallcount.setIvrConffailReason1(ivrConffailReason1);
//				hourlycallcount.setIvrConffailReason2(ivrConffailReason2);
//				hourlycallcount.setIvrConffailReason3(ivrConffailReason3);
//				hourlycallcount.setIvrConffailReason4(ivrConffailReason4);
//				hourlycallcount.setIvrConffailReasonx(ivrConffailReasonx);
//				hourlycallcount.setObdDtmfkey0(obdDtmfkey0);
//				hourlycallcount.setObdDtmfkey1(obdDtmfkey1);
//				hourlycallcount.setObdDtmfkey2(obdDtmfkey2);
//				hourlycallcount.setObdDtmfkey3(obdDtmfkey3);
//				hourlycallcount.setObdDtmfkey4(obdDtmfkey4);
//				hourlycallcount.setObdDtmfkey5(obdDtmfkey5);
//				hourlycallcount.setObdDtmfkey6(obdDtmfkey6);
//				hourlycallcount.setObdDtmfkey7(obdDtmfkey7);
//				hourlycallcount.setObdDtmfkey8(obdDtmfkey8);
//				hourlycallcount.setObdDtmfkey9(obdDtmfkey9);
//				hourlycallcount.setObdDtmfkeyStar(obdDtmfkeyStar);
//				hourlycallcount.setObdDtmfkeyHash(obdDtmfkeyHash);
//				hourlycallcount.setObdDtmfkeyX(obdDtmfkeyx);
//				hourlycallcount.setIvrConf0to5(ivrConf0to5);
//				hourlycallcount.setIvrConf5to10(ivrConf5to10);
//				hourlycallcount.setIvrConf10to15(ivrConf10to15);
//				hourlycallcount.setIvrConf15to20(ivrConf15to20);
//				hourlycallcount.setIvrConf20to25(ivrConf20to25);
//				hourlycallcount.setIvrConf25to30(ivrConf25to30);
//				hourlycallcount.setIvrConf30to35(ivrConf30to35);
//				hourlycallcount.setIvrConf35to40(ivrConf35to40);
//				hourlycallcount.setIvrConfMore40(ivrConfMore40);
//				hourlycallcount.setIvrCall0to5(ivrCall0to5);
//				hourlycallcount.setIvrCall5to10(ivrCall5to10);
//				hourlycallcount.setIvrCall10to15(ivrCall10to15);
//				hourlycallcount.setIvrCall15to20(ivrCall15to20);
//				hourlycallcount.setIvrCall20to25(ivrCall20to25);
//				hourlycallcount.setIvrCall25to30(ivrCall25to30);
//				hourlycallcount.setIvrCall30to35(ivrCall30to35);
//				hourlycallcount.setIvrCall35to40(ivrCall35to40);
//				hourlycallcount.setIvrCallMore40(ivrCallMore40);
//				hourlycallcount.setObdCallAttempt(obdCallAttempt);
//				hourlycallcount.setObdCallSuccess(obdCallSuccess);
//				hourlycallcount.setObdCallDuration(obdCallDuration);
//				hourlycallcount.setObdCallPulses(obdCallPulses);
//				hourlycallcount.setObdFailReason1(obdFailReason1);
//				hourlycallcount.setObdFailReason2(obdFailReason2);
//				hourlycallcount.setObdFailReason3(obdFailReason3);
//				hourlycallcount.setObdFailReason4(obdFailReason4);
//				hourlycallcount.setObdFailReasonx(obdFailReasonx);
//				hourlycallcount.setObdDtmfrcv0to5(obdDtmfrcv0to5);
//				hourlycallcount.setObdDtmfrcv5to10(obdDtmfrcv5to10);
//				hourlycallcount.setObdDtmfrcv10to15(obdDtmfrcv10to15);
//				hourlycallcount.setObdDtmfrcv15to20(obdDtmfrcv15to20);
//				hourlycallcount.setObdDtmfrcv20to25(obdDtmfrcv20to25);
//				hourlycallcount.setObdDtmfrcvMore25(obdDtmfrcvMore25);
//				hourlycallcount.setObdNoDtmf(obdNoDtmf);
//				hourlycallcount.setObdSmurlSuccess(obdSmurlSuccess);
//				hourlycallcount.setObdSmurlFail(obdSmurlFail);
//				hourlycallcount.setObdBillurlSuccess(obdBillurlSuccess);
//				hourlycallcount.setObdBillurlFail(obdBillurlFail);
//				hourlycallcount.setObdCgurlSuccess(obdCgurlSuccess);
//				hourlycallcount.setObdCgurlFail(obdCgurlFail);
//				hourlycallcount.setObdSmsurlSuccess(obdSmsurlSuccess);
//				hourlycallcount.setObdSmsurlFail(obdSmsurlFail);
//				hourlycallcount.setObdCrbturlSuccess(obdCrbturlSuccess);
//				hourlycallcount.setObdCrbturlFail(obdCrbturlFail);
//				hourlycallcount.setObdUssdurlSuccess(obdUssdurlSuccess);
//				hourlycallcount.setObdUssdurlFail(obdUssdurlFail);
//				hourlycallcount.setObdWapurlSuccess(obdWapurlSuccess);
//				hourlycallcount.setObdWapurlFail(obdWapurlFail);
//				hourlycallcount.setObdGenurlSuccess(obdGenurlSuccess);
//				hourlycallcount.setObdGenurlFail(obdGenurlFail);
//				hourlycallcount.setObdCall0to5(obdCall0to5);
//				hourlycallcount.setObdCall5to10(obdCall5to10);
//				hourlycallcount.setObdCall10to15(obdCall10to15);
//				hourlycallcount.setObdCall15to20(obdCall15to20);
//				hourlycallcount.setObdCall20to25(obdCall20to25);
//				hourlycallcount.setObdCall25to30(obdCall25to30);
//				hourlycallcount.setObdCall30to35(obdCall30to35);
//				hourlycallcount.setObdCall35to40(obdCall35to40);
//				hourlycallcount.setObdCallMore40(obdCallMore40);
//
//			}
//
//			hourlycallcount.setCallDate(dt_val);
//			hourlycallcount.setHourStamp(ts_val);
//			hourlycallcount.setCountry(country);
//			hourlycallcount.setCircle(circle);
//			hourlycallcount.setOperator(operator);
//			hourlycallcount.setService(service);
//
//			hourcounterBo.insertHourlyCounter(hourlycallcount, country);
//
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			Logger.sysLog(LogValues.error, CdrUnmarshaling.class.getName(), "CDR :: Hourly Counter Parsing failed "+e.getCause());
//			e.printStackTrace();
//		}
//
//	}
//	
//	public void updateDtmfMasterCalllogs(int masterId, String startDateTime, String dtmfDateTime, int dtmfDur, int dtmfPulse, String dtmfInput, String country){	
//		try {
//			DtmfMastercalllogs dtmfmastercalllogs = null;
//			
//			DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
//			Date sdt_val = df.parse(startDateTime);
//			Date ddt_val = df.parse(dtmfDateTime);		
//
//			dtmfmastercalllogs = new DtmfMastercalllogs();
//			dtmfmastercalllogs.setMasterId(masterId);
//			dtmfmastercalllogs.setCallStartDatetime(sdt_val);
//			dtmfmastercalllogs.setDtmfInputDatetime(ddt_val);
//			dtmfmastercalllogs.setDtmfInputDuration(dtmfDur);
//			dtmfmastercalllogs.setDtmfInputPulses(dtmfPulse);
//			dtmfmastercalllogs.setDtmfInputKays(dtmfInput);
//		
//		    dtmfmasterBo.insertDtmfMastercalllogs(dtmfmastercalllogs, country);
//
//		} catch (ParseException e) {
//			Logger.sysLog(LogValues.error, CdrUnmarshaling.class.getName(), "CDR :: DTMF MasterCalllogs Parsing failed "+e.getCause());
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//	
//	public void updateMediaMasterCalllogs(int masterId, String mediaStartTime, String mediaEndTime, int mediaDur, int mediaPulse, String mediaType, String mediaFileName, String mediaCode, String country){	
//		try {
//			MediaMastercalllogs mediamastercalllogs = null;
//			
//			DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
//			Date sdt_val = df.parse(mediaStartTime);
//			Date edt_val = df.parse(mediaEndTime);		
//            int mf_code = Integer.parseInt(mediaCode); 
//            
//			mediamastercalllogs = new MediaMastercalllogs();
//			mediamastercalllogs.setMasterId(masterId);
//			mediamastercalllogs.setStartDatetime(sdt_val);
//			mediamastercalllogs.setEndDatetime(edt_val);
//			mediamastercalllogs.setDuration(mediaDur);
//			mediamastercalllogs.setPulses(mediaPulse);
//			mediamastercalllogs.setMediaType(mediaType);
//			mediamastercalllogs.setMediaFile(mediaFileName);
//			mediamastercalllogs.setMediaCode(mf_code);
//			
//		    mediamasterBo.insertMediaMastercalllogs(mediamastercalllogs, country);
//
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			Logger.sysLog(LogValues.error, CdrUnmarshaling.class.getName(), "CDR :: MEDIA MasterCalllogs Parsing failed "+e.getCause());
//			e.printStackTrace();
//		}
//		
//	}
//	
//	public void updateUrlMasterCalllogs(int masterId, String startDateTime, String urlHitDateTime, int urlDur, int urlPulse, String urlResponse, String urlMethod, String urlType, String urlString, String urlOption, String country){
//		
//		try {
//			UrlMastercalllogs urlmastercalllogs = null;
//			
//			DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
//			Date sdt_val = df.parse(startDateTime);
//			Date edt_val = df.parse(urlHitDateTime);		
//			
//			urlmastercalllogs = new UrlMastercalllogs();
//			urlmastercalllogs.setMasterId(masterId);
//			urlmastercalllogs.setCallStartDatetime(sdt_val);
//			urlmastercalllogs.setUrlHitDatetime(edt_val);
//			urlmastercalllogs.setUrlHitDuration(urlDur);
//			urlmastercalllogs.setUrlHitPulses(urlPulse);
//			urlmastercalllogs.setUrlHitResponse(urlResponse);
//			urlmastercalllogs.setUrlHitMethod(urlMethod);
//			urlmastercalllogs.setUrlType(urlType);
//			urlmastercalllogs.setUrlString(urlString);
//			urlmastercalllogs.setUrlOption(urlOption);
//	
//		    urlmasterBo.insertUrlMastercalllogs(urlmastercalllogs, country);
//
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			Logger.sysLog(LogValues.error, CdrUnmarshaling.class.getName(), "CDR :: URL MasterCalllogs Parsing failed "+e.getCause());
//			e.printStackTrace();
//		}
//	}
//	
//	public void updateConfMasterCalllogs(int masterId, String confId, String systemIp, String plugin, String protocol, String callType, 
//			String aParty, String bParty, String confStartTime, String confEndTime, int confDur, int confPulse, String serviceName, 
//			String circle, String country, String confStatus, String confFailReason){
//		try {
//			IvrConfcalllogs ivrconfcalllogs = null;
//			
//			DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
//			Date sdt_val = df.parse(confStartTime);
//			Date edt_val = df.parse(confEndTime);
//			
//			ivrconfcalllogs = new IvrConfcalllogs();
//			ivrconfcalllogs.setMasterId(masterId);
//			ivrconfcalllogs.setConfId(confId);
//			ivrconfcalllogs.setSysIp(systemIp);
//			ivrconfcalllogs.setPlugin(plugin);
//			ivrconfcalllogs.setProtocol(protocol);
//			ivrconfcalllogs.setCallType(callType);
//			ivrconfcalllogs.setAParty(aParty);
//			ivrconfcalllogs.setBParty(bParty);
//			ivrconfcalllogs.setStartDatetime(sdt_val);
//			ivrconfcalllogs.setEndDatetime(edt_val);
//			ivrconfcalllogs.setDuration(confDur);
//			ivrconfcalllogs.setPulses(confPulse);
//			ivrconfcalllogs.setService(serviceName);
//			ivrconfcalllogs.setCircle(circle);
//			ivrconfcalllogs.setCountry(country);
//			ivrconfcalllogs.setStatus(confStatus);
//			ivrconfcalllogs.setReason(confFailReason);
//			
//			confmasterBo.insertConfMastercalllogs(ivrconfcalllogs, country);
//			
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			Logger.sysLog(LogValues.error, CdrUnmarshaling.class.getName(), "CDR :: CONF MasterCalllogs Parsing failed "+e.getCause());
//			e.printStackTrace();
//		}
//	}
//	
//
//	public int getDuration(String startDt, String endDt){
//		int dur=0;
//		Date d1 = null;
//		Date d2 = null;
//
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//
//		try {
//			d1 = format.parse(startDt);
//			d2 = format.parse(endDt);
//
//			long diff = d2.getTime() - d1.getTime(); 
//			long diffSeconds = diff / 1000;
//			dur = (int) diffSeconds;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return dur;
//	}
//	
//	public int getMasterId(String aParty){
//		
//		int mId=123456789;
//		DateFormat dateFormat = new SimpleDateFormat("mssSSS");
//		Calendar cal = Calendar.getInstance();
//		String val = aParty.substring(8, 10)+""+dateFormat.format(cal.getTime());
//		//System.out.println(val);
//	    mId = Integer.parseInt(val);
//		return mId;
//	}

}
