package com.lucas.apiplanilhas.dto;

import lombok.Data;

@Data
public class EnderecoDTO {

    private String logradouro;
    private String cidade;
    private String numero;
    private String cep;
    private String bairro;
}
