package com.lucas.apiplanilhas.service;
import org.springframework.stereotype.Service;
import com.lucas.apiplanilhas.repository.ImportUserRepository;
import java.util.List;
import com.lucas.apiplanilhas.model.ImportUser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

@Service
public class ImportUserService{
  private final ImportUserRepository importUserRepository;
  public ImportUserService (ImportUserRepository importUserRepository){
    this.importUserRepository = importUserRepository;
  }

    public List<ImportUser> listarTodos() {
      return importUserRepository.findAll();
    }

    public ImportUser salvar (ImportUser importUser){
      return importUserRepository.save(importUser);
    }

    public List<ImportUser> importarCsv(MultipartFile file) throws IOException {
    List<ImportUser> usuariosImportados = new ArrayList<>();

    BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
    String linha;
    boolean primeiraLinha = true;

    while ((linha = reader.readLine()) != null) {
        if (primeiraLinha) {
            primeiraLinha = false;
            continue;
        }

        String[] dados = linha.split(",");

        if (dados.length < 7) {
            continue;
        }

        ImportUser usuario = new ImportUser();
        usuario.setNome(dados[0].trim());
        usuario.setTelefone(dados[1].trim());
        usuario.setLogradouro(dados[2].trim());
        usuario.setNumero(dados[3].trim());
        usuario.setCep(dados[4].trim());
        usuario.setBairro(dados[5].trim());
        usuario.setCidade(dados[6].trim());

        ImportUser usuarioSalvo = importUserRepository.save(usuario);
        usuariosImportados.add(usuarioSalvo);
    }

    reader.close();
    return usuariosImportados;
}
}
