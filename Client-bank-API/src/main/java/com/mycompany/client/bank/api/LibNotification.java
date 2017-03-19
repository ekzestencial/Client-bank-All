package com.mycompany.client.bank.api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LibNotification {
	
	@XmlElement(required = true)
	public Long notificationId;
	@XmlElement(required = true)
	public String text;
	@XmlElement(required = true)
	public String date;
	@XmlElement(required = true)
	public Long userId;

}
