package com.lucas.apiplanilhas.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.lucas.apiplanilhas.dto.UsuarioDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioClientService {

    private final RestTemplate restTemplate;

    public UsuarioDTO buscarUsuarioPorId(String id) {
        try {
            return restTemplate.getForObject("http://localhost:8080/usuarios/{id}", UsuarioDTO.class, id);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new RuntimeException("Usuário não encontrado");
        }
    }
}
