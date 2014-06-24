package com.seavus.foodorder.gui;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

import com.seavus.foodorder.service.EmployeeManagerImpl;

@SuppressWarnings("serial")
public class WelcomePanel extends JFrame {

	private String username;
	private String password;
	private JPanel contentPane;
	private JTextField userText;
	private JTextField passText;
	private JButton login;
	private EmployeeManagerImpl employeeManager;

	Locale locale = new Locale("mk", "MK"); //$NON-NLS-1$ //$NON-NLS-2$
	final ResourceBundle labels = ResourceBundle.getBundle("com.seavus.foodorder.i18n.WelcomePanelMessages", locale); //$NON-NLS-1$
	//2 flags in buttons to be implemented, on press locale is changed
	
	public WelcomePanel() {
		
		String className = getLookAndFeelClassName("Nimbus"); //$NON-NLS-1$
		try {
			UIManager.setLookAndFeel(className);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		setTitle(labels.getString("WelcomePanel.Title")); //$NON-NLS-1$
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 320, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new FlowLayout());
		setContentPane(contentPane);

		createNamePanel();
		createUsernamePanel();
		createPasswordPanel();
		createLoginButton();

		contentPane.setVisible(true);
		
		this.getRootPane().setDefaultButton(login);

		this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "press"); 
		this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released ENTER"), "press"); 

		this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "none");
		this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released ENTER"), "press");


		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				username = userText.getText();
				password = passText.getText();
				employeeManager = new EmployeeManagerImpl();
				if (username.equals("admin") && password.equals("admin")) { //$NON-NLS-1$ //$NON-NLS-2$
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								AdminMainPanel frame = new AdminMainPanel(); // add constructor that takes locale as parameter
								frame.setVisible(true);
								System.out.println("Admin panel created");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} else {
					if (employeeManager.checkEmployee(username, password)) {
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									OrderPanel orderPanel = new OrderPanel(username, password); // add constructor that takes locale as parameter
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

	public static String getLookAndFeelClassName(String nameSnippet) {
		LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
		for (LookAndFeelInfo info : plafs) {
			if (info.getName().contains(nameSnippet)) {
				return info.getClassName();
			}
		}
		return null;
	}

	public void createNamePanel() {
		JLabel foodOrder = new JLabel(labels.getString("WelcomePanel.WelcomeLabel")); //$NON-NLS-1$
		foodOrder.setFont(new Font("Arial", Font.BOLD, 16)); //$NON-NLS-1$
		contentPane.add(foodOrder);
	}

	public void createUsernamePanel() {
		JLabel user = new JLabel(labels.getString("WelcomePanel.UsernameLabel")); //$NON-NLS-1$
		userText = new JTextField(15);
		JPanel userPanel = new JPanel();
		userPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		userPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		userPanel.add(user);
		userPanel.add(userText);
		contentPane.add(userPanel);
		userPanel.setVisible(true);
	}

	public void createPasswordPanel() {
		JLabel pass = new JLabel(labels.getString("WelcomePanel.PasswordLabel")); //$NON-NLS-1$
		passText = new JPasswordField(15);
		JPanel passPanel = new JPanel();
		passPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		passPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		passPanel.add(pass);
		passPanel.add(passText);
		contentPane.add(passPanel);
		passPanel.setVisible(true);
	}

	public void createLoginButton() {
		login = new JButton(labels.getString("WelcomePanel.EnterButton")); //$NON-NLS-1$
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(login);
		contentPane.add(buttonPanel);
		buttonPanel.setVisible(true);
	}

}
