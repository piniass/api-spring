package com.example.apipractica.repository;

import com.example.apipractica.model.Zapatilla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ZapatillaRepository extends JpaRepository<Zapatilla, Long> {
    List<Zapatilla> findAllByOrderByPrecioAsc();

}