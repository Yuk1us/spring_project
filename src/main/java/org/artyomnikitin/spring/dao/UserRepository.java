package org.artyomnikitin.spring.dao;

import org.artyomnikitin.spring.dto.UserBody;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface UserRepository<T>{
    T save(T entity);
    Iterable<T> findAll();
    boolean update(UserBody userBody);
    boolean exists(T entity);
    boolean delete(T entity);
}
