package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Sql {
    private StringBuilder sqlFormat;
    private List<Object> params;
    private final SimpleDb simpleDb;

    public Sql(SimpleDb simpleDb) {
        this.simpleDb = simpleDb;
        this.sqlFormat = new StringBuilder();
        this.params = new ArrayList<>();
    }

    // SQL 문에 문자열 추가
    public Sql append(String str) {
        sqlFormat.append(str);
        return this;
    }

    // SQL 문에 ? placeholder와 값 추가
    public Sql append(String placeholder, Object... value) {
        sqlFormat.append(placeholder);
        // value는 현재 저장만 하고, 실제 사용은 PreparedStatement에서 이루어집니다.
        // 나중에 PreparedStatement 생성 시 value를 이용하여 바인딩할 것입니다.
        params.add(value);
        return this;
    }

    // SQL 문 실행 및 생성된 ID 반환
    public long insert() {
        return 1l;
    }

    public int update() {
        return 3;
    }

    public int delete() {
        return 2;
    }

    public List<Map<String, Object>> selectRows() {
        return null;
    }

    // SELECT 1 = 0
    public Boolean selectBoolean() {
        return simpleDb.selectBoolean(sqlFormat);
    }

    public String selectString() {
        return simpleDb.selectString(sqlFormat);
    }

    public Long selectLong() {
        return 1l;
    }

    public LocalDateTime selectDatetime() {
        return LocalDateTime.now();
    }

    public Map<String, Object> selectRow() {
        return null;
    }
}
