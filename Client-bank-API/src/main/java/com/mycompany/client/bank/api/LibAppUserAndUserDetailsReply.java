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
 * @author ekzestencial
 */

@XmlRootElement
public class LibAppUserAndUserDetailsReply extends GenericReply{
    @XmlElement(required=true)
    public List<LibAppUserAndUserDetails> users = new ArrayList<>();
}