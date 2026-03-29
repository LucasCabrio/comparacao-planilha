package com.lucas.apiplanilhas.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.lucas.apiplanilhas.dto.UsuarioCadastroDTO;
import com.lucas.apiplanilhas.dto.UsuarioDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioClientService {

    private static final String USUARIOS_URL = "http://localhost:8080/usuarios";

    private final RestTemplate restTemplate;

    public UsuarioDTO buscarUsuarioPorId(String id) {
        try {
            return restTemplate.getForObject(USUARIOS_URL + "/{id}", UsuarioDTO.class, id);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new RuntimeException("Usuario nao encontrado");
        }
    }

    public UsuarioDTO cadastrarUsuario(UsuarioCadastroDTO usuario) {
        return restTemplate.postForObject(USUARIOS_URL, usuario, UsuarioDTO.class);
    }

    public List<UsuarioDTO> listarUsuarios() {
        return restTemplate.exchange(
                USUARIOS_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UsuarioDTO>>() {
                })
                .getBody();
    }
}
