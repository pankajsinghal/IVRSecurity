package cn.bluejoe.elfinder.controller.executors;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import cn.bluejoe.elfinder.controller.executor.AbstractJsonCommandExecutor;
import cn.bluejoe.elfinder.controller.executor.CommandExecutor;
import cn.bluejoe.elfinder.controller.executor.FsItemEx;
import cn.bluejoe.elfinder.impl.DefaultFsService;
import cn.bluejoe.elfinder.service.FsService;

import com.bng.core.bean.FileMeta;
import com.bng.core.bean.UploadConfig;
import com.bng.core.entity.Users;
import com.bng.core.util.LogValues;
import com.bng.core.util.Logger;

public class UploadCommandExecutor extends AbstractJsonCommandExecutor implements CommandExecutor
{
	//Changed for linux and windows,If not working revert to last update for only windows.... having copy of last working code.-Harish
	@Override
	public void execute(FsService fsService, HttpServletRequest request, ServletContext servletContext, JSONObject json)throws Exception
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();
	    Users currentUser=UploadConfig.fileup.getUser(username);
		List<FileItemStream> listFiles = (List<FileItemStream>) request.getAttribute(FileItemStream.class.getName());
		List<FsItemEx> added = new ArrayList<FsItemEx>();
		String target = request.getParameter("target");		
		for (FileItemStream fis : listFiles)
		{
			String fileName = fis.getName();			
			if(fileName.endsWith(".zip"))
			{
				byte[] buf = new byte[26214400];				
				ZipInputStream zis =new ZipInputStream(fis.openStream());
				ZipEntry ze = zis.getNextEntry();
				HashMap<String,File> zipFiles=new HashMap<String,File>();
				while(ze!=null)
				{
					if (!ze.isDirectory())
					{
						int n=0;	                
						File tempFile=File.createTempFile(ze.getName().replace("/","").replace("\\",""),"");
	                	new File(tempFile.getParent()).mkdirs();
	                	FileOutputStream fileoutputstream = new FileOutputStream(tempFile);
	                	while ((n = zis.read(buf, 0, 1024)) > -1)
	                    	fileoutputstream.write(buf, 0, n);	                	
	                	fileoutputstream.close(); 
	                	zis.closeEntry();
	                	zipFiles.put(ze.getName(),tempFile);
					}
	                ze = zis.getNextEntry();					
				}					
				Logger.sysLog(LogValues.debug, this.getClass().getName(),"Does zip contain meta.xlsx :"+zipFiles.containsKey("meta.xlsx"));
				ArrayList<FileMeta> fileMeta=new ArrayList<FileMeta>();
				if(zipFiles.containsKey("meta.xlsx"))
				{
					Logger.sysLog(LogValues.debug, this.getClass().getName(), "meta.xlsx file "+zipFiles.get("meta.xlsx").toString());
					Workbook wb = WorkbookFactory.create((File)zipFiles.get("meta.xlsx"));	
					Logger.sysLog(LogValues.debug, this.getClass().getName(), "No of sheets :"+wb.getNumberOfSheets());
					Sheet sheet = wb.getSheetAt(0);
					Logger.sysLog(LogValues.debug, this.getClass().getName(),"Last row no :" +sheet.getLastRowNum());
					Iterator<Row> rows = sheet.rowIterator();
					if(rows.hasNext())
					{
						Logger.sysLog(LogValues.debug, this.getClass().getName(),"First row : "+rows.next().toString());
					}
					while(rows.hasNext())
					{
						Row row = rows.next();		
						if(row.getCell(0)!=null)
						{
							FileMeta fm=new FileMeta(row.getCell(0).getStringCellValue(),row.getCell(1).getStringCellValue(),
								row.getCell(2).getStringCellValue(),row.getCell(3).getStringCellValue(),row.getCell(4).getStringCellValue(),
								row.getCell(5).getStringCellValue(),row.getCell(6).getStringCellValue(),row.getCell(7).getStringCellValue(),
								row.getCell(8).getStringCellValue(),row.getCell(9).getStringCellValue(),row.getCell(10).getStringCellValue(),
								row.getCell(11).getStringCellValue(),row.getCell(12).getStringCellValue(),row.getCell(13).getStringCellValue(),
								row.getCell(14).getStringCellValue());						
							fileMeta.add(fm);
							Logger.sysLog(LogValues.debug, this.getClass().getName(),"Path:"+fm.getPath()+" ContentProvider:"+fm.getContentProvider()+" ExpireDate:"+fm.getExpireDate()+" CategoryName:"+fm.getCategoryName()+" LabelName:"+fm.getLabelName()+" Language:"+fm.getLanguage()+" SongCode:"+fm.getSongcode()+" SongName:"+fm.getSongname()+" CountryName:"+fm.getCountryName()+" OperatorName:"+fm.getOperatorName()+" DownloadUrl:"+fm.getDownloadUrl()+" CrbtUrl"+fm.getCrbtUrl()+" BillingUrl:"+fm.getBillingUrl()+" Options:"+fm.getOptions());
						}
					}
				}				
				Logger.sysLog(LogValues.debug, this.getClass().getName(),"FileMeta "+fileMeta.toString());					
				for(int i=0;i<fileMeta.size();i++)
				{
					FileMeta filemeta=fileMeta.get(i);
					if(zipFiles.containsKey(filemeta.getPath()))
					{												
						FsItemEx fsi = super.findItem(fsService, target);
						File t=new File(filemeta.getPath());
					
						FsItemEx dir = new FsItemEx(fsi, t.getParent());
						dir.createFolder();					
						FsItemEx newFile = new FsItemEx(dir,t.getName());
						
						cn.bluejoe.elfinder.localfs.LocalFsVolume localfsvolume=(cn.bluejoe.elfinder.localfs.LocalFsVolume)((DefaultFsService) fsService).getVolumes()[0];
						String fullFileName=localfsvolume.getRootDir().toString();
						if(SystemUtils.IS_OS_WINDOWS)
							fullFileName=fullFileName+newFile.getPath().replace("/", "\\");
						if(SystemUtils.IS_OS_LINUX)
							fullFileName=fullFileName+newFile.getPath();
						Logger.sysLog(LogValues.debug, this.getClass().getName(),"ok"+fullFileName);
						if(!new File(fullFileName).exists())
						{
							newFile.createFile();
							File temp=zipFiles.get(filemeta.getPath());					
							FileInputStream is=new FileInputStream(temp.getPath());	
							OutputStream os = newFile.openOutputStream();
							IOUtils.copy(is, os);
							os.close();
							is.close();									
							filemeta.setPath(fullFileName);
							UploadConfig.fileup.saveContent(filemeta,currentUser);
							added.add(newFile);							
							temp.delete();
						}					
					}				
				}				
				Logger.sysLog(LogValues.debug, this.getClass().getName(), "Added "+added.toString());
			}
			else
			{
				Logger.sysLog(LogValues.info, this.getClass().getName(),"Single File Upload /Replace Start");
				try
				{
					String singleupload=request.getParameter("singleupload");
					if(singleupload.equals("replace"))
					{
						Logger.sysLog(LogValues.info, this.getClass().getName(),"Replace File Start");
						FsItemEx dir = super.findItem(fsService, target);
						dir.createFolder();	
						
						FsItemEx newFile = new FsItemEx(dir, fileName);
						if(newFile.exists())
						{
							newFile.createFile();							
							InputStream is = fis.openStream();
							OutputStream os = newFile.openOutputStream();
							IOUtils.copy(is, os);
							os.close();
							is.close();										
							added.add(newFile);
							Logger.sysLog(LogValues.debug, this.getClass().getName(),"File Replace complete "+added.toString());
						}
					}
					else
					{
						Logger.sysLog(LogValues.info, this.getClass().getName(),"Single File Upload Start");
						String contentProvider=request.getParameter("contentProvider");
						String expireDate=request.getParameter("expireDate");
						String categoryName=request.getParameter("categoryName");
						String labelName=request.getParameter("labelName");
						String language=request.getParameter("language");
						String songCode=request.getParameter("songCode");
						String songName=request.getParameter("songName");
						String countryName=request.getParameter("countryName");
						String operatorName=request.getParameter("operatorName");
						String downloadUrl=request.getParameter("downloadUrl");
						String crbtUrl=request.getParameter("crbtUrl");
						String billingUrl=request.getParameter("billingUrl");
						String options=request.getParameter("options");
						Logger.sysLog(LogValues.info, this.getClass().getName(),"ContentProvider:"+contentProvider+" ExpireDate:"+expireDate+" CategoryName :"+categoryName+" LabelName:"+labelName+" Language:"+language+"SongCode:"+songCode+" SongName:"+songName+" CountryName:"+countryName+" OperatorName:"+operatorName+" DownloadUrl:"+downloadUrl+" CrbtUrl:"+crbtUrl+" BillingUrl:"+billingUrl+" Options:"+options);
						if((contentProvider!=null)&&(expireDate!=null)&&(categoryName!=null)&&(labelName!=null)&&(language!=null)&&(songCode!=null)&&(songName!=null)&&(countryName!=null)&(operatorName!=null)&&(downloadUrl!=null)&&(crbtUrl!=null)&&(billingUrl!=null)&&(options!=null))
						{
											
							FsItemEx dir = super.findItem(fsService, target);
							dir.createFolder();	
							
							FsItemEx newFile = new FsItemEx(dir, fileName);
							if(!newFile.exists())
							{
								newFile.createFile();							
								InputStream is = fis.openStream();
								OutputStream os = newFile.openOutputStream();
								IOUtils.copy(is, os);
								os.close();
								is.close();
								
								cn.bluejoe.elfinder.localfs.LocalFsVolume localfsvolume=(cn.bluejoe.elfinder.localfs.LocalFsVolume)((DefaultFsService) fsService).getVolumes()[0];
								
								String fullFileName=localfsvolume.getRootDir().toString();
								if(SystemUtils.IS_OS_WINDOWS)
									fullFileName=fullFileName+  newFile.getPath().replace("/", "\\");
								if(SystemUtils.IS_OS_LINUX)
									fullFileName=fullFileName+  newFile.getPath();
								FileMeta filemeta=new FileMeta(fullFileName,fullFileName.substring(fullFileName.lastIndexOf(".")),expireDate,contentProvider,categoryName,labelName,language,songCode,songName,countryName,operatorName,downloadUrl,crbtUrl,billingUrl,options);
								UploadConfig.fileup.saveContent(filemeta,currentUser);
								added.add(newFile);
								Logger.sysLog(LogValues.debug, this.getClass().getName(),"Single File Upload Added "+added.toString());
							}
						}						
					}
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
					Logger.sysLog(LogValues.error, this.getClass().getName(),"Exception on Single File Upload"+e.toString());
				}
			}			
		}		
		json.put("added", files2JsonArray(request, added));
	}
}