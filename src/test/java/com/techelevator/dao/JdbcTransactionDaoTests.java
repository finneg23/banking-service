package com.techelevator.dao;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.TransactionDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

public class JdbcTransactionDaoTests extends BaseDaoTests{
    private JdbcTransactionDao sut3;
    private JdbcUserDao sut1;
    private JdbcAccountDao sut2;
    private final TransactionDTO TEST_TRANSACTIONDTO_1 =
            new TransactionDTO(3003, new BigDecimal("50.00"), "bob", "user");

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut1 = new JdbcUserDao(jdbcTemplate);
        sut3 = new JdbcTransactionDao(jdbcTemplate, sut1);
        sut2 = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void transactionCreatedCorrectly() {
        TransactionDTO actualTransactionDTO = sut3.create(TEST_TRANSACTIONDTO_1, sut2.getAccount("bob"));
        Assert.assertEquals(TEST_TRANSACTIONDTO_1.getTransactionId(), actualTransactionDTO.getTransactionId());
        Assert.assertEquals(TEST_TRANSACTIONDTO_1.getFrom(), actualTransactionDTO.getFrom());
        Assert.assertEquals(TEST_TRANSACTIONDTO_1.getTo(), actualTransactionDTO.getTo());
        Assert.assertEquals(TEST_TRANSACTIONDTO_1.getTransferAmount(), actualTransactionDTO.getTransferAmount());
    }

    @Test
    public void getAllTransactionsByUsernameTest() {
        List<TransactionDTO> res = sut3.allTransactionsByUsername("bob");

        Assert.assertEquals(3001, res.get(0).getTransactionId());
        Assert.assertEquals(3002, res.get(1).getTransactionId());
    }
}
