package com.prueba.crud.controller;

import com.prueba.crud.model.Estado;
import com.prueba.crud.service.EstadoService;
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

/**
 * Controller REST para Estados.
 * Swagger describe cada endpoint para la documentacion automatica.
 */
@RestController
@RequestMapping("/api/estados")
@Tag(name = "Estados", description = "CRUD del catalogo de estados")
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    // ── GET /api/estados ────────────────────────────────────────
    @GetMapping
    @Operation(summary = "Listar todos los estados")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<Estado>> findAll() {
        return ResponseEntity.ok(estadoService.findAll());
    }

    // ── GET /api/estados/{id} ───────────────────────────────────
    @GetMapping("/{id}")
    @Operation(summary = "Obtener estado por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado encontrado"),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado")
    })
    public ResponseEntity<Estado> findById(
            @Parameter(description = "ID del estado")
            @PathVariable Long id) {

        return estadoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ── POST /api/estados ───────────────────────────────────────
    @PostMapping
    @Operation(summary = "Crear nuevo estado")
    @ApiResponse(responseCode = "201", description = "Estado creado correctamente")
    public ResponseEntity<Estado> create(@RequestBody Estado estado) {
        Estado nuevo = estadoService.create(estado);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // ── PUT /api/estados/{id} ───────────────────────────────────
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar estado existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado"),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado")
    })
    public ResponseEntity<Estado> update(
            @PathVariable Long id,
            @RequestBody Estado estado) {

        return estadoService.update(id, estado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ── DELETE /api/estados/{id} ────────────────────────────────
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar estado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estado eliminado"),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return estadoService.delete(id)
                ? ResponseEntity.noContent().<Void>build()
                : ResponseEntity.notFound().build();
    }
}