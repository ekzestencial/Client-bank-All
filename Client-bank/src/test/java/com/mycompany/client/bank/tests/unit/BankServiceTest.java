/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.tests.unit;

import com.mycompany.client.bank.jpa.Bank;
import com.mycompany.client.bank.services.BankService;
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
public class BankServiceTest {
     @Autowired
    private BankService bankService;
    @Test
    public void findBankTest() throws Exception {
        int count = bankService.getAll().size();
        assert(count>=4);
    } 
    @Test
    public void findByBankIdTest() throws Exception {
         Bank tmp=bankService.findByBankId(1L);
        assertNotNull(tmp);
    }
    /*@Test
    public void AddBank() throws Exception {
         Bank tmp=bankService.findByBankId(1L);
        assert(tmp!=null);
    }*/
}
