package src.electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {
    JTextField passwordText,userText;
    Choice loginChoice;

    JButton loginBtn, cancelBtn, signUpBtn;
    Login(){
        super("Login"); // help of this insert text on the head on this frame
        getContentPane().setBackground(Color.cyan); // this is for background color

        // here is user field and password field
        JLabel userName = new JLabel("UserName"); // using this show the text on frame
        userName.setBounds(300,60,100,20);
        add(userName); // add label on frame

        userText = new JTextField(); // use this for view the text field on frame
        userText.setBounds(400,60,150,20);
        add(userText);


        JLabel userPassword = new JLabel("Password"); // using this show the text on frame
        userPassword.setBounds(300,100,100,20);
        add(userPassword); // add label on frame

        passwordText = new JTextField();
        passwordText.setBounds(400,100,150,20);
        add(passwordText);



        // here is choice for user mode and admin mode

        JLabel login = new JLabel("Login As In");
        login.setBounds(300,140,100,20);
        add(login);

        loginChoice = new Choice(); // this is for create choices
        loginChoice.add("Admin");
        loginChoice.add("Customer");
        loginChoice.setBounds(400, 140,150,20);
        add(loginChoice);



        // here is inserted all the button login ,cancel, and signUp
        loginBtn = new JButton("Login"); // this is for create a button
        loginBtn.setBounds(330,180,100,20);
        loginBtn.addActionListener(this);  // this is to use the define when you clicking on this to inform the event listener
        add(loginBtn);


        cancelBtn = new JButton("Cancel"); // this is for create a button
        cancelBtn.setBounds(440,180,100,20);
        cancelBtn.addActionListener(this);
        add(cancelBtn);


        signUpBtn = new JButton("SignUP"); // this is for create a button
        signUpBtn.setBounds(400,220,100,20);
        signUpBtn.addActionListener(this);
        add(signUpBtn);



        // insert image on the frame
        ImageIcon profileOne = new ImageIcon(ClassLoader.getSystemResource("src/icon/splash/profile.png"));
        Image profileTwo = profileOne.getImage().getScaledInstance(250,250, Image.SCALE_DEFAULT); // width height get default
        ImageIcon fProfileOne = new ImageIcon(profileTwo);
        JLabel profileLabel = new JLabel(fProfileOne);
        profileLabel.setBounds(5,5,250,250); // left , top, width , height
        add(profileLabel);


        // this is my screen size and default layout
        setSize(600,300); // size of the window
        setLocation(400,200);
        setLayout(null); // remove default layout from the frame
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginBtn){

            String user = loginChoice.getSelectedItem();
            String sUserName = userText.getText();
            String sPassword = passwordText.getText();

            try{
                database c = new database();
                // 1. check if the user name exist
                String checkQuery = "SELECT * FROM SignUp WHERE userName = '"+sUserName+"' and password = '"+sPassword+"'and userType = '"+user+"'";  // check username exist on the data bases
                ResultSet resultSet = c.statement.executeQuery(checkQuery);

                if(resultSet.next()){
                    JOptionPane.showMessageDialog(null,"Login Successfully");
                    setVisible(false);
                    // go to the next page
                }
                else{
                    JOptionPane.showMessageDialog(null,"! Invalid  UserName or password or user");
                }
            }
            catch (Exception error){
                System.out.println("error");
                error.printStackTrace();
            }

        }
        else if(e.getSource() == cancelBtn){
            setVisible(false);
        }
        else if(e.getSource() == signUpBtn){
            setVisible(false);
            new Register();
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
