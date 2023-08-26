package com.techelevator.dao;


import com.techelevator.tenmo.controller.AuthenticationController;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.RegisterUserDTO;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.math.BigDecimal;


public class JdbcUserDaoTests extends BaseDaoTests{

    private JdbcUserDao sut;
    private JdbcAccountDao sut2;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcUserDao(jdbcTemplate);
        sut2 = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void createNewUser() {
        boolean userCreated = sut.create("TEST_USER","test_password");
        Assert.assertTrue(userCreated);
        User user = sut.findByUsername("TEST_USER");
        Assert.assertEquals("TEST_USER", user.getUsername());
    }

    @Test
    public void newUserBalanceIsOneThousand() {
        BigDecimal expectedNewBalance = new BigDecimal("1000.00");
        sut.create("TEST_USER", "test_password");
        BigDecimal actualNewBalance = sut2.getAccount("TEST_USER").getBalance();
        Assert.assertEquals(expectedNewBalance, actualNewBalance);
    }


}
