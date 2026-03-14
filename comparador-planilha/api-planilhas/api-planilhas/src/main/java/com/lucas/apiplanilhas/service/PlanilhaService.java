package com.lucas.apiplanilhas.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lucas.apiplanilhas.model.Planilha;
import com.lucas.apiplanilhas.repository.PlanilhaRepository;

import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PlanilhaService {

    private final PlanilhaRepository planilhaRepository;
    private final UsuarioClientService usuarioClientService;

    public Planilha salvar(Planilha planilha) {
        if (usuarioClientService.buscarUsuarioPorId(planilha.getUsuarioId()) == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        return planilhaRepository.save(planilha);
    }

    public List<Planilha> listar() {
        return planilhaRepository.findAll();
    }

    public Planilha buscarPorId(String id) {
        return planilhaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Planilha nao encontrada"));
    }
}
