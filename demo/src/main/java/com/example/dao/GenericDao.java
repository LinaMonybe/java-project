
package com.example.dao;

import java.util.List;

public interface GenericDao<T> {
    void add(T entity);
    T get(int id);
    List<T> getAll();
    void update(T entity);
    void delete(int id);
}
