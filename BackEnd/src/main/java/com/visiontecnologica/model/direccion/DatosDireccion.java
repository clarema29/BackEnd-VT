package com.visiontecnologica.model.direccion;

import jakarta.validation.constraints.NotBlank;

public record  DatosDireccion(
        @NotBlank
        String direccion,
        @NotBlank
        String departamento,
        @NotBlank
        String ciudad
       ) {
}
