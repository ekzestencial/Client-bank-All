package com.mycompany.client.bank.api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LibAccount {
	
	@XmlElement(required = false)
	public Long accountId;
	@XmlElement(required = true)
	public Double value;
	@XmlElement(required = false)
	public String openDate;
        @XmlElement(required = false)
        public Long credit_limit;
	@XmlElement(required = false)
	public Long userId;
	@XmlElement(required = false)
	public Long bankId;
        @XmlElement(required =  false)
        public String bankName;
        @XmlElement(required = false)
        public String CreditPersent;
        @XmlElement(required = false)
        public String DepositPersent;
        @XmlElement(required = false)
        public Long CreditLimit;
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + userId.hashCode();
		result = 37 * result + bankId.hashCode();
		return result;
	}
}
