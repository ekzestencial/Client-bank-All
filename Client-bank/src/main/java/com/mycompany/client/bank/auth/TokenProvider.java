/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.auth;

/**
 *
 * @author alex
 */
import com.hazelcast.core.HazelcastInstance;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author al
 */
@Service
public class TokenProvider {
 @Autowired
 private HazelcastInstance instance;
 
 public void put(String token, AuthUser aauth ){
     instance.getMap("token-cache").put(token, aauth);
 }
 public AuthUser get(String token){
     AuthUser res = null;
     if(token!=null){
       res = (AuthUser)instance.getMap("token-cache").get(token);
     }   
     return res;
 }
 public String newToken() {
     return UUID.randomUUID().toString();
 }
}
