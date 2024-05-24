package com.visiontecnologica.controller;

import com.visiontecnologica.infra.security.DatosJWTToken;
import com.visiontecnologica.infra.security.TokenService;
import com.visiontecnologica.model.cliente.Cliente;
import com.visiontecnologica.model.usuario.DatosAutenticacionUsuario;
import com.visiontecnologica.model.usuario.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/login")
@RequiredArgsConstructor
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping()
    public ResponseEntity<DatosJWTToken> autenticarEntidad(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.email(), datosAutenticacionUsuario.clave());
        var entidadAutenticada = authenticationManager.authenticate(authenticationToken).getPrincipal();

        if (entidadAutenticada instanceof Usuario) {
            var jwtToken = tokenService.generarToken((Usuario) entidadAutenticada);
            return ResponseEntity.ok(new DatosJWTToken(jwtToken));
        } else if (entidadAutenticada instanceof Cliente) {
            var jwtToken = tokenService.generarToken((Cliente) entidadAutenticada);
            return ResponseEntity.ok(new DatosJWTToken(jwtToken));
        } else {
            throw new IllegalStateException("El principal autenticado no es ni un usuario ni un cliente.");
        }
    }

    /*@PostMapping()
    public ResponseEntity<DatosJWTToken> autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.email(), datosAutenticacionUsuario.clave());
        var usuarioAutenticado = authenticationManager.authenticate(authenticationToken);
        var jwtToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTToken(jwtToken));
    }*/
}
