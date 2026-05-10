package com.prueba.crud.service;

import com.prueba.crud.model.Estado;
import com.prueba.crud.repository.EstadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios - EstadoService")
class EstadoServiceTest {

    @Mock
    private EstadoRepository estadoRepository;

    @InjectMocks
    private EstadoService estadoService;

    private Estado estadoMock;

    @BeforeEach
    void setUp() {
        estadoMock = new Estado();
        estadoMock.setEstadoId(1L);
        estadoMock.setNombre("Jalisco");
    }

    @Test
    @DisplayName("findAll - debe retornar lista de estados")
    void findAll_debeRetornarLista() {
        Estado estado2 = new Estado();
        estado2.setEstadoId(2L);
        estado2.setNombre("Sinaloa");

        when(estadoRepository.findAll())
                .thenReturn(Arrays.asList(estadoMock, estado2));

        List<Estado> resultado = estadoService.findAll();

        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Jalisco");
        verify(estadoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll - debe retornar lista vacia")
    void findAll_listaVacia() {
        when(estadoRepository.findAll()).thenReturn(List.of());

        List<Estado> resultado = estadoService.findAll();

        assertThat(resultado).isEmpty();
        verify(estadoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById - debe retornar estado cuando existe")
    void findById_cuandoExiste_retornaEstado() {
        when(estadoRepository.findById(1L))
                .thenReturn(Optional.of(estadoMock));

        Optional<Estado> resultado = estadoService.findById(1L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Jalisco");
    }

    @Test
    @DisplayName("findById - debe retornar empty cuando no existe")
    void findById_cuandoNoExiste_retornaEmpty() {
        when(estadoRepository.findById(99L))
                .thenReturn(Optional.empty());

        Optional<Estado> resultado = estadoService.findById(99L);

        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("create - debe guardar y retornar el estado")
    void create_debeGuardarEstado() {
        Estado nuevo = new Estado();
        nuevo.setNombre("Sonora");

        when(estadoRepository.save(any(Estado.class)))
                .thenReturn(nuevo);

        Estado resultado = estadoService.create(nuevo);

        assertThat(resultado.getNombre()).isEqualTo("Sonora");
        assertThat(nuevo.getEstadoId()).isNull();
        verify(estadoRepository, times(1)).save(any(Estado.class));
    }

    @Test
    @DisplayName("update - debe actualizar cuando existe")
    void update_cuandoExiste_actualizaNombre() {
        Estado datosNuevos = new Estado();
        datosNuevos.setNombre("Jalisco Actualizado");

        when(estadoRepository.findById(1L))
                .thenReturn(Optional.of(estadoMock));
        when(estadoRepository.save(any(Estado.class)))
                .thenReturn(estadoMock);

        Optional<Estado> resultado = estadoService.update(1L, datosNuevos);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre())
                .isEqualTo("Jalisco Actualizado");
        verify(estadoRepository, times(1)).save(any(Estado.class));
    }

    @Test
    @DisplayName("update - debe retornar empty cuando no existe")
    void update_cuandoNoExiste_retornaEmpty() {
        when(estadoRepository.findById(99L))
                .thenReturn(Optional.empty());

        Optional<Estado> resultado = estadoService
                .update(99L, new Estado());

        assertThat(resultado).isEmpty();
        verify(estadoRepository, never()).save(any(Estado.class));
    }

    @Test
    @DisplayName("delete - debe retornar true cuando existe")
    void delete_cuandoExiste_retornaTrue() {
        when(estadoRepository.findById(1L))
                .thenReturn(Optional.of(estadoMock));
        doNothing().when(estadoRepository)
                .delete(any(Estado.class));

        boolean resultado = estadoService.delete(1L);

        assertThat(resultado).isTrue();
        verify(estadoRepository, times(1)).delete(estadoMock);
    }

    @Test
    @DisplayName("delete - debe retornar false cuando no existe")
    void delete_cuandoNoExiste_retornaFalse() {
        when(estadoRepository.findById(99L))
                .thenReturn(Optional.empty());

        boolean resultado = estadoService.delete(99L);

        assertThat(resultado).isFalse();
        verify(estadoRepository, never()).delete(any(Estado.class));
    }
}