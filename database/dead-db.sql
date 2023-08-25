SELECT * FROM tenmo_user;
SELECT * FROM account;
-- DELETE FROM tenmo_user WHERE user_id = 1002;

SELECT tenmo_user.username, balance  
                FROM account  
                JOIN tenmo_user ON tenmo_user.user_id = account.user_id
                WHERE tenmo_user.username ILIKE 'admin';
				
SELECT * FROM transaction;

INSERT INTO transaction (from_user_id, to_user_id, status, amount, timestamp)
VALUES (1001, 1003, 'pending', 100, CURRENT_DATE);
INSERT INTO transaction (from_user_id, to_user_id, status, amount, timestamp)
VALUES (1003, 1001, 'pending', 150, CURRENT_DATE);

DELETE FROM transaction WHERE transaction_id = 3002;

SELECT transaction_id, amount, 
(SELECT username FROM tenmo_user JOIN transaction ON 
 tenmo_user.user_id = transaction.from_user_id WHERE username = 'user')
 AS from_username,
 (SELECT username FROM tenmo_user JOIN transaction ON 
 tenmo_user.user_id = transaction.to_user_id WHERE username = 'admin')
 AS to_username
 FROM transaction;
 
 SELECT transaction_id, amount, 
(SELECT username FROM tenmo_user JOIN transaction ON 
 tenmo_user.user_id = transaction.from_user_id WHERE username = 'admin')
 AS from_username,
 (SELECT username FROM tenmo_user JOIN transaction ON 
 tenmo_user.user_id = transaction.to_user_id WHERE username = 'user')
 AS to_username
 FROM transaction;

SELECT * FROM transaction WHERE from_user_id = 1001 or to_user_id = 1001;

SELECT transaction.transaction_id, transaction.amount, from_table.from_user, to_table.to_user
FROM transaction
JOIN ( SELECT transaction_id, amount, tenmo_user.username as from_user FROM  transaction JOIN tenmo_user ON tenmo_user.user_id = transaction.from_user_id
WHERE username ILIKE 'admin') AS from_table ON from_table.amount = transaction.amount
JOIN ( SELECT transaction_id, amount, tenmo_user.username as to_user FROM  transaction JOIN tenmo_user ON tenmo_user.user_id = transaction.to_user_id
WHERE username ILIKE 'user') AS to_table ON from_table.amount = to_table.amount;

SELECT transaction.transaction_id, transaction.amount, from_table.from_user, to_table.to_user
FROM transaction
JOIN ( SELECT transaction_id, amount, tenmo_user.username as from_user FROM  transaction JOIN tenmo_user ON tenmo_user.user_id = transaction.from_user_id
WHERE username ILIKE 'admin') AS from_table ON from_table.amount = transaction.amount
JOIN ( SELECT transaction_id, amount, tenmo_user.username as to_user FROM  transaction JOIN tenmo_user ON tenmo_user.user_id = transaction.to_user_id)
AS to_table ON from_table.amount = to_table.amount;

SELECT transaction.transaction_id, transaction.amount, from_table.from_user, to_table.to_user
FROM transaction
JOIN ( SELECT transaction_id, amount, tenmo_user.username as from_user FROM  transaction JOIN tenmo_user ON tenmo_user.user_id = transaction.from_user_id)
AS from_table ON from_table.amount = transaction.amount
JOIN ( SELECT transaction_id, amount, tenmo_user.username as to_user FROM  transaction JOIN tenmo_user ON tenmo_user.user_id = transaction.to_user_id
WHERE username ILIKE 'admin') AS to_table ON from_table.amount = to_table.amount;