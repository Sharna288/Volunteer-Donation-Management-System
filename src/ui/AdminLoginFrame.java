package ui;

import db.DBConnection;

import javax.swing.*;
import java.sql.*;

public class AdminLoginFrame {

    public AdminLoginFrame() {
        JFrame f = new JFrame("Admin Login - Volunteer & Donation Management System");

        JLabel lUser = new JLabel("Username:");
        JLabel lPass = new JLabel("Password:");

        JTextField user = new JTextField();
        JPasswordField pass = new JPasswordField();

        JButton login = new JButton("Login");

        lUser.setBounds(50,30,100,25);
        lPass.setBounds(50,70,100,25);
        user.setBounds(150,30,150,25);
        pass.setBounds(150,70,150,25);
        login.setBounds(130,120,100,30);

        login.addActionListener(e -> {
            try{
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "SELECT * FROM users WHERE username=? AND password=? AND user_type='admin'"
                );
                ps.setString(1,user.getText());
                ps.setString(2,new String(pass.getPassword()));

                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    f.dispose();
                    new AdminDashboard(); // Directly show users & donations list
                } else {
                    JOptionPane.showMessageDialog(f,"Invalid Admin Login");
                }

            } catch(Exception ex){ ex.printStackTrace(); }
        });

        f.add(lUser); f.add(lPass);
        f.add(user); f.add(pass);
        f.add(login);

        f.setSize(380,200);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
