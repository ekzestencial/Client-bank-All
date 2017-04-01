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
public class LibTransaction {
    @XmlElement(required = false)
    public Long transactionId;
    @XmlElement(required = true)
    public Double value;
    @XmlElement(required = false)
    public String date;
    @XmlElement(required = false)
    public String Info;
    @XmlElement(required = false)
    public Long accountId;
}
