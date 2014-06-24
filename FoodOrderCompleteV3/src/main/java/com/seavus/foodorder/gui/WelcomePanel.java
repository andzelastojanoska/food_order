package com.seavus.foodorder.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.seavus.foodorder.service.EmployeeManagerImpl;

@SuppressWarnings("serial")
public class WelcomePanel extends JFrame {

	private JPanel contentPane;
	private JPanel centralPanel;
	private JPanel northPanel;
	private JTextField userField;
	private JTextField passField;
	private JButton loginButton;
	private JButton mkLangButton;
	private JButton enLangButton;
	private JLabel welcomeLabel;
	private JLabel userLabel;
	private JLabel passLabel;
	private EmployeeManagerImpl employeeManager;
	private GridBagConstraints gbc;

	Locale locale = new Locale("mk", "MK"); //$NON-NLS-1$ //$NON-NLS-2$
	final ResourceBundle labels = ResourceBundle.getBundle("com.seavus.foodorder.i18n.WelcomePanelMessages", locale); //$NON-NLS-1$
	//2 flags in buttons to be implemented, on press locale is changed
	
	public WelcomePanel() {		
		setLookAndFeel();
		setTitle(labels.getString("WelcomePanel.Title")); //$NON-NLS-1$
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		contentPane.setVisible(true);
		fillContentPane();
		
		this.getRootPane().setDefaultButton(getLoginButton());

		getLoginButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (getUsername().equals("admin") && getPassword().equals("admin")) { //$NON-NLS-1$ //$NON-NLS-2$
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								AdminMainPanel frame = new AdminMainPanel(); // add constructor that takes locale as parameter
								frame.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} else {
					if (getEmployeeManager().checkEmployee(getUsername(), getPassword())) {
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									OrderPanel orderPanel = new OrderPanel(getUsername(), getPassword()); // add constructor that takes locale as parameter
									orderPanel.setVisible(true);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
					} else {
						JOptionPane.showMessageDialog(contentPane, labels.getString("WelcomePanel.WrongUsernamePassword"), "ERROR", JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
					}
				}
			}
		});
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
	
	
	private void setLookAndFeel() {
		String className = getLookAndFeelClassName("Nimbus"); //$NON-NLS-1$
		try {
			UIManager.setLookAndFeel(className);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
	}
	
	private void fillContentPane() {
		getNorthPanel().setLayout(new FlowLayout(FlowLayout.TRAILING));
		getNorthPanel().add(getEnButton());
		getNorthPanel().add(getMkButton());		
		fillCentralPanel();
		contentPane.add(getCentralPanel(), BorderLayout.CENTER);
		contentPane.add(getNorthPanel(), BorderLayout.NORTH);
	}
	

	public void fillCentralPanel() {
		getCentralPanel().setLayout(new GridBagLayout());
		getGBC().insets = new Insets(0, 0, 20, 0);
		getGBC().gridx = 0;
		getGBC().gridy = 0;
		getGBC().fill = GridBagConstraints.HORIZONTAL;
		getGBC().gridwidth = 2;
		getCentralPanel().add(getWelcomeLabel(), getGBC());
		
		getGBC().insets = new Insets(0, 0, 0, 0);
		getGBC().gridx = 0;
		getGBC().gridy = 1;
		getGBC().fill = GridBagConstraints.HORIZONTAL;
		getGBC().gridwidth = 1;
		getCentralPanel().add(getUsernameLabel(), getGBC());
		
		getGBC().gridx = 1;
		getGBC().gridy = 1;
		getGBC().fill = GridBagConstraints.HORIZONTAL;
		getGBC().gridwidth = 1;
		getCentralPanel().add(getUserField(), getGBC());
		
		getGBC().gridx = 0;
		getGBC().gridy = 2;
		getGBC().fill = GridBagConstraints.HORIZONTAL;
		getGBC().gridwidth = 1;
		getCentralPanel().add(getPasswordLabel(), getGBC());
		
		getGBC().gridx = 1;
		getGBC().gridy = 2;
		getGBC().fill = GridBagConstraints.HORIZONTAL;
		getGBC().gridwidth = 1;
		getCentralPanel().add(getPassField(), getGBC());
		
		getGBC().gridx = 0;
		getGBC().gridy = 3;
		getGBC().fill = GridBagConstraints.HORIZONTAL;
		getGBC().gridwidth = 2;
		getCentralPanel().add(getLoginButton(), getGBC());
	}
	
	private JButton getLoginButton() {
		if(this.loginButton == null) {
			loginButton = new JButton((labels.getString("WelcomePanel.EnterButton")).toUpperCase());
		}
		return loginButton;
	}
	
	
	private JButton getMkButton() {
		if(this.mkLangButton == null) {
			mkLangButton = new JButton("MK");
		}
		return mkLangButton;
	}
	
	
	private JButton getEnButton() {
		if(this.enLangButton == null) {
			enLangButton = new JButton("EN");
		}
		return enLangButton;
	}
	
	
	private JLabel getUsernameLabel() {
		if(this.userLabel == null) {
			this.userLabel = new JLabel(labels.getString("WelcomePanel.UsernameLabel"), JLabel.RIGHT);
		}
		return userLabel;
	}
	
	
	private JLabel getPasswordLabel() {
		if(this.passLabel == null) {
			this.passLabel = new JLabel(labels.getString("WelcomePanel.PasswordLabel"), JLabel.RIGHT);
		}
		return passLabel;
	}
	
	
	private JLabel getWelcomeLabel() {
		if(this.welcomeLabel == null) {			
			this.welcomeLabel = new JLabel((labels.getString("WelcomePanel.WelcomeLabel")).toUpperCase(), JLabel.CENTER);
			this.welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
		}
		return welcomeLabel;
	}
	
		
	private JTextField getUserField() {
		if(this.userField == null) {
			userField = new JTextField(15);
		}
		return userField;
	}
	
	
	private JTextField getPassField() {
		if(this.passField == null) {
			passField = new JTextField(15);
		}
		return passField;
	}
	
	
	private String getUsername() {
		return this.userField.getText();
	}
	
	private String getPassword() {
		return this.passField.getText();
	}
	
	private JPanel getCentralPanel() {
		if(centralPanel == null) {
			centralPanel = new JPanel();
		}
		return centralPanel;
	}
	
	private JPanel getNorthPanel() {
		if(northPanel == null) {
			northPanel = new JPanel();
		}
		return northPanel;
	}
	
	private EmployeeManagerImpl getEmployeeManager() {
		if(employeeManager == null) {
			employeeManager = new EmployeeManagerImpl();
		}
		return employeeManager;
	}

	private GridBagConstraints getGBC() {
		if(this.gbc == null) {
			gbc = new GridBagConstraints();
		}
		return gbc;
	}
}
