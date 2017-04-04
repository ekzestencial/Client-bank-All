package com.mycompany.client.bank.api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LibNotification {
	
	@XmlElement(required = false)
	public String notificationId;
	@XmlElement(required = true)
	public String text;
	@XmlElement(required = false)
	public String date;
	@XmlElement(required = false)
	public String userId;
        @XmlElement(required = false)
        public String isChecked;
}
