package com.dangminhphuc.dev.jdbccore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("modelingJdbc")
public class ModelingJdbcFooDAOImpl implements FooDAO {
    private final MappingSqlQuery<Foo> fooMappingQuery;
    private final UpdateFoo fooUpdate;

    @Autowired
    public ModelingJdbcFooDAOImpl(DataSource dataSource) {
        this.fooMappingQuery = new FooMappingQuery(dataSource);
        this.fooUpdate = new UpdateFoo(dataSource);
    }

    @Override
    public Foo getById(long id) {
        return this.fooMappingQuery.findObject(id);
    }

    @Override
    public List<Foo> getAll() {
        return List.of();
    }

    @Override
    public int insert(Foo foo) {
        return 0;
    }

    @Override
    public int[] insert(List<Foo> foos) {
        return new int[0];
    }

    @Override
    public long insertAndGetId(Foo foo) {
        return 0;
    }

    @Override
    public void update(Foo foo) {
        this.fooUpdate.update(
                foo.getNumber(),
                foo.getString(),
                foo.isBool(),
                new java.sql.Date(foo.getDate().getTime()),
                foo.getAmount(),
                foo.getId()
        );
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

    static class FooMappingQuery extends MappingSqlQuery<Foo> {
        private static final String SQL = "SELECT * FROM foo WHERE id = ?";

        @Autowired
        public FooMappingQuery(DataSource ds) {
            super(ds, SQL);
            declareParameter(new org.springframework.jdbc.core.SqlParameter("id", java.sql.Types.BIGINT));
            compile();
        }

        @Override
        protected Foo mapRow(ResultSet rs, int rowNum) throws SQLException {
            Foo foo = new Foo();
            foo.setId(rs.getLong("id"));
            foo.setNumber(rs.getInt("number"));
            foo.setString(rs.getString("string"));
            foo.setBool(rs.getBoolean("bool"));
            foo.setDate(rs.getDate("date"));
            foo.setAmount(rs.getBigDecimal("amount"));
            return foo;
        }
    }

    static class UpdateFoo extends SqlUpdate {
        private static final String SQL = "UPDATE foo SET number = ?, string = ?, bool = ?, date = ?, amount = ? WHERE id = ?";

        @Autowired
        public UpdateFoo(DataSource ds) {
            super(ds, SQL);

            declareParameter(new SqlParameter("number", java.sql.Types.INTEGER));
            declareParameter(new SqlParameter("string", java.sql.Types.VARCHAR));
            declareParameter(new SqlParameter("bool", java.sql.Types.BOOLEAN));
            declareParameter(new SqlParameter("date", java.sql.Types.DATE));
            declareParameter(new SqlParameter("amount", java.sql.Types.DECIMAL));
            declareParameter(new SqlParameter("id", java.sql.Types.BIGINT));
            compile();
        }

    }

    static class InsertProcedure extends StoredProcedure {
        private static final String SQL = "insert_foo";

        @Autowired
        public InsertProcedure(DataSource ds) {
            setDataSource(ds);
            setFunction(true);
            setSql(SQL);

            declareParameter(new SqlOutParameter("date", Types.DATE));
            compile();
        }

        public Date execute() {
            // the 'sysdate' sproc has no input parameters, so an empty Map is supplied...
            Map<String, Object> results = execute(new HashMap<>());
            Date sysdate = (Date) results.get("date");
            return sysdate;
        }
    }
}
