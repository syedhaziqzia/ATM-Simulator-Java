package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Deposit extends JFrame implements ActionListener {

    JTextField t1;
    JButton b1, b2;
    JLabel l1, l3;
    String pin;

    Deposit(String pin) {
        this.pin = pin;

        // Initialize and add the background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        l3 = new JLabel(i3); // Initialize l3
        l3.setBounds(0, 0, 960, 1080);
        add(l3); // Add l3 to the JFrame

        // Add components to the background label
        l1 = new JLabel("ENTER AMOUNT YOU WANT TO DEPOSIT");
        l1.setForeground(new Color(240, 248, 255));
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(190, 350, 400, 35);
        l3.add(l1);

        t1 = new JTextField();
        t1.setFont(new Font("Segoe UI", Font.BOLD, 22));
        t1.setBounds(190, 420, 320, 25);
        l3.add(t1);

        b1 = new JButton("DEPOSIT");
        b1.setBounds(390, 588, 150, 35);
        l3.add(b1);

        b2 = new JButton("BACK");
        b2.setBounds(390, 633, 150, 35);
        l3.add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);

        JButton[] buttons = {b1, b2};
        for (JButton btn : buttons) {
            btn.setBackground(new Color(10, 25, 49)); // Navy Blue button
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

        // Frame settings
        setLayout(null);
        setSize(960, 1080);
        setUndecorated(true);
        setLocation(500, 0);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String amount = t1.getText();
            Date date = new Date();
            if (ae.getSource() == b1) {
                if (t1.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the Amount you want to Deposit", "ATM Simulator", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    Conn c1 = new Conn(); // Assuming `Conn` is your database connection class
                    String query = "INSERT INTO bank (pin, date, type, amount) VALUES (?, ?, ?, ?)";
                    java.sql.PreparedStatement pstmt = c1.c.prepareStatement(query);
                    pstmt.setString(1, pin);
                    pstmt.setString(2, date.toString());
                    pstmt.setString(3, "Deposit");
                    pstmt.setString(4, amount);
                    pstmt.executeUpdate();
                    
                    JOptionPane.showMessageDialog(null, "Rs. " + amount + " Deposited Successfully", "ATM Simulator", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                    new Transactions(pin).setVisible(true); // Assuming `Transactions` is another screen
                }
            } else if (ae.getSource() == b2) {
                setVisible(false);
                new Transactions(pin).setVisible(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage(), "ATM Simulator", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Deposit("").setVisible(true);
    }
}
