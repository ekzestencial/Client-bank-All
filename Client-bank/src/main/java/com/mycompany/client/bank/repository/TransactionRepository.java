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
    public Transaction findById(Long id);
    public List<Transaction> findByValue(int value);
    public List<Transaction> findByValueLessThanOrEquality(int value);
    public List<Transaction> findByValueGreaterThanOrEquality(int value);
    public List<Transaction> findByDate(Date date);
    public List<Transaction> findByDateLessThanOrEquality(Date date);
    public List<Transaction> findByDateGreaterThanOrEquality(Date date);
    public List<Transaction> findByAccaountId(Account accaountId);
    public Transaction removeById(Long id);
    public List<Transaction> removeByValueLessThanOrEquality(int value);
    public List<Transaction> removeByValueGreaterThanOrEquality(int value);
    public List<Transaction> removeByDate(Date date);
    public List<Transaction> removeByAccaountId(Account accaountId);
}
