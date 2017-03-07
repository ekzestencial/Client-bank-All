/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.services;

import com.mycompany.client.bank.jpa.Bank;
import com.mycompany.client.bank.repository.BankRepository;
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

public List<Bank> getAll(){
      return  bankRepository.findAll();
  }
public Bank findByBankId(Long id){
      return  bankRepository.findOne(id);
  }
}
