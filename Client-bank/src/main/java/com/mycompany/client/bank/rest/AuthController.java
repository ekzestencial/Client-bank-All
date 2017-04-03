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
import com.mycompany.client.bank.auth.SecretProvider;
import com.mycompany.client.bank.auth.SecuritySecret;
import com.mycompany.client.bank.auth.TokenProvider;
import com.mycompany.client.bank.jpa.Appuser;
import com.mycompany.client.bank.services.AppUserAndUserDetailsMapper;
import com.mycompany.client.bank.services.AppUserAndUserDetailsService;
import com.mycompany.client.bank.utils.PermisChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Autowired
    SecretProvider secretProvider;
    
    @RequestMapping(path="/auth",  method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LoginReply authUser( @RequestBody PostRequstLibAuthorization req){
        LoginReply rep = new LoginReply();
           Appuser au; 
	      req.password=AppUserAndUserDetailsService.digest(req.password);
	      System.out.println("pass = " + req.password);
           au = userService.authUser(req.login,req.password);
           System.out.println("USER = " + au);
           if(au!=null){
              String token = tokenProvider.newToken(au);
              tokenProvider.put(token, new AuthUser(au));
              rep.user = userMapper.fromInternal(au);
              rep.token = token;
              SecuritySecret tmpS = secretProvider.GenerateGandP(tokenProvider.get(token));
              rep.G=tmpS.getG().toString();
              rep.P=tmpS.getP().toString();
              rep.ServerPublicKey=tmpS.getPublicKey();
           }else{
              logger.error("Error loggin in user. User: "+req.login);
           }
        return rep;
    }
    @RequestMapping(path="/auth/secret/{user}/{publicKey}",  method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LoginReply SetSecretForUser( @PathVariable String publicKey, @PathVariable String user){
        if(!PermisChecker.ForUser(user)) return null;
        LoginReply rep = new LoginReply();
        rep.ServerPublicKey=secretProvider.GenerateNewSecret(PermisChecker.GetAuthUser(), publicKey);
        return rep;
    }
}