
package src.electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;

public class main_class extends JFrame {

    JMenuItem logout, profile, newCustomer, customerDetails, depositDetails, calculateBill, UpdateInformation, viewInformation;

    String accountType;
    String meterPass;

    public main_class(String accountType, String meterPass) {
        super("Electricity Billing System");
        this.accountType = accountType;
        this.meterPass = meterPass;

        // Set full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false); // keep it like login/register

        // Background Gradient Panel
        JPanel background = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(15, 12, 41),
                        0, getHeight(), new Color(48, 43, 99));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        background.setLayout(new BorderLayout());
        setContentPane(background);

        // Overlay panel (semi-transparent)
        JPanel overlay = new JPanel(new BorderLayout());
        overlay.setOpaque(false);
        background.add(overlay, BorderLayout.CENTER);

        // Welcome Message Panel
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 30));
        centerPanel.setOpaque(false);
        JLabel title = new JLabel("Electricity Billing System", JLabel.CENTER);
        title.setFont(new Font("Verdana", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        JLabel welcome = new JLabel("Welcome, " + (accountType.equals("Admin") ? "Administrator" : "Customer"), JLabel.CENTER);
        welcome.setFont(new Font("Verdana", Font.PLAIN, 24));
        welcome.setForeground(Color.LIGHT_GRAY);

        centerPanel.add(title);
        centerPanel.add(welcome);

        // Info Panel to fill middle space nicely
        JPanel infoPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        // Add info cards
        infoPanel.add(createInfoCard("Total Customers", getTotalCustomers(), new Color(72, 61, 139))); // Dark Slate Blue
        infoPanel.add(createInfoCard("Bills Generated", getBillsGenerated(), new Color(106, 90, 205))); // Slate Blue
        infoPanel.add(createInfoCard("Total Revenue", "$" + getTotalRevenue(), new Color(123, 104, 238))); // Medium Slate Blue

        centerPanel.add(infoPanel);

        overlay.add(centerPanel, BorderLayout.CENTER);

        // Status bar
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(new Color(0, 0, 50, 180));
        statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel statusLabel = new JLabel("User Type: " + accountType);
        statusLabel.setForeground(Color.WHITE);

        JLabel clock = new JLabel();
        clock.setForeground(Color.WHITE);

        Timer t = new Timer(1000, e -> {
            clock.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        });
        t.start();

        statusPanel.add(statusLabel, BorderLayout.WEST);
        statusPanel.add(clock, BorderLayout.EAST);

        overlay.add(statusPanel, BorderLayout.SOUTH);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(240, 240, 240));
        setJMenuBar(menuBar);

        if (accountType.equals("Admin")) {
            JMenu menu = new JMenu("Menu");
            menu.setFont(new Font("Segoe UI", Font.BOLD, 18));

            newCustomer = createMenuItem("New Customer", e -> new newCustomer());
            customerDetails = createMenuItem("Customer Details", e -> new customer_details());
            depositDetails = createMenuItem("Deposit Bar", e -> new DepositDetails());
            calculateBill = createMenuItem("Calculate Bill", e -> new calculate_bill());

            menu.add(newCustomer);
            menu.add(customerDetails);
            menu.add(depositDetails);
            menu.add(calculateBill);

            menuBar.add(menu);
        } else {
            JMenu bill = new JMenu("Bill");
            bill.setFont(new Font("Segoe UI", Font.BOLD, 18));
            JMenuItem generateBill = createMenuItem("Generate Bill", e -> new generate_bill(meterPass) {});
            bill.add(generateBill);
            menuBar.add(bill);

            JMenu user = new JMenu("User");
            user.setFont(new Font("Segoe UI", Font.BOLD, 18));
            profile = createMenuItem("Profile", e -> {});
            JMenuItem payBill = createMenuItem("Pay Bill", e -> new pay_bill(meterPass) {});
            logout = createMenuItem("Logout", e -> logout());
            user.add(profile);
            user.add(payBill);
            user.add(logout);
            menuBar.add(user);

            JMenu info = new JMenu("Information");
            info.setFont(new Font("Segoe UI", Font.BOLD, 18));
            UpdateInformation = createMenuItem("Update Information", e -> new update_information(meterPass) {});
            viewInformation = createMenuItem("View Information", e -> new view_information(meterPass) {});
            info.add(UpdateInformation);
            info.add(viewInformation);
            menuBar.add(info);
        }

        JMenu utility = new JMenu("Utility");
        utility.setFont(new Font("Segoe UI", Font.BOLD, 18));
        JMenuItem notepad = createMenuItem("Notepad", e -> openUtility("notepad.exe"));
        JMenuItem calc = createMenuItem("Calculator", e -> openUtility("calc.exe"));
        utility.add(notepad);
        utility.add(calc);
        menuBar.add(utility);

        JMenu exit = new JMenu("Exit");
        exit.setFont(new Font("Segoe UI", Font.BOLD, 18));
        JMenuItem exitItem = createMenuItem("Exit", e -> {
            JOptionPane.showMessageDialog(this, "Exited Successfully");
            System.exit(0);
        });
        exit.add(exitItem);
        menuBar.add(exit);

        setVisible(true);
    }

    private JMenuItem createMenuItem(String text, ActionListener action) {
        JMenuItem item = new JMenuItem(text);
        item.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        item.setToolTipText(text);
        item.addActionListener(action);
        item.setBackground(Color.WHITE);
        addHoverEffect(item);
        return item;
    }

    private void logout() {
        JOptionPane.showMessageDialog(this, "Logout Successful");
        new Login();
        setVisible(false);
    }

    private void openUtility(String command) {
        try {
            Runtime.getRuntime().exec(command);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error opening utility: " + command);
        }
    }

    private void addHoverEffect(JMenuItem item) {
        item.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                item.setBackground(new Color(173, 216, 230));
            }

            public void mouseExited(MouseEvent e) {
                item.setBackground(Color.WHITE);
            }
        });
    }

    // --- New helper methods for info cards and DB queries ---

    private JPanel createInfoCard(String title, String value, Color bgColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(bgColor);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        card.setPreferredSize(new Dimension(250, 150));
        card.setOpaque(true);

        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        JLabel valueLabel = new JLabel(value, JLabel.CENTER);
        valueLabel.setFont(new Font("Verdana", Font.BOLD, 36));
        valueLabel.setForeground(Color.WHITE);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private String getTotalCustomers() {
        try {
            database c = new database();
            ResultSet rs = c.statement.executeQuery("SELECT COUNT(*) FROM signUp");
            if (rs.next()) return String.valueOf(rs.getInt(1));
            rs.close();
//            stmt.close();
//            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    private String getBillsGenerated() {
        try {
           database c = new database();
            ResultSet rs = c.statement.executeQuery("SELECT COUNT(*) FROM bill");
            if (rs.next()) return String.valueOf(rs.getInt(1));
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    private String getTotalRevenue() {
        try {
            database c = new database();
            ResultSet rs = c.statement.executeQuery("SELECT SUM(total_bill) FROM bill");
            if (rs.next()) return String.format("%.2f", rs.getDouble(1));
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    public static void main(String[] args) {
        new main_class("Admin", ""); // Or "Customer"
    }
}
