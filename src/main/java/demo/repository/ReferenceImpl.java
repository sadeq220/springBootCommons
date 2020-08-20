package demo.repository;

import demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ReferenceImpl implements Reference {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public User getReference(String userName) {
        return entityManager.getReference(User.class,userName);
    }
}
