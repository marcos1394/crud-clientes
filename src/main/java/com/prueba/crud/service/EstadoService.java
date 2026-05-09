package com.prueba.crud.service;

import com.prueba.crud.model.Estado;
import com.prueba.crud.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de negocio para Estado.
 *
 * @Transactional abre una transaccion al entrar a cada metodo
 * y la cierra al salir. Si ocurre una excepcion no chequeada
 * hace rollback automaticamente.
 *
 * El HibernateTransactionManager definido en spring-config.xml
 * es quien gestiona esas transacciones.
 */
@Service
@Transactional
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> findAll() {
        return estadoRepository.findAll();
    }

    public Optional<Estado> findById(Long id) {
        return estadoRepository.findById(id);
    }

    public Estado create(Estado estado) {
        // Forzamos INSERT limpio — sin ID previo
        estado.setEstadoId(null);
        return estadoRepository.save(estado);
    }

    public Optional<Estado> update(Long id, Estado datosNuevos) {
        return estadoRepository.findById(id).map(existente -> {
            existente.setNombre(datosNuevos.getNombre());
            return estadoRepository.save(existente);
        });
    }

    public boolean delete(Long id) {
        return estadoRepository.findById(id).map(estado -> {
            estadoRepository.delete(estado);
            return true;
        }).orElse(false);
    }
}