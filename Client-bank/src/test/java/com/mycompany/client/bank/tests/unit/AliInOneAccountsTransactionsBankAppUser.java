/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.tests.unit;

import com.mycompany.client.bank.jpa.Account;
import com.mycompany.client.bank.jpa.Appuser;
import com.mycompany.client.bank.jpa.Bank;
import com.mycompany.client.bank.jpa.Role;
import com.mycompany.client.bank.jpa.Transaction;
import com.mycompany.client.bank.jpa.Userdetails;
import com.mycompany.client.bank.services.AccountService;
import com.mycompany.client.bank.services.AppUserAndUserDetailsService;
import com.mycompany.client.bank.services.BankService;
import com.mycompany.client.bank.services.TransactionService;
import com.mycompany.client.bank.utils.EntityIdGenerator;
import java.time.Instant;
import java.util.Collection;
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
 * @author alex
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AliInOneAccountsTransactionsBankAppUser {

	@Autowired
	AppUserAndUserDetailsService userService;
	@Autowired
	BankService bankService;
	@Autowired
	AccountService accService;
	@Autowired
	TransactionService transacservice;

	public AliInOneAccountsTransactionsBankAppUser() {
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

	@Test
	public void deleteAccountAndAllTransactionsForSpecificUser() {
		Long user_id = EntityIdGenerator.random();
		Date date = new Date(System.currentTimeMillis());
		Role role = new Role(0L);
////    add_new_user;
		Long account_id = EntityIdGenerator.random();
		Account acc = new Account(account_id);
		Appuser appuser = new Appuser(user_id);
		Userdetails ud = new Userdetails(user_id);
		acc.setUserId(appuser);
		Long bank_id = EntityIdGenerator.random();
		Bank bank = new Bank(bank_id);
		bank.setName("testbank25");
		acc.setBankId(bank);
		acc.setCreditLimit(0L);
		acc.setValue(0L);
		acc.setOpenDate(date);
		acc.setCreditLimit(500L);
		//determination of the appuser
		appuser.setUsername("tedastxsa211111");
		appuser.setPassword("fdsfdsa");
		appuser.setEmail("testa11212111@test.com");
		appuser.setRegDate(date);
		appuser.setLastActivity(date);
		appuser.setRoleId(role);
		//create userdetails
		ud.setFirstName("TestFirstUser");
		ud.setLastName("TestLastName");

		assertNotNull("Not create Bank", bankService.addBank(bank));
		assertNotNull("Not create User", userService.addUser(appuser));
		assertNotNull("Not create User", userService.addUserDetails(ud));
		assertNotNull("Not create Account", accService.addAccount(acc));
		Long CreditLimivValue = EntityIdGenerator.random();
		Long FirsTransaction_Id = EntityIdGenerator.random();
		Transaction Firsttransaction = new Transaction(FirsTransaction_Id);
		Long SecondTransaction_Id = EntityIdGenerator.random();
		Transaction Secondtransaction = new Transaction(SecondTransaction_Id);
		Firsttransaction.setValue(2000);
		Secondtransaction.setValue(2000);
		Firsttransaction.setDate(Date.from(Instant.parse("2016-12-03T15:15:30.00Z")));
		Secondtransaction.setDate(Date.from(Instant.parse("2016-12-03T15:15:30.00Z")));
	Secondtransaction.setTransactionInfo("Bla Bla");
		Firsttransaction.setTransactionInfo("Bla Bla");
		Firsttransaction.setAccountId(acc);
		Secondtransaction.setAccountId(acc);
		transacservice.addTransaction(Firsttransaction);
		transacservice.addTransaction(Secondtransaction);
//		//get all transactions for specific user;
		List <Transaction> allTransactions = transacservice.findTransByAccount(acc);
//    assertNotNull("Not found transactions",allTransactions);
		//delete allTransactions
			transacservice.deleteAllTransactions(allTransactions);
		 allTransactions = transacservice.findTransByAccount(acc);
		assertTrue("Can not delete user",allTransactions.isEmpty());
		//delete accounts and other entities;
		accService.deleteAccount(account_id);
		Account accuser = accService.getAccount(account_id);
		assertNull("Can not delete account", accuser);
		userService.delUser(user_id);
		Userdetails userdetails = userService.getUserDetails(appuser);
		assertNull("Can not delete user",userdetails);
		bankService.delBank(bank_id);
	
		
		

	}

	// TODO add test methods here.
	// The methods must be annotated with annotation @Test. For example:
	//
	// @Test
	// public void hello() {}
}
