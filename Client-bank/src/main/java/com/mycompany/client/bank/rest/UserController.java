package com.mycompany.client.bank.rest;

import com.mycompany.client.bank.api.LibAppUserAndUserDetailsReply;
import com.mycompany.client.bank.api.PostRequstLibAuthorization;
import com.mycompany.client.bank.api.LibNotificationReply;
import com.mycompany.client.bank.api.PostRequest;
import com.mycompany.client.bank.api.User_Info_Reply;
import com.mycompany.client.bank.jpa.Account;
import com.mycompany.client.bank.jpa.Appuser;
import com.mycompany.client.bank.jpa.Notification;
import com.mycompany.client.bank.services.AccountMapper;
import com.mycompany.client.bank.services.AccountService;
import com.mycompany.client.bank.services.AppUserAndUserDetailsMapper;
import com.mycompany.client.bank.services.AppUserAndUserDetailsService;
import com.mycompany.client.bank.services.NotificationMapper;
import com.mycompany.client.bank.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
     @RequestMapping(path="/{username}/info",  method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User_Info_Reply getAppInfoByUserName(@PathVariable String username){
        User_Info_Reply reply=new User_Info_Reply();
        reply.UserName=username;
        for(Account user_acc : accountService.getUserAccounts(userService.getUserByName(username))){
        reply.account_list.add(accountMapper.fromInternal(user_acc));
        }
        reply.notif_size=notifService.getAllUserNotifications(userService.getUserByName(username)).size();
        reply.wallet=userService.getUserByName(username).getWallet();
        return reply;
    }
    // check_user method. If user exists return  LibAppUserAndUserDetailsReply
        @RequestMapping(path="/users/check_user",  method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	    public LibAppUserAndUserDetailsReply check_user(@RequestBody PostRequstLibAuthorization req){
        LibAppUserAndUserDetailsReply reply = new LibAppUserAndUserDetailsReply();
        try{
           Appuser au;
           au = userService.getUserByUsernameAndPassword(userMapper.toInternal(req).getUsername(),userMapper.toInternal(req).getPassword());
	   reply.users.add(userMapper.fromInternal(au));
        }catch(Exception e){
            reply.retcode = -1;
            reply.error_message = e.getMessage();
        }
        return reply;
    }
                @CrossOrigin(origins = "*")
	        @RequestMapping(path="/users/add",  method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LibAppUserAndUserDetailsReply addUser( @RequestBody PostRequest req){
        LibAppUserAndUserDetailsReply rep = new LibAppUserAndUserDetailsReply();
        try{
           Appuser au;
           au = userService.addUser(userMapper.toInternal(req.user));
           rep.users.add(userMapper.fromInternal(au));
        }catch(Exception e){
            rep.retcode = -1;
            rep.error_message = e.getMessage();
        }
        return rep;
    }
}
