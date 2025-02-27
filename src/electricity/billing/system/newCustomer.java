package src.electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

public class newCustomer extends JFrame implements ActionListener {
    TextField nameText , addressText, cityText,stateText , emailText , phoneText;
    JLabel heading , customerName, meterNo , address , city, state,email , phone, meterText;
    JButton nextBtn , cancelBtn;
    newCustomer(){
        super("New Customer");


        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(252,186,3));
        add(panel);

        heading = new JLabel("New Customer");
        heading.setBounds(180,10,200,20);
        heading.setFont(new Font("Tahoma",Font.BOLD,20));
        panel.add(heading);

        customerName = new JLabel("New Customer : ");
        customerName.setBounds(50,80,100,20);
//        customerName.setFont(new Font("Arial",Font.BOLD,20));
        panel.add(customerName);


        nameText = new TextField();
        nameText.setBounds(180,80,150,20);
        panel.add(nameText);


        meterNo = new JLabel("Meter Number : ");
        meterNo.setBounds(50,120,100,20);
        panel.add(meterNo);


        meterText = new JLabel("");
        meterText.setBounds(180,120,150,20);
        panel.add(meterText);

        Random ran = new Random(); // for random number generator
        long number = ran.nextLong() % 10000000;
        meterText.setText("" + Math.abs(number));  // set text on the meter text

        address = new JLabel("Address : ");
        address.setBounds(50,160,100,20);
        panel.add(address);

        addressText = new TextField();
        addressText.setBounds(180,160,150,20);
        panel.add(addressText);

        city = new JLabel("City : ");
        city.setBounds(50,200,100,20);
        panel.add(city);

        cityText = new TextField();
        cityText.setBounds(180,200,150,20);
        panel.add(cityText);


        state = new JLabel("State : ");
        state.setBounds(50,240,100,20);
        panel.add(state);

        stateText = new TextField();
        stateText.setBounds(180,240,150,20);
        panel.add(stateText);


        email = new JLabel("Email : ");
        email.setBounds(50,280,100,20);
        panel.add(email);

        emailText = new TextField();
        emailText.setBounds(180,280,150,20);
        panel.add(emailText);

        phone = new JLabel("Phone : ");
        phone.setBounds(50,320,100,20);
        panel.add(phone);

        phoneText = new TextField();
        phoneText.setBounds(180,320,150,20);
        panel.add(phoneText);


        nextBtn = new JButton("Next");
        nextBtn.setBounds(120,360,100,30);
        nextBtn.setBackground(Color.black);
        nextBtn.setForeground(Color.white);
        nextBtn.addActionListener(this);
        panel.add(nextBtn);

        cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(300,360,100,30);
        cancelBtn.setBackground(new Color(0xC20514));
        cancelBtn.setForeground(Color.white);
        cancelBtn.addActionListener(this);
        panel.add(cancelBtn);


        setLayout(new BorderLayout());
        add(panel,BorderLayout.CENTER);
        setSize(700,500);
        setLocation(400,200);
        setVisible(true);



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

        if(e.getSource() == nextBtn){
            try{
                database c = new database();
                String insertQuery = "INSERT INTO NewCustomer VALUES('"+sMeter+"','"+sName+"','"+sAddress+"','"+sCity+"','"+sState+"','"+sEmail+"','"+sPhone+"')";
                String insertQuery1 = "insert into SignUp value('"+sMeter+"','','"+sName+"','','')";  // insert query in mysql database
                c.statement.executeUpdate(insertQuery);
                c.statement.executeUpdate(insertQuery1);
                JOptionPane.showMessageDialog(null,"Customer Added Successfully");
                setVisible(false);
                new meterInfo(sMeter);
                // pass props in the meterInfo
            }catch(Exception E){
                System.out.println(E.getMessage());
            }
        }
        else{
            setVisible(false);
            new main_class();
        }
    }

    public static void main(String[] args) {
        new newCustomer();
    }


}
