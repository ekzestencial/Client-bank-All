/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.auth;
import org.springframework.security.core.GrantedAuthority;
/**
 *
 * @author alex
 */

public class UserAuthority implements GrantedAuthority{
    private String autority; 
    
    public UserAuthority(AuthorityName n) {
        autority = n.name();
    }   
    
    @Override
    public String getAuthority() {
       return autority;
    }   
    
}