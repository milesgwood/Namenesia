package dataTypes;


import java.util.ArrayList;

public class Person {
	
	public String firstName = null;
	public String middle = null;
	public String lastName = null;
	public ArrayList<String> tags = null;

	public Person(String first,String middle,String last, ArrayList<String> tags)
	{
		this.firstName = first;
		this.middle = middle;
		this.lastName = last;
		this.tags = tags;
	}
	
	public Person(String first, String middle, String last)
	{
		this.firstName = first;
		this.middle = middle;
		this.lastName = last;
	}
	
	public Person(String first, String last)
	{
		this.firstName = first;
		this.lastName = last;
	}
	
	public Person(String first)
	{
		this.firstName = first;
	}
	
	public void addTag(String tag)
	{
		this.tags = new ArrayList<String>();
		this.tags.add(tag);
	}
	
	public String toString()
	{
		String name;
		name = firstName;
		if(middle != null)  name += middle;
		if(lastName != null)name += lastName;
		return name;
	}
	
	public String toStringWithSpaces()
	{
		String name;
		name = firstName;
		if(middle != null)  name += " " + middle;
		if(lastName != null)name += " " + lastName;
		return name;
	}
}
