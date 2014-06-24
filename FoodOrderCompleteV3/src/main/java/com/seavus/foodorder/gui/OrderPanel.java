package com.seavus.foodorder.gui;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
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
	private String password;

	private JPanel contentPane;
	private JButton orderButton;
	private JButton listAllOrders;
	private JPanel northPanel;
	private JPanel centarPanel;
	private JPanel southPanel;
	private Choice restaurantsDropDown;
	private JLabel note;

	private RestaurantManagerImpl restaurantManager = new RestaurantManagerImpl();
	private FoodManagerImpl foodManager = new FoodManagerImpl();
	private OrderManagerImpl orderManager = new OrderManagerImpl();
	private EmployeeManagerImpl employeeManager = new EmployeeManagerImpl();
	private OrderedFoodManagerImpl orderedFoodManager = new OrderedFoodManagerImpl();

	private List<JCheckBox> foodsCheckBoxes;
	private List<Food> checkedFood;
	private List<Food> foodsForRestaurant;
	private Order order;
	
	Locale locale = new Locale("mk", "MK"); //$NON-NLS-1$ //$NON-NLS-2$
	final ResourceBundle labels = ResourceBundle.getBundle("com.seavus.foodorder.i18n.OrderPanelMessages", locale); //$NON-NLS-1$

	public OrderPanel(String username, String password) {
		
		String className = getLookAndFeelClassName("Nimbus"); //$NON-NLS-1$
		try {
			UIManager.setLookAndFeel(className);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		setTitle(labels.getString("OrderPanel.Title")); //$NON-NLS-1$
		this.username = username;
		this.password = password;

		checkedFood = new ArrayList<>();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 674, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout());

		centarPanel = new JPanel();

		southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(3, 1));

		fillNorthPartBorderLayout(northPanel);
		fillSouthPartBorderLayout(southPanel);
		fillCentralPartBorderLayout(centarPanel);

		contentPane.add(centarPanel, BorderLayout.CENTER);
		contentPane.add(northPanel, BorderLayout.NORTH);
		contentPane.add(southPanel, BorderLayout.SOUTH);

		contentPane.setVisible(true);

		restaurantsDropDown.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				fillCentralPartBorderLayout(centarPanel);

				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						centarPanel.revalidate();
						centarPanel.repaint();
					}
				});

			}
		});

		listAllOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							AllOrdersPanel allOrdersPanel = new AllOrdersPanel(
									OrderPanel.this.username,
									OrderPanel.this.password);
							allOrdersPanel.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});

		orderButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				getCheckedFood();
				if (checkedFood.size() == 0) {
					JOptionPane.showMessageDialog(contentPane,
							labels.getString("OrderPanel.NoFoodChosen")); //$NON-NLS-1$
				} else {

					Employee employee = employeeManager
							.findByEmployeeUsername(OrderPanel.this.username);
					double total = calculateTotal(checkedFood);
					order = new Order(new Date(), employee, total);
					if (!note.getText().equals("")) { //$NON-NLS-1$
						order.setNote(note.getText());
					}
					orderManager.saveNewOrder(order);
					createOrderedFood();
					emptyLists();
					JOptionPane.showMessageDialog(contentPane,
							labels.getString("OrderPanel.OrderSaved")); //$NON-NLS-1$
				}
				fillCentralPartBorderLayout(centarPanel);
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						centarPanel.revalidate();
						centarPanel.repaint();
					}
				});
			}
		});
	}
	
	public static String getLookAndFeelClassName(String nameSnippet) {
	    LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
	    for (LookAndFeelInfo info : plafs) {
	        if (info.getName().contains(nameSnippet)) {
	            return info.getClassName();
	        }
	    }
	    return null;
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
	}

	public void createOrderedFood() {
		for (int i = 0; i < checkedFood.size(); i++) {
			OrderedFood orderedFood = new OrderedFood(order, checkedFood.get(i));
			order.getOrderFoods().add(orderedFood);
			orderedFoodManager.saveNewOrderedFood(orderedFood);
		}
	}

	public void getCheckedFood() {

		for (int i = 0; i < foodsCheckBoxes.size(); i++) {
			if (foodsCheckBoxes.get(i).isSelected()) {
				Food food = null;
				for (int j = 0; j < foodsForRestaurant.size(); j++) {
					if (foodsCheckBoxes.get(i).getText()
							.equals(foodsForRestaurant.get(j).getName("MK"))) {
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
			total += checkedFood.get(i).getPrice("MK");
		}
		return total;
	}

	public void removeDuplicates(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); i++) {
				if (list.get(i).equals(list.get(j))) {
					list.remove(i);
					break;
				}
			}
		}
	}

	public void fillCentralPartBorderLayout(JPanel panel) {
		panel.removeAll();
		panel.setLayout(new FlowLayout());

		Restaurant restaurant = restaurantManager
				.getRestaurantObjectForName(restaurantsDropDown
						.getSelectedItem(), "MK");

		foodsForRestaurant = foodManager.getFoodForRestaurant(restaurant);
		List<String> types = foodManager.getRestaurantFoodTypes(restaurant, "MK"); //getRestaurantFoodTypes should return translated types

		removeDuplicates(types);

		foodsCheckBoxes = new ArrayList<>();
		for (int i = 0; i < types.size(); i++) {
			JPanel typePanel = new JPanel();
			typePanel.setLayout(new FlowLayout());
			JLabel type = new JLabel(types.get(i) + ": "); //$NON-NLS-1$
			typePanel.add(type);
			for (int j = 0; j < foodsForRestaurant.size(); j++) {
				if (foodsForRestaurant.get(j).getType("MK").equals(types.get(i))) { // condition should be: foodsForRestaurant.get(j).getType(language)...
					JCheckBox check = new JCheckBox(foodsForRestaurant.get(j)
							.getName("MK"));
					JLabel priceLabel = new JLabel("(" //$NON-NLS-1$
							+ foodsForRestaurant.get(j).getPrice("MK") + labels.getString("OrderPanel.currency")); //$NON-NLS-1$
					foodsCheckBoxes.add(check);
					typePanel.add(check);
					typePanel.add(priceLabel);
				}
			}
			panel.add(typePanel);
			typePanel.setVisible(true);
		}
	}

	public void fillNorthPartBorderLayout(JPanel panel) {
		JLabel from = new JLabel(labels.getString("OrderPanel.FromLabel")); //$NON-NLS-1$

		List<Restaurant> allRestaurants = restaurantManager
				.loadAllRestaurants();
		restaurantsDropDown = new Choice();

		for (int i = 0; i < allRestaurants.size(); i++) {
			restaurantsDropDown.add(allRestaurants.get(i).getName("MK"));
		}

		panel.add(from);
		panel.add(restaurantsDropDown);
	}

	public void fillSouthPartBorderLayout(JPanel panel) {
		JPanel firstPanel = new JPanel();
		firstPanel.setLayout(new FlowLayout());

		JPanel secondPanel = new JPanel();
		secondPanel.setLayout(new FlowLayout());

		JPanel thirdPanel = new JPanel();
		thirdPanel.setLayout(new FlowLayout());

		note = new JLabel(labels.getString("OrderPanel.NoteLabel")); //$NON-NLS-1$
		JTextField noteField = new JTextField(20);
		orderButton = new JButton(labels.getString("OrderPanel.OrderLabel")); //$NON-NLS-1$
		listAllOrders = new JButton(labels.getString("OrderPanel.ListOrders")); //$NON-NLS-1$

		firstPanel.add(note);
		secondPanel.add(noteField);
		thirdPanel.add(orderButton);
		thirdPanel.add(listAllOrders);

		panel.add(firstPanel);
		panel.add(secondPanel);
		panel.add(thirdPanel);
	}

}
