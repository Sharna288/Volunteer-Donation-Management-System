package ui;

import javax.swing.*;

public class StartFrame {

    public StartFrame() {
        JFrame f = new JFrame("VDMS Role Selection");

        JButton adminBtn = new JButton("Admin");
        JButton userBtn = new JButton("User");

        adminBtn.setBounds(100,50,120,40);
        userBtn.setBounds(100,120,120,40);

        adminBtn.addActionListener(e -> {
            f.dispose();
            new AdminLoginFrame();
        });

        userBtn.addActionListener(e -> {
            f.dispose();
            new LoginFrame(); // User login / registration
        });

        f.add(adminBtn);
        f.add(userBtn);

        f.setSize(350,250);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
