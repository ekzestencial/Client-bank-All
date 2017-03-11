/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.tests.unit;

import com.mycompany.client.bank.jpa.Account;
import com.mycompany.client.bank.jpa.Transaction;
import com.mycompany.client.bank.services.TransactionService;
import com.mycompany.client.bank.utils.EntityIdGenerator;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author artem
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TransactionServiceTest {
    @Autowired
    private TransactionService transactionService;
    @Test
    public void findTransactionTest() throws Exception {
        int count = transactionService.getAll().size();
        assert(count>=3);
    } 
    @Test
    public void findByTransactionIdTest() throws Exception {
        Transaction tmp=transactionService.findByTransactionId(2L);
        assertNotNull(tmp);
    }
    @Test
    public void AddTransaction() throws Exception {
        Long tr_id = EntityIdGenerator.random();
         Transaction tr = new Transaction(tr_id);
         tr.setAccountId(new Account(1L));
         tr.setValue(2000);
         tr.setDate(Date.from(Instant.parse("2016-12-03T15:15:30.00Z")));
         transactionService.addTransaction(tr);
         tr=transactionService.findByTransactionId(tr_id);
         assertNotNull("New Transaction not found", tr);
         transactionService.delTransaction(tr_id);
         tr=transactionService.findByTransactionId(tr_id);
         assertNull("Can not delete transaction", tr);
    }
    @Test
    public void findByValueTest(){
    List<Transaction> tr_list=transactionService.findByValue(10, 96);
     assert(!tr_list.isEmpty());
    }
    @Test
    public void findByDateTest(){
    List<Transaction> tr_list=transactionService.findByDate(Date.from(Instant.parse("2007-12-03T15:15:30.00Z")), Date.from(Instant.now()));
     assert(!tr_list.isEmpty());
    }
}
