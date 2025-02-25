package src.electricity.billing.system;

import javax.swing.*;
import java.awt.*;

public class main_class extends JFrame{

    JMenuItem logout , profile , newCustomer , customerDetails,depositBar;

    main_class(){

        super("Main Page");


        JMenuBar menuBar = new JMenuBar();  // show menu bar in the top of the page
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("Menu");  // inset text in the page
        menu.setFont(new Font("serif", Font.BOLD,20));
        menuBar.add(menu);

        // for dropdown

        profile = new JMenuItem("Profile");
        profile.setFont(new Font("serif", Font.PLAIN,15));
        menu.add(profile);


        newCustomer = new JMenuItem("New Customer");
        newCustomer.setFont(new Font("serif", Font.PLAIN,15));
//        ImageIcon newCustomerIcon = new ImageIcon(ClassLoader.getSystemResource("src/icon/splash/newCustomer.png"));
//        Image CustomerImage = newCustomerIcon.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT);
//        newCustomer.setIcon(new ImageIcon(CustomerImage));
        menu.add(newCustomer);


        customerDetails = new JMenuItem("Customer Details");
        customerDetails.setFont(new Font("serif", Font.PLAIN,15));
        menu.add(customerDetails);


        depositBar = new JMenuItem("Deposit Bar");
        depositBar.setFont(new Font("serif",Font.PLAIN,15));
        menu.add(depositBar);

        logout = new JMenuItem("Logout");
        logout.setFont(new Font("serif", Font.PLAIN,15));
        menu.add(logout);







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


    public static void main(String[] args) {
        new main_class();
    }
}
