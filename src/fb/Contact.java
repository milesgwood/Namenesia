package fb;
import java.util.ArrayList;

import db.PeopleGetter;

/**
 * These facebook contacts will become entries in the people database after approval from the user
 */
public class Contact {
	private ArrayList<String> names = new ArrayList<String>();
	private int pID;
	public static int countID = PeopleGetter.getNextID();
	private ContactType type = ContactType.DEFAULT;

	String email = "";

	Contact(String first, String last) {
		names.add(first);
		names.add(last);
		this.pID = new Integer(countID++);
	}

	Contact(String first, String middle, String last) {
		names.add(first);
		names.add(middle);
		names.add(last);
		this.pID = new Integer(countID++);
	}
	
	Contact(String[] split, int id)
	{
		for(String s : split)
		{
			names.add(s.replace("'", ""));
		}
		this.pID = id;
	}
	
	public void setType(ContactType type)
	{
		this.type = type;
	}
	
	public Contact(String[] split) {
		for(String s : split)
		{
			names.add(s.replace("'", ""));
		}
		this.pID = new Integer(countID++);
	}

	public int getpID()
	{
		return this.pID;
	}
	
	public ArrayList<String> getListofNames()
	{
		return this.names;
	}
	
	public ContactType getType(){
		return this.type;
	}
	
	public String fullName()
	{
		String full = new String();
		for(String s: names)
		{
			full += s + " ";
		}
		full = full.trim();
		return full;
	}

	public String toString() {
		String result = this.fullName();
		if (email.length() > 0) {
				result += " " + email + " ";
		}
		return result + " " + this.pID + " " + this.type;
	}

	public void printContact() {
		System.out.println(this.toString());
	}

	public void setEmail(String newEmail) {
			this.email = newEmail;
	}
	
	public String getEmail()
	{
		return this.email;
	}
}
