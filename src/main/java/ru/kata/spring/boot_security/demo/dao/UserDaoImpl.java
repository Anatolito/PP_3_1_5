package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User findOne(int id) {
        return entityManager.find(User.class, id);
    }
    @Transactional
    @Override
    public void saveUser(User user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void update(int id, User updatedUser) {
        updatedUser.setId(id);
        entityManager.merge(updatedUser);
    }

    @Override
    public void deleteUser(int id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public User findByUserName(String username) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u join fetch u.roles where u.email=:email", User.class).setParameter("email", username);
        return query.getSingleResult();
    }
}
