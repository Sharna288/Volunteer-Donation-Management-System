package ui;

import db.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DonationForm {

    public DonationForm() {
        JFrame f = new JFrame("Make Donation");
        f.setSize(400,250);
        f.setLayout(null);
        f.setLocationRelativeTo(null); // Center window
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel typeLabel = new JLabel("Type:");
        JLabel amountLabel = new JLabel("Amount:");
        JLabel bloodLabel = new JLabel("Blood Group:");

        typeLabel.setBounds(40,30,100,25);
        amountLabel.setBounds(40,65,100,25);
        bloodLabel.setBounds(40,100,100,25);

        JComboBox<String> type = new JComboBox<>(new String[]{"money","blood"});
        JTextField amount = new JTextField();
        JTextField blood = new JTextField();

        type.setBounds(150,30,150,25);
        amount.setBounds(150,65,150,25);
        blood.setBounds(150,100,150,25);

        JButton donate = new JButton("Donate");
        donate.setBounds(150,140,100,30);
        donate.setBackground(Color.GREEN);
        donate.setForeground(Color.WHITE);
        donate.setFont(new Font("Arial", Font.BOLD, 14));

        // Tooltips
        type.setToolTipText("Select donation type");
        amount.setToolTipText("Enter donation amount (numeric)");
        blood.setToolTipText("Enter blood group if donation type is blood");

        donate.addActionListener(e -> {
            String donationType = type.getSelectedItem().toString();
            String amt = amount.getText();
            String bloodGrp = blood.getText();

            // Input validation
            if(amt.isEmpty() || !amt.matches("\\d+")) {
                JOptionPane.showMessageDialog(f,"Enter valid numeric amount");
                return;
            }

            if(donationType.equals("blood") && bloodGrp.isEmpty()) {
                JOptionPane.showMessageDialog(f,"Enter blood group for blood donation");
                return;
            }

            // Database Insert
            try(Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO donations(donation_type,amount,blood_group) VALUES(?,?,?)")) {

                ps.setString(1, donationType);
                ps.setString(2, amt);
                ps.setString(3, bloodGrp);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(f,"Donation Successful");
                amount.setText("");
                blood.setText("");

            } catch(Exception ex) { ex.printStackTrace(); }
        });

        f.add(typeLabel); f.add(amountLabel); f.add(bloodLabel);
        f.add(type); f.add(amount); f.add(blood); f.add(donate);

        f.setVisible(true);
    }
}
