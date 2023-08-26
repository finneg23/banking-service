package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.exception.TransactionNotFoundException;
import com.techelevator.tenmo.model.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class JdbcTransactionDao implements TransactionDao{
    private JdbcTemplate jdbcTemplate;
    private JdbcUserDao jdbcUserDao;

    public JdbcTransactionDao(JdbcTemplate jdbcTemplate, JdbcUserDao jdbcUserDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcUserDao = jdbcUserDao;
    }



    @Override
    public List<TransactionDTO> allTransactionsByUsername(String username) {
        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        String sql1 = "SELECT transaction_id, amount, from_username, to_username FROM " +
        "transaction WHERE from_username = ? OR to_username = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql1, username, username);
            while (results.next()) {
                transactionDTOList.add(
                        new TransactionDTO(results.getInt("transaction_id"), results.getBigDecimal("amount"),
                                results.getString("from_username"), results.getString("to_username")));
            }
        } catch(CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the database.");
        } catch(DataIntegrityViolationException e) {
            throw new DaoException("The integrity of the data will be compromised.");
        }

        return transactionDTOList;
    }

    @Override
    public TransactionDTO create(TransactionDTO transaction, Account account) {
        TransactionDTO newTransaction = null;
        List<String> usernames = new ArrayList<>();
        List<UserDTO> objectsWithUsernames = jdbcUserDao.getAllUsername();
        String status = "";
        for (UserDTO users : objectsWithUsernames) {
            usernames.add(users.getUsername());
        }
        String sql = "INSERT INTO transaction (from_username, to_username, status, amount, timestamp) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING transaction_id;";
        try {
            if (!usernames.contains(transaction.getFrom()) || !usernames.contains(transaction.getTo())) {
                throw new DaoException("One of these users do not exist: (" + transaction.getFrom() + " / " + transaction.getTo() + "). Check your spelling.");
            }

            if (!transaction.getTo().equals(account.getUsername()) && !transaction.getFrom().equals(account.getUsername())) {
                throw new DaoException("You're not God. You can't make transfers happen between other people.");
            }

            if (transaction.getTo().equals(account.getUsername()) && transaction.getFrom().equals(account.getUsername())) {
                throw new DaoException("You cannot make a transaction to yourself.");
            }

            if (transaction.getTransferAmount().compareTo(account.getBalance()) > 0 || transaction.getTransferAmount().compareTo(BigDecimal.valueOf(0)) != 1) {
                throw new DaoException("Insufficient funds.");
            }

            if (transaction.getFrom().equals(account.getUsername())) {
                status = "approved";
            } else {status = "pending";}

            Integer newTransactionId = jdbcTemplate.queryForObject(sql, Integer.class,
                    transaction.getFrom(), transaction.getTo(), status, transaction.getTransferAmount(), new Date());

            newTransaction = getTransactionById(newTransactionId);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("The integrity of the data will be compromised.");
        } catch (NullPointerException e) {
            throw new DaoException("The account ID was not found.");
        }

        if(status.equals("approved")) {
            updateAccounts(transaction.getTo(), account.getUsername(), transaction.getTransferAmount());
        }
        return newTransaction;
    }

    private void updateAccounts(String receiver, String sender, BigDecimal amount) {
        String sqlReceiver = "UPDATE account AS a SET balance = balance + ? " +
                "FROM tenmo_user AS u WHERE a.user_id = u.user_id " +
                "AND u.username = ?";

        String sqlSender = "UPDATE account AS a SET balance = balance - ? " +
                "FROM tenmo_user AS u WHERE a.user_id = u.user_id " +
                "AND u.username = ?";

        try {
           jdbcTemplate.update(sqlReceiver, amount, receiver);
           jdbcTemplate.update(sqlSender, amount, sender);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("The integrity of the data will be compromised.");
        } catch (NullPointerException e) {
            throw new DaoException("The account ID was not found.");
        }
    }

    @Override
    public TransactionDTO getTransactionById(int transactionId) throws TransactionNotFoundException {
        String sql = "SELECT * FROM transaction WHERE transaction_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transactionId);
        if (results.next()) {
            return new TransactionDTO(results.getInt("transaction_id"), results.getBigDecimal("amount"),
                    results.getString("from_username"), results.getString("to_username"));
        }
        throw new TransactionNotFoundException("Transaction " + transactionId + " not found.");
    }



}

