package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Sql {
    private StringBuilder sql = new StringBuilder();
    private Connection connection; // simpleDb에서 가져온 연결 객체
    private Map<String, Object> parameters = new LinkedHashMap<>();

    // 생성자에서 connection 설정
    public Sql(Connection connection) {
        this.connection = connection;
    }

    // SQL 문에 문자열 추가
    public Sql append(String str) {
        sql.append(str);
        return this;
    }

    // SQL 문에 ? placeholder와 값 추가
    public Sql append(String placeholder, Object value) {
        sql.append(placeholder);
        // value는 현재 저장만 하고, 실제 사용은 PreparedStatement에서 이루어집니다.
        // 나중에 PreparedStatement 생성 시 value를 이용하여 바인딩할 것입니다.
        parameters.put(placeholder, value);
        return this;
    }


    // SQL 문 실행 및 생성된 ID 반환
    public long insert() throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
        // ? placeholder에 값 설정 (append에서 추가된 순서대로)
        // ... (PreparedStatement의 setXXX 메서드를 사용하여 값 설정)

        // placeholder에 값 바인딩
        int index = 1;
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof String) {
                pstmt.setString(index++, (String) value);
            } else if (value instanceof Integer) {
                pstmt.setInt(index++, (Integer) value);
            } else {
                // 다른 데이터 타입에 대한 처리 추가
                pstmt.setObject(index++, value);
            }
        }

        int affectedRows = pstmt.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("No rows affected");
        }

        // 생성된 ID 가져오기
        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getLong(1);
        } else {
            throw new SQLException("No ID obtained.");
        }
    }
}