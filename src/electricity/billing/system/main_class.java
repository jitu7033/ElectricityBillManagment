package src.electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main_class extends JFrame {

    JMenuItem logout , profile , newCustomer , customerDetails,depositDetails,calculateBill, UpdateInformation , viewInformation;

    main_class(){
        super("Main Page");

        JMenuBar menuBar = new JMenuBar();  // show menu bar in the top of the page
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("Menu");  // inset text in the page
        menu.setFont(new Font("serif", Font.BOLD,20));
        menuBar.add(menu);

        // for dropdown

        newCustomer = new JMenuItem("New Customer");
        newCustomer.setFont(new Font("serif", Font.PLAIN,15));
//        ImageIcon newCustomerIcon = new ImageIcon(ClassLoader.getSystemResource("src/icon/splash/newCustomer.png"));
//        Image CustomerImage = newCustomerIcon.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT);
//        newCustomer.setIcon(new ImageIcon(CustomerImage));
        menu.add(newCustomer);

        newCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new newCustomer();
            }
        });

        customerDetails = new JMenuItem("Customer Details");
        customerDetails.setFont(new Font("serif", Font.PLAIN,15));
        menu.add(customerDetails);


        depositDetails = new JMenuItem("Deposit Bar");
        depositDetails.setFont(new Font("serif",Font.PLAIN,15));
        menu.add(depositDetails);


        calculateBill = new JMenuItem("Calculate Bill");
        calculateBill.setFont(new Font("serif",Font.PLAIN,15));
        menu.add(calculateBill);


        JMenu information = new JMenu("Information");
        information.setFont(new Font("serif", Font.BOLD,20));
        menuBar.add(information);

        UpdateInformation = new JMenuItem("Update Information");
        UpdateInformation.setFont(new Font("serif",Font.PLAIN,15));
        information.add(UpdateInformation);

        viewInformation = new JMenuItem("View Information");
        viewInformation.setFont(new Font("serif",Font.PLAIN,15));
        information.add(viewInformation);



        JMenu user = new JMenu("User");
        user.setFont(new Font("serif", Font.BOLD,20));
        menuBar.add(user);

        profile = new JMenuItem("Profile");
        profile.setFont(new Font("serif", Font.PLAIN,15));
        user.add(profile);

        JMenuItem payBill = new JMenuItem("Pay Bill");
        payBill.setFont(new Font("serif",Font.PLAIN,15));
        user.add(payBill);

        logout = new JMenuItem("Logout");
        logout.setFont(new Font("serif", Font.PLAIN,15));
        user.add(logout);

        JMenu bill = new JMenu("Bill");
        bill.setFont(new Font("serif", Font.BOLD,20));
        menuBar.add(bill);

        JMenuItem generateBill = new JMenuItem("Generate Bill");
        generateBill.setFont(new Font("serif",Font.PLAIN,15));
        bill.add(generateBill);


        JMenu utility = new JMenu("Utility");
        utility.setFont(new Font("serif", Font.BOLD,20));
        menuBar.add(utility);


        JMenuItem notepad = new JMenuItem("Notepad");
        notepad.setFont(new Font("serif",Font.PLAIN,15));
        utility.add(notepad);


        JMenuItem calculator = new JMenuItem("Calculator");
        calculator.setFont(new Font("serif",Font.PLAIN,15));
        utility.add(calculator);

        JMenu exit = new JMenu("Exit");
        exit.setFont(new Font("serif",Font.BOLD,20));
        menuBar.add(exit);

        JMenuItem exit1 = new JMenuItem("Exit");
        exit1.setFont(new Font("serif",Font.PLAIN,15));
        exit.add(exit1);

        exit1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Exit Successfully");
                System.exit(0);
            }
        });

        logout.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Logout Successfully");
                new Login();
                setVisible(false);
            }
        });

        // insert image on the frame
        ImageIcon homeImage = new ImageIcon(ClassLoader.getSystemResource("src/icon/splash/pexels-pixabay-46169.jpg"));
        Image homeImage2 = homeImage.getImage();
        Image scaledHomeImage = homeImage2.getScaledInstance(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height,Image.SCALE_DEFAULT);
        JLabel homeLabel = new JLabel(new ImageIcon(scaledHomeImage));
        add(homeLabel);


        setExtendedState(JFrame.MAXIMIZED_BOTH); // set the size in full screen
        setLayout(new FlowLayout()); //that arrange component in left to right in a flow
        setVisible(true); // set screen visible

    }
//
//    public void actionPerformed(ActionEvent e){
//
//    }




    public static void main(String[] args) {
        new main_class();
    }
}
