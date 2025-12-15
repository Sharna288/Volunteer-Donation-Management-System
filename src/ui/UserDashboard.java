package ui;

import javax.swing.*;

public class UserDashboard {
    private final String username;
    public UserDashboard(String username){
        this.username = username;

        JFrame f = new JFrame("User Dashboard - Welcome " + username);

        JButton donateBtn = new JButton("Make Donation");
        JButton viewDonationsBtn = new JButton("View My Donations");

        donateBtn.setBounds(50,50,180,40);
        viewDonationsBtn.setBounds(250,50,180,40);

        donateBtn.addActionListener(e -> new DonationForm(username));
        viewDonationsBtn.addActionListener(e -> new UserDonationsFrame(username));

        f.add(donateBtn);
        f.add(viewDonationsBtn);

        f.setSize(500,300);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
