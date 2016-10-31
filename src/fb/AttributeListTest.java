package fb;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class AttributeListTest {

	@Test
	public void test() {
		String miles = "Miles";
		String bad = "BADNAME";
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		attributes.add(new Attribute("Mile", new ArrayList<String>()));
		assertTrue(bad.equals(Attribute.isNameTaken(attributes, "Mile")));
		assertFalse(bad.equals(Attribute.isNameTaken(attributes, "Miles")));
		assertEquals("Miles", miles);
	}
}
