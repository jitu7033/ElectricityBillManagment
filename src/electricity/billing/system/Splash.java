
package src.electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Splash extends JFrame {
    private JProgressBar progressBar;
    private JLabel textLabel;

    Splash() {
        setLayout(new BorderLayout());
        setSize(600, 400);
        setLocation(300, 100);
        setUndecorated(true);  // Remove window border
        getContentPane().setBackground(new Color(0xFF5733));  // Splash background color (orange-red)

        // Create a label for the splash screen text
        textLabel = new JLabel("Loading... Please wait.", JLabel.CENTER);
        textLabel.setFont(new Font("Arial", Font.BOLD, 20));
        textLabel.setForeground(Color.WHITE);
        add(textLabel, BorderLayout.CENTER);

        // Create a progress bar at the bottom
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setForeground(Color.GREEN);
        progressBar.setBackground(Color.WHITE);
        add(progressBar, BorderLayout.SOUTH);

        setVisible(true);

        // Create a timer to simulate the loading process
        Timer loadingTimer = new Timer(50, new ActionListener() {
            int progress = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                progress += 1;
                progressBar.setValue(progress);

                // Change the label text when the progress reaches 60%
                if (progress >= 60 && progress < 80) {
                    textLabel.setText("Almost done...");
                }

                // Once progress reaches 100%, hide the splash screen and show the login screen
                if (progress >= 100) {
                    ((Timer) e.getSource()).stop();  // Stop the timer
                    setVisible(false);  // Hide splash screen
                    new Login();  // Show the login screen
                }
            }
        });
        loadingTimer.start();
    }

    public static void main(String[] args) {
        new Splash();
    }
}
