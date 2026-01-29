package bai5;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

public class CustomerRegistrationValidatorTest {

    @Test
    public void validInput_shouldPass() {
        String birth = LocalDate.now().minusYears(20).toString();
        ValidationResult vr = CustomerRegistrationValidator.validate(
                "KH1234",
                "Nguyễn Văn A",
                "nguyenvana@email.com",
                "0123456789",
                "123 Đường ABC, Quận 1",
                "12345678",
                "12345678",
                birth,
                true);
        assertTrue(vr.isValid());
    }

    @Test
    public void birthdate_8Digits_shouldBeAccepted() {
        ValidationResult vr = CustomerRegistrationValidator.validate(
                "KH1234",
                "Nguyễn Văn A",
                "nguyenvana@email.com",
                "0123456789",
                "123 Đường ABC",
                "12345678",
                "12345678",
                "01012000",
                true);
        assertTrue(vr.isValid());
    }

    @Test
    public void invalidCustomerCode_format() {
        ValidationResult vr = CustomerRegistrationValidator.validate(
                "KH-1234",
                "Nguyễn Văn A",
                "nguyenvana@email.com",
                "0123456789",
                "123 Đường ABC",
                "12345678",
                "12345678",
                "",
                true);
        assertFalse(vr.isValid());
        assertNotNull(vr.getError(CustomerRegistrationValidator.FIELD_CUSTOMER_CODE));
    }

    @Test
    public void invalidFullName_tooShort() {
        ValidationResult vr = CustomerRegistrationValidator.validate(
                "KH1234",
                "A B",
                "nguyenvana@email.com",
                "0123456789",
                "123 Đường ABC",
                "12345678",
                "12345678",
                "",
                true);
        assertFalse(vr.isValid());
        assertNotNull(vr.getError(CustomerRegistrationValidator.FIELD_FULL_NAME));
    }

    @Test
    public void invalidEmail_format() {
        ValidationResult vr = CustomerRegistrationValidator.validate(
                "KH1234",
                "Nguyễn Văn A",
                "abc",
                "0123456789",
                "123 Đường ABC",
                "12345678",
                "12345678",
                "",
                true);
        assertFalse(vr.isValid());
        assertNotNull(vr.getError(CustomerRegistrationValidator.FIELD_EMAIL));
    }

    @Test
    public void invalidPhone_mustStartWith0_andLength() {
        ValidationResult vr = CustomerRegistrationValidator.validate(
                "KH1234",
                "Nguyễn Văn A",
                "nguyenvana@email.com",
                "12345",
                "123 Đường ABC",
                "12345678",
                "12345678",
                "",
                true);
        assertFalse(vr.isValid());
        assertNotNull(vr.getError(CustomerRegistrationValidator.FIELD_PHONE));
    }

    @Test
    public void invalidPassword_confirmMismatch() {
        ValidationResult vr = CustomerRegistrationValidator.validate(
                "KH1234",
                "Nguyễn Văn A",
                "nguyenvana@email.com",
                "0123456789",
                "123 Đường ABC",
                "12345678",
                "87654321",
                "",
                true);
        assertFalse(vr.isValid());
        assertNotNull(vr.getError(CustomerRegistrationValidator.FIELD_CONFIRM_PASSWORD));
    }

    @Test
    public void invalidBirthdate_under18() {
        String birth = LocalDate.now().minusYears(17).toString();
        ValidationResult vr = CustomerRegistrationValidator.validate(
                "KH1234",
                "Nguyễn Văn A",
                "nguyenvana@email.com",
                "0123456789",
                "123 Đường ABC",
                "12345678",
                "12345678",
                birth,
                true);
        assertFalse(vr.isValid());
        assertNotNull(vr.getError(CustomerRegistrationValidator.FIELD_BIRTHDATE));
    }

    @Test
    public void invalidTerms_notChecked() {
        ValidationResult vr = CustomerRegistrationValidator.validate(
                "KH1234",
                "Nguyễn Văn A",
                "nguyenvana@email.com",
                "0123456789",
                "123 Đường ABC",
                "12345678",
                "12345678",
                "",
                false);
        assertFalse(vr.isValid());
        assertNotNull(vr.getError(CustomerRegistrationValidator.FIELD_TERMS));
    }
}
