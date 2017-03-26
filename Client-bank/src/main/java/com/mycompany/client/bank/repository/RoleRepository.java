/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.repository;

import com.mycompany.client.bank.jpa.Role;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author artem
 */
public interface RoleRepository extends CrudRepository<Role, Long> {
    
}
