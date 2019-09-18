DROP TABLE IF EXISTS Account;

CREATE TABLE Account (AccountId LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
UserName VARCHAR(30),
TotalBalance DECIMAL(19,4)
);

CREATE UNIQUE INDEX idx_acc on Account(UserName);

INSERT INTO Account (UserName,TotalBalance) VALUES ('yangluo',110.7000);
INSERT INTO Account (UserName,TotalBalance) VALUES ('qinfran',200.0000);
