package fb;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class ParserTest {

	private static Document doc;
	private static Elements links, temp;

	public static void setup(String path) {
		try {
			File input = new File(path);
			doc = Jsoup.parse(input, "UTF-8");
			if (links == null) {
				links = new Elements();
				temp = doc.select("a[href]");
				for (Element link : temp) {
					if (link.parent().parent().parent().className().equals("nav")) {
						links.add(link);
					}
				}
				assertEquals(links.size(), 14);
				printLinks();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void printLinks() throws IOException {
		String relHref;
		for (Element e : links) {
			relHref = e.attr("href");
			// System.out.println(relHref);
		}
	}

	@Test
	public void testContactInfo() {
		setup("/home/vice6/Downloads/FBMiles/html/contact_info.htm");
		assertEquals(links.size(), 14);
		Elements items = doc.getElementsByTag(Parser.listItemTag);
		assertEquals(32, items.size());
		Elements less = Parser.removeLinks(items);
		assertEquals(17, less.size());
	}

	@Test
	public void testParseOrder() throws IOException {
		String path = "/home/vice6/Downloads/FBMiles/html/contact_info.htm";
		File input = new File(path);
		doc = Jsoup.parse(input, "UTF-8");
		Elements longWay = doc.select("div.contents > div > table > tbody > tr > td > ul > li");
		Elements contents = doc.select("div.contents");
		contents = contents.select("div > table > tbody > tr > td > ul > li");
		assertEquals(contents.size(), longWay.size());
		// System.out.println("longWay");
		for (Element e : longWay) {
			// System.out.println(e.toString() + "\n");
		}

	}

	@Test
	public void testDirectListParse() throws IOException {
		Parser.clearAttribute();
		String path = "/home/vice6/Downloads/FBMiles/html/contact_info.htm";
		File input = new File(path);
		doc = Jsoup.parse(input, "UTF-8");
		Parser.parseContentBodyLists(doc);
		for (Attribute a : Parser.attributes) {
			// System.out.println(a.toString());
		}
		assertEquals(Parser.attributes.size(), 3);
	}

	@Test
	public void testMetaSpanParse() throws IOException {
		Parser.clearAttribute();
		String path = "/home/vice6/Downloads/FBMiles/html/contact_info.htm";
		File input = new File(path);
		doc = Jsoup.parse(input, "UTF-8");
		Parser.parseMetaLists(doc);
		for (Attribute a : Parser.attributes) {
			// System.out.println(a.toString());
		}
		assertEquals(Parser.attributes.size(), 1);
	}

	@Test
	public void testParser() throws IOException {
		Parser.clearAttribute();
		ArrayList<Attribute> attributes = Parser.parseMain("/home/vice6/Downloads/FBMiles/index.htm");
		ArrayList<String> names = Attribute.printAllNames(attributes);
		// System.out.println(names.toString());
		assertEquals(names.size(), 27);
		assertTrue(names.contains("Pages You Admin"));
		assertTrue(names.contains("Hometown"));
		assertTrue(names.contains("App"));
		assertTrue(names.contains("Group"));
	}

	@Test
	public void truncateTest() throws IOException {
		assertEquals("Phone", Attribute.truncate("Phones"));
	}

	@Test
	public void testParserHomeVariable() throws IOException {
		ArrayList<Attribute> attributes = Parser.parseMain("/home/vice6/Downloads/FBMiles/index.htm");
		// System.out.println(Parser.home);
		// System.out.println("/home/vice6/Downloads/FBMiles/");
		assertEquals(Parser.home, "/home/vice6/Downloads/FBMiles/");
	}

	@Test
	public void testSecondvalue() throws IOException {
		String path = new String("/home/vice6/Downloads/FBMiles/html/contact_info.htm");
		File input = new File(path);
		Document doc = Jsoup.parse(input, "UTF-8");
		Elements e = doc.getElementsByClass("nav");
		int i = 0;
		Elements e2, e3, e4;
		Element single = e.get(0);
		Elements listItems = single.getElementsByTag("li");
		for (Element item : listItems) {
			assertEquals(e.size(), 1);
			// System.out.println(i++ + " " + item.toString());
		}
		assertEquals(listItems.size(), 15);
	}

	@Test
	public void testFriends() throws IOException {
		Parser.clearAttribute();
		String path = new String("/home/vice6/Downloads/FBMiles/html/friends.htm");
		File input = new File(path);
		Document doc = Jsoup.parse(input, "UTF-8");
		Parser.parseFriends(doc);
		assertEquals(Parser.attributes.size(), 1);
		//System.out.println(Parser.attributes.get(0).toString());
	}
	
	@Test
	public void testMyPid() throws IOException {
		Parser.clearAttribute();
		Parser.parseMain("/home/vice6/Downloads/FBMiles/index.htm");
		assertEquals(Parser.getContactName(0), "Miles Greatwood");
		assertEquals(Parser.findIDNum("Miles Greatwood"), 0);
	}

	@Test
	public void printFriends() throws IOException {
		Parser.clearAttribute();
		String path = new String("/home/vice6/Downloads/FBMiles/html/friends.htm");
		File input = new File(path);
		Document doc = Jsoup.parse(input, "UTF-8");
		Parser.parseFriends(doc);
		for (Contact c : Parser.contacts) {
			c.fullName();
		}
	}

	@Test
	public void parseMessages() throws IOException {
		Parser.clearAttribute();
		String path = new String("/home/vice6/Downloads/FBMiles/html/messages.htm");
		File input = new File(path);
		Document doc = Jsoup.parse(input, "UTF-8");
		Parser.parseMessages(doc);
		Message m = Parser.messageList.get(0);
		//System.out.println(m.toString());
		assertEquals(m.messageSenderName(), "Michael Chen");
		Message n = Parser.messageList.get(7);
		System.out.println(n.toString());
		assertEquals(n.messageSenderName(), "Megan Morris");
		assertEquals(m.getThreadID(), n.getThreadID());
	}
}
