
package src.electricity.billing.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class meterInfo extends JFrame implements ActionListener {
    private JTextField meterNoText;
    private JComboBox<String> meterLocCombo, meterTypeCombo, phaseCodeCombo, billTypeCombo;
    private JButton submitBtn;
    private final String meterNumber;

    public meterInfo(String meterNumber) {
        this.meterNumber = meterNumber;

        setTitle("Meter Information");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Make full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Use BorderLayout for the frame
        setLayout(new BorderLayout());

        // Left panel for form with fixed width
        JPanel formPanel = new JPanel();
        formPanel.setPreferredSize(new Dimension(450, getHeight()));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(40, 40, 40, 40)); // padding
        formPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Heading
        JLabel heading = new JLabel("Meter Information");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 32));
        heading.setForeground(new Color(33, 37, 41));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(heading, gbc);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 16);

        // Row 1: Meter Number label + textfield (non-editable)
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel meterNoLabel = new JLabel("Meter Number:");
        meterNoLabel.setFont(labelFont);
        formPanel.add(meterNoLabel, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        meterNoText = new JTextField(meterNumber);
        meterNoText.setFont(inputFont);
        meterNoText.setEditable(false);
        meterNoText.setBackground(new Color(240, 240, 240));
        meterNoText.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        formPanel.add(meterNoText, gbc);

        // Row 2: Meter Location
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel meterLocLabel = new JLabel("Meter Location:");
        meterLocLabel.setFont(labelFont);
        formPanel.add(meterLocLabel, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        meterLocCombo = new JComboBox<>(new String[]{"Outside", "Inside"});
        meterLocCombo.setFont(inputFont);
        formPanel.add(meterLocCombo, gbc);

        // Row 3: Meter Type
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel meterTypeLabel = new JLabel("Meter Type:");
        meterTypeLabel.setFont(labelFont);
        formPanel.add(meterTypeLabel, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        meterTypeCombo = new JComboBox<>(new String[]{"Electric Meter", "Gas Meter"});
        meterTypeCombo.setFont(inputFont);
        formPanel.add(meterTypeCombo, gbc);

        // Row 4: Phase Code
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel phaseCodeLabel = new JLabel("Phase Code:");
        phaseCodeLabel.setFont(labelFont);
        formPanel.add(phaseCodeLabel, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        phaseCodeCombo = new JComboBox<>(new String[]{
                "011", "112", "213", "314", "415", "516", "617", "718", "819", "920"
        });
        phaseCodeCombo.setFont(inputFont);
        formPanel.add(phaseCodeCombo, gbc);

        // Row 5: Bill Type
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel billTypeLabel = new JLabel("Bill Type:");
        billTypeLabel.setFont(labelFont);
        formPanel.add(billTypeLabel, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        billTypeCombo = new JComboBox<>(new String[]{"Normal", "Industrial"});
        billTypeCombo.setFont(inputFont);
        formPanel.add(billTypeCombo, gbc);

        // Row 6: Note
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel noteLabel = new JLabel("Note: Bill is calculated for 30 days by default.");
        noteLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        noteLabel.setForeground(new Color(100, 100, 100));
        formPanel.add(noteLabel, gbc);

        // Row 7: Submit Button
        gbc.gridy++;
        submitBtn = new JButton("Submit");
        submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        submitBtn.setBackground(new Color(0, 123, 255));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        submitBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        submitBtn.setPreferredSize(new Dimension(150, 50));
        submitBtn.addActionListener(this);
        formPanel.add(submitBtn, gbc);

        add(formPanel, BorderLayout.WEST);

        // Right panel with gradient background and nice message or image
        JPanel rightPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw vertical gradient background
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(252, 186, 3);
                Color color2 = new Color(255, 140, 0);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        rightPanel.setLayout(new GridBagLayout());

        JLabel infoLabel = new JLabel("<html><div style='text-align:center;'>" +
                "<h1 style='color:white;'>Welcome to Meter Info Panel</h1>" +
                "<p style='color:#fff; font-size:18px;'>" +
                "Please fill out the meter details on the left panel.<br>" +
                "Ensure all information is accurate for proper billing.<br><br>" +
                "Contact support for any queries." +
                "</p></div></html>");
        rightPanel.add(infoLabel);

        add(rightPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String sMeterNo = meterNumber;
        String sMeterLoc = (String) meterLocCombo.getSelectedItem();
        String sMeterType = (String) meterTypeCombo.getSelectedItem();
        String sPhaseCode = (String) phaseCodeCombo.getSelectedItem();
        String sBillType = (String) billTypeCombo.getSelectedItem();
        String sDays = "30";

        // Basic validation
        if (sMeterLoc == null || sMeterType == null || sPhaseCode == null || sBillType == null) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            database c = new database();
            String insertQuery = "INSERT INTO meter_info " +
                    "(meter_no, meter_location, meter_type, phase_code, bill_type, billing_days) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            var ps = c.connection.prepareStatement(insertQuery);
            ps.setString(1, sMeterNo);
            ps.setString(2, sMeterLoc);
            ps.setString(3, sMeterType);
            ps.setString(4, sPhaseCode);
            ps.setString(5, sBillType);
            ps.setString(6, sDays);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Meter Information Added Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding meter information: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new meterInfo("9r80w980w9"));
    }
}
