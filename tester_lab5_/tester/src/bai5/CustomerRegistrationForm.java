package bai5;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.text.MaskFormatter;

public class CustomerRegistrationForm extends JFrame {

    private final JTextField customerCodeField = new PlaceholderTextField("6-10 ký tự, chỉ chữ và số");
    private final JTextField fullNameField = new PlaceholderTextField("Nhập họ tên đầy đủ");
    private final JTextField emailField = new PlaceholderTextField("Ví dụ: nguyenvana@email.com");
    private final JTextField phoneField = new PlaceholderTextField("Bắt đầu bằng số 0, 10-12 số");
    private final JTextArea addressArea = new PlaceholderTextArea(3, 20, "Nhập địa chỉ chi tiết");
    private final JPasswordField passwordField = new PlaceholderPasswordField("Ít nhất 8 ký tự");
    private final JPasswordField confirmPasswordField = new PlaceholderPasswordField("Nhập lại mật khẩu");
    private final JFormattedTextField birthdateField = createBirthdateField();
    private final JButton birthdatePickBtn = new JButton("Chọn...");

    private final JRadioButton maleRadio = new JRadioButton("Nam");
    private final JRadioButton femaleRadio = new JRadioButton("Nữ");
    private final JRadioButton otherRadio = new JRadioButton("Khác");
    private final ButtonGroup genderGroup = new ButtonGroup();

    private final JCheckBox termsCheck = new JCheckBox("Tôi đồng ý với các điều khoản dịch vụ");

    private final JLabel codeError = errorLabel();
    private final JLabel nameError = errorLabel();
    private final JLabel emailError = errorLabel();
    private final JLabel phoneError = errorLabel();
    private final JLabel addressError = errorLabel();
    private final JLabel passwordError = errorLabel();
    private final JLabel confirmError = errorLabel();
    private final JLabel birthdateError = errorLabel();
    private final JLabel termsError = errorLabel();

    private final JLabel statusLabel = new JLabel(" ");

    private final CustomerRepository repository = new CustomerRepository();

    public CustomerRegistrationForm() {
        super("Đăng ký tài khoản khách hàng");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(620, 560));
        setLocationRelativeTo(null);

        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        genderGroup.add(otherRadio);

        birthdateField.setToolTipText("dd/MM/yyyy (gõ số tự chèn /). Có thể để trống.");
        birthdatePickBtn.setToolTipText("Chọn ngày sinh");

        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel title = new JLabel("ĐĂNG KÝ TÀI KHOẢN KHÁCH HÀNG");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        content.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 2, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int row = 0;

        row = addRow(form, gbc, row, "Mã Khách Hàng *", customerCodeField, codeError);
        row = addRow(form, gbc, row, "Họ và Tên *", fullNameField, nameError);
        row = addRow(form, gbc, row, "Email *", emailField, emailError);
        row = addRow(form, gbc, row, "Số điện thoại *", phoneField, phoneError);

        JScrollPane addressScroll = new JScrollPane(addressArea);
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);
        row = addRow(form, gbc, row, "Địa chỉ *", addressScroll, addressError);

        row = addRow(form, gbc, row, "Mật khẩu *", passwordField, passwordError);
        row = addRow(form, gbc, row, "Xác nhận Mật khẩu *", confirmPasswordField, confirmError);

        JPanel birthPanel = new JPanel(new BorderLayout(8, 0));
        birthPanel.add(birthdateField, BorderLayout.CENTER);
        birthPanel.add(birthdatePickBtn, BorderLayout.EAST);
        row = addRow(form, gbc, row, "Ngày sinh", birthPanel, birthdateError);

        JPanel genderPanel = new JPanel();
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        genderPanel.add(otherRadio);
        row = addRow(form, gbc, row, "Giới tính", genderPanel, errorLabel());

        row = addRow(form, gbc, row, "Điều khoản *", termsCheck, termsError);

        content.add(form, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel buttons = new JPanel();
        JButton registerBtn = new JButton("Đăng ký");
        JButton resetBtn = new JButton("Nhập lại");
        buttons.add(registerBtn);
        buttons.add(resetBtn);

        bottom.add(buttons, BorderLayout.NORTH);
        bottom.add(statusLabel, BorderLayout.SOUTH);

        content.add(bottom, BorderLayout.SOUTH);
        setContentPane(content);

        registerBtn.addActionListener(e -> onRegister());
        resetBtn.addActionListener(e -> onReset());
        birthdatePickBtn.addActionListener(e -> onPickBirthdate());

        clearErrors();
    }

    private int addRow(JPanel form, GridBagConstraints gbc, int row, String label, java.awt.Component field,
            JLabel error) {
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.weightx = 0.35;
        form.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65;
        form.add(field, gbc);

        row++;
        gbc.gridy = row;
        gbc.gridx = 1;
        gbc.weightx = 0.65;
        form.add(error, gbc);

        return row + 1;
    }

    private void onReset() {
        customerCodeField.setText("");
        fullNameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressArea.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        birthdateField.setText("");
        genderGroup.clearSelection();
        termsCheck.setSelected(false);
        statusLabel.setText(" ");
        clearErrors();
    }

    private void onRegister() {
        clearErrors();
        statusLabel.setForeground(Color.BLACK);
        statusLabel.setText(" ");

        String code = customerCodeField.getText();
        String name = fullNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressArea.getText();
        String password = new String(passwordField.getPassword());
        String confirm = new String(confirmPasswordField.getPassword());
        String birthText = birthdateField.getText();
        boolean accepted = termsCheck.isSelected();

        ValidationResult vr = CustomerRegistrationValidator.validate(
                code,
                name,
                email,
                phone,
                address,
                password,
                confirm,
                birthText,
                accepted);

        if (!vr.isValid()) {
            renderValidationErrors(vr);
            statusLabel.setForeground(Color.RED);
            statusLabel.setText("Vui lòng kiểm tra lại dữ liệu.");
            return;
        }

        LocalDate birthDate = CustomerRegistrationValidator.parseBirthdate(birthText);
        GenderOption gender = selectedGender();

        try {
            Connection conn = Db.openConnection();
            try {
                String trimmedCode = code.trim();
                String trimmedEmail = email.trim();

                if (repository.existsByCustomerCode(conn, trimmedCode)) {
                    codeError.setText("Mã khách hàng đã tồn tại.");
                    statusLabel.setForeground(Color.RED);
                    statusLabel.setText("Vui lòng kiểm tra lại dữ liệu.");
                    return;
                }

                if (repository.existsByEmail(conn, trimmedEmail)) {
                    emailError.setText("Email đã tồn tại.");
                    statusLabel.setForeground(Color.RED);
                    statusLabel.setText("Vui lòng kiểm tra lại dữ liệu.");
                    return;
                }

                String passwordHash = PasswordHasher.hash(password);
                repository.insert(
                        conn,
                        trimmedCode,
                        name.trim(),
                        trimmedEmail,
                        phone.trim(),
                        address.trim(),
                        passwordHash,
                        birthDate,
                        gender,
                        accepted);
            } finally {
                conn.close();
            }

            statusLabel.setForeground(new Color(0, 128, 0));
            statusLabel.setText("Đăng ký tài khoản thành công!");
        } catch (SQLException ex) {
            statusLabel.setForeground(Color.RED);
            if (CustomerRepository.isUniqueViolation(ex)) {
                statusLabel.setText("Dữ liệu bị trùng (mã khách hàng hoặc email). Vui lòng kiểm tra lại.");
            } else {
                statusLabel.setText("Không thể kết nối/ghi dữ liệu CSDL: " + ex.getMessage());
            }
        } catch (Exception ex) {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText("Có lỗi xảy ra: " + ex.getMessage());
        }
    }

    private GenderOption selectedGender() {
        if (maleRadio.isSelected()) {
            return GenderOption.MALE;
        }
        if (femaleRadio.isSelected()) {
            return GenderOption.FEMALE;
        }
        if (otherRadio.isSelected()) {
            return GenderOption.OTHER;
        }
        return null;
    }

    private void renderValidationErrors(ValidationResult vr) {
        setIfPresent(codeError, vr.getError(CustomerRegistrationValidator.FIELD_CUSTOMER_CODE));
        setIfPresent(nameError, vr.getError(CustomerRegistrationValidator.FIELD_FULL_NAME));
        setIfPresent(emailError, vr.getError(CustomerRegistrationValidator.FIELD_EMAIL));
        setIfPresent(phoneError, vr.getError(CustomerRegistrationValidator.FIELD_PHONE));
        setIfPresent(addressError, vr.getError(CustomerRegistrationValidator.FIELD_ADDRESS));
        setIfPresent(passwordError, vr.getError(CustomerRegistrationValidator.FIELD_PASSWORD));
        setIfPresent(confirmError, vr.getError(CustomerRegistrationValidator.FIELD_CONFIRM_PASSWORD));
        setIfPresent(birthdateError, vr.getError(CustomerRegistrationValidator.FIELD_BIRTHDATE));
        setIfPresent(termsError, vr.getError(CustomerRegistrationValidator.FIELD_TERMS));
    }

    private void setIfPresent(JLabel label, String text) {
        if (text != null) {
            label.setText(text);
        }
    }

    private void clearErrors() {
        codeError.setText(" ");
        nameError.setText(" ");
        emailError.setText(" ");
        phoneError.setText(" ");
        addressError.setText(" ");
        passwordError.setText(" ");
        confirmError.setText(" ");
        birthdateError.setText(" ");
        termsError.setText(" ");
    }

    private static JLabel errorLabel() {
        JLabel lbl = new JLabel(" ");
        lbl.setForeground(Color.RED);
        return lbl;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomerRegistrationForm().setVisible(true));
    }

    private static JFormattedTextField createBirthdateField() {
        try {
            MaskFormatter fmt = new MaskFormatter("##/##/####");
            fmt.setPlaceholderCharacter(' ');
            JFormattedTextField field = new PlaceholderFormattedTextField(fmt, "dd/MM/yyyy");
            field.setColumns(10);
            return field;
        } catch (Exception ex) {
            return new JFormattedTextField();
        }
    }

    private void onPickBirthdate() {
        LocalDate current = CustomerRegistrationValidator.parseBirthdate(birthdateField.getText());
        if (current == null) {
            current = LocalDate.now().minusYears(18);
        }

        Date initial = Date.from(current.atStartOfDay(ZoneId.systemDefault()).toInstant());
        SpinnerDateModel model = new SpinnerDateModel(initial, null, null, java.util.Calendar.DAY_OF_MONTH);
        JSpinner spinner = new JSpinner(model);
        spinner.setEditor(new JSpinner.DateEditor(spinner, "dd/MM/yyyy"));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(spinner);

        int choice = JOptionPane.showConfirmDialog(this, panel, "Chọn ngày sinh", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (choice == JOptionPane.OK_OPTION) {
            Date selected = (Date) spinner.getValue();
            LocalDate date = selected.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            birthdateField.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
    }
}
