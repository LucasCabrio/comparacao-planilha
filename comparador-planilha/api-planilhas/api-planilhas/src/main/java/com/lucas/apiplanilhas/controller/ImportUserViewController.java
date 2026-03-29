package com.lucas.apiplanilhas.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lucas.apiplanilhas.dto.UsuarioCadastroDTO;
import com.lucas.apiplanilhas.dto.UsuarioDTO;
import com.lucas.apiplanilhas.model.ComparacaoPlanilha;
import com.lucas.apiplanilhas.model.ImportUser;
import com.lucas.apiplanilhas.model.Planilha;
import com.lucas.apiplanilhas.service.ComparacaoService;
import com.lucas.apiplanilhas.service.ImportUserService;
import com.lucas.apiplanilhas.service.PlanilhaService;
import com.lucas.apiplanilhas.service.UsuarioClientService;

@Controller
public class ImportUserViewController {

    private final ImportUserService importUserService;
    private final UsuarioClientService usuarioClientService;
    private final PlanilhaService planilhaService;
    private final ComparacaoService comparacaoService;

    public ImportUserViewController(ImportUserService importUserService,
            UsuarioClientService usuarioClientService,
            PlanilhaService planilhaService,
            ComparacaoService comparacaoService) {
        this.importUserService = importUserService;
        this.usuarioClientService = usuarioClientService;
        this.planilhaService = planilhaService;
        this.comparacaoService = comparacaoService;
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

    @GetMapping("/comparacoes/nova")
    public String exibirPaginaComparacao(Model model) {
        prepararLayout(model);
        model.addAttribute("comparacao", new ComparacaoPlanilha());
        return "comparacao-form";
    }

    @PostMapping("/comparacoes/nova")
    public String compararPlanilhas(@ModelAttribute("comparacao") ComparacaoPlanilha comparacao, Model model) {
        prepararLayout(model);

        if (comparacao.getPlanilhaInicialId() != null
                && comparacao.getPlanilhaInicialId().equals(comparacao.getPlanilhaComparacaoId())) {
            model.addAttribute("erro", "Escolha duas planilhas diferentes para comparar.");
            model.addAttribute("comparacao", comparacao);
            return "comparacao-form";
        }

        try {
            ComparacaoPlanilha comparacaoCriada = comparacaoService.criarComparacao(comparacao);
            model.addAttribute("mensagem", "Comparacao realizada com sucesso!");
            model.addAttribute("comparacaoCriada", comparacaoCriada);
            model.addAttribute("comparacaoInicial", buscarPlanilhaPorId(comparacaoCriada.getPlanilhaInicialId()));
            model.addAttribute("comparacaoSecundaria", buscarPlanilhaPorId(comparacaoCriada.getPlanilhaComparacaoId()));
            model.addAttribute("comparacao", new ComparacaoPlanilha());
        } catch (RuntimeException ex) {
            model.addAttribute("erro", "Nao foi possivel comparar as planilhas selecionadas. Confira se ambas existem.");
            model.addAttribute("comparacao", comparacao);
        }

        return "comparacao-form";
    }

    private void prepararLayout(Model model) {
        model.addAttribute("usuariosDisponiveis", buscarUsuariosDisponiveis());
        model.addAttribute("planilhasDisponiveis", buscarPlanilhasDisponiveis());
        model.addAttribute("comparacoesRecentes", buscarComparacoesRecentes());
    }

    private List<UsuarioDTO> buscarUsuariosDisponiveis() {
        try {
            List<UsuarioDTO> usuarios = usuarioClientService.listarUsuarios();
            return usuarios != null ? usuarios : new ArrayList<>();
        } catch (RuntimeException ex) {
            return new ArrayList<>();
        }
    }

    private List<Planilha> buscarPlanilhasDisponiveis() {
        try {
            List<Planilha> planilhas = planilhaService.listar();
            return planilhas != null ? planilhas : new ArrayList<>();
        } catch (RuntimeException ex) {
            return new ArrayList<>();
        }
    }

    private List<ComparacaoPlanilha> buscarComparacoesRecentes() {
        try {
            List<ComparacaoPlanilha> comparacoes = comparacaoService.listarComparacoes();
            if (comparacoes == null) {
                return new ArrayList<>();
            }

            return comparacoes.stream()
                    .sorted((a, b) -> {
                        if (a.getDataComparacao() == null && b.getDataComparacao() == null) {
                            return 0;
                        }
                        if (a.getDataComparacao() == null) {
                            return 1;
                        }
                        if (b.getDataComparacao() == null) {
                            return -1;
                        }
                        return b.getDataComparacao().compareTo(a.getDataComparacao());
                    })
                    .limit(5)
                    .collect(Collectors.toList());
        } catch (RuntimeException ex) {
            return new ArrayList<>();
        }
    }

    private Planilha buscarPlanilhaPorId(String id) {
        try {
            return planilhaService.buscarPorId(id);
        } catch (RuntimeException ex) {
            return null;
        }
    }
}
