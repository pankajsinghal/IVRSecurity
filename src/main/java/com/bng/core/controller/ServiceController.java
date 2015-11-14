package com.bng.core.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Principal;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.bng.core.bean.Blackout;
import com.bng.core.bean.Editjob;
import com.bng.core.bean.JobBean;
import com.bng.core.bean.JobName;
import com.bng.core.entity.Mxgraph;
import com.bng.core.entity.MxgraphVersion;
import com.bng.core.entity.ObdBlackoutHours;
import com.bng.core.entity.ObdCli;
import com.bng.core.entity.ObdFailureReasons;
import com.bng.core.entity.Service;
import com.bng.core.entity.Users;
import com.bng.core.service.SchedulerBo;
import com.bng.core.service.UserService;
import com.bng.core.util.LogValues;
import com.bng.core.util.Logger;
import com.bng.core.util.Utility;
import com.bng.core.util.coreException;
import com.bng.core.xmlparser.marshal.ConverterCoreEngine;

/**
 * @author Anjali Sarkar
 * 
 */

@Controller
public class ServiceController {

	@Autowired
	private SchedulerBo schedulerBo;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/newjob.htm", method = RequestMethod.GET)
	public String newjob(Principal principal, ModelMap model) {
		JobBean job = new JobBean();
		model.addAttribute("job", job);
		String username = principal.getName();
		Users currentUser = userService.getUser(username);
		Logger.sysLog(LogValues.info, this.getClass().getName(), username);
		List mxgraphVersions = schedulerBo.getmxgraph(currentUser.getId());
		Iterator iter = mxgraphVersions.iterator();
		List<Mxgraph> mxGraphList = new ArrayList<Mxgraph>();
		while (iter.hasNext()) {
			MxgraphVersion mxGraphVersion = (MxgraphVersion) iter.next();
			if (mxGraphVersion.getMxgraph().getCallType()
					.equalsIgnoreCase("obd"))
				mxGraphList.add(mxGraphVersion.getMxgraph());
		}
		model.addAttribute("mxGraphList", mxGraphList);
		if (mxGraphList.size() > 0)
			Logger.sysLog(LogValues.info, this.getClass().getName(),
					mxGraphList.get(0).getServiceName());
		return "newjob";
	}

	@RequestMapping(value = "/duplicate.htm", method = RequestMethod.POST)
	public @ResponseBody
	String duplicatejob(@ModelAttribute(value = "jname") JobName jname,
			BindingResult result) {
		Logger.sysLog(LogValues.info, this.getClass().getName(),
				"--In controller--");
		String job = schedulerBo.getJobname(jname.getJobname());
		String returnText = null;
		if (job != null) {
			returnText = "Jobname Exists please enter another <input type='hidden' name='check_job' id='check_job'>";
		} else {
			returnText = "<input type='hidden' name='check_job' id='check_job' value='1'>";
		}
		Logger.sysLog(LogValues.info, this.getClass().getName(), "return text="
				+ returnText);
		return returnText;
	}

	@RequestMapping(value = "/getObdFailureReasons")
	public @ResponseBody String getObdFailureReasons() {
		List list = schedulerBo.getObdFailureReasons();
		Iterator iterator = list.iterator();
		JSONObject obj = new JSONObject();
		while(iterator.hasNext()){
			ObdFailureReasons failureReasons = (ObdFailureReasons) iterator.next();
			try {
				obj.put(failureReasons.getCeReason()+"", failureReasons.getReasonValue());
			} catch (JSONException e) {
				Logger.sysLog(LogValues.error, this.getClass().getName(),coreException.GetStack(e));
			}
		}
		return obj.toString();
	}
//	@RequestMapping(value = "/newjob.htm", method = RequestMethod.POST)
//	public String addjob(@ModelAttribute("job") JobBean jb,
//			BindingResult result, SessionStatus status) {
//		String[] values = jb.getServiceName().split("[()]");
//		String serviceName = values[0];
//		String shortCode = values[1];
//		Mxgraph mxgraph = schedulerBo.getmxgraph(serviceName, shortCode);
//		Logger.sysLog(LogValues.info, this.getClass().getName(),
//				jb.getServiceName());
//		ObdBlackoutHours obh = schedulerBo.getobh(1);
//		System.out.println(jb.getStartDate());
//		System.out.println(jb.getEndDate());
//		SimpleDateFormat dateformatter = new SimpleDateFormat("MM/dd/yyy");
//		SimpleDateFormat timeformatter = new SimpleDateFormat("HH:mm:ss");
//		Date StartDate = null;
//		Date EndDate = null;
//		try {
//			StartDate = dateformatter.parse(jb.getStartDate());
//			EndDate = dateformatter.parse(jb.getEndDate());
//		} catch (ParseException e) {
//			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(),
//					coreException.GetStack(e));
//		}
//		System.out.println(StartDate);
//		Date StartTime = null;
//		Date EndTime = null;
//		try {
//			StartTime = timeformatter.parse(jb.getStartTime());
//			EndTime = timeformatter.parse(jb.getEndTime());
//		} catch (ParseException e) {
//			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(),
//					coreException.GetStack(e));
//		}
//		Service service = new Service(mxgraph, obh, jb.getJobName(), StartDate,
//				EndDate, StartTime, EndTime, jb.getPriority(), "scheduled");
//
//		MultipartFile dataFile = jb.getMsisdn();
//		Logger.sysLog(LogValues.debug, this.getClass().getName(),
//				"Uploaded file Size " + dataFile.getSize());
//		Logger.sysLog(LogValues.info, this.getClass().getName(),
//				jb.getServiceName());
//		Logger.sysLog(LogValues.info, this.getClass().getName(),
//				jb.getEndDate());
//		InputStream is = null;
//		File tempFile = new File(System.getProperty("java.io.tmpdir")
//				+ jb.getJobName());
//		BufferedReader br = null;
//		FileOutputStream out = null;
//		try {
//			is = dataFile.getInputStream();
//
//			br = new BufferedReader(new InputStreamReader(is));
//			out = new FileOutputStream(tempFile);
//			String line;
//
//			while ((line = br.readLine()) != null) {
//
//				if (line.length() < 10)
//					continue;
//				String corrected = line.substring(line.length() - 10);
//				if (!(corrected.matches("[0-9]+")))
//					continue;
//
//				out.write((corrected + "\r\n").getBytes());
//			}
//		} catch (IOException e) {
//			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(),
//					coreException.GetStack(e));
//		}
//		finally {
//	        try {
//	        	out.close();
//				br.close();
//				is.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	    }
//		schedulerBo.addMsisdns(tempFile.getAbsolutePath(), service.getJobname());
//		tempFile.delete();
//		String tableName = "obd_msisdn_" + jb.getJobName();
//		Logger.sysLog(LogValues.info, this.getClass().getName(), tableName
//				+ ": table name");
//		Logger.sysLog(LogValues.info, this.getClass().getName(),
//				jb.getDndOption() + ": dnd preference");
//
//		if (jb.isDndcheck()) {
//			Logger.sysLog(LogValues.info, this.getClass().getName(),
//					jb.getDndOption() + "dnd option:" + jb.getDndOption());
//			schedulerBo.dndFilter(tableName, jb.getDndOption());
//		}
//		schedulerBo.addJob(service);
//		Service serv = schedulerBo.getService(serviceName, shortCode);
//		Logger.sysLog(LogValues.info, this.getClass().getName(),
//				jb.getCliNumber());
//		// Logger.sysLog(LogValues.info, serv.getMxgraph().getId());
//		String[] clinumber = jb.getCliNumber().split(",");
//		for (int i = 0; i < clinumber.length; i++) {
//			Logger.sysLog(LogValues.info, this.getClass().getName(),
//					serv.getId() + " :service_id , mxGrapgId : "
//							+ serv.getMxgraph().getId());
//			ObdCli cli = new ObdCli(serv, clinumber[i]);
//			Logger.sysLog(LogValues.info, this.getClass().getName(),
//					serv.getId() + "service_id");
//			Logger.sysLog(LogValues.info, this.getClass().getName(),
//					clinumber[i] + "cli");
//			schedulerBo.addCli(cli);
//
//		}
//
//		Logger.sysLog(LogValues.info, this.getClass().getName(),
//				jb.getStartDate());
//		Logger.sysLog(LogValues.info, this.getClass().getName(),
//				jb.getStartTime());
//		Logger.sysLog(LogValues.info, this.getClass().getName(), StartDate);
//		Logger.sysLog(LogValues.info, this.getClass().getName(), StartTime);
//		return "jobadded";
//	}

	@RequestMapping(value = "/newjob.htm", method = RequestMethod.POST)
	public String addjob(HttpServletRequest request, ModelMap modelMap) {
		try {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List items = upload.parseRequest(request);
			Iterator iterator = items.iterator();
			JobBean jb = new JobBean();
			while (iterator.hasNext()) {
				FileItem fi = (FileItem) iterator.next();
				Logger.sysLog(LogValues.info, this.getClass().getName(),
						fi.getFieldName() + " :" + fi.getString());
				switch (fi.getFieldName()) {
				case "serviceName":
					jb.setServiceName(fi.getString());
					break;
				case "jobName":
					jb.setJobName(fi.getString());
					break;
				case "jobType":
					jb.setJobType(fi.getString());
					break;
				case "startDate":
					jb.setStartDate(fi.getString());
					break;
				case "endDate":
					jb.setEndDate(fi.getString());
					break;
				case "startTime":
					jb.setStartTime(fi.getString());
					break;
				case "endTime":
					jb.setEndTime(fi.getString());
					break;
				case "dndcheck":
					jb.setDndcheck(fi.getString().equalsIgnoreCase("true"));
					break;
				case "dndOption":
					jb.setDndOption(Integer.parseInt(fi.getString()));
					break;
				case "cliNumber":
					jb.setCliNumber(fi.getString());
					break;
				case "msisdn":
					jb.setMsisdn(new CommonsMultipartFile(fi));
					break;
				case "priority":
					jb.setPriority(Integer.parseInt(fi.getString()));
					break;
				case "retrycheck":
					jb.setRetrycheck(fi.getString().equalsIgnoreCase("true"));
					break;
				case "retryreason":
					jb.setRetryreason(fi.getString());
				}

			}

			if (jb.getJobType().equalsIgnoreCase("starcopy") || jb.getJobType().equalsIgnoreCase("recorddedication")) {
				jb.setStartDate("01/01/1900");
				jb.setEndDate("01/01/2400");
				jb.setStartTime("00:00:00");
				jb.setEndTime("23:59:59");
			}

			int maxRetry = 0;
			if (!jb.isRetrycheck()) {
				jb.setRetryreason(null);
			} else {
				try {
					JSONObject jsonObject = new JSONObject(jb.getRetryreason());
					Iterator ite = jsonObject.keys();
					while (ite.hasNext()) {
						String s = (String) ite.next();
						if (s.equalsIgnoreCase("max-retry")) {
							maxRetry = jsonObject.getInt(s);
						}

					}
				} catch (JSONException e) {
					Logger.sysLog(LogValues.error,
							ConverterCoreEngine.class.getName(),
							coreException.GetStack(e));
				}
			}
			// JobBean jb=null;
			String[] values = jb.getServiceName().split("[()]");
			String serviceName = values[0];
			String shortCode = values[1];
			Mxgraph mxgraph = schedulerBo.getmxgraph(serviceName, shortCode);
			Logger.sysLog(LogValues.info, this.getClass().getName(),
					jb.getServiceName());
			ObdBlackoutHours obh = schedulerBo.getobh(1);
			Logger.sysLog(LogValues.info, this.getClass().getName(),
					jb.getStartDate());
			Logger.sysLog(LogValues.info, this.getClass().getName(),
					jb.getEndDate());
			SimpleDateFormat dateformatter = new SimpleDateFormat("MM/dd/yyy");
			SimpleDateFormat timeformatter = new SimpleDateFormat("HH:mm:ss");
			Date StartDate = null;
			Date EndDate = null;
			try {
				StartDate = dateformatter.parse(jb.getStartDate());
				EndDate = dateformatter.parse(jb.getEndDate());
			} catch (Exception e) {
				Logger.sysLog(LogValues.error,
						ConverterCoreEngine.class.getName(),
						coreException.GetStack(e));
				modelMap.addAttribute("status",
						"'Could not add job. Error in parsing date.'");
				return "jobadded";
			}
			Logger.sysLog(LogValues.info, this.getClass().getName(), StartDate);
			Date StartTime = null;
			Date EndTime = null;
			try {
				StartTime = timeformatter.parse(jb.getStartTime());
				EndTime = timeformatter.parse(jb.getEndTime());
			} catch (ParseException e) {
				Logger.sysLog(LogValues.error,
						ConverterCoreEngine.class.getName(),
						coreException.GetStack(e));
				modelMap.addAttribute("status",
						"'Could not add job. Error in parsing time.'");
				return "jobadded";
			}
			Service service = new Service(mxgraph, obh, jb.getJobName(),
					StartDate, EndDate, StartTime, EndTime, jb.getPriority(),
					"scheduled", maxRetry, maxRetry,jb.getJobType().equalsIgnoreCase("starcopy"),jb.getJobType().equalsIgnoreCase("recorddedication"));

			File tempFile = new File(System.getProperty("java.io.tmpdir")
					+ jb.getJobName());
			if (jb.getJobType().equalsIgnoreCase("starcopy") || jb.getJobType().equalsIgnoreCase("recorddedication")){ 
				tempFile.getParentFile().mkdirs();
				tempFile.createNewFile();
			}
			else if(jb.getJobType().equalsIgnoreCase("normal")){
				MultipartFile dataFile = jb.getMsisdn();
				// dataFile = new CommonsMultipartFile(null);
				Logger.sysLog(LogValues.debug, this.getClass().getName(),
						"Uploaded file Size " + dataFile.getSize());
				Logger.sysLog(LogValues.info, this.getClass().getName(),
						jb.getServiceName());
				Logger.sysLog(LogValues.info, this.getClass().getName(),
						jb.getEndDate());
				InputStream is = null;

				BufferedReader br = null;
				FileOutputStream out = null;
				try {
					is = dataFile.getInputStream();

					br = new BufferedReader(new InputStreamReader(is));
					out = new FileOutputStream(tempFile);
					String line;

					while ((line = br.readLine()) != null) {

						if (line.length() < Utility.obdMsisdnLength)
							continue;
						String corrected = line.substring(line.length() - Utility.obdMsisdnLength);
						if (!(corrected.matches("[0-9]+")))
							continue;

						out.write((corrected + "\r\n").getBytes());
					}
				} catch (IOException e) {
					Logger.sysLog(LogValues.error,
							ConverterCoreEngine.class.getName(),
							coreException.GetStack(e));
					modelMap.addAttribute("status",
							"'Could not add job. Error in reading msisdn file.'");
					return "jobadded";
				} finally {
					try {
						out.close();
						br.close();
						is.close();
					} catch (IOException e) {
						Logger.sysLog(LogValues.error,
								ConverterCoreEngine.class.getName(),
								coreException.GetStack(e));
					}
				}
			}
			String status = null;
			try {
				schedulerBo.addJob(tempFile.getAbsolutePath(), service,
						serviceName, shortCode, jb);
			} catch (Exception e) {
				status = ExceptionUtils.getRootCauseMessage(e).replace("'", "");
				if (status != null
						&& !status.contains("Use a different Jobname.")
						&& schedulerBo.getJobname(jb.getJobName()) == null) {
					ArrayList<String> create_table = new ArrayList<String>();
					// String deleteTable =
					// "drop table if exists obd_failure_reason_"+service.getJobname();
					// create_table.add(deleteTable);
					// String sql =
					// "drop table if exists obd_msisdn_"+service.getJobname();
					String tableExistsCheck = "select count(*) as len from  information_schema.tables where table_schema = 'ivr_data' AND table_name = 'obd_msisdn_"
							+ service.getJobname() + "'";
					ResultSet resultSet = schedulerBo
							.executeQuery(tableExistsCheck);
					if (resultSet != null && resultSet.first()
							&& resultSet.getInt("len") > 0) {
						Logger.sysLog(
								LogValues.error,
								ConverterCoreEngine.class.getName(),
								"renaming table obd_msisdn_"
										+ service.getJobname());
						String sql = "rename table obd_msisdn_"
								+ service.getJobname()
								+ " to failed_obd_msisdn_"
								+ service.getJobname() + "_"
								+ System.currentTimeMillis();
						schedulerBo.addMsisdnsMysql(create_table, sql);
					}
				}

			}
			tempFile.delete();
			if (status == null) {
				modelMap.addAttribute("status", "'Job added Successfully.'");
				return "jobadded";
			} else {
				modelMap.addAttribute(
						"status",
						"'Could not add job."
								+ status.substring(status.indexOf(": ") + 1)
								+ "'");
				return "jobadded";
			}
			// schedulerBo.addMsisdns(tempFile.getAbsolutePath(),
			// service.getJobname());
			//
			// String tableName = "obd_msisdn_" + jb.getJobName();
			// Logger.sysLog(LogValues.info, this.getClass().getName(),
			// tableName
			// + ": table name");
			// Logger.sysLog(LogValues.info, this.getClass().getName(),
			// jb.getDndOption() + ": dnd preference");
			//
			// if (jb.isDndcheck()) {
			// Logger.sysLog(LogValues.info, this.getClass().getName(),
			// jb.getDndOption() + "dnd option:" + jb.getDndOption());
			// schedulerBo.dndFilter(tableName, jb.getDndOption());
			// }
			// schedulerBo.addJob(service);
			// Service serv = schedulerBo.getService(serviceName, shortCode);
			// Logger.sysLog(LogValues.info, this.getClass().getName(),
			// jb.getCliNumber());
			// // Logger.sysLog(LogValues.info, serv.getMxgraph().getId());
			// String[] clinumber = jb.getCliNumber().split(",");
			// for (int i = 0; i < clinumber.length; i++) {
			// Logger.sysLog(LogValues.info, this.getClass().getName(),
			// serv.getId() + " :service_id , mxGrapgId : "
			// + serv.getMxgraph().getId());
			// ObdCli cli = new ObdCli(serv, clinumber[i]);
			// Logger.sysLog(LogValues.info, this.getClass().getName(),
			// serv.getId() + "service_id");
			// Logger.sysLog(LogValues.info, this.getClass().getName(),
			// clinumber[i] + "cli");
			// schedulerBo.addCli(cli);
			//
			// }
			//
			// Logger.sysLog(LogValues.info, this.getClass().getName(),
			// jb.getStartDate());
			// Logger.sysLog(LogValues.info, this.getClass().getName(),
			// jb.getStartTime());
			// Logger.sysLog(LogValues.info, this.getClass().getName(),
			// StartDate);
			// Logger.sysLog(LogValues.info, this.getClass().getName(),
			// StartTime);
		} catch (Exception e) {
			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(),
					coreException.GetStack(e));
			modelMap.addAttribute("status",
					"'Could not add job. Unknown Error.'");
			return "jobadded";
		}
	}

	@RequestMapping(value = "/geteditjobrequest.htm", method = RequestMethod.POST)
	public String editjob(@ModelAttribute("job") Editjob ejob,
			BindingResult result, SessionStatus status) {
		Logger.sysLog(LogValues.info, this.getClass().getName(), "jobname :"
				+ ejob.getJobName());
		Logger.sysLog(LogValues.info, this.getClass().getName(),
				"inside anjali's controller");
		Service service = schedulerBo.getService(ejob.getJobName());
		SimpleDateFormat dateformatter = new SimpleDateFormat("MM/dd/yyy");
		SimpleDateFormat timeformatter = new SimpleDateFormat("HH:mm:ss");
		Date StartDate = null;
		Date EndDate = null;
		try {
			StartDate = dateformatter.parse(ejob.getStartDate());
			EndDate = dateformatter.parse(ejob.getEndDate());
		} catch (ParseException e) {
			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(),
					coreException.GetStack(e));
		}
		Date StartTime = null;
		Date EndTime = null;
		try {
			StartTime = timeformatter.parse(ejob.getStartTime());
			EndTime = timeformatter.parse(ejob.getEndTime());
		} catch (ParseException e) {
			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(),
					coreException.GetStack(e));
		}
		service.setStartDate(StartDate);
		service.setEndDate(EndDate);
		service.setStartTime(StartTime);
		service.setEndTime(EndTime);
		service.setPriority(ejob.getPriority());
		schedulerBo.updatejob(service);
		schedulerBo.deleteCli(service);
		String[] clinumber = ejob.getCliNumber().split(",");
		for (int i = 0; i < clinumber.length; i++) {
			Logger.sysLog(LogValues.info, this.getClass().getName(),
					service.getId() + " :service_id , mxGrapgId : "
							+ service.getMxgraph().getId());
			ObdCli clinum = new ObdCli(service, clinumber[i]);
			Logger.sysLog(LogValues.info, this.getClass().getName(),
					service.getId() + "service_id");
			Logger.sysLog(LogValues.info, this.getClass().getName(),
					clinumber[i] + "cli");
			schedulerBo.addCli(clinum);
		}
		return "welcome";
	}

	@RequestMapping(value = "/geteditjobrequest.htm")
	public String geteditjob(@RequestParam("jobname") String jobname,
			ModelMap model) {
		Logger.sysLog(LogValues.info, this.getClass().getName(), "anjali");
		Logger.sysLog(LogValues.info, this.getClass().getName(),
				"in pankaj's controller: " + jobname);
		Editjob ejob = new Editjob();
		model.addAttribute("job", ejob);
		Service service = schedulerBo.getService(jobname);
		Date sdate = service.getStartDate();
		Date edate = service.getEndDate();
		SimpleDateFormat dateformatter = new SimpleDateFormat("MM/dd/yyy");
		String startdate = dateformatter.format(sdate);
		String enddate = dateformatter.format(edate);
		Logger.sysLog(LogValues.info, this.getClass().getName(), "startdate:"
				+ startdate);
		List<ObdCli> cli = schedulerBo.getCli(service);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < cli.size(); i++) {
			sb.append(cli.get(i).getCli() + ",");
		}
		Logger.sysLog(LogValues.info, this.getClass().getName(), "cli numbers"
				+ sb.toString());
		model.addAttribute("jobdetail", service);
		model.addAttribute("cli",
				sb.toString().substring(0, sb.toString().lastIndexOf(",")));
		model.addAttribute("sdate", startdate);
		model.addAttribute("edate", enddate);
		return "geteditjob";
	}

	@RequestMapping(value = "/deletejob.htm")
	public void deletejob(@RequestParam("jobname") String jobname) {
		Service service = schedulerBo.getService(jobname);
		service.setStatus("deleted");
		schedulerBo.addJob(service);
	}

	@RequestMapping(value = "/viewjob.htm", method = RequestMethod.GET)
	public void viewjob(Principal principal, ModelMap model) {
		String username = principal.getName();
		Users currentUser = userService.getUser(username);
		Logger.sysLog(LogValues.debug, this.getClass().getName(), username);
		List mxgraphVersions = schedulerBo.getmxgraph(currentUser.getId());
		Iterator iter = mxgraphVersions.iterator();
		List<Mxgraph> mxGraphList = new ArrayList<Mxgraph>();
		while (iter.hasNext()) {
			MxgraphVersion mxGraphVersion = (MxgraphVersion) iter.next();
			if (mxGraphVersion.getMxgraph().getCallType()
					.equalsIgnoreCase("obd"))
				mxGraphList.add(mxGraphVersion.getMxgraph());
		}
		if (mxGraphList.size() > 0) {
			Logger.sysLog(LogValues.debug, this.getClass().getName(),
					mxGraphList.size());
			Logger.sysLog(LogValues.debug, this.getClass().getName(),
					mxGraphList.get(0).getId());
		}
		List service = new ArrayList();
		for (int i = 0; i < mxGraphList.size(); i++) {
			List ser = schedulerBo.getServicelist(mxGraphList.get(i).getId());
			if (ser.size() < 1)
				continue;
			if (ser.size() < 2) {
				service.add((Service) ser.get(0));
			} else {
				Iterator iterator = ser.iterator();
				while (iterator.hasNext()) {
					service.add(iterator.next());
				}
			}
			// Mxgraph mxgraph= schedulerBo.mxgraph(ser.getMxgraph().getId());

			// Logger.sysLog(LogValues.info,this.getClass().getName(),
			// ser.getJobname());

		}
		Logger.sysLog(LogValues.debug, this.getClass().getName(), service.size());
		model.addAttribute("service", service);

		if (mxGraphList.size() > 0)
			Logger.sysLog(LogValues.info, this.getClass().getName(),
					mxGraphList.get(0).getServiceName());

	}

	@RequestMapping(value = "/blackouthours.htm")
	public String blackout(ModelMap model) {
		Logger.sysLog(LogValues.info, this.getClass().getName(),
				"----I am in Blackout-------");
		ObdBlackoutHours obdBlackoutHours = schedulerBo.getobh(1);
		Blackout black;
		if(obdBlackoutHours!=null){
			black = new Blackout(obdBlackoutHours.getBlackoutStart().toString(),obdBlackoutHours.getBlackoutEnd().toString());
		}
		else
		{
			black = new Blackout();
		}
		model.addAttribute("black", black);
		return "blackouthours";
	}

	@RequestMapping(value = "/blackouthours.htm", method = RequestMethod.POST)
	public String addblackout(@ModelAttribute("black") Blackout bout,
			BindingResult result, SessionStatus status) {
		SimpleDateFormat timeformatter = new SimpleDateFormat("HH:mm:ss");
		Date StartTime = null;
		Date EndTime = null;
		try {
			StartTime = timeformatter.parse(bout.getStartBtime());
			EndTime = timeformatter.parse(bout.getEndBtime());
		} catch (ParseException e) {
			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(),
					coreException.GetStack(e));
		}
		int id = 1;
		ObdBlackoutHours blackout = new ObdBlackoutHours(id, StartTime, EndTime);
		schedulerBo.addobh(blackout);
		return "blackoutadded";
	}

}