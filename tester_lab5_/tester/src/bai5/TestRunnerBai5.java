package bai5;

import javax.swing.SwingUtilities;

public class TestRunnerBai5 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomerRegistrationForm().setVisible(true));
    }
}
