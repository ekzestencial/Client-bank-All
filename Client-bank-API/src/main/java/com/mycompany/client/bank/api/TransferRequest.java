package com.mycompany.client.bank.api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TransferRequest {
	@XmlElement
	public String value;
	@XmlElement(required = false)
	public String toAccountId;
}
