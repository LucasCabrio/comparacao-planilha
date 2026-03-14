package com.lucas.apiplanilhas.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lucas.apiplanilhas.model.ComparacaoPlanilha;
import com.lucas.apiplanilhas.repository.ComparacaoRepository;
import com.lucas.apiplanilhas.repository.PlanilhaRepository;

import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ComparacaoService {

    private final ComparacaoRepository comparacaoRepository;
    private final PlanilhaRepository planilhaRepository;

    public ComparacaoPlanilha criarComparacao(ComparacaoPlanilha comparacao) {
        var planilhaInicial = planilhaRepository.findById(comparacao.getPlanilhaInicialId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Planilha inicial nao encontrada"));

        var planilhaComparacao = planilhaRepository.findById(comparacao.getPlanilhaComparacaoId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Planilha de comparacao nao encontrada"));

        double maiorQtdLinhas = Math.max(planilhaInicial.getQtdLinhas(), planilhaComparacao.getQtdLinhas());
        double maiorQtdColunas = Math.max(planilhaInicial.getQtdColunas(), planilhaComparacao.getQtdColunas());

        double difLinhasPercent = maiorQtdLinhas == 0
                ? 0
                : Math.abs(planilhaInicial.getQtdLinhas() - planilhaComparacao.getQtdLinhas()) / maiorQtdLinhas;

        double difColunasPercent = maiorQtdColunas == 0
                ? 0
                : Math.abs(planilhaInicial.getQtdColunas() - planilhaComparacao.getQtdColunas()) / maiorQtdColunas;

        int score = (int) (100 - (((difLinhasPercent + difColunasPercent) / 2) * 100));

        comparacao.setScore(score);
        comparacao.setDataComparacao(LocalDateTime.now());

        return comparacaoRepository.save(comparacao);
    }

    public List<ComparacaoPlanilha> listarComparacoes() {
        return comparacaoRepository.findAll();
    }

    public ComparacaoPlanilha buscarComparacao(String id) {
        return comparacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Comparacao nao encontrada"));
    }
}
