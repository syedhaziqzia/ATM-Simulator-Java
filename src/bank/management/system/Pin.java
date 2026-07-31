package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Pin extends JFrame implements ActionListener{
    
    JPasswordField t1,t2;
    JButton b1,b2;                               
    JLabel l1,l2,l3;
    String pin;
    Pin(String pin){
        this.pin = pin;
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l4 = new JLabel(i3);
        l4.setBounds(0, 0, 960, 1080);
        add(l4);
        
        l1 = new JLabel("CHANGE YOUR PIN");
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setForeground(new Color(240, 248, 255));
        
        l2 = new JLabel("New PIN:");
        l2.setFont(new Font("System", Font.BOLD, 16));
        l2.setForeground(new Color(240, 248, 255));
        
        l3 = new JLabel("Re-Enter New PIN:");
        l3.setFont(new Font("System", Font.BOLD, 16));
        l3.setForeground(new Color(240, 248, 255));
        
        t1 = new JPasswordField();
        t1.setFont(new Font("Segoe UI", Font.BOLD, 25));
        
        t2 = new JPasswordField();
        t2.setFont(new Font("Segoe UI", Font.BOLD, 25));
        
        b1 = new JButton("CHANGE");
        b2 = new JButton("BACK");
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        
        JButton[] buttons = {b1, b2};
        for (JButton btn : buttons) {
            btn.setBackground(new Color(10, 25, 49));
            btn.setForeground(Color.WHITE);
            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    btn.setBackground(new Color(20, 50, 90));
                }
                public void mouseExited(MouseEvent evt) {
                    btn.setBackground(new Color(10, 25, 49));
                }
            });
        }
        
        setLayout(null);
        
        l1.setBounds(280,330,800,35);
        l4.add(l1);
        
        l2.setBounds(180,390,150,35);
        l4.add(l2);
        
        l3.setBounds(180,440,200,35);
        l4.add(l3);
        
        t1.setBounds(350,390,180,25);
        l4.add(t1);
        
        t2.setBounds(350,440,180,25);
        l4.add(t2);
        
        b1.setBounds(390,588,150,35);
        l4.add(b1);
        
        b2.setBounds(390,633,150,35);
        l4.add(b2);
        
        setSize(960,1080);
        setLocation(500,0);
        setUndecorated(true);
        setVisible(true);
    
    }
    
    public void actionPerformed(ActionEvent ae){
        try{        
            String npin = new String(t1.getPassword());
            String rpin = new String(t2.getPassword());
            
            if(!npin.equals(rpin)){
                JOptionPane.showMessageDialog(null, "Entered PIN does not match", "ATM Simulator", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            if(ae.getSource()==b1){
                if (new String(t1.getPassword()).equals("")){
                    JOptionPane.showMessageDialog(null, "Enter New PIN", "ATM Simulator", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (new String(t2.getPassword()).equals("")){
                    JOptionPane.showMessageDialog(null, "Re-Enter new PIN", "ATM Simulator", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                Conn c1 = new Conn();
                String q1 = "UPDATE bank SET pin = ? WHERE pin = ?";
                PreparedStatement pstmt1 = c1.c.prepareStatement(q1);
                pstmt1.setString(1, rpin);
                pstmt1.setString(2, pin);
                pstmt1.executeUpdate();
                
                String q2 = "UPDATE login SET pin = ? WHERE pin = ?";
                PreparedStatement pstmt2 = c1.c.prepareStatement(q2);
                pstmt2.setString(1, rpin);
                pstmt2.setString(2, pin);
                pstmt2.executeUpdate();
                
                String q3 = "UPDATE signupthree SET pin = ? WHERE pin = ?";
                PreparedStatement pstmt3 = c1.c.prepareStatement(q3);
                pstmt3.setString(1, rpin);
                pstmt3.setString(2, pin);
                pstmt3.executeUpdate();

                JOptionPane.showMessageDialog(null, "PIN changed successfully", "ATM Simulator", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
                new Transactions(rpin).setVisible(true);
            
            }else if(ae.getSource()==b2){
                new Transactions(pin).setVisible(true);
                setVisible(false);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage(), "ATM Simulator", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new Pin("").setVisible(true);
    }
}












