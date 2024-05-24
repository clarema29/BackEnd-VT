package com.visiontecnologica.controller;

import com.visiontecnologica.model.usuario.Usuario;
import com.visiontecnologica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("v1/api/usuario")
public class UsuarioController {

    @Autowired
    public UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/create")
    public ResponseEntity<Usuario> createUser(@RequestBody Usuario usuario){
        String passwordEncriptada = passwordEncoder.encode(usuario.getClave());
        usuario.setClave(passwordEncriptada);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuarioSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUser(@PathVariable Long id){
        Usuario usuario = usuarioRepository.findById(String.valueOf(id)).orElseThrow();
        return ResponseEntity.ok(usuario);
    }
}