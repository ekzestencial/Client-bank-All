package com.mycompany.client.bank.api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LibAccount {
	
	@XmlElement(required = false)
	public Long accountId;
	@XmlElement(required = true)
	public Double value;
	@XmlElement(required = true)
	public String openDate;
	@XmlElement(required = true)
	public Long userId;
	@XmlElement(required = true)
	public Long bankId;
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + userId.hashCode();
		result = 37 * result + bankId.hashCode();
		return result;
	}
}
