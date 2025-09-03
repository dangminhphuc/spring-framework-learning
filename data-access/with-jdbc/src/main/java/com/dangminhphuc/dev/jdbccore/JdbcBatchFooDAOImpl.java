package com.dangminhphuc.dev.jdbccore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository("jdbcBatch")
public class JdbcBatchFooDAOImpl implements FooDAO {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcBatchFooDAOImpl(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Foo getById(long id) {
        return null;
    }

    @Override
    public List<Foo> getAll() {
        return List.of();
    }

    @Override
    public int insert(Foo foo) {
        return 1;
    }

    @Override
    public long insertAndGetId(Foo foo) {
        return 0;
    }

    @Override
    public int[] insert(List<Foo> foos) {
        String sql = "INSERT INTO foo (number, string, bool, date, amount) VALUES (?, ?, ?, ?, ?)";
        return this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Foo foo = foos.get(i);
                ps.setInt(1, foo.getNumber());
                ps.setString(2, foo.getString());
                ps.setBoolean(3, foo.isBool());
                ps.setDate(4, new java.sql.Date(foo.getDate().getTime()));
                ps.setBigDecimal(5, foo.getAmount());
            }

            @Override
            public int getBatchSize() {
                return foos.size();
            }
        });
    }

    @Override
    public void update(Foo foo) {

    }

    @Override
    public int[] update(List<Foo> foos) {
        String sql = "UPDATE foo SET number = :number, string = :string, bool = :bool, date = :date, amount = :amount" +
                " WHERE id = :id";

        return this.namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(foos));
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public int count() {
        return 0;
    }
}
