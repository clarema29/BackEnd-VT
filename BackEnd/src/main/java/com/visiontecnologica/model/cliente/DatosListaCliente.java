package com.visiontecnologica.model.cliente;

import com.visiontecnologica.model.direccion.DatosDireccion;

public record DatosListaCliente(
        Long id,
        String nombre,
        String email,
        String clave,
        String documento
) {
    public DatosListaCliente(Cliente cliente){
        this(cliente.getId(), cliente.getNombre(), cliente.getEmail(), cliente.getClave(), cliente.getDocumento());
    }
}
