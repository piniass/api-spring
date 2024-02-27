package com.example.apipractica.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "modelo")
public class Zapatilla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "precio", nullable = false)
    private Integer precio;

    @Column(name = "color", nullable = false, length = 50)
    private String color;

    @Column(name = "imagen", nullable = false, length = 100)
    private String imagen;

    @Column(name="created_at")
    private LocalDateTime created_at = LocalDateTime.now();

    @Column(name="updated_at")
    private LocalDateTime updated_at = LocalDateTime.now();

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "foto", columnDefinition = "longblob", nullable = true)
    private byte[] foto;

    @ManyToOne
    @JoinColumn(name = "id_marca", nullable = false)
    @JsonBackReference
    private Marca marca;

    public Zapatilla(){

    }

    public Zapatilla(String nombre, Integer precio, String color, Marca marca) {
        this.nombre = nombre;
        this.precio = precio;
        this.color = color;
        this.marca = marca;
    }


    public Zapatilla(String nombre, Integer precio, String color, String imagen, byte[] foto, Marca marca) {
        this.nombre = nombre;
        this.precio = precio;
        this.color = color;
        this.imagen = imagen;
        this.foto = foto;
        this.marca = marca;
    }
}


