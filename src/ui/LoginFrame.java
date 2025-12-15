package ui;

import db.DBConnection;
import javax.swing.*;
import java.sql.*;

public class LoginFrame {

    public LoginFrame() {
        JFrame f = new JFrame("User Login");

        JLabel u = new JLabel("Username:");
        JLabel p = new JLabel("Password:");

        JTextField user = new JTextField();
        JPasswordField pass = new JPasswordField();

        JButton login = new JButton("Login");
        JButton register = new JButton("Register");

        u.setBounds(50,40,80,25);
        p.setBounds(50,80,80,25);
        user.setBounds(150,40,150,25);
        pass.setBounds(150,80,150,25);
        login.setBounds(50,130,100,30);
        register.setBounds(200,130,100,30);

        // Login
        login.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "SELECT * FROM users WHERE username=? AND password=? AND user_type='donor'"
                );
                ps.setString(1,user.getText());
                ps.setString(2,new String(pass.getPassword()));

                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    JOptionPane.showMessageDialog(f,"Login Success");
                    f.dispose();
                    new DonationForm();
                } else {
                    JOptionPane.showMessageDialog(f,"Invalid Login");
                }
            } catch(Exception ex){ ex.printStackTrace(); }
        });

        // Register
        register.addActionListener(e -> {
            f.dispose();
            new RegisterFrame();
        });

        f.add(u); f.add(p); f.add(user); f.add(pass);
        f.add(login); f.add(register);

        f.setSize(380,230);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
