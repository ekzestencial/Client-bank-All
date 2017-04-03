package com.mycompany.client.bank.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.mycompany.client.bank.api.LibAccount;
import com.mycompany.client.bank.auth.SecretProvider;
import com.mycompany.client.bank.jpa.Account;
import com.mycompany.client.bank.repository.AccountRepository;
import com.mycompany.client.bank.repository.BankRepository;
import com.mycompany.client.bank.repository.UserRepository;
import com.mycompany.client.bank.utils.CryptMessage;
import com.mycompany.client.bank.utils.EntityIdGenerator;
import com.mycompany.client.bank.utils.PermisChecker;

import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Component;
@Component
public class AccountMapper {
	
	@Autowired
	AccountRepository accRepo;
	@Autowired
	BankRepository bankRepo;
	@Autowired
	UserRepository userRepo;
        @Autowired
        SecretProvider secretProvider;
	
	public LibAccount fromInternal(Account acc) {
		LibAccount la = null;
		if(acc != null) {
                    List <String> lstParam=CryptMessage.fromInternal(secretProvider.get(PermisChecker.GetAuthUser()).getSuperSecretKey().toString(), acc.getAccountId().toString(), acc.getValue(), acc.getOpenDate().toString(),
                        acc.getOpenDate().toString(), acc.getBankId().getBankId().toString(),
                        acc.getUserId().getUserId().toString(), acc.getCreditLimit());
			la = new LibAccount();
			la.accountId = lstParam.get(0);
			la.value = lstParam.get(1);
			la.openDate = lstParam.get(2);
			la.bankId = lstParam.get(3);
			la.userId = lstParam.get(4);
                        la.credit_limit= lstParam.get(5);
		}
		return la;
	}
	
	public Account toInternal(LibAccount la) {
		Account acc = null;
		if(la.accountId != null) {
			acc = accRepo.findOne(Long.valueOf(la.accountId));
			acc.setValue(Double.valueOf(la.value));
                        if(la.credit_limit!=null)acc.setCreditLimit(Long.valueOf(la.credit_limit));
                        else acc.setCreditLimit(0L);
		} else {
			Long newId = EntityIdGenerator.random();
			while(accRepo.findOne(newId) != null) {
				newId = EntityIdGenerator.random();
			}
			acc = new Account(newId, 0L, Double.valueOf(la.value), Date.from(Instant.now()));
			acc.setBankId(bankRepo.findOne(Long.valueOf(la.bankId)));
			acc.setUserId(userRepo.findOne(Long.valueOf(la.userId)));
		}
		return acc;
	}
}
