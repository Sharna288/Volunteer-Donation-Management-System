package ui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddEditUserForm {

    private JFrame f;
    private JTextField usernameField, emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private AdminDashboard dashboard;
    private Integer userId; // null if adding new user

    public AddEditUserForm(AdminDashboard dashboard, Integer userId) {
        this.dashboard = dashboard;
        this.userId = userId;

        f = new JFrame(userId == null ? "Add New User" : "Edit User");
        f.setSize(400, 300);
        f.setLayout(null);
        f.setLocationRelativeTo(null);

        JLabel lUsername = new JLabel("Username:");
        JLabel lEmail = new JLabel("Email:");
        JLabel lPassword = new JLabel("Password:");
        JLabel lRole = new JLabel("Role:");

        usernameField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();
        roleBox = new JComboBox<>(new String[]{"donor", "volunteer", "admin"});

        lUsername.setBounds(30, 30, 100, 25);
        usernameField.setBounds(140, 30, 200, 25);

        lEmail.setBounds(30, 70, 100, 25);
        emailField.setBounds(140, 70, 200, 25);

        lPassword.setBounds(30, 110, 100, 25);
        passwordField.setBounds(140, 110, 200, 25);

        lRole.setBounds(30, 150, 100, 25);
        roleBox.setBounds(140, 150, 200, 25);

        JButton saveBtn = new JButton(userId == null ? "Add User" : "Save Changes");
        saveBtn.setBounds(120, 200, 150, 35);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Arial", Font.BOLD, 14));

        if(userId == null) {
            saveBtn.setBackground(Color.GREEN); // Add new user
        } else {
            saveBtn.setBackground(Color.BLUE); // Edit user
        }

        f.add(lUsername); f.add(usernameField);
        f.add(lEmail); f.add(emailField);
        f.add(lPassword); f.add(passwordField);
        f.add(lRole); f.add(roleBox);
        f.add(saveBtn);

        // Load data if editing
        if(userId != null) loadUserData();

        saveBtn.addActionListener(e -> saveUser());

        f.setVisible(true);
    }

    private void loadUserData() {
        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE user_id=?")) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                usernameField.setText(rs.getString("username"));
                emailField.setText(rs.getString("email"));
                passwordField.setText(rs.getString("password"));
                roleBox.setSelectedItem(rs.getString("user_type"));
            }
        } catch(Exception e) { e.printStackTrace(); }
    }

    private void saveUser() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String role = roleBox.getSelectedItem().toString();

        if(username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(f, "All fields are required");
            return;
        }

        try(Connection con = DBConnection.getConnection()) {
            if(userId == null) {
                // Add new user
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO users(username,password,email,user_type) VALUES(?,?,?,?)");
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, email);
                ps.setString(4, role);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(f, "User added successfully");
            } else {
                // Update existing user
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE users SET username=?, password=?, email=?, user_type=? WHERE user_id=?");
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, email);
                ps.setString(4, role);
                ps.setInt(5, userId);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(f, "User updated successfully");
            }
            f.dispose();
            dashboard.refreshUsers(); // Refresh table in dashboard
        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(f, "Error saving user");
        }
    }
}
