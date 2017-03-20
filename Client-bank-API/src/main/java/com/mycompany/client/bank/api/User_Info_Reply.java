/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.api;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author artem
 */
@XmlRootElement
public class User_Info_Reply {
    @XmlElement(required = true)
    public String UserName;
    @XmlElement(required = true)
    public List<LibAccount> account_list= new ArrayList<>();
    @XmlElement(required = true)
    public Double wallet;
    @XmlElement(required = true)
    public Integer notif_size;
}
