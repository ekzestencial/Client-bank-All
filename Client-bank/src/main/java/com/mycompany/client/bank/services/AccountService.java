package com.mycompany.client.bank.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.client.bank.jpa.Account;
import com.mycompany.client.bank.jpa.Appuser;
import com.mycompany.client.bank.repository.AccountRepository;

@Service
public class AccountService {
	
	@Autowired
	AccountRepository accountRepo;
	
	public List<Account> getUserAccounts(Appuser user) {
		return accountRepo.findByUserId(user);
	}
}
