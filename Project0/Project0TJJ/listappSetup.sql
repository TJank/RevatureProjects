
create table user_account(
id serial primary key,
username varchar unique not null,
password varchar not null
);

create table list(
id serial primary key,
title varchar not null,
sharable boolean not null
);

create table user_account_list(
user_id int references user_account(id) not null,
list_id int references list(id) on delete cascade
);

create table list_item(
id serial primary key,
item_title varchar not null,
--details varchar not null,
list_id int references list(id) on delete cascade
);

commit;


--creating & testing  dml statements
SELECT L.id, L.title, L.sharable FROM list as L INNER JOIN user_account_list as UL ON L.id = UL.list_id WHERE UL.user_id = 1; 

SELECT 
    L.id,
    L.title,
    L.sharable

FROM 
    list as L
        INNER JOIN 
    user_account_list as UL
        ON L.id = UL.list_id
    
WHERE 
    UL.user_id = 1; 


SELECT L.id, L.title, L.sharable, LI.id, LI.item_title FROM list as L INNER JOIN user_account_list as UL ON L.id = UL.list_id full join list_item as LI on L.id = LI.list_id WHERE L.id = 1 and UL.user_id = 1;   

SELECT 
--    L.id,
--    L.title,
--    L.sharable,
    LI.id,
    LI.item_title

FROM 
    list as L
        INNER JOIN 
    user_account_list as UL
        ON L.id = UL.list_id

        full JOIN
    list_item as LI
        on L.id = LI.list_id
WHERE 
    L.id = 1
    	AND
    UL.user_id = 1;


update list as L set sharable = true from user_account_list as UL where L.id = 9 and UL.list_id = 9 and UL.user_id = 1
		
		
delete from list where id = 1;
	

SELECT L.id, L.title, L.sharable FROM list as L WHERE L.sharable = true order by L.title;

select LI.id, LI.item_title FROM list as L INNER JOIN user_account_list as UL ON L.id = UL.list_id full join list_item as LI on L.id = LI.list_id WHERE L.sharable = true  order by L.title;


