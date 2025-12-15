package ui;

import db.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminDashboard {

    private JFrame f;
    private JTable usersTable, donationsTable;
    private DefaultTableModel usersModel, donationsModel;

    public AdminDashboard() {
        f = new JFrame("Admin Dashboard");
        f.setSize(700, 500);
        f.setLayout(new BorderLayout());
        f.setLocationRelativeTo(null); 
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Tabs
        JTabbedPane tabs = new JTabbedPane();

        // --- Users Table ---
        usersModel = new DefaultTableModel(
                new String[]{"ID","Username","Email","Role","Donations"},0); // Added Donations column
        usersTable = new JTable(usersModel);
        loadUsers();

        // Buttons Panel
        JPanel userButtonPanel = new JPanel();
        userButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));

        JButton addUserBtn = new JButton("Add User");
        addUserBtn.setBackground(new Color(0, 153, 0)); // green
        addUserBtn.setForeground(Color.WHITE);
        addUserBtn.setFont(new Font("Arial", Font.BOLD, 12));

        JButton editUserBtn = new JButton("Edit Selected");
        editUserBtn.setBackground(new Color(0, 102, 204)); // blue
        editUserBtn.setForeground(Color.WHITE);
        editUserBtn.setFont(new Font("Arial", Font.BOLD, 12));

        JButton deleteUserBtn = new JButton("Delete Selected");
        deleteUserBtn.setBackground(new Color(204, 0, 0)); // red
        deleteUserBtn.setForeground(Color.WHITE);
        deleteUserBtn.setFont(new Font("Arial", Font.BOLD, 12));

        // Button actions
        addUserBtn.addActionListener(e -> new AddEditUserForm(this, null)); // Add new user
        editUserBtn.addActionListener(e -> editUser());
        deleteUserBtn.addActionListener(e -> deleteUser());

        userButtonPanel.add(addUserBtn);
        userButtonPanel.add(editUserBtn);
        userButtonPanel.add(deleteUserBtn);

        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.add(new JScrollPane(usersTable), BorderLayout.CENTER);
        userPanel.add(userButtonPanel, BorderLayout.SOUTH);

        // --- Donations Table ---
        donationsModel = new DefaultTableModel(new String[]{"ID","Type","Amount","Blood Group"},0);
        donationsTable = new JTable(donationsModel);
        loadDonations();

        JPanel donationPanel = new JPanel(new BorderLayout());
        donationPanel.add(new JScrollPane(donationsTable), BorderLayout.CENTER);

        // Add tabs
        tabs.add("Users", userPanel);
        tabs.add("Donations", donationPanel);

        f.add(tabs, BorderLayout.CENTER);
        f.setVisible(true);
    }

    // --- Load Users with donation count ---
    public void loadUsers() {
        usersModel.setRowCount(0);
        String query = "SELECT u.user_id, u.username, u.email, u.user_type, " +
                       "(SELECT COUNT(*) FROM donations d WHERE d.donor_username = u.username) AS donation_count " +
                       "FROM users u";

        try(Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query)) {

            while(rs.next()) {
                usersModel.addRow(new Object[]{
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("user_type"),
                        rs.getInt("donation_count") // donation count
                });
            }

        } catch(Exception e) { e.printStackTrace(); }
    }

    private void loadDonations() {
        donationsModel.setRowCount(0);
        try(Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM donations")) {

            while(rs.next()) {
                donationsModel.addRow(new Object[]{
                        rs.getInt("donation_id"),
                        rs.getString("donation_type"),
                        rs.getString("amount"),
                        rs.getString("blood_group")
                });
            }

        } catch(Exception e) { e.printStackTrace(); }
    }

    private void editUser() {
        int row = usersTable.getSelectedRow();
        if(row == -1) {
            JOptionPane.showMessageDialog(f,"Select a user to edit");
            return;
        }
        int userId = (int) usersModel.getValueAt(row, 0);
        new AddEditUserForm(this, userId);
    }

    private void deleteUser() {
        int row = usersTable.getSelectedRow();
        if(row == -1) {
            JOptionPane.showMessageDialog(f,"Select a user to delete");
            return;
        }

        int userId = (int) usersModel.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(f,
                "Are you sure to delete this user?", "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if(confirm == JOptionPane.YES_OPTION) {
            try(Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM users WHERE user_id=?")) {
                ps.setInt(1, userId);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(f,"User deleted successfully");
                loadUsers();
            } catch(Exception e) { e.printStackTrace(); }
        }
    }

    public void refreshUsers() {
        loadUsers(); // called from AddEditUserForm after add/edit
    }
}
