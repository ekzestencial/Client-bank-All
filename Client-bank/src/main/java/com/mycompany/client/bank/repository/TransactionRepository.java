/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.repository;

import com.mycompany.client.bank.jpa.Account;
import com.mycompany.client.bank.jpa.Transaction;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import java.util.Date;
/**
 *
 * @author artem
 */
public interface TransactionRepository extends CrudRepository<Transaction, Long>{
             @Override
    public List<Transaction> findAll();
    public List<Transaction> findByValueBetween(int value1,int value2);
    public List<Transaction> findByDateBetween(Date date1, Date date2);
    public List<Transaction> findByAccountId(Account accountId);
    public List<Transaction> removeByValueBetween(int value1, int value2);
    public List<Transaction> removeByDateBetween(Date date1, Date date2);
    public List<Transaction> removeByAccountId(Account accaountId);
}
