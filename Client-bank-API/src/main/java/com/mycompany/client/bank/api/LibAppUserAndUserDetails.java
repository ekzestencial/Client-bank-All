/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ekzestencial
 */
@XmlRootElement
public class LibAppUserAndUserDetails {
	@XmlElement(required = false) 
	public Long user_id;
	@XmlElement(required = true)
	public String login;
        @XmlElement(required = false)
        public String password;
	@XmlElement(required = false)
	public String firstName;
	@XmlElement(required = false)
	public String lastName;
	@XmlElement(required = false)
	public String email;
	@XmlElement(required = false)
	public String adress;
	@XmlElement(required = false)
	public String phone;
        @XmlElement(required = false)
	public Double wallet;	
        @XmlElement(required = false)
	public Long role_id;	


}
