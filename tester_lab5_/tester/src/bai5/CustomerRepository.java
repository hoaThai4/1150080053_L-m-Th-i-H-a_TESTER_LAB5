package bai5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;

public class CustomerRepository {

    public boolean existsByCustomerCode(Connection connection, String customerCode) throws SQLException {
        String sql = "select 1 from customers where customer_code = ? limit 1";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, customerCode);
        ResultSet rs = ps.executeQuery();
        boolean exists = rs.next();
        rs.close();
        ps.close();
        return exists;
    }

    public boolean existsByEmail(Connection connection, String email) throws SQLException {
        String sql = "select 1 from customers where email = ? limit 1";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        boolean exists = rs.next();
        rs.close();
        ps.close();
        return exists;
    }

    public void insert(
            Connection connection,
            String customerCode,
            String fullName,
            String email,
            String phone,
            String address,
            String passwordHash,
            LocalDate birthdate,
            GenderOption gender,
            boolean acceptedTerms) throws SQLException {
        String sql = "insert into customers (customer_code, full_name, email, phone, address, password_hash, birthdate, gender, accepted_terms) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, customerCode);
        ps.setString(2, fullName);
        ps.setString(3, email);
        ps.setString(4, phone);
        ps.setString(5, address);
        ps.setString(6, passwordHash);
        if (birthdate == null) {
            ps.setDate(7, null);
        } else {
            ps.setDate(7, Date.valueOf(birthdate));
        }
        ps.setString(8, gender == null ? null : gender.name());
        ps.setBoolean(9, acceptedTerms);
        ps.executeUpdate();
        ps.close();
    }

    public static boolean isUniqueViolation(SQLException ex) {
        // PostgreSQL unique_violation = 23505
        return ex != null && "23505".equals(ex.getSQLState());
    }
}
