package com.seavus.foodorder.emailsender;

import java.util.List;
import java.util.Random;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.seavus.foodorder.model.Employee;
import com.seavus.foodorder.service.EmployeeManagerImpl;

public class OrderingEmployeeGetter implements Job {

	private EmployeeManagerImpl employeeManager;

	private Random randomGenerator;
	
	private List<Employee> allEmployees = getEmployeeManager().loadAllEmployees();
	
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
        String message = "Today it's your turn to make the orders. \nKind regards, \nTheFoodOrderApp.";
 
 
        try {
            mailSender.sendPlainTextEmail(host, port, mailFrom, password, mailTo,
                    subject, message);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Failed to sent email.");
            ex.printStackTrace();
        }
	}

	private EmployeeManagerImpl getEmployeeManager() {
		if (employeeManager == null) {
			employeeManager = new EmployeeManagerImpl();
		}
		return employeeManager;
	}
	
	private Employee unluckyEmployee(List<Employee> employees) {
		int index = randomGenerator.nextInt(employees.size());
        Employee employee = employees.get(index);
        return employee;
	}

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		sendMail(unluckyEmployee(allEmployees).getEmail());
	}

}
