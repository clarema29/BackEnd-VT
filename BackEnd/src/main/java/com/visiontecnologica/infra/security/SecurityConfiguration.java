package com.visiontecnologica.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/").permitAll()
                        .requestMatchers(HttpMethod.POST, "v1/api/login", "v1/api/usuario/create").permitAll()
                        .requestMatchers(HttpMethod.POST, "v1/api/login", "v1/api/clientes/create").permitAll()
                        .requestMatchers(HttpMethod.POST, "v1/api/productos/**").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "v1/api/productos/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "v1/api/productos/**").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "v1/api/clientes/**").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "v1/api/clientes/**").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "v1/api/clientes/**").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "v1/api/clientes/{id}").hasRole("CLIENTE")
                        .requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        // Añadir restricciones para listaClientes.html y listaProductos.html
                        .requestMatchers("/listaClientes.html").hasRole("USER")
                        .requestMatchers("/listaProuctos.html").hasRole("USER")

                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
