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
public class LibBank {
    @XmlElement(required=false)
    public String bank_id;
    @XmlElement(required=false)
    public String name;
    @XmlElement(required=true)
    public String creditPersent;
    @XmlElement(required=true)
    public String depositPersent;
}
