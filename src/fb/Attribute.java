package fb;
import java.util.ArrayList;

public class Attribute {
	private static int attCount = 0;
	private String attName;
	private int elementID;
	ArrayList<String> list;

	Attribute(String name, ArrayList<String> newAttList) {
		this.attName = name;
		this.elementID = this.getCount();
		this.list = new ArrayList<String>();
		this.list.addAll(newAttList);
		attCount++;
	}

	public static void resetCount() {
		attCount = 0;
	}

	public int getCount() {
		return attCount;
	}

	public String getName() {
		return this.attName;
	}

	public int getID() {
		return this.elementID;
	}

	public static ArrayList<String> printAllNames(ArrayList<Attribute> attributes) {
		ArrayList<String> names = new ArrayList<String>();
		for (Attribute att : attributes) {
			names.add(att.getName());
		}
		return names;
	}
	
	public void printAttribute() {
		System.out.println(this.toString());
	}

	public String toString() {
		return (getID() + ":" + getName() + "\n\t" + printList() + "\n");
	}
	
	private String printList()
	{
		String result = "";
		for(String s : this.list)
		{
			result += s + "\n\t";
		}
		return result;
	}

	public ArrayList<String> getInfo() {
		return this.list;
	}

	public static void addToList(ArrayList<Attribute> attributes, String name, ArrayList<String> dataList) {
		for (Attribute a : attributes) {
			if (a.getName().equals(name)) {
				a.getInfo().addAll(dataList);
			}
		}
	}

	public static String isNameTaken(ArrayList<Attribute> attributes, String name) {
		String trunc = truncate(name);
		for (Attribute att : attributes) {
			if (att.getName().equals(name)) {
				return "BADNAME";
			}
		}
		return trunc;
	}

	public static String truncate(String extra) {
		if (extra.endsWith("s") && !extra.endsWith("ss")) {
			extra = extra.substring(0, extra.length() - 1);
		}
		return extra;

	}
}
