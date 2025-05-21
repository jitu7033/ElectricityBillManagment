

package src.electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class update_information extends JFrame implements ActionListener {
    JLabel nametext, meterText;
    JTextField addressText, cityText, stateText, emailText, phoneText;
    String meter;
    JButton update, cancel;

    update_information(String meter) {
        this.meter = meter;

        // Gradient background panel
        JPanel background = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(58, 123, 213),
                        0, getHeight(), new Color(0, 210, 255));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        background.setLayout(new GridBagLayout());

        // Centered panel
        JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(500, 400));
        panel.setBackground(new Color(255, 255, 255, 230));
        background.add(panel);
        setContentPane(background);

        JLabel heading = new JLabel("Update Customer Information");
        heading.setBounds(0, 20, 500, 30);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(heading);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 15);
        int labelX = 40, labelWidth = 120, fieldX = 180, fieldWidth = 250;
        int y = 70, gap = 35;

        JLabel name = new JLabel("Name:");
        name.setBounds(labelX, y, labelWidth, 25);
        name.setFont(labelFont);
        panel.add(name);

        nametext = new JLabel();
        nametext.setBounds(fieldX, y, fieldWidth, 25);
        panel.add(nametext);

        y += gap;
        JLabel meterNo = new JLabel("Meter Number:");
        meterNo.setBounds(labelX, y, labelWidth, 25);
        meterNo.setFont(labelFont);
        panel.add(meterNo);

        meterText = new JLabel();
        meterText.setBounds(fieldX, y, fieldWidth, 25);
        panel.add(meterText);

        y += gap;
        JLabel address = new JLabel("Address:");
        address.setBounds(labelX, y, labelWidth, 25);
        address.setFont(labelFont);
        panel.add(address);

        addressText = new JTextField();
        addressText.setBounds(fieldX, y, fieldWidth, 25);
        panel.add(addressText);

        y += gap;
        JLabel city = new JLabel("City:");
        city.setBounds(labelX, y, labelWidth, 25);
        city.setFont(labelFont);
        panel.add(city);

        cityText = new JTextField();
        cityText.setBounds(fieldX, y, fieldWidth, 25);
        panel.add(cityText);

        y += gap;
        JLabel state = new JLabel("State:");
        state.setBounds(labelX, y, labelWidth, 25);
        state.setFont(labelFont);
        panel.add(state);

        stateText = new JTextField();
        stateText.setBounds(fieldX, y, fieldWidth, 25);
        panel.add(stateText);

        y += gap;
        JLabel email = new JLabel("Email:");
        email.setBounds(labelX, y, labelWidth, 25);
        email.setFont(labelFont);
        panel.add(email);

        emailText = new JTextField();
        emailText.setBounds(fieldX, y, fieldWidth, 25);
        panel.add(emailText);

        y += gap;
        JLabel phone = new JLabel("Phone:");
        phone.setBounds(labelX, y, labelWidth, 25);
        phone.setFont(labelFont);
        panel.add(phone);

        phoneText = new JTextField();
        phoneText.setBounds(fieldX, y, fieldWidth, 25);
        panel.add(phoneText);

        y += gap + 10;
        update = new JButton("Update");
        update.setBounds(120, y, 100, 35);
        update.setFont(new Font("Segoe UI", Font.BOLD, 15));
        update.setBackground(new Color(33, 106, 145));
        update.setForeground(Color.WHITE);
        update.addActionListener(this);
        panel.add(update);

        cancel = new JButton("Cancel");
        cancel.setBounds(260, y, 100, 35);
        cancel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        cancel.setBackground(new Color(33, 106, 145));
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        panel.add(cancel);

        // Load existing data
        try {
            database c = new database();
            ResultSet resultSet = c.statement.executeQuery("select * from newcustomer where meter_no = '" + meter + "'");
            if (resultSet.next()) {
                nametext.setText(resultSet.getString("name"));
                meterText.setText(resultSet.getString("meter_no"));
                addressText.setText(resultSet.getString("address"));
                cityText.setText(resultSet.getString("city"));
                stateText.setText(resultSet.getString("state"));
                emailText.setText(resultSet.getString("email"));
                phoneText.setText(resultSet.getString("phonenum"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Final frame setup
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == update) {
            String saddress = addressText.getText();
            String scity = cityText.getText();
            String sstate = stateText.getText();
            String semail = emailText.getText();
            String sphone = phoneText.getText();

            try {
                database c = new database();
                c.statement.executeUpdate("UPDATE newcustomer SET address = '" + saddress + "', city = '" + scity +
                        "', state = '" + sstate + "', email = '" + semail + "', phonenum = '" + sphone +
                        "' WHERE meter_no = '" + meter + "'");
                JOptionPane.showMessageDialog(null, "User Information Updated Successfully");
                setVisible(false);
            } catch (Exception E) {
                E.printStackTrace();
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new update_information("YourMeterNumberHere");
    }
}
