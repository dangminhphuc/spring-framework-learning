package com.dangminhphuc.dev.jdbccore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("simpleJdbc")
public class SimpleJdbcFooDAOImpl implements FooDAO {

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final SimpleJdbcCall simpleJdbcCall;

    @Autowired
    public SimpleJdbcFooDAOImpl(DataSource dataSource) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("foo")
                .usingGeneratedKeyColumns("id");

        this.simpleJdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("get_foo_by_id")
                .returningResultSet("foo", (rs, rowNum) -> new Foo(
                        rs.getLong("id"),
                        rs.getInt("number"),
                        rs.getString("string"),
                        rs.getBoolean("bool"),
                        rs.getDate("date"),
                        rs.getBigDecimal("amount")
                ));
    }

    @Override
    public Foo getById(long id) {
        return null;
    }

    @Override
    public Foo getByProcedure(long id) {


        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Foo> getAll() {
        return List.of();
    }

    @Override
    public int insert(Foo foo) {
        Map<String, Object> params = new HashMap<>();
        params.put("number", foo.getNumber());
        params.put("string", foo.getString());
        params.put("bool", foo.isBool());
        params.put("date", new java.sql.Date(foo.getDate().getTime()));
        params.put("amount", foo.getAmount());

        return this.simpleJdbcInsert.execute(params);
    }

    @Override
    public int[] insert(List<Foo> foos) {
        return new int[0];
    }

    @Override
    public long insertAndGetId(Foo foo) {
        Map<String, Object> params = new HashMap<>();
        params.put("number", foo.getNumber());
        params.put("string", foo.getString());
        params.put("bool", foo.isBool());
        params.put("date", new java.sql.Date(foo.getDate().getTime()));
        params.put("amount", foo.getAmount());

        return this.simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    @Override
    public void update(Foo foo) {
    }

    @Override
    public int[] update(List<Foo> foos) {
        return new int[0];
    }

    @Override
    public void delete(int id) {
    }

    @Override
    public int count() {
        return 0;
    }
}
