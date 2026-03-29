package com.lucas.apiplanilhas.dto;

import lombok.Data;

@Data
public class UsuarioCadastroDTO {

    private String nome;
    private String telefone;
    private EnderecoDTO endereco = new EnderecoDTO();
}
