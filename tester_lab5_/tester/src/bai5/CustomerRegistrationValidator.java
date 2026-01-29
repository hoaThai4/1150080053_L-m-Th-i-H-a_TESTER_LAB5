package bai5;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class CustomerRegistrationValidator {

    public static final String FIELD_CUSTOMER_CODE = "customerCode";
    public static final String FIELD_FULL_NAME = "fullName";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_PHONE = "phone";
    public static final String FIELD_ADDRESS = "address";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_CONFIRM_PASSWORD = "confirmPassword";
    public static final String FIELD_BIRTHDATE = "birthdate";
    public static final String FIELD_TERMS = "terms";

    private static final DateTimeFormatter[] BIRTHDATE_FORMATS = new DateTimeFormatter[] {
            DateTimeFormatter.ofPattern("dd/MM/uuuu"),
            DateTimeFormatter.ofPattern("MM/dd/uuuu"),
            DateTimeFormatter.ISO_LOCAL_DATE
    };

    private CustomerRegistrationValidator() {
    }

    public static ValidationResult validate(
            String customerCode,
            String fullName,
            String email,
            String phone,
            String address,
            String password,
            String confirmPassword,
            String birthdateText,
            boolean acceptedTerms) {
        ValidationResult result = new ValidationResult();

        String code = safeTrim(customerCode);
        if (code.isEmpty()) {
            result.addError(FIELD_CUSTOMER_CODE, "Mã khách hàng là bắt buộc.");
        } else if (code.length() < 6 || code.length() > 10) {
            result.addError(FIELD_CUSTOMER_CODE, "Mã khách hàng phải từ 6 đến 10 ký tự.");
        } else if (!code.matches("^[A-Za-z0-9]+$")) {
            result.addError(FIELD_CUSTOMER_CODE, "Mã khách hàng chỉ gồm chữ cái và số.");
        }

        String name = safeTrim(fullName);
        if (name.isEmpty()) {
            result.addError(FIELD_FULL_NAME, "Họ và tên là bắt buộc.");
        } else if (name.length() < 5 || name.length() > 50) {
            result.addError(FIELD_FULL_NAME, "Họ và tên phải từ 5 đến 50 ký tự.");
        } else if (!name.matches("^[\\p{L}\\s]+$")) {
            result.addError(FIELD_FULL_NAME, "Họ và tên chỉ được chứa chữ và khoảng trắng.");
        }

        String mail = safeTrim(email);
        if (mail.isEmpty()) {
            result.addError(FIELD_EMAIL, "Email là bắt buộc.");
        } else if (mail.length() > 254) {
            result.addError(FIELD_EMAIL, "Email quá dài.");
        } else if (!mail.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            result.addError(FIELD_EMAIL, "Email không đúng định dạng (vd: nguyenvana@email.com). ");
        }

        String phoneValue = safeTrim(phone);
        if (phoneValue.isEmpty()) {
            result.addError(FIELD_PHONE, "Số điện thoại là bắt buộc.");
        } else if (!phoneValue.matches("^0\\d{9,11}$")) {
            result.addError(FIELD_PHONE, "Số điện thoại phải bắt đầu bằng 0 và gồm 10-12 chữ số.");
        }

        String addr = safeTrim(address);
        if (addr.isEmpty()) {
            result.addError(FIELD_ADDRESS, "Địa chỉ là bắt buộc.");
        } else if (addr.length() > 255) {
            result.addError(FIELD_ADDRESS, "Địa chỉ tối đa 255 ký tự.");
        }

        String pw = password == null ? "" : password;
        if (pw.trim().isEmpty()) {
            result.addError(FIELD_PASSWORD, "Mật khẩu là bắt buộc.");
        } else if (pw.length() < 8) {
            result.addError(FIELD_PASSWORD, "Mật khẩu tối thiểu 8 ký tự.");
        }

        String pw2 = confirmPassword == null ? "" : confirmPassword;
        if (pw2.trim().isEmpty()) {
            result.addError(FIELD_CONFIRM_PASSWORD, "Xác nhận mật khẩu là bắt buộc.");
        } else if (!pw2.equals(pw)) {
            result.addError(FIELD_CONFIRM_PASSWORD, "Xác nhận mật khẩu không khớp.");
        }

        String birthText = normalizeBirthdateText(birthdateText);
        if (!birthText.isEmpty()) {
            if (countDigits(birthText) != 8) {
                result.addError(FIELD_BIRTHDATE, "Ngày sinh chưa đầy đủ. Nhập theo dd/MM/yyyy.");
            } else {
                LocalDate birthDate = parseBirthdate(birthText);
                if (birthDate == null) {
                    result.addError(FIELD_BIRTHDATE,
                            "Ngày sinh không hợp lệ. Dùng dd/MM/yyyy hoặc MM/dd/yyyy hoặc yyyy-MM-dd.");
                } else {
                    LocalDate now = LocalDate.now();
                    if (birthDate.isAfter(now)) {
                        result.addError(FIELD_BIRTHDATE, "Ngày sinh không được lớn hơn ngày hiện tại.");
                    } else {
                        int years = Period.between(birthDate, now).getYears();
                        if (years < 18) {
                            result.addError(FIELD_BIRTHDATE, "Người dùng phải đủ 18 tuổi.");
                        }
                    }
                }
            }
        }

        if (!acceptedTerms) {
            result.addError(FIELD_TERMS, "Bạn phải đồng ý với các điều khoản dịch vụ.");
        }

        return result;
    }

    public static LocalDate parseBirthdate(String input) {
        String raw = normalizeBirthdateText(input);
        if (raw.isEmpty()) {
            return null;
        }

        // Support ddMMyyyy (8 digits) as a convenience
        if (raw.matches("^\\d{8}$")) {
            raw = raw.substring(0, 2) + "/" + raw.substring(2, 4) + "/" + raw.substring(4, 8);
        }

        for (DateTimeFormatter fmt : BIRTHDATE_FORMATS) {
            try {
                return LocalDate.parse(raw, fmt);
            } catch (DateTimeParseException ignored) {
            }
        }
        return null;
    }

    private static String normalizeBirthdateText(String input) {
        String raw = safeTrim(input);
        if (raw.isEmpty()) {
            return "";
        }

        // Masked field may contain spaces and separators even when user didn't enter
        // anything.
        String digitsOnly = raw.replaceAll("[^0-9]", "");
        if (digitsOnly.isEmpty()) {
            return "";
        }

        // If user typed 8 digits (e.g. 10022004), keep as-is for parseBirthdate()
        // handling.
        if (digitsOnly.length() == 8 && raw.matches("^\\d{8}$")) {
            return raw;
        }

        // Otherwise keep original text; parsing formats will decide.
        return raw;
    }

    private static int countDigits(String input) {
        if (input == null) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c >= '0' && c <= '9') {
                count++;
            }
        }
        return count;
    }

    private static String safeTrim(String s) {
        return s == null ? "" : s.trim();
    }
}
