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
 * @author alex
 */

    @XmlRootElement
public class LoginReply {
    @XmlElement
    public String G;
    @XmlElement
    public String P;
    @XmlElement
    public String ServerPublicKey;
    @XmlElement
    public String token="";
    @XmlElement
    public LibAppUserAndUserDetails user;
}