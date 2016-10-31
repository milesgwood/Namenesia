package fb;

public class ParseCleaner {

	public static void clean() {
		for (Attribute a : Parser.attributes) {
			if (a.getName().equals("Family"))
				findFamilyTags(a);
		}
	}

	public static void findFamilyTags(Attribute family) {
		int pID;
		String truncated;
		String[] names;
		Contact famMem;
		ContactType type;
		
		for (String person : family.list) {
			type = ContactType.DEFAULT;
			truncated = person.substring(0, person.indexOf('('));
			truncated = truncated.trim();
			pID = Parser.findIDNum(truncated);
			names = truncated.split("\\s+");
			
			if(person.contains("(Cousin)")) type = ContactType.COUSIN;
			else if(person.contains("(Aunt)")) type = ContactType.RELATIVE;
			else if(person.contains("(Brother)")) type = ContactType.FAMILY;
			else if(person.contains("(Sister)")) type = ContactType.FAMILY;
			else if(person.contains("(Mother)")) type = ContactType.FAMILY;
			else if(person.contains("(Father)")) type = ContactType.FAMILY;
			else if(person.contains("(Uncle)")) type = ContactType.RELATIVE;
			else if(person.contains("(Grandfather)")) type = ContactType.RELATIVE;
			else if(person.contains("(Grandmother)")) type = ContactType.RELATIVE;
			else{
				System.out.println("UNHANDLED CONTACT TYPE" + person);
			}
			
			Parser.contacts.remove(Parser.getContact(pID));
			famMem = new Contact(names, pID);
			famMem.setType(type);
			//famMem.printContact();
			Parser.contacts.add(famMem);
		}
	}
}
