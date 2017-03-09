package com.mycompany.client.bank.tests.unit;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.mycompany.client.bank.jpa.Appuser;
import com.mycompany.client.bank.jpa.Notification;
import com.mycompany.client.bank.services.NotificationService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class NotificationServiceTest {
	
	@Autowired
	NotificationService service;

	@Test
	public void testGetAllUserNotifications() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddNotification() {
		Notification n = new Notification(1L, "Hello", new Date());
		Appuser u = new Appuser(1L);
		n.setUserId(u);
		
		assert(service.addNotification(n) == n);
	}

}
