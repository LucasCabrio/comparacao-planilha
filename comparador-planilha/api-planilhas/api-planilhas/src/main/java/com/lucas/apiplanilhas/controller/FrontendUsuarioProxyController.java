package com.lucas.apiplanilhas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.apiplanilhas.dto.UsuarioCadastroDTO;
import com.lucas.apiplanilhas.dto.UsuarioDTO;
import com.lucas.apiplanilhas.service.UsuarioClientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/frontend-api/usuarios")
@RequiredArgsConstructor
public class FrontendUsuarioProxyController {

    private final UsuarioClientService usuarioClientService;

    @GetMapping
    public List<UsuarioDTO> listar() {
        return usuarioClientService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public UsuarioDTO buscarPorId(@PathVariable String id) {
        return usuarioClientService.buscarUsuarioPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO salvar(@RequestBody UsuarioCadastroDTO usuario) {
        return usuarioClientService.cadastrarUsuario(usuario);
    }
}
