package ui;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class UserDonationsFrame {

    private JFrame f;
    private JTable table;
    private DefaultTableModel model;
    private String username;

    public UserDonationsFrame(String username){
        this.username = username;

        f = new JFrame(username + "'s Donations");

        model = new DefaultTableModel(new String[]{"ID","Type","Amount","Blood Group"},0);
        table = new JTable(model);

        loadDonations(); // load from database

        f.add(new JScrollPane(table));
        f.setSize(600,300);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    // Method to load or refresh donations
    public void loadDonations() {
        model.setRowCount(0); // clear table
        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM donations WHERE donor_username=?"
            )) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("donation_id"),
                        rs.getString("donation_type"),
                        rs.getString("amount"),
                        rs.getString("blood_group")
                });
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
