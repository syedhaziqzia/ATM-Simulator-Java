package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class MiniStatement extends JFrame implements ActionListener{
 
    JButton b1, b2;
    JLabel l1;
    MiniStatement(String pin){
        super("Haziq's ATM Simulator - Mini Statement");
        getContentPane().setBackground(new Color(240, 248, 255));
        setSize(400,600);
        setLocation(20,20);
        
        l1 = new JLabel();
        add(l1);
        
        JLabel l2 = new JLabel("Haziq's ATM Simulator");
        l2.setFont(new Font("Segoe UI", Font.BOLD, 16));
        l2.setForeground(new Color(10, 25, 49));
        l2.setBounds(110, 20, 200, 20);
        add(l2);
        
        JLabel l3 = new JLabel();
        l3.setBounds(20, 80, 300, 20);
        add(l3);
        
        JLabel l4 = new JLabel();
        l4.setBounds(20, 400, 300, 20);
        add(l4);
        
        try{
            Conn c = new Conn();
            String query1 = "SELECT * FROM login WHERE pin = ?";
            PreparedStatement pstmt1 = c.c.prepareStatement(query1);
            pstmt1.setString(1, pin);
            ResultSet rs = pstmt1.executeQuery();
            while(rs.next()){
                l3.setText("Card Number:    " + rs.getString("cardnumber").substring(0, 4) + "XXXXXXXX" + rs.getString("cardnumber").substring(12));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        	 
        try{
            int balance = 0;
            Conn c1  = new Conn();
            String query2 = "SELECT * FROM bank WHERE pin = ?";
            PreparedStatement pstmt2 = c1.c.prepareStatement(query2);
            pstmt2.setString(1, pin);
            ResultSet rs = pstmt2.executeQuery();
            while(rs.next()){
                l1.setText(l1.getText() + "<html>"+rs.getString("date")+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rs.getString("type") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rs.getString("amount") + "<br><br><html>");
                if(rs.getString("type").equals("Deposit")){
                    balance += Integer.parseInt(rs.getString("amount"));
                }else{
                    balance -= Integer.parseInt(rs.getString("amount"));
                }
            }
            l4.setText("Your total Balance is Rs "+balance);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        setLayout(null);
        b1 = new JButton("Exit");
        b1.setBackground(new Color(10, 25, 49));
        b1.setForeground(Color.WHITE);
        b1.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                b1.setBackground(new Color(20, 50, 90));
            }
            public void mouseExited(MouseEvent evt) {
                b1.setBackground(new Color(10, 25, 49));
            }
        });
        add(b1);
        
        b1.addActionListener(this);
        
        l1.setBounds(20, 140, 400, 200);
        b1.setBounds(20, 500, 100, 25);
    }
    public void actionPerformed(ActionEvent ae){
        this.setVisible(false);
    }
    
    public static void main(String[] args){
        new MiniStatement("").setVisible(true);
    }
    
}
