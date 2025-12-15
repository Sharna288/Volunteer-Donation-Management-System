package ui;

import javax.swing.*;

public class StartFrame {
    public StartFrame() {
        JFrame f = new JFrame("Volunteer & Donation Management System");
        JButton userBtn = new JButton("User");
        JButton adminBtn = new JButton("Admin");

        userBtn.setBounds(50,50,150,50);
        adminBtn.setBounds(220,50,150,50);

        userBtn.setFont(userBtn.getFont().deriveFont(16f));
        adminBtn.setFont(adminBtn.getFont().deriveFont(16f));

        userBtn.addActionListener(e -> {
            f.dispose();
            new LoginFrame(); // user login + registration
        });

        adminBtn.addActionListener(e -> {
            f.dispose();
            new AdminLoginFrame(); // admin login
        });

        f.add(userBtn);
        f.add(adminBtn);

        f.setSize(450,200);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new StartFrame();
    }
}
