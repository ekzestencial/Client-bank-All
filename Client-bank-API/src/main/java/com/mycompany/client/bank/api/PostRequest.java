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
 * @author artem
 */
@XmlRootElement
public class PostRequest {
    @XmlElement(required=false)
    public LibTransaction transaction;
    @XmlElement(required=false)
    public LibBank bank;
    @XmlElement(required=false)
    public LibAppUserAndUserDetails user;
    
}
