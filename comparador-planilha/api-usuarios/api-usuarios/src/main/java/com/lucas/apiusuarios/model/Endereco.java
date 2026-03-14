package com.lucas.apiusuarios.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    private String logradouro;
    private String cidade;
    private String numero;
    private String cep;
    private String bairro;
}
