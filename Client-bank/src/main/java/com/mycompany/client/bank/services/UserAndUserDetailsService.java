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
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author ekzestencial
 */
@Service
public class UserAndUserDetailsService {

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
}
