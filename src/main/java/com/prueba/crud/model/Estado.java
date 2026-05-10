package com.prueba.crud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;


public class Estado {


    private Long estadoId;
    private String nombre;

    // @JsonIgnore evita recursion infinita al serializar a JSON
    // (Estado -> Clientes -> Estado -> Clientes...)
    @JsonIgnore
    private List<Cliente> clientes = new ArrayList<>();

    // Constructor vacio OBLIGATORIO para Hibernate
    public Estado() {}

    public Estado(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters
    public Long getEstadoId() { return estadoId; }
    public void setEstadoId(Long estadoId) { this.estadoId = estadoId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Cliente> getClientes() { return clientes; }
    public void setClientes(List<Cliente> clientes) { this.clientes = clientes; }

    @Override
    public String toString() {
        return "Estado{estadoId=" + estadoId + ", nombre='" + nombre + "'}";
    }
}