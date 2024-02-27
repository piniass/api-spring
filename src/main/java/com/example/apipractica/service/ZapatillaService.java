package com.example.apipractica.service;

import com.example.apipractica.exceptions.ZapatillaBadRequestException;
import com.example.apipractica.exceptions.ZapatillaException;
import com.example.apipractica.exceptions.ZapatillaNotFoundException;
import com.example.apipractica.model.Zapatilla;
import com.example.apipractica.repository.ZapatillaRepository;
import com.example.apipractica.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Date;

@Service
public class ZapatillaService {
    @Autowired
    private ZapatillaRepository zapatillaRepository;

    public List<Zapatilla> getAllModelos() {
        return zapatillaRepository.findAll();
    }

    public Zapatilla createModelo(Zapatilla zapatilla, MultipartFile file) throws IOException {

        if (zapatilla.getNombre() == null || zapatilla.getNombre().isEmpty())
            throw new ZapatillaBadRequestException("Debe introducirse el nombre");
        if (zapatilla.getPrecio() == null || zapatilla.getPrecio() <= 0)
            throw new ZapatillaBadRequestException("Debe introducirse un precio o uno superior a 0");
        if (zapatilla.getColor() == null || zapatilla.getColor().isEmpty())
            throw new ZapatillaBadRequestException("Debe introducirse el color");

        Zapatilla zapatillasave = new Zapatilla(zapatilla.getNombre(), zapatilla.getPrecio(), zapatilla.getColor(), zapatilla.getMarca());
        if (!file.isEmpty()) {
            zapatillasave.setImagen(file.getOriginalFilename());
            zapatillasave.setFoto(ImageUtils.compressImage(file.getBytes())); // Almacenaen BD el binario de la foto
            /*Path dirImg = Paths.get("src//main//resources//static//img");
            String rutaAbsoluta = dirImg.toFile().getAbsolutePath();
            try {
                byte[] bytesImg = file.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + file.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
            } catch (IOException e) {
                throw new ZapatillaException("Error de escritura");
            }*/
        } else
            throw new ZapatillaBadRequestException("Debe introducirse el fichero imagen");

        return zapatillaRepository.save(zapatillasave);
    }

    public Optional<Zapatilla> getModeloById(Long id) {
        return Optional.ofNullable(zapatillaRepository.findById(id).orElseThrow(
                () -> new ZapatillaNotFoundException("No se ha encontrado la persona con id: " + id)
        ));

    }

    public Zapatilla updateModelo(Zapatilla zapatilla, MultipartFile file) throws IOException {
        if (zapatilla.getNombre() == null || zapatilla.getNombre().isEmpty())
            throw new ZapatillaBadRequestException("Debe introducirse el nombre");
        if (zapatilla.getPrecio() == null || zapatilla.getPrecio() <= 0)
            throw new ZapatillaBadRequestException("Debe introducirse un precio o uno superior a 0");
        if (zapatilla.getColor() == null || zapatilla.getColor().isEmpty())
            throw new ZapatillaBadRequestException("Debe introducirse el color");

        if (!file.isEmpty()) {
            zapatilla.setImagen(file.getOriginalFilename());
            zapatilla.setFoto(ImageUtils.compressImage(file.getBytes())); // Almacena en BDel binario de la foto
            // El resto de líneas es para almacenar la imagen en disco
            Path dirImg = Paths.get("src//main//resources//static//img");
            String rutaAbsoluta = dirImg.toFile().getAbsolutePath();
            try {
                byte[] bytesImg = file.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" +
                        file.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
            } catch (IOException e) {
                throw new ZapatillaException("Error de escritura");
            }
        } else
            throw new ZapatillaBadRequestException("Debe introducirse el fichero imagen");
        return (Zapatilla) zapatillaRepository.save(zapatilla);
    }

    public void deleteModeloById(Long id) {
        zapatillaRepository.deleteById(id);

    }

    public List<Zapatilla> getAllZapatillasOrderedByPriceAsc() {
        return zapatillaRepository.findAllByOrderByPrecioAsc();
    }

    public byte[] descargarFoto(Long id) {
        Zapatilla zapatilla = zapatillaRepository.findById(id).orElse(null);
        return zapatilla != null ? ImageUtils.decompressImage(zapatilla.getFoto()) : null;
    }
}
