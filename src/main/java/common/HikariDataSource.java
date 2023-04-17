package common;

import com.zaxxer.hikari.HikariConfig;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariDataSource {

    private static final HikariConfig config = new HikariConfig();
    private static final com.zaxxer.hikari.HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:mysql://localhost:3306/lazy");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setUsername("root");
        config.setPassword(""); // 更改為你自己的密碼
        config.addDataSourceProperty("maximumPoolSize", "20");
        config.addDataSourceProperty("minimumIdle", 10);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new com.zaxxer.hikari.HikariDataSource(config);
    }

    private HikariDataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
