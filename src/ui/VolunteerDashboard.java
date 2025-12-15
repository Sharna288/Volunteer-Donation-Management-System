package ui;

import javax.swing.*;

public class VolunteerDashboard {

    public VolunteerDashboard() {
        JFrame f = new JFrame("Volunteer Dashboard");

        JLabel l = new JLabel("Welcome Volunteer");
        l.setBounds(90,60,200,30);

        f.add(l);

        f.setSize(350,200);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
