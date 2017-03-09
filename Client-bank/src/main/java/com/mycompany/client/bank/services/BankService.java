/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.services;

import com.mycompany.client.bank.jpa.Account;
import com.mycompany.client.bank.jpa.Bank;
import com.mycompany.client.bank.repository.AccountRepository;
import com.mycompany.client.bank.repository.BankRepository;
import com.mycompany.client.bank.repository.TransactionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author artem
 */
@Service
public class BankService {
@Autowired
BankRepository bankRepository;
@Autowired
AccountRepository accountRepository;
@Autowired
TransactionRepository transactionRepository;

public List<Bank> getAll(){
      return  bankRepository.findAll();
  }
public Bank findByBankId(Long id){
      return  bankRepository.findOne(id);
  }

public Bank addBank(Bank bank) {
        bank = bankRepository.save(bank);
        return bank;
    }
public void delBank(Long id){
        Bank b = bankRepository.findOne(id);
        if(b!=null){
           List<Account> tmp_accaoutn=accountRepository.removeByBankId(b);
           for(Account ac:tmp_accaoutn)
           transactionRepository.removeByAccountId(ac);
           bankRepository.delete(b);
        }
    }
public Bank updateBank(Bank bank) {
        bank = bankRepository.save(bank);
        return bank;
    }
}
