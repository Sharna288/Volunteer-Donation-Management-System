package ui;

import db.DBConnection;

import javax.swing.*;
import java.sql.*;

public class LoginFrame {
    public LoginFrame() {
        JFrame f = new JFrame("User Login");
        JLabel lUser = new JLabel("Username:");
        JLabel lPass = new JLabel("Password:");

        JTextField user = new JTextField();
        JPasswordField pass = new JPasswordField();

        JButton login = new JButton("Login");
        JButton register = new JButton("Register");

        lUser.setBounds(50,30,100,25);
        lPass.setBounds(50,70,100,25);
        user.setBounds(150,30,150,25);
        pass.setBounds(150,70,150,25);
        login.setBounds(50,120,100,30);
        register.setBounds(200,120,100,30);

        login.addActionListener(e -> {
            if(user.getText().isEmpty() || new String(pass.getPassword()).isEmpty()){
                JOptionPane.showMessageDialog(f,"Username / Password required");
                return;
            }
            try{
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "SELECT * FROM users WHERE username=? AND password=? AND user_type!='admin'"
                );
                ps.setString(1,user.getText());
                ps.setString(2,new String(pass.getPassword()));
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    f.dispose();
                    new UserDashboard(rs.getString("username")); // pass username
                } else {
                    JOptionPane.showMessageDialog(f,"Invalid login");
                }
            } catch(Exception ex){ ex.printStackTrace(); }
        });

        register.addActionListener(e -> {
            f.dispose();
            new RegisterFrame(); // user registration
        });

        f.add(lUser); f.add(lPass);
        f.add(user); f.add(pass);
        f.add(login); f.add(register);

        f.setSize(400,250);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
