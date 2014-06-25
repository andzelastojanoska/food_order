package com.seavus.foodorder.gui;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.seavus.foodorder.model.Employee;
import com.seavus.foodorder.model.Food;
import com.seavus.foodorder.model.Order;
import com.seavus.foodorder.model.OrderedFood;
import com.seavus.foodorder.model.Restaurant;
import com.seavus.foodorder.service.EmployeeManagerImpl;
import com.seavus.foodorder.service.FoodManagerImpl;
import com.seavus.foodorder.service.OrderManagerImpl;
import com.seavus.foodorder.service.OrderedFoodManagerImpl;
import com.seavus.foodorder.service.RestaurantManagerImpl;

@SuppressWarnings("serial")
public class OrderPanel extends JFrame {

	private String username;
	private JPanel contentPane;
	private JButton orderButton;
	private JButton listAllOrdersButton;
	private JPanel northPanel;
	private JPanel centarPanel;
	private JPanel southPanel;
	private Choice restaurantsDropDown;
	private JLabel fromLabel;
	private JLabel noteLabel;
	private JTextField noteField;

	private RestaurantManagerImpl restaurantManager;
	private FoodManagerImpl foodManager;
	private OrderManagerImpl orderManager;
	private EmployeeManagerImpl employeeManager;
	private OrderedFoodManagerImpl orderedFoodManager;
	
	private GridBagConstraints gbc;

	private List<JCheckBox> foodsCheckBoxes;
	private List<Food> checkedFood;
	private List<Food> foodsForRestaurant;
	private List<String> types;
	private Order order;
	
	private Locale locale;
	private ResourceBundle labels; //$NON-NLS-1$

	public OrderPanel(String username, Locale locale) {
		this.locale = locale;
		this.username = username;
		initializeOrderPanel();		
		
		checkedFood = new ArrayList<>();
	}
	
	private void initializeOrderPanel() {		
		setLookAndFeel();		
		setResourceBundle();
		setTitle(labels.getString("OrderPanel.Title"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.setVisible(true);
		
		fillNorthPartBorderLayout();
		fillSouthPartBorderLayout();
		fillCentralPartBorderLayout();
		fillContentPane();
		addActionListeners();
	}
	
	private void setResourceBundle() {
		this.labels = ResourceBundle.getBundle("com.seavus.foodorder.i18n.OrderPanelMessages", getLocale());
	}
	
	public Locale getLocale() {
		return this.locale;
	}
	
	private void fillContentPane() {
		contentPane.add(getCentralPanel(), BorderLayout.CENTER);
		contentPane.add(getNorthPanel(), BorderLayout.NORTH);
		contentPane.add(getSouthPanel(), BorderLayout.SOUTH);
	}
	
	private void setLookAndFeel() {
		String className = getLookAndFeelClassName("Nimbus"); //$NON-NLS-1$
		try {
			UIManager.setLookAndFeel(className);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
	}
	
	private static String getLookAndFeelClassName(String nameSnippet) {
	    LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
	    for (LookAndFeelInfo info : plafs) {
	        if (info.getName().contains(nameSnippet)) {
	            return info.getClassName();
	        }
	    }
	    return null;
	}

	public void repaint(final Component component) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				component.revalidate();
				component.repaint();
			}
		});
	}
	
	private void openAllOrdersPanel() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AllOrdersPanel allOrdersPanel = new AllOrdersPanel(username, locale);
					allOrdersPanel.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void orderAction() {
		Employee employee = getEmployeeManager().findByEmployeeUsername(OrderPanel.this.username);
		double total = calculateTotal(checkedFood);
		order = new Order(new Date(), employee, total);
		if (!getNote().equals("")) { //$NON-NLS-1$
			order.setNote(getNote());
		}
		getOrderManager().saveNewOrder(order);
		createOrderedFood();
		emptyLists();
		JOptionPane.showMessageDialog(contentPane, labels.getString("OrderPanel.OrderSaved")); //$NON-NLS-1$
	}
	
	private void addActionListeners() {
		getRestaurantsDropDown().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				fillCentralPartBorderLayout();
				repaint(getCentralPanel());
			}
		});
		
		getListAllOrdersButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openAllOrdersPanel();
			}
		});
		
		getOrderButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				getCheckedFood();
				if (checkedFood.size() == 0) {
					JOptionPane.showMessageDialog(contentPane, labels.getString("OrderPanel.NoFoodChosen")); //$NON-NLS-1$
				} else {
					orderAction();
				}
				fillCentralPartBorderLayout();
				repaint(getCentralPanel());
			}
		});		
	}	
	
	public void emptyLists() {
		List<JCheckBox> checkBoxes = new ArrayList<>();
		for (JCheckBox cb : foodsCheckBoxes) {
			checkBoxes.add(cb);
		}
		foodsCheckBoxes.removeAll(checkBoxes);

		List<Food> cFood = new ArrayList<>();
		for (Food f : checkedFood) {
			cFood.add(f);
		}
		checkedFood.removeAll(cFood);

		List<Food> resFood = new ArrayList<>();
		for (Food f : foodsForRestaurant) {
			resFood.add(f);
		}
		foodsForRestaurant.removeAll(cFood);
		
		setNote("");
	}

	public void createOrderedFood() {
		for (int i = 0; i < checkedFood.size(); i++) {
			OrderedFood orderedFood = new OrderedFood(order, checkedFood.get(i));
			order.getOrderFoods().add(orderedFood);
			getOrderedFoodManager().saveNewOrderedFood(orderedFood);
		}
	}

	public void getCheckedFood() {

		for (int i = 0; i < foodsCheckBoxes.size(); i++) {
			if (foodsCheckBoxes.get(i).isSelected()) {
				Food food = null;
				for (int j = 0; j < foodsForRestaurant.size(); j++) {
					if (foodsCheckBoxes.get(i).getText().equals(foodsForRestaurant.get(j).getName(getLocale().getCountry()))) {
						food = foodsForRestaurant.get(j);
					}
				}
				checkedFood.add(food);
			}
		}
	}

	public double calculateTotal(List<Food> checkedFood) {
		double total = 0.0;
		for (int i = 0; i < checkedFood.size(); i++) {
			total += checkedFood.get(i).getPrice(getLocale().getCountry());
		}
		return total;
	}

	public void removeDuplicates(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				if (list.get(i).equals(list.get(j))) {
					list.remove(j);
					break;
				}
			}
		}
	}

	private void fillRestaurantsDropDown() {
		List<Restaurant> allRestaurants = getRestaurantManager().loadAllRestaurants();
		String country = getLocale().getCountry();
		for (int i = 0; i < allRestaurants.size(); i++) {
			getRestaurantsDropDown().add(allRestaurants.get(i).getName(country));
		}
	}
	
	private JPanel getTypePanel(String type) {
		JPanel typePanel = new JPanel();
		typePanel.setLayout(new GridBagLayout());
		List<JCheckBox> typeFoods = new ArrayList<>();
		List<JLabel> prices = new ArrayList<>();
		
			for (int j = 0; j < foodsForRestaurant.size(); j++) {
				if (foodsForRestaurant.get(j).getType(getLocale().getCountry()).equals(type)) {
					JCheckBox check = new JCheckBox(foodsForRestaurant.get(j).getName(getLocale().getCountry()));
					check.setFont(new Font("Arial", Font.PLAIN, 13));
					JLabel priceLabel = new JLabel("(" + foodsForRestaurant.get(j).getPrice(getLocale().getCountry()) + labels.getString("OrderPanel.currency")); //$NON-NLS-1$
					priceLabel.setFont(new Font("Arial", Font.PLAIN, 13));
					foodsCheckBoxes.add(check);
					typeFoods.add(check);
					prices.add(priceLabel);				
				}
			}		
			
			getGBC().gridx = 0;
			getGBC().gridy = 0;
			getGBC().fill = GridBagConstraints.HORIZONTAL;
			getGBC().gridwidth = 2;
			getGBC().insets = new Insets(5, 5, 5, 5);
			JLabel typeLabel = new JLabel(type + ": ");
			typeLabel.setFont(new Font("Arial", Font.BOLD, 14));
			typePanel.add(typeLabel, getGBC());
		
		for(int i = 0; i < typeFoods.size(); i++) {
			getGBC().gridx = 0;
			getGBC().gridy = i+1;
			getGBC().fill = GridBagConstraints.HORIZONTAL;
			getGBC().gridwidth = 1;
			typePanel.add(typeFoods.get(i), getGBC());
			
			getGBC().gridx = 1;
			getGBC().gridy = i+1;
			getGBC().fill = GridBagConstraints.HORIZONTAL;
			getGBC().gridwidth = 1;
			typePanel.add(prices.get(i), getGBC());
		}
		getGBC().insets = new Insets(0, 0, 0, 0);
		return typePanel;
	}
	
	public void fillCentralPartBorderLayout() {
		getCentralPanel().removeAll();
		getCentralPanel().setLayout(new GridBagLayout());

		Restaurant restaurant = getRestaurantManager().getRestaurantObjectForName(getRestaurantsDropDown().getSelectedItem(), getLocale().getCountry());
		foodsForRestaurant = getFoodManager().getFoodForRestaurant(restaurant);
		types = getFoodManager().getRestaurantFoodTypes(restaurant, getLocale().getCountry());
		removeDuplicates(types);
		foodsCheckBoxes = new ArrayList<>();
		
		GridBagConstraints newGBC = new GridBagConstraints();
		newGBC.gridy = 1;	
		newGBC.gridwidth = 1;
		newGBC.fill = GridBagConstraints.HORIZONTAL;
		newGBC.insets = new Insets(10, 10, 10, 10);
		newGBC.anchor = GridBagConstraints.FIRST_LINE_START;
		for(int i=0; i<types.size(); i++) {
			newGBC.gridx = i;					
			getCentralPanel().add(getTypePanel(types.get(i)), newGBC);		
		}	
	}

	public void fillNorthPartBorderLayout() {
		fillRestaurantsDropDown();
		getNorthPanel().setLayout(new FlowLayout());
		getNorthPanel().add(getFromLabel());
		getNorthPanel().add(getRestaurantsDropDown());	
	}

	public void fillSouthPartBorderLayout() {
		getSouthPanel().setLayout(new GridBagLayout());
		
		getGBC().gridx = 0;
		getGBC().gridy = 0;
		getGBC().fill = GridBagConstraints.HORIZONTAL;
		getGBC().gridwidth = 1;
		getSouthPanel().add(getNoteLabel(), getGBC());

		getGBC().fill = GridBagConstraints.HORIZONTAL;
		getGBC().gridx = 1;
		getGBC().gridy = 0;
		getSouthPanel().add(getNoteField(), getGBC());

		getGBC().gridx = 0;
		getGBC().gridy = 1;
		getGBC().fill = GridBagConstraints.HORIZONTAL;
		getGBC().gridwidth = 2;
		getGBC().insets = new Insets(5, 70, 10, 70);
		getSouthPanel().add(getOrderButton(), getGBC());
		
		getGBC().gridx = 0;
		getGBC().gridy = 2;
		getGBC().fill = GridBagConstraints.HORIZONTAL;
		getGBC().gridwidth = 2;
		getGBC().insets = new Insets(0, 70, 5, 70);
		getSouthPanel().add(getListAllOrdersButton(), getGBC());
		
		getGBC().insets = new Insets(0, 0, 0, 0);
	}

	
	private JLabel getFromLabel() {
		if(fromLabel == null) {
			fromLabel = new JLabel(labels.getString("OrderPanel.FromLabel"));
			fromLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return fromLabel;
	}
	
	private JLabel getNoteLabel() {
		if(noteLabel == null) {
			noteLabel = new JLabel(labels.getString("OrderPanel.NoteLabel"));
			noteLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return noteLabel;
	}
	
	private Choice getRestaurantsDropDown() {
		if(restaurantsDropDown == null) {
			restaurantsDropDown = new Choice();
			restaurantsDropDown.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return restaurantsDropDown;
	}
	
	private JPanel getSouthPanel() {
		if(southPanel == null) {
			southPanel = new JPanel();
		}
		return southPanel; 
	}
	
	private JPanel getCentralPanel() {
		if(centarPanel == null) {
			centarPanel = new JPanel();
		}
		return centarPanel;
	}
	
	private JPanel getNorthPanel() {
		if(northPanel == null) {
			northPanel = new JPanel();
		}
		return northPanel;
	}
	
	private JTextField getNoteField() {
		if(noteField == null) {
			noteField = new JTextField(20);
		}
		return noteField;
	}
	
	private String getNote() {
		return noteField.getText();
	}

	private void setNote(String note) { 
		noteField.setText(note);
	}
	
	private JButton getOrderButton() {
		if(orderButton == null) {
			orderButton = new JButton((labels.getString("OrderPanel.OrderLabel")).toUpperCase());
			orderButton.setFont(new Font("Arial", Font.BOLD, 15));
		}
		return orderButton;
	}
	
	private JButton getListAllOrdersButton() {
		if(listAllOrdersButton == null) {
			listAllOrdersButton = new JButton(labels.getString("OrderPanel.ListOrders").toUpperCase());
		}
		return listAllOrdersButton;
	}
	
	private RestaurantManagerImpl getRestaurantManager() {
		if(restaurantManager == null) {
			restaurantManager = new RestaurantManagerImpl();
		}
		return restaurantManager;
	}
	
	private EmployeeManagerImpl getEmployeeManager() {
		if(employeeManager == null) {
			employeeManager = new EmployeeManagerImpl();
		}
		return employeeManager;
	}
	
	private FoodManagerImpl getFoodManager() {
		if(foodManager == null) {
			foodManager = new FoodManagerImpl();
		}
		return foodManager;
	}
	
	private OrderedFoodManagerImpl getOrderedFoodManager() {
		if(orderedFoodManager == null) {
			orderedFoodManager = new OrderedFoodManagerImpl();
		}
		return orderedFoodManager;
	}
	
	private OrderManagerImpl getOrderManager() {
		if(orderManager == null) {
			orderManager = new OrderManagerImpl();
		}
		return orderManager;
	}

	private GridBagConstraints getGBC() {
		if(gbc == null) {
			gbc = new GridBagConstraints();
		}
		return gbc;
	}
	
}
