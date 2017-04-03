/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.utils;

import com.mycompany.client.bank.auth.AuthUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author artem
 */
public class PermisChecker {
    public static AuthUser GetAuthUser(){
    return ((AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
    public static boolean ForUserAndAdmin(String username){
    return ForUser(username) || ForAdmin(username) ;
    }
    public static boolean ForUser(String username){
    return ((AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAppUser().getUsername().equals(username);
    }
    public static boolean ForAdmin(String username){
    return ((AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAppUser().getRoleId().getRoleId() == 0L;
    }
}
