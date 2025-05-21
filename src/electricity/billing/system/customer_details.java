

package src.electricity.billing.system;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.sql.ResultSet;

public class customer_details extends JFrame implements ActionListener {

    JButton search, print, close;
    JComboBox<String> searchMeterCombo, searchNameCombo;
    JTable table;

    customer_details() {
        super("Customer Details");

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
        background.setLayout(new BorderLayout());
        background.setBorder(new EmptyBorder(30, 40, 40, 40));

        // Content panel with white background and rounded corners
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new RoundedBorder(20));
        contentPanel.setLayout(new BorderLayout(20, 20));
        background.add(contentPanel, BorderLayout.CENTER);

        // Header Label
        JLabel heading = new JLabel("Customer Details");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 28));
        heading.setForeground(new Color(30, 30, 30));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(new EmptyBorder(20, 0, 10, 0));
        contentPanel.add(heading, BorderLayout.NORTH);

        // Top search panel
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setOpaque(false);
        searchPanel.setBorder(new EmptyBorder(0, 20, 0, 20));
        contentPanel.add(searchPanel, BorderLayout.PAGE_START);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 15);

        // Meter No Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel searchMeterLabel = new JLabel("Search By Meter No:");
        searchMeterLabel.setFont(labelFont);
        searchPanel.add(searchMeterLabel, gbc);

        // Meter No ComboBox
        gbc.gridx = 1;
        searchMeterCombo = new JComboBox<>();
        searchMeterCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchMeterCombo.setPreferredSize(new Dimension(180, 28));
        searchPanel.add(searchMeterCombo, gbc);

        // Name Label
        gbc.gridx = 2;
        JLabel searchNameLabel = new JLabel("Search By Name:");
        searchNameLabel.setFont(labelFont);
        searchPanel.add(searchNameLabel, gbc);

        // Name ComboBox
        gbc.gridx = 3;
        searchNameCombo = new JComboBox<>();
        searchNameCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchNameCombo.setPreferredSize(new Dimension(180, 28));
        searchPanel.add(searchNameCombo, gbc);

        // Search Button
        gbc.gridx = 4;
        search = new JButton("Search");
        styleButton(search, new Color(0, 123, 255));
        search.addActionListener(this);
        searchPanel.add(search, gbc);

        // Print Button
        gbc.gridx = 5;
        print = new JButton("Print");
        styleButton(print, new Color(0, 153, 76));
        print.addActionListener(this);
        searchPanel.add(print, gbc);

        // Close Button
        gbc.gridx = 6;
        close = new JButton("Cancel");
        styleButton(close, new Color(204, 0, 51));
        close.addActionListener(this);
        searchPanel.add(close, gbc);

        // Table setup with scroll pane
        table = new JTable();
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setGridColor(new Color(220, 220, 220));
        table.setFillsViewportHeight(true);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(240, 240, 240));
        table.getTableHeader().setForeground(Color.DARK_GRAY);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
        scrollPane.setPreferredSize(new Dimension(920, 450));
        scrollPane.getViewport().setBackground(Color.WHITE);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Load combobox data from DB
        loadComboBoxes();

        // Load initial table data
        loadTableData();

        // Frame setup
        setContentPane(background);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Helper method to apply button styles
    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(6, 15, 6, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }

    private void loadComboBoxes() {
        try {
            database c = new database();
            ResultSet rs = c.statement.executeQuery("SELECT meter_no, name FROM NewCustomer");

            searchMeterCombo.removeAllItems();
            searchMeterCombo.addItem("Select Meter No");
            searchNameCombo.removeAllItems();
            searchNameCombo.addItem("Select Name");

            while (rs.next()) {
                searchMeterCombo.addItem(rs.getString("meter_no"));
                searchNameCombo.addItem(rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTableData() {
        try {
            database c = new database();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM NewCustomer");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == search) {
            String meter = (String) searchMeterCombo.getSelectedItem();
            String name = (String) searchNameCombo.getSelectedItem();

            String query = "SELECT * FROM NewCustomer WHERE 1=1 ";

            if (meter != null && !meter.equals("Select Meter No")) {
                query += "AND meter_no = '" + meter + "' ";
            }
            if (name != null && !name.equals("Select Name")) {
                query += "AND name = '" + name + "' ";
            }

            try {
                database c = new database();
                ResultSet rs = c.statement.executeQuery(query);
                table.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == print) {
            try {
                boolean complete = table.print();
                if (!complete) {
                    JOptionPane.showMessageDialog(this, "Printing Cancelled", "Print", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(this, "Error printing: " + ex.getMessage(), "Print Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == close) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new customer_details());
    }

    // Rounded border class for the white content panel
    private static class RoundedBorder extends LineBorder {
        private int radius;

        public RoundedBorder(int radius) {
            super(Color.LIGHT_GRAY, 1, true);
            this.radius = radius;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(lineColor);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}
