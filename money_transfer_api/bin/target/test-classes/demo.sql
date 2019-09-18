DROP TABLE IF EXISTS Account;

CREATE TABLE Account (AccountId LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
UserName VARCHAR(30),
TotalBalance DECIMAL(19,4)
);

CREATE UNIQUE INDEX idx_acc on Account(UserName);

INSERT INTO Account (UserName,TotalBalance) VALUES ('marcelo',110.7000);
INSERT INTO Account (UserName,TotalBalance) VALUES ('cristiano',200.0000);
INSERT INTO Account (UserName,TotalBalance) VALUES ('jonatas',300.0000);
INSERT INTO Account (UserName,TotalBalance) VALUES ('jonatasyano',350.0000);
