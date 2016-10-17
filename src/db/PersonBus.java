package db;

import dataTypes.Person;

public class PersonBus {
	
	public static Person currentPerson;
	public static String wholeName;
	public static int id;
	
	public static void setPerson(Person p, int pid)
	{
		currentPerson = p;
		wholeName = p.toString();
		id = pid;
	}
}
