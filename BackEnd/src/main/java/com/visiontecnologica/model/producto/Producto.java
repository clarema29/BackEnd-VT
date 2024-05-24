package com.visiontecnologica.model.producto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "producto")
@Entity(name = "Producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String nombre;
    private String descripcion;
    private  int cantidad;
    private double precio;
    private String imagen;
    @Enumerated(EnumType.STRING)
    private ProductosCategoriaEnum categoriaEnum;
    private boolean enOferta;

}
