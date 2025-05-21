package src.electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class generate_bill extends JFrame implements ActionListener {
    Choice searchmonthcho;
    String meter;
    JTextArea area;
    JButton bill;

    generate_bill(String meter) {
        this.meter = meter;
        setTitle("Electricity Bill Generator");
        setSize(700, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // North Panel - Title and Month Selection
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(230, 230, 250));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel heading = new JLabel("⚡ Electricity Bill Generator");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(heading, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        inputPanel.setBackground(new Color(230, 230, 250));

        JLabel meterLabel = new JLabel("Meter No: " + meter);
        meterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        searchmonthcho = new Choice();
        for (String month : new String[]{
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"}) {
            searchmonthcho.add(month);
        }

        inputPanel.add(meterLabel);
        inputPanel.add(new JLabel("Select Month:"));
        inputPanel.add(searchmonthcho);
        topPanel.add(inputPanel, BorderLayout.CENTER);

        // Center Panel - Bill Text Area
        area = new JTextArea();
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        area.setBackground(new Color(248, 248, 255));
        area.setEditable(false);
        area.setMargin(new Insets(10, 10, 10, 10));
        area.setText("\n\n\t-------- Click 'Generate Bill' to view details --------");

        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // South Panel - Generate Button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(245, 245, 245));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        bill = new JButton("Generate Bill");
        bill.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bill.setBackground(new Color(0, 123, 255));
        bill.setForeground(Color.WHITE);
        bill.setFocusPainted(false);
        bill.setPreferredSize(new Dimension(160, 40));
        bill.addActionListener(this);
        bottomPanel.add(bill);

        // Layout
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            database c = new database();
            String smonth = searchmonthcho.getSelectedItem();
            area.setText("\nPower Limited\nElectricity Bill for " + smonth + ", 2023\n");
            area.append("-------------------------------------------------------------\n");

            ResultSet rs = c.statement.executeQuery("select * from newcustomer where meter_no ='" + meter + "'");
            if (rs.next()) {
                area.append("Customer Name     : " + rs.getString("name") + "\n");
                area.append("Meter Number      : " + rs.getString("meter_no") + "\n");
                area.append("Address           : " + rs.getString("address") + "\n");
                area.append("City              : " + rs.getString("city") + "\n");
                area.append("State             : " + rs.getString("state") + "\n");
                area.append("Email             : " + rs.getString("email") + "\n");
                area.append("Phone             : " + rs.getString("phonenum") + "\n\n");
            }

            rs = c.statement.executeQuery("select * from meter_info where meter_no ='" + meter + "'");
            if (rs.next()) {
                area.append("Meter Location    : " + rs.getString("meter_location") + "\n");
                area.append("Meter Type        : " + rs.getString("meter_type") + "\n");
                area.append("Phase Code        : " + rs.getString("phase_code") + "\n");
                area.append("Bill Type         : " + rs.getString("bill_type") + "\n");
                area.append("Days              : " + rs.getString("days") + "\n\n");
            }

            rs = c.statement.executeQuery("select * from tax");
            if (rs.next()) {
                area.append("Cost Per Unit     : ₹" + rs.getString("cost_per_unit") + "\n");
                area.append("Meter Rent        : ₹" + rs.getString("meter_rent") + "\n");
                area.append("Service Charge    : ₹" + rs.getString("service_charge") + "\n");
                area.append("Service Tax       : ₹" + rs.getString("service_tax") + "\n");
                area.append("Swachh Bharat     : ₹" + rs.getString("swatch_bharat") + "\n");
                area.append("Fixed Tax         : ₹" + rs.getString("fixed_text") + "\n\n");
            }

            rs = c.statement.executeQuery("select * from bill where meter_no = '" + meter + "' and month = '" + smonth + "'");
            if (rs.next()) {
                area.append("Billing Month     : " + rs.getString("month") + "\n");
                area.append("Units Consumed    : " + rs.getString("unit") + "\n");
                area.append("Total Charges     : ₹" + rs.getString("total_bill") + "\n");
                area.append("Total Payable     : ₹" + rs.getString("total_bill") + "\n");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new generate_bill("123456");
    }
}
