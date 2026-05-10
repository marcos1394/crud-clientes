package com.prueba.crud.controller;

import com.prueba.crud.model.Cliente;
import com.prueba.crud.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "CRUD completo de clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // ── GET /api/clientes ───────────────────────────────────────
    @GetMapping
    @Operation(summary = "Listar todos los clientes")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<Cliente>> findAll() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    // ── GET /api/clientes/{id} ──────────────────────────────────
    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<Cliente> findById(
            @Parameter(description = "ID del cliente")
            @PathVariable Long id) {

        return clienteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ── POST /api/clientes ──────────────────────────────────────
    @PostMapping
    @Operation(
            summary = "Crear nuevo cliente",
            description = "Para asignar estado enviar: \"estado\": {\"estadoId\": 1}"
    )
    @ApiResponse(responseCode = "201", description = "Cliente creado")
    @ApiResponse(responseCode = "400", description = "Estado no encontrado")
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
        try {
            Cliente nuevo = clienteService.create(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ── PUT /api/clientes/{id} ──────────────────────────────────
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente actualizado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "400", description = "Estado no encontrado")
    })
    public ResponseEntity<Cliente> update(
            @PathVariable Long id,
            @RequestBody Cliente cliente) {
        try {
            return clienteService.update(id, cliente)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ── DELETE /api/clientes/{id} ───────────────────────────────
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente eliminado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return clienteService.delete(id)
                ? ResponseEntity.noContent().<Void>build()
                : ResponseEntity.notFound().build();
    }
}