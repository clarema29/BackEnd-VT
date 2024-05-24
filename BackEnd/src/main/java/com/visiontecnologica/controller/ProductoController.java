package com.visiontecnologica.controller;

import com.visiontecnologica.model.producto.Producto;
import com.visiontecnologica.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos(){
        return ResponseEntity.ok(productoRepository.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id){
        Producto producto = productoRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(producto);
    }
    @PostMapping("/create")
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto){
        System.out.println("Producto a guardar " + producto.toString());
        Producto productoSalvo = productoRepository.save(producto);
        System.out.println("Producto guardado " + producto.toString());
        return ResponseEntity.ok(productoSalvo);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Producto> actualizarProducto(@RequestBody Producto producto, @PathVariable Long id){
        Producto productoActualizado = productoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("El producto con id " + id + " no existe"));
        productoActualizado.setNombre(producto.getNombre());
        productoActualizado.setPrecio(producto.getPrecio());
        productoActualizado.setDescripcion(producto.getDescripcion());
        productoActualizado.setCantidad(producto.getCantidad());
        productoActualizado.setImagen(producto.getImagen());
        productoActualizado.setCategoriaEnum(producto.getCategoriaEnum());
        productoActualizado.setEnOferta(producto.isEnOferta());
        productoActualizado = productoRepository.save(productoActualizado);
        return ResponseEntity.ok(productoActualizado);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id){
        productoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
