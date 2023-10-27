package com.crud.crudBancoUnion.controller;

import com.crud.crudBancoUnion.model.Usuario;
import com.crud.crudBancoUnion.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.guardarUsuario(usuario);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.obtenerUsuarioPorId(id);
        return usuario.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<Iterable<Usuario>> obtenerTodosLosUsuarios() {
        Iterable<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuarioPorId(@PathVariable Long id) {
        usuarioService.eliminarUsuarioPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        if (!id.equals(usuario.getId())) {
            return new ResponseEntity<>("El ID de usuario no coincide con el ID proporcionado en la ruta", HttpStatus.BAD_REQUEST);
        }

        try {
            Usuario usuarioActualizado = usuarioService.actualizarUsuario(usuario);
            if (usuarioActualizado != null) {
                return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("El usuario con el ID proporcionado no existe", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
