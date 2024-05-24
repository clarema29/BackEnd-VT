package com.visiontecnologica.infra.security;



import com.visiontecnologica.repository.ClienteRepository;
import com.visiontecnologica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = usuarioRepository.findByEmail(username);
        if (userDetails == null) {
            userDetails = clienteRepository.findByEmail(username);
        }
        if (userDetails == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return userDetails;
    }

}
