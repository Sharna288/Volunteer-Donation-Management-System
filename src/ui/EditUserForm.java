package ui;

import db.DBConnection;
import javax.swing.*;
import java.sql.*;

public class EditUserForm {
    public EditUserForm(int userId){
        JFrame f = new JFrame("Edit User");
        JTextField username = new JTextField();
        JTextField email = new JTextField();
        JComboBox<String> role = new JComboBox<>(new String[]{"admin","donor","volunteer"});
        JButton save = new JButton("Save");

        username.setBounds(120,30,150,25);
        email.setBounds(120,65,150,25);
        role.setBounds(120,100,150,25);
        save.setBounds(140,140,100,30);

        f.add(new JLabel("Username")).setBounds(30,30,80,25);
        f.add(new JLabel("Email")).setBounds(30,65,80,25);
        f.add(new JLabel("Role")).setBounds(30,100,80,25);

        // Load user data
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE user_id=?");
            ps.setInt(1,userId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                username.setText(rs.getString("username"));
                email.setText(rs.getString("email"));
                role.setSelectedItem(rs.getString("user_type"));
            }
        } catch(Exception e){ e.printStackTrace(); }

        save.addActionListener(e -> {
            try{
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE users SET username=?, email=?, user_type=? WHERE user_id=?"
                );
                ps.setString(1, username.getText());
                ps.setString(2, email.getText());
                ps.setString(3, role.getSelectedItem().toString());
                ps.setInt(4,userId);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(f,"User Updated");
                f.dispose();
            } catch(Exception ex){ ex.printStackTrace(); }
        });

        f.add(username); f.add(email); f.add(role); f.add(save);
        f.setSize(350,230);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
