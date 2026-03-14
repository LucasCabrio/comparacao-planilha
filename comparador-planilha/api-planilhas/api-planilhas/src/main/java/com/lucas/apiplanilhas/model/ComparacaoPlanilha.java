package com.lucas.apiplanilhas.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "comparacoes")
public class ComparacaoPlanilha {

    @Id
    private String id;

    private String planilhaInicialId;
    private String planilhaComparacaoId;
    private String descricao;
    private Integer score;
    private LocalDateTime dataComparacao;
}
