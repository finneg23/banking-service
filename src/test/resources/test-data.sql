BEGIN TRANSACTION;

DROP TABLE IF EXISTS tenmo_user, account;

DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id;

-- Sequence to start user_id values at 1001 instead of 1
CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

CREATE TABLE tenmo_user (
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);

-- Sequence to start account_id values at 2001 instead of 1
-- Note: Use similar sequences with unique starting values for additional tables
CREATE SEQUENCE seq_account_id
  INCREMENT BY 1
  START WITH 2001
  NO MAXVALUE;

CREATE TABLE account (
	account_id int NOT NULL DEFAULT nextval('seq_account_id'),
	user_id int NOT NULL,
	balance decimal(13, 2) NOT NULL,
	CONSTRAINT PK_account PRIMARY KEY (account_id),
	CONSTRAINT FK_account_tenmo_user FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id)
);

CREATE SEQUENCE seq_transaction_id
  INCREMENT BY 1
  START WITH 3001
  NO MAXVALUE;

CREATE TABLE transaction (
    transaction_id int NOT NULL DEFAULT nextval('seq_transaction_id'),
    from_username varchar(50) NOT NULL,
    to_username varchar(50) NOT NULL,
    status varchar(10) NOT NULL,
    amount numeric(13, 2) NOT NULL CHECK (amount > 0), --positive
    timestamp date NOT NULL,
    CONSTRAINT PK_transaction PRIMARY KEY (transaction_id),
    CONSTRAINT FK_transaction_tenmo_user_to FOREIGN KEY (to_username) REFERENCES tenmo_user (username),
    CONSTRAINT FK_transaction_tenmo_user_from FOREIGN KEY (from_username) REFERENCES tenmo_user (username)
);

INSERT INTO tenmo_user (username, password_hash)
VALUES ('bob', '$2a$10$G/MIQ7pUYupiVi72DxqHquxl73zfd7ZLNBoB2G6zUb.W16imI2.W2'),
       ('user', '$2a$10$Ud8gSvRS4G1MijNgxXWzcexeXlVs4kWDOkjE7JFIkNLKEuE57JAEy'),
       ('test1', '$2a$10$Ud8gSvRS4G1MijNgxXWzcexeXlVs4kWDOkjE7JFIkNLKEuE57JAEy');

INSERT INTO account (user_id, balance)
VALUES ('1001', '1000'),
       ('1002', '1000');

INSERT INTO transaction (from_username, to_username, status, amount, timestamp)
VALUES  ('bob', 'user', 'pending', 100.00, CURRENT_DATE),
        ('user', 'bob', 'approved', 100.00, CURRENT_DATE);

COMMIT;