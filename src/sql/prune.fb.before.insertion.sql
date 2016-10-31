DELETE FROM fb 
WHERE fb.id IN (SELECT fb.id 
FROM people 
JOIN fb  
ON fb.first = people.first  
AND fb.last = people.last); 
