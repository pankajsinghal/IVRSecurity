/**
 * 
 */
package com.bng.core.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.bng.core.bean.FileMeta;
import com.bng.core.da.FileUploadDao;
import com.bng.core.da.UserDao;
import com.bng.core.entity.Content;
import com.bng.core.entity.ContentCategory;
import com.bng.core.entity.ContentLabel;
import com.bng.core.entity.ContentMapper;
import com.bng.core.entity.ContentMeta;
import com.bng.core.entity.ContentMetaExtra;
import com.bng.core.entity.ContentPlaylist;
import com.bng.core.entity.ContentPlaylistMapper;
import com.bng.core.entity.ContentProvider;
import com.bng.core.entity.Users;
import com.bng.core.util.LogValues;
import com.bng.core.util.Logger;
import com.bng.core.util.coreException;

/**
 * @author Harish Shan
 *
 */
public class FileUploadImp implements FileUpload 
{

	private FileUploadDao fileUploadDao;
	private String outputFolder;
	public static String filestoreFolder;
	private UserDao userDao;


	public FileUploadDao getFileUploadDao() 
	{
		return fileUploadDao;
	}
	public void setFileUploadDao(FileUploadDao fileUploadDao) 
	{
		this.fileUploadDao = fileUploadDao;
	}
	public String getOutputFolder() 
	{
		return outputFolder;
	}
	public void setOutputFolder(String outputFolder) 
	{
		this.outputFolder = outputFolder;
	}
	public String getFilestoreFolder() 
	{
		return filestoreFolder;
	}
	public void setFilestoreFolder(String filestoreFolder) 
	{
		this.filestoreFolder = filestoreFolder;
	}
	public UserDao getUserDao() 
	{
		return userDao;
	}
	public void setUserDao(UserDao userDao) 
	{
		this.userDao = userDao;
	}
	public void saveContent(FileMeta filemeta,Users currentUser) 
	{	
		Date expireDate=null;
		try{expireDate=new SimpleDateFormat("dd-MM-yyyy").parse(filemeta.getExpireDate());}
		catch(ParseException e){}
		Content content=new Content(filemeta.getPath(),filemeta.getType(),new Date(),expireDate);
		fileUploadDao.saveContent(content);

		ContentMapper contentMapper=new ContentMapper(content,-1,filemeta.getDownloadUrl(),filemeta.getCrbtUrl(),filemeta.getBillingUrl());
		fileUploadDao.saveContentMapper(contentMapper);

		ContentLabel contentLabel=new ContentLabel(filemeta.getLabelName(),Boolean.TRUE);		
		contentLabel=fileUploadDao.saveContentLabel(contentLabel);

		ContentProvider contentProvider=new ContentProvider(filemeta.getContentProvider(),Boolean.TRUE);
		contentProvider=fileUploadDao.saveContentProvider(contentProvider);		

		ContentCategory contentCategory=new ContentCategory(filemeta.getCategoryName());
		contentCategory=fileUploadDao.saveContentCategory(contentCategory);

		ContentMeta contentMeta=new ContentMeta(content,contentProvider,contentLabel,contentCategory,filemeta.getLanguage(),filemeta.getSongname(),filemeta.getSongcode());
		fileUploadDao.saveContentMeta(contentMeta);

		ContentMetaExtra contentMetaExtra=new ContentMetaExtra(contentMeta,filemeta.getOptions());
		fileUploadDao.saveContentMetaExtra(contentMetaExtra);	
		
		if(filemeta.getPlaylist()!=""&&filemeta.getPlaylist()!=null)
		{
			try
			{
				Logger.sysLog(LogValues.info, this.getClass().getName(),"Playlist is not null:"+filemeta.getPlaylist());
				ContentPlaylist contentPlaylist=new ContentPlaylist(filemeta.getPlaylist());
				contentPlaylist=fileUploadDao.saveContentPlaylist(contentPlaylist);
				
				ContentPlaylistMapper contentPlaylistMapper=new ContentPlaylistMapper(content, contentPlaylist);
				fileUploadDao.saveContentPlaylistMapper(contentPlaylistMapper);
			}
			catch(Exception e)
			{
				Logger.sysLog(LogValues.error, this.getClass().getName(),"Exception Occured during Extract"+e.getMessage());
			}
		}
		
		
	}
	public Users getUser(String username) 
	{
		Users user=userDao.getUser(username);
		return user;
	}


	public void uploadFiles(String filename) 
	{
		String outputFolder = "";
		try
		{
			try
			{
				byte[] buffer = new byte[1024];
				File zipFile=new File(filename);
				ZipInputStream zis =new ZipInputStream(new FileInputStream(zipFile));
				ZipEntry ze = zis.getNextEntry();
				HashMap<String,File> zipFiles=new HashMap<String,File>();
				outputFolder=this.getOutputFolder()+File.separator+zipFile.getName().substring(0,zipFile.getName().lastIndexOf(".zip"));
				while(ze!=null)
				{
					File outputfolder = new File(outputFolder);
					if(!outputfolder.exists())
						outputfolder.mkdirs();
					if(ze.isDirectory())
					{
						File newFile=new File(outputFolder + File.separator + ze.getName());
						newFile.mkdir();
					}
					else
					{
						File newFile = new File(outputFolder + File.separator + ze.getName());
						Logger.sysLog(LogValues.info, this.getClass().getName(),"newFile "+newFile.getPath()+" Exists "+newFile.exists());
						new File(newFile.getParent()).mkdirs();
						FileOutputStream fos = new FileOutputStream(newFile);             
						int len;
						while((len = zis.read(buffer)) > 0) 
						{
							fos.write(buffer, 0, len);
						}
						fos.close();
					}
					ze = zis.getNextEntry();					
				}	
				zis.closeEntry();
				zis.close();
				zipFile.delete();
				Logger.sysLog(LogValues.info, this.getClass().getName(),"Unzip completed and deleted zip file from ftppath");
			}
			catch(Exception e)
			{	
				Logger.sysLog(LogValues.error, this.getClass().getName(),"Exception Occured during Extract"+e.getMessage());
			}

			ArrayList<FileMeta> fileMeta=new ArrayList<FileMeta>();
			File metaFile=new File(outputFolder+File.separator+"meta.xlsx");
			if(metaFile.exists())
			{
				Workbook wb = null;
				NPOIFSFileSystem npoifsFileSystem = null;
				OPCPackage opcPackage = null;
				try
				{
					try
					{
						npoifsFileSystem = new NPOIFSFileSystem(metaFile);
						wb = WorkbookFactory.create(npoifsFileSystem);
					}
					catch(OfficeXmlFileException e)
					{
						Logger.sysLog(LogValues.error, this.getClass().getName(),"Exception occured during "+coreException.GetStack(e));
						opcPackage = OPCPackage.open(metaFile);
						wb = WorkbookFactory.create(opcPackage);
					}
					//wb = WorkbookFactory.create(metaFile);
					Sheet sheet = wb.getSheetAt(0);
					Iterator<Row> rows = sheet.rowIterator();
					if(rows.hasNext())
					{
						rows.next();
					}
					while(rows.hasNext())
					{
						Row row = rows.next();
						if(row.getCell(0)!=null&&row.getCell(15)!=null)
						{
							FileMeta fm=new FileMeta(row.getCell(0).getStringCellValue(),row.getCell(1).getStringCellValue(),
									row.getCell(2).getStringCellValue(),row.getCell(3).getStringCellValue(),row.getCell(4).getStringCellValue(),
									row.getCell(5).getStringCellValue(),row.getCell(6).getStringCellValue(),row.getCell(7).getStringCellValue(),
									row.getCell(8).getStringCellValue(),row.getCell(9).getStringCellValue(),row.getCell(10).getStringCellValue(),
									row.getCell(11).getStringCellValue(),row.getCell(12).getStringCellValue(),row.getCell(13).getStringCellValue(),
									row.getCell(14).getStringCellValue(),row.getCell(15).getStringCellValue());						
							fileMeta.add(fm);					
						}
						else
						{
							FileMeta fm=new FileMeta(row.getCell(0).getStringCellValue(),row.getCell(1).getStringCellValue(),
									row.getCell(2).getStringCellValue(),row.getCell(3).getStringCellValue(),row.getCell(4).getStringCellValue(),
									row.getCell(5).getStringCellValue(),row.getCell(6).getStringCellValue(),row.getCell(7).getStringCellValue(),
									row.getCell(8).getStringCellValue(),row.getCell(9).getStringCellValue(),row.getCell(10).getStringCellValue(),
									row.getCell(11).getStringCellValue(),row.getCell(12).getStringCellValue(),row.getCell(13).getStringCellValue(),
									row.getCell(14).getStringCellValue());						
							fileMeta.add(fm);
						}
					}
				}
				catch(Exception e)
				{
					Logger.sysLog(LogValues.error, this.getClass().getName(),"Exception occured during Excel file read "+coreException.GetStack(e));
				}
				finally
				{
					try
					{
						if(npoifsFileSystem!=null){npoifsFileSystem.close();}
						if(opcPackage!=null){opcPackage.close();}
					}
					catch(Exception e)
					{
						Logger.sysLog(LogValues.error, this.getClass().getName(),"Exception occured during Excel file read "+coreException.GetStack(e));
					}
				}
			}
			Logger.sysLog(LogValues.debug, this.getClass().getName(),"FileMeta "+fileMeta.toString());
			for(int i=0;i<fileMeta.size();i++)
			{
				try
				{
					FileMeta filemeta=fileMeta.get(i);
					File sourceFile=new File(outputFolder+File.separator+filemeta.getPath());	
					if(sourceFile.exists())
					{													
						File destinationFile=new File(filestoreFolder+File.separator+filemeta.getPath());
						try
						{
							if(destinationFile.exists())
							{
								Logger.sysLog(LogValues.info, this.getClass().getName(),"destination file exists "+destinationFile.getName() +" NO change in DB");
								continue;								
							}
							else
							{
								for(int k=0;k<10;k++)
								{
									try
									{
										FileUtils.copyFile(sourceFile, destinationFile, true);
										Logger.sysLog(LogValues.debug, this.getClass().getName(),"file copy successfull in times "+k);
										filemeta.setPath(destinationFile.getPath());
										saveContent(filemeta,new Users());
										Thread.sleep(1000);
										break;
									}
									catch(Exception e)
									{
										Logger.sysLog(LogValues.error, this.getClass().getName(),"file copy error retrying times "+k);
									}
								}
							}
						}
						catch(Exception e)
						{
							Logger.sysLog(LogValues.error, this.getClass().getName(),"Exception on File copy"+coreException.GetStack(e));
						}									

					}						
				}
				catch(Exception e)
				{
					Logger.sysLog(LogValues.error, this.getClass().getName(),"Exception on File copy "+coreException.GetStack(e));
				}
			}			
			Logger.sysLog(LogValues.info, this.getClass().getName(),"Completed upload in filestore and database");			
		}
		catch(Exception e)
		{
			Logger.sysLog(LogValues.error, this.getClass().getName(),"Exception occured during file upload "+coreException.GetStack(e));
		}
	}	
}