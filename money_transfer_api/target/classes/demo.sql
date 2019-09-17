--This script is used for unit test cases, DO NOT CHANGE!

DROP TABLE IF EXISTS User;

CREATE TABLE User (UserId LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
 UserName VARCHAR(30) NOT NULL,
 EmailAddress VARCHAR(30) NOT NULL);

CREATE UNIQUE INDEX idx_ue on User(UserName,EmailAddress);

INSERT INTO User (UserName, EmailAddress) VALUES ('yangluo','yangluo@gmail.com');
INSERT INTO User (UserName, EmailAddress) VALUES ('qinfran','qinfran@gmail.com');
INSERT INTO User (UserName, EmailAddress) VALUES ('liusisi','liusisi@gmail.com');

DROP TABLE IF EXISTS Account;

CREATE TABLE Account (AccountId LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
UserName VARCHAR(30),
TotalBalance DECIMAL(19,4),
Currency VARCHAR(30)
);

CREATE UNIQUE INDEX idx_acc on Account(UserName,Currency);

INSERT INTO Account (UserName,TotalBalance,Currency) VALUES ('yangluo',100.0000,'USD');
INSERT INTO Account (UserName,TotalBalance,Currency) VALUES ('qinfran',200.0000,'USD');
INSERT INTO Account (UserName,TotalBalance,Currency) VALUES ('yangluo',500.0000,'EUR');
INSERT INTO Account (UserName,TotalBalance,Currency) VALUES ('qinfran',500.0000,'EUR');
INSERT INTO Account (UserName,TotalBalance,Currency) VALUES ('yangluo',500.0000,'GBP');
INSERT INTO Account (UserName,TotalBalance,Currency) VALUES ('qinfran',500.0000,'GBP');
