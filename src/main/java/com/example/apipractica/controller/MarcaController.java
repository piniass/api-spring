package com.example.apipractica.controller;

import com.example.apipractica.model.Marca;
import com.example.apipractica.model.Zapatilla;
import com.example.apipractica.service.MarcaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MarcaController {
    @Autowired
    private MarcaService marcaService;

    @Operation(summary = "Obtiene todas las marcas", description = "Obtiene una lista de Modelos de Marcas", tags = {"marcas"})
    @ApiResponse(responseCode = "200", description = "Lista de Zapatillas")
    @GetMapping("/marca")
    public List<Marca> getAllModelos() {
        return marcaService.getAllModelos();
    }

    @Operation(summary = "Obtiene una Marca", description = "Obtiene una marca dado su id", tags = {"marcas"})
    @Parameter(name = "id", description = "ID de la marcas", required = true, example = "1")
    @ApiResponse(responseCode = "200", description = "Marca encontrada")
    @ApiResponse(responseCode = "404", description = "Marca no encontrada")
    @GetMapping("/marca/{id}")
    public ResponseEntity<Marca> getModeloById(@PathVariable Long id) {
        Optional<Marca> optionalMarca = marcaService.getMarcaById(id);
        if (((Optional<?>) optionalMarca).isPresent()) {
            optionalMarca = marcaService.getMarcaById(id);
            return new ResponseEntity<>(optionalMarca.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
