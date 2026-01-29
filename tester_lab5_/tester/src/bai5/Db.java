package bai5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Db {

    private Db() {
    }

    public static Connection openConnection() throws SQLException {
        // Requires PostgreSQL JDBC driver on classpath.
        return DriverManager.getConnection(DbConfig.jdbcUrl(), DbConfig.user(), DbConfig.password());
    }
}
