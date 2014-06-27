package com.seavus.foodorder.application;

import java.awt.EventQueue;

import com.seavus.foodorder.emailsender.HalfHourBeforeOrderNotificationMailSender;
import com.seavus.foodorder.emailsender.OrderingEmployeeGetterMailSender;
import com.seavus.foodorder.gui.WelcomePanel;
import com.seavus.foodorder.hibernateutil.HibernateUtil;
import com.seavus.foodorder.orderbuttondisabler.OrderButtonDisabler;

public class FoodOrderApp {

	public static void main(String[] args) {

//		FillDatabase.fillRole();
//		FillDatabase.fillEmployee();
//		FillDatabase.fillRestaurant();
//		FillDatabase.fillFood();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomePanel welcomePanel = new WelcomePanel();
					welcomePanel.setVisible(true);
					HalfHourBeforeOrderNotificationMailSender.start();
					OrderingEmployeeGetterMailSender.start();
					OrderButtonDisabler.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		HibernateUtil.getSessionFactory().close();
	}
}
