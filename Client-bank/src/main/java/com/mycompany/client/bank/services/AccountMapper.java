package com.mycompany.client.bank.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.mycompany.client.bank.api.LibAccount;
import com.mycompany.client.bank.jpa.Account;
import com.mycompany.client.bank.repository.AccountRepository;
import com.mycompany.client.bank.repository.BankRepository;
import com.mycompany.client.bank.repository.UserRepository;
import org.springframework.stereotype.Component;
@Component
public class AccountMapper {
	
	@Autowired
	AccountRepository accRepo;
	@Autowired
	BankRepository bankRepo;
	@Autowired
	UserRepository userRepo;
	
	public LibAccount fromInternal(Account acc) {
		LibAccount la = null;
		
		if(acc != null) {
			la = new LibAccount();
			la.accountId = acc.getAccountId();
			la.value = acc.getValue();
			la.openDate = acc.getOpenDate().toString();
			la.bankId = acc.getBankId().getBankId();
			la.userId = acc.getUserId().getUserId();
		}
		return la;
	}
	
	public Account toInternal(LibAccount la) {
		Account acc = null;
		
		if(la.accountId != null) {
			acc = accRepo.findOne(la.accountId);
			acc.setValue(la.value);
		} else {
			acc = new Account(Long.valueOf(la.hashCode()), la.value, new Date(System.nanoTime()));
			acc.setBankId(bankRepo.findOne(la.bankId));
			acc.setUserId(userRepo.findOne(la.userId));
		}
		return acc;
	}
}
