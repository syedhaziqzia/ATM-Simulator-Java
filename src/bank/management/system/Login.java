package bank.management.system;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JLabel l1, l2, l3;
    JTextField tf1;
    JPasswordField pf2;
    JButton b1, b2, b3;
    JCheckBox showPin;
  
    Login() {
        setTitle("Haziq's ATM Simulator");
        
        // Safe image loading - won't crash if icon is missing
        try {
            ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("./icons/logo.jpg"));
            if (i1.getIconWidth() > 0) {
                Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
                ImageIcon i3 = new ImageIcon(i2);
                JLabel l11 = new JLabel(i3);
                l11.setBounds(70, 10, 100, 100);
                add(l11);
            }
        } catch (Exception ignored) { }
        
        l1 = new JLabel("WELCOME TO ATM");
        l1.setFont(new Font("Segoe UI", Font.BOLD, 38));
        l1.setForeground(new Color(255, 204, 0)); // Gold text
        l1.setBounds(200, 40, 450, 40);
        add(l1);
        
        l2 = new JLabel("Card No:");
        l2.setFont(new Font("Segoe UI", Font.BOLD, 28));
        l2.setForeground(Color.WHITE);
        l2.setBounds(125, 150, 375, 30);
        add(l2);
        
        tf1 = new JTextField(15);
        tf1.setBounds(300, 150, 230, 30);
        tf1.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(tf1);
        
        l3 = new JLabel("PIN:");
        l3.setFont(new Font("Segoe UI", Font.BOLD, 28));
        l3.setForeground(Color.WHITE);
        l3.setBounds(125, 220, 375, 30);
        add(l3);
        
        pf2 = new JPasswordField(15);
        pf2.setFont(new Font("Segoe UI", Font.BOLD, 16));
        pf2.setBounds(300, 220, 230, 30);
        add(pf2);
        
        showPin = new JCheckBox("Show PIN");
        showPin.setBackground(new Color(10, 25, 49));
        showPin.setForeground(Color.WHITE);
        showPin.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        showPin.setBounds(540, 220, 100, 30);
        showPin.addActionListener(e -> {
            if (showPin.isSelected()) {
                pf2.setEchoChar((char) 0);
            } else {
                pf2.setEchoChar('\u2022');
            }
        });
        add(showPin);
                
        b1 = new JButton("SIGN IN");
        b1.setBackground(new Color(255, 204, 0)); // Gold button
        b1.setForeground(Color.BLACK);
        
        b2 = new JButton("CLEAR");
        b2.setBackground(new Color(255, 204, 0));
        b2.setForeground(Color.BLACK);
        
        b3 = new JButton("SIGN UP");
        b3.setBackground(new Color(255, 204, 0));
        b3.setForeground(Color.BLACK);
        
        setLayout(null);
        
        b1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b1.setBounds(300, 300, 100, 30);
        add(b1);
        
        b2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b2.setBounds(430, 300, 100, 30);
        add(b2);
        
        b3.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b3.setBounds(300, 350, 230, 30);
        add(b3);
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        
        getContentPane().setBackground(new Color(10, 25, 49)); // Navy Blue background
        
        setSize(800, 480);
        setLocation(550, 200);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        try {        
            if (ae.getSource() == b1) {
                Conn c1 = new Conn();
                String cardno = tf1.getText();
                String pin = new String(pf2.getPassword());
                
                // Using PreparedStatement for robustness and security
                String query = "SELECT * FROM login WHERE cardnumber = ? AND pin = ?";
                PreparedStatement pstmt = c1.c.prepareStatement(query);
                pstmt.setString(1, cardno);
                pstmt.setString(2, pin);
                
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    setVisible(false);
                    new Transactions(pin).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect Card Number or PIN", "Haziq's ATM Simulator", JOptionPane.ERROR_MESSAGE);
                }
            } else if (ae.getSource() == b2) {
                tf1.setText("");
                pf2.setText("");
            } else if (ae.getSource() == b3) {
                setVisible(false);
                new Signup().setVisible(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage(), "Haziq's ATM Simulator", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new Login().setVisible(true);
    }
}





