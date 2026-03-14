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

import com.lucas.apiplanilhas.model.Planilha;
import com.lucas.apiplanilhas.service.PlanilhaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/planilhas")
@RequiredArgsConstructor
public class PlanilhaController {

    private final PlanilhaService planilhaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Planilha salvar(@RequestBody Planilha planilha) {
        return planilhaService.salvar(planilha);
    }

    @GetMapping
    public List<Planilha> listar() {
        return planilhaService.listar();
    }

    @GetMapping("/{id}")
    public Planilha buscarPorId(@PathVariable String id) {
        return planilhaService.buscarPorId(id);
    }
}
