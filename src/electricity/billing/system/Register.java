package src.electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; // this is  for action mean when you click on button then what happend
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;

public class Register extends JFrame implements ActionListener {
    JTextField EmployerText,passwordText,userNameText,meterText, nameText;
    JButton signUpBtn, back;
    Choice registerChoice;

    int y = 60;
    Register(){
        super("Register");
        getContentPane().setBackground(new Color(168,203,255));
        JLabel register = new JLabel("Create Account As:");
        register.setBounds(300,y = y,100,20);
        add(register);

        registerChoice = new Choice();
        registerChoice.add("Admin");
        registerChoice.add("Customer");
        registerChoice.setBounds(400,y,100,20);
        add(registerChoice);


        JLabel meterNo = new JLabel("Meter No");
        meterNo.setBounds(300,y = y + 40,100,20);
        meterNo.setVisible(false);
        add(meterNo);

        meterText = new JTextField();
        meterText.setBounds(400,y,140,20);
        meterText.setVisible(false);
        add(meterText);


        JLabel Employer = new JLabel("Employer ID :");
        Employer.setBounds(300,y = y ,100,20);
        Employer.setVisible(true);
        add(Employer);

        EmployerText = new JTextField();
        EmployerText.setBounds(400,y,140,20);
        Employer.setVisible(true);
        add(EmployerText);


        JLabel userName = new JLabel("userName :");
        userName.setBounds(300,y = y + 40,100,20);
        add(userName);

        userNameText = new JTextField();
        userNameText.setBounds(400,y,140,20);
        add(userNameText);

        JLabel Name = new JLabel("Name :");
        Name.setBounds(300,y = y + 40,100,20);
        add(Name);


        nameText = new JTextField();
        nameText.setBounds(400,y,140,20);
        add(nameText);



        JLabel password = new JLabel("Password : ");
        password.setBounds(300,y = y + 40,100,20);
        add(password);
        passwordText = new JTextField();
        passwordText.setBounds(400,y,140,20);
        add(passwordText);




        // use which choice you are select and use event listener to handle the event
        registerChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String user = registerChoice.getSelectedItem(); // use getSelected item to get what is selected by user in this case if user choose admin then store admin in user
                System.out.println(user);
                if(user.equals("Customer")){
                    Employer.setVisible(false);
                    EmployerText.setVisible(false);
                    meterNo.setVisible(true);
                    meterText.setVisible(true);
                }
                else{
                    meterText.setVisible(false);
                    meterNo.setVisible(false);
                    Employer.setVisible(true);
                    EmployerText.setVisible(true);
                }
            }
        });


        signUpBtn = new JButton("signUp");
        signUpBtn.setBounds(350,y = y + 40,100,30);
        signUpBtn.setBackground(Color.green); // set background color
        signUpBtn.setForeground(Color.black); // this is for set color on the text or button
        signUpBtn.addActionListener(this);  // when click on this button then action performed on actionPerformed
        add(signUpBtn);

        back = new JButton("Back");
        back.setBounds(470,y = y,100,30);
        back.setBackground(new Color(0xC20514));
        back.setForeground(Color.white);
        back.addActionListener(this);
        add(back);



        ImageIcon profileOne = new ImageIcon(ClassLoader.getSystemResource("src/icon/splash/profile.png"));
        Image profileTwo = profileOne.getImage().getScaledInstance(250,250, Image.SCALE_DEFAULT); // width height get default
        ImageIcon fProfileOne = new ImageIcon(profileTwo);
        JLabel profileLabel = new JLabel(fProfileOne);
        profileLabel.setBounds(20,25,250,250); // left , top, width , height
        add(profileLabel);


        setSize(600,480);
        setLocation(400,200);
        setLayout(null);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) { // override the function from the actionListener
        // e.getSource -> its give me a resources  which button you clicked on
        if(e.getSource() == signUpBtn){
            String sloginAs = registerChoice.getSelectedItem();  // user selected what ? Admin : customer
            String sUserName = userNameText.getText();  // get the value from the text field
            String sName = nameText.getText();  // get the name from the name
            String sPassword = passwordText.getText();
            String sMeter = meterText.getText();
            String sEmployer = EmployerText.getText();


            try{
                database c = new database();

                // 1. check if userName already exist
                String checkQuery = "SELECT COUNT(*) FROM SignUp WHERE username = '"+sUserName+"'";
                ResultSet resultSet = c.statement.executeQuery(checkQuery);
                resultSet.next();
                int count = resultSet.getInt(1); // get count from query

                if(count > 0){
                    JOptionPane.showMessageDialog(null, "User Name already Exist");
                }
                else{
                    String query = null;
                    query = "insert into SignUp value('"+sMeter+"','"+sUserName+"','"+sName+"','"+sPassword+"','"+sloginAs+"')";  // insert query in mysql database
                    c.statement.executeUpdate(query);

                    JOptionPane.showMessageDialog(null,"Account Created SuccessFully");
                    setVisible(false);
                    new Login();
                }

            }catch (Exception error){
                error.printStackTrace();
            }
        }
        else if(e.getSource() == back){
            setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args) {
        new Register();
    }
}
