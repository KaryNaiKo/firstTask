package com.example.hibernate.repository;

import com.example.hibernate.JPAUtil;
import com.example.hibernate.entity.Data;

import javax.persistence.EntityManager;
import java.util.List;

public class DataRepository {
    private static DataRepository instance;
    private EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

    private DataRepository() {
    }

    public static DataRepository getInstance() {
        if (instance == null) {
            synchronized (DataRepository.class) {
                if (instance == null) {
                    instance = new DataRepository();
                }
            }
        }

        return instance;
    }

    public List<Data> getData() {
        return getData("");
    }

    public List<Data> getData(String filter) {
        return em.createQuery("SELECT d FROM Data d WHERE d.data1 LIKE CONCAT('%', :filter, '%') OR d.data2 LIKE CONCAT('%', :filter, '%')")
                .setParameter("filter", filter)
                .getResultList();
    }


    public void delete(Data data) {
        em.getTransaction().begin();
        em.remove(data);
        em.flush();
        em.getTransaction().commit();
    }

    public void save(Data data) {
        em.getTransaction().begin();
        em.persist(data);
        em.flush();
        em.getTransaction().commit();
    }

    public void update(Data data) {
        em.detach(data);
        em.getTransaction().begin();
        em.merge(data);
        em.flush();
        em.getTransaction().commit();
    }

    public void clear() {
        em.clear();
    }
}
