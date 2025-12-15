package ui;

import db.DBConnection;

import javax.swing.*;
import java.sql.*;

public class AdminLoginFrame {

    public AdminLoginFrame() {
        JFrame f = new JFrame("Admin Login");

        JLabel lUser = new JLabel("Username");
        JLabel lPass = new JLabel("Password");

        JTextField user = new JTextField();
        JPasswordField pass = new JPasswordField();

        JButton login = new JButton("Login");

        lUser.setBounds(50,40,100,25);
        lPass.setBounds(50,80,100,25);
        user.setBounds(150,40,150,25);
        pass.setBounds(150,80,150,25);
        login.setBounds(130,130,100,30);

        login.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();

                PreparedStatement ps = con.prepareStatement(
                        "SELECT * FROM users WHERE username=? AND password=? AND user_type='admin'"
                );
                ps.setString(1, user.getText());
                ps.setString(2, new String(pass.getPassword()));

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    f.dispose();
                    new AdminDashboard(); // ✅ Admin Dashboard open
                } else {
                    JOptionPane.showMessageDialog(f, "Invalid Admin Username or Password");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        f.add(lUser);
        f.add(lPass);
        f.add(user);
        f.add(pass);
        f.add(login);

        f.setSize(380,220);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
