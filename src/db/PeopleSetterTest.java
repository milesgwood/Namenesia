package db;

import dataTypes.Person;
import static org.junit.Assert.*;

import org.junit.Test;

public class PeopleSetterTest {

	@Test
	public void test() {
		Person p = new Person("Emily");
		p.addTag("Tall Red Head");
		p.addTag("Tall Red Head");
		p.addTag("Tall Red Head");
		p.addTag("Tall Red Head");
		p.addTag("Tall Red Head");
		p.addTag("Tall Red Head");
		p.addTag("Tall Red Head");
		p.addTag("Tall Red Head");
		p.addTag("Tall Red Head");
		p.addTag("Tall Red Head");
		p.addTag("Tall Red Head");
		p.addTag("Tall Red Head");
		p.addTag("Saw her at Dave's Taverna when I had dinner with Katie");
		assert(p.tags.size() > 0);
		PeopleSetter.addPersonToDatabase(p);
	}
	
	public void test2() {
		Person p = new Person("Aaron", "Minnick");
		p.addTag("Philosophy Major");
		p.addTag("His sister is Zoe");
		assert(p.tags.size() > 0);
		PeopleSetter.addPersonToDatabase(p);
	}

}
