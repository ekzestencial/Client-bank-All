/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.services;

import com.mycompany.client.bank.api.LibAppUserAndUserDetails;
import com.mycompany.client.bank.api.PostRequstLibAuthorization;
import com.mycompany.client.bank.jpa.Appuser;
import com.mycompany.client.bank.jpa.Role;
import com.mycompany.client.bank.jpa.Userdetails;
import com.mycompany.client.bank.repository.RoleRepository;
import com.mycompany.client.bank.repository.UserRepository;
import com.mycompany.client.bank.utils.EntityIdGenerator;
import java.time.Instant;
import java.util.Date;
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
        @Autowired
        RoleRepository roleRep;
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
                lu.password = u.getPassword();
		lu.user_id = u.getUserId();
		lu.wallet = u.getWallet();
                lu.email=u.getEmail();
                lu.role_id = u.getRoleId().getRoleId();
		if (ud != null) {
			lu.firstName = ud.getFirstName();
			lu.lastName = ud.getLastName();
			lu.adress = ud.getAdress();
			lu.phone = ud.getPhone();
		}
		return lu;
	}

	private Appuser newUser(Long user_id) {
		//TODO: get logged user from security context
		Appuser au = new Appuser();
		Userdetails ud = new Userdetails();
		au.setUserId(user_id);
		ud.setUserId(user_id);
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
			au = newUser(lu.user_id);
		}
		Userdetails ud = au.getUserdetails();
		au.setUsername(lu.login);
                au.setPassword(lu.password);
                au.setEmail(lu.email);
                au.setWallet(lu.wallet);
                au.setRoleId(roleRep.findOne(lu.role_id));
                au.setRegDate(Date.from(Instant.now()));
                au.setLastActivity(Date.from(Instant.now()));
		ud.setFirstName(lu.firstName);
		ud.setLastName(lu.lastName);
		ud.setPhone(lu.phone);
		ud.setAdress(lu.phone);
                ud.setAppuser(au);
		return au;
	}
//method Overloading "toInternal" for the LibAuthorizaiton.class;
	public Appuser toInternal(PostRequstLibAuthorization la) {
		Appuser au = null;
		au=userRepository.findUserByUsernameAndPassword(la.login, la.password);
		return au;
	}
}
