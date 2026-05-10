package com.prueba.crud.repository;

import com.prueba.crud.model.Cliente;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de Cliente.
 * Acceso a datos via Hibernate SessionFactory.
 */
@Repository
public class ClienteRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public Cliente save(Cliente cliente) {
        sessionFactory.getCurrentSession().saveOrUpdate(cliente);
        return cliente;
    }

    public Optional<Cliente> findById(Long id) {
        Cliente cliente = sessionFactory
                .getCurrentSession()
                .get(Cliente.class, id);
        return Optional.ofNullable(cliente);
    }

    @SuppressWarnings("unchecked")
    public List<Cliente> findAll() {
        // LEFT JOIN FETCH: trae el Estado junto con cada Cliente
        // en una sola query — evita el problema N+1
        return sessionFactory
                .getCurrentSession()
                .createQuery(
                        "FROM Cliente c LEFT JOIN FETCH c.estado " +
                                "ORDER BY c.apPaterno, c.nombre")
                .list();
    }

    @SuppressWarnings("unchecked")
    public List<Cliente> findByEstadoId(Long estadoId) {
        return sessionFactory
                .getCurrentSession()
                .createQuery(
                        "FROM Cliente c WHERE c.estado.estadoId = :estadoId")
                .setParameter("estadoId", estadoId)
                .list();
    }

    public void delete(Cliente cliente) {
        sessionFactory.getCurrentSession().delete(cliente);
    }
}