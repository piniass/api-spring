package com.example.apipractica.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "marca")

public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombre", nullable = false, length = 50)
    private String nombre;

    @OneToMany(mappedBy = "marca", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Zapatilla> zapatillas;
    public Marca() {
    }
    public Marca(String nombre) {
        this.nombre = nombre;
    }

    public Marca(Long idMarca, String nombre) {
        this.id = idMarca;
        this.nombre = nombre;
    }
}
