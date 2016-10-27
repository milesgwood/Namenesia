package fb;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import controller.StageChanger;
import dataTypes.Person;

import javafx.stage.FileChooser;

public class fbParser {
	
		// These are the locations of all of the pages we are going to parse
		static String home = null;
		static String friends = "/html/friends.htm";
		static String messages = "/html/messages.htm";
		static String contact = "/html/contact_info.htm";
		private static Elements links = null;
		
		static ArrayList<Person> parsedPersonList = new ArrayList<Person>();
		static String mainName = "Miles Greatwood";

	/**
	 * Gets the index.html location of the person's facebook archive. Ideally it
	 * would deal with the person giving the wrong location. It would extract
	 * the archive if they neglect to.
	 * 
	 * This must be run on the event thread so it must be put into a view
	 * component
	 * 
	 * @return
	 */
	public static String getIndexLocationFromUser() {
		final FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(StageChanger.getMainStage());
		return file.getAbsolutePath();
	}

	/**
	 * Creates a dialog that explains to the user how to get their facebook
	 * archive. This needs to be replaced with non-swing elements.
	 */
	public static void showDownloadInstructions() {
		JEditorPane ep = new JEditorPane("text/html",
				"<html><body>" + " Download your facebook archive. <br> "
						+ "<a href=https://www.facebook.com/help/212802592074644>Instruction link</a>"
						+ "<br>Expand the archive and select the index.htm file in the folder" + "</body></html>");

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
	}
	
	public static void beginParse(String path) throws IOException {
		File input = new File(path);
		Document doc = Jsoup.parse(input, "UTF-8");
		Elements names = doc.getElementsByTag("th");

		if (home == null) {
			home = path.substring(0, path.lastIndexOf('/') + 1);
		}
		if (links == null) {
			links = doc.select("div.nav > ul > li > a[href]");
		}
		
		input = new File(home + friends);
		doc = Jsoup.parse(input, "UTF-8");
		parseFriends(doc);
		
		
		//HANDLE THE MESSAGE PARSING. LEAVE THE REST OF THE ARCHIVE ALONE
		
		// parse the body of the archive
		//parseContentBodyLists(doc);
		//parseSingleTextFields(names);

		input = new File(home + messages);
		doc = Jsoup.parse(input, "UTF-8");
		//parseMessages(doc);
	}
	
	/**
	 * Creates an arrayList of friends names and email addresses as a Person List.
	 * @param doc
	 */
	public static void parseFriends(Document doc) {
		Elements friendsList = doc.select("div.contents > div > ul > li");
		for (Element e : friendsList) {
			Person p = parseFirendsName(e.text());
			if (p != null) parsedPersonList.add(p);
		}
	}

	/**
	 * This method takes in an array strigs with a possible email at the end in ().
	 * It treats emails and any extra strings as tags for the person
	 * @param name
	 * @return
	 */
	private static Person parseFirendsName(String name) {
		boolean hasEmail = name.contains(")");
		name = name.replace("'", "");
		String[] info = name.split("\\s+");
		
		Person p = null;
		
		switch(info.length){
		case 1:
			if (hasEmail) {break;}
			else {
				p = new Person(info[0]); 
				break; 
				}
		case 2:
			if (hasEmail) {
				p = new Person(info[0]);
				p.addTag(info[1]);
			}
			else {
				p = new Person(info[0], info[1]);
			}
			break;
		case 3:
			if (hasEmail)
			{
				p = new Person(info[0], info[1]);
				p.addTag(info[2]);
			}
			else
			{
				p = new Person(info[0], info[1], info[2]);
			}
			break;
		case 4:
			p = new Person(info[0], info[1], info[2]);
			for(int i = 3; i < info.length; i++)
			{
				p.addTag(info[i]);
			}
		}
		return p;
	}

	public static void main(String [] args) throws IOException
	{
		beginParse("/cs/home/stu/greatwmc/Documents/fb/index.htm");
		for(Person p : parsedPersonList)
		{
			System.out.println(p.toStringWithSpaces());
		}
	}
}
