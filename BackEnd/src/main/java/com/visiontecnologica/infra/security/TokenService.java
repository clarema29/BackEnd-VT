package com.visiontecnologica.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.visiontecnologica.model.cliente.Cliente;
import com.visiontecnologica.model.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    public String generarToken(Object entidad) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            JWTCreator.Builder jwtBuilder = JWT.create().withIssuer("voll med");

            if (entidad instanceof Usuario) {
                Usuario usuario = (Usuario) entidad;
                return jwtBuilder
                        .withSubject(usuario.getEmail()) // Sujeto del token: correo electrónico del usuario
                        .withClaim("usuario_id", usuario.getId()) // Reclamo personalizado: ID del usuario
                        .withClaim("authorities", obtenerRoles(usuario)) // Agregar roles como reclamo "authorities"
                        .withExpiresAt(generarFechaExpiracion())
                        .sign(algorithm);
            } else if (entidad instanceof Cliente) {
                Cliente cliente = (Cliente) entidad;
                return jwtBuilder
                        .withSubject(cliente.getEmail()) // Sujeto del token: correo electrónico del cliente
                        .withClaim("cliente_id", cliente.getId()) // Reclamo personalizado: ID del cliente
                        .withClaim("nombre", cliente.getNombre()) // Reclamo personalizado: Nombre del cliente
                        .withClaim("authorities", obtenerRoles(cliente)) // Agregar roles como reclamo "authorities"
                        .withExpiresAt(generarFechaExpiracion())
                        .sign(algorithm);
            } else {
                throw new IllegalArgumentException("Tipo de entidad no compatible para generar token.");
            }
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }

    // Método para obtener los roles de un usuario o cliente
    private List<String> obtenerRoles(Object entidad) {
        if (entidad instanceof Usuario) {
            Usuario usuario = (Usuario) entidad;
            return usuario.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
        } else if (entidad instanceof Cliente) {
            Cliente cliente = (Cliente) entidad;
            return cliente.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Tipo de entidad no compatible para obtener roles.");
        }
    }

    public boolean hasRole(String token, String role) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getClaim("authorities").asList(String.class).contains(role);
        } catch (JWTDecodeException exception) {
            return false;
        }
    }




    /*public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("voll med")
                    .withSubject(usuario.getEmail())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }

    }*/

    public String getSubject(String token){
        if(token == null){
            throw new RuntimeException();
        }
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                    .withIssuer("voll med")
                    .build()
                    .verify(token);
            verifier.getSubject();
        } catch (JWTCreationException exception){
            System.out.println(exception.toString());
            throw new RuntimeException();
        }
        if(verifier.getSubject() == null){
            throw new RuntimeException("Verifier invalido");
        }
        return verifier.getSubject();
    }

    private Instant generarFechaExpiracion(){
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-05:00"));
    }
}
