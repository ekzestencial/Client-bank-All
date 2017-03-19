/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.tests.unit;

import com.mycompany.client.bank.jpa.Appuser;
import com.mycompany.client.bank.jpa.Role;
import com.mycompany.client.bank.jpa.Userdetails;
import com.mycompany.client.bank.repository.UserRepository;
import com.mycompany.client.bank.services.AppUserAndUserDetailsService;
import com.mycompany.client.bank.utils.EntityIdGenerator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author ekzestencial
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AppUserAndUserDetailsServiceTest {

	@Autowired
	private AppUserAndUserDetailsService userService;
    
 

	public AppUserAndUserDetailsServiceTest() {
	}

	@BeforeClass
	public static void setUpClass() {
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of getAllUsers method, of class UserAndUserDetailsService.
	 */
	@Test
	public void testGetAllUsers() {
		int count = userService.getAllUsers().size();
		assert (count >= 4);
	}

	/**
	 * Test of getUserById method, of class UserAndUserDetailsService.
	 */
	@Test
	public void testGetUserById() {
		Long index = 1L;
		String name = userService.getUserById(index).getUsername();
		assertEquals("admin", name);
	}
	@Test
	public void getUserByUsernamename(){
	Appuser user = userService.getUserByName("admin");
		assertNotNull(user);
	}
	//test of adduser,deluser and getUserDetailsByAppUser;
	@Test
	public void AddUser() {
		Long user_id = EntityIdGenerator.random();
		//new date
		//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		Role role = new Role(0L);
		Appuser au = new Appuser(user_id);
		Userdetails ud = new Userdetails(user_id);
		//determination of the appuser
		au.setUsername("test");
		au.setPassword("fdsfewffds");
		au.setEmail("test@rfd.com");
		au.setRegDate(date);
		au.setLastActivity(date);
		au.setRoleId(role);
		//determination of the userdetails 
		ud.setFirstName("TestFirstUser");
		ud.setLastName("TestLastName");
		userService.addUser(au);
		userService.addUserDetails(ud);
		//getUserDetailsByAppuser
		Userdetails userdetails=userService.getUserDetails(au);
		assertNotNull(userdetails);
		Appuser u = userService.getUserById(user_id);
		assertNotNull("New user not found!", u);
		userService.delUser(user_id);
		u = userService.getUserById(user_id);
		assertNull("Can not delete user!", u);
	}

}
