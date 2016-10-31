package fb;

import java.awt.Desktop;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

//My package imports
import db.FBTables;

public class FBParseDriver {
	
	public static void main(String [] args)
	{
		FBTables.fbParseSetup();
		fbParser();
		FBTables.fbParseFinish();
		FBTables.addAllToPeople();
		System.exit(0);
	}

	public static void fbParser() {
		String fbLocation = "";
		// html content
		JEditorPane ep = new JEditorPane("text/html",
				"<html><body>" 
						+ " Download your facebook archive. <br> " 
						+ "<a href=https://www.facebook.com/help/212802592074644>Instruction link</a>"
						+ "<br>Expand the archive and select the index.htm file in the folder"
						+ "</body></html>"
		);

		// handle link events
		ep.addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
					try {
						URI uri = new URL("https://www.facebook.com/help/212802592074644").toURI();
						Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
						if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
							desktop.browse(uri);
					} catch (IOException | URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		ep.setEditable(false);
		JOptionPane.showMessageDialog(null, ep);
		try {
			// Create info objects
			fbLocation = "/cs/home/stu/greatwmc/Documents/fb/index.htm";
			Parser.parseMain(fbLocation); // home/vice6/Downloads/FBMiles/index.htm
			ParseCleaner.clean();
			printAllAttributes(Parser.attributes);

			populateOwnerAttributes(Parser.attributes);
			populateFbTable();
			populateMessages();
			FBTables.populateContactsAndScores();
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Could not find the index.htm file at this specified location: \n" + fbLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void populateOwnerAttributes(ArrayList<Attribute> attributes) {
		for (Attribute a : attributes) {
			if (a.getName().equals("Friend"))
				continue;
			for (String info : a.list) {
				FBTables.addOwnerInfo(a.getName(), info, 1);
			}
		}
	}

	private static void populateMessages() {
		// printAllMessages(Parser.messageList);
		for (Message m : Parser.messageList) {
			FBTables.addMessages(m.getThreadID(), m.getMessageID(), m.messageSenderID(), m.getDate(),
					m.getMessage());
		}
	}

	public static void populateFbTable() {
		// printAllContacts(Parser.contacts);
		addAllContactsToDatabase(Parser.contacts);
		FBTables.defaulttypeMultipliers();
		System.out.println("Size of contacts list: - " + Parser.contacts.size());
	}

	private static void addAllContactsToDatabase(HashSet<Contact> contacts) {
		ArrayList<Integer> failed = new ArrayList<Integer>();
		for (Contact c : contacts) {
			if (!FBTables.addFBContacts(c.getpID(), c.getListofNames(), c.getType().toString(),
					c.getEmail())) {
				failed.add(c.getpID());
			}
		}
		System.out.println(failed.size() + " failed");
	}

	@SuppressWarnings("unused")
	private static void printAllMessages(ArrayList<Message> messageList) {
		for (Message m : messageList) {
			m.printMessage();
		}
	}

	public static void printAllAttributes(ArrayList<Attribute> attributes) {
		for (Attribute a : attributes) {
			// Whole list printed
			a.printAttribute();
			// Or just the header names
			// System.out.println(a.getName());
		}
	}

	public static void printAllContacts(HashSet<Contact> contacts) {
		for (Contact c : contacts) {
			c.printContact();
		}
	}
}
