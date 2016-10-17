package dataTypes;

import static org.junit.Assert.*;

import org.junit.Test;

public class PersonTest {

	@Test
	public void test() {
		Person p = new Person("d", "a", " lkjasdf");
		assertEquals(true, p.toString().equals("da lkjasdf"));
	}
	
	@Test
	public void testNull() {
		Person p = new Person("a", "bcde");
		assertEquals(true, p.toString().equals("abcde"));
	}

}
