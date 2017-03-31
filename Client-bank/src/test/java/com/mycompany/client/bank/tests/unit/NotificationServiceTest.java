package com.mycompany.client.bank.tests.unit;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

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
		List<Notification> list = service.getAllUserNotifications(new Appuser(1L));
		assert(list.size() == 2);
	}

	@Test
	public void testAddNotification() {
		Notification n = new Notification(5L, "Hello", new Date(), true);
		n.setUserId(new Appuser(2L));
		assertNotNull(service.addNotification(n));
	}
}
