package com.visiontecnologica.controller;

import com.visiontecnologica.model.cliente.Cliente;
import com.visiontecnologica.model.cliente.DatosRegistroCliente;
import com.visiontecnologica.model.cliente.DatosRespuestaCliente;
import com.visiontecnologica.model.direccion.DatosDireccion;
import com.visiontecnologica.model.producto.Producto;
import com.visiontecnologica.repository.ClienteRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable Long id){
        Cliente cliente= clienteRepository.findById(String.valueOf(id)).orElseThrow();
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/all")
    @Operation(summary = "Obtiene todos los clientes registrados")
    public ResponseEntity<List<DatosRespuestaCliente>> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();

        List<DatosRespuestaCliente> datosRespuestaClientes = clientes.stream()
                .map(cliente -> new DatosRespuestaCliente(
                        cliente.getId(),
                        cliente.getNombre(),
                        cliente.getEmail(),
                        cliente.getClave(),
                        cliente.getTelefono(),
                        cliente.getDocumento(),
                        new DatosDireccion(
                                cliente.getDireccion().getDireccion(),
                                cliente.getDireccion().getDepartamento(),
                                cliente.getDireccion().getCiudad()
                        )
                )).collect(Collectors.toList());

        return ResponseEntity.ok(datosRespuestaClientes);
    }

    @PostMapping("/create")
    @Transactional
    @Operation(summary = "Registra un nuevo cliente")
    public ResponseEntity<DatosRespuestaCliente> registrarCliente(@RequestBody @Valid DatosRegistroCliente datosRegistroCliente,
                                                                  UriComponentsBuilder uriComponentsBuilder) {
        // Encriptar la contraseña del cliente antes de guardarla en la base de datos
        String claveEncriptada = passwordEncoder.encode(datosRegistroCliente.clave());

        Cliente cliente = new Cliente(datosRegistroCliente); // Crear instancia de Cliente con datosRegistroCliente
        cliente.setClave(claveEncriptada); // Establecer la contraseña encriptada

        cliente = clienteRepository.save(cliente); // Guardar cliente en la base de datos

        DatosRespuestaCliente datosRespuestaCliente = new DatosRespuestaCliente(cliente.getId(),
                cliente.getNombre(), cliente.getEmail(), cliente.getClave(), cliente.getTelefono(),
                cliente.getDocumento(), new DatosDireccion(cliente.getDireccion().getDireccion(),
                cliente.getDireccion().getDepartamento(), cliente.getDireccion().getCiudad()));
        URI url = uriComponentsBuilder.path("/clientes/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaCliente);
    }

    @PutMapping("/update/{id}")
    @Transactional
    @Operation(summary = "Actualiza los datos de un cliente existente")
    public ResponseEntity<DatosRespuestaCliente> actualizarCliente(@PathVariable Long id,
                                                                   @RequestBody @Valid DatosRegistroCliente datosRegistroCliente) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(String.valueOf(id));

        if (!clienteOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Cliente cliente = clienteOpt.get();

        // Actualizar los datos del cliente
        cliente.setNombre(datosRegistroCliente.nombre());
        cliente.setEmail(datosRegistroCliente.email());

        // Encriptar la nueva contraseña si ha sido proporcionada
        if (datosRegistroCliente.clave() != null && !datosRegistroCliente.clave().isEmpty()) {
            String claveEncriptada = passwordEncoder.encode(datosRegistroCliente.clave());
            cliente.setClave(claveEncriptada);
        }

        cliente.setTelefono(datosRegistroCliente.telefono());
        cliente.setDocumento(datosRegistroCliente.documento());
        cliente.getDireccion().setDireccion(datosRegistroCliente.direccion().direccion());
        cliente.getDireccion().setDepartamento(datosRegistroCliente.direccion().departamento());
        cliente.getDireccion().setCiudad(datosRegistroCliente.direccion().ciudad());

        cliente = clienteRepository.save(cliente); // Guardar los cambios en la base de datos

        DatosRespuestaCliente datosRespuestaCliente = new DatosRespuestaCliente(cliente.getId(),
                cliente.getNombre(), cliente.getEmail(), cliente.getClave(), cliente.getTelefono(),
                cliente.getDocumento(), new DatosDireccion(cliente.getDireccion().getDireccion(),
                cliente.getDireccion().getDepartamento(), cliente.getDireccion().getCiudad()));

        return ResponseEntity.ok(datosRespuestaCliente);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id){
        clienteRepository.deleteById(String.valueOf(id));
        return ResponseEntity.noContent().build();
    }




   /* @PostMapping("/create")
    @Transactional
    @Operation(summary = "Registra un nuevo cliente")
    public ResponseEntity<DatosRespuestaCliente> registrarCliente(@RequestBody @Valid DatosRegistroCliente datosRegistroCliente,
                                                                  UriComponentsBuilder uriComponentsBuilder){
        String claveEncriptada = passwordEncoder.encode(datosRegistroCliente.clave());
        Cliente cliente = clienteRepository.save(new Cliente(datosRegistroCliente));
        DatosRespuestaCliente datosRespuestaCliente = new DatosRespuestaCliente(cliente.getId(),
                cliente.getNombre(), cliente.getEmail(), cliente.getClave(), cliente.getTelefono(),
                cliente.getDocumento(), new DatosDireccion(cliente.getDireccion().getCalle(),
                cliente.getDireccion().getDistrito(), cliente.getDireccion().getCiudad(),
                cliente.getDireccion().getNumero(), cliente.getDireccion().getComplemento()));
        URI url = uriComponentsBuilder.path("/clientes/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaCliente);

    }*/
}
