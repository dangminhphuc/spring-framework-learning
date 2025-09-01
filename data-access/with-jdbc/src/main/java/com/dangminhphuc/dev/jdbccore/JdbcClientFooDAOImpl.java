package com.dangminhphuc.dev.jdbccore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

public class JdbcClientFooDAOImpl implements FooDAO {
    private final JdbcClient jdbcClient;

    @Autowired
    public JdbcClientFooDAOImpl(DataSource dataSource) {
        this.jdbcClient = JdbcClient.create(dataSource);
    }

    @Override
    public Foo getById(long id) {
        return this.jdbcClient.sql("SELECT * FROM foo WHERE id = ?")
                .param(id)
                .query(Foo.class)
                .single();
    }

    @Override
    public List<Foo> getAll() {
        return this.jdbcClient.sql("SELECT * FROM foo")
                .query(Foo.class)
                .list();
    }

    @Override
    public void insert(Foo foo) {
        String sql = "INSERT INTO foo (id, number, string, bool, date, amount) VALUES (:id, :number, :string, :bool, :date, :amount)";
        this.jdbcClient.sql(sql)
                .param("id", foo.getId())
                .param("number", foo.getNumber())
                .param("string", foo.getString())
                .param("bool", foo.isBool())
                .param("date", foo.getDate())
                .param("amount", foo.getAmount())
                .update();
    }

    @Override
    public long insertAndGetId(Foo foo) {
        return 0;
    }

    @Override
    public void update(Foo foo) {
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public int count() {
        return 0;
    }
}
