package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Date;
import java.sql.*;

public class Withdrawl extends JFrame implements ActionListener {

    JTextField t1;
    JButton b1, b2;
    JLabel l1, l2, l3; // Declare l3 here for the background image
    String pin;

    // Constructor
    Withdrawl(String pin) {
        this.pin = pin;

        // Background Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg")); // Ensure 'icons/atm.jpg' exists
        Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        l3 = new JLabel(i3); // Initialize l3
        l3.setBounds(0, 0, 960, 1080);
        add(l3);

        // Labels
        l1 = new JLabel("MAXIMUM WITHDRAWAL IS RS.10,000");
        l1.setForeground(new Color(240, 248, 255));
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(190, 350, 400, 20);
        l3.add(l1);

        l2 = new JLabel("PLEASE ENTER YOUR AMOUNT");
        l2.setForeground(new Color(240, 248, 255));
        l2.setFont(new Font("System", Font.BOLD, 16));
        l2.setBounds(190, 400, 400, 20);
        l3.add(l2);

        // Text Field
        t1 = new JTextField();
        t1.setFont(new Font("Segoe UI", Font.BOLD, 25));
        t1.setBounds(190, 450, 330, 30);
        l3.add(t1);

        // Buttons
        b1 = new JButton("WITHDRAW");
        b1.setBounds(390, 588, 150, 35);
        l3.add(b1);

        b2 = new JButton("BACK");
        b2.setBounds(390, 633, 150, 35);
        l3.add(b2);

        // Add action listeners
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

        // Frame Settings
        setLayout(null);
        setSize(960, 1080);
        setLocation(500, 0);
        setUndecorated(true);
        setVisible(true);
    }

    // Action Handling
    public void actionPerformed(ActionEvent ae) {
        try {
            String amount = t1.getText();
            Date date = new Date();

            if (ae.getSource() == b1) { // Withdraw Button
                if (amount.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the amount you want to withdraw", "ATM Simulator", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    Conn c1 = new Conn();
                    
                    String q1 = "SELECT * FROM bank WHERE pin = ?";
                    PreparedStatement pstmt1 = c1.c.prepareStatement(q1);
                    pstmt1.setString(1, pin);
                    ResultSet rs = pstmt1.executeQuery();

                    int balance = 0;
                    while (rs.next()) {
                        if (rs.getString("type").equals("Deposit")) {
                            balance += Integer.parseInt(rs.getString("amount"));
                        } else {
                            balance -= Integer.parseInt(rs.getString("amount"));
                        }
                    }

                    if (balance < Integer.parseInt(amount)) {
                        JOptionPane.showMessageDialog(null, "Insufficient Balance", "ATM Simulator", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    String q2 = "INSERT INTO bank (pin, date, type, amount) VALUES (?, ?, ?, ?)";
                    PreparedStatement pstmt2 = c1.c.prepareStatement(q2);
                    pstmt2.setString(1, pin);
                    pstmt2.setString(2, date.toString());
                    pstmt2.setString(3, "Withdrawl");
                    pstmt2.setString(4, amount);
                    pstmt2.executeUpdate();
                    
                    JOptionPane.showMessageDialog(null, "Rs. " + amount + " Debited Successfully", "ATM Simulator", JOptionPane.INFORMATION_MESSAGE);

                    setVisible(false);
                    new Transactions(pin).setVisible(true);
                }
            } else if (ae.getSource() == b2) { // Back Button
                setVisible(false);
                new Transactions(pin).setVisible(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage(), "ATM Simulator", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Main Method
    public static void main(String[] args) {
        new Withdrawl("").setVisible(true);
    }
}
