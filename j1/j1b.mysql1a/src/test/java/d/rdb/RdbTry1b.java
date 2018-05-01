/**
 * This is unit test implementing the jdbc tutorials from mkyong website 
 * http://www.mkyong.com/tutorials/jdbc-tutorials/
 * 
 *  WARN: Establishing SSL connection without server's identity verification is not recommended. 
 *   https://stackoverflow.com/questions/34189756/warning-about-ssl-connection-when-connecting-to-mysql-database
 */

package d.rdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;





@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RdbTry1b {
	
	static Logger LOGGER = Logger.getLogger(RdbTry1b.class);
	
	
	protected static final String DB_NAME = "try1a"; 	// CREATE DATABASE try1a
	protected static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	protected static final String DB_CONF="?autoReconnect=true&useSSL=false";
	protected static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/"+DB_NAME+DB_CONF;
	protected static final String DB_USER = "root";
	protected static final String DB_PASSWORD = "";
	
	@BeforeClass
	public static void t10a_setup() {

		// %d
	    ConsoleAppender consoleApp = new ConsoleAppender(); //create appender	    
	    consoleApp.setLayout(new PatternLayout( "%d{HH:mm:ss} %p %l %m %n")); 
	    consoleApp.setThreshold(Level.INFO);
	    consoleApp.activateOptions();
		
		// 
		BasicConfigurator.resetConfiguration();
		BasicConfigurator.configure(consoleApp);
	    
		// []
	    ConsoleAppender console = new ConsoleAppender(); //create appender
	    String PATTERN = "%d [%p|%c|%C{1}] %m%n";
	    console.setLayout(new PatternLayout(PATTERN)); 
	    console.setThreshold(Level.FATAL);
	    console.activateOptions();
	    //Logger.getRootLogger().addAppender(console);

	    // [] 
	    FileAppender fa = new FileAppender();
	    fa.setName("FileLogger");
	    fa.setFile("mylog.log");
	    fa.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
	    fa.setThreshold(Level.DEBUG);
	    fa.setAppend(true);
	    fa.activateOptions();
	    //Logger.getRootLogger().addAppender(fa);
	    
	    // [] 
	    Enumeration e = Logger.getRootLogger().getAllAppenders();
	    while ( e.hasMoreElements() ){
	      Appender app = (Appender)e.nextElement();
	      if ( app instanceof FileAppender ){
	        System.out.println("File: " + ((FileAppender)app).getFile());
	      } else {
	    	  	System.out.println( app.getClass().getName() );
	      }
	    }	    
	}
	
	/**
	 * 
	 * @throws SQLException
	 */
	protected static String t10a_table_name="dbuser1";
	

	@Test
	public void t11a_table_create() throws SQLException {

		String createTableSQL = "CREATE TABLE " 
				+ t10a_table_name
				+ "("
				+ "USER_ID INT(5) NOT NULL, "
				+ "USERNAME VARCHAR(20) NOT NULL, "
				+ "CREATED_BY VARCHAR(20) NOT NULL, "
				+ "CREATED_DATE DATE NOT NULL, " 
				+ "PRIMARY KEY (USER_ID) "
				+ ")";
		LOGGER.debug(createTableSQL);

		Connection dbConnection = null;
		Statement statement = null;
		try {			
			dbConnection = getDBConnection();
			dbConnection.createStatement().execute(createTableSQL);		
			LOGGER.info("Table "+ t10a_table_name +"  is created!");
		} catch (SQLException e) { LOGGER.error(e.getMessage());			
		} finally {
			if (statement != null) {	statement.close(); }
			if (dbConnection != null) {	dbConnection.close(); }
		}

	}

	
	/**
	 * Drop table
	 */
	@Test
	public void t11b_table_drop() throws SQLException {
		
		String createTableSQL = "DROP TABLE "+ t10a_table_name;
		LOGGER.debug(createTableSQL);
		
		Connection dbConnection = null;
		Statement statement = null;
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();			
			statement.execute(createTableSQL);
			LOGGER.info("Table "+t10a_table_name +" is dropped !");
		} catch (SQLException e) { LOGGER.error(e.getMessage());			
		} finally {
			if (statement != null) {	statement.close(); }
			if (dbConnection != null) {	dbConnection.close(); }
		}		
		
	}
	
	private static Connection getDBConnection() {

		try { 
			Class.forName(DB_DRIVER);  			   			
		} catch (ClassNotFoundException e) { LOGGER.error(e.getMessage()); } 


		Connection dbConnection = null;
		try {  
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {			LOGGER.error(e.getMessage());		}

		return dbConnection;
	}
	
	
	
	/**
	 * The “Statement” interface is used to execute a simple SQL statement with no parameters. 
	 * For create, insert, update or delete statement, 
	 * uses “Statement.executeUpdate(sql)“; 
	 * select query, uses “Statement.executeQuery(sql)“.	
	 * @throws SQLException
	 */
	protected static String t20a_table_name="dbuser2";
	
	/**
	 * Example to create a table in database.
	 * @throws SQLException
	 */
	@Test
	public void t21a_table_create() throws SQLException {
		
		String createTableSQL = "CREATE TABLE "
				+ t20a_table_name 
				+ " ( "
				+ "USER_ID INT(5) NOT NULL, "
				+ "USERNAME VARCHAR(20) NOT NULL, "
				+ "CREATED_BY VARCHAR(20) NOT NULL, "
				+ "CREATED_DATE DATE NOT NULL, " 
				+ "PRIMARY KEY (USER_ID) "
				+ " ) ";
		LOGGER.debug(createTableSQL);

		Connection dbConnection = null;
		Statement statement = null;		
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			statement.execute(createTableSQL);
			LOGGER.info("Table "+ t20a_table_name +" is created!");
		} catch (SQLException e) { LOGGER.error(e.getMessage());			
		} finally {
			if (statement != null) {	 statement.close(); }
			if (dbConnection != null) {	dbConnection.close(); }
		}
		
	}
	
	/**

	 * Example to insert a record into table. 
	 * @throws SQLException
	 */
	@Test
	public void t22b_record_insert() throws SQLException {
		
		String insertTableSQL = "INSERT INTO " 
				+ t20a_table_name 
				+ "(USER_ID, USERNAME, CREATED_BY, CREATED_DATE) " 
				+ "VALUES"
				+ "(1,'mkyong','system', " + "str_to_date('"
				+ getCurrentTimeStamp() + "', '%Y/%m/%d'))";

		Connection dbConnection = null;
		Statement statement = null;		
		try { LOGGER.debug(insertTableSQL);
			dbConnection = getDBConnection();		
			statement = dbConnection.createStatement(); 			
			statement.executeUpdate(insertTableSQL);
			LOGGER.info("Record is inserted into "+t20a_table_name+" table!");
		} catch (SQLException e) { System.out.println(e.getMessage());
		} finally {
			if (statement != null) { statement.close(); } 
			if (dbConnection != null) { dbConnection.close(); } 
		}		
		
	}

	
	/**
	 * Example to insert records in batch process, via JDBC Statement.
	 * @throws SQLException
	 */
	@Ignore
	@Test
	public void t23c_record_insert_batch() throws SQLException {
		

		String insertTableSQL1 = "INSERT INTO DBUSER"
				+ "(USER_ID, USERNAME, CREATED_BY, CREATED_DATE) " + "VALUES"
				+ "(101,'mkyong','system', " + "to_date('"
				+ getCurrentTimeStamp() + "', 'yyyy/mm/dd hh24:mi:ss'))";
		
		String insertTableSQL2 = "INSERT INTO DBUSER"
				+ "(USER_ID, USERNAME, CREATED_BY, CREATED_DATE) " + "VALUES"
				+ "(102,'mkyong','system', " + "to_date('"
				+ getCurrentTimeStamp() + "', 'yyyy/mm/dd hh24:mi:ss'))";

		String insertTableSQL3 = "INSERT INTO DBUSER"
				+ "(USER_ID, USERNAME, CREATED_BY, CREATED_DATE) " + "VALUES"
				+ "(103,'mkyong','system', " + "to_date('"
				+ getCurrentTimeStamp() + "', 'yyyy/mm/dd hh24:mi:ss'))";
		
		
		Connection dbConnection = null;
		Statement statement = null;		
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			dbConnection.setAutoCommit(false);

			statement.addBatch(insertTableSQL1);
			statement.addBatch(insertTableSQL2);
			statement.addBatch(insertTableSQL3);

			statement.executeBatch();

			dbConnection.commit();

			LOGGER.info("Records are inserted into "
					+ t20a_table_name 
					+ " table!");

		} catch (SQLException e) { LOGGER.error(e.getMessage());
		} finally {

			if (statement != null) { statement.close(); } 
			if (dbConnection != null) { dbConnection.close(); } 

		}		
	}	
	
	/**
	 *  Example to update a record in table. 
	 * @throws SQLException
	 */
	@Test
	public void t24d_record_update() throws SQLException {
		
		String updateTableSQL = "UPDATE "
				+ t20a_table_name
				+ " SET USERNAME = 'mkyong_new' "
				+ " WHERE USER_ID = 1";
		LOGGER.debug(updateTableSQL);

		Connection dbConnection = null;
		Statement statement = null;		
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			statement.execute(updateTableSQL);
			LOGGER.info("Record is updated to "
					+ t20a_table_name
					+ "table!");

		} catch (SQLException e) { LOGGER.error(e.getMessage());
		} finally {
			if (statement != null) { statement.close(); } 
			if (dbConnection != null) { dbConnection.close(); } 
		}
	}	

	/**
	 *  Example to select the entire records from a table, and iterate the records via a ResultSet object. 
	 * @throws SQLException
	 */
	@Test
	public void t25e_record_list() throws SQLException {
		
		String selectTableSQL = "SELECT USER_ID, USERNAME from "+t20a_table_name;
		LOGGER.debug(selectTableSQL);
		
		Connection dbConnection = null;
		Statement statement = null;		
		try { 
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			ResultSet rs = statement.executeQuery(selectTableSQL);
			while (rs.next()) {
				String userid = rs.getString("USER_ID");
				String username = rs.getString("USERNAME");
				LOGGER.info("userid : " + userid +" username : " + username); 				
			}

		} catch (SQLException e) { System.out.println(e.getMessage());
		} finally {
			if (statement != null) { statement.close(); } 
			if (dbConnection != null) { dbConnection.close(); } 
		}


	}			
	
	/**
	 *  Example to delete a record from a table. 
	 * @throws SQLException
	 */
	@Test
	public void t26f_record_delete() throws SQLException {
		
		String deleteTableSQL = "DELETE FROM "+t20a_table_name +" WHERE USER_ID = 1";
		Connection dbConnection = null;
		Statement statement = null;
		try { LOGGER.debug(deleteTableSQL);
			dbConnection = getDBConnection();
			dbConnection.createStatement().execute(deleteTableSQL);
			LOGGER.info("Record is deleted from DBUSER table!");
		} catch (SQLException e) { System.out.println(e.getMessage());
		} finally {
			if (statement != null) { statement.close(); }
			if (dbConnection != null) { dbConnection.close(); } 
		}
		
	}
	

	@Test
	public void t27g_table_drop() throws SQLException {
		
		String createTableSQL = "DROP TABLE "+ t20a_table_name;				
		LOGGER.debug(createTableSQL);

		Connection dbConnection = null;
		Statement statement = null;
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			statement.execute(createTableSQL);	 
			LOGGER.info("Table "+ t20a_table_name +" is dropped");			
		} catch (SQLException e) { LOGGER.error(e.getMessage());	
		} finally {
			if (statement != null) {		statement.close();	}
			if (dbConnection != null) {	dbConnection.close();	}
		}		
		
	}
	
	//private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	
	private static String getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return dateFormat.format(today.getTime());

	}	
	
	
	
	/**
	 * JDBC & PreparedStatement
	 * The “PreparedStatement” interface is extended “Statement”, with extra feature to send a pre-compiled SQL statement with parameters. For create, insert, update or delete statement, uses “PreparedStatement.executeUpdate(sql)“; select query, uses “PreparedStatement.executeQuery(sql)“. 
	 */
	protected static String t30a_table_name="dbuser3";
	
	/**
	 * Example to create a table in database. 
	 */
	@Test
	public void t31a_table_create() throws SQLException {
		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String createTableSQL = "CREATE TABLE "
				+ t30a_table_name
				+ "("
				+ "USER_ID INT(5) NOT NULL, "
				+ "USERNAME VARCHAR(20) NOT NULL, "
				+ "CREATED_BY VARCHAR(20) NOT NULL, "
				+ "CREATED_DATE DATE NOT NULL, " + "PRIMARY KEY (USER_ID) "
				+ ")";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(createTableSQL);
			LOGGER.debug(createTableSQL);			
			preparedStatement.executeUpdate(); 			// execute create SQL stetement
			LOGGER.info("Table "
					+ t30a_table_name
					+ " is created!");

		} catch (SQLException e) { LOGGER.error(e.getMessage());			
		} finally {
			if (preparedStatement != null) { preparedStatement.close(); } 
			if (dbConnection != null) {dbConnection.close(); }  
		}
		
	}

	/**
	 * 
	 */
	@Test
	public void t32b_record_insert() {}

	
	/**
	 * 
	 */
	@Test
	public void t32c_record_insert_batch() {}

	
	/**
	 * 
	 */
	@Test
	public void t32d_record_update() {}

	
	/**
	 * 
	 */
	@Test
	public void t32e_record_list() {}
	
	/**
	 * 
	 */
	@Test
	public void t32f_record_delete() {}

	
	/**
	 * 
	 */
	@Test
	public void t32f_table_drop() throws SQLException {
		

		String createTableSQL = "DROP TABLE "
				+ t30a_table_name;
		LOGGER.debug(createTableSQL);			
		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(createTableSQL);			
			preparedStatement.executeUpdate(); 			// execute create SQL stetement
			LOGGER.info("Table "+ t30a_table_name + " is dropped!");
		} catch (SQLException e) { LOGGER.error(e.getMessage());			
		} finally {
			if (preparedStatement != null) { preparedStatement.close(); } 
			if (dbConnection != null) {dbConnection.close(); }  
		}
		
	}
	
	
	
	
	
	
	/**
	 * DBC & Stored Procedure
	 * JDBC CallableStatement and Stored Procedure, IN, OUT, CURSOR examples.
	 */
	
	/**
	 * Example to create a table in database. 
	 */
	@Test
	public void t41a_table_create() throws SQLException  {}

	/**
	 * Stored procedure IN parameter via JDBC CallableStatement. 
	 */
	@Test
	public void t42b_procedure_in() throws SQLException  {}

	
	/**
	 * 
	 */
	@Test
	public void t42c_procedure_out() throws SQLException  {}

	
	/**
	 * 
	 */
	@Test
	public void t42d_procedure_cursor() throws SQLException  {}

	
	/**
	 * 
	 */
	@Test
	public void t42e_taable_drop() throws SQLException  {}
	
	
	/**
	 * JDBC Transaction example
	 */
	
	/**
	 * 
	 */
	@Test
	public void t51a_table_create() throws SQLException  {}

	/**
	 * Example to show you how to use JDBC Transaction.
	 */
	@Test
	public void t52b_transaction() throws SQLException  {}
	
	/**
	 * 
	 */
	@Test
	public void t53c_tabel_drop() throws SQLException {}
	
	
	/**
	 * JDBC Integration Example
	 * Integrate JDBC with other frameworks.
	 */
	

}
