package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import sql.SQLParser;

public class FBTables {

	static Connection c = null;
	static Statement stmt = null;
	static String sql;

	public static void fbParseSetup() {
		// Auto commit all of the setup table creation
		Connector.dbConnect(true);
		c = Connector.getConnection();
		deleteAll();
		createFBTables();
		Connector.dbDisconnect();

		// Don't auto commit the data insertion
		Connector.dbConnect(false);
		c = Connector.getConnection();
	}

	public static void fbParseFinish() {
		pruneFBTable();
		Connector.dbDisconnect();
		JOptionPane.showMessageDialog(new JFrame(),
				"Facebook data processed. Select the people you want to add or just add all");
	}

	private static void pruneFBTable() {
		try {
			stmt = c.createStatement();
			sql = SQLParser.sqlStringCreation("prune.fb.before.insertion.sql");
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void createFBTables() {
		try {
			// create contacts also called fb_with_scores
			stmt = c.createStatement();
			sql = SQLParser.sqlStringCreation("create.table.fb_with_scores.sql");
			stmt.executeUpdate(sql);

			// create tags
			stmt = c.createStatement();
			sql = SQLParser.sqlStringCreation("create.table.tags.sql");
			stmt.executeUpdate(sql);

			// create people
			stmt = c.createStatement();
			sql = SQLParser.sqlStringCreation("create.table.people.sql");
			stmt.executeUpdate(sql);
			
			// create the owner info table
			stmt = c.createStatement();
			sql = SQLParser.sqlStringCreation("create.table.owner_info.sql");
			stmt.executeUpdate(sql);

			// Create fb table
			stmt = c.createStatement();
			sql = SQLParser.sqlStringCreation("create.table.fb.sql");
			stmt.executeUpdate(sql);

			// Create messages
			stmt = c.createStatement();
			sql = SQLParser.sqlStringCreation("create.table.fb_messages.sql");
			stmt.executeUpdate(sql);

			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Tables created successfully");
	}

	public static void defaulttypeMultipliers() {
		try {
			stmt = c.createStatement();
			stmt.executeUpdate("DROP TABLE IF EXISTS fb_typemultipliers;");
			sql = SQLParser.sqlStringCreation("create.table.type.fb_multiplier.sql");
			stmt.execute(sql);
			stmt.execute("INSERT INTO fb_typeMultipliers(type) select distinct type from fb;");
			stmt.executeUpdate("UPDATE fb_typeMultipliers SET multiplier = 0 WHERE type='OWNER';");
			stmt.executeUpdate("UPDATE fb_typeMultipliers SET multiplier = 1 WHERE type='DEFAULT';");
			stmt.executeUpdate("UPDATE fb_typeMultipliers SET multiplier = rowid WHERE multiplier IS null;");
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void deleteTable(String table) {
		Connector.getConnection();
		try {
			stmt = c.createStatement();
			String sql = "DROP TABLE IF EXISTS " + table + ";";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println(table + " dropped");
	}

	public static boolean addFBContacts(int id, ArrayList<String> names, String type, String email) {
		try {
			stmt = c.createStatement();
			String sqlHead = "INSERT INTO fb(ID, FIRST, TYPE";
			String sqlValues = "( " + id + ", '" + names.get(0) + "', '" + type + "'";
			if (names.size() > 2) {
				sqlHead += " , MIDDLE";
				sqlValues += ", '" + names.get(1) + "'";
				sqlHead += " , LAST";
				sqlValues += ", '" + names.get(2) + "'";
			} else if (names.size() > 1) {
				sqlHead += " , LAST";
				sqlValues += ", '" + names.get(1) + "'";
			}
			if (email.length() > 0) {
				sqlHead += " , EMAIL";
				sqlValues += ", '" + email + "'";
			}
			sqlHead += ") VALUES ";
			sqlValues += ");";
			sql = sqlHead + sqlValues;
			// System.out.println(sqlHead + sqlValues);
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			System.err.println(sql);
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean addMessages(int threadID, int messageID, int messageSenderID, String date, String message) {
		date = cleanSqlString(date);
		message = cleanSqlString(message);
		try {
			stmt = c.createStatement();
			sql = "INSERT INTO fb_messages(tID, mID, senderID, date, message) VALUES (" + threadID + " , " + messageID
					+ " , " + messageSenderID + ", '" + date + "' " + " ,'" + message + "');";
			// System.out.println(sql);
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			System.err.println(sql);
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * Cleans the string of bad values since we aren't using prepared
	 * statements.
	 */
	private static String cleanSqlString(String s) {
		s = s.replace(",", " ");
		s = s.replace("'", "");
		return s;
	}

	public static void populateContactsAndScores() {
		try {
			stmt = c.createStatement();
			sql = SQLParser.sqlStringCreation("set.initial.scores.from.messages.sql");
			// System.out.println(sql);
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addOwnerInfo(String name, String info, int weight) {
		name = cleanSqlString(name);
		info = cleanSqlString(info);
		try {
			stmt = c.createStatement();
			sql = "INSERT INTO owner_info(type, info, weight) VALUES ('" + name + "' , '" + info + "' , " + weight
					+ ");";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void deleteAll() {
		deleteTable("fb_with_scores");
		deleteTable("fb");
		deleteTable("fb_messages");
		deleteTable("fb_typeMultipliers");
		deleteTable("owner_info");
	}

	public static void addAllToPeople() {
		try {
			c = Connector.getConnection();
			stmt = c.createStatement();
			//sql = "INSERT INTO people (id, first, middle, last) SELECT id, first, middle, last FROM fb; ";
			sql = SQLParser.sqlStringCreation("insert.all.fb.into.people.sql");
			stmt.executeUpdate(sql);
			//sql = "INSERT INTO tags (id, tag) SELECT id, email FROM fb_with_scores WHERE email IS NOT NULL;";
			sql = SQLParser.sqlStringCreation("insert.emails.sql");
			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println("Added all of the fb names and emails");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
