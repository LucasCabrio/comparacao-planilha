package com.lucas.apiplanilhas.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "planilhas")
public class Planilha {

    @Id
    private String id;

    private String nomePlanilha;
    private Double tamanhoArquivo;
    private Integer qtdLinhas;
    private Integer qtdColunas;
    private String usuarioId;
    private Boolean status;
}
