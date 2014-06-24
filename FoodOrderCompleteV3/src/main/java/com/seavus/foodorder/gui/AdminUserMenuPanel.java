package com.seavus.foodorder.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.seavus.foodorder.model.Employee;
import com.seavus.foodorder.model.Role;

public class AdminUserMenuPanel extends AdminMenuPanel implements MenuPanel{

	private JMenuBar menuBar;
	private JMenu userMenu;
	private JMenuItem userAddMenuItem;
	private JMenuItem userDeleteMenuItem;
	private JMenuItem userUpdateMenuItem;	
	private JTextField username;
	private JTextField password;
	private JTextField email;
	private JTextField usernameToUpdate;	
	private JRadioButton employeeRadioButton;
	private JRadioButton adminRadioButton;
	private ButtonGroup roleRadioButtonGroup;
	private JPanel updateUserPanel;
	private GridBagConstraints gbc;

	public AdminUserMenuPanel(JPanel contentPane, JMenuBar menuBar) {
		super(contentPane);
		this.menuBar = menuBar;
		
		initializeComponents();
	}

	@Override
	public void initializeComponents() {
		gbc = getConstraints();
		userMenu = getMenu("User");
		menuBar.add(userMenu);
		initializeMenuItems();
		fillMenu();
		initializeTextFileds();
		initializeRadioButtons();
		addMenuListeners();
	}
	
	public void initializeRadioButtons() {
		roleRadioButtonGroup = new ButtonGroup();
		employeeRadioButton = new JRadioButton("Employee");
		employeeRadioButton.setActionCommand("Employee");
		adminRadioButton = new JRadioButton("Administrator");
		adminRadioButton.setActionCommand("Admin");
		roleRadioButtonGroup.add(employeeRadioButton);
		roleRadioButtonGroup.add(adminRadioButton);
		employeeRadioButton.setSelected(true);
	}
	
	public void initializeTextFileds() {	
		username = new JTextField(15);
		password = new JTextField(15);
		usernameToUpdate = new JTextField(15);
		email = new JTextField(15);
	}
	
	public void initializeMenuItems() {
		userAddMenuItem = new JMenuItem("Add user");
		userDeleteMenuItem = new JMenuItem("Delete user");
		userUpdateMenuItem = new JMenuItem("Update user");
	}

	@Override
	public void fillMenu() {
		userMenu.add(userAddMenuItem);
		userMenu.add(userUpdateMenuItem);
		userMenu.add(userDeleteMenuItem);
	}
	
	public void fillAddUserPanel() {
		contentPane.removeAll();
		repaint();
		contentPane.setLayout(new GridBagLayout());

		setTitle("CREATE NEW USER");
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		contentPane.add(getTitle(), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		contentPane.add(new JLabel("Enter username: ", JLabel.RIGHT), gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		contentPane.add(username, gbc);
		username.requestFocusInWindow();

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 2;
		contentPane.add(new JLabel("Enter password: ", JLabel.RIGHT), gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		contentPane.add(password, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 3;
		contentPane.add(new JLabel("Enter e-mail: ", JLabel.RIGHT), gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		contentPane.add(email, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 4;
		contentPane.add(new JLabel("Enter role: ", JLabel.RIGHT), gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		contentPane.add(employeeRadioButton, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 5;
		contentPane.add(adminRadioButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		contentPane.add(getCreateButton(), gbc);
		
		setDefaultButton(getCreateButton());
		repaint();
	}
	
	public void fillDeleteUserPanel() {
		contentPane.removeAll();
		repaint();
		contentPane.setLayout(new GridBagLayout());

		setTitle("DELETE USER");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		contentPane.add(getTitle(), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		contentPane.add(new JLabel("Enter username: "), gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		contentPane.add(username, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		contentPane.add(getDeleteButton(), gbc);

		setDefaultButton(getDeleteButton());
		username.requestFocusInWindow();
		repaint();
	}
	
	public void fillUsernameToUpdateUserPanel() {
		contentPane.removeAll();
		emptyFileds();
		repaint();
		contentPane.setLayout(new GridBagLayout());

		setTitle("UPDATE USER");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		contentPane.add(getTitle(), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		contentPane.add(new JLabel("Enter username: ", JLabel.RIGHT), gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		contentPane.add(usernameToUpdate, gbc);
		usernameToUpdate.requestFocusInWindow();

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		contentPane.add(getFindButton(), gbc);

		setDefaultButton(getFindButton());
		repaint();
	}
	
	public void fillUpdateUserPanel() {
		if(updateUserPanel != null) {
			contentPane.remove(updateUserPanel);
		}
		getFindButton().setEnabled(false);
		usernameToUpdate.setEditable(false);
		usernameToUpdate.setBackground(Color.LIGHT_GRAY);
		repaint();
		updateUserPanel = getUpdateUserPanel();
		Employee employee = getEmployeeManager().findByEmployeeUsername(getUsernameToUpdate());
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		updateUserPanel.setLayout(new GridBagLayout());
		contentPane.add(updateUserPanel, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		updateUserPanel.add(new JLabel("Enter new username: ", JLabel.RIGHT), gbc);

		setUsername(employee.getUsername());
		gbc.gridx = 1;
		gbc.gridy = 0;
		updateUserPanel.add(username, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 1;
		updateUserPanel.add(new JLabel("Enter new password: ", JLabel.RIGHT), gbc);

		setPassword(employee.getPassword());
		gbc.gridx = 1;
		gbc.gridy = 1;
		updateUserPanel.add(password, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 2;
		updateUserPanel.add(new JLabel("Enter new e-mail: ", JLabel.RIGHT), gbc);

		setEmail(employee.getEmail());
		gbc.gridx = 1;
		gbc.gridy = 2;
		updateUserPanel.add(email, gbc);		

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		updateUserPanel.add(getUpdateButton(), gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		updateUserPanel.add(getCancelButton(), gbc);

		username.requestFocusInWindow();
		setDefaultButton(getUpdateButton());
		repaint();
	}
	
	public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
 }
	
	public void deleteUserAction() {
		Employee employee = getEmployeeManager().findByEmployeeUsername(getUsername());
		if (employee != null) {
			final JOptionPane optionPane = new JOptionPane("Are you sure that you want to delete this user?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
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
				getEmployeeManager().deleteEmployee(employee);
				JOptionPane.showMessageDialog(contentPane, "The user has been successfuly deleted", "INFO", JOptionPane.INFORMATION_MESSAGE);
				emptyFileds();
			} else if (value == JOptionPane.NO_OPTION) {
				dialog.dispose();
			}
		} else {
			JOptionPane.showMessageDialog(contentPane, "There is no such user in the database", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void addUserAction() {
		String selectedRole = roleRadioButtonGroup.getSelection().getActionCommand();
		Role role = getRoleManager().getRole(selectedRole);
		Employee employee = new Employee(getUsername(), getPassword(), getEmail(), role) ;
		getEmployeeManager().saveNewEmployee(employee);
		JOptionPane.showMessageDialog(contentPane,"The user has been successfuly created","INFO", JOptionPane.INFORMATION_MESSAGE);
		emptyFileds();
	}
	
	public void updateUserAction() {
		if(!checkIfFieldIsNotEmpty(username) && !checkIfFieldIsNotEmpty(password) && checkIfFieldIsNotEmpty(email)) {
			JOptionPane.showMessageDialog(contentPane,"Enter username/password/email","ERROR",JOptionPane.ERROR_MESSAGE);
		} else {	
			if(isValidEmailAddress(getEmail())) {
				Employee employee = getEmployeeManager().findByEmployeeUsername(getUsernameToUpdate());
				getEmployeeManager().updateEmployee(getUsername(),getPassword(),getEmail(),employee.getRole(),employee);
				JOptionPane.showMessageDialog(contentPane,"User succssefuly updated","INFO",JOptionPane.INFORMATION_MESSAGE);	
				cancelAction();
			} else {
				JOptionPane.showMessageDialog(contentPane,"Enter valid email address!","ERROR",JOptionPane.ERROR_MESSAGE);
			}
		}		
	}
	
	public void cancelAction() {
		emptyFileds();
		getFindButton().setEnabled(true);
		usernameToUpdate.setEditable(true);
		usernameToUpdate.setBackground(Color.WHITE);
		fillUsernameToUpdateUserPanel();
	}
	
	@Override
	public void addMenuListeners() {
		userAddMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fillAddUserPanel();

				getCreateButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (checkIfFieldIsNotEmpty(username) && checkIfFieldIsNotEmpty(password) && checkIfFieldIsNotEmpty(email)) {
							if(isValidEmailAddress(getEmail())) {
								addUserAction();
							} else {
								JOptionPane.showMessageDialog(contentPane, "Enter valid email address", "ERROR", JOptionPane.ERROR_MESSAGE);
							}
							
						} else {
							JOptionPane.showMessageDialog(contentPane, "Enter username and password", "ERROR", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
			}
		});
		
		userDeleteMenuItem.addActionListener(new ActionListener() {	
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fillDeleteUserPanel();

				getDeleteButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (checkIfFieldIsNotEmpty(username)) {
							deleteUserAction();
						} else {							
							JOptionPane.showMessageDialog(contentPane, "Enter username", "ERROR",JOptionPane.ERROR_MESSAGE);
						}
						repaint();
					}
				});				
			}
		});
		
		userUpdateMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fillUsernameToUpdateUserPanel();

				getFindButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (checkIfFieldIsNotEmpty(usernameToUpdate)) {
							if (getEmployeeManager().findByEmployeeUsername(getUsernameToUpdate()) != null) {
								fillUpdateUserPanel();								
							} else {
								JOptionPane.showMessageDialog(contentPane,"There is no such user in the database","ERROR",JOptionPane.ERROR_MESSAGE);
								setUsernameToUpdate("");
								usernameToUpdate.requestFocusInWindow();
							}
						} else {
							JOptionPane.showMessageDialog(contentPane,"Enter username", "ERROR",JOptionPane.ERROR_MESSAGE);
							usernameToUpdate.requestFocusInWindow();
						}
					}
				});					
				
				
				getUpdateButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						updateUserAction();											
					}
				});
				
				getCancelButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						cancelAction();
					}
				});
				
			}
		});
	}
	
	public void emptyFileds() {
		setUsername("");
		setPassword("");
		setUsernameToUpdate("");
		setEmail("");
	}
	
	public String getUsername() {
		return username.getText();
	}
	
	public String getPassword() {
		return password.getText();
	}
	
	public String getUsernameToUpdate() {
		return usernameToUpdate.getText();
	}

	public String getEmail() {
		return this.email.getText();
	}
	
	public void setUsername(String username) {
		this.username.setText(username);
	}

	public void setPassword(String password) {
		this.password.setText(password);
	}
	
	public void setUsernameToUpdate(String usernameToUpdate) {
		this.usernameToUpdate.setText(usernameToUpdate);
	}

	public void setEmail(String email) {
		this.email.setText(email);
	}
	
	public JPanel getUpdateUserPanel() {
		return new JPanel();
	}

	public GridBagConstraints getConstraints() {
		return new GridBagConstraints();
	}

	public JMenu getMenu(String title) {
		return new JMenu(title);
	}
}