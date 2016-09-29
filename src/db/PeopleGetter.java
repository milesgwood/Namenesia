package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PeopleGetter {
	
	public static int getNextID()
	{
		int nextID = 0;
		Connection c;
		PreparedStatement stmt;
		ResultSet rs;
		
		c = Connector.getConnection();
		try {
			stmt = c.prepareStatement("SELECT max(id) from people;");
			rs = stmt.executeQuery();
			while(rs.next())
			{
				nextID = rs.getInt(1);
				nextID++;
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nextID;
	}
}
