package com.bng.core.da;

import java.sql.BatchUpdateException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import javax.transaction.Transaction;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.bng.core.bean.JobBean;
import com.bng.core.entity.Content;
import com.bng.core.entity.ContentPlaylist;
import com.bng.core.entity.ContentPlaylistMapper;
import com.bng.core.entity.Mxgraph;
import com.bng.core.entity.MxgraphVersion;
import com.bng.core.entity.ObdBlackoutHours;
import com.bng.core.entity.ObdCli;
import com.bng.core.entity.ObdFailureReasons;
import com.bng.core.entity.Playlist;
import com.bng.core.entity.Service;
import com.bng.core.util.ConnectionPool;
import com.bng.core.util.LogValues;
import com.bng.core.util.Logger;
import com.bng.core.util.coreException;
import com.jolbox.bonecp.BoneCPDataSource;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

public class SchedulerDaoImp implements SchedulerDao {

	private SessionFactory sessionFactorySCP;

	//private ComboPooledDataSource dataSource;

	private ConnectionPool connectionPool;

	private BoneCPDataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	@Override
	public void setDataSource(BoneCPDataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public void setConnectionPool(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}

	public SessionFactory getSessionFactorySCP() {
		return sessionFactorySCP;
	}

	public void setSessionFactorySCP(SessionFactory sessionFactorySCP) {
		this.sessionFactorySCP = sessionFactorySCP;
	}

	@Transactional
	public List<Mxgraph> getService() {
		Session session = sessionFactorySCP.getCurrentSession();
		List<Mxgraph> list = session.createQuery("from Mxgraph ").list();
		return list;
	}

	@Override
	@Transactional
	public List getmxgraph(int id) {

		Session session = sessionFactorySCP.getCurrentSession();
		Criteria crit = session.createCriteria(MxgraphVersion.class);
		List mxversionlist = crit.add(Restrictions.eq("userMappingId", id))
				.list();
		Iterator iter = mxversionlist.iterator();
		while (iter.hasNext()) {
			MxgraphVersion mxGraphVersion = (MxgraphVersion) iter.next();
			mxGraphVersion.getMxgraph().getCallType();
			mxGraphVersion.getMxgraph().getServiceName();
			mxGraphVersion.getMxdata();
			mxGraphVersion.getUserMappingId();
		}		
		return mxversionlist;

	}

	@Override
	@Transactional
	public Mxgraph getmxgraph(String servicename, String shortcode) {
		Session session = sessionFactorySCP.getCurrentSession();
		Mxgraph mdetail = null;
		Criteria crit = session.createCriteria(Mxgraph.class);
		List mxlist = crit.add(Restrictions.eq("serviceName", servicename))
				.add(Restrictions.eq("shortcode", shortcode)).list();
		Iterator iter = mxlist.iterator();
		while (iter.hasNext()) {
			mdetail = (Mxgraph) iter.next();
			mdetail.getMxdata().getData();
		}
		return mdetail;
	}
	@Transactional
	public Mxgraph mxgraph(int id)
	{
		Session session = sessionFactorySCP.getCurrentSession();
		Mxgraph mdetail = null;
		Criteria crit = session.createCriteria(Mxgraph.class);
		List mxlist = crit.add(Restrictions.eq("id", id)).list();
		Iterator iter = mxlist.iterator();
		while(iter.hasNext())
		{
			mdetail = (Mxgraph)iter.next();
		}	
		return mdetail;
	}

	@Transactional
	public Service getService(String servicename, String shortcode){

		Service ser = new Service();

		Mxgraph m = getmxgraph(servicename, shortcode);
		int id = m.getId();
		Session session = sessionFactorySCP.getCurrentSession();
		Criteria crit = session.createCriteria(Service.class);
		List servicelist = crit.add(Restrictions.eq("mxgraph", m)).list();
		Iterator iter = servicelist.iterator();
		while (iter.hasNext()) {
			ser = (Service) iter.next();
			ser.getMxgraph().getMxdata().getData();
		}
		return ser;
	}
	@Override
	@Transactional
	public Service getService(String jobname) {

		Service ser = null;
		Session session = sessionFactorySCP.getCurrentSession();
		Criteria crit = session.createCriteria(Service.class);
		List servicelist = crit.add(Restrictions.eq("jobname", jobname)).list();
		Iterator iter = servicelist.iterator();
		while(iter.hasNext()){
		ser = (Service)iter.next();
		ser.getMxgraph().getServiceName();
		ser.getStatus();
		}
		return ser;
	}
	@Transactional
	public void addJob(Service service) {
		Session session = sessionFactorySCP.getCurrentSession();
		session.saveOrUpdate(service);
	}

	@Transactional
	public void addCli(ObdCli cli) {
		Session session = sessionFactorySCP.getCurrentSession();
		session.save(cli);
	}

	@Transactional
	public ObdBlackoutHours getobh(int i) {
		Session session = sessionFactorySCP.getCurrentSession();
		ObdBlackoutHours obdh = null;
		Criteria crit = session.createCriteria(ObdBlackoutHours.class);
		List servicelist = crit.add(Restrictions.eq("id", i)).list();
		Iterator iter = servicelist.iterator();
		while (iter.hasNext()) {
			obdh = (ObdBlackoutHours) iter.next();
		}
		return obdh;
	}

	@Override
	@Transactional
	public void addobh(ObdBlackoutHours blackout) {
		Logger.sysLog(LogValues.info, this.getClass().getName(), "value of id:"+ blackout.getId());
		Session session = sessionFactorySCP.getCurrentSession();
		//		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(blackout);
		//		tx.commit();
	}

	@Override
	@Transactional
	public String getJobname(String jobname) {

		String name = null;
		Service ser = new Service();
		Session session = sessionFactorySCP.getCurrentSession();
		Criteria crit = session.createCriteria(Service.class);
		List joblist = crit.add(Restrictions.eq("jobname", jobname)).list();
		Iterator iter = joblist.iterator();
		while (iter.hasNext()) {
			ser = (Service) iter.next();
		}
		if (ser != null) {

			Logger.sysLog(LogValues.info, this.getClass().getName(),"user ::" + ser.getJobname());
			name = ser.getJobname();
		}
		return name;
	}

	@Override
	@Transactional
	public List getServicelist(Integer id) {
		Service service = new Service();
		Mxgraph m = mxgraph(id);
		Session session = sessionFactorySCP.getCurrentSession();
		Criteria crit = session.createCriteria(Service.class);
		List servicelist = crit.add(Restrictions.eq("mxgraph", m)).list();
		Iterator iter = servicelist.iterator();
		while(iter.hasNext())
		{
			service = (Service)iter.next();
			service.getMxgraph().getServiceName();
			service.getMxgraph().getShortcode();
		}
		return servicelist;
	}


	@Transactional
	private NameValuePair addMsisdns(String path, String jobname, JobBean jobBean) {
		// TODO Auto-generated method stub
//		System.out.println("retryReasons: "+retryReasons);
		Logger.sysLog(LogValues.info, this.getClass().getName(), "--in add new msisdn--");
		ArrayList<String> create_table=new ArrayList<String>();
		String sql = "LOAD DATA LOCAL INFILE '"+path.replace("\\", "\\\\")+"'"+
				" IGNORE"+
				" INTO TABLE obd_msisdn_"+jobname+
				" LINES TERMINATED BY '\r\n'"+
				" (msisdn)"+
				" SET status='scheduled'";
		//		LOAD DATA LOCAL INFILE 'C:\\Users\\Pankaj\\Desktop\\b.csv'
		//	     IGNORE
		//	     INTO TABLE test
		//	     LINES TERMINATED BY '\r\n'
		//		(@number)
		//	     SET msisdn=CONCAT("pan",substring(@number,-10)),
		//	     status='scheduled';		
		Logger.sysLog(LogValues.info, this.getClass().getName(), "query : "+sql);
		if(this.connectionPool.getDriverClassName().toLowerCase().contains("mysql"))
		{
			Logger.sysLog(LogValues.info, this.getClass().getName(), "in mysql if condition");
			if(jobBean.getJobType().equalsIgnoreCase("recorddedication")){
				String query = "CREATE TABLE obd_msisdn_"+jobname +
						"(id INTEGER not NULL auto_increment, " +
						" `cli` varchar(25) NOT NULL,"+                             
						" `msisdn` varchar(25) NOT NULL,"+                          
						" `endtime` datetime NOT NULL,    "+                        
						" `calltime` datetime NOT NULL,     "+                      
						" `status` varchar(20) NOT NULL DEFAULT 'scheduled',"+      
						" `message` varchar(500) NOT NULL,                    "+    
						" `autotimestamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,"+
						" PRIMARY KEY (`id`))";
				Logger.sysLog(LogValues.info, this.getClass().getName(), "query : "+query);
				create_table.add(query); 
			}
			else{
				String query = "CREATE TABLE obd_msisdn_"+jobname +
						"(id INTEGER not NULL auto_increment, " +
						" msisdn VARCHAR(25), " + 
						" status VARCHAR(25), " +
						" reason VARCHAR(100), ";
				if(jobBean.getRetryreason()!=null){ 
					query+=	" failedreason_status VARCHAR(100) DEFAULT 'scheduled', ";
	//				String query2="CREATE TABLE obd_failure_reason_"+jobname +
	//						"(reasonid VARCHAR(100) not NULL , " +
	//						" retrycount INTEGER not NULL, "+
	//						" UNIQUE KEY (reasonid))";
	//				create_table.add(query2); 
	//				query2 = "insert into obd_failure_reason_"+jobname +" (reasonid,retrycount) values ";
					try {
						JSONObject jsonObject = new JSONObject(jobBean.getRetryreason()); 
						Iterator iterator = jsonObject.keys();
						while (iterator.hasNext()) {
							String s = (String) iterator.next();
							if(jobBean.getJobType().equalsIgnoreCase("starcopy") || !s.equalsIgnoreCase("max-retry")){		//because only starcopy contains "max-retry" 
								query+=" `failedreason_"+ s +"` INTEGER(11) DEFAULT "+jsonObject.getInt(s)+",";
	//							sql+=", failedreason_"+ s +"="+jsonObject.getInt(s);
	//							if(iterator.hasNext()){
	//								query2+=" (\""+s+"\","+jsonObject.getInt(s)+") , ";
	//								
	//							}
	//							else{
	//								query2+=" (\""+s+"\","+jsonObject.getInt(s)+") ";
	//							}
							}
						}
					} catch (JSONException e) {
						Logger.sysLog(LogValues.error, this.getClass().getName(), coreException.GetStack(e));
					}
	//				create_table.add(query2);
				}
				query+=" `autotimestamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,"+
				" PRIMARY KEY ( id )," +
				" UNIQUE KEY (msisdn))";
				Logger.sysLog(LogValues.info, this.getClass().getName(), "query : "+query);
				create_table.add(query);   
			}
		}
		else if(this.connectionPool.getDriverClassName().toLowerCase().contains("microsoft"))
		{
			Logger.sysLog(LogValues.info, this.getClass().getName(),"in mssql if condition");
			create_table.add("CREATE TABLE obd_msisdn_"+jobname +
					"(ID numeric(11) identity NOT NULL,"+
					"		MSISDN varchar(25) UNIQUE NOT NULL,"+
					"		STATUS varchar(25),"+
					"		REASON varchar(100),"+
					"		Time_Info datetime DEFAULT GETDATE(),"+
					"		PRIMARY KEY (ID))");
			create_table.add("CREATE TRIGGER updateLastUpdated_"+jobname+" ON obd_msisdn_"+jobname +" "+
					"FOR UPDATE "+
					"AS "+
					"if not update(Time_Info) "+
					"UPDATE [obd_msisdn_"+jobname +"] "+
					"SET Time_Info = GETDATE() "+
					"FROM [obd_msisdn_"+jobname +"] "+
					"INNER JOIN Inserted "+
					"ON [obd_msisdn_"+jobname +"].id = Inserted.id");
		}
		return addMsisdnsMysql(create_table,sql);
	}

	@Transactional
	public NameValuePair addMsisdnsMysql(ArrayList<String> create_table, String sql) {
		Logger.sysLog(LogValues.info, this.getClass().getName(),"--in addmsisdnmysql--");

		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = this.connectionPool.getConnection();
			for(String create : create_table){
				try{
					ps = connection.prepareStatement(create);
					ps.executeUpdate();
					ps.close();
				}catch(CommunicationsException e){
					try{connection.close();}catch(Exception e1){}
					connection = this.connectionPool.getNewConnection();
					ps = connection.prepareStatement(create);
					ps.executeUpdate();
					ps.close();
				}
			}

			ps = connection.prepareStatement(sql);
			ps.executeUpdate(); // insert remaining records
			if(connection != null){
				this.connectionPool.disConnect(connection);
				connection = null;
			}
			return new NameValuePair("true", "success");
		}
		catch(BatchUpdateException e){
			Logger.sysLog(LogValues.error, this.getClass().getName(),"BatchUpdateException " +e.getMessage());
			Logger.sysLog(LogValues.error, this.getClass().getName(), "BatchUpdateException exception occured msisdn "+coreException.GetStack(e));
			return new NameValuePair("false", ExceptionUtils.getRootCauseMessage(e).replace("'", ""));
		}
		catch (SQLException e) {
			Logger.sysLog(LogValues.error, this.getClass().getName(), "SQLException - " +e.getMessage());
			if(!e.getMessage().contains("already exists"))
				Logger.sysLog(LogValues.error, this.getClass().getName(), "SQLException exception occured msisdn "+coreException.GetStack(e));
			return new NameValuePair("false", ExceptionUtils.getRootCauseMessage(e).replace("'", ""));
		}
		catch (Exception e) {
			Logger.sysLog(LogValues.error, this.getClass().getName(), "exception occured msisdn"+coreException.GetStack(e));
			return new NameValuePair("false", ExceptionUtils.getRootCauseMessage(e).replace("'", ""));
		}
		finally{
			try {
				if(ps!=null){
					ps.close();
				}
				if(connection != null){
					this.connectionPool.disConnect(connection);
				}
			} catch (Exception e) {
				Logger.sysLog(LogValues.error, this.getClass().getName(), "exception occured while closing "+coreException.GetStack(e));
			}

		}


	}

	@Override
	@Transactional
	public ResultSet executeQuery(String tableExistsCheck) {
		Logger.sysLog(LogValues.info, this.getClass().getName(),"--in executeQuery--");

		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = this.connectionPool.getConnection();

			ps = connection.prepareStatement(tableExistsCheck);
			ResultSet resultSet = ps.executeQuery();
			if(connection != null){
				this.connectionPool.disConnect(connection);
				connection = null;
			}
			return resultSet;
		}
		catch(BatchUpdateException e){
			Logger.sysLog(LogValues.error, this.getClass().getName(),"BatchUpdateException " +e.getMessage());
			Logger.sysLog(LogValues.error, this.getClass().getName(), "BatchUpdateException exception occured msisdn "+coreException.GetStack(e));
			return null;
		}
		catch (SQLException e) {
			Logger.sysLog(LogValues.error, this.getClass().getName(), "SQLException" +e.getMessage());
			if(!e.getMessage().contains("Duplicate"))
				Logger.sysLog(LogValues.error, this.getClass().getName(), "SQLException exception occured msisdn "+coreException.GetStack(e));
			return null;
		}
		catch (Exception e) {
			Logger.sysLog(LogValues.error, this.getClass().getName(), "exception occured msisdn"+coreException.GetStack(e));
			return null;
		}
		finally{
			try {
				if(ps!=null){
					ps.close();
				}
				if(connection != null){
					this.connectionPool.disConnect(connection);
				}
			} catch (Exception e) {
				Logger.sysLog(LogValues.error, this.getClass().getName(), "exception occured while closing "+coreException.GetStack(e));
			}

		}
	}

	//	@Override
	//	public void addMsisdns(ArrayList<String> msisdns,String jobname) {
	//		Logger.sysLog(LogValues.debug, this.getClass().getName(), "--in add msisdn--");
	//		ArrayList<String> create_table=new ArrayList<String>();
	//		String sql = "insert into obd_msisdn_"+jobname +" (msisdn, status) values (?, ?)";
	//		if(this.connectionPool.getDriverClassName().toLowerCase().contains("mysql"))
	//		{
	//			Logger.sysLog(LogValues.debug, this.getClass().getName(), "in mysql if condition");
	//			create_table.add("CREATE TABLE obd_msisdn_"+jobname +
	//					"(id INTEGER not NULL auto_increment, " +
	//					" msisdn VARCHAR(25), " + 
	//					" status VARCHAR(25), " +
	//					" reason VARCHAR(100), " + 
	//					" `autotimestamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,"+
	//					" PRIMARY KEY ( id )," +
	//					" UNIQUE KEY (msisdn))");   
	//		}
	//		else if(this.connectionPool.getDriverClassName().toLowerCase().contains("microsoft"))
	//		{
	//			Logger.sysLog(LogValues.debug, this.getClass().getName(),"in mssql if condition");
	//			create_table.add("CREATE TABLE obd_msisdn_"+jobname +
	//					"(ID numeric(11) identity NOT NULL,"+
	//					"		MSISDN varchar(25) UNIQUE NOT NULL,"+
	//					"		STATUS varchar(25),"+
	//					"		REASON varchar(100),"+
	//					"		Time_Info datetime DEFAULT GETDATE(),"+
	//					"		PRIMARY KEY (ID))");
	//			create_table.add("CREATE TRIGGER updateLastUpdated_"+jobname+" ON obd_msisdn_"+jobname +" "+
	//					"FOR UPDATE "+
	//					"AS "+
	//					"if not update(Time_Info) "+
	//					"UPDATE [obd_msisdn_"+jobname +"] "+
	//					"SET Time_Info = GETDATE() "+
	//					"FROM [obd_msisdn_"+jobname +"] "+
	//					"INNER JOIN Inserted "+
	//					"ON [obd_msisdn_"+jobname +"].id = Inserted.id");
	//		}
	//		addMsisdnsMysql(msisdns,create_table,sql);
	//	}
	//
	//	private void addMsisdnsMysql(ArrayList<String> msisdns,ArrayList<String> create_table,String sql)  {
	//		Logger.sysLog(LogValues.debug, this.getClass().getName(),"--in addmsisdnmysql--");
	//
	//		Connection connection = null;
	//		PreparedStatement ps = null;
	//		try {
	//			connection = this.connectionPool.getConnection();
	//			for(String create : create_table){
	//				ps = connection.prepareStatement(create);
	//				ps.executeUpdate();
	//				ps.close();
	//			}
	//
	//			ps = connection.prepareStatement(sql);
	//			final int batchSize = 1000;
	//			int count = 1;
	//
	//			for (String msisdn: msisdns) {
	//				Logger.sysLog(LogValues.debug, this.getClass().getName(), "in add msisdn for loop");
	//
	//				ps.setString(1, msisdn);
	//				ps.setString(2, "scheduled");
	//				ps.addBatch();
	//
	//				if(++count % batchSize == 0) {
	//					ps.executeBatch();
	//				}
	//			}
	//			int[] a = ps.executeBatch(); // insert remaining records
	//			if(connection != null){
	//				this.connectionPool.disConnect(connection);
	//			}
	//		}
	//		catch(BatchUpdateException e){
	//			Logger.sysLog(LogValues.error, this.getClass().getName(),"BatchUpdateException "+e.getMessage());
	//			Logger.sysLog(LogValues.error, this.getClass().getName(), "BatchUpdateException exception occured msisdn "+coreException.GetStack(e));
	//		}
	//		catch (SQLException e) {
	//			Logger.sysLog(LogValues.error, this.getClass().getName(),"SQLException "+e.getMessage());
	//			if(!e.getMessage().contains("Duplicate"))
	//			Logger.sysLog(LogValues.error, this.getClass().getName(), "SQLException exception occured msisdn "+coreException.GetStack(e));
	//		}
	//		catch (Exception e) {
	//			Logger.sysLog(LogValues.error, this.getClass().getName(), "exception occured msisdn"+coreException.GetStack(e));
	//		}
	//		finally{
	//			try {
	//				if(ps!=null){
	//					ps.close();
	//				}
	//				if(connection != null){
	//					this.connectionPool.disConnect(connection);
	//				}
	//			} catch (Exception e) {
	//				Logger.sysLog(LogValues.error, this.getClass().getName(), "exception occured while closing "+coreException.GetStack(e));
	//			}
	//		}
	//
	//
	//	}

	@Override
	@Transactional
	public void dndFilter(String tablename, int i) {

		Logger.sysLog(LogValues.debug, this.getClass().getName(), "--In dndFilter function--");
		//String storedProcedure = "EXEC DnD_Temp_Test ?,?";
		String storedProcedure= "{CALL DnD_Temp_Test (?,?)}";
		Connection connection = null;
		CallableStatement callableStatement = null;
		try 
		{
			Logger.sysLog(LogValues.debug, this.getClass().getName(),"in try");
			connection = this.connectionPool.getConnection();
			callableStatement = connection.prepareCall(storedProcedure);
			callableStatement.setString(1, tablename);
			callableStatement.setInt(2, i);
			callableStatement.execute();
			Logger.sysLog(LogValues.info, this.getClass().getName(), "callable statement");
			if(connection != null){
				this.connectionPool.disConnect(connection);
				connection = null;
			}
		} 
		catch (SQLException e) 
		{
			Logger.sysLog(LogValues.error, this.getClass().getName(), "exception"+coreException.GetStack(e));
		}
		finally{
			try {
				Logger.sysLog(LogValues.info, this.getClass().getName(), "in 2nd try");
				if(callableStatement != null)
					callableStatement.close();
				if(connection != null){
					this.connectionPool.disConnect(connection);
					connection = null;
				}
			} catch (SQLException e) {
				Logger.sysLog(LogValues.error, this.getClass().getName(), "exception occured while closing "+coreException.GetStack(e));
			}

		}
	}

	@Override
	@Transactional
	public void updatejob(Service service) {
		Session session = sessionFactorySCP.getCurrentSession();
		//		Transaction tx = session.beginTransaction();
		session.update(service);
		//		tx.commit();
	}

	@Override
	@Transactional
	public List<ObdCli> getCli(Service service) {
		ObdCli cli = null;
		Session session = sessionFactorySCP.getCurrentSession();
		Criteria criteria = session.createCriteria(ObdCli.class);
		List cliList = criteria.add(Restrictions.eq("service", service)).list();
		Iterator iterator = cliList.iterator();
		while(iterator.hasNext())
		{
			cli = (ObdCli)iterator.next();
			cli.getCli();
			cli.getService();
		}
		return cliList;
	}

	@Override
	@Transactional
	public void deleteCli(Service service) {
		ObdCli cli = null;
		int id=service.getId();
		Session session = sessionFactorySCP.getCurrentSession();
		//		Transaction tx = session.beginTransaction();
		String query = "delete ObdCli where cli_id= :ser";
		int deletedEntities = session.createQuery( query )
				.setInteger( "ser", id )
				.executeUpdate();
		//		tx.commit();
	}

	@Override
	@Transactional
	public void addJob(String absolutePath, Service service,
			String serviceName, String shortCode, JobBean jobBean){
		Session session = sessionFactorySCP.getCurrentSession();

		NameValuePair status = addMsisdns(absolutePath, service.getJobname(),jobBean);
		if(Boolean.parseBoolean(status.getName())){
			session.saveOrUpdate(service);

			Service serv = getService(serviceName, shortCode);
			String[] clinumber = jobBean.getCliNumber().split(",");
			for (int i = 0; i < clinumber.length; i++) {
				ObdCli cli = new ObdCli(serv, clinumber[i]);
				Logger.sysLog(LogValues.info, this.getClass().getName(),
						serv.getId() + "service_id");
				Logger.sysLog(LogValues.info, this.getClass().getName(),
						clinumber[i] + "cli");
				session.save(cli);
			}
		}
		else if(status.getValue().contains("already exists")){
			throw new RuntimeException("Use a different Jobname.");
		}
		else
			throw new RuntimeException("Error adding msisdns in database(Maybe because of duplicate job entry OR connectivity issues).");
	}

	@Override
	@Transactional
	public List getObdFailureReasons() {
		Session session = sessionFactorySCP.getCurrentSession();		
		Criteria criteria = session.createCriteria(ObdFailureReasons.class);
		List list = criteria.list();
		Iterator iterator = list.iterator();
		while(iterator.hasNext()){
			ObdFailureReasons failureReasons = (ObdFailureReasons) iterator.next();
			failureReasons.getReasonId();
			failureReasons.getReasonValue();
		}
		return list;
	}

	//	private String extractRootCause(Exception e) {
	//		if(e.getCause()==null)
	//			return e.getMessage();
	//		else{
	////			Class c = e.getClass();
	////			extractRootCause(c.cast(e.getCause()));
	//			
	//			return extractRootCause((Exception) cast(e.getCause().getClass(),e.getCause()));
	//		}
	//	}
	//	private <T> T cast(Class<T> cls,Throwable e) {
	//	    return cls.cast(e);
	//	}

	@Override
	@Transactional
	public String insertMsisdn(String jobname, String msisdn){

		String status= "scheduled";
		String tablename = "obd_msisdn_"+jobname;
		Connection connection = null;
		PreparedStatement ps = null;
		int count=0;
		try {
			connection = this.connectionPool.getConnection();
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet res = meta.getTables(null, null, tablename, 
				    null);
			if(res.next())
			{
				String SQL = "insert into "+tablename+" (msisdn, status) values (?, ?)";
				Logger.sysLog(LogValues.info, this.getClass().getName(),"tablename: "+tablename+ " query: "+SQL);
			    ps = connection.prepareStatement(SQL);
				ps.setString(1, msisdn);
				ps.setString(2, status);
				ps.executeUpdate();
				res.close();
				count++;
			}
			else{
				
			}

		} catch (SQLException e) {
			Logger.sysLog(LogValues.error, this.getClass().getName(),
					coreException.GetStack(e));
			return e.getMessage();
		} finally {
			try {
				if(ps!=null)
				ps.close();
				if(connection != null){
					this.connectionPool.disConnect(connection);
					connection = null;
				}
			} catch (SQLException e) {
				Logger.sysLog(LogValues.error, this.getClass().getName(),
						coreException.GetStack(e));
				return e.getMessage();
			}

		}
		
		if(count!=0)
			return "Inserted successfuly";
		else
			return "table does not exist";
	}
	

	@Override
	@Transactional
	public List<Playlist> getPlayList()
	{
		Playlist playlist = null;		
		Session session = sessionFactorySCP.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(Playlist.class);
		List<Playlist> play = criteria.list();
		return play;
	}

	
	@Override
	@Transactional
	public List<ContentPlaylist> getPlaylists()
	{
		Session session=sessionFactorySCP.getCurrentSession();
		Criteria criteria=session.createCriteria(ContentPlaylist.class);
		List<ContentPlaylist> contentPlaylist=criteria.list();
		return contentPlaylist;
	}
	@Override
	@Transactional
	public List<ContentPlaylistMapper> getContent(int playlistId) 
	{
		List<ContentPlaylistMapper> contentPlaylistMapperList=null;
		Session session = sessionFactorySCP.getCurrentSession();
		Criteria criteria =session.createCriteria(ContentPlaylist.class).add(Restrictions.eq("id",playlistId));
		List<ContentPlaylist> contentPlaylists=criteria.list();
		Iterator<ContentPlaylist> contentPlaylistIterator=contentPlaylists.iterator();
		if(contentPlaylistIterator.hasNext())
		{
			ContentPlaylist contentPlaylist=contentPlaylistIterator.next();
			Criteria criteria1 = session.createCriteria(ContentPlaylistMapper.class).add(Restrictions.eq("contentPlaylist", contentPlaylist));
			contentPlaylistMapperList=criteria1.list();
			Iterator<ContentPlaylistMapper> contentPlaylistMapperIterator=contentPlaylistMapperList.iterator();
			while(contentPlaylistMapperIterator.hasNext())
			{
				ContentPlaylistMapper contentPlaylistMapper=contentPlaylistMapperIterator.next();
				Logger.sysLog(LogValues.debug, this.getClass().getName(),"" + contentPlaylistMapper.toString());
				Content content=contentPlaylistMapper.getContent();
				Logger.sysLog(LogValues.debug, this.getClass().getName(),"" + content.getPath());				
			}			
		}	
		return contentPlaylistMapperList;
	}
	
	@Override
	/*@Transactional
	public int addNewPlayList(String playlistName)
	{
		Session session =sessionFactorySCP.getCurrentSession();
		Criteria criteria = session.createCriteria(ContentPlaylist.class).add(Restrictions.eq("playlistName", playlistName));
		int returnId = -2;
		List<ContentPlaylist> list = criteria.list();		
		Iterator<ContentPlaylist> iterator = list.iterator();
		
		if(!iterator.hasNext())		
		{
			PreparedStatement ps;
		
			Connection connection = this.connectionPool.getConnection();
			String sql = "insert into content_playlist(playlist_name) values (?)";	
			try {
				ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, playlistName);
				ps.executeUpdate();
				ResultSet keys = ps.getGeneratedKeys();
				keys.next();
				returnId = keys.getInt(1);
				Logger.sysLog(LogValues.info, this.getClass().getName(), ps.toString());
				if(connection!=null)
					this.connectionPool.disConnect(connection);
			} catch (SQLException e) {
				if(connection!=null)
					this.connectionPool.disConnect(connection);
				Logger.sysLog(LogValues.error, this.getClass().getName(), e.getMessage());
			}			
		}
		else
		{
			returnId = -1;
		}
		
		return returnId;
	}*/
	
	@Transactional
	public int addNewPlayList(String playlistName)
	{
		Session session =sessionFactorySCP.getCurrentSession();
		Criteria criteria = session.createCriteria(ContentPlaylist.class).add(Restrictions.eq("playlistName", playlistName));
		List<ContentPlaylist> list = criteria.list();		
		Iterator<ContentPlaylist> iterator = list.iterator();		
		if(!iterator.hasNext())		
		{
			ContentPlaylist contentPlaylist=new ContentPlaylist(playlistName);
			session.saveOrUpdate(contentPlaylist);
			return contentPlaylist.getId();
		}
		else
		{
			return -1;
		}
		
	}
	

	/*@Override
	public int savePlaylistContents(int playlistId,int[] deleteContent ,int[] playContent)
	{
		try
		{
			Session session =sessionFactorySCP.getCurrentSession();
			session.setFlushMode(FlushMode.ALWAYS);
			Transaction tx = session.beginTransaction();

			if(deleteContent[0] != -1)
			{
				for(int i=0;i<deleteContent.length;i++)
				{
					ContentPlaylistMapper contentPlaylistMapper=(ContentPlaylistMapper)session.get(ContentPlaylistMapper.class, deleteContent[i]);
					session.delete(contentPlaylistMapper);
				}
			}

			ContentPlaylist contentPlaylist=(ContentPlaylist)session.get(ContentPlaylist.class,playlistId);

			if(playContent[0] != -1)
			{
				for(int i=0;i<playContent.length;i++)
				{
					ContentPlaylistMapper contentPlaylistMapper=new ContentPlaylistMapper();
					Content content=(Content)session.get(Content.class,playContent[i]);			
					contentPlaylistMapper.setContent(content);
					contentPlaylistMapper.setContentPlaylist(contentPlaylist);
					session.save(contentPlaylistMapper);
				}
			}
			
			tx.commit();
			return 1;
		}
		catch(Exception e)
		{
			Logger.sysLog(LogValues.error, this.getClass().getName(),e);
			return 0;
		}
		
	}*/
	
	
	@Override
	@Transactional
	public int savePlaylistContents(int playlistId,int[] insertContent)
	{
		try
		{
			Session session =sessionFactorySCP.getCurrentSession();
			session.setFlushMode(FlushMode.ALWAYS);
			
			Criteria criteria_delete = session.createCriteria(ContentPlaylistMapper.class).add(Restrictions.eq("contentPlaylist.id", playlistId));
			List<ContentPlaylistMapper> contentPlaylistMapper_deleteList = criteria_delete.list();
			Iterator<ContentPlaylistMapper> contentPlaylistMapper_deleteiterator = contentPlaylistMapper_deleteList.iterator();
			while(contentPlaylistMapper_deleteiterator.hasNext())
			{
				ContentPlaylistMapper contentPlaylistMapper_delete = contentPlaylistMapper_deleteiterator.next();
				session.delete(contentPlaylistMapper_delete);
			}
			if(insertContent[0] != -1)			// IF THE PLAYLIST IS NOT EMPTY
			{
				ContentPlaylistMapper contentPlaylistMapper_insert = null;
				ContentPlaylist contentPlaylist_insert = (ContentPlaylist)session.get(ContentPlaylist.class, playlistId);
				Content content_insert = null;
				for(int i = 0; i < insertContent.length; i++)
				{
					contentPlaylistMapper_insert = new ContentPlaylistMapper();
					content_insert = (Content)session.get(Content.class, insertContent[i]);
					contentPlaylistMapper_insert.setContent(content_insert);
					contentPlaylistMapper_insert.setContentPlaylist(contentPlaylist_insert);
					session.save(contentPlaylistMapper_insert);
				}
			}
			return 1;
		}
		catch(Exception e)
		{
			Logger.sysLog(LogValues.error, this.getClass().getName(),e);
			return 0;
		}		
	}
	
	@Override
	@Transactional
	public void savePlaylistContents(ContentPlaylist playlist,ArrayList<Content> contentArrayList)
	{
		try
		{
			Session session =sessionFactorySCP.getCurrentSession();
			session.setFlushMode(FlushMode.ALWAYS);
			session.save(playlist);
			for(Content content:contentArrayList)
			{
				try
				{
					ContentPlaylistMapper contentPlaylistMapper=new ContentPlaylistMapper(content,playlist);
					session.save(contentPlaylistMapper);
				}
				catch(Exception e)
				{
					Logger.sysLog(LogValues.error, this.getClass().getName(),coreException.GetStack(e));
				}
			}
		}
		catch(Exception e)
		{
			Logger.sysLog(LogValues.error, this.getClass().getName(),coreException.GetStack(e));			
		}
	}
	
	@Transactional
	public List<ContentPlaylistMapper> getPlaylists(int[] playlists)
	{
		Logger.sysLog(LogValues.info, this.getClass().getName(),"Content:");
		ArrayList<ContentPlaylistMapper> contentPlaylistMappers=new ArrayList<ContentPlaylistMapper>();
		try
		{
			Session session =sessionFactorySCP.getCurrentSession();
			session.setFlushMode(FlushMode.ALWAYS);
			for(int i=0;i<playlists.length;i++)
			{	
				Logger.sysLog(LogValues.info, this.getClass().getName(),"Playlist ID:"+playlists[i]);
				Criteria criteria = session.createCriteria(ContentPlaylistMapper.class).add(Restrictions.eq("contentPlaylist.id", playlists[i]));
				List<ContentPlaylistMapper> contentPlaylistMapperList = criteria.list();
				for(ContentPlaylistMapper contentPlaylistMapper : contentPlaylistMapperList)
				{
					Logger.sysLog(LogValues.info, this.getClass().getName(),"Content:"+contentPlaylistMapper.getContent().getPath());
					contentPlaylistMapper.getContent().getPath();
					contentPlaylistMapper.getContentPlaylist().getPlaylistName();
					contentPlaylistMappers.add(contentPlaylistMapper);
				}				
			}
		}
		catch(Exception e)
		{
			Logger.sysLog(LogValues.error, this.getClass().getName(),coreException.GetStack(e));
		}		
		return contentPlaylistMappers;
	}
}