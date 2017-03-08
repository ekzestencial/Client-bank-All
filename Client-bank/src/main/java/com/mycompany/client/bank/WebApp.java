/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank;

/**
 *
 * @author ekzestencial
 */
import com.mycompany.client.bank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.mycompany.client.bank.services.UserService;

@SpringBootApplication
public class WebApp {

	public static void main(String[] args) {
		SpringApplication.run(WebApp.class, args);
	}

}
