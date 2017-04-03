package com.mycompany.client.bank.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.client.bank.jpa.Account;
import com.mycompany.client.bank.jpa.Appuser;
import com.mycompany.client.bank.jpa.Transaction;
import com.mycompany.client.bank.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	AccountRepository accountRepo;

	public List<Account> getUserAccounts(Appuser user) {
		return accountRepo.findByUserId(user);
	}

	public Account addAccount(Account acc) {
		return accountRepo.save(acc);
	}

	public Account getAccount(Long id) {
		return accountRepo.findOne(id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void transfer(Account from, Account to, double value) {
		from.setValue(from.getValue() - value);
		to.setValue(to.getValue() + value);
		accountRepo.save(from);
		accountRepo.save(to);
	}
		public void deleteAccount(Long Id) {
		accountRepo.delete(Id);
	}

}
