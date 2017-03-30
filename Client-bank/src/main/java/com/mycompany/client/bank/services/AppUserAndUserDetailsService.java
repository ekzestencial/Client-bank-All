/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.services;

import com.mycompany.client.bank.jpa.Appuser;
import com.mycompany.client.bank.jpa.Userdetails;
import com.mycompany.client.bank.repository.UserDetailsRepository;
import com.mycompany.client.bank.repository.UserRepository;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;

/**
 *
 * @author ekzestencial
 */
@Service
public class AppUserAndUserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(AppUserAndUserDetailsService.class);

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserDetailsRepository userDetailsRepository;

	public List<Appuser> getAllUsers() {
		return userRepository.findAll();
	}

	public Appuser getUserById(Long index) {
		return userRepository.findOne(index);
		//return userRepository.findUserById(index);
	}
//
	public Appuser getUserByName(String name) {
		return userRepository.findUserByUsername(name);
	}
	
	public Userdetails getUserDetails(Appuser user) {
		Long userId=user.getUserId();
		return userDetailsRepository.findUserByUserId(userId);
	}

	public Appuser addUser(Appuser au) {
		au = userRepository.save(au);
		return au;
	}

	public Userdetails addUserDetails(Userdetails ad) {
		ad = userDetailsRepository.save(ad);
		return ad;
	}

	public void delUser(Long id) {
		Appuser u = userRepository.findOne(id);
		if (u != null) {
			userDetailsRepository.delete(id);
			userRepository.delete(id);
		}
	}
	public Appuser getUserByUsernameAndPassword(String username, String password){
	return userRepository.findUserByUsernameAndPassword(username, password);	
	} 
	    public Appuser updateUser(Appuser appuser) {
        appuser = userRepository.save(appuser);
        return appuser;
    }   

    public Appuser authUser(String login, String password) {
        Appuser appuser = userRepository.findUserByUsername(login);
        if (appuser != null) {
            if( ! appuser.getPassword().equalsIgnoreCase(password)){
                appuser = null;
                logger.debug("Invalid password");
            }
            logger.debug("Login ok");
        }else{
            logger.debug("User not found");    
        }
        return appuser;
    }   

    public static String digest(String original) {
        String res = ""; 
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(original.getBytes());
            byte[] digest = md.digest();
            res = new String(Hex.encode(digest));
        } catch (NoSuchAlgorithmException ex) {
           logger.error("Can not create SHA-256 digester");
        }
        return res;
    }
}
