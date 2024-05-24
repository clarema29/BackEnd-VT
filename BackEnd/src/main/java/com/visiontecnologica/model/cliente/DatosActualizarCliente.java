package com.visiontecnologica.model.cliente;

import com.visiontecnologica.model.direccion.DatosDireccion;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarCliente(
        @NotNull
        Long id,
        String nombre,
        String documento,
        String clave,
        DatosDireccion direccion
) {
}
