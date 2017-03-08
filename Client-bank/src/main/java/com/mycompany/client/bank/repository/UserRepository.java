/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.repository;

import java.io.Serializable;
import org.springframework.data.repository.CrudRepository;
import com.mycompany.client.bank.jpa.Appuser;
import java.util.List;
/**
 *
 * @author ekzestencial
 */
public interface UserRepository extends CrudRepository<Appuser, Long>{
	 @Override
    public List<Appuser> findAll();
    //public List<Appuser> findById();
}
