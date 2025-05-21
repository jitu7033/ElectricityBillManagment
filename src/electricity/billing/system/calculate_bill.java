



package src.electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class calculate_bill extends JFrame implements ActionListener {
    Choice MeterChoice, monthChoice;
    JTextField unitConsumedText;
    JButton submit, cancelBtn;
    JLabel nameText, addressText;

    calculate_bill() {
        setTitle("SmartBill - Calculate Electricity Bill");
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Gradient background
        JPanel background = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(72, 61, 139), 0, getHeight(), new Color(123, 104, 238));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        background.setLayout(new GridBagLayout());

        JPanel card = new JPanel(new GridBagLayout());
        card.setPreferredSize(new Dimension(600, 500));
        card.setBackground(new Color(255, 255, 255, 240));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Calculate Electricity Bill");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        card.add(title, gbc);

        // Row 1: Meter Number
        gbc.gridwidth = 1;
        gbc.gridy++;
        card.add(new JLabel("Meter No:"), gbc);
        MeterChoice = new Choice();
        try {
            database c = new database();
            ResultSet rs = c.statement.executeQuery("SELECT meter_no FROM newCustomer");
            while (rs.next()) MeterChoice.add(rs.getString("meter_no"));
        } catch (Exception e) { e.printStackTrace(); }
        gbc.gridx = 1;
        card.add(MeterChoice, gbc);

        // Row 2: Name
        gbc.gridx = 0; gbc.gridy++;
        card.add(new JLabel("Customer Name:"), gbc);
        nameText = new JLabel("-");
        gbc.gridx = 1;
        card.add(nameText, gbc);

        // Row 3: Address
        gbc.gridx = 0; gbc.gridy++;
        card.add(new JLabel("Address:"), gbc);
        addressText = new JLabel("-");
        gbc.gridx = 1;
        card.add(addressText, gbc);

        // Row 4: Unit Consumed
        gbc.gridx = 0; gbc.gridy++;
        card.add(new JLabel("Units Consumed:"), gbc);
        unitConsumedText = new JTextField();
        gbc.gridx = 1;
        card.add(unitConsumedText, gbc);

        // Row 5: Month
        gbc.gridx = 0; gbc.gridy++;
        card.add(new JLabel("Month:"), gbc);
        monthChoice = new Choice();
        for (String m : new String[]{"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"}) {
            monthChoice.add(m);
        }
        gbc.gridx = 1;
        card.add(monthChoice, gbc);

        // Row 6: Buttons
        gbc.gridx = 0; gbc.gridy++;
        submit = new JButton("Generate Bill");
        submit.setBackground(new Color(46, 204, 113));
        submit.setForeground(Color.WHITE);
        submit.setFocusPainted(false);
        submit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        submit.addActionListener(this);
        card.add(submit, gbc);

        gbc.gridx = 1;
        cancelBtn = new JButton("Cancel");
        cancelBtn.setBackground(new Color(231, 76, 60));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelBtn.addActionListener(this);
        card.add(cancelBtn, gbc);

        MeterChoice.addItemListener(e -> {
            try {
                database c = new database();
                ResultSet rs = c.statement.executeQuery("SELECT * FROM newCustomer WHERE meter_no = '" + MeterChoice.getSelectedItem() + "'");
                while (rs.next()) {
                    nameText.setText(rs.getString("name"));
                    addressText.setText(rs.getString("address"));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        background.add(card);
        setContentPane(background);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            String meter = MeterChoice.getSelectedItem();
            String month = monthChoice.getSelectedItem();
            String unitStr = unitConsumedText.getText();

            try {
                int units = Integer.parseInt(unitStr);
                int total = 0;

                database c = new database();
                ResultSet rs = c.statement.executeQuery("SELECT * FROM tax");
                while (rs.next()) {
                    total += units * Integer.parseInt(rs.getString("cost_per_unit"));
                    total += units * Integer.parseInt(rs.getString("meter_rent"));
                    total += units * Integer.parseInt(rs.getString("service_charge"));
                    total += units * Integer.parseInt(rs.getString("swatch_bharat"));
                    total += units * Integer.parseInt(rs.getString("fixed_text"));
                }

                c.statement.executeUpdate("INSERT INTO Bill VALUES('" + meter + "','" + month + "','" + units + "','" + total + "','Pending')");
                JOptionPane.showMessageDialog(this, "Bill Generated Successfully");

                setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input or Database Error");
                ex.printStackTrace();
            }
        } else if (e.getSource() == cancelBtn) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new calculate_bill();
    }
}
