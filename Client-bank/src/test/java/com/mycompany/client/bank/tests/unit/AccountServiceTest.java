package com.mycompany.client.bank.tests.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.mycompany.client.bank.jpa.Account;
import com.mycompany.client.bank.jpa.Appuser;
import com.mycompany.client.bank.jpa.Bank;
import com.mycompany.client.bank.services.AccountService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AccountServiceTest {
	@Autowired
	AccountService service;
	
	static Account from;
	static Account to;
	
	@BeforeClass
	public static void init() {
		from = new Account(1L);
		from.setValue(1000.0);
		from.setBankId(new Bank(1L));
		from.setUserId(new Appuser(1L));
		from.setCreditLimit(2000L);
		from.setOpenDate(new Date(2016-12-04));
		to = new Account(2L);
		to.setValue(0);
		to.setBankId(new Bank(2L));
		to.setUserId(new Appuser(2L));
		to.setCreditLimit(500L);
		to.setOpenDate(new Date(2016-12-05));
	}
	
	@Test
	public void testGetUserAccounts() {
		List<Account> list = service.getUserAccounts(new Appuser(1L));
		assertNotNull(list);
		Account acc = list.get(0);
		assertEquals(acc.getUserId().getUserId(), new Long(1L));
	}
	
	@Test
	public void testGetAccount() {
		Account acc = service.getAccount(1L);
		assertNotNull(acc);
		assertEquals(acc.getAccountId(), new Long(1L));
	}
	
	@Test
	public void testTransfer() {
		try {
			service.transfer(from, to, 1000);
		} catch (Exception e) {
			fail("Exception while tranfer");
		}
	}
	
	@Test
	public void testAddAccount() {
		Account acc = new Account(5L);
		acc.setUserId(new Appuser(1L));
		acc.setBankId(new Bank(2L));
		acc.setValue(0);
		acc.setCreditLimit(555L);
		acc.setOpenDate(new Date(System.currentTimeMillis()));
		
		assertNotNull(service.addAccount(acc));
	}
}
