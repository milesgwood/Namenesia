package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dataTypes.Person;

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
	
	public static String getNameFromID(int id)
	{
		String first, middle, last;
		first = "";
		middle = "";
		last = "";
		
		Person p = null;
		Connection c;
		PreparedStatement stmt;
		ResultSet rs;
		
		c = Connector.getConnection();
		try {
			stmt = c.prepareStatement("SELECT first, middle, last FROM people WHERE id = ?;");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			while(rs.next())
			{
				first = rs.getString(1);
				middle = rs.getString(2);
				last = rs.getString(3);
			}
			System.out.println(first + middle + last);
			p = new Person(first, middle, last);
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p.toString();
	}
	
	public static Person getPersonFromID(int id)
	{
		String first, middle, last;
		first = "";
		middle = "";
		last = "";
		
		Person p = null;
		Connection c;
		PreparedStatement stmt;
		ResultSet rs;
		
		c = Connector.getConnection();
		try {
			stmt = c.prepareStatement("SELECT first, middle, last FROM people WHERE id = ?;");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			while(rs.next())
			{
				first = rs.getString(1);
				middle = rs.getString(2);
				last = rs.getString(3);
			}
			p = new Person(first, middle, last);
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		p.tags = getTagsFromID(id);
		return p;
	}
	
	public static ArrayList<String> getTagsFromID(int id)
	{
		Connection c;
		PreparedStatement stmt;
		ResultSet rs;
		ArrayList<String> tags = new ArrayList<String>();
		
		c = Connector.getConnection();
		try {
			stmt = c.prepareStatement("SELECT tag FROM tags WHERE id = ?;");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			while(rs.next())
			{
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
}
