package com.example.hibernate.dao;

import com.example.hibernate.HibernateSessionFactoryUtil;
import com.example.hibernate.entity.User;
import org.hibernate.SessionFactory;

public class UserDAO {
    private SessionFactory sessionFactory;
    private static UserDAO instance;

    private UserDAO() {
        sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            synchronized (DataDAO.class) {
                if (instance == null) {
                    instance = new UserDAO();
                }
            }
        }

        return instance;
    }

    public User authUser(String login, String password) {
        return sessionFactory.openSession().createQuery("SELECT u FROM User u WHERE u.login=:login AND u.password=:password", User.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .getSingleResult();
    }
}
