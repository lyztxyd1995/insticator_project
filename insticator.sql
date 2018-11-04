CREATE DATABASE insticator;
USE insticator;

CREATE TABLE users(
	username VARCHAR(50) PRIMARY KEY,
	password VARCHAR(50) NOT NULL,
	points INT NOT NULL
);


CREATE TABLE administrator(
	username VARCHAR(50) PRIMARY KEY,
	password VARCHAR(50) NOT NULL
);

CREATE TABLE questions(
	question_id INT PRIMARY KEY AUTO_INCREMENT,
	question_type INT NOT NULL,
	content VARCHAR(255) NOT NULL
);

CREATE TABLE user_record(
	username VARCHAR(50) NOT NULL,
	question_id INT NOT NULL,
	primary key (username, question_id)
);

CREATE TABLE choice(
	choice_id INT PRIMARY KEY AUTO_INCREMENT,
	question_id INT NOT NULL,
	content VARCHAR(255) NOT NULL,
	is_answer BOOLEAN NOT NULL
);

CREATE TABLE matric_item(
	item_id INT PRIMARY KEY AUTO_INCREMENT,
	item_name VARCHAR(255) NOT NULL,
	question_id INT NOT NULL
);

CREATE TABLE matric_item_default_choice(
	choice_id INT PRIMARY KEY AUTO_INCREMENT,
	matric_item_id INT NOT NULL,
	content VARCHAR(255) NOT NULL
);

INSERT INTO questions
(question_type, content)
VALUES
(1,'checkbox');

INSERT INTO choice
(question_id, content, is_answer)
VALUES
(35,'first',true);

INSERT INTO choice
(question_id, content, is_answer)
VALUES
(35,'second',true);

INSERT INTO choice
(question_id, content, is_answer)
VALUES
(35,'third',false);