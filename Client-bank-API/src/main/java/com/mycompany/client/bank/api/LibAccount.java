package com.mycompany.client.bank.api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LibAccount {
	
	@XmlElement(required = false)
	public String accountId;
	@XmlElement(required = true)
	public String value;
	@XmlElement(required = false)
	public String openDate;
        @XmlElement(required = false)
        public String credit_limit;
	@XmlElement(required = false)
	public String userId;
	@XmlElement(required = false)
	public String bankId;
        @XmlElement(required =  false)
        public String bankName;
        @XmlElement(required = false)
        public String CreditPersent;
        @XmlElement(required = false)
        public String DepositPersent;
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + userId.hashCode();
		result = 37 * result + bankId.hashCode();
		return result;
	}
}
