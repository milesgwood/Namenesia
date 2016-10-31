package fb;

public enum ContactType {
	FAMILY (10),
	BESTFRIEND(9),
	SO (8),
	OLDFRIEND (6),
	COUSIN (4),
	RELATIVE (3),
	FRIEND (2),
	DEFAULT (1),
	OWNER(0);
	
	private final int importance;
	
	ContactType(int i)
	{
		this.importance = i;
	}
	
	public int getImportance()
	{
		return this.importance;
	}
}
