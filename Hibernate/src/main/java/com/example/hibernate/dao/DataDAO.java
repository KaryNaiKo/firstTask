package com.example.hibernate.dao;

import com.example.hibernate.HibernateSessionFactoryUtil;
import com.example.hibernate.entity.Data;
import org.hibernate.SessionFactory;

import java.util.List;

public class DataDAO {
    private SessionFactory sessionFactory;
    private static DataDAO instance;

    private DataDAO() {
        sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    public List<Data> getData() {
        return sessionFactory.openSession().createQuery("From Data").list();
    }

    public static DataDAO getInstance() {
        if (instance == null) {
            synchronized (DataDAO.class) {
                if (instance == null) {
                    instance = new DataDAO();
                }
            }
        }

        return instance;
    }
}
