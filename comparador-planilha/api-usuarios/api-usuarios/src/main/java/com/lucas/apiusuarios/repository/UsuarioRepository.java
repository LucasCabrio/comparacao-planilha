package com.lucas.apiusuarios.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lucas.apiusuarios.model.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
}
