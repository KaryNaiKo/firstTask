package dao;

import com.example.hibernate.HibernateSessionFactoryUtil;
import com.example.hibernate.entity.Data;
import org.hibernate.SessionFactory;

import java.util.List;

public class DataDAO {
    private SessionFactory sessionFactory;

    public DataDAO() {
        sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    public List<Data> getData() {
        return sessionFactory.openSession().createQuery("From Data").list();
    }
}
