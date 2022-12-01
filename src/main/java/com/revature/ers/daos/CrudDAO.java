package com.revature.ers.daos;

import java.util.List;

public interface CrudDAO<T> {
    void save(T obj);

    void delete(T obj);

    void update (T obj);

    T findByID(String id);

    List<T> findAll();
}
