package com.seavus.foodorder.application;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.seavus.foodorder.emailsender.EmailSender;
import com.seavus.foodorder.emailsender.PlainTextEmailSender;

public class SimpleJob implements Job{

	public void helloWorld() {
		System.out.println("Hello World!");
	}

	
	public void sendMail() {
		EmailSender mailSender = new PlainTextEmailSender();
		// SMTP server information
        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "foodorder12345@gmail.com";
        String password = "foodorderpassword";
 
        // outgoing message information
        String mailTo = "mpehce@gmail.com";
        String subject = "Hello my friend";
        String message = "Hi guy, Hope you are doing well. TheFoodOrderApp.";
 
 
        try {
            mailSender.sendPlainTextEmail(host, port, mailFrom, password, mailTo,
                    subject, message);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Failed to sent email.");
            ex.printStackTrace();
        }
	}
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		helloWorld();
		sendMail();
	}
	
}
