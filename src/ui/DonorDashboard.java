package ui;

import javax.swing.*;

public class DonorDashboard {

    public DonorDashboard() {
        JFrame f = new JFrame("Donor Dashboard");

        JButton donate = new JButton("Make Donation");
        donate.setBounds(80,60,180,30);

        f.add(donate);

        f.setSize(350,200);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
