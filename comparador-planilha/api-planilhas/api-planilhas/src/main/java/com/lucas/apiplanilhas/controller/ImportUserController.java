package com.lucas.apiplanilhas.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import com.lucas.apiplanilhas.model.ImportUser;
import com.lucas.apiplanilhas.service.ImportUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
public class ImportUserController {
  private final ImportUserService importUserService;

  public ImportUserController (ImportUserService importUserService){
    this.importUserService = importUserService;
  }

  @GetMapping("/importdata")
    public List<ImportUser> listarTodos(){
      return importUserService.listarTodos();
    }

   @PostMapping(value = "/importdata", consumes = "multipart/form-data")
    public List<ImportUser> importarArquivo(@RequestParam("file")   MultipartFile file) throws IOException {
    return importUserService.importarCsv(file);
}
}
