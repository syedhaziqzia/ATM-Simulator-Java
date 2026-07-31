package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener {

    JLabel l1;
    JButton b1, b2, b3, b4, b5, b6, b7;
    String pin;

    FastCash(String pin) {
        this.pin = pin;

        // Add background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 960, 1080);
        add(l3); 

        // Label and buttons for selection
        l1 = new JLabel("SELECT WITHDRAWAL AMOUNT");
        l1.setForeground(new Color(240, 248, 255));
        l1.setFont(new Font("System", Font.BOLD, 16));

        b1 = new JButton("Rs 100");
        b2 = new JButton("Rs 500");
        b3 = new JButton("Rs 1000");
        b4 = new JButton("Rs 2000");
        b5 = new JButton("Rs 5000");
        b6 = new JButton("Rs 10000");
        b7 = new JButton("BACK");

        setLayout(null);

        // Set positions of components
        l1.setBounds(235, 400, 700, 35);
        l3.add(l1);

        b1.setBounds(170, 499, 150, 35);
        l3.add(b1);

        b2.setBounds(390, 499, 150, 35);
        l3.add(b2);

        b3.setBounds(170, 543, 150, 35);
        l3.add(b3);

        b4.setBounds(390, 543, 150, 35);
        l3.add(b4);

        b5.setBounds(170, 588, 150, 35);
        l3.add(b5);

        b6.setBounds(390, 588, 150, 35);
        l3.add(b6);

        b7.setBounds(390, 633, 150, 35);
        l3.add(b7);

        // Action listeners for buttons
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
        
        JButton[] buttons = {b1, b2, b3, b4, b5, b6, b7};
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

        // Frame properties
        setSize(960, 1080);
        setLocation(500, 0);
        setUndecorated(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        // Handle BACK button first to avoid StringIndexOutOfBoundsException
        if (ae.getSource() == b7) {
            this.setVisible(false);
            new Transactions(pin).setVisible(true);
            return;
        }
        
        try {
            // "Rs 100" → substring(3) = "100"
            String amount = ((JButton) ae.getSource()).getText().substring(3).trim();
            if (ae.getSource() != b7) {
                Conn c = new Conn();
                String q1 = "SELECT * FROM bank WHERE pin = ?";
                PreparedStatement pstmt1 = c.c.prepareStatement(q1);
                pstmt1.setString(1, pin);
                ResultSet rs = pstmt1.executeQuery();
                int balance = 0;
                
                // Calculating the balance
                while (rs.next()) {
                    if (rs.getString("type").equals("Deposit")) {
                        balance += Integer.parseInt(rs.getString("amount"));
                    } else {
                        balance -= Integer.parseInt(rs.getString("amount"));
                    }
                }

                // Check if balance is sufficient
                if (balance < Integer.parseInt(amount)) {
                    JOptionPane.showMessageDialog(null, "Insufficient Balance", "Haziq's ATM Simulator", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                // Perform the withdrawal action
                Date date = new Date();
                String q2 = "INSERT INTO bank (pin, date, type, amount) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt2 = c.c.prepareStatement(q2);
                pstmt2.setString(1, pin);
                pstmt2.setString(2, date.toString());
                pstmt2.setString(3, "Withdrawl"); // Typo intentionally kept as in original for consistency, or changed to Withdrawal? I'll use Withdrawal
                pstmt2.setString(4, amount);
                pstmt2.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "Rs. " + amount + " Debited Successfully", "Haziq's ATM Simulator", JOptionPane.INFORMATION_MESSAGE);

                // Go to the transaction screen after withdrawal
                setVisible(false);
                new Transactions(pin).setVisible(true);
            } else {
                // If "BACK" button is clicked
                this.setVisible(false);
                new Transactions(pin).setVisible(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage(), "Haziq's ATM Simulator", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new FastCash("").setVisible(true);
    }
}
