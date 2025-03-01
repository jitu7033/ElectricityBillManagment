package src.electricity.billing.system;

import javax.swing.*;
import javax.xml.transform.Result;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;

public class calculate_bill extends JFrame implements ActionListener{
    Choice MeterChoice , monthChoice;
    String nameText1,addressText1,unitConsumedText1,monthChoice1;
    JTextField unitConsumedText;
    JButton submit , cancelBtn;
    JLabel MeterNo, name, address,unitConsumed,month, nameText,addressText;
    int x = 50; int y = 50;
    calculate_bill(){

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(252,186,3));
        add(panel);

        JLabel heading = new JLabel("Calculate ElectricCity Bill");
        heading.setBounds(180,10,400,20);
        heading.setFont(new Font("Tahoma",Font.BOLD,20));
        panel.add(heading);

        MeterNo = new JLabel("Meter NO : ");
        MeterNo.setFont(new Font("Arial",Font.BOLD,15));
        MeterNo.setBounds(x,y,100,20);
        panel.add(MeterNo);

        MeterChoice = new Choice();
        try{
            database c = new database();
            ResultSet resultSet = c.statement.executeQuery("select * from newCustomer");
            while(resultSet.next()){
                MeterChoice.add(resultSet.getString("meter_no"));
            }
            MeterChoice.setBounds(180,y,150,20);
            panel.add(MeterChoice);

        }catch (Exception E) {
            E.printStackTrace();
        }

        name = new JLabel("Name : ");
        name.setFont(new Font("Arial",Font.BOLD,15));
        name.setBounds(x,y+40,100,20);
        panel.add(name);

        nameText = new JLabel("");
        nameText.setBounds(180,y+40,150,20);
        nameText.setFont(new Font("Arial",Font.PLAIN,15));
        panel.add(nameText);


        address = new JLabel("Address : ");
        address.setFont(new Font("Arial",Font.BOLD,15));
        address.setBounds(x,y+80,100,20);
        panel.add(address);

        addressText = new JLabel("");
        addressText.setBounds(180,y+80,150,20);
        addressText.setFont(new Font("Arial",Font.PLAIN,15));
        panel.add(addressText);

        try{
            database c = new database();
            ResultSet resultSet = c.statement.executeQuery("select *  from newCustomer where meter_no = '"+MeterChoice.getSelectedItem()+"'");
            while(resultSet.next()){
               nameText.setText(resultSet.getString("name"));
               addressText.setText(resultSet.getString("address"));
            }

        }catch (Exception E){
            E.printStackTrace();
        }

        // when i changed meter no my name and are not change then i have to some thing for this
       MeterChoice.addItemListener(new ItemListener() {
           public void itemStateChanged(ItemEvent e) {
               try{
                   database c = new database();
                   ResultSet resultSet = c.statement.executeQuery("select *  from newCustomer where meter_no = '"+MeterChoice.getSelectedItem()+"'");
                   while(resultSet.next()){
                       nameText.setText(resultSet.getString("name"));
                       addressText.setText(resultSet.getString("address"));
                   }

               }catch (Exception E){
                   E.printStackTrace();
               }
           }
       });

        unitConsumed = new JLabel("Unit Consumed : ");
        unitConsumed.setFont(new Font("Arial",Font.BOLD,15));
        unitConsumed.setBounds(x,y+120,100,20);
        panel.add(unitConsumed);

        unitConsumedText = new JTextField();
        unitConsumedText.setBounds(180,y+120,150,20);
        unitConsumedText.setFont(new Font("Arial",Font.PLAIN,15));
        panel.add(unitConsumedText);

        month = new JLabel("Month : ");
        month.setFont(new Font("Arial",Font.BOLD,15));
        month.setBounds(x,y+160,100,20);
        panel.add(month);

        monthChoice = new Choice();
        monthChoice.setBounds(180,y+160,150,20);
        monthChoice.setFont(new Font("Arial",Font.PLAIN,15));
        monthChoice.add("January");
        monthChoice.add("February");
        monthChoice.add("March");
        monthChoice.add("April");
        monthChoice.add("May");
        monthChoice.add("June");
        monthChoice.add("July");
        monthChoice.add("August");
        monthChoice.add("September");
        monthChoice.add("October");
        monthChoice.add("November");
        monthChoice.add("December");
        panel.add(monthChoice);

        submit = new JButton("Submit");
        submit.setFont(new Font("Arial",Font.BOLD,15));
        submit.setBounds(150,y+220,100,30);
        submit.setBackground(Color.black);
        submit.setForeground(Color.white);
        submit.addActionListener(this);
        panel.add(submit);

        cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(new Font("Arial",Font.BOLD,15));
        cancelBtn.setBounds(300,y+220,100,30);
        cancelBtn.setBackground(new Color(0xC20514));
        cancelBtn.setForeground(Color.white);
        cancelBtn.addActionListener(this);
        panel.add(cancelBtn);



        setSize(700,500);
        setLocation(400,200);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e){


        if(e.getSource() == submit){
            String sMeterNo = MeterChoice.getSelectedItem();
            String sMonth  = monthChoice.getSelectedItem();
            String sUnit= unitConsumedText.getText();

            int total_bill = 0;
            int unit = Integer.parseInt(sUnit);

            String query_text = "select * from tax";
            try{
                database c = new database();
                ResultSet resultSet = c.statement.executeQuery(query_text);
                while(resultSet.next()){
                        total_bill += unit * Integer.parseInt(resultSet.getString("cost_per_unit"));
                        total_bill += unit * Integer.parseInt(resultSet.getString("meter_rent"));
                        total_bill += unit * Integer.parseInt(resultSet.getString("service_charge"));
                        total_bill += unit * Integer.parseInt(resultSet.getString("service_charge"));
                        total_bill += unit * Integer.parseInt(resultSet.getString("swatch_bharat"));
                        total_bill += unit * Integer.parseInt(resultSet.getString("fixed_text"));
                }
            }catch (Exception E){
                E.printStackTrace();
            }
            String total_Bill = "Insert into Bill values('"+sMeterNo+"','"+sMonth+"','"+sUnit+"','"+total_bill+"','Pending')";

            try{
                database c = new database();
                c.statement.executeUpdate(total_Bill);  // set data in databases
                JOptionPane.showMessageDialog(null,"Bill Generated Successfully");
                setVisible(false);

            }catch (Exception E){
                E.printStackTrace();
            }
        }

        else{
            setVisible(false);
        }
    }
    public static void main(String[] args) {
        new calculate_bill();
    }
}
