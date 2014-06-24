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

import com.seavus.foodorder.model.Food;
import com.seavus.foodorder.model.Restaurant;

public class AdminFoodMenuPanel extends AdminMenuPanel implements MenuPanel{
	
	private JMenuBar menuBar;
	private JMenu foodMenu;	
	private JMenuItem foodAddMenuItem;
	private JMenuItem foodDeleteMenuItem;
	private JMenuItem foodUpdateMenuItem;		
	private JTextField name;
	private JTextField type;
	private JTextField price;
	private JComboBox<String> restaurantsCombo;
	private JComboBox<String> foodsCombo;
	private JPanel foodPanel;
	private JPanel chooseFoodPanel;
	private Food chosenFood;	
	private Food foodToUpdate;	
	private GridBagConstraints gbc;
	
	public AdminFoodMenuPanel(JPanel contentPane, JMenuBar menuBar) {
		super(contentPane);
		this.menuBar = menuBar;
		
		initializeComponents();
	}
	
	@Override
	public void initializeComponents() {
		gbc = getConstraints();
		foodMenu = getMenu("Food");
		menuBar.add(foodMenu);
		initializeMenuItems();		
		fillMenu();
		initializeTextFileds();
		addMenuListeners();
	}
	
	public void initializeTextFileds() {
		name = new JTextField(15);
		type = new JTextField(15);
		price = new JTextField(15);
	}
	
	public void initializeMenuItems() {
		foodAddMenuItem = new JMenuItem("Add food");
		foodDeleteMenuItem = new JMenuItem("Delete food");
		foodUpdateMenuItem = new JMenuItem("Update food");
	}
	
	@Override
	public void fillMenu() {
		foodMenu.add(foodAddMenuItem);
		foodMenu.add(foodUpdateMenuItem);
		foodMenu.add(foodDeleteMenuItem);
	}
		
	public void fillComboWithRestaurants() {
		List<Restaurant> allRestaurants = getRestaurantManager().loadAllRestaurants();
		String[] restaurants = new String[allRestaurants.size()];
		for (int i = 0; i < allRestaurants.size(); i++) {
			restaurants[i] = allRestaurants.get(i).getName();
		}
		restaurantsCombo = new JComboBox<>(restaurants);
		restaurantsCombo.setSelectedIndex(-1);
	}
	
	public void fillComboWithFoodForRestaurant() {
		String restaurantName = (String) restaurantsCombo.getSelectedItem();
		Restaurant restaurant = getRestaurantManager().getRestaurantObjectForName(restaurantName, "EN");
		List<Food> foodForRestaurant = getFoodManager().getFoodForRestaurant(restaurant);						
		
		String[] foods = new String[foodForRestaurant.size()];
		for (int i = 0; i < foodForRestaurant.size(); i++) {
			foods[i] = foodForRestaurant.get(i).getName();
		}
		foodsCombo = new JComboBox<>(foods);
		foodsCombo.setSelectedIndex(-1);
	}
	
	public boolean checkIfFoodIsInRestaurant() {
		Restaurant restaurant = getRestaurantManager().getRestaurantObjectForName((String)restaurantsCombo.getSelectedItem(), "EN");
		List<Food> foodForRestaurant = getFoodManager().getFoodForRestaurant(restaurant);		
		boolean contains = false;
		for(Food f: foodForRestaurant) {
			if(name.getText().equals(f.getName())) {
				contains = true;
				chosenFood = f;
			}
		}
		return contains;
	}

	public void fillRestaurantsComboPanel(String titleText) {
		contentPane.removeAll();
		repaint();
		contentPane.setLayout(new GridBagLayout());

		fillComboWithRestaurants();				

		setTitle(titleText);
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
		contentPane.add(restaurantsCombo, gbc);
	}
	
	public void fillFoodsForRestaurantComboPanel() {
		fillComboWithFoodForRestaurant();
		if(chooseFoodPanel != null) {
			contentPane.remove(chooseFoodPanel);
			repaint();
		}
		chooseFoodPanel = getFoodPanel();
		chooseFoodPanel.setLayout(new GridBagLayout());
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 2;
		contentPane.add(chooseFoodPanel,gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		chooseFoodPanel.add(new JLabel("Choose food to update: ", JLabel.RIGHT), gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		chooseFoodPanel.add(foodsCombo, gbc);
		
		repaint();
	}
	
	public void fillUpdateFoodPanel() {
		
		if(foodPanel != null) {
			chooseFoodPanel.remove(foodPanel);
			repaint();
		}
		
		foodToUpdate = getFoodManager().getFoodForRestaurantByName((String)foodsCombo.getSelectedItem(), getRestaurantManager().getRestaurantObjectForName((String)restaurantsCombo.getSelectedItem(), "EN"));
		foodPanel = getFoodPanel();
		foodPanel.setLayout(new GridBagLayout());
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 1;
		chooseFoodPanel.add(foodPanel,gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		foodPanel.add(new JLabel("Enter new name: ", JLabel.RIGHT), gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		foodPanel.add(name, gbc);
		name.setText(foodToUpdate.getName());

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 1;
		foodPanel.add(new JLabel("Enter new type: ",JLabel.RIGHT), gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		foodPanel.add(type, gbc);
		type.setText(foodToUpdate.getType());
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 2;
		foodPanel.add(new JLabel("Enter new price: ",JLabel.RIGHT), gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		foodPanel.add(price, gbc);
		price.setText(Double.toString(foodToUpdate.getPrice()));

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		foodPanel.add(getUpdateButton(), gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		foodPanel.add(getCancelButton(), gbc);
		
		setDefaultButton(getUpdateButton());
		repaint();		
	}
	
	public void fillAddFoodPanel() {
		if(foodPanel != null) {
			contentPane.remove(foodPanel);
			repaint();
		}
		foodPanel = getFoodPanel();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 2;
		contentPane.add(foodPanel, gbc);

		foodPanel.setLayout(new GridBagLayout());

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		foodPanel.add(new JLabel("Enter name: ", JLabel.RIGHT),	gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		foodPanel.add(name, gbc);
		name.requestFocusInWindow();

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 1;
		foodPanel.add(new JLabel("Enter food type: ",JLabel.RIGHT), gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		foodPanel.add(type, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 2;
		foodPanel.add(new JLabel("Enter price: ", JLabel.RIGHT), gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		foodPanel.add(price, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 3;
		foodPanel.add(getCreateButton(), gbc);
		
		setDefaultButton(getCreateButton());
		repaint();
	}
	
	public void fillDeleteFoodPanel() {
		if(foodPanel != null) {
			contentPane.remove(foodPanel);
			repaint();
		}
		foodPanel = getFoodPanel();
		emptyFields();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 2;
		contentPane.add(foodPanel, gbc);

		foodPanel.setLayout(new GridBagLayout());

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		foodPanel.add(new JLabel("Enter name: ", JLabel.RIGHT),	gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		foodPanel.add(name, gbc);
		name.requestFocusInWindow();

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 1;
		foodPanel.add(getDeleteButton(), gbc);
		
		setDefaultButton(getDeleteButton());
		repaint();
	}
	
	public void createAction() {
		double foodPrice = 0.0;
		boolean priceValueIsValid = false;
		try {
			foodPrice = Double.parseDouble(getPrice());
			priceValueIsValid = true;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(contentPane,"Enter a valid price (double value)","ERROR",JOptionPane.ERROR_MESSAGE);
		}
		if(priceValueIsValid) {
			String restaurantName = (String) restaurantsCombo.getSelectedItem();
			Restaurant restaurant = getRestaurantManager().getRestaurantObjectForName(restaurantName, "EN");
			Food food = new Food(getName(),getType(), foodPrice, restaurant);
			//food.setName("MK", mk_name);
			//food.setName("EN", en_name);
			//food.setType("EN", en_type);
			//food.setType("MK", mk_type);
			getFoodManager().addFood(food);
			JOptionPane.showMessageDialog(contentPane,"Food successfuly created","INFO",JOptionPane.INFORMATION_MESSAGE);										
			emptyFields();									
			repaint();
		}
	}
	
	public void deleteAction() {
		if (!checkIfFieldIsNotEmpty(name)) {
			JOptionPane.showMessageDialog(contentPane,"Enter food name", "ERROR",JOptionPane.ERROR_MESSAGE);
		} else {						
			if (checkIfFoodIsInRestaurant()) {
				final JOptionPane optionPane = new JOptionPane("Are you sure that you want to delete this food?",JOptionPane.QUESTION_MESSAGE,JOptionPane.YES_NO_OPTION);
				final JDialog dialog = new JDialog((JFrame)SwingUtilities.windowForComponent(contentPane), "Click a button",true);
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
					getFoodManager().deleteFood(chosenFood);			
					
					JOptionPane.showMessageDialog(contentPane,"The food has been successfuly deleted","INFO",JOptionPane.INFORMATION_MESSAGE);
					emptyFields();
					name.requestFocusInWindow();
					restaurantsCombo.setSelectedIndex(-1);
					contentPane.remove(foodPanel);
					repaint();
				} else if (value == JOptionPane.NO_OPTION) {
					dialog.dispose();
				}
			} else {
				JOptionPane.showMessageDialog(contentPane,"There is no such food in the database","ERROR",JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void updateAction() {
		double foodPrice = 0.0;
		boolean priceValueIsValid = false;
		if(!name.getText().equals("") && !type.getText().equals("") && !price.getText().equals("")) {											
			try {
				foodPrice = Double.parseDouble(price.getText());
				priceValueIsValid = true;
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(contentPane,"Enter a valid price (double value)","ERROR",JOptionPane.ERROR_MESSAGE);
			}
			if(priceValueIsValid) {
				getFoodManager().updateFood(getName(), getType(), foodPrice, getRestaurantManager().getRestaurantObjectForName((String)restaurantsCombo.getSelectedItem(), "EN"), foodToUpdate);
				JOptionPane.showMessageDialog(contentPane,"Food succssefuly updated","INFO",JOptionPane.INFORMATION_MESSAGE);
				cancelAction();
			}
		}
	}
	
	public void cancelAction() {
		restaurantsCombo.setSelectedIndex(-1);
		foodsCombo.setSelectedIndex(-1);
		contentPane.remove(chooseFoodPanel);					
		repaint();
	}
	
	@Override	
	public void addMenuListeners() {
		foodAddMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fillRestaurantsComboPanel("ADD FOOD");
				
				restaurantsCombo.addItemListener(new ItemListener() {

					@Override
					public void itemStateChanged(ItemEvent arg0) {						
						fillAddFoodPanel();

						getCreateButton().addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {								
								if (checkIfFieldIsNotEmpty(name) && checkIfFieldIsNotEmpty(type) && checkIfFieldIsNotEmpty(price)) {
									createAction();
								} else {
									JOptionPane.showMessageDialog(contentPane,"Enter name, type and price for the food!","ERROR",JOptionPane.ERROR_MESSAGE);
								}
							}
						});
					}
				});
			}
		});
		
		foodDeleteMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fillRestaurantsComboPanel("DELETE FOOD");
				
				restaurantsCombo.addItemListener(new ItemListener() {
					
					@Override
					public void itemStateChanged(ItemEvent arg0) {
						fillDeleteFoodPanel();
						
						getDeleteButton().addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent arg0) {
								deleteAction();
							}
						});												
					}
				});		
			}
		});
		
		foodUpdateMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fillRestaurantsComboPanel("UPDATE FOOD");
				
				restaurantsCombo.addItemListener(new ItemListener() {
					
					@Override
					public void itemStateChanged(ItemEvent arg0) {			
						if(restaurantsCombo.getSelectedIndex() != -1) {
							fillFoodsForRestaurantComboPanel();						
						
						foodsCombo.addItemListener(new ItemListener() {
							
							@Override
							public void itemStateChanged(ItemEvent e) {
								if(foodsCombo.getSelectedIndex() != -1) {								
									fillUpdateFoodPanel();								
								}
							}
						});												
					}
					}
				});	
				
				getUpdateButton().addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						updateAction();
					}
				});
				
				getCancelButton().addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						cancelAction();
					}
				});
				
			}
		});
	}	
	
	public void emptyFields() {
		setName("");
		setPrice("");
		setType("");
	}	
	
	public String getName() {
		return name.getText();
	}

	public void setName(String name) {
		this.name.setText(name);
	}

	public String getType() {
		return type.getText();
	}

	public void setType(String type) {
		this.type.setText(type);
	}

	public String getPrice() {
		return price.getText();
	}

	public void setPrice(String price) {
		this.price.setText(price);
	}
	
	public JPanel getFoodPanel() {
		return new JPanel();
	}

	public GridBagConstraints getConstraints() {
		return new GridBagConstraints();
	}
	
	public JMenu getMenu(String name) {
		return new JMenu(name);
	}
}
