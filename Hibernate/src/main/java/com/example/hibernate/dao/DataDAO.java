package com.example.hibernate.dao;

import com.example.hibernate.HibernateSessionFactoryUtil;
import com.example.hibernate.entity.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class DataDAO {
    private SessionFactory sessionFactory;
    private static DataDAO instance;

    private DataDAO() {
        sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
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

    public List<Data> getData() {
        return sessionFactory.openSession().createQuery("From Data").list();
    }


    public void delete(Data data) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(data);
        tx1.commit();
        session.close();
    }


    public void save(Data data) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(data);
        tx1.commit();
        session.close();
    }

    public void update(Data data) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(data);
        tx1.commit();
        session.close();
    }
}
