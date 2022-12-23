CREATE TABLE users(
	users_id serial PRIMARY KEY,
	username TEXT NOT NULL UNIQUE,
	pass TEXT NOT NULL,
	first_name TEXT NOT NULL,
	last_name TEXT NOT NULL,
	role_id_fk int REFERENCES roles(role_id),
	reimb_id_fk int REFERENCES reimb(reimb_id)
);

CREATE TABLE roles(
	role_id serial PRIMARY KEY, 
	user_role TEXT NOT NULL 
);

CREATE TABLE reimb(
	reimb_id serial PRIMARY KEY,
	reimb_amount int NOT NULL, 
	reimb_description TEXT NOT NULL,
	reimb_type_fk int REFERENCES reimb_type(reimb_type_id),
	reimb_status_fk int REFERENCES reimb_status(reimb_status_id)
);

CREATE TABLE reimb_type(
	reimb_type_id serial PRIMARY KEY, 
	reimb_types TEXT NOT NULL 
);

CREATE TABLE reimb_status(
	reimb_status_id serial PRIMARY KEY, 
	reimb_statuss TEXT NOT NULL  
);

INSERT INTO users(username, pass, first_name, last_name, role_id_fk)
VALUES('beard','Bboy','Mason','Bearden', 2), ('dav', 'passwo', 'David', 'Bowman', 1), ('ry', 'ryguy','Ryan','Bearden', 1);

INSERT INTO reimb (reimb_amount, reimb_description, reimb_type_fk, reimb_status_fk)
VALUES(100, 'Gas', 2, 1), (40, 'Hotel', 1, 1), (1008, 'Food', 1, 1);

INSERT INTO reimb_type(reimb_types)
VALUES('Cash'),('Card');

INSERT INTO reimb_status(reimb_statuss)
VALUES('Pending'),('Approved'),('Denied');

INSERT INTO roles(user_role)
VALUES('Employee'), ('Manager');

SELECT * FROM users;
SELECT * FROM reimb;
SELECT * FROM reimb_type;
SELECT * FROM reimb_status;
SELECT * FROM roles;


TRUNCATE TABLE users, reimb;

DROP TABLE reimb_type; 
DROP TABLE reimb_status;
DROP TABLE reimb; 
DROP TABLE users;
DROP TABLE roles; 