package com.prueba.crud.repository;

import com.prueba.crud.model.Estado;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de Estado.
 *
 * Usa Hibernate SessionFactory directamente.
 * La SessionFactory fue definida en spring-config.xml
 * y Spring la inyecta aqui con @Autowired.

 */
@Repository
public class EstadoRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public Estado save(Estado estado) {
        sessionFactory.getCurrentSession().saveOrUpdate(estado);
        return estado;
    }

    public Optional<Estado> findById(Long id) {
        Estado estado = sessionFactory
                .getCurrentSession()
                .get(Estado.class, id);
        return Optional.ofNullable(estado);
    }

    @SuppressWarnings("unchecked")
    public List<Estado> findAll() {
        // HQL: como SQL pero con nombres de clase Java, no de tabla
        return sessionFactory
                .getCurrentSession()
                .createQuery("FROM Estado ORDER BY nombre")
                .list();
    }

    public void delete(Estado estado) {
        sessionFactory.getCurrentSession().delete(estado);
    }

    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }
}