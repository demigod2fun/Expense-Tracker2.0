
import javax.swing.*;

/**
 * Main entry point for the Expense Tracker application
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Expense Tracker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            JLabel label = new JLabel("Expense Tracker Application Started!");
            label.setHorizontalAlignment(JLabel.CENTER);
            frame.add(label);
            
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}