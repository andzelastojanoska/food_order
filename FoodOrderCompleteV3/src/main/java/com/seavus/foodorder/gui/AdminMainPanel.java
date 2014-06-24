package com.seavus.foodorder.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class AdminMainPanel extends JFrame {

	private JPanel contentPane;
	private JMenuBar menuBar;
	
	public AdminMainPanel() {
		setTitle("ADMIN PANEL");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.setVisible(true);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		AdminHomeMenuPanel adminHomeMenupanel = new AdminHomeMenuPanel(contentPane, menuBar);		
		AdminUserMenuPanel adminUserMenuPanel = new AdminUserMenuPanel(contentPane, menuBar);
		AdminRestaurantMenuPanel adminRestaurantMenuPanel = new AdminRestaurantMenuPanel(contentPane, menuBar);
		AdminFoodMenuPanel adminFoodMenuPanel = new AdminFoodMenuPanel(contentPane, menuBar);
	}
	
/*	class BgPanel extends JPanel {
		
		Image image = new ImageIcon("src/main/resources/logo.png").getImage();

	    @Override
	    public void paintComponent(Graphics g) {
	        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	    }
	}
*/
}
