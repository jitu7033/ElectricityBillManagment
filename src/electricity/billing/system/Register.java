
package src.electricity.billing.system;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;

public class Register extends JFrame implements ActionListener {
    JTextField EmployerText, passwordText, userNameText, meterText, nameText;
    JButton signUpBtn, back;
    Choice registerChoice;

    Register() {
        super("Register");
        getContentPane().setBackground(new Color(255, 255, 255));
        setLayout(null);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(50, 30, 500, 400);
        formPanel.setBackground(new Color(232, 245, 255));
        formPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(0, 120, 215), 2, true),
                new EmptyBorder(15, 15, 15, 15)
        ));
        add(formPanel);

        JLabel heading = new JLabel("Create Account");
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        heading.setForeground(new Color(0, 102, 204));
        heading.setBounds(160, 10, 200, 30);
        formPanel.add(heading);

        JLabel register = new JLabel("Register As:");
        register.setBounds(50, 60, 120, 20);
        formPanel.add(register);

        registerChoice = new Choice();
        registerChoice.add("Admin");
        registerChoice.add("Customer");
        registerChoice.setBounds(180, 60, 200, 20);
        formPanel.add(registerChoice);

        JLabel meterNo = new JLabel("Meter No:");
        meterNo.setBounds(50, 100, 120, 20);
        meterNo.setVisible(false);
        formPanel.add(meterNo);

        meterText = new JTextField();
        meterText.setBounds(180, 100, 200, 25);
        meterText.setVisible(false);
        formPanel.add(meterText);

        JLabel employer = new JLabel("Employer ID:");
        employer.setBounds(50, 100, 120, 20);
        formPanel.add(employer);

        EmployerText = new JTextField();
        EmployerText.setBounds(180, 100, 200, 25);
        formPanel.add(EmployerText);

        JLabel userName = new JLabel("Username:");
        userName.setBounds(50, 140, 120, 20);
        formPanel.add(userName);

        userNameText = new JTextField();
        userNameText.setBounds(180, 140, 200, 25);
        formPanel.add(userNameText);

        JLabel name = new JLabel("Name:");
        name.setBounds(50, 180, 120, 20);
        formPanel.add(name);

        nameText = new JTextField();
        nameText.setBounds(180, 180, 200, 25);
        formPanel.add(nameText);

        JLabel password = new JLabel("Password:");
        password.setBounds(50, 220, 120, 20);
        formPanel.add(password);

        passwordText = new JTextField();
        passwordText.setBounds(180, 220, 200, 25);
        formPanel.add(passwordText);

        signUpBtn = new JButton("Sign Up");
        signUpBtn.setBounds(100, 280, 120, 35);
        signUpBtn.setBackground(new Color(34, 167, 240));
        signUpBtn.setForeground(Color.white);
        signUpBtn.setFont(new Font("Arial", Font.BOLD, 14));
        signUpBtn.setFocusPainted(false);
        signUpBtn.addActionListener(this);
        formPanel.add(signUpBtn);

        back = new JButton("Back");
        back.setBounds(250, 280, 120, 35);
        back.setBackground(new Color(220, 53, 69));
        back.setForeground(Color.white);
        back.setFont(new Font("Arial", Font.BOLD, 14));
        back.setFocusPainted(false);
        back.addActionListener(this);
        formPanel.add(back);

        meterText.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                try {
                    database c = new database();
                    ResultSet resultSet = c.statement.executeQuery("select * from signup where meter_no = '" + meterText.getText() + "'");
                    if (resultSet.next()) {
                        nameText.setText(resultSet.getString("name"));
                    }
                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });

        registerChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String user = registerChoice.getSelectedItem();
                if (user.equals("Customer")) {
                    employer.setVisible(false);
                    EmployerText.setVisible(false);
                    meterNo.setVisible(true);
                    meterText.setVisible(true);
                    nameText.setEditable(false);
                } else {
                    meterText.setVisible(false);
                    meterNo.setVisible(false);
                    employer.setVisible(true);
                    EmployerText.setVisible(true);
                    nameText.setEditable(true);
                }
            }
        });

        setSize(620, 500);
        setLocation(400, 200);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signUpBtn) {
            String sloginAs = registerChoice.getSelectedItem();
            String sUserName = userNameText.getText();
            String sName = nameText.getText();
            String sPassword = passwordText.getText();
            String sMeter = meterText.getText();
            String sEmployer = EmployerText.getText();

            try {
                database c = new database();
                String checkQuery = "SELECT COUNT(*) FROM SignUp WHERE username = '" + sUserName + "'";
                ResultSet resultSet = c.statement.executeQuery(checkQuery);
                resultSet.next();
                int count = resultSet.getInt(1);

                if (count > 0) {
                    JOptionPane.showMessageDialog(null, "Username already exists!");
                } else {
                    if (!isValidPassword(sPassword)) {
                        JOptionPane.showMessageDialog(null,
                                "Password must be at least 8 characters long,\ncontain an uppercase, lowercase, digit, and a special character!",
                                "Invalid Password", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!isValidUsername(sUserName)) {
                        JOptionPane.showMessageDialog(null,
                                "Username must start with a letter,\nbe 5-15 characters and only contain letters, digits, or underscores!",
                                "Invalid Username", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String query = null;
                    if (sloginAs.equals("Admin")) {
                        query = "INSERT INTO SignUp VALUES('" + sMeter + "','" + sUserName + "','" + sName + "','" + sPassword + "','" + sloginAs + "')";
                    } else {
                        query = "UPDATE signup SET username = '" + sUserName + "', password = '" + sPassword + "', userType = '" + sloginAs + "' WHERE meter_no = '" + sMeter + "'";
                    }

                    c.statement.executeUpdate(query);
                    JOptionPane.showMessageDialog(null, "Account Created Successfully!");
                    setVisible(false);
                    new Login();
                }

            } catch (Exception error) {
                error.printStackTrace();
            }
        } else if (e.getSource() == back) {
            setVisible(false);
            new Login();
        }
    }

    public static boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$");
    }

    public static boolean isValidUsername(String username) {
        return username.matches("^[A-Za-z][A-Za-z0-9_]{4,14}$");
    }

    public static void main(String[] args) {
        new Register();
    }
}

