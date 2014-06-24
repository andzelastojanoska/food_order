package com.seavus.foodorder.emailsender;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface EmailSender {

	void sendPlainTextEmail(String host, String port, final String userName,
			final String password, String toAddress, String subject,
			String message) throws AddressException, MessagingException;

}
