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

import com.lucas.apiplanilhas.model.ComparacaoPlanilha;
import com.lucas.apiplanilhas.service.ComparacaoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/comparacoes")
@RequiredArgsConstructor
public class ComparacaoController {

    private final ComparacaoService comparacaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComparacaoPlanilha criarComparacao(@RequestBody ComparacaoPlanilha comparacao) {
        return comparacaoService.criarComparacao(comparacao);
    }

    @GetMapping
    public List<ComparacaoPlanilha> listarComparacoes() {
        return comparacaoService.listarComparacoes();
    }

    @GetMapping("/{id}")
    public ComparacaoPlanilha buscarComparacao(@PathVariable String id) {
        return comparacaoService.buscarComparacao(id);
    }
}
