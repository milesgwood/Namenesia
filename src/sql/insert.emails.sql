INSERT INTO tags (id, tag) SELECT id, email FROM fb_with_scores WHERE email IS NOT NULL; 

