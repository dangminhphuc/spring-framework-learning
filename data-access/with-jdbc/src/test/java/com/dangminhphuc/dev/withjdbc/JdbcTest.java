package com.dangminhphuc.dev.withjdbc;


import com.dangminhphuc.dev.jdbccore.AppConfig;
import com.dangminhphuc.dev.jdbccore.Foo;
import com.dangminhphuc.dev.jdbccore.FooDAO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
public class JdbcTest {

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
    class JdbcTemplateTest {
        @Test
        void testGetById() {
            var foo = jdbcTemplate.getById(1L);

            assertNotNull(foo);
            assertEquals(1L, foo.getId());
            assertEquals(123, foo.getNumber());
            assertEquals("alpha", foo.getString());
//        assertEquals(foo.getDate(), new Date());
//        assertEquals(foo.getAmount(), new BigDecimal(100.00));
        }

        @Test
        void testGetAll() {
            var foos = jdbcTemplate.getAll();
            assertNotNull(foos);
            assertEquals(3, foos.size());
        }

        @Test
        void testCount() {
            var count = jdbcTemplate.count();
            assertEquals(3, count);
        }

        @Test
        void testInsert() {
            var foo = new com.dangminhphuc.dev.jdbccore.Foo();
            foo.setId(4L);
            foo.setNumber(456);
            foo.setString("gamma");
            foo.setBool(false);
            foo.setDate(new java.util.Date(System.currentTimeMillis()));
            foo.setAmount(new java.math.BigDecimal("150.00"));

            jdbcTemplate.insert(foo);

            Foo eFoo = jdbcTemplate.getById(4L);

            assertEquals(4L, eFoo.getId());
            assertEquals(foo.getNumber(), eFoo.getNumber());
            assertEquals(foo.getString(), eFoo.getString());
            assertEquals(foo.isBool(), eFoo.isBool());
        }

        @Test
        void testInsertAndGetId() {
            var foo = new com.dangminhphuc.dev.jdbccore.Foo();
            foo.setNumber(456);
            foo.setString("gamma");
            foo.setBool(false);
            foo.setDate(new java.util.Date(System.currentTimeMillis()));
            foo.setAmount(new java.math.BigDecimal("150.00"));

            long id = jdbcTemplate.insertAndGetId(foo);

            assertEquals(4L, id);
        }

        @Test
        void testUpdate() {
            var foo = new com.dangminhphuc.dev.jdbccore.Foo();
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
        @Test
        void testInsertBatch() {
            int count = jdbcBatch.count();
            int size = 100;
            List<Foo> foos = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                var foo = new com.dangminhphuc.dev.jdbccore.Foo();
                foo.setId(i + 10L);
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
            int size = 3;
            List<Foo> foos = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                var foo = new com.dangminhphuc.dev.jdbccore.Foo();
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
            System.out.println(Arrays.toString(update));

            int newCount = jdbcTemplate.count();
            assertEquals(count, newCount);
        }
    }

    @Nested
    @DisplayName("Simple JDBC Tests")
    class SimpleJdbcTest {

        @Test
        void testInsert() {
            var foo = new Foo();
            foo.setNumber(456);
            foo.setString("gamma");
            foo.setBool(false);
            foo.setDate(new java.util.Date(System.currentTimeMillis()));
            foo.setAmount(new java.math.BigDecimal("150.00"));

            long rowAffected = simpleJdbc.insert(foo);
            assertEquals(1, rowAffected);
        }

        @Test
        @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        void testInsertAndReturnId() {
            var foo = new Foo();
            foo.setNumber(456);
            foo.setString("gamma");
            foo.setBool(false);
            foo.setDate(new java.util.Date(System.currentTimeMillis()));
            foo.setAmount(new java.math.BigDecimal("150.00"));

            long id = simpleJdbc.insertAndGetId(foo);
            assertEquals(1L, id);


            Foo eFoo = getFoo(1L);
            assertEquals(foo.getNumber(), eFoo.getNumber());
            assertEquals(foo.getString(), eFoo.getString());
            assertEquals(foo.isBool(), eFoo.isBool());
//            assertEquals(foo.getDate(), eFoo.getDate());
            assertEquals(foo.getAmount(), eFoo.getAmount());
        }

    }

    @Nested
    @DisplayName("Mapping SQL Query Tests")
    class MappingSqlQueryTest {
        @Test
        @Sql(scripts = "/initial.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        void testGetByProcedure() {
            var foo = modelingJdbc.getById(1L);

            assertNotNull(foo);
            assertEquals(1L, foo.getId());
            assertEquals(123, foo.getNumber());
            assertEquals("alpha", foo.getString());
//        assertEquals(foo.getDate(), new Date());
            assertEquals(100, foo.getAmount().longValue());
        }

        @Test
        @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @Sql(scripts = "/initial.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        void testUpdate() {
            var foo = new Foo();
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
