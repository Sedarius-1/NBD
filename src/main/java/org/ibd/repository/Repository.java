package org.ibd.repository;

import org.ibd.exceptions.RepositoryException;

public interface Repository<T> {
    void add(T t) throws RepositoryException;

    void remove(T t) throws RepositoryException;

    T get(Long id) throws RepositoryException;

    // TODO: add `find`
    // TODO: add `getAll`
}
