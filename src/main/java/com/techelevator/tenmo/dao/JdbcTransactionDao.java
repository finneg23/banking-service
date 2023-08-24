package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.exception.TransactionNotFoundException;
import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.TransactionDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcTransactionDao implements TransactionDao{
    private JdbcTemplate jdbcTemplate;
    public JdbcTransactionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Transaction> allTransactions() {
        return null;
    }

    @Override
    public List<TransactionDTO> allTransactionsByUsername(String username) {
        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        String sql1 = "SELECT transaction.transaction_id, transaction.amount, from_table.from_user, to_table.to_user " +
                "FROM transaction " +
                "JOIN ( SELECT transaction_id, amount, tenmo_user.username as from_user " +
                "FROM  transaction JOIN tenmo_user ON tenmo_user.user_id = transaction.from_user_id " +
                "WHERE username ILIKE ?) AS from_table ON from_table.amount = transaction.amount " +
                "JOIN ( SELECT transaction_id, amount, tenmo_user.username as to_user " +
                "FROM  transaction JOIN tenmo_user ON tenmo_user.user_id = transaction.to_user_id) " +
                "AS to_table ON from_table.amount = to_table.amount;";
        String sql2 = "SELECT transaction.transaction_id, transaction.amount, from_table.from_user, to_table.to_user " +
                "FROM transaction " +
                "JOIN ( SELECT transaction_id, amount, tenmo_user.username as from_user " +
                "FROM  transaction JOIN tenmo_user ON tenmo_user.user_id = transaction.from_user_id) " +
                "AS from_table ON from_table.amount = transaction.amount " +
                "JOIN ( SELECT transaction_id, amount, tenmo_user.username as to_user " +
                "FROM  transaction JOIN tenmo_user ON tenmo_user.user_id = transaction.to_user_id " +
                "WHERE username ILIKE ?) AS to_table ON from_table.amount = to_table.amount;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql1, username);
            while (results.next()) {
                transactionDTOList.add(
                        new TransactionDTO(results.getInt("transaction_id"), results.getBigDecimal("amount"),
                                results.getString("from_user"), results.getString("to_user")));
            }
        } catch(CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to database.");
        } catch(DataIntegrityViolationException e) {
            throw new DaoException("The integrity of the data will be compromised.");
        }
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql2, username);
            while (results.next()) {
                transactionDTOList.add(
                        new TransactionDTO(results.getInt("transaction_id"), results.getBigDecimal("amount"),
                                results.getString("from_user"), results.getString("to_user")));
            }
        } catch(CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to database.");
        } catch(DataIntegrityViolationException e) {
            throw new DaoException("The integrity of the data will be compromised.");
        }
        return transactionDTOList;
    }

    @Override
    public TransactionDTO createTransaction(Transaction transaction) {
        TransactionDTO newTransaction = null;
        Integer newTransactionId;
        String sql =    "INSERT INTO transaction (from_user_id, to_user_id, " +
                        "status, amount, timestamp)" +
                        "VALUES (?, ?, ?, ?, ?) RETURNING transaction_id;";
        try {
            if (transaction.getToUserId() == transaction.getFromUserId()) {
                throw new DaoException("You cannot make a transaction to yourself");
            }
            newTransactionId =  jdbcTemplate.queryForObject(sql, Integer.class,
                    transaction.getFromUserId(), transaction.getToUserId(), transaction.getStatus(),
                    transaction.getAmount(), transaction.getTimestamp());
            newTransaction = getTransactionById(newTransactionId);
        } catch(CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to database.");
        } catch(DataIntegrityViolationException e) {
            throw new DaoException("The integrity of the data will be compromised.");
        } catch(NullPointerException e) {
            throw new DaoException("The account id was found to be null.");
        }
        return newTransaction;
    }

    @Override
    public TransactionDTO getTransactionById(int transactionId) throws TransactionNotFoundException {
        String sql = "SELECT * FROM transaction WHERE transaction_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transactionId);
        if (results.next()) {
            return new TransactionDTO(results.getInt("transaction_id"), results.getBigDecimal("amount"),
                    results.getString("to_user_id"), results.getString("from_user_id"));
        }
        throw new TransactionNotFoundException("Transaction" + transactionId + "not found.");
    }

    private Transaction mapRowToTransaction(SqlRowSet results) {
        Transaction transaction = null;
        transaction.setFromUserId(results.getInt("from_user_id"));
        transaction.setToUserId(results.getInt("to_user_id"));
        transaction.setAmount(results.getBigDecimal("amount"));
        transaction.setStatus(results.getString("status"));
        transaction.setTimestamp(results.getDate("timestamp").toLocalDate());
        return transaction;
    }


}


//        String sql =    "SELECT transaction_id, amount, username" +
//                        "(SELECT username FROM tenmo_user " +
//                        "JOIN transaction ON tenmo_user.user_id = transaction.from_user_id " +
//                        "WHERE username = ?) AS to_username";