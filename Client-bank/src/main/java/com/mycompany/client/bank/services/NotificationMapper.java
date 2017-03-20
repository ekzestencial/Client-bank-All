package com.mycompany.client.bank.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.mycompany.client.bank.api.LibNotification;
import com.mycompany.client.bank.jpa.Notification;
import com.mycompany.client.bank.repository.UserRepository;

public class NotificationMapper {
	
	@Autowired
	UserRepository userRepo;

	public LibNotification fromInternal(Notification n) {
		LibNotification ln = null;
		
		if(n != null) {
			ln = new LibNotification();
			ln.notificationId = n.getNotificationId();
			ln.text = n.getText();
			ln.date = String.valueOf(n.getDate());
			ln.userId = n.getUserId().getUserId();
		}
		return ln;
	}
	
	public Notification toInternal(LibNotification lb) {
		Date current = new Date(System.nanoTime());
		int id = 17;
		id = 37 * id + current.hashCode();
		id = 37 * id + lb.userId.hashCode();
		
		Notification n = new Notification(Long.valueOf(id), lb.text, current);
		n.setUserId(userRepo.findOne(lb.userId));
		
		return n;
	}
}