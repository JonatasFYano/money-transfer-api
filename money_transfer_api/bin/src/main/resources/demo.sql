DROP TABLE IF EXISTS Account;

CREATE TABLE Account (AccountId LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
UserName VARCHAR(30),
TotalBalance DECIMAL(19,4)
);

CREATE UNIQUE INDEX idx_acc on Account(UserName);

INSERT INTO Account (UserName,TotalBalance) VALUES ('jonatas',500.7000);
INSERT INTO Account (UserName,TotalBalance) VALUES ('adriano',300.0000);
INSERT INTO Account (UserName,TotalBalance) VALUES ('enio',50.7000);
INSERT INTO Account (UserName,TotalBalance) VALUES ('alexandre',280.0000);
