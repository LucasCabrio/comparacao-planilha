package com.lucas.apiplanilhas.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Data;
import lombok.NoArgsConstructor;

@Document (collection = "import_users")
@Data
@NoArgsConstructor

public class ImportUser {
  
  @Id
  private String id;

  private String nome;
  private String telefone;
  private String logradouro;
  private String numero;
  private String cep;
  private String bairro;
  private String cidade;
}
