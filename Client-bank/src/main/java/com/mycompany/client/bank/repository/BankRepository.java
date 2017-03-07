/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.repository;
import com.mycompany.client.bank.jpa.Bank;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
/**
 *
 * @author artem
 */
public interface BankRepository extends CrudRepository<Bank, Long>{
     @Override
     public List<Bank> findAll();
}
