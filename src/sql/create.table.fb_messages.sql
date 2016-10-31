CREATE TABLE IF NOT EXISTS fb_messages(
	tID INT, 
	mID INT PRIMARY KEY, 
	date VARCHAR(45), 
	senderID INT, 
	message TEXT);
