package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.exception.TransactionNotFoundException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.CreateTransactionDTO;
import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.TransactionDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class JdbcTransactionDao implements TransactionDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcTransactionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public List<TransactionDTO> allTransactionsByUsername(String username) {
        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        String sql1 = "SELECT transaction_id, amount, from_username, to_username FROM " +
        "transaction WHERE from_username = ? OR to_username =?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql1, username, username);
            while (results.next()) {
                transactionDTOList.add(
                        new TransactionDTO(results.getInt("transaction_id"), results.getBigDecimal("amount"),
                                results.getString("from_username"), results.getString("to_username")));
            }
        } catch(CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to database.");
        } catch(DataIntegrityViolationException e) {
            throw new DaoException("The integrity of the data will be compromised.");
        }

        return transactionDTOList;
    }

    @Override
    public TransactionDTO create(CreateTransactionDTO transaction, Account account) {

        TransactionDTO newTransaction = null;

        String sql = "INSERT INTO transaction (from_username, to_username, status, amount, timestamp) " +
                "VALUES (?, ?, 'approved', ?, ?) RETURNING transaction_id;";

        try {
            if (transaction.getTo().equals(account.getUsername())) {
                throw new DaoException("You cannot make a transaction to yourself");
            }

            if (transaction.getTransferAmount().compareTo(account.getBalance()) == 1) {
                throw new DaoException("Balance is not enough to transfer");
            }

            Integer newTransactionId = jdbcTemplate.queryForObject(sql, Integer.class,
                    account.getUsername(), transaction.getTo(), transaction.getTransferAmount(), new Date());

            newTransaction = getTransactionById(newTransactionId);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("The integrity of the data will be compromised.");
        } catch (NullPointerException e) {
            throw new DaoException("The account id was found to be null.");
        }

        updateAccounts(transaction.getTo(), account.getUsername(), transaction.getTransferAmount());

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
            throw new DaoException("The account id was found to be null.");
        }
    }

    @Override
    public TransactionDTO getTransactionById(int transactionId) throws TransactionNotFoundException {
        String sql = "SELECT * FROM transaction WHERE transaction_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transactionId);
        if (results.next()) {
            return new TransactionDTO(results.getInt("transaction_id"), results.getBigDecimal("amount"),
                    results.getString("to_username"), results.getString("from_username"));
        }
        throw new TransactionNotFoundException("Transaction" + transactionId + "not found.");
    }



}

