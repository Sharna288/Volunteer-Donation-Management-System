package ui;

import javax.swing.*;

public class RoleSelectionFrame {

    public RoleSelectionFrame() {
        JFrame f = new JFrame("Select Role");

        JButton userBtn = new JButton("User");
        JButton adminBtn = new JButton("Admin");

        userBtn.setBounds(50,50,120,40);
        adminBtn.setBounds(200,50,120,40);

        userBtn.addActionListener(e -> {
            f.dispose();
            new LoginFrame(); // Normal user login
        });

        adminBtn.addActionListener(e -> {
            f.dispose();
            new AdminLoginFrame(); // Admin login with PIN
        });

        f.add(userBtn);
        f.add(adminBtn);

        f.setSize(400,180);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
