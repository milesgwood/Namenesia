package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	public static ArrayList<String> getTagsForOnePersonID(int id)
	{
		ArrayList<String> tags = new ArrayList<String>();
		
		Connection c;
		PreparedStatement stmt;
		ResultSet rs;

		c = Connector.getConnection();
		try {
			stmt = c.prepareStatement("SELECT tag FROM tags WHERE id = ? ;");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				tags.add(rs.getString(1));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tags;	
	}
	
	public static void deleteString(String tag, int id)
	{
		Connection c;
		PreparedStatement stmt;

		c = Connector.getConnection();
		try {
			stmt = c.prepareStatement("DELETE FROM tags WHERE tag = ? AND id = ?;");
			stmt.setString(1, tag);
			stmt.setInt(2, id);
			stmt.executeUpdate();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		//searchByStringPattern("tall");
		ArrayList<String> tags = getTagsForOnePersonID(25);
		for(String s : tags)
		{
			System.out.println(s);
		}
	}
}