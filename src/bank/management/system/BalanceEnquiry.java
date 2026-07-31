package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import javax.swing.*;

class BalanceEnquiry extends JFrame implements ActionListener {

    JTextField t1, t2;
    JButton b1;
    JLabel l1;
    String pin;

    BalanceEnquiry(String pin) {
        this.pin = pin;

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 960, 1080);
        add(l3); 

        l1 = new JLabel();
        l1.setForeground(new Color(240, 248, 255));
        l1.setFont(new Font("System", Font.BOLD, 16));

        b1 = new JButton("BACK");

        setLayout(null);

        l1.setBounds(190, 350, 400, 35);
        l3.add(l1);

        b1.setBounds(390, 633, 150, 35);
        l3.add(b1);

        int balance = 0;
        try {
            Conn c1 = new Conn();
            String query = "SELECT * FROM bank WHERE pin = ?";
            java.sql.PreparedStatement pstmt = c1.c.prepareStatement(query);
            pstmt.setString(1, pin);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("type").equals("Deposit")) {
                    balance += Integer.parseInt(rs.getString("amount"));
                } else {
                    balance -= Integer.parseInt(rs.getString("amount"));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage(), "ATM Simulator", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        l1.setText("Your Current Account Balance is Rs " + balance);

        b1.addActionListener(this);
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

        setSize(960, 1080);
        setUndecorated(true);
        setLocation(500, 0);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Transactions(pin).setVisible(true);
    }

    public static void main(String[] args) {
        new BalanceEnquiry("").setVisible(true);
    }
}
