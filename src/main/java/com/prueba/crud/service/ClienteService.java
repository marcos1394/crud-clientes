package com.prueba.crud.service;

import com.prueba.crud.model.Cliente;
import com.prueba.crud.model.Estado;
import com.prueba.crud.repository.ClienteRepository;
import com.prueba.crud.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente create(Cliente cliente) {
        // Resolver el Estado desde la BD si viene estado_id
        if (cliente.getEstado() != null
                && cliente.getEstado().getEstadoId() != null) {

            Estado estado = estadoRepository
                    .findById(cliente.getEstado().getEstadoId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Estado no encontrado con id: "
                                    + cliente.getEstado().getEstadoId()));

            cliente.setEstado(estado);
        }

        // Fecha de alta por defecto = hoy
        if (cliente.getFechaAlta() == null) {
            cliente.setFechaAlta(LocalDate.now());
        }

        // Status por defecto = 1 (activo)
        if (cliente.getStatus() == null) {
            cliente.setStatus(1);
        }

        // Forzamos INSERT limpio
        cliente.setClienteId(null);
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> update(Long id, Cliente datosNuevos) {
        return clienteRepository.findById(id).map(existente -> {

            existente.setNombre(datosNuevos.getNombre());
            existente.setApPaterno(datosNuevos.getApPaterno());
            existente.setApMaterno(datosNuevos.getApMaterno());
            existente.setFechaNacimiento(datosNuevos.getFechaNacimiento());
            existente.setStatus(datosNuevos.getStatus());

            // Actualizar Estado si viene en el request
            if (datosNuevos.getEstado() != null
                    && datosNuevos.getEstado().getEstadoId() != null) {

                Estado estado = estadoRepository
                        .findById(datosNuevos.getEstado().getEstadoId())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Estado no encontrado con id: "
                                        + datosNuevos.getEstado().getEstadoId()));

                existente.setEstado(estado);
            }

            return clienteRepository.save(existente);
        });
    }

    public boolean delete(Long id) {
        return clienteRepository.findById(id).map(cliente -> {
            clienteRepository.delete(cliente);
            return true;
        }).orElse(false);
    }
}