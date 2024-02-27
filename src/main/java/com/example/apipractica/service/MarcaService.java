package com.example.apipractica.service;

import com.example.apipractica.exceptions.MarcaNotFoundException;
import com.example.apipractica.model.Marca;
import com.example.apipractica.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarcaService {
    @Autowired
    private MarcaRepository MarcaRepository;

    public List<Marca> getAllModelos() {
        return MarcaRepository.findAll();
    }

    public Optional<Marca> getMarcaById(Long id) {
        return Optional.ofNullable(MarcaRepository.findById(id).orElseThrow(
                () -> new MarcaNotFoundException("No se ha encontrado la marca con id: " + id)
        ));

    }
}
