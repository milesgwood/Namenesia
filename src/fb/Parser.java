package fb;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {
	static String headerTag = "th";
	static String dataTag = "td";
	static String listTag = "ul";
	static String listItemTag = "li";
	static String friends = "/html/friends.htm";
	static String messages = "/html/messages.htm";
	static String contact = "/html/contact_info.htm";
	static String home = null;
	private static Elements links = null;

	static ArrayList<Attribute> attributes = new ArrayList<Attribute>();
	static HashSet<Contact> contacts = new HashSet<Contact>();
	static ArrayList<Message> messageList = new ArrayList<Message>();
	static ArrayList<String> friendsAList = new ArrayList<String>();
	static String mainName = "Miles Greatwood";

	public static String getContactName(int pID) {
		for (Contact c : contacts) {
			if (c.getpID() == pID) {
				return c.fullName();
			}
		}
		return "NO NAME";
	}
	
	public static Contact getContact(int pID) {
		for (Contact c : contacts) {
			if (c.getpID() == pID) {
				return c;
			}
		}
		return null;
	}

	public static int findIDNum(String name) {
		name = name.replace("'", "");
		for (Contact c : contacts) {
			if (c.fullName().equals(name)) {
				return c.getpID();
			}
		}
		String[] splitNames = name.split("\\s+");
		Contact newC = new Contact(splitNames);
		contacts.add(newC);
		return newC.getpID();
	}
	
	public static Contact getContact(String name) {
		name = name.replace("'", "");
		for (Contact c : contacts) {
			if (c.fullName().equals(name)) {
				return c;
			}
		}
		String[] splitNames = name.split("\\s+");
		Contact newC = new Contact(splitNames);
		contacts.add(newC);
		return newC;
	}

	public static Elements removeLinks(Elements set) {
		Elements less = new Elements();
		for (Element e : set) {
			if (!e.parent().parent().className().equals("nav")) {
				less.add(e);
			}
		}
		return less;
	}

	public static void clearAttribute() {
		attributes = new ArrayList<Attribute>();
		Attribute.resetCount();
	}

	// This class gets passed the index.htm file and finds the rest of the
	// needed files
	public static ArrayList<Attribute> parseMain(String path) throws IOException {
		File input = new File(path);
		Document doc = Jsoup.parse(input, "UTF-8");
		Elements names = doc.getElementsByTag(headerTag);
	
		if (home == null) {
			home = path.substring(0, path.lastIndexOf('/') + 1);
		}
		if (links == null) {
			links = doc.select("div.nav > ul > li > a[href]");
		}
		//mainName = doc.select("div.contents > h1").first().text();
		Contact owner = new Contact(mainName.split("\\s+"), 0);
		owner.setType(ContactType.OWNER);
		contacts.add(owner);
		
		//Parse the body
		parseContentBodyLists(doc);
		parseSingleTextFields(names);
		input = new File(home + friends);
		doc = Jsoup.parse(input, "UTF-8");
		parseFriends(doc);
		input = new File(home + messages);
		doc = Jsoup.parse(input, "UTF-8");
		parseMessages(doc);
		return attributes;
	}

	private static void parseContacts(String name) {
		boolean hasEmail = name.endsWith(")");
		name = name.replace("'", "");
		String[] info = name.split("\\s+");
		Contact newContact;
		if (hasEmail) {
			if (info.length > 3) {
				newContact = new Contact(info[0], info[1], info[2]);
				newContact.setEmail(info[3].substring(1, info[3].length() - 1));
				newContact.setType(ContactType.FRIEND);
			} else {
				newContact = new Contact(info[0], info[1]);
				newContact.setEmail(info[2].substring(1, info[2].length() - 1));
				newContact.setType(ContactType.FRIEND);
			}
		} else {
			if (info.length > 2) {
				newContact = new Contact(info[0], info[1], info[2]);
				newContact.setType(ContactType.FRIEND);
			} else {
				newContact = new Contact(info[0], info[1]);
				newContact.setType(ContactType.FRIEND);
			}
		}
		contacts.add(newContact);
	}

	// Format is "div.contents > div > table > tbody > tr > td > ul > li"
	public static void parseContentBodyLists(Document doc) {
		ArrayList<String> dataList;
		Element grandparentTr;
		String name, newName;

		name = "a09sdfysdofa";
		newName = "d98fa6osdt";
		dataList = new ArrayList<String>();
		Elements listItems = doc.select("div.contents > div > table > tbody > tr > td > ul > li");

		for (Element e : listItems) {
			grandparentTr = e.parent().parent();
			newName = grandparentTr.firstElementSibling().text();
			if (!newName.equals(name) && dataList.size() > 0 || e.equals(listItems.last())) {
				name = Attribute.isNameTaken(attributes, name);
				if (!name.equals("BADNAME")) {
					attributes.add(new Attribute(name, dataList));
				}
				dataList = new ArrayList<String>();
			}
			name = new String(newName);
			dataList.add(new String(e.text()));
		}
		name = Attribute.isNameTaken(attributes, name);
		if (!name.equals("BADNAME") && !dataList.isEmpty()) {
			attributes.add(new Attribute(name, dataList));
		}
	}

	public static void parseFriends(Document doc) {
		Elements friendsList = doc.select("div.contents > div > ul > li");
		for (Element e : friendsList) {
			friendsAList.add(e.text());
			parseContacts(e.text());
		}
		attributes.add(new Attribute("Friend", friendsAList));
	}

	// Format is "div.contents > div > table > tbody > tr > td >span.meta > ul >
	// li"
	public static void parseMetaLists(Document doc) {
		ArrayList<String> dataList;
		Element greatGrandparentTr;
		String name, newName;

		name = "a09sdfysdofa";
		newName = "d98fa6osdt";
		dataList = new ArrayList<String>();
		Elements listItems = doc.select("div.contents > div > table > tbody > tr > td >span.meta > ul > li");

		for (Element e : listItems) {
			greatGrandparentTr = e.parent().parent().parent();
			newName = greatGrandparentTr.firstElementSibling().text();
			if (!newName.equals(name) && dataList.size() > 0 || e.equals(listItems.last())) {
				name = Attribute.isNameTaken(attributes, name);
				if (!name.equals("BADNAME")) {
					attributes.add(new Attribute(name, dataList));
				}
				dataList = new ArrayList<String>();
			}
			name = new String(newName);
			dataList.add(new String(e.text()));
		}
		name = Attribute.isNameTaken(attributes, name);
		if (!name.equals("BADNAME") && !dataList.isEmpty()) {
			attributes.add(new Attribute(name, dataList));
		}
	}

	public static void parseMessages(Document doc) {
		int pID;
		String name;
		String date;
		String message;
		int threadId = 0;
	
		Elements users = doc.select("div.message_header > span.user");
		Element oldThread = users.first().parent();
		Element newThread;
	
		for (Element e : users) {
			if (!e.text().endsWith(".com")) {
				name = e.text();
				date = e.nextElementSibling().text();
				pID = findIDNum(name);
				if (mainName.equals(name)) {
					pID = 0;
				}
				message = e.parent().parent().nextElementSibling().text();
				newThread = e.parent().parent().parent();
				if (!newThread.equals(oldThread)) {
					threadId++;
					oldThread = newThread;
				}
				messageList.add(new Message(pID, name, date, message, threadId));
			}
		}
	}

	// This splits String fields on commas
	private static void parseSingleTextFields(Elements names) {
		String data;
		String[] splitData;
		String name;
		Element next;
		ArrayList<String> dataList;

		for (Element e : names) {
			dataList = new ArrayList<String>();
			name = e.text();
			next = e.nextElementSibling();

			if (next.tagName().equals(dataTag)) { // we have a dataTag
				if (next.hasText()) {
					data = next.text();
					splitData = data.split(", ");
					for (String s : splitData) {
						dataList.add(s);
					}
				} else if (next.childNodeSize() > 1) // We have a straight list
				{
					dataList = parseStraightList(next);
				}
			}
			if (dataList.size() == 0) {
				return;
			}
			name = Attribute.isNameTaken(attributes, name);
			if (!name.equals("BADNAME")) {
				attributes.add(new Attribute(name, dataList));
			}
		}
	}

	private static ArrayList<String> parseStraightList(Element next) {
		Elements childElements;
		ArrayList<String> list = new ArrayList<String>();
		childElements = next.children();
		for (Element e : childElements) {
			list.add(e.text());
		}
		return list;
	}

	public static int getMessageCount() {
		
		return messageList.size();
	}

	public static int getThreadCount() {
		return (messageList.get(messageList.size() -1)).getThreadID();
	}
}