/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.services;

import com.mycompany.client.bank.jpa.Appuser;
import com.mycompany.client.bank.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ekzestencial
 */
@Service
public class UserService {
	@Autowired
UserRepository userRepository;
	  public List<Appuser> getAllUsers(){
      return  userRepository.findAll();
  }
	
}
