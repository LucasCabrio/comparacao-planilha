package com.lucas.apiplanilhas.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lucas.apiplanilhas.dto.UsuarioCadastroDTO;
import com.lucas.apiplanilhas.dto.UsuarioDTO;
import com.lucas.apiplanilhas.model.ImportUser;
import com.lucas.apiplanilhas.model.Planilha;
import com.lucas.apiplanilhas.service.ImportUserService;
import com.lucas.apiplanilhas.service.PlanilhaService;
import com.lucas.apiplanilhas.service.UsuarioClientService;

@Controller
public class ImportUserViewController {

    private final ImportUserService importUserService;
    private final UsuarioClientService usuarioClientService;
    private final PlanilhaService planilhaService;

    public ImportUserViewController(ImportUserService importUserService,
            UsuarioClientService usuarioClientService,
            PlanilhaService planilhaService) {
        this.importUserService = importUserService;
        this.usuarioClientService = usuarioClientService;
        this.planilhaService = planilhaService;
    }

    @GetMapping("/upload")
    public String exibirPaginaUpload(Model model) {
        prepararLayout(model);
        return "upload";
    }

    @PostMapping("/upload")
    public String importarArquivoPelaTela(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        List<ImportUser> usuarios = importUserService.importarCsv(file);
        prepararLayout(model);
        model.addAttribute("mensagem", "Arquivo importado com sucesso!");
        model.addAttribute("usuarios", usuarios);
        return "upload";
    }

    @GetMapping("/usuarios/novo")
    public String exibirPaginaCadastroUsuario(Model model) {
        prepararLayout(model);
        model.addAttribute("usuario", new UsuarioCadastroDTO());
        return "usuario-form";
    }

    @PostMapping("/usuarios/novo")
    public String cadastrarUsuario(@ModelAttribute("usuario") UsuarioCadastroDTO usuario, Model model) {
        prepararLayout(model);
        try {
            UsuarioDTO usuarioCriado = usuarioClientService.cadastrarUsuario(usuario);
            model.addAttribute("mensagem", "Usuario criado com sucesso!");
            model.addAttribute("usuarioCriado", usuarioCriado);
            model.addAttribute("usuario", new UsuarioCadastroDTO());
        } catch (RuntimeException ex) {
            model.addAttribute("erro", "Nao foi possivel criar o usuario. Verifique se a API de usuarios esta ativa.");
            model.addAttribute("usuario", usuario);
        }
        return "usuario-form";
    }

    @GetMapping("/planilhas/comprar")
    public String exibirPaginaCompraPlanilha(Model model) {
        prepararLayout(model);
        model.addAttribute("planilha", new Planilha());
        return "planilha-form";
    }

    @PostMapping("/planilhas/comprar")
    public String comprarPlanilha(@ModelAttribute("planilha") Planilha planilha, Model model) {
        prepararLayout(model);
        try {
            Planilha planilhaCriada = planilhaService.salvar(planilha);
            model.addAttribute("mensagem", "Planilha registrada com sucesso!");
            model.addAttribute("planilhaCriada", planilhaCriada);
            model.addAttribute("planilha", new Planilha());
        } catch (RuntimeException ex) {
            model.addAttribute("erro", "Nao foi possivel registrar a planilha. Confirme o usuario informado e a API de usuarios.");
            model.addAttribute("planilha", planilha);
        }
        return "planilha-form";
    }

    private void prepararLayout(Model model) {
        model.addAttribute("usuariosDisponiveis", buscarUsuariosDisponiveis());
    }

    private List<UsuarioDTO> buscarUsuariosDisponiveis() {
        try {
            List<UsuarioDTO> usuarios = usuarioClientService.listarUsuarios();
            return usuarios != null ? usuarios : new ArrayList<>();
        } catch (RuntimeException ex) {
            return new ArrayList<>();
        }
    }
}
