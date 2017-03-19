package com.mycompany.client.bank.rest;

import com.mycompany.client.bank.api.AddRequest;
import com.mycompany.client.bank.api.LibAppUserAndUserDetailsReply;
import com.mycompany.client.bank.api.LibBankReply;
import com.mycompany.client.bank.api.LibTransactionReply;
import com.mycompany.client.bank.jpa.Appuser;
import com.mycompany.client.bank.jpa.Bank;
import com.mycompany.client.bank.jpa.Transaction;
import com.mycompany.client.bank.services.AppUserAndUserDetailsMapper;
import com.mycompany.client.bank.services.AppUserAndUserDetailsService;
import com.mycompany.client.bank.services.BankMapper;
import com.mycompany.client.bank.services.BankService;
import com.mycompany.client.bank.services.TransactionMapper;
import com.mycompany.client.bank.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserController {
	//checkAuthorization()	
        //RegisrateNewUser()
        //GetInfoForUserPage()
        //Notification()
	    @Autowired         
     AppUserAndUserDetailsService userService;
    @Autowired
    AppUserAndUserDetailsMapper userMapper;    
    
    
    @RequestMapping(path="/users/all",  method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LibAppUserAndUserDetailsReply getAllUsers(){
        LibAppUserAndUserDetailsReply reply = new LibAppUserAndUserDetailsReply();
        for(Appuser au: userService.getAllUsers()){
           reply.users.add(userMapper.fromInternal(au));    
        }
        return reply;
    }
        @RequestMapping(path="/users/security/{username}/{password}",  method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LibAppUserAndUserDetailsReply getUserById(@PathVariable String username,@PathVariable String password ){
        LibAppUserAndUserDetailsReply reply = new LibAppUserAndUserDetailsReply();
        reply.users.add(userMapper.fromInternal(userService.getUserByUsernameAndPassword(username, password)));
        return reply;
    }
}
