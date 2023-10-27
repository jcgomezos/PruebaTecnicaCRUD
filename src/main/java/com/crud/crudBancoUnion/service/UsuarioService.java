package com.crud.crudBancoUnion.service;

import com.crud.crudBancoUnion.model.Usuario;
import com.crud.crudBancoUnion.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Iterable<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    public void eliminarUsuarioPorId(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario actualizarUsuario(Usuario usuario) throws Exception {
        if (usuario.getId() != null && usuarioRepository.existsById(usuario.getId())) {
            return usuarioRepository.save(usuario);
        } else {
            throw new Exception("El usuario con el ID proporcionado no existe");
        }
    }
}
