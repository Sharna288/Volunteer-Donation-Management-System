package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminDashboard {

    public AdminDashboard() {
        JFrame f = new JFrame("Admin Dashboard");

        // Tabs
        JTabbedPane tabs = new JTabbedPane();

        // USERS TABLE
        JTable usersTable = new JTable(new DefaultTableModel(
                new String[]{"ID", "Username", "Email", "Role"}, 0
        ));

        // DONATIONS TABLE
        JTable donationsTable = new JTable(new DefaultTableModel(
                new String[]{"ID", "Type", "Amount", "Blood Group"}, 0
        ));

        tabs.add("Users", new JScrollPane(usersTable));
        tabs.add("Donations", new JScrollPane(donationsTable));

        f.add(tabs, BorderLayout.CENTER);

        f.setSize(800, 500);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
