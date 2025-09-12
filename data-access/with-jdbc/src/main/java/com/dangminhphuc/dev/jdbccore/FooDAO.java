package com.dangminhphuc.dev.jdbccore;

import java.util.List;

public interface FooDAO {
    Foo getById(long id);

    default Foo getByProcedure(long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    List<Foo> getAll();

    int insert(Foo foo);

    int[] insert(List<Foo> foos);

    long insertAndGetId(Foo foo);

    void update(Foo foo);

    int[] update(List<Foo> foos);

    void delete(int id);

    int count();


}
