package ui;

import db.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class UserDashboard {

    private JTable donationTable;
    private DefaultTableModel donationModel;
    private int userId;

    public UserDashboard(int userId) {
        this.userId = userId;

        JFrame f = new JFrame("My Donations");
        f.setSize(500,300);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        donationModel = new DefaultTableModel(new String[]{"Donation ID","Type","Amount","Blood Group","Date"},0);
        donationTable = new JTable(donationModel);
        donationTable.setBounds(20,20,450,200);

        loadUserDonations();

        f.add(new JScrollPane(donationTable));
        f.setVisible(true);
    }

    public UserDashboard(String text) {
		// TODO Auto-generated constructor stub
	}

	private void loadUserDonations() {
        donationModel.setRowCount(0);
        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM donations WHERE donor_id=?"
            )) {
            ps.setInt(1,userId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                donationModel.addRow(new Object[]{
                        rs.getInt("donation_id"),
                        rs.getString("donation_type"),
                        rs.getString("amount"),
                        rs.getString("blood_group"),
                        rs.getDate("donation_date")
                });
            }

        } catch(Exception e) { e.printStackTrace(); }
    }
}
