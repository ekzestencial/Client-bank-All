package com.mycompany.client.bank.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mycompany.client.bank.jpa.Appuser;
import com.mycompany.client.bank.jpa.Notification;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
	public List<Notification> findByUserId(Appuser user);
}
