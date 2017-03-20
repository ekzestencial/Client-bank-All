/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.services;

import com.mycompany.client.bank.api.LibAppUserAndUserDetails;
import com.mycompany.client.bank.api.PostRequstLibAuthorization;
import com.mycompany.client.bank.jpa.Appuser;
import com.mycompany.client.bank.jpa.Userdetails;
import com.mycompany.client.bank.repository.UserRepository;
import com.mycompany.client.bank.utils.EntityIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author ekzestencial
 */
@Component
public class AppUserAndUserDetailsMapper {
//

	@Autowired
	UserRepository userRepository;
//
////Mapping of internal JPA model to external REST model

	public LibAppUserAndUserDetails fromInternal(Appuser u) {
		LibAppUserAndUserDetails lu = null;
		if (u != null) {
//
			lu = new LibAppUserAndUserDetails();
		}
		Userdetails ud = u.getUserdetails();
		lu.login = u.getUsername();
		lu.user_id = u.getUserId();
		lu.wallet = u.getWallet();
		if (ud != null) {
			lu.firstName = ud.getFirstName();
			lu.lastName = ud.getLastName();
			lu.adress = ud.getAdress();
			lu.phone = ud.getPhone();
			lu.role_id = ud.getUserId();
		}
		return lu;
	}

	private Appuser newUser() {
		//TODO: get logged user from security context
		Appuser au = new Appuser();
		Userdetails ud = new Userdetails();
		boolean idOK = false;
		Long id = 0L;
		while (!idOK) {
			id = EntityIdGenerator.random();
			idOK = !userRepository.exists(id);
		}
		au.setUserId(id);
		ud.setUserId(id);
		au.setUserdetails(ud);
		return au;
	}
	////Mapping of external REST model to internal Appuser;
	//

	public Appuser toInternal(LibAppUserAndUserDetails lu) {
		Appuser au = null;

//check user existence
		if (lu.user_id != null) {
			au = userRepository.findOne(lu.user_id);
		}
		if (au == null) { //not found, create new
			au = newUser();
		}
		Userdetails ud = au.getUserdetails();
		au.setUsername(lu.login);
		ud.setFirstName(lu.firstName);
		ud.setLastName(lu.lastName);
		ud.setPhone(lu.phone);
		ud.setAdress(lu.phone);
		au.setEmail(lu.email);
		return au;
	}
//method Overloading "toInternal" for the LibAuthorizaiton.class;
	public Appuser toInternal(PostRequstLibAuthorization la) {
		Appuser au = null;
		au=userRepository.findUserByUsernameAndPassword(la.login, la.password);

		return au;
	}
}
