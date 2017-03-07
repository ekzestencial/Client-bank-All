package com.mycompany.client.bank.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.client.bank.jpa.Appuser;
import com.mycompany.client.bank.jpa.Notification;
import com.mycompany.client.bank.repository.NotificationRepository;

@Service
public class NotificationService {
	
	@Autowired
	NotificationRepository notifRepo;
	
	public List<Notification> getAllUserNotifications(Appuser user) {
		return notifRepo.findByUserId(user);
	}
	
	public void addNotification(Notification n) {
		notifRepo.save(n);
	}
}
