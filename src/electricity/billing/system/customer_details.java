package src.electricity.billing.system;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.sql.ResultSet;

public class customer_details extends JFrame implements ActionListener {

    JButton search;
    JButton print;
    JButton close;


    Choice searchMeterCho;Choice searchNameCho;
    JTable table;  // this is for table view
    // set screen size
    int x = 20; int y = 40;
   customer_details(){
       super("customer Details");

       JPanel panel = new JPanel();
       panel.setLayout(null);
       panel.setBackground(Color.yellow);
       add(panel);


       JLabel searchMeter = new JLabel("Search By Meter No: ");
       searchMeter.setBounds(x,y,180,20);
       searchMeter.setFont(new Font("tahna",Font.BOLD,15));
       panel.add(searchMeter);


       searchMeterCho = new Choice();
       searchMeterCho.setBounds(x + 180, y, 150, 20);
       panel.add(searchMeterCho);

       try{
           database c = new database();
           ResultSet resultSet = c.statement.executeQuery("select * from NewCustomer");
           while (resultSet.next()){
//               System.out.println(resultSet.getString("meter_no"));
               searchMeterCho.add(resultSet.getString("meter_no"));
           }

       }catch (Exception error){
           error.printStackTrace();
       }

       JLabel searchName = new JLabel("Search By Name: ");
       searchName.setBounds(x = x + 350,y,150,20);
       searchName.setFont(new Font("tahna",Font.BOLD,15));
       panel.add(searchName);


       searchNameCho = new Choice();
       searchNameCho.setBounds(x + 150,y,150,20);
       panel.add(searchNameCho);

       try{
           database c = new database();
           ResultSet resultSet = c.statement.executeQuery("select * from NewCustomer");
           while(resultSet.next()){
               searchNameCho.add(resultSet.getString("name"));
           }
       }catch (Exception error){
           error.printStackTrace();
       }


       table = new JTable();
       try{
           database c = new database();
           ResultSet resultSet = c.statement.executeQuery("select * from NewCustomer");
           table.setModel(DbUtils.resultSetToTableModel(resultSet));  // DbUtils mean direct set table.
       }catch(Exception error){
           error.printStackTrace();
       }
       JScrollPane scrollPane = new JScrollPane(table);
       scrollPane.setBounds(0,110,700,500);
       scrollPane.setBackground(Color.white);
       panel.add(scrollPane);


       search = new JButton("search");
       search.setBackground(Color.blue);
       search.setBounds(20,70,80,30);
       search.setForeground(Color.white);
       search.setFont(new Font("tahna",Font.BOLD,12));
       search.addActionListener(this);
       panel.add(search);

       print = new JButton("Print");
       print.setBackground(Color.blue);
       print.setBounds(130,70,80,30);
       print.setForeground(Color.white);
       print.setFont(new Font("tahna",Font.BOLD,12));
       print.addActionListener(this);
       panel.add(print);

       close = new JButton("Cancel");
       close.setBackground(Color.red);
       close.setBounds(240,70,80,30);
       close.setForeground(Color.white);
       close.setFont(new Font("tahna",Font.BOLD,12));
       close.addActionListener(this);
       panel.add(close);








       setSize(700,500);
       setLocation(400,200);
       setVisible(true);
   }


    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource() == search){
            String query_search = "select * from NewCustomer where meter_no = '"+searchMeterCho.getSelectedItem()+"' and name='"+searchNameCho.getSelectedItem()+"'"; // search by which meter no is selected in dropdown
            try{
                database c = new database();
                ResultSet resultSet = c.statement.executeQuery(query_search);
                table.setModel(DbUtils.resultSetToTableModel(resultSet));
            }catch (Exception error){
                error.printStackTrace();
            }
       }
       else if(e.getSource() == print){
           try {
               table.print();
           } catch (PrinterException ex) {
               throw new RuntimeException(ex);
           }
       }
       else{
           setVisible(false);
       }
    }

    public static void main(String[] args) {
        new customer_details();
    }

}
