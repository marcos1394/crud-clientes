package com.prueba.crud.service;

import com.prueba.crud.model.Cliente;
import com.prueba.crud.model.Estado;
import com.prueba.crud.repository.ClienteRepository;
import com.prueba.crud.repository.EstadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios - ClienteService")
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EstadoRepository estadoRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente clienteMock;
    private Estado estadoMock;

    @BeforeEach
    void setUp() {
        estadoMock = new Estado();
        estadoMock.setEstadoId(1L);
        estadoMock.setNombre("Jalisco");

        clienteMock = new Cliente();
        clienteMock.setClienteId(1L);
        clienteMock.setNombre("Juan");
        clienteMock.setApPaterno("Garcia");
        clienteMock.setApMaterno("Lopez");
        clienteMock.setFechaNacimiento(LocalDate.of(1990, 5, 15));
        clienteMock.setFechaAlta(LocalDate.now());
        clienteMock.setStatus(1);
        clienteMock.setEstado(estadoMock);
    }

    // ── findAll ─────────────────────────────────────────────────

    @Test
    @DisplayName("findAll - debe retornar lista de clientes")
    void findAll_debeRetornarLista() {
        Cliente cliente2 = new Cliente();
        cliente2.setClienteId(2L);
        cliente2.setNombre("Maria");
        cliente2.setApPaterno("Martinez");

        when(clienteRepository.findAll())
                .thenReturn(Arrays.asList(clienteMock, cliente2));

        List<Cliente> resultado = clienteService.findAll();

        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Juan");
        assertThat(resultado.get(1).getNombre()).isEqualTo("Maria");
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll - debe retornar lista vacia")
    void findAll_listaVacia() {
        when(clienteRepository.findAll()).thenReturn(List.of());

        List<Cliente> resultado = clienteService.findAll();

        assertThat(resultado).isEmpty();
        verify(clienteRepository, times(1)).findAll();
    }

    // ── findById ────────────────────────────────────────────────

    @Test
    @DisplayName("findById - debe retornar cliente cuando existe")
    void findById_cuandoExiste_retornaCliente() {
        when(clienteRepository.findById(1L))
                .thenReturn(Optional.of(clienteMock));

        Optional<Cliente> resultado = clienteService.findById(1L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Juan");
        assertThat(resultado.get().getApPaterno()).isEqualTo("Garcia");
        assertThat(resultado.get().getEstado().getNombre())
                .isEqualTo("Jalisco");
    }

    @Test
    @DisplayName("findById - debe retornar empty cuando no existe")
    void findById_cuandoNoExiste_retornaEmpty() {
        when(clienteRepository.findById(99L))
                .thenReturn(Optional.empty());

        Optional<Cliente> resultado = clienteService.findById(99L);

        assertThat(resultado).isEmpty();
    }

    // ── create ──────────────────────────────────────────────────

    @Test
    @DisplayName("create - debe asignar fecha alta y status por defecto")
    void create_debeAsignarDefaults() {
        Cliente nuevo = new Cliente();
        nuevo.setNombre("Carlos");
        nuevo.setApPaterno("Rodriguez");
        // Sin fechaAlta ni status — el servicio los asigna

        when(clienteRepository.save(any(Cliente.class)))
                .thenReturn(nuevo);

        Cliente resultado = clienteService.create(nuevo);

        assertThat(resultado.getFechaAlta()).isEqualTo(LocalDate.now());
        assertThat(resultado.getStatus()).isEqualTo(1);
        assertThat(nuevo.getClienteId()).isNull();
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    @DisplayName("create - debe asignar estado cuando viene estado_id valido")
    void create_debeAsignarEstado() {
        Cliente nuevo = new Cliente();
        nuevo.setNombre("Carlos");
        nuevo.setApPaterno("Rodriguez");

        Estado estadoRef = new Estado();
        estadoRef.setEstadoId(1L);
        nuevo.setEstado(estadoRef);

        when(estadoRepository.findById(1L))
                .thenReturn(Optional.of(estadoMock));
        when(clienteRepository.save(any(Cliente.class)))
                .thenReturn(nuevo);

        Cliente resultado = clienteService.create(nuevo);

        assertThat(resultado.getEstado()).isNotNull();
        verify(estadoRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    @DisplayName("create - debe lanzar excepcion cuando estado_id no existe")
    void create_estadoInvalido_lanzaExcepcion() {
        Cliente nuevo = new Cliente();
        nuevo.setNombre("Carlos");
        nuevo.setApPaterno("Rodriguez");

        Estado estadoRef = new Estado();
        estadoRef.setEstadoId(99L);
        nuevo.setEstado(estadoRef);

        when(estadoRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> clienteService.create(nuevo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Estado no encontrado con id: 99");

        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    // ── update ──────────────────────────────────────────────────

    @Test
    @DisplayName("update - debe actualizar campos cuando cliente existe")
    void update_cuandoExiste_actualizaCampos() {
        Cliente datosNuevos = new Cliente();
        datosNuevos.setNombre("Juan Carlos");
        datosNuevos.setApPaterno("Garcia");
        datosNuevos.setApMaterno("Lopez Actualizado");
        datosNuevos.setStatus(2);

        when(clienteRepository.findById(1L))
                .thenReturn(Optional.of(clienteMock));
        when(clienteRepository.save(any(Cliente.class)))
                .thenReturn(clienteMock);

        Optional<Cliente> resultado = clienteService.update(1L, datosNuevos);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Juan Carlos");
        assertThat(resultado.get().getApMaterno())
                .isEqualTo("Lopez Actualizado");
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    @DisplayName("update - debe retornar empty cuando cliente no existe")
    void update_cuandoNoExiste_retornaEmpty() {
        when(clienteRepository.findById(99L))
                .thenReturn(Optional.empty());

        Optional<Cliente> resultado = clienteService
                .update(99L, new Cliente());

        assertThat(resultado).isEmpty();
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    // ── delete ──────────────────────────────────────────────────

    @Test
    @DisplayName("delete - debe retornar true cuando cliente existe")
    void delete_cuandoExiste_retornaTrue() {
        when(clienteRepository.findById(1L))
                .thenReturn(Optional.of(clienteMock));
        doNothing().when(clienteRepository)
                .delete(any(Cliente.class));

        boolean resultado = clienteService.delete(1L);

        assertThat(resultado).isTrue();
        verify(clienteRepository, times(1)).delete(clienteMock);
    }

    @Test
    @DisplayName("delete - debe retornar false cuando cliente no existe")
    void delete_cuandoNoExiste_retornaFalse() {
        when(clienteRepository.findById(99L))
                .thenReturn(Optional.empty());

        boolean resultado = clienteService.delete(99L);

        assertThat(resultado).isFalse();
        verify(clienteRepository, never()).delete(any(Cliente.class));
    }
}