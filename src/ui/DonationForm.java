package ui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DonationForm {
    private final String username;

    public DonationForm(String username){
        this.username = username;

        JFrame f = new JFrame("Donation Form");

        JLabel lType = new JLabel("Donation Type:");
        JLabel lAmount = new JLabel("Amount:");
        JLabel lBlood = new JLabel("Blood Group:");

        String[] donationTypes = {"Money","Blood"};
        JComboBox<String> type = new JComboBox<>(donationTypes);

        JTextField amount = new JTextField();
        String[] bloodGroups = {"A+","A-","B+","B-","AB+","AB-","O+","O-"};
        JComboBox<String> blood = new JComboBox<>(bloodGroups);

        JButton submit = new JButton("Submit");

        // Set bounds
        lType.setBounds(50,30,120,25);
        type.setBounds(180,30,120,25);
        lAmount.setBounds(50,70,120,25);
        amount.setBounds(180,70,120,25);
        lBlood.setBounds(50,110,120,25);
        blood.setBounds(180,110,120,25);
        submit.setBounds(130,160,100,30);

        // Submit action
        submit.addActionListener(e -> {
            String donationType = type.getSelectedItem().toString();
            String amtText = amount.getText().trim();
            String bg = blood.getSelectedItem().toString();

            int amt = 0; // default for blood donation

            if(donationType.equals("Money")) {
                if(amtText.isEmpty() || !amtText.matches("\\d+")) {
                    JOptionPane.showMessageDialog(f,"Enter valid amount");
                    return;
                }
                amt = Integer.parseInt(amtText);
            }

            try (Connection con = DBConnection.getConnection()) {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO donations(donation_type, amount, blood_group, donor_username) VALUES(?,?,?,?)"
                );
                ps.setString(1, donationType);
                ps.setInt(2, amt);
                ps.setString(3, bg);
                ps.setString(4, username);

                int result = ps.executeUpdate();
                if(result > 0) {
                    JOptionPane.showMessageDialog(f,"Donation Submitted Successfully");
                    f.dispose();
                } else {
                    JOptionPane.showMessageDialog(f,"Donation could not be submitted");
                }
            } catch(Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(f,"Error submitting donation: " + ex.getMessage());
            }
        });

        // Add components
        f.add(lType); f.add(type);
        f.add(lAmount); f.add(amount);
        f.add(lBlood); f.add(blood);
        f.add(submit);

        f.setSize(400,250);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
