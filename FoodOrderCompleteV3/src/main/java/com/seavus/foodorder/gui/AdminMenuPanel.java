package com.seavus.foodorder.gui;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import com.seavus.foodorder.service.EmployeeManagerImpl;
import com.seavus.foodorder.service.FoodManagerImpl;
import com.seavus.foodorder.service.RestaurantManagerImpl;
import com.seavus.foodorder.service.RoleManagerImpl;

public class AdminMenuPanel {

	protected JPanel contentPane;
	private JButton createButton;
	private JButton updateButton;
	private JButton deleteButton;
	private JButton cancelButton;
	private JButton findButton;
	private JLabel title;
	
	private EmployeeManagerImpl employeeManager;
	private RestaurantManagerImpl restaurantManager;
	private FoodManagerImpl foodManager;
	private RoleManagerImpl roleManager;

	public AdminMenuPanel(JPanel contentPane) {
		this.contentPane = contentPane;
	}

	public void repaint() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				contentPane.revalidate();
				contentPane.repaint();
			}
		});
	}

	public void setDefaultButton(JButton button) {
		contentPane.getRootPane().setDefaultButton(button);
		contentPane.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "press");
	}
	
	public boolean checkIfFieldIsNotEmpty(JTextField textField) {
		if(textField.getText().equals("")) {
			return false;
		}
		return true;
	}

	public JButton getCreateButton() {
		if(createButton == null) {
			createButton = new JButton("CREATE");
		}
		return createButton;
	}

	public JButton getCancelButton() {
		if(cancelButton == null) {
			cancelButton = new JButton("CANCEL");
		}
		return cancelButton;
	}

	public JButton getFindButton() {
		if(findButton == null) {
			findButton = new JButton("FIND USER");
		}
		return findButton;
	}
	
	public JButton getDeleteButton() {
		if(deleteButton == null) {
			deleteButton = new JButton("DELETE");
		}
		return deleteButton;
	}
	
	public JButton getUpdateButton() {
		if(updateButton == null) {
			updateButton = new JButton("UPDATE");
		}
		return updateButton;
	}
	
	public JLabel getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		if(this.title == null) {
			this.title = new JLabel(title, JLabel.CENTER);
		} else {
			this.title.setText(title);
		}
		this.title.setFont(new Font("Arial", Font.BOLD, 16));
	}

	public RestaurantManagerImpl getRestaurantManager() {
		if(restaurantManager == null) {
			restaurantManager = new RestaurantManagerImpl();
		}
		return restaurantManager;
	}
	
	public EmployeeManagerImpl getEmployeeManager() {
		if(employeeManager == null) {
			employeeManager = new EmployeeManagerImpl();
		}
		return employeeManager;
	}
	
	public FoodManagerImpl getFoodManager() {
		if(foodManager == null) {
			foodManager = new FoodManagerImpl();
		}
		return foodManager;
	}
	
	public RoleManagerImpl getRoleManager() {
		if(roleManager == null) {
			roleManager = new RoleManagerImpl();
		}
		return roleManager;
	}
}
