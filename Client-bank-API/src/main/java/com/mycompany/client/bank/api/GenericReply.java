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
public class GenericReply {
    @XmlElement(required=true)
    public Integer retcode = 0;
    @XmlElement(required=true)
    public String apiVer = "0.0.1";
    @XmlElement(required=false)
    public String error_message;
}
