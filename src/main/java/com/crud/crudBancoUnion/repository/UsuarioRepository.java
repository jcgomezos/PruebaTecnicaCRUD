package com.crud.crudBancoUnion.repository;

import com.crud.crudBancoUnion.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    // Aquí puedes agregar métodos personalizados de consulta si los necesitas
}
