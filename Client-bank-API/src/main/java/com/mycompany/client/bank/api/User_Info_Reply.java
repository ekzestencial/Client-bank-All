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
    @XmlElement(required = false)
    public String wallet;
    @XmlElement(required = false)
    public String notif_size;
    @XmlElement (required = false)
    public String currentTime;
    @XmlElement (required = false)
    public String FullName;
}
