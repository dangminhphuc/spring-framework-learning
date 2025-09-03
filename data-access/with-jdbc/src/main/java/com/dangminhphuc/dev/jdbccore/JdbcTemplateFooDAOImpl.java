package com.dangminhphuc.dev.jdbccore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

//@Primary
@Repository("jdbcTemplateImpl")
public class JdbcTemplateFooDAOImpl implements FooDAO {

    private final JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    public JdbcTemplateFooDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public Foo getById(long id) {
        return this.jdbcTemplate.queryForObject(
                "SELECT * FROM foo WHERE id = ?",
                (rs, rowNum) -> new Foo(
                        rs.getLong("id"),
                        rs.getInt("number"),
                        rs.getString("string"),
                        rs.getBoolean("bool"),
                        rs.getDate("date"),
                        rs.getBigDecimal("amount")
                ),
                id
        );
    }

    @Override
    public List<Foo> getAll() {
        RowMapper<Foo> mapper = (rs, rowNum) -> new Foo(
                rs.getLong("id"),
                rs.getInt("number"),
                rs.getString("string"),
                rs.getBoolean("bool"),
                rs.getDate("date"),
                rs.getBigDecimal("amount")
        );

        return this.jdbcTemplate.query("SELECT * FROM foo", mapper);
    }

    @Override
    public int insert(Foo foo) {
        String sql = "INSERT INTO foo (id, number, string, bool, date, amount) VALUES (:id, :number, :string, :bool, " +
                ":date, :amount)";
        Map<String, Object> paramMap = Map.of(
                "id", foo.getId(),
                "number", foo.getNumber(),
                "string", foo.getString(),
                "bool", foo.isBool(),
                "date", foo.getDate(),
                "amount", foo.getAmount()
        );

        this.namedParameterJdbcTemplate.update(sql, paramMap);
        return 1;
    }

    @Override
    public int[] insert(List<Foo> foos) {
        return new int[0];
    }

    @Override
    public long insertAndGetId(Foo foo) {
        String sql = "INSERT INTO foo (number, string, bool, date, amount) VALUES (?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setInt(1, foo.getNumber());
            ps.setString(2, foo.getString());
            ps.setBoolean(3, foo.isBool());
            ps.setDate(4, new java.sql.Date(foo.getDate().getTime()));
            ps.setBigDecimal(5, foo.getAmount());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Foo foo) {
        String sql = "UPDATE foo SET number = :number, string = :string, bool = :bool, date = :date, amount = :amount WHERE id = :id";
        SqlParameterSource nameParameters = new MapSqlParameterSource("id", foo.getId())
                .addValue("number", foo.getNumber())
                .addValue("string", foo.getString())
                .addValue("bool", foo.isBool())
                .addValue("date", foo.getDate())
                .addValue("amount", foo.getAmount());

        this.namedParameterJdbcTemplate.update(sql, nameParameters);
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
        return this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM foo", Integer.class);
    }
}
