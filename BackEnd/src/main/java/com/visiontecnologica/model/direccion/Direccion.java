package com.visiontecnologica.model.direccion;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {

    private String direccion;
    private String departamento;
    private String ciudad;

    public Direccion(DatosDireccion datosDireccion){
        this.direccion = datosDireccion.direccion();
        this.departamento = datosDireccion.departamento();
        this.ciudad = datosDireccion.ciudad();

    }

    public Direccion actualizarDatos(DatosDireccion datosDireccion) {
        this.direccion = datosDireccion.direccion();
        this.departamento = datosDireccion.departamento();
        this.ciudad = datosDireccion.ciudad();
        return this;
    }
}
