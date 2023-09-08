DROP TABLE IF EXISTS response cascade;
DROP TABLE IF EXISTS question cascade;
DROP TABLE IF EXISTS quiz cascade;
DROP SEQUENCE IF EXISTS seq1;

CREATE SEQUENCE seq1 START WITH 1;

CREATE TABLE quiz (
                      id INT DEFAULT NEXT VALUE FOR seq1 PRIMARY KEY,
                      name VARCHAR(255) NOT NULL
);


CREATE TABLE question (
                          id INT DEFAULT NEXT VALUE FOR seq1 PRIMARY KEY,
                          topic VARCHAR(255) NOT NULL,
                          content VARCHAR(255) NOT NULL,
                          difficulty INTEGER NOT NULL,
                          quiz_id INT,
                          FOREIGN KEY (quiz_id) REFERENCES quiz ON DELETE CASCADE
);

CREATE TABLE response (
                          id INT DEFAULT NEXT VALUE FOR seq1 PRIMARY KEY,
                          text VARCHAR(255) NOT NULL,
                          correct BIT NOT NULL,
                          question_id INT,
                          FOREIGN KEY (question_id) REFERENCES question ON DELETE CASCADE
);

INSERT INTO quiz (id, name) VALUES (10, 'default');
INSERT INTO question (id, topic, content, difficulty, quiz_id) VALUES (20, 'math', '2+2', 1, 10);
INSERT INTO question (id, topic, content, difficulty, quiz_id) VALUES (21, 'math', '4*5', 3, 10);
INSERT INTO response (id, text, correct, question_id) VALUES (31, '5', false, 20);
INSERT INTO response (id, text, correct, question_id) VALUES (32, '4', true, 20);
INSERT INTO response (id, text, correct, question_id) VALUES (33, '11', false, 20);
