package com.visiontecnologica.model.cliente;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.visiontecnologica.model.direccion.Direccion;
import com.visiontecnologica.model.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Cliente")
@Table(name = "clientes")
public class Cliente implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    @Email
    private String email;
    private String clave;
    private String documento;
    private String telefono;
    private Boolean activo;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Usuario> usuarios;

    @Embedded
    private Direccion direccion;

    public Cliente(DatosRegistroCliente datos) {
        this.activo = true;
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.clave = datos.clave();
        this.telefono = datos.telefono();
        this.documento = datos.documento();
        this.direccion = new Direccion(datos.direccion());
    }

    public void actualizarDatos(DatosActualizarCliente datosActualizarCliente) {
        if(datosActualizarCliente.nombre() != null){
            this.nombre = datosActualizarCliente.nombre();
        }
        if(datosActualizarCliente.documento() != null){
            this.documento = datosActualizarCliente.documento();
        }
        if(datosActualizarCliente.direccion() != null){
            this.direccion = direccion.actualizarDatos(datosActualizarCliente.direccion());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"));
    }

    @Override
    public String getPassword() {
        return clave;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


