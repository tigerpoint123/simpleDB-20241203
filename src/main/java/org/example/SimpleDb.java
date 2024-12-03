package org.example;

import java.sql.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.DriverManager;
import java.sql.SQLException;

@RequiredArgsConstructor
public class SimpleDb {
    private static final Logger log = LoggerFactory.getLogger(SimpleDb.class);

    private final String host;
    private final String username;
    private final String password;
    private final String dbName;

    public void run(String sql) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
            log.info("SQL 실행 성공: {}", sql);
        } catch (SQLException e) {
            log.error("SQL 실행 실패: {}", sql, e);
            // 구체적인 예외 처리 (예: SQLSyntaxErrorException)
        }
    }

    private Connection getConnection() throws SQLException {
        String url = String.format("jdbc:mysql://%s/%s", host, dbName);
        return DriverManager.getConnection(url, username, password);
    }
}
