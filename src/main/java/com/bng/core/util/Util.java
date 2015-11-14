package com.bng.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;


public class Util {

	public static void sysLog(int severity, String applicationName, String message)
    {
		com.bng.core.util.Logger.sysLog(severity, applicationName, message);
    }
	

	public static byte[] object2byte(Object obj) throws Exception{
		ByteArrayOutputStream byteObject = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteObject);
		objectOutputStream.writeObject(obj);
		objectOutputStream.flush();
		objectOutputStream.close();
		byteObject.close();
		return byteObject.toByteArray();
	}

	public static Object byte2object(byte[] obj) throws Exception{
		ByteArrayInputStream bais = new ByteArrayInputStream(obj); 
		ObjectInputStream object = new ObjectInputStream(bais);
		bais.close();
		object.close();
		return object.readObject();
	}

	/**
	 * Class used for fetching result from the resultset and add it into vector
	 * @param iMaxRows
	 * @param vRecords
	 * @return
	 * @throws SQLException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int fetchResultSettoVector( int iMaxRows, Vector vRecords, ResultSet rs ,Logger logger) throws SQLException
	{
		boolean bRemoveTrailingBlanks = false;
		int result = 0;
		String szRow[];
		int columns;
		int iFetchedRows = 0;
		String szTemp;
		int iCount;
		boolean bNextRowAvailable = false;
		logger.debug("_fetchSQL");
		
		if (vRecords == null) {
			logger.error("	Exception : Vector Null" );
			return -2;
		}
		try	{
			if (rs == null)	{
				return(0);
			}
			ResultSetMetaData RSMData = rs.getMetaData();
			if (RSMData == null) {
				logger.error("_	Exception : ResultSetMetaData Null" );

				return( -202 );
			}
			columns = RSMData.getColumnCount();
			if (bRemoveTrailingBlanks) { // to improve performance, this test is done only once
				while((iMaxRows > iFetchedRows || iMaxRows == 0) && (bNextRowAvailable = rs.next())) { 	// cycle through the szRows, and write results in vector
					szRow = new String[columns];
					for (int i = 1; i <= columns; i++) {
						szTemp = rs.getString(i);
						// now remove all trailing blanks, if not NULL
						if (szTemp != null) {
							for (iCount = szTemp.length() - 1; iCount >= 0 && szTemp.charAt(iCount) == ' '; iCount--) {} // look for first non-blank character
							szRow[i-1] = new String(szTemp.substring(0, iCount + 1));
						} else {
							szRow[i-1] = null;
						} // if (szTemp != null) ...
					} // for
					vRecords.addElement(szRow);
					iFetchedRows++;
				} // while
			}
			else
			{
				while((iMaxRows > iFetchedRows || iMaxRows == 0) && (bNextRowAvailable = rs.next())) { 	// cycle through the szRows, and write results in vector
					szRow = new String[columns];
					for (int i = 1; i <= columns; i++) {
						szRow[i-1] = rs.getString(i);
					}
					vRecords.addElement(szRow);
					iFetchedRows++;
				} // while
			} // if (bRemoveTrailingBlanks)...
			result = iFetchedRows;
			if (!bNextRowAvailable)
			{ // if no more rows are available, close and clean the result set resources
				rs.close();
			} 
		}
		catch (SQLException ex) {
			// Got some type of exception.  Dump it.
			logger.error( ex);
			logger.error("_General Exception on Fetch");
			if (ex.getErrorCode() == 0)
				return result=-222;
			return ex.getErrorCode();
		}
		return (result);
	} // fetchSQL()


	public static int fetchListtoVector( int iMaxRows, Vector vRecords, List<Map<String, String>> mResult,String szQuery ,Logger logger) throws SQLException
	{
		String data = szQuery.substring(0,szQuery.indexOf(" from "));
		data = data.substring((data.indexOf("select ")+("select ").length()));
		String [] saData = data.split(",");
		int result = 0;
		logger.debug("_fetchSQLListToVector");

		if (vRecords == null) {
			logger.error("	Exception : Vector Null" );
			return -2;
		}
		try	{
			for(int k = 0;k<mResult.size();k++){
				Map<String, String> m = mResult.get(k);
				String szRows[] = new String[saData.length];
				for(int j = 0;j<saData.length;j++){
					szRows[j] = m.get(saData[j].trim());
				}
				vRecords.addElement(szRows);
			}
		}
		catch (Exception ex) {
			logger.error(ex);
			result = -1;;
		}
		return (result);
	}

	public static String hitURL(String surl, Logger logger){

		logger.debug("url to hit "+surl);
		String szCnt = "";
		HttpMethod method = null;
		try {
			HttpClient client = new HttpClient();
			client.setConnectionTimeout(10000);
			method = new GetMethod(surl);
			int status = client.executeMethod(method);
			if (status > 0) {
				szCnt = method.getResponseBodyAsString();
			} else {
				szCnt = "Err";
			}
		} catch (Exception e) {
			logger.error(coreException.GetStack(e));

		} finally {
			method.releaseConnection();
		}
		logger.debug("url response "+szCnt);
		return szCnt;
	}

	final static String ENC_DEF = System.getProperty("file.encoding");
	public static String OPTIONS_SEPARATOR         = "&";
	public static Properties optionString2Hash( String szOptions, boolean bRemoveEmptyOptions ) throws Exception {
		if( szOptions == null )
			return null;

		StringTokenizer stOptions = new StringTokenizer( szOptions, OPTIONS_SEPARATOR );
		Properties htOptions = new Properties();
		String szKey, szValue, szToken;
		int iEqualPos;
		while( stOptions.hasMoreTokens() ) {
			szToken = stOptions.nextToken();
			iEqualPos = szToken.indexOf( (int)'=' );
			if( iEqualPos < 0 )
				continue;

			szValue = URLDecoder.decode( szToken.substring( iEqualPos + 1 ), ENC_DEF );

			if( bRemoveEmptyOptions && ( szValue.length() == 0 ) )
				continue;

			szKey = URLDecoder.decode( szToken.substring( 0, iEqualPos ), ENC_DEF );

			htOptions.put( szKey, szValue );
		}
		return htOptions;
	}

	/**
	 * Convert a Hashtable Options in a String <br>
	 * bRemoveEmptyOptions If true removes the empty options from the result.
	 * 
	 * @param htOptions
	 * @param bRemoveEmptyOptions
	 * @return the String
	 * @throws Exception
	 */
	public static String optionHash2String( Hashtable htOptions, boolean bRemoveEmptyOptions ) throws Exception {
		if( htOptions == null )
			return null;
		Enumeration eKeys = htOptions.keys();
		String szKey, szValue;
		String szOptions = "";
		while( eKeys.hasMoreElements() ) {
			szKey = (String)eKeys.nextElement();
			if( szKey.length() == 0 )
				continue;

			szValue = URLEncoder.encode( (String)htOptions.get( szKey ), ENC_DEF );

			if( bRemoveEmptyOptions && ( szValue.length() == 0 ) )
				continue;

			if( szOptions.length() == 0 )
				szOptions += URLEncoder.encode( szKey, ENC_DEF ) + "=" + szValue;
			else
				szOptions += OPTIONS_SEPARATOR + URLEncoder.encode( szKey, ENC_DEF ) + "=" + szValue;
		}
		return szOptions;
	}
}
