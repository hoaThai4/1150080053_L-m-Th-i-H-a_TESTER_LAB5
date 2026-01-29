package bai4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class PaymentForm extends JFrame {

    // Use JCheckBox to match the mock UI, but still enforce single selection via
    // ButtonGroup.
    private final JCheckBox male = new JCheckBox("Male");
    private final JCheckBox female = new JCheckBox("Female");
    private final JCheckBox child = new JCheckBox("Child (0 - 17 years)");

    private final JTextField ageField = new JTextField(10);
    private final JTextField paymentField = new JTextField(10);

    public PaymentForm() {
        super("Calculate the Payment for the Patient");

        paymentField.setEditable(false);
        paymentField.setBackground(new Color(245, 245, 245));

        ((AbstractDocument) ageField.getDocument()).setDocumentFilter(new DigitsOnlyFilter(3));

        ButtonGroup group = new ButtonGroup();
        group.add(male);
        group.add(female);
        group.add(child);

        JLabel title = new JLabel("Calculate the Payment for the Patient", JLabel.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        title.setBorder(new EmptyBorder(12, 12, 8, 12));

        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBorder(new EmptyBorder(12, 12, 12, 12));
        content.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        groupPanel.add(male);
        groupPanel.add(female);
        groupPanel.add(child);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        form.add(groupPanel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        form.add(new JLabel("Age (Years)"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        form.add(ageField, gbc);

        JButton calcBtn = new JButton("Calculate");
        calcBtn.addActionListener(e -> onCalculate());
        gbc.gridy = 2;
        gbc.gridx = 1;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        form.add(calcBtn, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        form.add(new JLabel("Payment is"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        form.add(paymentField, gbc);

        gbc.gridy = 4;
        gbc.gridx = 1;
        gbc.weightx = 0.0;
        form.add(new JLabel("euro €"), gbc);

        content.add(form, BorderLayout.CENTER);
        setContentPane(content);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void onCalculate() {
        try {
            String rawAge = ageField.getText().trim();
            if (rawAge.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Age is required", "Input error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int age = Integer.parseInt(rawAge);
            GenderGroup selected = getSelectedGroup();
            int payment = PaymentCalculator.calculate(age, selected);
            paymentField.setText(String.valueOf(payment));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Age must be an integer", "Input error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private GenderGroup getSelectedGroup() {
        if (male.isSelected())
            return GenderGroup.MALE;
        if (female.isSelected())
            return GenderGroup.FEMALE;
        if (child.isSelected())
            return GenderGroup.CHILD;
        return null;
    }

    private static final class DigitsOnlyFilter extends DocumentFilter {
        private final int maxLen;

        private DigitsOnlyFilter(int maxLen) {
            this.maxLen = maxLen;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            replace(fb, offset, 0, string, attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (text == null) {
                return;
            }

            String digits = text.replaceAll("[^0-9]", "");
            String current = fb.getDocument().getText(0, fb.getDocument().getLength());
            String next = current.substring(0, offset) + digits + current.substring(offset + length);

            if (maxLen > 0 && next.length() > maxLen) {
                return;
            }

            fb.replace(offset, length, digits, attrs);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaymentForm().setVisible(true));
    }
}
