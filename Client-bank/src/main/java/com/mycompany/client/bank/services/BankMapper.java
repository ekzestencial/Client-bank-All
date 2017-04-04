/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.services;
import com.mycompany.client.bank.api.LibBank;
import com.mycompany.client.bank.auth.SecretProvider;
import com.mycompany.client.bank.jpa.Bank;
import com.mycompany.client.bank.repository.BankRepository;
import com.mycompany.client.bank.utils.CryptMessage;
import com.mycompany.client.bank.utils.PermisChecker;
import java.util.List;
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
    @Autowired
    SecretProvider secretProvider;
    public LibBank fromInternal(Bank b) {
        LibBank lb = null;
        if (b != null) {
            List <String> lstParam=CryptMessage.fromInternal(secretProvider.get(PermisChecker.GetAuthUser()).getSuperSecretKey().toString(),
                    b.getBankId().toString(), b.getName(), b.getCreditPersent(), b.getDepositPersent());
            lb = new LibBank();
            lb.bank_id = lstParam.get(0);
            lb.name = lstParam.get(1);
            lb.creditPersent = lstParam.get(2);
            lb.depositPersent = lstParam.get(3);
        }
        return lb;
    }
        public Bank toInternal(LibBank lb) {
        System.out.println(secretProvider.get(PermisChecker.GetAuthUser()).getSuperSecretKey());
        lb=(LibBank)CryptMessage.toInternal(secretProvider.get(PermisChecker.GetAuthUser()).getSuperSecretKey().toString(), lb);
        Long new_Id=null;
        Bank b = null;
        //first, check if it exists
        if (lb.bank_id != null) {
            b = bankRepository.findOne(Long.valueOf(lb.bank_id));
        }
        new_Id=Long.valueOf(lb.name.hashCode());
        if (b == null) { //not found, create new
            //logger.debug("Creating new user");
            b = new Bank(new_Id, lb.name, Integer.valueOf(lb.depositPersent.trim()), Integer.valueOf(lb.creditPersent.trim()));
        }
        else{
        //logger.debug("Updating existing user");
        b.setBankId(Long.valueOf(lb.bank_id));
        b.setName(lb.name);
        b.setDepositPersent(Integer.valueOf(lb.depositPersent));
        b.setCreditPersent(Integer.valueOf(lb.creditPersent));
        }
        return b;
    }
}
