package src.electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class view_information extends JFrame implements ActionListener {
    String view;
    JButton cancel;

    view_information(String view) {
        this.view = view;

        // Gradient Background Panel
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

        // Main Content Panel with white background
        JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(850, 500));
        panel.setBackground(new Color(255, 255, 255, 230));

        JScrollPane outerScroll = new JScrollPane(panel);
        outerScroll.setOpaque(false);
        outerScroll.getViewport().setOpaque(false);
        outerScroll.setBorder(null);
        background.add(outerScroll);

        // Heading
        JLabel heading = new JLabel("View Customer Information", SwingConstants.CENTER);
        heading.setBounds(0, 20, 850, 40);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setForeground(Color.BLACK);
        panel.add(heading);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 15);

        // Labels
        int leftX = 100, rightX = 450, yGap = 40, startY = 100;

        String[] labels = {"Name:", "Meter Number:", "Address:", "City:"};
        JLabel[] labelLeft = new JLabel[labels.length];
        JLabel[] textLeft = new JLabel[labels.length];

        for (int i = 0; i < labels.length; i++) {
            labelLeft[i] = new JLabel(labels[i]);
            labelLeft[i].setBounds(leftX, startY + i * yGap, 150, 25);
            labelLeft[i].setFont(labelFont);
            panel.add(labelLeft[i]);

            textLeft[i] = new JLabel();
            textLeft[i].setBounds(leftX + 150, startY + i * yGap, 200, 25);
            textLeft[i].setFont(labelFont);
            panel.add(textLeft[i]);
        }

        String[] rLabels = {"State:", "Email:", "Phone:"};
        JLabel[] labelRight = new JLabel[rLabels.length];
        JLabel[] textRight = new JLabel[rLabels.length];

        for (int i = 0; i < rLabels.length; i++) {
            labelRight[i] = new JLabel(rLabels[i]);
            labelRight[i].setBounds(rightX, startY + i * yGap, 150, 25);
            labelRight[i].setFont(labelFont);
            panel.add(labelRight[i]);

            textRight[i] = new JLabel();
            textRight[i].setBounds(rightX + 150, startY + i * yGap, 200, 25);
            textRight[i].setFont(labelFont);
            panel.add(textRight[i]);
        }

        // Fetch Data
        try {
            database c = new database();
            System.out.println("this is from view Information "+ view);
            ResultSet rs = c.statement.executeQuery("SELECT * FROM newcustomer WHERE meter_no = '" + view + "'");
            if (rs.next()) {
                textLeft[0].setText(rs.getString("name"));
                textLeft[1].setText(rs.getString("meter_no"));
                textLeft[2].setText(rs.getString("address"));
                textLeft[3].setText(rs.getString("city"));

                textRight[0].setText(rs.getString("state"));
                textRight[1].setText(rs.getString("email"));
                textRight[2].setText(rs.getString("phonenum"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Cancel Button
        cancel = new JButton("Cancel");
        cancel.setBounds(350, 340, 150, 35);
        cancel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        cancel.setBackground(new Color(0, 123, 255));
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        panel.add(cancel);

        // Frame Settings
        setContentPane(background);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new view_information("9r80w980w9");
    }
}
