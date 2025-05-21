
package src.electricity.billing.system;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {
    JTextField userText;
    JPasswordField passwordText;
    JCheckBox showPasswordCheckBox;  // <-- added checkbox here
    Choice loginChoice;
    JButton loginBtn, cancelBtn, signUpBtn;

    Login() {
        super("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel heading = new JLabel("User Login");
        heading.setFont(new Font("Arial", Font.BOLD, 32));
        heading.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(heading, gbc);

        JLabel userName = new JLabel("Username:");
        userName.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(userName, gbc);

        userText = new JTextField(20);
        userText.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(userText, gbc);

        JLabel userPassword = new JLabel("Password:");
        userPassword.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(userPassword, gbc);

        passwordText = new JPasswordField(20);
        passwordText.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(passwordText, gbc);

        // Show Password Checkbox - NEW
        showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setBackground(Color.WHITE);
        showPasswordCheckBox.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(showPasswordCheckBox, gbc);

        // Listener to toggle password visibility
        showPasswordCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (showPasswordCheckBox.isSelected()) {
                    passwordText.setEchoChar((char) 0); // Show password
                } else {
                    passwordText.setEchoChar('\u2022'); // Mask password (bullet)
                }
            }
        });

        JLabel loginAs = new JLabel("Login As:");
        loginAs.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(loginAs, gbc);

        loginChoice = new Choice();
        loginChoice.add("Admin");
        loginChoice.add("Customer");
        JPanel choicePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        choicePanel.setBackground(Color.WHITE);
        choicePanel.add(loginChoice);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(choicePanel, gbc);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonsPanel.setBackground(Color.WHITE);

        loginBtn = new JButton("Login");
        styleButton(loginBtn, new Color(34, 167, 240));
        loginBtn.addActionListener(this);
        buttonsPanel.add(loginBtn);

        cancelBtn = new JButton("Cancel");
        styleButton(cancelBtn, new Color(255, 99, 71));
        cancelBtn.addActionListener(this);
        buttonsPanel.add(cancelBtn);

        signUpBtn = new JButton("Sign Up");
        styleButton(signUpBtn, new Color(0, 177, 106));
        signUpBtn.addActionListener(this);
        buttonsPanel.add(signUpBtn);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonsPanel, gbc);

        setVisible(true);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 40));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn) {
            String user = loginChoice.getSelectedItem();
            String sUserName = userText.getText();
            String sPassword = passwordText.getText();

            try {
                database c = new database();
                String checkQuery = "SELECT * FROM SignUp WHERE userName = '" + sUserName +
                        "' AND password = '" + sPassword + "' AND userType = '" + user + "'";
                ResultSet resultSet = c.statement.executeQuery(checkQuery);


                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(null, "Login Successfully");
                    String meter = resultSet.getString("meter_no");
                    System.out.println(meter);
                    new main_class(user,meter);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username, Password, or User Type");
                }
            } catch (Exception error) {
                error.printStackTrace();
            }

        } else if (e.getSource() == cancelBtn) {
            setVisible(false);
        } else if (e.getSource() == signUpBtn) {
            setVisible(false);
            new Register();
        }
    }


    public static void main(String[] args) {
        new Login();
    }
}
