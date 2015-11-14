/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bng.core.xmlparser.marshal;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author richa
 */
@XmlRootElement(namespace = "MxParams")  
@XmlAccessorType(XmlAccessType.FIELD)
public class MxParam implements Serializable{
	
	@XmlAttribute(name = "shortcode")
	private String shortcode;

	@XmlAttribute(name = "service")
	private String service;

	@XmlAttribute(name = "previousdtmf")
	private String previousdtmf;

	@XmlAttribute(name = "nextdtmf")
	private String nextdtmf;

	@XmlAttribute(name = "promptfile")
	private String promptfile;

	@XmlAttribute(name = "checkdb")
	private String checkdb;

	@XmlAttribute(name = "calltype")
	private String calltype;

	@XmlAttribute(name = "startoverdtmf")
	private String startoverdtmf;

	@XmlAttribute(name = "url")
	private String url;

	@XmlAttribute(name = "addtofavdtmf")
	private String addtofavdtmf;

	@XmlAttribute(name = "defaultlang")
	private String defaultlang;

	@XmlAttribute(name = "method")
	private String method;

	@XmlAttribute(name = "patch")
	private String patch;

	@XmlAttribute(name = "contentlist")
	private String contentlist;

	@XmlAttribute(name = "stoprecord")
	private String stoprecord;

	@XmlAttribute(name = "sleep")
	private String sleep;

	@XmlAttribute(name = "cli")
	private String cli;

	@XmlAttribute(name = "dialout")
	private String dialout;

	@XmlAttribute(name = "recording")
	private String recording;

	@XmlAttribute(name = "repeatcount")
	private String repeatcount;

	@XmlAttribute(name = "repeatcurrent")
	private String repeatcurrent;

	@XmlAttribute(name = "minlen")
	private String minlen;

	@XmlAttribute(name = "maxlen")
	private String maxlen;

	@XmlAttribute(name = "exittimer")
	private String exittimer;

	@XmlAttribute(name = "timeout")
	private String timeout;

	@XmlAttribute(name = "seekdtmf")
	private String seekdtmf;

	@XmlAttribute(name = "autoanswer")
	private String autoanswer;

	@XmlAttribute(name = "randomplay")
	private String randomplay;

	@XmlAttribute(name = "seek")
	private String seek;

	@XmlAttribute(name = "bargein")
	private String bargein;

	@XmlAttribute(name = "type")
	private String type;

	@XmlAttribute(name = "cutonringing")
	private String cutonringing;

	@XmlAttribute(name = "resourceurl")
	private String resourceurl;

	@XmlAttribute(name = "confirmlist")
	private String confirmlist;

	@XmlAttribute(name = "terminationchar")
	private String terminationchar;

	@XmlAttribute(name = "confirmation")
	private String confirmation;

	@XmlAttribute(name = "digittype")
	private String digittype;

	@XmlAttribute(name = "exit")
	private String exit;
	    
    @XmlAttribute(name="dialouttype")
    private String dialouttype;
    
    @XmlAttribute(name="dialouttime")
    private String dialouttime;
    
    @XmlAttribute(name="value")
    private String value;
    
    @XmlAttribute(name="contentid")
    private String contentid;

    @XmlAttribute(name="urltype")
    private String urltype;
    
    @XmlAttribute(name="startfrom")
    private String startfrom;
    
    @XmlAttribute(name="live")
    private String live;
    
    @XmlAttribute(name="timertype")
    private String timertype;
    
    @XmlAttribute(name="timervalue")
    private String timervalue;
    
    @XmlAttribute(name="downloaddtmf")
    private String downloaddtmf;
    
    @XmlAttribute(name="downloadurl")
    private String downloadurl;
    
    @XmlAttribute(name="crbtdtmf")
    private String crbtdtmf;
    
    @XmlAttribute(name="crbturl")
    private String crbturl;
    
    @XmlAttribute(name="voicefreq")
    private String voicefreq;
    
    @XmlAttribute(name="time")
    private String time;
    
    @XmlAttribute(name="callmode")
    private String callmode;
    
    @XmlAttribute(name="synchronous")
    private String synchronous;
    
    @XmlAttribute(name="consentgateway")
    private String consentgateway;
    
    @XmlAttribute(name = "patchwith")
    private String patchwith;
    
    @XmlAttribute(name="cgcli")
    private String cgcli;
    
    @XmlAttribute(name="savefilefor")
    private String savefilefor;
    
    @XmlAttribute(name="savefiledtmf")
    private String savefiledtmf;
    
    @XmlAttribute(name="servicename")
    private String servicename;

    @XmlAttribute(name="optiondealing")
    private String optiondealing;

    @XmlAttribute(name="user")
    private String user;
    
    @XmlAttribute(name="mStringime")
    private String mStringime ;
    
    @XmlAttribute(name="maxtime")
    private String maxtime ;
    
    @XmlAttribute(name="mintime")
    private String mintime ;

	@XmlAttribute(name="dtmfvalue")
    private String dtmfvalue ;
    
    @XmlAttribute(name="percentage")
    private String percentage ;
    
    @XmlAttribute(name="dialbg")
    private String dialbg ;
    
    @XmlAttribute(name="maxcount")
    private String maxcount ;
    
    @XmlAttribute(name="subservicename")
    private String subservicename;   
    
    @XmlAttribute(name="bpartyminlength")
    private String bpartyminlength;
    
    @XmlAttribute(name="validate")
    private String validate;
    
    @XmlAttribute(name="ioccode")
    private String ioccode;
    
    @XmlAttribute(name="prefixioc")
    private String prefixioc;
    
    @XmlAttribute(name="suffixioc")
    private String suffixioc;
    
    @XmlAttribute(name="iocaction")
    private String iocaction;
    
    @XmlAttribute(name="countrycode")
    private String countrycode;
    
    @XmlAttribute(name="prefixconcode")
    private String prefixconcode;
    
    @XmlAttribute(name="suffixconcode")
    private String suffixconcode;
    
    @XmlAttribute(name="conaction")
    private String conaction;
    
    @XmlAttribute(name="apartylength")
    private String apartylength;
    
    @XmlAttribute(name="bpartylength")
    private String bpartylength;
    
    @XmlAttribute(name = "recorddedication")
   	private String recorddedication;
    
    @XmlAttribute(name = "recordurl")
   	private String recordurl;
     
    @XmlAttribute(name = "bpartyprefix")
   	private String bpartyprefix;
    
	@XmlAttribute(name = "recordenddtmf")
  	private String recordenddtmf;
    
    @XmlAttribute(name = "vxmlurl")
  	private String vxmlurl;
    
    @XmlAttribute(name = "patchmode")
  	private String patchmode;
    
    @XmlAttribute(name = "patchtimer")
  	private String patchtimer;
    
    @XmlAttribute(name = "patchtimevalue")
  	private String patchtimevalue;
    
    @XmlAttribute(name = "dynamicplayurl")
  	private String dynamicplayurl;
    
    @XmlAttribute(name = "jumpfor")
  	private String jumpfor;
    
    @XmlAttribute(name = "singlebookmark")
  	private String singlebookmark;
    
    @XmlAttribute(name = "previousbookdtmf")
  	private String previousbookdtmf;
    
    @XmlAttribute(name = "nextbookdtmf")
  	private String nextbookdtmf;
    
    @XmlAttribute(name = "playlistnumber")
  	private String playlistnumber;
    
    @XmlAttribute(name = "playlist")
  	private String playlist;
    
    @XmlAttribute(name = "allowgreater")
  	private String allowgreater;
    
    @XmlAttribute(name = "resourceurlstart")
  	private String resourceurlstart;

    @XmlAttribute(name = "invalidcharacters")
  	private String invalidcharacters;

    @XmlAttribute(name = "checkfirst")
  	private String checkfirst;
    
    @XmlAttribute(name = "bpartyprefixaction")
  	private String bpartyprefixaction;
    
    @XmlAttribute(name = "probability")
  	private String probability;
    
	public String getShortcode() {
		return shortcode;
	}

	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getPreviousdtmf() {
		return previousdtmf;
	}

	public void setPreviousdtmf(String previousdtmf) {
		this.previousdtmf = previousdtmf;
	}

	public String getNextdtmf() {
		return nextdtmf;
	}

	public void setNextdtmf(String nextdtmf) {
		this.nextdtmf = nextdtmf;
	}

	public String getPromptfile() {
		return promptfile;
	}

	public void setPromptfile(String promptfile) {
		this.promptfile = promptfile;
	}

	public String getCheckdb() {
		return checkdb;
	}

	public void setCheckdb(String checkdb) {
		this.checkdb = checkdb;
	}

	public String getCalltype() {
		return calltype;
	}

	public void setCalltype(String calltype) {
		this.calltype = calltype;
	}

	public String getStartoverdtmf() {
		return startoverdtmf;
	}

	public void setStartoverdtmf(String startoverdtmf) {
		this.startoverdtmf = startoverdtmf;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAddtofavdtmf() {
		return addtofavdtmf;
	}

	public void setAddtofavdtmf(String addtofavdtmf) {
		this.addtofavdtmf = addtofavdtmf;
	}

	public String getDefaultlang() {
		return defaultlang;
	}

	public void setDefaultlang(String defaultlang) {
		this.defaultlang = defaultlang;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getPatch() {
		return patch;
	}

	public void setPatch(String patch) {
		this.patch = patch;
	}

	public String getContentlist() {
		return contentlist;
	}

	public void setContentlist(String contentlist) {
		this.contentlist = contentlist;
	}

	public String getStoprecord() {
		return stoprecord;
	}

	public void setStoprecord(String stoprecord) {
		this.stoprecord = stoprecord;
	}

	public String getSleep() {
		return sleep;
	}

	public void setSleep(String sleep) {
		this.sleep = sleep;
	}

	public String getCli() {
		return cli;
	}

	public void setCli(String cli) {
		this.cli = cli;
	}

	public String getDialout() {
		return dialout;
	}

	public void setDialout(String dialout) {
		this.dialout = dialout;
	}

	public String getRecording() {
		return recording;
	}

	public void setRecording(String recording) {
		this.recording = recording;
	}

	public String getRepeatcount() {
		return repeatcount;
	}

	public void setRepeatcount(String repeatcount) {
		this.repeatcount = repeatcount;
	}

	public String getRepeatcurrent() {
		return repeatcurrent;
	}

	public void setRepeatcurrent(String repeatcurrent) {
		this.repeatcurrent = repeatcurrent;
	}

	public String getMinlen() {
		return minlen;
	}

	public void setMinlen(String minlen) {
		this.minlen = minlen;
	}

	public String getMaxlen() {
		return maxlen;
	}

	public void setMaxlen(String maxlen) {
		this.maxlen = maxlen;
	}

	public String getExittimer() {
		return exittimer;
	}

	public void setExittimer(String exittimer) {
		this.exittimer = exittimer;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getSeekdtmf() {
		return seekdtmf;
	}

	public void setSeekdtmf(String seekdtmf) {
		this.seekdtmf = seekdtmf;
	}

	public String getAutoanswer() {
		return autoanswer;
	}

	public void setAutoanswer(String autoanswer) {
		this.autoanswer = autoanswer;
	}

	public String getRandomplay() {
		return randomplay;
	}

	public void setRandomplay(String randomplay) {
		this.randomplay = randomplay;
	}

	public String getSeek() {
		return seek;
	}

	public void setSeek(String seek) {
		this.seek = seek;
	}

	public String getBargein() {
		return bargein;
	}

	public void setBargein(String bargein) {
		this.bargein = bargein;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCutonringing() {
		return cutonringing;
	}

	public void setCutonringing(String cutonringing) {
		this.cutonringing = cutonringing;
	}

	public String getResourceurl() {
		return resourceurl;
	}

	public void setResourceurl(String resourceurl) {
		this.resourceurl = resourceurl;
	}

	public String getConfirmlist() {
		return confirmlist;
	}

	public void setConfirmlist(String confirmlist) {
		this.confirmlist = confirmlist;
	}

	public String getTerminationchar() {
		return terminationchar;
	}

	public void setTerminationchar(String terminationchar) {
		this.terminationchar = terminationchar;
	}

	public String getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}

	public String getDigittype() {
		return digittype;
	}

	public void setDigittype(String digittype) {
		this.digittype = digittype;
	}

	public String getExit() {
		return exit;
	}

	public void setExit(String exit) {
		this.exit = exit;
	}

	public String getDialouttype() {
		return dialouttype;
	}

	public void setDialouttype(String dialouttype) {
		this.dialouttype = dialouttype;
	}

	public String getDialouttime() {
		return dialouttime;
	}

	public void setDialouttime(String dialouttime) {
		this.dialouttime = dialouttime;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getContentid() {
		return contentid;
	}

	public void setContentid(String contentid) {
		this.contentid = contentid;
	}

	public String getUrltype() {
		return urltype;
	}

	public void setUrltype(String urltype) {
		this.urltype = urltype;
	}

	public String getStartfrom() {
		return startfrom;
	}

	public void setStartfrom(String startfrom) {
		this.startfrom = startfrom;
	}

	public String getLive() {
		return live;
	}

	public void setLive(String live) {
		this.live = live;
	}

	public String getTimertype() {
		return timertype;
	}

	public void setTimertype(String timertype) {
		this.timertype = timertype;
	}

	public String getTimervalue() {
		return timervalue;
	}

	public void setTimervalue(String timervalue) {
		this.timervalue = timervalue;
	}

	public String getDownloaddtmf() {
		return downloaddtmf;
	}

	public void setDownloaddtmf(String downloaddtmf) {
		this.downloaddtmf = downloaddtmf;
	}

	public String getDownloadurl() {
		return downloadurl;
	}

	public void setDownloadurl(String downloadurl) {
		this.downloadurl = downloadurl;
	}

	public String getCrbtdtmf() {
		return crbtdtmf;
	}

	public void setCrbtdtmf(String crbtdtmf) {
		this.crbtdtmf = crbtdtmf;
	}

	public String getCrbturl() {
		return crbturl;
	}

	public void setCrbturl(String crbturl) {
		this.crbturl = crbturl;
	}

	public String getVoicefreq() {
		return voicefreq;
	}

	public void setVoicefreq(String voicefreq) {
		this.voicefreq = voicefreq;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCallmode() {
		return callmode;
	}

	public void setCallmode(String callmode) {
		this.callmode = callmode;
	}

	public String getSynchronous() {
		return synchronous;
	}

	public void setSynchronous(String synchronous) {
		this.synchronous = synchronous;
	}

	public String getConsentgateway() {
		return consentgateway;
	}

	public void setConsentgateway(String consentgateway) {
		this.consentgateway = consentgateway;
	}

	public String getPatchwith() {
		return patchwith;
	}

	public void setPatchwith(String patchwith) {
		this.patchwith = patchwith;
	}

	public String getCgcli() {
		return cgcli;
	}

	public void setCgcli(String cgcli) {
		this.cgcli = cgcli;
	}

	public String getSavefilefor() {
		return savefilefor;
	}

	public void setSavefilefor(String savefilefor) {
		this.savefilefor = savefilefor;
	}

	public String getSavefiledtmf() {
		return savefiledtmf;
	}

	public void setSavefiledtmf(String savefiledtmf) {
		this.savefiledtmf = savefiledtmf;
	}

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public String getOptiondealing() {
		return optiondealing;
	}

	public void setOptiondealing(String optiondealing) {
		this.optiondealing = optiondealing;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getmStringime() {
		return mStringime;
	}

	public void setmStringime(String mStringime) {
		this.mStringime = mStringime;
	}

	public String getMaxtime() {
		return maxtime;
	}

	public void setMaxtime(String maxtime) {
		this.maxtime = maxtime;
	}

	public String getDtmfvalue() {
		return dtmfvalue;
	}

	public void setDtmfvalue(String dtmfvalue) {
		this.dtmfvalue = dtmfvalue;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getDialbg() {
		return dialbg;
	}

	public void setDialbg(String dialbg) {
		this.dialbg = dialbg;
	}

	public String getMaxcount() {
		return maxcount;
	}

	public void setMaxcount(String maxcount) {
		this.maxcount = maxcount;
	}

	public String getSubservicename() {
		return subservicename;
	}

	public void setSubservicename(String subservicename) {
		this.subservicename = subservicename;
	}

	public String getBpartyminlength() {
		return bpartyminlength;
	}

	public void setBpartyminlength(String bpartyminlength) {
		this.bpartyminlength = bpartyminlength;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	public String getIoccode() {
		return ioccode;
	}

	public void setIoccode(String ioccode) {
		this.ioccode = ioccode;
	}

	public String getPrefixioc() {
		return prefixioc;
	}

	public void setPrefixioc(String prefixioc) {
		this.prefixioc = prefixioc;
	}

	public String getSuffixioc() {
		return suffixioc;
	}

	public void setSuffixioc(String suffixioc) {
		this.suffixioc = suffixioc;
	}

	public String getIocaction() {
		return iocaction;
	}

	public void setIocaction(String iocaction) {
		this.iocaction = iocaction;
	}

	public String getCountrycode() {
		return countrycode;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	public String getPrefixconcode() {
		return prefixconcode;
	}

	public void setPrefixconcode(String prefixconcode) {
		this.prefixconcode = prefixconcode;
	}

	public String getSuffixconcode() {
		return suffixconcode;
	}

	public void setSuffixconcode(String suffixconcode) {
		this.suffixconcode = suffixconcode;
	}

	public String getConaction() {
		return conaction;
	}

	public void setConaction(String conaction) {
		this.conaction = conaction;
	}

	public String getApartylength() {
		return apartylength;
	}

	public void setApartylength(String apartylength) {
		this.apartylength = apartylength;
	}

	public String getBpartylength() {
		return bpartylength;
	}

	public void setBpartylength(String bpartylength) {
		this.bpartylength = bpartylength;
	}

	public String getRecorddedication() {
		return recorddedication;
	}

	public void setRecorddedication(String recorddedication) {
		this.recorddedication = recorddedication;
	}

	public String getRecordenddtmf() {
		return recordenddtmf;
	}

	public void setRecordenddtmf(String recordenddtmf) {
		this.recordenddtmf = recordenddtmf;
	}

	public String getVxmlurl() {
		return vxmlurl;
	}

	public void setVxmlurl(String vxmlurl) {
		this.vxmlurl = vxmlurl;
	}

	public String getPatchmode() {
		return patchmode;
	}

	public void setPatchmode(String patchmode) {
		this.patchmode = patchmode;
	}

	public String getPatchtimer() {
		return patchtimer;
	}

	public void setPatchtimer(String patchtimer) {
		this.patchtimer = patchtimer;
	}

	public String getPatchtimevalue() {
		return patchtimevalue;
	}

	public void setPatchtimevalue(String patchtimevalue) {
		this.patchtimevalue = patchtimevalue;
	}

	public String getDynamicplayurl() {
		return dynamicplayurl;
	}

	public void setDynamicplayurl(String dynamicplayurl) {
		this.dynamicplayurl = dynamicplayurl;
	}

	public String getJumpfor() {
		return jumpfor;
	}

	public void setJumpfor(String jumpfor) {
		this.jumpfor = jumpfor;
	}

	 public String getRecordurl() {
			return recordurl;
		}

		public void setRecordurl(String recordurl) {
			this.recordurl = recordurl;
		}

		public String getBpartyprefix() {
			return bpartyprefix;
		}

		public void setBpartyprefix(String bpartyprefix) {
			this.bpartyprefix = bpartyprefix;
		}

		public String getSinglebookmark() {
			return singlebookmark;
		}

		public void setSinglebookmark(String singlebookmark) {
			this.singlebookmark = singlebookmark;
		}

		public String getPreviousbookdtmf() {
			return previousbookdtmf;
		}

		public void setPreviousbookdtmf(String previousbookdtmf) {
			this.previousbookdtmf = previousbookdtmf;
		}

		public String getNextbookdtmf() {
			return nextbookdtmf;
		}

		public void setNextbookdtmf(String nextbookdtmf) {
			this.nextbookdtmf = nextbookdtmf;
		}

		public String getPlaylistnumber() {
			return playlistnumber;
		}

		public void setPlaylistnumber(String playlistnumber) {
			this.playlistnumber = playlistnumber;
		}

		public String getPlaylist() {
			return playlist;
		}

		public void setPlaylist(String playlist) {
			this.playlist = playlist;
		}

		public String getAllowgreater() {
			return allowgreater;
		}

		public void setAllowgreater(String allowgreater) {
			this.allowgreater = allowgreater;
		}

		public String getResourceurlstart() {
			return resourceurlstart;
		}

		public void setResourceurlstart(String resourceurlstart) {
			this.resourceurlstart = resourceurlstart;
		}

		public String getInvalidcharacters() {
			return invalidcharacters;
		}

		public void setInvalidcharacters(String invalidcharacters) {
			this.invalidcharacters = invalidcharacters;
		}

		public String getCheckfirst() {
			return checkfirst;
		}

		public void setCheckfirst(String checkfirst) {
			this.checkfirst = checkfirst;
		}

		public String getBpartyprefixaction() {
			return bpartyprefixaction;
		}

		public void setBpartyprefixaction(String bpartyprefixaction) {
			this.bpartyprefixaction = bpartyprefixaction;
		}
		
	    
	    public String getMintime()
		{
			return mintime;
		}

		public void setMintime(String mintime)
		{
			this.mintime = mintime;
		}

		public String getProbability()
		{
			return probability;
		}

		public void setProbability(String probability)
		{
			this.probability = probability;
		}

		
}