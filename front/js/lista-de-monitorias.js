// Assim que a página carrega, ele roda essa função
document.addEventListener('DOMContentLoaded', buscarMonitorias);

function buscarMonitorias() {
    // 1. Abre a "mochila" do navegador e pega o ID do curso salvo no cadastro/login
    const cursoId = localStorage.getItem('cursoUsuarioId');

    // Se por algum motivo não tiver ID (ex: acessou o link direto), avisa e volta
    if (!cursoId) {
        alert("Sessão inválida. Por favor, faça o cadastro ou login novamente.");
        window.location.href = 'index.html'; // Nome da sua tela inicial
        return;
    }

    // 2. Monta a URL do seu endpoint
    const urlDaApi = `http://localhost:8080/api/curso/${cursoId}/monitorias`;

    // 3. Faz a requisição GET
    fetch(urlDaApi)
        .then(resposta => {
            if (!resposta.ok) {
                throw new Error("Não foi possível carregar as monitorias.");
            }
            return resposta.json();
        })
        .then(listaDeMonitorias => {
            const container = document.getElementById('containerMonitorias');

            // Limpa o aviso de "Carregando..."
            container.innerHTML = '';

            // Se a lista vier vazia do Java
            if (listaDeMonitorias.length === 0) {
                container.innerHTML = `
                    <div class="col-12 text-center mt-5">
                        <h5 class="text-muted">Poxa, nenhuma monitoria cadastrada para o seu curso ainda.</h5>
                    </div>`;
                return;
            }

            // 4. Para cada monitoria na lista, cria um Card HTML e joga na tela
            listaDeMonitorias.forEach(monitoria => {

                const cardHTML = `
    <div class="col-md-6 col-lg-4">
        <div class="card h-100 shadow-sm border-0">
            <div class="card-body">
                <h5 class="card-title text-primary fw-bold">
                    ${monitoria.monitoriaNome}
                </h5>
                <h6 class="card-subtitle mb-2 text-muted">
                    Disciplina: ${monitoria.nomeDisciplina || 'Geral'}
                </h6>
                <p class="card-text small">
                    <strong>Monitor:</strong> ${monitoria.nomeMonitor || 'A definir'}
                </p>
            </div>
            <div class="card-footer bg-white border-0 pb-3 text-center">
                <button class="btn btn-primary w-100 fw-semibold" onclick="inscreverNaMonitoria(${monitoria.monitoriaId})">
                    Participar
                </button>
            </div>
        </div>
    </div>
`;

                // Adiciona o card dentro do container
                container.innerHTML += cardHTML;
            });
        })
        .catch(erro => {
            console.error(erro);
            document.getElementById('containerMonitorias').innerHTML = `
                <div class="col-12 text-center text-danger mt-5">
                    <h5>Erro ao buscar dados: ${erro.message}</h5>
                </div>`;
        });
}

// Função engatilhada para o botão "Participar"
function inscreverNaMonitoria(idMonitoria) {
    const usuarioString = localStorage.getItem('usuarioLogadoId');
    const usuario = JSON.parse(usuarioString);
    const idAluno = usuario.id;

    fetch(`http://localhost:8080/api/usuario/${idAluno}/monitorias/${idMonitoria}/inscrever`, {
        method: 'POST'
    })
    .then(resposta => {
        if (resposta.ok) {
            alert("Inscrição realizada com sucesso!");
            // Aqui podemos recarregar a tela para atualizar a lista do aluno
            window.location.reload(); 
        } else {
            // Se o Java devolver um badRequest (ex: Aluno já inscrito)
            return resposta.text().then(texto => { throw new Error(texto) });
        }
    })
    .catch(erro => {
        console.error("Erro:", erro);
        alert("Não foi possível se inscrever: " + erro.message);
    });
}

function sair() {
    localStorage.clear();
    window.location.href = 'login.html'; 
}

function irParaHome() {
    window.location.href = 'home.html'; // Coloque o nome correto do seu arquivo
}