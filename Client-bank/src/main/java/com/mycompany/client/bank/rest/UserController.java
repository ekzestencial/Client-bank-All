package com.mycompany.client.bank.rest;

import com.mycompany.client.bank.api.LibAccount;
import com.mycompany.client.bank.api.LibAccountReply;
import com.mycompany.client.bank.api.LibAppUserAndUserDetailsReply;
import com.mycompany.client.bank.api.PostRequstLibAuthorization;
import com.mycompany.client.bank.api.LibNotificationReply;
import com.mycompany.client.bank.api.PostRequest;
import com.mycompany.client.bank.api.User_Info_Reply;
import com.mycompany.client.bank.auth.AuthUser;
import com.mycompany.client.bank.auth.AuthorityName;
import com.mycompany.client.bank.jpa.Account;
import com.mycompany.client.bank.jpa.Appuser;
import com.mycompany.client.bank.jpa.Bank;
import com.mycompany.client.bank.jpa.Notification;
import com.mycompany.client.bank.jpa.Userdetails;
import com.mycompany.client.bank.services.AccountMapper;
import com.mycompany.client.bank.services.AccountService;
import com.mycompany.client.bank.services.AppUserAndUserDetailsMapper;
import com.mycompany.client.bank.services.AppUserAndUserDetailsService;
import com.mycompany.client.bank.services.BankService;
import com.mycompany.client.bank.services.NotificationMapper;
import com.mycompany.client.bank.services.NotificationService;
import com.mycompany.client.bank.utils.PermisChecker;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

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
    @Autowired
    NotificationService notifService;
    @Autowired
    NotificationMapper notifMapper;
    @Autowired
    AccountService accountService;
    @Autowired
    BankService bankService;
    @Autowired
    AccountMapper accountMapper;
    
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
    
    @RequestMapping(path="/{username}/notifications",  method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LibNotificationReply getNotifications(@PathVariable String username){
         LibNotificationReply reply = new LibNotificationReply();
        Appuser tmp_user=userService.getUserByName(username);
        if(tmp_user==null)return null;
        for(Notification tmp_notif : notifService.getAllUserNotifications(tmp_user)){
            reply.notifications.add(notifMapper.fromInternal(tmp_notif));
        }
        return reply;
    }
     @Secured({"ROLE_USER"})
     @RequestMapping(path="/users/{username}/sliderInfo",  method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User_Info_Reply getAppInfoByUserName(@PathVariable String username){
        if(!PermisChecker.ForUser(username)) return null;
        User_Info_Reply reply=new User_Info_Reply();
        userService.getUserByName(username).setLastActivity(Date.from(Instant.now()));
        List<Notification> lst=notifService.getAllUserNotifications(userService.getUserByName(username));
        for(Notification notif : lst){
        if(notif.getIsChecked())lst.remove(notif);
        }
        reply.notif_size=lst.size();
        reply.wallet=userService.getUserByName(username).getWallet();
        reply.currentTime=Date.from(Instant.now()).toString();
        Userdetails ud=userService.getUserByName(username).getUserdetails();
        reply.FullName=ud.getFirstName() + " " + ud.getLastName();
        return reply;
    }
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(path="/users/{username}/accounts", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LibAccountReply getAccountsByUserName(@PathVariable String username){
    if(!PermisChecker.ForUserAndAdmin(username)) return null;
    LibAccountReply reply = new LibAccountReply();
        List<Account> lst=accountService.getUserAccounts(userService.getUserByName(username));
        for(Account account : lst){
        LibAccount tempAcc=accountMapper.fromInternal(account);
        Bank tempBank=bankService.findByBankId(account.getBankId().getBankId());
        tempAcc.bankName=tempBank.getName();
        tempAcc.CreditPersent=String.valueOf(tempBank.getCreditPersent());
        tempAcc.DepositPersent=String.valueOf(tempBank.getDepositPersent());
        reply.accounts.add(tempAcc);
        }
        return reply;
    }
    // check_user method. If user exists return  LibAppUserAndUserDetailsReply
        @RequestMapping(path="/users/check_user",  method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	    public LibAppUserAndUserDetailsReply check_user(@RequestBody PostRequest req){
        LibAppUserAndUserDetailsReply reply = new LibAppUserAndUserDetailsReply();
        try{
           Appuser au;
           au = userService.getUserByUsernameAndPassword(req.user.login, req.user.password);
	   reply.users.add(userMapper.fromInternal(au));
        }catch(Exception e){
            reply.retcode = -1;
            reply.error_message = e.getMessage();
        }
        return reply;
    }
	        @RequestMapping(path="/users/byid/{userid}",  method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LibAppUserAndUserDetailsReply getUserById(@PathVariable Long userid){
        LibAppUserAndUserDetailsReply reply = new LibAppUserAndUserDetailsReply();
        reply.users.add(userMapper.fromInternal(userService.getUserById(userid)));
        return reply;
    }
	        @RequestMapping(path="/users/add",  method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LibAppUserAndUserDetailsReply addUser( @RequestBody PostRequest req){
        LibAppUserAndUserDetailsReply rep = new LibAppUserAndUserDetailsReply();
        try{
           Appuser au;
           if(req.user.user_id!=null && userService.getUserById(Long.valueOf(req.user.user_id))!=null) throw new Exception("This user already exist in database!");
	   au=userMapper.toInternal(req.user);
	   String password=au.getPassword();
	   au.setPassword(AppUserAndUserDetailsService.digest(password));
	   System.out.println(au);
           au = userService.addUser(au);
           rep.users.add(userMapper.fromInternal(au));
        }catch(Exception e){
            rep.retcode = -1;
            rep.error_message = e.getMessage();
        }
        return rep;
    }
}
