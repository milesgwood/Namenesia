package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import dataTypes.Person;

public class PeopleSetter {

	static Connection c;
	static PreparedStatement stmt;

	/*
	 * Add a new person to the database that must have a first name
	 */
	public static void addPersonToDatabase(Person p) {
		int id = PeopleGetter.getNextID();
		String sql = "INSERT INTO people (id, first) VALUES (? , ?);";
		Connector.dbConnect(false);
		c = Connector.getConnection();
		try {
			stmt = c.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.setString(2, p.firstName);
			stmt.executeUpdate();
			if (p.middle != null) {
				updateMiddleName(id, p.middle);
			}
			if (p.lastName != null) {
				updateLastName(id, p.middle);
			}
			if (p.tags.size() > 0) {
				insertTags(id, p.tags);
			}
			stmt.close();
			c.commit();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update an already existing person in the database using their id number.
	 * 
	 * @param p
	 * @throws SQLException 
	 */
	static public void updatePerson(Person p, int id) throws SQLException {
		if(p.firstName != null) updateFirstName(id, p.firstName);
		if(p.middle != null) updateMiddleName(id, p.middle);
		if(p.lastName != null) updateLastName(id, p.lastName);
		if(p.tags.size() > 0) insertTags(id, p.tags);
	}

	/**
	 * Adds all of the tags for that person to the tags database using the same
	 * statement object.
	 * 
	 * @param id
	 * @param tags
	 * @throws SQLException
	 */
	static private void insertTags(int id, ArrayList<String> tags) throws SQLException {
		c = Connector.getConnection();
		String sql = "INSERT INTO tags(id, tag) VALUES (?, ?)";
		stmt = c.prepareStatement(sql);
		stmt.setInt(1, id);
		for (String tag : tags) {
			stmt.setString(2, tag);
			stmt.executeUpdate();
			System.out.println("Added another tag");
		}
	}

	static private void updateFirstName(int id, String first) throws SQLException {
		c = Connector.getConnection();
		String sql = "UPDATE people SET first = ? WHERE id = ?";
		stmt = c.prepareStatement(sql);
		stmt.setString(1, first);
		stmt.setInt(2, id);
		stmt.executeUpdate();
	}
	
	/**
	 * Adds the middle name to the people databse. Don't call directly. Use the
	 * updatePerson method.
	 * 
	 * @param id
	 * @param middle
	 * @throws SQLException
	 */
	static private void updateMiddleName(int id, String middle) throws SQLException {
		c = Connector.getConnection();
		String sql = "UPDATE people SET middle = ? WHERE id = ?";
		stmt = c.prepareStatement(sql);
		stmt.setString(1, middle);
		stmt.setInt(2, id);
		stmt.executeUpdate();
	}

	/**
	 * Adds the last name to the people database. Don't call directly. Use the
	 * updatePerson method.
	 * 
	 * @param id
	 * @param last
	 * @throws SQLException
	 */
	static private void updateLastName(int id, String last) throws SQLException {
		c = Connector.getConnection();
		String sql = "UPDATE people SET last = ? WHERE id = ?";
		stmt = c.prepareStatement(sql);
		stmt.setString(1, last);
		stmt.setInt(2, id);
		stmt.executeUpdate();
	}
}
