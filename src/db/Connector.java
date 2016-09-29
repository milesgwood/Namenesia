package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import sql.SQLParser;

public class Connector {

	static Connection c = null;
	static Statement stmt = null;
	static String sql;

	/**
	 * Makes the connection to the database. If there are lots of SQL statements to
	 * be made set autoCommit to false and call the disconnect method.
	 * 
	 * @param autoCommit
	 */
	public static void dbConnect(boolean autoCommit) {
		try {
			if (c != null)
			{
				if(!c.isClosed()) c.close();
			}
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Namenesia.sqlite");
			c.setAutoCommit(autoCommit);
			System.out.println("Opened database successfully");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * Disconnects from the database and closes resources.
	 */
	public static void dbDisconnect() {
		try {
			if(!stmt.isClosed()) stmt.close();
			
			if(!c.isClosed()) 
				{
					c.commit();
					c.close();
				}
		} catch (Exception e){
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Creates all of the database tables using the SQLParser and SQL text files.
	 */
	public static void createTables() {
		dbConnect(true);
		try {
			//create contacts
			stmt = c.createStatement();
			sql = SQLParser.sqlStringCreation("create.table.people.sql");
			stmt.executeUpdate(sql);
			
			//create tags
			stmt = c.createStatement(); 
			sql = SQLParser.sqlStringCreation("create.table.tags.sql");
			stmt.executeUpdate(sql);
			
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Tables created successfully");
	}
	
	/**
	 * Drops tables and prints a string.
	 * @param table
	 */
	public static void deleteTable(String table) {
		dbConnect(true);
		try {
			stmt = c.createStatement();
			String sql = "DROP TABLE IF EXISTS " + table + ";";
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println(table + " dropped");
	}
	
	/**
	 * Returns a Connection to the database.
	 * @return
	 */
	public static Connection getConnection(){
		try {
			if(c == null || c.isClosed())
			{
				dbConnect(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public static void main(String args[])
	{
		dbConnect(true);
		createTables();
		//deleteTable("people");
	}
}