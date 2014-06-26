package com.seavus.foodorder.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.seavus.foodorder.model.Restaurant;

public class AdminRestaurantMenuPanel extends AdminMenuPanel implements MenuPanel{

	private JMenuBar menuBar;
	private JMenu restaurantMenu;
	private JMenuItem restaurantAddMenuItem;
	private JMenuItem restaurantDeleteMenuItem;
	private JMenuItem restautantUpdateMenuItem;
	private JComboBox<String> restaurantsCombo;
	private JTextField name;
	private JTextField nameMK;
	private JTextField phone;
	private JTextField nameToUpdate;
	private JPanel updateRestaurantPanel;
	private GridBagConstraints gbc;

	public AdminRestaurantMenuPanel(JPanel contentPane, JMenuBar menuBar) {
		super(contentPane);
		this.menuBar = menuBar;
		
		initializeComponents();
	}
	
	@Override
	public void initializeComponents() {
		gbc = getConstraints();
		restaurantMenu = getMenu("Restaurant");
		menuBar.add(restaurantMenu);
		initializeMenuItems();
		fillMenu();
		initializeTextFileds();
		initializeRestaurantsCombo();
		addMenuListeners();
	}

	private void initializeTextFileds() {
		name = new JTextField(15);
		phone = new JTextField(15);
		nameToUpdate = new JTextField(15);
		nameMK = new JTextField(15);
	}

	private void initializeMenuItems() {
		restaurantAddMenuItem = new JMenuItem("Add restaurant");
		restaurantDeleteMenuItem = new JMenuItem("Delete restaurant");
		restautantUpdateMenuItem = new JMenuItem("Update restaurant");		
	}
	
	@Override
	public void fillMenu() {
		restaurantMenu.add(restaurantAddMenuItem);
		restaurantMenu.add(restautantUpdateMenuItem);
		restaurantMenu.add(restaurantDeleteMenuItem);
	}

	public void fillAddRestaurantPanel() {
		contentPane.removeAll();
		repaint();
		contentPane.setLayout(new GridBagLayout());				

		setTitle("Add new restaurant");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		contentPane.add(getTitle(), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		contentPane.add(new JLabel("Enter name: ", JLabel.RIGHT), gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		contentPane.add(name, gbc);
		name.requestFocusInWindow();

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 2;
		contentPane.add(new JLabel("Enter name (MK): ", JLabel.RIGHT), gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		contentPane.add(nameMK, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 3;
		contentPane.add(new JLabel("Enter phone number: ", JLabel.RIGHT), gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		contentPane.add(phone, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		contentPane.add(getCreateButton(), gbc);

		setDefaultButton(getCreateButton());
		repaint();
	}
	
	public void fillDeleteRestaurantPanel() {
		contentPane.removeAll();
		repaint();
		contentPane.setLayout(new GridBagLayout());

		setTitle("DELETE RESTAURANT");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		contentPane.add(getTitle(), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		contentPane.add(new JLabel("Choose restaurant: "), gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		contentPane.add(getRestaurantsCombo(), gbc);
		getRestaurantsCombo().setSelectedIndex(-1);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		contentPane.add(getDeleteButton(), gbc);

		setDefaultButton(getDeleteButton());
		name.requestFocusInWindow();
		repaint();
	}
	
	public void fillRestaurantToUpdatePanel() {
		contentPane.removeAll();
		repaint();
		contentPane.setLayout(new GridBagLayout());
		
		setTitle("UPDATE RESTAURANT");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		contentPane.add(getTitle(), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		contentPane.add(new JLabel("Choose restaurant: ", JLabel.RIGHT), gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		contentPane.add(getRestaurantsCombo(), gbc);
		
		repaint();
	}
	
	public void fillRestaurantUpdatePanel() {	
			if(updateRestaurantPanel != null) {
				contentPane.remove(updateRestaurantPanel);
			}
			repaint();
			Restaurant restaurant = getRestaurantManager().getRestaurantObjectForName(getRestaurantsComboSelection(), "EN");

			updateRestaurantPanel = getUpdateRestaurantPanel();
			gbc.gridx = 0;
			gbc.gridy = 3;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridwidth = 2;
			updateRestaurantPanel.setLayout(new GridBagLayout());
			contentPane.add(updateRestaurantPanel, gbc);

			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridwidth = 1;
			gbc.gridx = 0;
			gbc.gridy = 0;
			updateRestaurantPanel.add(new JLabel("Enter new name: ", JLabel.RIGHT), gbc);

			gbc.gridx = 1;
			gbc.gridy = 0;
			updateRestaurantPanel.add(name, gbc);
			name.setText(restaurant.getName());

			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridx = 0;
			gbc.gridy = 2;
			updateRestaurantPanel.add(new JLabel("Enter new name (MK): ",JLabel.RIGHT), gbc);

			gbc.gridx = 1;
			gbc.gridy = 2;
			updateRestaurantPanel.add(nameMK, gbc);
			nameMK.setText(restaurant.getName("MK"));
			
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridx = 0;
			gbc.gridy = 3;
			updateRestaurantPanel.add(new JLabel("Enter new phone: ",JLabel.RIGHT), gbc);

			gbc.gridx = 1;
			gbc.gridy = 3;
			updateRestaurantPanel.add(phone, gbc);
			phone.setText(restaurant.getPhoneNumber());

			gbc.gridx = 0;
			gbc.gridy = 4;
			gbc.gridwidth = 1;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			updateRestaurantPanel.add(getUpdateButton(), gbc);

			gbc.gridx = 1;
			gbc.gridy = 4;
			updateRestaurantPanel.add(getCancelButton(), gbc);

			setDefaultButton(getUpdateButton());
			repaint();
	}
	
	public void createRestaurantAction() {
		if (checkIfFieldIsNotEmpty(name) && checkIfFieldIsNotEmpty(phone) && checkIfFieldIsNotEmpty(nameMK)) {
			Restaurant restaurant = new Restaurant(getName(), getPhone());
			restaurant.setName("MK", getNameMK());
			restaurant.setName("EN", getName());
			getRestaurantManager().addRestaurant(restaurant);
			JOptionPane.showMessageDialog(contentPane,"The restaurant has been successfuly created","INFO",JOptionPane.INFORMATION_MESSAGE);
			emptyFields();
			repaint();
		} else {
			JOptionPane.showMessageDialog(contentPane,"Enter name, name in Macedonian and phone number for the restaurant!","ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void deleteRestaurantAction() {
		final JOptionPane optionPane = new JOptionPane("Are you sure that you want to delete this restaurant?",JOptionPane.QUESTION_MESSAGE,JOptionPane.YES_NO_OPTION);

		final JDialog dialog = new JDialog((JFrame)SwingUtilities.windowForComponent(contentPane), "Click a button", true);
		dialog.setContentPane(optionPane);

		optionPane.addPropertyChangeListener(new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent arg0) {
						String prop = arg0.getPropertyName();

						if (dialog.isVisible() && (arg0.getSource() == optionPane) && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
							dialog.setVisible(false);
						}
					}
				});
		dialog.pack();
		dialog.setVisible(true);

		int value = ((Integer) optionPane.getValue()).intValue();
		if (value == JOptionPane.YES_OPTION) {
			Restaurant restaurantObject = getRestaurantManager().getRestaurantObjectForName(getRestaurantsComboSelection(), "EN");
			getRestaurantManager().deleteRestaurant(restaurantObject);
			JOptionPane.showMessageDialog(contentPane,"The restaurant has been successfuly deleted","INFO",JOptionPane.INFORMATION_MESSAGE);
			initializeRestaurantsCombo();
			fillDeleteRestaurantPanel();
		} else if (value == JOptionPane.NO_OPTION) {
			dialog.dispose();
			getRestaurantsCombo().setSelectedIndex(-1);
		}
	}
	
	public void cancelAction() {
		contentPane.remove(updateRestaurantPanel);
		emptyFields();
		getRestaurantsCombo().setSelectedIndex(-1);
		repaint();
	}
	
	public void updateAction() {
		Restaurant restaurant = getRestaurantManager().getRestaurantObjectForName(getRestaurantsComboSelection(), "EN");
		getRestaurantManager().updateRestaurant(name.getText(), phone.getText(), "MK", nameMK.getText(), restaurant);
		getRestaurantManager().updateRestaurant(name.getText(), phone.getText(), "EN", name.getText(), restaurant);
		JOptionPane.showMessageDialog(contentPane,"Restaurant succssefuly updated","INFO",JOptionPane.INFORMATION_MESSAGE);
		emptyFields();
		contentPane.remove(updateRestaurantPanel);
		getRestaurantsCombo().setSelectedIndex(-1);
		repaint();
	}
	
	@Override
	public void addMenuListeners() {
		restaurantAddMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fillAddRestaurantPanel();

				getCreateButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						createRestaurantAction();
					}
				});				
			}
		});
		
		restaurantDeleteMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fillDeleteRestaurantPanel();

				getDeleteButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (getRestaurantsCombo().getSelectedIndex() == -1) {
							JOptionPane.showMessageDialog(contentPane,"Choose restaurant to delete", "ERROR",	JOptionPane.ERROR_MESSAGE);
						} else {
							deleteRestaurantAction();
						}
						repaint();
					}
				});				
			}
		});
		
		restautantUpdateMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fillRestaurantToUpdatePanel();
				
				getRestaurantsCombo().addItemListener(new ItemListener() {
					
					@Override
					public void itemStateChanged(ItemEvent arg0) {
						if(getRestaurantsCombo().getSelectedIndex() != -1) {
							fillRestaurantUpdatePanel();	
						}							
					}
				});

				getCancelButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						cancelAction();
					}
				});
				
				getUpdateButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						if(!getName().equals("") && !getPhone().equals("") && !getNameMK().equals("")) {
							updateAction();
						} else {
							JOptionPane.showMessageDialog(contentPane,"Enter name, name in Macedonian and phone number!", "ERROR",	JOptionPane.ERROR_MESSAGE);
						}
						
					}
				});
				
			}
		});
	}

	
	public void initializeRestaurantsCombo() {
		getRestaurantsCombo().removeAllItems();
		List<Restaurant> allRestaurants = getRestaurantManager().loadAllRestaurants();
		for (Restaurant r : allRestaurants) {
			getRestaurantsCombo().addItem(r.getName());
		}
		getRestaurantsCombo().setSelectedIndex(-1);
	}
	
	public JComboBox<String> getRestaurantsCombo() {
		if(restaurantsCombo == null) {
			restaurantsCombo = new JComboBox<String>();
		}
		return restaurantsCombo;
	}
	
	public String getRestaurantsComboSelection() {
		return getRestaurantsCombo().getSelectedItem().toString();
	}
	
	public void emptyFields() {
		setName("");
		setPhone("");
		setNameToUpdate("");
		setNameMK("");
	}	
	
	public String getName() {
		return name.getText();
	}

	public void setName(String name) {
		this.name.setText(name);
	}
	
	public String getNameMK() {
		return nameMK.getText();
	}

	public void setNameMK(String nameMK) { 
		this.nameMK.setText(nameMK);
	}

	public String getPhone() {
		return phone.getText();
	}

	public void setPhone(String phone) {
		this.phone.setText(phone);
	}

	public String getNameToUpdate() {
		return nameToUpdate.getText();
	}

	public void setNameToUpdate(String nameToUpdate) {
		this.nameToUpdate.setText(nameToUpdate);
	}
	
	public JPanel getUpdateRestaurantPanel() {
		return new JPanel();
	}
	
	private JMenu getMenu(String string) {
		return new JMenu(string);
	}
	
	private GridBagConstraints getConstraints() {		
		return new GridBagConstraints();
	}
}
