package com.dangminhphuc.dev.withjdbc;


import com.dangminhphuc.dev.jdbccore.AppConfig;
import com.dangminhphuc.dev.jdbccore.Foo;
import com.dangminhphuc.dev.jdbccore.FooDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@Sql(scripts = "/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class JdbcTemplateTest {

    @Autowired
    private FooDAO fooDAO;


    @Test
    void testGetById() {
        var foo = fooDAO.getById(1L);

        assertNotNull(foo);
        assertEquals(1L, foo.getId());
        assertEquals(123, foo.getNumber());
        assertEquals("alpha", foo.getString());
//        assertEquals(foo.getDate(), new Date());
//        assertEquals(foo.getAmount(), new BigDecimal(100.00));
    }

    @Test
    void testGetAll() {
        var foos = fooDAO.getAll();
        assertNotNull(foos);
        assertEquals(3, foos.size());
    }

    @Test
    void testCount() {
        var count = fooDAO.count();
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

        fooDAO.insert(foo);

        Foo eFoo = fooDAO.getById(4L);

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

        long id = fooDAO.insertAndGetId(foo);

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

        fooDAO.update(foo);

        Foo eFoo = fooDAO.getById(3L);

        assertEquals(foo.getId(), eFoo.getId());
        assertEquals(foo.getNumber(), eFoo.getNumber());
        assertEquals(foo.getString(), eFoo.getString());
        assertEquals(foo.isBool(), eFoo.isBool());
//        assertEquals(foo.getDate(), eFoo.getDate());
        assertEquals(foo.getAmount(), eFoo.getAmount());
    }

}
