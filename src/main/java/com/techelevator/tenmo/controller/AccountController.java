package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.PrimaryAccountDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {
    private AccountDao accountDao;
    public AccountController(AccountDao accountDao){
        this.accountDao = accountDao;
    }

    //TODO: need to modify to get one account for the MVP
    @RequestMapping(path ="/account", method = RequestMethod.GET)
    public PrimaryAccountDTO getAccount(Principal principal){
        return accountDao.findAccountByUsername(principal.getName());
    }


}
