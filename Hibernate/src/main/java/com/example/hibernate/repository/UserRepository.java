package com.example.hibernate.repository;

import com.example.hibernate.JPAUtil;
import com.example.hibernate.entity.User;
import org.hibernate.jpa.QueryHints;

import javax.persistence.EntityManager;

public class UserRepository {
    private volatile static UserRepository instance;
    private EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (DataRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                }
            }
        }

        return instance;
    }

    public User authUser(String login, String password) {
        return em.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.login=:login AND u.password=:password", User.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
//                .getSingleResult();
                .getResultList().get(0);
    }

    public User authUser(String login) {
        return em.createQuery("SELECT u FROM User u WHERE u.login=:login", User.class)
                .setParameter("login", login)

                .getSingleResult();
    }

    public static void main(String[] args) {
        UserRepository repository = new UserRepository();
        User u = repository.authUser("admin", "password");
        System.out.println(u);

    }
}
