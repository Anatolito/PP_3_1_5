package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> getAllUsers() {
        return entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }

    @Override
    public Role showUserById(int id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Role findRoleByRole(String role) {
        TypedQuery<Role> query = entityManager.createQuery("select r from Role r where r.role=:name", Role.class).setParameter("name", role);
        return query.getSingleResult();
    }

    @Transactional
    @Override
    public void save(Role role) {
        entityManager.persist(role);
    }

    @Override
    public void deleteById(int id) {
        entityManager.remove(entityManager.find(Role.class, id));
    }


}