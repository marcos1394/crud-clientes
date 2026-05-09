package com.prueba.crud.model;

import java.time.LocalDate;

/**
 * POJO de Cliente.
 * SIN anotaciones JPA. El mapeo completo incluyendo
 * la relacion con Estado esta en Cliente.hbm.xml
 */
public class Cliente {

    private Long clienteId;
    private String nombre;
    private String apPaterno;
    private String apMaterno;
    private LocalDate fechaAlta;
    private LocalDate fechaNacimiento;
    private Integer status;

    // Relacion con Estado declarada en Cliente.hbm.xml
    // Hibernate carga el Estado completo gracias al fetch="join"
    private Estado estado;

    // Constructor vacio OBLIGATORIO para Hibernate
    public Cliente() {}

    // Getters y Setters
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApPaterno() { return apPaterno; }
    public void setApPaterno(String apPaterno) { this.apPaterno = apPaterno; }

    public String getApMaterno() { return apMaterno; }
    public void setApMaterno(String apMaterno) { this.apMaterno = apMaterno; }

    public LocalDate getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Cliente{clienteId=" + clienteId
                + ", nombre='" + nombre + "'"
                + ", apPaterno='" + apPaterno + "'"
                + ", status=" + status + "}";
    }
}