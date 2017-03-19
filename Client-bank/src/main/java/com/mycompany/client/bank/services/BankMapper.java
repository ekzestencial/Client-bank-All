/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.services;
import com.mycompany.client.bank.api.LibBank;
import com.mycompany.client.bank.jpa.Bank;
import com.mycompany.client.bank.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 *
 * @author artem
 */
@Component
public class BankMapper {
    @Autowired
    BankRepository bankRepository;
    public LibBank fromInternal(Bank b) {
        LibBank lb = null;
        if (b != null) {
            lb = new LibBank();
            lb.bank_id=b.getBankId();
            lb.name=b.getName();
            lb.creditPersent=b.getCreditPersent();
            lb.depositPersent=b.getDepositPersent();
        }
        return lb;
    }
        public Bank toInternal(LibBank lb) {
        Long new_Id=null;
        Bank b = null;
        //first, check if it exists
        if (lb.bank_id != null) {
            b = bankRepository.findOne(lb.bank_id);
        }
        else{
        new_Id=Long.valueOf(lb.name.hashCode());
        }
        if (b == null) { //not found, create new
            //logger.debug("Creating new user");
            b = new Bank(new_Id, lb.name, lb.depositPersent, lb.creditPersent);
        }
        else{
        //logger.debug("Updating existing user");
        b.setBankId(lb.bank_id);
        b.setName(lb.name);
        b.setDepositPersent(lb.depositPersent);
        b.setCreditPersent(lb.creditPersent);
        }
        return b;
    }
}
