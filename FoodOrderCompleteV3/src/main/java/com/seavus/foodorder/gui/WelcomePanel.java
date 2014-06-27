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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;

import com.seavus.foodorder.model.Employee;
import com.seavus.foodorder.service.EmployeeManagerImpl;

@SuppressWarnings("serial")
public class WelcomePanel extends JFrame {

	private JPanel contentPane;
	private JPanel centralPanel;
	private JPanel northPanel;
	private JTextField userField;
	private JPasswordField passField;
	private JButton loginButton;
	private JButton mkLangButton;
	private JButton enLangButton;
	private JLabel welcomeLabel;
	private JLabel userLabel;
	private JLabel passLabel;
	private EmployeeManagerImpl employeeManager;
	private GridBagConstraints gbc;

	private Locale locale; //$NON-NLS-1$ //$NON-NLS-2$
	private ResourceBundle labels; //$NON-NLS-1$
	private Logger log;
	//2 flags in buttons to be implemented, on press locale is changed
	
	public WelcomePanel() {				
		initLogger();
		initializeWelcomePanel();
		addActionListeners();
	}
	
	private void initializeWelcomePanel() {
		setLookAndFeel();
		setLocale("en", "EN");
		setResourceBundle();
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
	}
	
	private void addActionListeners() {
		getLoginButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				if(getEmployeeManager().checkEmployee(getUsername(), getPassword())) {
					Employee employee = getEmployeeManager().findByEmployeeUsername(getUsername());
					if(employee.getRole().getRole().equals("Employee")) {
						startOrderPanel();						
					} else if(employee.getRole().getRole().equals("Admin")){
						startAdminPanel();
					} 
				} else {
					JOptionPane.showMessageDialog(contentPane, labels.getString("WelcomePanel.WrongUsernamePassword"), labels.getString("WelcomePanel.Error"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
		});
		
		getMkButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setLocale("mk", "MK");	
				setResourceBundle();
				reinitializeTitles();
				repaint();
			}
		});
		
		getEnButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setLocale("en", "EN");	
				setResourceBundle();
				reinitializeTitles();
				repaint();				
			}
		});
	}
	
	private void startOrderPanel() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrderPanel orderPanel = new OrderPanel(getUsername(), getLocale());
					orderPanel.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void startAdminPanel() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMainPanel adminPanel = new AdminMainPanel();
					adminPanel.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void reinitializeTitles() {
		this.setTitle(labels.getString("WelcomePanel.Title"));
		getLoginButton().setText((labels.getString("WelcomePanel.EnterButton")).toUpperCase());
		getUsernameLabel().setText(labels.getString("WelcomePanel.UsernameLabel"));
		getPasswordLabel().setText(labels.getString("WelcomePanel.PasswordLabel"));
		getWelcomeLabel().setText((labels.getString("WelcomePanel.WelcomeLabel")).toUpperCase());
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

	private void setLocale(String lang, String country) {
		locale = new Locale(lang, country);				
	}
	
	private void initLogger() {
		log = Logger.getLogger(WelcomePanel.class.getName());
		Appender appender = Logger.getRootLogger().getAppender("AdminFileAppender");
		log.removeAppender(appender);
	}
	
	private Logger getLogger() {
		if (log == null) {
			initLogger();
		}
		return log;
	}
	
	public Locale getLocale() {
		return this.locale;
	}
	
	public ResourceBundle getLabels() {
		return labels;
	}
	
	private void setResourceBundle() {
		labels = ResourceBundle.getBundle("com.seavus.foodorder.i18n.WelcomePanelMessages", getLocale());
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
		getUserField().requestFocusInWindow();
		
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
	
	
	private JPasswordField getPassField() {
		if(this.passField == null) {
			passField = new JPasswordField(15);
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
