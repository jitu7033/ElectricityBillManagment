
package src.electricity.billing.system;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.sql.ResultSet;

public class DepositDetails extends JFrame implements ActionListener {

    private JTable table;
    private Choice meterChoice, monthChoice;
    private JButton searchBtn, printBtn, cancelBtn;

    public DepositDetails() {
        super("Deposit Details");

        // === Gradient Background Panel ===
        JPanel background = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(74, 144, 226),
                        0, getHeight(), new Color(144, 224, 239));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        background.setLayout(new BorderLayout());

        // === Centered Content Panel ===
        JPanel contentPanel = new JPanel(null);
        contentPanel.setOpaque(true);
        contentPanel.setBackground(new Color(255, 255, 255, 245));
        contentPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
        contentPanel.setPreferredSize(new Dimension(1000, 650));
        contentPanel.setBounds(100, 50, 1100, 650);

        background.setLayout(null);
        background.add(contentPanel);

        JLabel heading = new JLabel("Electricity Bill - Deposit Details");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 26));
        heading.setBounds(350, 20, 500, 40);
        heading.setForeground(new Color(33, 37, 41));
        contentPanel.add(heading);

        // === Filter Labels & Choices ===
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 15);

        JLabel meterLabel = new JLabel("Meter No:");
        meterLabel.setBounds(60, 90, 100, 25);
        meterLabel.setFont(labelFont);
        contentPanel.add(meterLabel);

        meterChoice = new Choice();
        meterChoice.setBounds(150, 90, 150, 25);
        contentPanel.add(meterChoice);

        JLabel monthLabel = new JLabel("Month:");
        monthLabel.setBounds(330, 90, 80, 25);
        monthLabel.setFont(labelFont);
        contentPanel.add(monthLabel);

        monthChoice = new Choice();
        monthChoice.setBounds(410, 90, 150, 25);
        for (String month : new String[]{
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"}) {
            monthChoice.add(month);
        }
        contentPanel.add(monthChoice);

        // === Buttons ===
        searchBtn = createStyledButton("Search", new Color(0, 123, 255));
        searchBtn.setBounds(600, 87, 100, 30);
        contentPanel.add(searchBtn);

        printBtn = createStyledButton("Print", new Color(40, 167, 69));
        printBtn.setBounds(720, 87, 100, 30);
        contentPanel.add(printBtn);

        cancelBtn = createStyledButton("Cancel", new Color(220, 53, 69));
        cancelBtn.setBounds(840, 87, 100, 30);
        contentPanel.add(cancelBtn);

        // === Table ===
        table = new JTable();
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(230, 230, 230));

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(40, 140, 1000, 450);
        tablePane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Deposit Records",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 16), Color.DARK_GRAY));
        contentPanel.add(tablePane);

        // === Data Load ===
        loadMeterNumbers();
        loadAllBills();

        // === Listeners ===
        searchBtn.addActionListener(this);
        printBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        // === Frame Settings ===
        setContentPane(background);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void loadMeterNumbers() {
        try {
            database db = new database();
            ResultSet rs = db.statement.executeQuery("SELECT DISTINCT meter_no FROM bill");
            while (rs.next()) {
                meterChoice.add(rs.getString("meter_no"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAllBills() {
        try {
            database db = new database();
            ResultSet rs = db.statement.executeQuery("SELECT * FROM bill");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchBtn) {
            String meter = meterChoice.getSelectedItem();
            String month = monthChoice.getSelectedItem();
            try {
                String query = "SELECT * FROM bill WHERE meter_no = '" + meter + "' AND month = '" + month + "'";
                database db = new database();
                ResultSet rs = db.statement.executeQuery(query);
                table.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == printBtn) {
            try {
                table.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == cancelBtn) {
            dispose();
        }
    }

    public static void main(String[] args) {
        new DepositDetails();
    }
}
