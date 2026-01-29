package bai5;

public final class DbConfig {

    private DbConfig() {
    }

    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 5432;
    public static final String DEFAULT_DATABASE = "lab5";
    public static final String DEFAULT_USER = "postgres";
    public static final String DEFAULT_PASSWORD = "12345678";

    public static String host() {
        return System.getProperty("db.host", DEFAULT_HOST);
    }

    public static int port() {
        String raw = System.getProperty("db.port");
        if (raw == null || raw.trim().isEmpty()) {
            return DEFAULT_PORT;
        }
        try {
            return Integer.parseInt(raw.trim());
        } catch (NumberFormatException ex) {
            return DEFAULT_PORT;
        }
    }

    public static String database() {
        return System.getProperty("db.name", DEFAULT_DATABASE);
    }

    public static String user() {
        return System.getProperty("db.user", DEFAULT_USER);
    }

    public static String password() {
        return System.getProperty("db.pass", DEFAULT_PASSWORD);
    }

    public static String jdbcUrl() {
        return "jdbc:postgresql://" + host() + ":" + port() + "/" + database();
    }
}
