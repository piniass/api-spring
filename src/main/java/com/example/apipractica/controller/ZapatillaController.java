package com.example.apipractica.controller;

import com.example.apipractica.exceptions.ZapatillaNotFoundException;
import com.example.apipractica.model.Marca;
import com.example.apipractica.model.Zapatilla;
import com.example.apipractica.service.ZapatillaService;
import com.example.apipractica.repository.ZapatillaRepository;
import com.example.apipractica.service.MarcaService;
import com.example.apipractica.util.ImageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")

public class ZapatillaController {
    @Autowired
    private ZapatillaService ZapatillaService;

    @Autowired
    private MarcaService marcaService;

    @Operation(summary = "Obtiene todas las zapatillas", description = "Obtiene una lista de Modelos de Zapatilla", tags = {"zapatillas"})
    @ApiResponse(responseCode = "200", description = "Lista de Zapatillas")
    @GetMapping("/zapatilla")
    public List<Zapatilla> getAllZapatillas() {
        return ZapatillaService.getAllModelos();
    }


    @Operation(summary = "Obtiene todas las zapatillas ordenadas por precio", description = "Obtiene una lista de Modelos de Zapatilla ordenados", tags = {"zapatillas"})
    @ApiResponse(responseCode = "200", description = "Lista Ordenada de Zapatillas")
    @GetMapping("/ordered")
    public ResponseEntity<List<Zapatilla>> getZapatillasOrderedByPriceAsc() {
        List<Zapatilla> zapatillas = ZapatillaService.getAllZapatillasOrderedByPriceAsc();
        return new ResponseEntity<>(zapatillas, HttpStatus.OK);
    }

    @Operation(summary = "Crea un modelo de zapatilla", description = "Añade un modelo de zapatilla a la colección", tags = {"zapatillas"})
    @ApiResponse(responseCode = "201", description = "Zapatilla añadida")
    @ApiResponse(responseCode = "400", description = "Datos de zapatilla no válidos")
    @PostMapping(value = "/zapatilla", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Zapatilla> createZapatilla(@RequestParam String nombre,
                                                     @RequestParam Integer precio,
                                                     @RequestParam String color,
                                                     @RequestParam Long idMarca,
                                                     @RequestPart(name = "imagen", required = false) MultipartFile imagen) throws IOException {
        Marca marca = new Marca();
        Optional<Marca> optionalMarca = marcaService.getMarcaById(idMarca);
        if (optionalMarca.isPresent()) {
            marca = optionalMarca.get();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Crear la zapatilla con la marca existente y la fecha de creación
        Zapatilla createdModelo = ZapatillaService.createModelo(new Zapatilla(nombre, precio, color, marca), imagen);

        return new ResponseEntity<>(createdModelo, HttpStatus.CREATED);
    }



    @Operation(summary = "Obtiene una Zapatilla", description = "Obtiene una zapatilla dado su id", tags = {"zapatillas"})
    @Parameter(name = "id", description = "ID de la Zapatilla", required = true, example = "1")
    @ApiResponse(responseCode = "200", description = "Zapatilla encontrada")
    @ApiResponse(responseCode = "404", description = "Zapatilla no encontrada")
    @GetMapping("/zapatilla/{id}")
    public ResponseEntity<Zapatilla> getModeloById(@PathVariable Long id) {
        Optional<Zapatilla> optionalZapatilla = ZapatillaService.getModeloById(id);
        if (((Optional<?>) optionalZapatilla).isPresent()) {
            optionalZapatilla = ZapatillaService.getModeloById(id);
            return new ResponseEntity<>(optionalZapatilla.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Actualiza una Zapatilla", description = "Actualiza una zapatilla dado su id", tags = {"zapatillas"})
    @Parameter(name = "id", description = "ID de la Zapatilla", required = true, example = "1")
    @ApiResponse(responseCode = "200", description = "Zapatilla encontrada")
    @ApiResponse(responseCode = "404", description = "Zapatilla no encontrada")
    @PutMapping(value = "/zapatilla/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Zapatilla> updateModelo(@PathVariable Long id,
                                                  @RequestParam String nombre,
                                                  @RequestParam Integer precio,
                                                  @RequestParam String color,
                                                  @RequestParam Long idMarca,
                                                  @RequestPart(name="imagen", required = false) MultipartFile imagen) throws IOException {
        Optional<Zapatilla> optionalZapatilla = ZapatillaService.getModeloById(id);

        Marca marca = new Marca();
        Optional<Marca> optionalMarca = marcaService.getMarcaById(idMarca);
        if (optionalMarca.isPresent()) {
            marca = optionalMarca.get();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (optionalZapatilla.isPresent()) {
            Zapatilla existingZapatilla = optionalZapatilla.get();
            existingZapatilla.setNombre(nombre);
            existingZapatilla.setPrecio(precio);
            existingZapatilla.setColor(color);
            existingZapatilla.setMarca(marca);
            existingZapatilla.setUpdated_at(LocalDateTime.now());

            Zapatilla updatedModelo = ZapatillaService.updateModelo(existingZapatilla, imagen);
            return new ResponseEntity<>(updatedModelo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Elimina una Zapatilla", description = "Elimina una zapatilla dado su id", tags = {"zapatillas"})
    @Parameter(name = "id", description = "ID de la Zapatilla", required = true, example = "1")
    @ApiResponse(responseCode = "200", description = "Zapatilla encontrada")
    @ApiResponse(responseCode = "404", description = "Zapatilla no encontrada")
    @DeleteMapping("/zapatilla/{id}")
    public ResponseEntity<Void> deleteModelo(@PathVariable Long id) {
        Optional<Zapatilla> optionalZapatilla = ZapatillaService.getModeloById(id);
        if (optionalZapatilla.isPresent()) {
            ZapatillaService.deleteModeloById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Muestra foto", description = "Obtiene foto de una zapatilla dado el id", tags = {"zapatillas"})
    @Parameter(name = "id", description = "ID de la zapatilla", required = true,
            example = "13")
    @ApiResponse(responseCode = "200", description = "Foto de la zapatilla")
    @ApiResponse(responseCode = "404", description = "zapatilla no encontrada")
    @GetMapping(value = "/{id}/foto", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> descargarFoto(@PathVariable Long id) {
        byte[] foto = ZapatillaService.descargarFoto(id);
        if ( foto != null ) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(foto);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/zapatillasview")
    public ModelAndView listado(Zapatilla zapatilla) throws UnsupportedEncodingException {
        List<Zapatilla> zapatillas = getAllZapatillas();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("listaZapatillas", zapatillas);
        modelAndView.setViewName("listado.html");
        return modelAndView;
    }


}
