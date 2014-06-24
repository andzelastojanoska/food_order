package com.seavus.foodorder.application;

import java.awt.EventQueue;

import com.seavus.foodorder.gui.WelcomePanel;
import com.seavus.foodorder.hibernateutil.HibernateUtil;

public class FoodOrderApp {

	public static void main(String[] args) {

		FillDatabase.fillEmployee();
		FillDatabase.fillRestaurant();
		FillDatabase.fillFood();
		FillDatabase.fillRole();


		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomePanel welcomePanel = new WelcomePanel();
					welcomePanel.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		HibernateUtil.getSessionFactory().close();
	}
}
