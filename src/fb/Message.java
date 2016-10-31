package fb;

public class Message {
	private int pID;
	private String name;
	private String date;
	private String message;
	private static int mIDcount = 0;
	private int mID;
	private int threadID;
	
	Message(int pID, String name,  String date, String message, int threadID)
	{
		this.pID = pID;
		this.name = name;
		this.date = date;
		this.message = message;
		this.threadID = threadID;
		this.mID = mIDcount++;
	}
	
	public String toString(int x)
	{
		String result;
		result = this.threadID + ":" + this.mID + " " + this.name + "\t\t" + date + "\t\t";
		if(this.message.length() > x)
		{
			result += message.substring(0, x-1);
		}
		else
		{
			result += message;
		}
		return result;
	}
	
	//change the size of the value in to String to change the length of the message returned
	public void printMessage()
	{
		System.out.println(this.toString(40));
	}
	
	public int getThreadID()
	{
		return this.threadID;
	}
	public int getMessageID()
	{
		return this.mID;
	}
	public String getMessage()
	{
		return this.message;
	}
	public String getDate()
	{
		return this.date;
	}
	
	public int messageSenderID()
	{
		return Parser.findIDNum(this.name);
	}
	
	public String messageSenderName()
	{
		return this.name;
	}
}
