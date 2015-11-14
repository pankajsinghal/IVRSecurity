package com.bng.excel;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.sql.Time;


//import javax.enterprise.context.spi.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.bng.parse.CdrUnmarshaling;



@Controller
public class EcxelGenerator {
	
	@RequestMapping("/cdr.htm")
	public String cdr(){
		return "cdr";
	}
	
	@RequestMapping("/confDownloadExcel")
	public String confDownloadExcel(Model model,@RequestParam("despath") String despath,@RequestParam("filename") String filename){
		model.addAttribute("despath",despath);
        model.addAttribute("filename",filename);
		return "DownloadExcel";
	}
	
	@RequestMapping("/downloadcdr.htm")
	public String create(Model model,@RequestParam("date") String date,@RequestParam("time") String time,@RequestParam("interval") int interval){
        try{    
            String srcpath=CdrConfig.cdrPath;
            String despath=CdrConfig.cdrPath;
            String datetime = date+" "+time;
            System.out.print(datetime);
            Calendar now = Calendar.getInstance();
            int year = now.get(Calendar.YEAR);
            int month = now.get(Calendar.MONTH)+1; // Note: zero based!
            int day = now.get(Calendar.DAY_OF_MONTH);
            int hour = now.get(Calendar.HOUR_OF_DAY);
            int minute = now.get(Calendar.MINUTE);
            int second=now.get(Calendar.SECOND);

           String filename="Report_"+year+month+day+"_"+hour+minute+second+".xls";
           String [] arrdate=date.split("-");
           String [] arrtime=time.split(":");
           
           String stryear=arrdate[2];
           String strmonth=arrdate[1];
           String strday=arrdate[0];
           String strhour=arrtime[0];
           
           srcpath+="\\"+stryear+"\\"+strmonth+"\\"+strday+"\\"+strhour;
          
           int timediff=5;
           if(interval!=0)
           {
        	   timediff=interval;
           }
           createExcel(srcpath,despath+"\\"+filename,datetime,timediff);
           model.addAttribute("despath",despath+"\\"+filename);
           model.addAttribute("filename",filename);
           File f = new File(despath+"\\"+filename); 
	   		if(f.exists())
	   		{
	   		 model.addAttribute("fcreated","true");
	   		}
	   		else
	   		{
	   		 model.addAttribute("fcreated","false");
	   		}
		}
        catch(Exception ex){}
        
        return "confdownload";
    }
	 
	public void createExcel(String srcpath,String despath,String datetime,int timediff)
	{
		try{
			//ExcelGenerator e= new ExcelGenerator();
//			            //     System.out.println(ssp);
//                                         System.out.println("hello ............................!"+new InitialContext().doLookup("ssp"));
			
			File [] files=listFilesForFolder(srcpath,datetime,timediff);
			if(files!=null)
			{
				int count =files.length;
				String cdrRec[][]=new String[count+1][];
				cdrRec[0]="Aparty,Bparty,Callid,Callstatus,Calltype,Calltype,Cesystemip,Cic,Circle,Country,Enddatetime,Hardware,Operator,Pickupdatetime,Protocol,Releasereason,Servicename,Shortcode,Startdatetime,Tesystemip,TimeZone".split(",");
				int i=1;
				CdrUnmarshaling cdrunmarshal=new CdrUnmarshaling();
				for (final File fileEntry : files)
				{
					 String content = FileUtils.readFileToString(fileEntry);
					 cdrRec[i++]= cdrunmarshal.getRecord(content);
				}
				exportData(despath,"report",cdrRec);
				//System.out.println("done");
			}
			else
			{
				//System.out.println("there is no file within given time.");
			}
		}
		catch(Exception ex){}
	}
	
    public  void exportData(String fileName, String tabName, String[][] data) throws FileNotFoundException, IOException
    {FileOutputStream fileOut = new FileOutputStream(fileName);
        try
        {//Create new workbook and tab
        HSSFWorkbook wb = new HSSFWorkbook();
        
        HSSFSheet sheet = wb.createSheet(tabName);  //createSheet(tabName);
 
        //Create 2D Cell Array
        HSSFRow[] row = new HSSFRow[data.length];
        HSSFCell[][] cell = new HSSFCell[row.length][];
 
        //Define and Assign Cell Data from Given
        for(int i = 0; i < row.length; i ++)
        {
            row[i] = sheet.createRow(i);
            cell[i] = new HSSFCell[data[i].length];
 
            for(int j = 0; j < cell[i].length; j ++)
            {
                cell[i][j] = row[i].createCell(j);
                cell[i][j].setCellValue(data[i][j]);
            }
 
        }
 
        //Export Data
        wb.write(fileOut);
        fileOut.close();
        }
        catch(Exception ex)
        {ex.printStackTrace();
               fileOut.close();
        }
    }
	
	
	
	public File[] listFilesForFolder(String path,String datetime, int timeDiffInMin) throws ParseException {
		

                File folder=new File(path);
                if(folder.exists())
                { 
                    int count=0;
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    Date d = formatter.parse(datetime);
                    for (final File fileEntry : folder.listFiles()) {
                    if (fileEntry.isFile() && ( (new Date(fileEntry.lastModified()).getTime()/60000)-(int)((d.getTime()/60000)))<=timeDiffInMin && fileEntry.getName().endsWith(".xml")) {
                            count++;
                        //System.out.println(fileEntry.getName()+" "+new Date(fileEntry.lastModified()).toString());   
                    }

                    }
                    if(count>0)
                    {

                            File[] fileList=new File[count];
                            count=0;
                            for (final File fileEntry : folder.listFiles()) 
                            {
                            	if (fileEntry.isFile() && ( (new Date(fileEntry.lastModified()).getTime()/60000)-(int)((d.getTime()/60000)))<=timeDiffInMin && fileEntry.getName().endsWith(".xml"))  {
                                        fileList[count]=fileEntry;
                                        count++;
                                    //System.out.println(fileEntry.getName()+" "+new Date(fileEntry.lastModified()).toString());   
                                }

                            }
                            return fileList;
                    }
                    else
                    {
                        return null;
                    }
                }
                else
                {
                    return null;
                }
	}

	
	

}
