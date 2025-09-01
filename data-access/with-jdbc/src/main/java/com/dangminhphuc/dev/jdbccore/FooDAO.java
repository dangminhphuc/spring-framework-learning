package com.dangminhphuc.dev.jdbccore;

import java.util.List;

public interface FooDAO {
    Foo getById(long id);

    List<Foo> getAll();

    void insert(Foo foo);

    long insertAndGetId(Foo foo);

    void update(Foo foo);

    void delete(int id);

    int count();

}
