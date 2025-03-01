package src.electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class meterInfo extends JFrame implements ActionListener {
    JLabel heading , meterNo , meterType, phaseCode, billType, meterLoc;
    Choice meterChoice , meterTypeChoice , phaseCodeChoice , billTypeChoice;
    JButton submitBtn;
    String meterNumber;


    meterInfo(String meterNumber){
        this.meterNumber = meterNumber;
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(252,186,3));
        add(panel);

        heading = new JLabel("Meter Information");
        heading.setBounds(180,10,200,20);
        heading.setFont(new Font("tanhoma",Font.BOLD,20));
        panel.add(heading);

        meterNo = new JLabel("Meter Number : ");
        meterNo.setBounds(50,80,140,20);
        meterNo.setFont(new Font("Arial",Font.BOLD,15));
        panel.add(meterNo);

        JLabel meterNoText = new JLabel(meterNumber);  // pass the meter no in the j label
        meterNoText.setBounds(200,80,150,20);
        panel.add(meterNoText);

//        meterNo.setText(meterNumber);


        meterLoc = new JLabel("Meter Location : ");
        meterLoc.setBounds(50,120,140,20);
        meterLoc.setFont(new Font("Arial",Font.BOLD,15));
        panel.add(meterLoc);


        meterChoice = new Choice();
        meterChoice.setBounds(200,120,150,20);
        meterChoice.add("outSide");
        meterChoice.add("InSide");
        panel.add(meterChoice);


        meterType = new JLabel("Meter Type : ");
        meterType.setBounds(50,160,140,20);
        meterType.setFont(new Font("Arial",Font.BOLD,15));
        panel.add(meterType);

        meterTypeChoice = new Choice();
        meterTypeChoice.setBounds(200,160,150,20);
        meterTypeChoice.add("Electric Meter");
        meterTypeChoice.add("Gas Meter");
        panel.add(meterTypeChoice);

        phaseCode = new JLabel("Phase Code : ");
        phaseCode.setBounds(50,200,140,20);
        phaseCode.setFont(new Font("Arial",Font.BOLD,15));
        panel.add(phaseCode);

        phaseCodeChoice = new Choice();
        phaseCodeChoice.setBounds(200,200,150,20);
        phaseCodeChoice.add("011");
        phaseCodeChoice.add("112");
        phaseCodeChoice.add("213");
        phaseCodeChoice.add("314");
        phaseCodeChoice.add("415");
        phaseCodeChoice.add("516");
        phaseCodeChoice.add("617");
        phaseCodeChoice.add("718");
        phaseCodeChoice.add("819");
        phaseCodeChoice.add("920");
        panel.add(phaseCodeChoice);

        billType = new JLabel("Bill Type : ");
        billType.setBounds(50,240,140,20);
        billType.setFont(new Font("Arial",Font.BOLD,15));
        panel.add(billType);

        billTypeChoice = new Choice();
        billTypeChoice.setBounds(200,240,150,20);
        billTypeChoice.add("Normal");
        billTypeChoice.add("Industrial");
        panel.add(billTypeChoice);


        JLabel biilingType = new JLabel("30 Days Billing Time.............");
        biilingType.setBounds(50,280,250,20);
        biilingType.setFont(new Font("Arial",Font.BOLD,15));
        panel.add(biilingType);

        JLabel note = new JLabel("Note : ");
        note.setBounds(50,320,100,20);
        note.setFont(new Font("Arial",Font.BOLD,15));
        panel.add(note);

        JLabel bydefault = new JLabel("By Default Bill is Calculated for 30 Days");
        bydefault.setBounds(50,340,300,20);
        bydefault.setFont(new Font("Arial",Font.BOLD,15));
        panel.add(bydefault);


        submitBtn = new JButton("Submit");
        submitBtn.setFont(new Font("Arial",Font.BOLD,15));
        submitBtn.setBounds(300,380,100,30);
        submitBtn.setBackground(Color.black);
        submitBtn.addActionListener(this);
        submitBtn.setForeground(Color.white);
        panel.add(submitBtn);


        setSize(700,500);
        setLocation(400,200);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String sMeterNo = meterNumber;
        String sMeterLoc = meterChoice.getSelectedItem();
        String sMeterType = meterTypeChoice.getSelectedItem();
        String sPhaseCode = phaseCodeChoice.getSelectedItem();
        String sBillType = billTypeChoice.getSelectedItem();
        String sDays = "30";
        try{
            database c = new database();
            String insertQuery = "insert into meter_info values('"+sMeterNo+"','"+sMeterLoc+"','"+sMeterType+"','"+sPhaseCode+"','"+sBillType+"','"+sDays+"')";
            c.statement.executeUpdate(insertQuery);
            JOptionPane.showMessageDialog(null,"Meter Information Added Successfully");
            setVisible(false);
        }catch (Exception error){
            System.out.println("error");
            error.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new meterInfo("9r80w980w9");
    }
}
