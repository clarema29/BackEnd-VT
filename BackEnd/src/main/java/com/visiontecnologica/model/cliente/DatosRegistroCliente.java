package com.visiontecnologica.model.cliente;

import com.visiontecnologica.model.direccion.DatosDireccion;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DatosRegistroCliente (
        @NotBlank String nombre,
        @NotBlank @Email String email,
        @NotBlank String clave,
        @NotBlank String telefono,
        @NotBlank @Pattern(regexp = "\\d{6,10}") String documento,
        @NotNull @Valid DatosDireccion direccion
){
}
