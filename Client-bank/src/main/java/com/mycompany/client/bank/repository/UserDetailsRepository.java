/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.repository;
import com.mycompany.client.bank.jpa.Userdetails;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
/**
 *
 * @author ekzestencial
 */
public interface UserDetailsRepository extends CrudRepository<Userdetails, Long>{
}
