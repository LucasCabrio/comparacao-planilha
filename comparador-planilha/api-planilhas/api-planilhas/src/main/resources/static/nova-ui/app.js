const endpoints = {
    usuarios: "/frontend-api/usuarios",
    planilhas: "/planilhas",
    comparacoes: "/comparacoes",
    importacoes: "/importdata"
};

const state = {
    usuarios: [],
    planilhas: [],
    comparacoes: [],
    importacoes: []
};

document.addEventListener("DOMContentLoaded", () => {
    bindForms();
    bindButtons();
    refreshAll();
});

function bindForms() {
    document.getElementById("usuarioForm").addEventListener("submit", handleUsuarioSubmit);
    document.getElementById("planilhaForm").addEventListener("submit", handlePlanilhaSubmit);
    document.getElementById("comparacaoForm").addEventListener("submit", handleComparacaoSubmit);
    document.getElementById("importForm").addEventListener("submit", handleImportSubmit);
}

function bindButtons() {
    document.getElementById("refreshAllBtn").addEventListener("click", refreshAll);

    document.querySelectorAll("[data-refresh]").forEach((button) => {
        button.addEventListener("click", async () => {
            const target = button.dataset.refresh;
            await refreshSection(target);
        });
    });

    document.querySelector('[data-search="usuario"]').addEventListener("click", () => searchById("usuario"));
    document.querySelector('[data-search="planilha"]').addEventListener("click", () => searchById("planilha"));
    document.querySelector('[data-search="comparacao"]').addEventListener("click", () => searchById("comparacao"));
}

async function refreshAll() {
    await Promise.all([
        refreshSection("usuarios"),
        refreshSection("planilhas"),
        refreshSection("comparacoes"),
        refreshSection("importacoes")
    ]);
}

async function refreshSection(section) {
    try {
        if (section === "usuarios") {
            state.usuarios = await requestJson(endpoints.usuarios);
            renderUsuarios();
            updateMetric("usuariosCount", state.usuarios.length);
            return;
        }

        if (section === "planilhas") {
            state.planilhas = await requestJson(endpoints.planilhas);
            renderPlanilhas();
            updateMetric("planilhasCount", state.planilhas.length);
            return;
        }

        if (section === "comparacoes") {
            state.comparacoes = await requestJson(endpoints.comparacoes);
            renderComparacoes();
            updateMetric("comparacoesCount", state.comparacoes.length);
            return;
        }

        if (section === "importacoes") {
            state.importacoes = await requestJson(endpoints.importacoes);
            renderImportacoes();
            updateMetric("importacoesCount", state.importacoes.length);
        }
    } catch (error) {
        showToast(error.message, "error");
    }
}

async function handleUsuarioSubmit(event) {
    event.preventDefault();
    const form = event.currentTarget;
    const formData = new FormData(form);

    const payload = {
        nome: formData.get("nome"),
        telefone: formData.get("telefone"),
        endereco: {
            logradouro: formData.get("logradouro"),
            numero: formData.get("numero"),
            cidade: formData.get("cidade"),
            bairro: formData.get("bairro"),
            cep: formData.get("cep")
        }
    };

    try {
        const data = await requestJson(endpoints.usuarios, {
            method: "POST",
            body: JSON.stringify(payload)
        });

        form.reset();
        document.getElementById("usuarioSearchResult").textContent = formatJson(data);
        showToast("Usuario cadastrado com sucesso.", "success");
        await refreshSection("usuarios");
    } catch (error) {
        showToast(error.message, "error");
    }
}

async function handlePlanilhaSubmit(event) {
    event.preventDefault();
    const formData = new FormData(event.currentTarget);

    const payload = {
        nomePlanilha: formData.get("nomePlanilha"),
        tamanhoArquivo: Number(formData.get("tamanhoArquivo")),
        qtdLinhas: Number(formData.get("qtdLinhas")),
        qtdColunas: Number(formData.get("qtdColunas")),
        usuarioId: formData.get("usuarioId"),
        status: formData.get("status") === "true"
    };

    try {
        const data = await requestJson(endpoints.planilhas, {
            method: "POST",
            body: JSON.stringify(payload)
        });

        event.currentTarget.reset();
        document.getElementById("planilhaSearchResult").textContent = formatJson(data);
        showToast("Planilha registrada com sucesso.", "success");
        await refreshSection("planilhas");
    } catch (error) {
        showToast(error.message, "error");
    }
}

async function handleComparacaoSubmit(event) {
    event.preventDefault();
    const formData = new FormData(event.currentTarget);
    const planilhaInicialId = formData.get("planilhaInicialId");
    const planilhaComparacaoId = formData.get("planilhaComparacaoId");

    if (planilhaInicialId === planilhaComparacaoId) {
        showToast("Escolha duas planilhas diferentes para comparar.", "error");
        return;
    }

    const payload = {
        planilhaInicialId,
        planilhaComparacaoId,
        descricao: formData.get("descricao")
    };

    try {
        const data = await requestJson(endpoints.comparacoes, {
            method: "POST",
            body: JSON.stringify(payload)
        });

        event.currentTarget.reset();
        document.getElementById("comparacaoSearchResult").textContent = formatJson(data);
        showToast("Comparacao criada com sucesso.", "success");
        await refreshSection("comparacoes");
    } catch (error) {
        showToast(error.message, "error");
    }
}

async function handleImportSubmit(event) {
    event.preventDefault();
    const form = event.currentTarget;
    const formData = new FormData(form);

    try {
        const response = await fetch(endpoints.importacoes, {
            method: "POST",
            body: formData
        });

        const data = await parseResponse(response);
        state.importacoes = Array.isArray(data) ? data : [];
        renderImportacoes();
        updateMetric("importacoesCount", state.importacoes.length);
        document.getElementById("importSearchResult").textContent = formatJson(data);
        form.reset();
        showToast("Arquivo importado com sucesso.", "success");
    } catch (error) {
        showToast(error.message, "error");
    }
}

async function searchById(type) {
    const config = {
        usuario: {
            inputId: "usuarioIdInput",
            outputId: "usuarioSearchResult",
            baseUrl: endpoints.usuarios
        },
        planilha: {
            inputId: "planilhaIdInput",
            outputId: "planilhaSearchResult",
            baseUrl: endpoints.planilhas
        },
        comparacao: {
            inputId: "comparacaoIdInput",
            outputId: "comparacaoSearchResult",
            baseUrl: endpoints.comparacoes
        }
    }[type];

    const input = document.getElementById(config.inputId);
    const output = document.getElementById(config.outputId);
    const id = input.value.trim();

    if (!id) {
        showToast("Informe um ID para realizar a busca.", "error");
        return;
    }

    try {
        const data = await requestJson(`${config.baseUrl}/${id}`);
        output.textContent = formatJson(data);
    } catch (error) {
        output.textContent = error.message;
        showToast(error.message, "error");
    }
}

function renderUsuarios() {
    renderList("usuariosList", state.usuarios, (usuario) => `
        <div class="result-item">
            <strong>${escapeHtml(usuario.nome || "Sem nome")}</strong>
            <p>ID: ${escapeHtml(usuario.id || "-")}</p>
            <p>Telefone: ${escapeHtml(usuario.telefone || "-")}</p>
            <span class="pill">Usuario</span>
        </div>
    `);
}

function renderPlanilhas() {
    renderList("planilhasList", state.planilhas, (planilha) => `
        <div class="result-item">
            <strong>${escapeHtml(planilha.nomePlanilha || "Planilha")}</strong>
            <p>ID: ${escapeHtml(planilha.id || "-")}</p>
            <p>Usuario ID: ${escapeHtml(planilha.usuarioId || "-")}</p>
            <p>Linhas x Colunas: ${escapeHtml(String(planilha.qtdLinhas ?? "-"))} x ${escapeHtml(String(planilha.qtdColunas ?? "-"))}</p>
            <span class="pill">${planilha.status ? "Ativa" : "Inativa"}</span>
        </div>
    `);
}

function renderComparacoes() {
    renderList("comparacoesList", state.comparacoes, (comparacao) => `
        <div class="result-item">
            <strong>${escapeHtml(comparacao.descricao || "Comparacao")}</strong>
            <p>ID: ${escapeHtml(comparacao.id || "-")}</p>
            <p>Inicial: ${escapeHtml(comparacao.planilhaInicialId || "-")}</p>
            <p>Comparacao: ${escapeHtml(comparacao.planilhaComparacaoId || "-")}</p>
            <span class="pill">Score ${escapeHtml(String(comparacao.score ?? "-"))}</span>
        </div>
    `);
}

function renderImportacoes() {
    renderList("importacoesList", state.importacoes, (item) => `
        <div class="result-item">
            <strong>${escapeHtml(item.nome || "Registro")}</strong>
            <p>ID: ${escapeHtml(item.id || "-")}</p>
            <p>Telefone: ${escapeHtml(item.telefone || "-")}</p>
            <p>${escapeHtml(item.cidade || "-")} / ${escapeHtml(item.bairro || "-")}</p>
            <span class="pill">Importado</span>
        </div>
    `);
}

function renderList(containerId, items, template) {
    const container = document.getElementById(containerId);

    if (!Array.isArray(items) || items.length === 0) {
        container.innerHTML = '<div class="result-item"><strong>Sem registros</strong><p>Nenhum item encontrado para este recurso.</p></div>';
        return;
    }

    container.innerHTML = items.map(template).join("");
}

function updateMetric(elementId, value) {
    document.getElementById(elementId).textContent = String(value);
}

async function requestJson(url, options = {}) {
    const response = await fetch(url, {
        headers: {
            "Content-Type": "application/json",
            ...(options.headers || {})
        },
        ...options
    });

    return parseResponse(response);
}

async function parseResponse(response) {
    const contentType = response.headers.get("content-type") || "";
    const isJson = contentType.includes("application/json");
    const payload = isJson ? await response.json() : await response.text();

    if (!response.ok) {
        const detail = typeof payload === "string"
            ? payload
            : payload.message || payload.error || JSON.stringify(payload);
        throw new Error(`Erro ${response.status}: ${detail}`);
    }

    return payload;
}

function formatJson(data) {
    return JSON.stringify(data, null, 2);
}

function showToast(message, type) {
    const toast = document.getElementById("feedback");
    toast.textContent = message;
    toast.className = `toast ${type}`;

    window.clearTimeout(showToast.timeoutId);
    showToast.timeoutId = window.setTimeout(() => {
        toast.className = "toast hidden";
    }, 4200);
}

function escapeHtml(value) {
    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#39;");
}
