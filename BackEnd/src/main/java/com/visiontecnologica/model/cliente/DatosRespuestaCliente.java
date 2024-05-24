package com.visiontecnologica.model.cliente;

import com.visiontecnologica.model.direccion.DatosDireccion;

public record DatosRespuestaCliente(
        Long id,
        String nombre,
        String email,
        String clave,
        String telefono,
        String documento,
        DatosDireccion direccion
) {

}
