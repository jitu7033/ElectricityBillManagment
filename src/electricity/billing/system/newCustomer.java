
package src.electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class newCustomer extends JFrame implements ActionListener {

    TextField nameText, addressText, cityText, stateText, emailText, phoneText;
    JLabel meterText;
    JButton nextBtn, cancelBtn;

    public newCustomer() {
        super("New Customer");

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

        // Main form panel
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(600, 500));
        panel.setBackground(new Color(255, 255, 255, 220));

        // ScrollPane to ensure visibility on smaller screens
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(700, 600));

        background.add(scrollPane);
        setContentPane(background);

        JLabel heading = new JLabel("New Customer");
        heading.setBounds(200, 10, 300, 30);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setForeground(new Color(33, 33, 33));
        panel.add(heading);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);

        panel.add(createLabel("Customer Name:", 50, 60, labelFont));
        nameText = createTextField(200, 60);
        panel.add(nameText);

        panel.add(createLabel("Meter Number:", 50, 100, labelFont));
        meterText = new JLabel();
        meterText.setBounds(200, 100, 250, 25);
        meterText.setFont(labelFont);
        panel.add(meterText);

        Random ran = new Random();
        long number = Math.abs(ran.nextLong() % 10000000);
        meterText.setText("" + number);

        panel.add(createLabel("Address:", 50, 140, labelFont));
        addressText = createTextField(200, 140);
        panel.add(addressText);

        panel.add(createLabel("City:", 50, 180, labelFont));
        cityText = createTextField(200, 180);
        panel.add(cityText);

        panel.add(createLabel("State:", 50, 220, labelFont));
        stateText = createTextField(200, 220);
        panel.add(stateText);

        panel.add(createLabel("Email:", 50, 260, labelFont));
        emailText = createTextField(200, 260);
        panel.add(emailText);

        panel.add(createLabel("Phone:", 50, 300, labelFont));
        phoneText = createTextField(200, 300);
        panel.add(phoneText);

        nextBtn = new JButton("Next");
        nextBtn.setBounds(150, 360, 100, 35);
        nextBtn.setBackground(new Color(0, 153, 76));
        nextBtn.setForeground(Color.WHITE);
        nextBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nextBtn.addActionListener(this);
        panel.add(nextBtn);

        cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(280, 360, 100, 35);
        cancelBtn.setBackground(new Color(204, 0, 51));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelBtn.addActionListener(this);
        panel.add(cancelBtn);

        // Frame settings
        setExtendedState(JFrame.MAXIMIZED_BOTH); // fullscreen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JLabel createLabel(String text, int x, int y, Font font) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 130, 25);
        label.setFont(font);
        label.setForeground(new Color(40, 40, 40));
        return label;
    }

    private TextField createTextField(int x, int y) {
        TextField tf = new TextField();
        tf.setBounds(x, y, 250, 25);
        return tf;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String sName = nameText.getText();
        String sMeter = meterText.getText();
        String sAddress = addressText.getText();
        String sCity = cityText.getText();
        String sState = stateText.getText();
        String sEmail = emailText.getText();
        String sPhone = phoneText.getText();

        if (e.getSource() == nextBtn) {
            try {
                database c = new database();
                String insertQuery = "INSERT INTO NewCustomer VALUES('" + sMeter + "','" + sName + "','" + sAddress + "','" + sCity + "','" + sState + "','" + sEmail + "','" + sPhone + "')";
                String insertQuery1 = "INSERT INTO SignUp VALUES('" + sMeter + "','','" + sName + "','','')";
                c.statement.executeUpdate(insertQuery);
                c.statement.executeUpdate(insertQuery1);
                JOptionPane.showMessageDialog(null, "Customer Added Successfully");
                setVisible(false);
                new meterInfo(sMeter);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new newCustomer();
    }
}
