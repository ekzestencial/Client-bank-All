package com.mycompany.client.bank.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mycompany.client.bank.jpa.Account;
import com.mycompany.client.bank.jpa.Appuser;
import com.mycompany.client.bank.jpa.Bank;

public interface AccountRepository extends CrudRepository<Account, Long> {
	
	public List<Account> findByUserId(Appuser user);
    public List<Account> findByBankId(Bank bank);
	public List<Account> findByUserIdAndBankId(Appuser user, Bank bank);
    public List<Account> removeByBankId(Bank bank);
}
