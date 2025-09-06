package com.dangminhphuc.dev.withjdbc;

import com.dangminhphuc.dev.jdbccore.AppConfig;
import com.dangminhphuc.dev.jdbccore.Foo;
import com.dangminhphuc.dev.jdbccore.FooDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
public class JdbcTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("jdbcTemplateImpl")
    private FooDAO jdbcTemplate;

    @Autowired
    @Qualifier("jdbcBatch")
    private FooDAO jdbcBatch;

    @Autowired
    @Qualifier("simpleJdbc")
    private FooDAO simpleJdbc;

    @Autowired
    @Qualifier("modelingJdbc")
    private FooDAO modelingJdbc;

    @Nested
    @DisplayName("JDBC Template Tests")
    @Sql(scripts = {"/schema.sql", "/cleanup.sql", "/initial.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    class JdbcTemplateTest {
        @Test
        void testGetById() {
            Foo foo = jdbcTemplate.getById(1L);

            assertNotNull(foo);
            assertEquals(1L, foo.getId());
            assertEquals(123, foo.getNumber());
            assertEquals("alpha", foo.getString());
//        assertEquals(foo.getDate(), new Date());
//        assertEquals(foo.getAmount(), new BigDecimal(100.00));
        }

        @Test
        void testGetAll() {
            List<Foo> foos = jdbcTemplate.getAll();
            assertNotNull(foos);
            assertEquals(3, foos.size());
        }

        @Test
        void testCount() {
            int count = jdbcTemplate.count();
            assertEquals(3, count);
        }

        @Test
        void testInsert() {
            Foo foo = new com.dangminhphuc.dev.jdbccore.Foo();
            foo.setNumber(456);
            foo.setString("gamma");
            foo.setBool(false);
            foo.setDate(new java.util.Date(System.currentTimeMillis()));
            foo.setAmount(new java.math.BigDecimal("150.00"));

            long id = jdbcTemplate.insertAndGetId(foo);
            assertTrue(id > 0);

            Foo eFoo = jdbcTemplate.getById(id);

            assertEquals(id, eFoo.getId());
            assertEquals(foo.getNumber(), eFoo.getNumber());
            assertEquals(foo.getString(), eFoo.getString());
            assertEquals(foo.isBool(), eFoo.isBool());
        }

        @Test
        void testInsertAndGetId() {
            Foo foo = new com.dangminhphuc.dev.jdbccore.Foo();
            foo.setNumber(456);
            foo.setString("gamma");
            foo.setBool(false);
            foo.setDate(new java.util.Date(System.currentTimeMillis()));
            foo.setAmount(new java.math.BigDecimal("150.00"));

            long id = jdbcTemplate.insertAndGetId(foo);

            assertTrue(id > 0);
        }

        @Test
        void testUpdate() {
            Foo foo = new com.dangminhphuc.dev.jdbccore.Foo();
            foo.setId(3L);
            foo.setNumber(999);
            foo.setString("beta");
            foo.setBool(true);
            foo.setDate(new java.util.Date(System.currentTimeMillis()));
            foo.setAmount(new java.math.BigDecimal("200.00"));

            jdbcTemplate.update(foo);

            Foo eFoo = jdbcTemplate.getById(3L);

            assertEquals(foo.getId(), eFoo.getId());
            assertEquals(foo.getNumber(), eFoo.getNumber());
            assertEquals(foo.getString(), eFoo.getString());
            assertEquals(foo.isBool(), eFoo.isBool());
//        assertEquals(foo.getDate(), eFoo.getDate());
            assertEquals(foo.getAmount(), eFoo.getAmount());
        }
    }


    @Nested
    @DisplayName("JDBC Batch Tests")
    class JdbcBatchTest {

        @BeforeEach
        void setUp() throws SQLException {
            try (Connection connection = dataSource.getConnection()) {
                ScriptUtils.executeSqlScript(connection, new ClassPathResource("schema.sql"));
                ScriptUtils.executeSqlScript(connection, new ClassPathResource("cleanup.sql"));
                ScriptUtils.executeSqlScript(connection, new ClassPathResource("initial.sql"));
            }
        }

        @Test
        void testInsertBatch() {
            int count = jdbcBatch.count();
            assertEquals(3, count);
            int size = 100;
            List<Foo> foos = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Foo foo = new com.dangminhphuc.dev.jdbccore.Foo();
                foo.setNumber(100 + i);
                foo.setString("string " + i);
                foo.setBool(i % 2 == 0);
                foo.setDate(new java.util.Date(System.currentTimeMillis()));
                foo.setAmount(new java.math.BigDecimal("100.00").add(new java.math.BigDecimal(i)));
                foos.add(foo);
            }
            int[] insert = jdbcBatch.insert(foos);
            assertEquals(size, insert.length);

            int newCount = jdbcTemplate.count();
            assertEquals(count + size, newCount);
        }

        @Test
        void testUpdateBatch() {
            int count = jdbcBatch.count();
            assertEquals(3, count);
            int size = 3;
            List<Foo> foos = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Foo foo = new com.dangminhphuc.dev.jdbccore.Foo();
                foo.setId(i + 1L);
                foo.setNumber(200 + i);
                foo.setString("updated string " + i);
                foo.setBool(i % 2 != 0);
                foo.setDate(new java.util.Date(System.currentTimeMillis()));
                foo.setAmount(new java.math.BigDecimal("200.00").add(new java.math.BigDecimal(i)));
                foos.add(foo);
            }
            int[] update = jdbcBatch.update(foos);
            assertEquals(size, update.length);

            int newCount = jdbcTemplate.count();
            assertEquals(count, newCount);
        }
    }

    @Nested
    @DisplayName("Simple JDBC Tests")
    @Sql(scripts = {"/schema.sql", "/cleanup.sql", "/initial.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    class SimpleJdbcTest {

        @Test
        void testInsert() {
            Foo foo = new Foo();
            foo.setNumber(456);
            foo.setString("gamma");
            foo.setBool(false);
            foo.setDate(new java.util.Date(System.currentTimeMillis()));
            foo.setAmount(new java.math.BigDecimal("150.00"));

            long rowAffected = simpleJdbc.insert(foo);
            assertEquals(1, rowAffected);
        }

        @Test
        void testInsertAndReturnId() {
            Foo foo = new Foo();
            foo.setNumber(456);
            foo.setString("gamma");
            foo.setBool(false);
            foo.setDate(new java.util.Date(System.currentTimeMillis()));
            foo.setAmount(new java.math.BigDecimal("150.00"));

            long id = simpleJdbc.insertAndGetId(foo);
            assertTrue(id > 0);


            Foo eFoo = getFoo(id);
            assertEquals(foo.getNumber(), eFoo.getNumber());
            assertEquals(foo.getString(), eFoo.getString());
            assertEquals(foo.isBool(), eFoo.isBool());
//            assertEquals(foo.getDate(), eFoo.getDate());
            assertEquals(foo.getAmount(), eFoo.getAmount());
        }

    }

    @Nested
    @DisplayName("Mapping SQL Query Tests")
    @Sql(scripts = {"/schema.sql", "/cleanup.sql", "/initial.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    class MappingSqlQueryTest {
        @Test
        void testGetByProcedure() {
            Foo foo = modelingJdbc.getById(1L);

            assertNotNull(foo);
            assertEquals(1L, foo.getId());
            assertEquals(123, foo.getNumber());
            assertEquals("alpha", foo.getString());
//        assertEquals(foo.getDate(), new Date());
            assertEquals(100, foo.getAmount().longValue());
        }

        @Test
        void testUpdate() {
            Foo foo = new Foo();
            foo.setId(3L);
            foo.setNumber(999);
            foo.setString("beta");
            foo.setBool(true);
            foo.setDate(new java.util.Date(System.currentTimeMillis()));
            foo.setAmount(new java.math.BigDecimal("200.00"));

            modelingJdbc.update(foo);

            Foo eFoo = modelingJdbc.getById(3L);

            assertEquals(foo.getId(), eFoo.getId());
            assertEquals(foo.getNumber(), eFoo.getNumber());
            assertEquals(foo.getString(), eFoo.getString());
            assertEquals(foo.isBool(), eFoo.isBool());
        }
    }

    private Foo getFoo(long id) {
        return this.jdbcTemplate.getById(id);
    }
}
