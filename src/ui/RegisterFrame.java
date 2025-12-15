package ui;

import db.DBConnection;
import javax.swing.*;
import java.sql.*;

public class RegisterFrame {

    public RegisterFrame() {
        JFrame f = new JFrame("User Registration");

        JTextField user = new JTextField();
        JTextField email = new JTextField();
        JPasswordField pass = new JPasswordField();

        JComboBox<String> role = new JComboBox<>(
            new String[]{"donor","volunteer"}
        );

        JButton save = new JButton("Register");

        user.setBounds(120,30,150,25);
        email.setBounds(120,65,150,25);
        pass.setBounds(120,100,150,25);
        role.setBounds(120,135,150,25);
        save.setBounds(140,175,100,30);

        f.add(new JLabel("Username")).setBounds(30,30,80,25);
        f.add(new JLabel("Email")).setBounds(30,65,80,25);
        f.add(new JLabel("Password")).setBounds(30,100,80,25);
        f.add(new JLabel("Role")).setBounds(30,135,80,25);

        save.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO users(username,password,email,user_type) VALUES(?,?,?,?)"
                );
                ps.setString(1, user.getText());
                ps.setString(2, new String(pass.getPassword()));
                ps.setString(3, email.getText());
                ps.setString(4, role.getSelectedItem().toString());

                ps.executeUpdate();
                JOptionPane.showMessageDialog(f,"Registration Successful");
                f.dispose();
                new LoginFrame();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        f.add(user); f.add(email); f.add(pass); f.add(role); f.add(save);

        f.setSize(350,260);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
