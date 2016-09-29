package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TagSearchEngine {

	/**
	 * This method matches based on one single keyword in the tag. It is limited to 20 results.
	 * @param keyword
	 * @return
	 */
	public static int[] searchByStringPattern(String keyword) {
		int[] resultIDs = new int[20];
		keyword = '%' + keyword + '%';
		
		Connection c;
		PreparedStatement stmt;
		ResultSet rs;

		c = Connector.getConnection();
		try {
			stmt = c.prepareStatement("SELECT id, COUNT(tag) as freq FROM tags WHERE tag LIKE ? GROUP BY id  ORDER BY freq DESC LIMIT 20;");
			stmt.setString(1, keyword);
			rs = stmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				resultIDs[i++] = rs.getInt(1);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultIDs;
	}

	public static void main(String[] args) {
		searchByStringPattern("tall");
	}
}