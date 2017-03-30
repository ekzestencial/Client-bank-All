/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.rest;

/**
 *
 * @author alex
 */

import com.mycompany.client.bank.api.LoginReply;
import com.mycompany.client.bank.api.PostRequest;
import com.mycompany.client.bank.api.PostRequstLibAuthorization;
import com.mycompany.client.bank.auth.AuthUser;
import com.mycompany.client.bank.auth.TokenProvider;
import com.mycompany.client.bank.jpa.Appuser;
import com.mycompany.client.bank.services.AppUserAndUserDetailsMapper;
import com.mycompany.client.bank.services.AppUserAndUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author al
 */
@RestController
public class AuthController {
private static final Logger logger =  LoggerFactory.getLogger(AuthController.class);
    @Autowired    
    AppUserAndUserDetailsService userService;
    @Autowired
    AppUserAndUserDetailsMapper userMapper;    
    @Autowired
    TokenProvider tokenProvider;
    
    @RequestMapping(path="/auth",  method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LoginReply authUser( @RequestBody PostRequstLibAuthorization req){
        LoginReply rep = new LoginReply();
           Appuser au; 
           au = userService.authUser(req.login,req.password);
           if(au!=null){
              String token = tokenProvider.newToken(au);
              tokenProvider.put(token, new AuthUser(au));
              rep.user = userMapper.fromInternal(au);
              rep.token = token;
           }else{
              logger.error("Error loggin in user. User: "+req.login);
           }
        return rep;
    }
    
}