/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.services;

import com.mycompany.client.bank.api.LibAppUserAndUserDetails;
import com.mycompany.client.bank.jpa.Appuser;
import com.mycompany.client.bank.jpa.Userdetails;
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
AppUserAndUserDetailsService userService;
//
////Mapping of internal JPA model to external REST model
	public LibAppUserAndUserDetails fromInternal(Appuser u) {
		LibAppUserAndUserDetails lu = null;
		if (u != null) {
//
			lu = new LibAppUserAndUserDetails();
//			Userdetails ud = u.getUserdetails();
//			if (u.getUsername() == "admin") {
//				lu.isAdmin = true;
//			}
//			lu.login = u.getUsername();
//			lu.user_id = u.getUserId();
//			lu.wallet = u.getWallet();
//			if (ud != null) {
//				lu.firstName = ud.getFirstName();
//				lu.lastName = ud.getLastName();
//				lu.adress = ud.getAdress();
//				lu.phone = ud.getPhone();
//			}
		}
		return lu;
	}
////Mapping of external REST model to internal Appuser;
//
//	public Appuser toInernal(LibAppUserAndUserdetails lu) {
//		Appuser au = null;
//
////check user existence
//		if (lu.user_id != null) {
//			au = userService.getUserById(lu.user_id);
//		}
//		Userdetails ud = au.getUserdetails();
//		au.setUsername(lu.login);
//                ud.setFirstName(lu.firstName);
//		ud.setLastName(lu.lastName);
//		ud.setPhone(lu.phone);
//		ud.setAdress(lu.phone);
//		au.setEmail(lu.email);
		return au;
//	}
}
