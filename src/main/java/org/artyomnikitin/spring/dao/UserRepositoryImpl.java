package org.artyomnikitin.spring.dao;


import org.artyomnikitin.spring.dto.User;
import org.artyomnikitin.spring.dto.UserBody;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserRepositoryImpl implements UserRepository<User>{

    private final SessionFactory sessionFactory;
    @Autowired
    public UserRepositoryImpl(final SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }


    /**SQL Query takes count(id)+1 from DB and login + password From USER*/
    @Override
    public User save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.createNativeQuery("INSERT INTO users VALUES ((SELECT count(id) FROM users)+1,?,?)")
               .setParameter(1, user.getLogin())
               .setParameter(2, user.getPassword())
               .executeUpdate();
        return user;
    }
    /**Get All Users*/
    @Override
    public Iterable<User> findAll() {
        Session session = sessionFactory.getCurrentSession();
        List<User> userList = session.createQuery("FROM User", User.class).getResultList();
        userList.forEach(System.out::print);
        return userList;

    }


    /**Working*/
    @Override
    public boolean update(UserBody userBody) {
        Session session = sessionFactory.getCurrentSession();
        int a =session.createNativeQuery("UPDATE users SET password = ? WHERE login = ? AND password = ?")
               .setParameter(1, userBody.getNewPassword())
               .setParameter(2, userBody.getLogin())
               .setParameter(3, userBody.getOldPassword())
               .executeUpdate();
        return a > 0;
    }

    @Override
    public boolean exists(User user) {
        Session session = sessionFactory.getCurrentSession();
        boolean bool = session.createNativeQuery("SELECT (id,login,password) FROM users WHERE login = ? AND password = ?")
                .setParameter(1, user.getLogin())
                .setParameter(2, user.getPassword()).getResultList().size() > 0;
        return bool;
    }


    /**Should Delete One User from DB*/
    @Override
    public boolean delete(User user) {
        Session session = sessionFactory.getCurrentSession();
        int isUpdated = session.createNativeQuery("DELETE FROM users WHERE login = ? AND password = ?")
               .setParameter(1,user.getLogin())
               .setParameter(2, user.getPassword()).executeUpdate();
        return isUpdated > 0;
    }
}
