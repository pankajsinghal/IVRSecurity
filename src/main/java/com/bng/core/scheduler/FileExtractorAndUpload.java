package com.bng.core.scheduler;

import java.io.File;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bng.core.service.FileUpload;
import com.bng.core.util.LogValues;
import com.bng.core.util.Logger;

public class FileExtractorAndUpload implements Job{

	private String filetype;
	private String filepath;
	private FileUpload fileUpload;
	
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public FileUpload getFileUpload() {
		return fileUpload;
	}

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub

	}

	public void setFileUpload(FileUpload fileUpload)
	{
		this.fileUpload=fileUpload;
	}
	
	public void upload() 
	{	
		Logger.sysLog(LogValues.info, this.getClass().getName(), "Scheduler Called");
		File ftpPath=new File(this.getFilepath());	
		if(!ftpPath.exists())
		{
			ftpPath.mkdir();
		}
		File[] files=ftpPath.listFiles();
		if(files==null) return;
		for(File file : files)
		{
			if(file.getName().endsWith(this.getFiletype()))
			{
				Logger.sysLog(LogValues.info, this.getClass().getName(), "Scheduler Called Processing");
				fileUpload.uploadFiles(file.getPath());
			}
		}		
		/*	File [] file = FileUtils.convertFileCollectionToFileArray(FileUtils.listFiles(this.getFile(), this.getFileFilter(), false));
		for (File file2 : file) {
			if(file2.getName().endsWith(".zip")){
				
			}
		}*/
	}

}
