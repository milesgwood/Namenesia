package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TagPruner {

	static Connection c = null;
	static Statement stmt = null;

	public static void pruneDuplicates() {

		String[] statements = { " CREATE TABLE tmp(id INT, tag TEXT); ",
				"INSERT INTO tmp SELECT DISTINCT * FROM tags; ", 
				"DROP TABLE tags; ",
				"ALTER TABLE tmp RENAME TO tags; " };
		try {
			c = Connector.getConnection();
			stmt = c.createStatement();
			for(String sql: statements)
			{
				stmt.execute(sql);
			}
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Got rid of duplicate tags");
	}
}
