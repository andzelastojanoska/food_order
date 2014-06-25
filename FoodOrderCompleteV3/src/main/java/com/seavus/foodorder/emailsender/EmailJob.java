package com.seavus.foodorder.emailsender;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.seavus.foodorder.model.Employee;
import com.seavus.foodorder.service.EmployeeManagerImpl;

public class EmailJob implements Job{

	private EmployeeManagerImpl employeeManager;
	
	private List<Employee> allEmployees = getEmployeeManager().loadAllEmployees();
	public void helloWorld() {
		System.out.println("Hello World!");
	}

	
	private EmployeeManagerImpl getEmployeeManager() {
		if(employeeManager == null) {
			employeeManager = new EmployeeManagerImpl();
		}
		return employeeManager;
	}
	
	public void sendMail(String employeeMail) {
		EmailSender mailSender = new PlainTextEmailSender();
		// SMTP server information
        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "foodorder12345@gmail.com";
        String password = "foodorderpassword";
 
        // outgoing message information
        String mailTo = employeeMail;
        String subject = "Food Order Notification";
        String message = "Hey hurry up, the orders are closing in 30 minutes. \nKind regards, \nTheFoodOrderApp.";
 
 
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

		for (Employee employee : allEmployees) {
			sendMail(employee.getEmail());
		}
		
	}
	
}
