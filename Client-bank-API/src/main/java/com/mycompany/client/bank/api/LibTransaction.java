/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.api;

import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author artem
 */
@XmlRootElement
public class LibTransaction {
    @XmlElement(required = true)
    public Long transactionId;
    @XmlElement(required = true)
    public String value;
    @XmlElement(required = true)
    public String date;
    @XmlElement(required = true)
    public Long accountId;
}
