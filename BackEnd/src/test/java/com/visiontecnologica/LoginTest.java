package com.visiontecnologica;

import com.visiontecnologica.model.usuario.DatosAutenticacionUsuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testSuccessfulLogin() {

        DatosAutenticacionUsuario loginRequest = new DatosAutenticacionUsuario("admin@mail.com", "123456");

        ResponseEntity<String> response = restTemplate.postForEntity("/v1/api/login", loginRequest, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testUnsuccessfulLogin() {
        DatosAutenticacionUsuario loginRequest = new DatosAutenticacionUsuario("admini@gmail.com", "255555");

        ResponseEntity<String> response = restTemplate.postForEntity("/v1/api/login", loginRequest, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}