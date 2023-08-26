package com.techelevator.dao;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.CreateTransactionDTO;
import com.techelevator.tenmo.model.TransactionDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

public class JdbcTransactionDaoTests extends BaseDaoTests{
    private JdbcTransactionDao sut3;
    private JdbcAccountDao sut2;

    private TransactionDTO sendTransExpect, requestTransExpect, transaction;
    private CreateTransactionDTO sendTransRequest, requestTransRequest;

    private Account myAccount;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcUserDao sut1 = new JdbcUserDao(jdbcTemplate);
        sut3 = new JdbcTransactionDao(jdbcTemplate, sut1);
        sut2 = new JdbcAccountDao(jdbcTemplate);

        sendTransExpect = new TransactionDTO(3003, new BigDecimal("50.00"), "bob", "user", "approved");
        sendTransRequest = new CreateTransactionDTO(new BigDecimal("50.00"), "user");

        requestTransExpect = new TransactionDTO(3004, new BigDecimal("50.00"), "user", "bob", "pending");
        requestTransRequest = new CreateTransactionDTO(new BigDecimal("50.00"), "user");

        transaction = new TransactionDTO(3001, new BigDecimal("100.00"), "bob", "user", "pending");
        myAccount = new Account();
        myAccount.setUsername("bob");
        myAccount.setBalance(new BigDecimal("1000"));
    }

    @Test
    public void getAllTransactionsByUsernameTest() {
        List<TransactionDTO> res = sut3.allTransactionsByUsername("bob");

        Assert.assertEquals(3001, res.get(0).getTransactionId());
        Assert.assertEquals(3002, res.get(1).getTransactionId());
    }

    @Test
    public void createSendTransaction() {
        TransactionDTO actualTransactionDTO = sut3.create(sendTransRequest, sut2.getAccount("bob"), true);
        Assert.assertEquals(sendTransExpect.getTransactionId(), actualTransactionDTO.getTransactionId());
        Assert.assertEquals(sendTransExpect.getFrom(), actualTransactionDTO.getFrom());
        Assert.assertEquals(sendTransExpect.getTo(), actualTransactionDTO.getTo());
        Assert.assertEquals(sendTransExpect.getTransferAmount(), actualTransactionDTO.getTransferAmount());
        Assert.assertEquals(sendTransExpect.getStatus(), actualTransactionDTO.getStatus());
    }

    @Test
    public void createRequestTransaction() {
        TransactionDTO actualTransactionDTO = sut3.create(requestTransRequest, sut2.getAccount("bob"), false);
        Assert.assertEquals(requestTransExpect.getTransactionId(), actualTransactionDTO.getTransactionId());
        Assert.assertEquals(requestTransExpect.getFrom(), actualTransactionDTO.getFrom());
        Assert.assertEquals(requestTransExpect.getTo(), actualTransactionDTO.getTo());
        Assert.assertEquals(requestTransExpect.getTransferAmount(), actualTransactionDTO.getTransferAmount());
        Assert.assertEquals(requestTransExpect.getStatus(), actualTransactionDTO.getStatus());
    }

    @Test
    public void createTransactionWithInvalidUser() {
        sendTransRequest.setTo("zombie");
        try {
            sut3.create(sendTransRequest, sut2.getAccount("bob"), true);
        } catch (Exception e) {
            String message = "One of these users do not exist: (bob / zombie). Check your spelling.";
            Assert.assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void createTransactionWithSameUser() {
        sendTransRequest.setTo("bob");
        try {
            sut3.create(sendTransRequest, sut2.getAccount("bob"), true);
        } catch (Exception e) {
            String message = "You cannot make a transaction to yourself.";
            Assert.assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void createTransactionWithAmountLargerThanBalance() {
        sendTransRequest.setTransferAmount(new BigDecimal(5000));
        try {
            sut3.create(sendTransRequest, sut2.getAccount("bob"), true);
        } catch (Exception e) {
            String message = "Insufficient funds.";
            Assert.assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void createTransactionWithNegativeAmount() {
        sendTransRequest.setTransferAmount(new BigDecimal(-100));
        try {
            sut3.create(sendTransRequest, sut2.getAccount("bob"), true);
        } catch (Exception e) {
            String message = "Insufficient funds.";
            Assert.assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void updateTransactionStatusWithRejectRequest() {
        TransactionDTO res = sut3.updateTransactionStatus(transaction, "reject", myAccount);

        Assert.assertEquals("reject", res.getStatus());
    }

    @Test
    public void updateTransactionStatusWithApproveRequest() {
        TransactionDTO res = sut3.updateTransactionStatus(transaction, "approved", myAccount);

        Assert.assertEquals("approved", res.getStatus());
    }
}
