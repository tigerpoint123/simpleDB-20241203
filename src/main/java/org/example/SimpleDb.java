package org.example;

import java.sql.*; // SQL 관련 기능을 사용하기 위한 라이브러리 임포트

import lombok.RequiredArgsConstructor; // Lombok 라이브러리 사용 선언 (생성자 자동 생성 등 편의 기능 제공)
import org.slf4j.Logger; // 로그를 남기기 위한 인터페이스
import org.slf4j.LoggerFactory; // 로그를 생성하는 클래스

import java.sql.DriverManager; // JDBC 드라이버 관리 클래스
import java.sql.SQLException; // SQL 관련 예외 클래스

@RequiredArgsConstructor // 생성자 자동 생성 어노테이션
public class SimpleDb {
    private static final Logger log = LoggerFactory.getLogger(SimpleDb.class); // 로그 객체 생성

    private final String host; // 데이터베이스 서버 주소
    private final String username; // 데이터베이스 사용자 이름
    private final String password; // 데이터베이스 사용자 비밀번호
    private final String dbName; // 데이터베이스 이름

    public void run(String sql, Object... args) { // Object... args : 가변 인자로, SQL 문에 사용될 동적 값들을 배열 형태로 받습니다.
        // 객체를 자동으로 닫아서 사용하지 않는 자원을 닫아 메모리 누수를 방지
        try (Connection connection = getConnection(); // 데이터베이스 연결 객체
             PreparedStatement pstmt = connection.prepareStatement(sql)) { // sql문을 전달받고 이를 기반으로 객체를 생성. PreparedStatement는 sql 삽입 공격을 방지하고 성능을 향상
            for (int i = 0; i < args.length; i++) {
                pstmt.setObject(i + 1, args[i]);
            }
            pstmt.executeUpdate(); // insert update delete 등 실행
            log.info("SQL 실행 성공: {}", sql);
        } catch (SQLException e) {
            log.error("SQL 실행 실패: {}", sql, e);
        }
    }

    private Connection getConnection() throws SQLException { // 데이터베이스 연결 메서드
        String url = String.format("jdbc:mysql://%s/%s", host, dbName); // 데이터베이스 연결 URL 생성
        return DriverManager.getConnection(url, username, password); // 연결 객체 반환
    }

}
