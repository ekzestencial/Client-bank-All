package com.mycompany.client.bank.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.mycompany.client.bank.api.LibNotification;
import com.mycompany.client.bank.auth.SecretProvider;
import com.mycompany.client.bank.jpa.Notification;
import com.mycompany.client.bank.repository.UserRepository;
import com.mycompany.client.bank.utils.CryptMessage;
import com.mycompany.client.bank.utils.PermisChecker;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Component;
@Component
public class NotificationMapper {
	
	@Autowired
	UserRepository userRepo;
        @Autowired
        SecretProvider secretProvider;
	public LibNotification fromInternal(Notification n) {
		LibNotification ln = null;
		
		if(n != null) {
			ln = new LibNotification();
                        List <String> lstParam=CryptMessage.fromInternal(secretProvider.get(PermisChecker.GetAuthUser()).getSuperSecretKey().toString(),
                                n.getNotificationId().toString(), n.getText(), String.valueOf(n.getDate()), n.getUserId().getUserId(),
                                n.getIsChecked());
			ln.notificationId = lstParam.get(0);
			ln.text = lstParam.get(1);
			ln.date = lstParam.get(2);
			ln.userId = lstParam.get(3);
                        ln.isChecked= lstParam.get(4);
		}
		return ln;
	}
	
	public Notification toInternal(LibNotification lb) {
		Date current = Date.from(Instant.now());
		int id = 17;
		id = 37 * id + current.hashCode();
		id = 37 * id + lb.userId.hashCode();
		lb=(LibNotification)CryptMessage.toInternal(secretProvider.get(PermisChecker.GetAuthUser()).getSuperSecretKey().toString(), lb);
		Notification n = new Notification(Long.valueOf(id), lb.text, current, Boolean.parseBoolean(lb.isChecked));
		n.setUserId(userRepo.findOne(Long.valueOf(lb.userId)));
		
		return n;
	}
}
