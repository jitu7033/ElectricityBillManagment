
package src.electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class pay_bill extends JFrame implements ActionListener {

    private Choice searchMonthChoice;
    private String meter;

    private JLabel meterNumberValue, nameValue, unitValue, totalBillValue, statusValue;
    private JButton payButton, backButton;

    public pay_bill(String meter) {
        this.meter = meter;

        setTitle("Pay Bill");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // fullscreen
        setLayout(new BorderLayout());

        // Left Panel: Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Heading
        JLabel heading = new JLabel("Pay Bill");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 36));
        heading.setForeground(new Color(33, 37, 41));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(heading, gbc);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 18);
        Font valueFont = new Font("Segoe UI", Font.PLAIN, 18);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;

        // Meter Number Label & Value
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel meterNumberLabel = new JLabel("Meter Number:");
        meterNumberLabel.setFont(labelFont);
        formPanel.add(meterNumberLabel, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        meterNumberValue = new JLabel(meter);
        meterNumberValue.setFont(valueFont);
        formPanel.add(meterNumberValue, gbc);

        // Name Label & Value
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(labelFont);
        formPanel.add(nameLabel, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        nameValue = new JLabel("");
        nameValue.setFont(valueFont);
        formPanel.add(nameValue, gbc);

        // Month Label & Choice
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel monthLabel = new JLabel("Month:");
        monthLabel.setFont(labelFont);
        formPanel.add(monthLabel, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        searchMonthChoice = new Choice();
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        for (String m : months) searchMonthChoice.add(m);
        searchMonthChoice.setFont(valueFont);
        formPanel.add(searchMonthChoice, gbc);

        // Unit Label & Value
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel unitLabel = new JLabel("Unit:");
        unitLabel.setFont(labelFont);
        formPanel.add(unitLabel, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        unitValue = new JLabel("");
        unitValue.setFont(valueFont);
        formPanel.add(unitValue, gbc);

        // Total Bill Label & Value
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel totalBillLabel = new JLabel("Total Bill:");
        totalBillLabel.setFont(labelFont);
        formPanel.add(totalBillLabel, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        totalBillValue = new JLabel("");
        totalBillValue.setFont(valueFont);
        formPanel.add(totalBillValue, gbc);

        // Status Label & Value
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(labelFont);
        formPanel.add(statusLabel, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        statusValue = new JLabel("");
        statusValue.setForeground(Color.RED);
        statusValue.setFont(valueFont);
        formPanel.add(statusValue, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonPanel.setBackground(Color.WHITE);

        payButton = new JButton("Pay");
        styleButton(payButton);
        payButton.addActionListener(this);
        buttonPanel.add(payButton);

        backButton = new JButton("Back");
        styleButton(backButton);
        backButton.addActionListener(this);
        buttonPanel.add(backButton);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(30, 15, 15, 15);
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.WEST);

        // Right Panel with gradient background & info
        JPanel rightPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                Color c1 = new Color(52, 152, 219);
                Color c2 = new Color(41, 128, 185);
                GradientPaint gp = new GradientPaint(0, 0, c1, 0, h, c2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        rightPanel.setLayout(new GridBagLayout());

        JLabel infoLabel = new JLabel("<html><div style='text-align:center;'>" +
                "<h1 style='color:white; font-family: Segoe UI;'>Welcome to the Payment Portal</h1>" +
                "<p style='color:#ecf0f1; font-size:20px; font-family: Segoe UI;'>" +
                "Select your billing month on the left.<br>" +
                "Review your unit usage and total bill.<br><br>" +
                "Click <b>Pay</b> to complete the transaction.<br>" +
                "For assistance, contact customer support." +
                "</p></div></html>");
        rightPanel.add(infoLabel);

        add(rightPanel, BorderLayout.CENTER);

        // Load customer name & initial bill info
        loadCustomerName();
        loadBillDetails(searchMonthChoice.getSelectedItem());

        // Update bill details on month change
        searchMonthChoice.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                loadBillDetails(searchMonthChoice.getSelectedItem());
            }
        });

        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(41, 128, 185));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 40));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(52, 152, 219));
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(41, 128, 185));
            }
        });
    }

    private void loadCustomerName() {
        try {
            database c = new database();
            ResultSet rs = c.statement.executeQuery("SELECT name FROM newcustomer WHERE meter_no = '" + meter + "'");
            if (rs.next()) {
                nameValue.setText(rs.getString("name"));
            } else {
                nameValue.setText("N/A");
            }
        } catch (Exception e) {
            e.printStackTrace();
            nameValue.setText("Error");
        }
    }

    private void loadBillDetails(String month) {
        try {
            database c = new database();
            ResultSet rs = c.statement.executeQuery(
                    "SELECT unit, total_bill, status FROM bill WHERE meter_no = '" + meter + "' AND month = '" + month + "'");
            if (rs.next()) {
                unitValue.setText(rs.getString("unit"));
                totalBillValue.setText(rs.getString("total_bill"));
                statusValue.setText(rs.getString("status"));
                if ("Paid".equalsIgnoreCase(rs.getString("status"))) {
                    payButton.setEnabled(false);
                    payButton.setText("Already Paid");
                    payButton.setBackground(Color.GRAY);
                } else {
                    payButton.setEnabled(true);
                    payButton.setText("Pay");
                    payButton.setBackground(new Color(41, 128, 185));
                }
            } else {
                unitValue.setText("N/A");
                totalBillValue.setText("N/A");
                statusValue.setText("No Bill Found");
                payButton.setEnabled(false);
                payButton.setText("Pay");
                payButton.setBackground(Color.GRAY);
            }
        } catch (Exception e) {
            e.printStackTrace();
            unitValue.setText("Error");
            totalBillValue.setText("Error");
            statusValue.setText("Error");
            payButton.setEnabled(false);
            payButton.setText("Pay");
            payButton.setBackground(Color.GRAY);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == payButton) {
            try {
                database c = new database();
                c.statement.executeUpdate("UPDATE bill SET status='Paid' WHERE meter_no='" + meter +
                        "' AND month='" + searchMonthChoice.getSelectedItem() + "'");
                JOptionPane.showMessageDialog(this, "Payment Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadBillDetails(searchMonthChoice.getSelectedItem()); // Refresh after payment
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Payment Failed! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == backButton) {
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new pay_bill("123456"));
    }
}

