INSERT INTO fb_with_scores(id, first, middle, last, email, type, score) 
SELECT id, first, middle, last, email, type, mscore*multiplier as score 
from fb 
natural join fb_typemultipliers 
left join (select count(*) as mScore , senderID 
from fb_messages 
group by senderID 
order by mScore) ON id = senderID;
