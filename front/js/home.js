const usuarioString = localStorage.getItem('usuarioLogado');

    if (!usuarioString) {
        window.location.href = 'login.html';
    } else {
        const usuario = JSON.parse(usuarioString);
        document.getElementById('nomeUsuario').innerText = usuario.nome;

        if (usuario.tipo === 'MONITOR') {
            document.getElementById('menu-monitor').style.display = 'block';
            document.getElementById('tituloSecaoCursos').innerText = "Salas que você gerencia";
            
            // CHAMA A SUA API PARA BUSCAR AS MONITORIAS DESTE MONITOR!
            buscarMonitoriasDoMonitor(usuario.id);
        } else {
            document.getElementById('tituloSecaoCursos').innerText = "Minhas Inscrições";
            ListaDeMonitorias(usuario.id);
            document.getElementById('containerCards').innerHTML = "<p>Você ainda não está inscrito em nenhuma monitoria.</p>";
        }
    }

    // Função que vai no seu Java buscar a lista
    function buscarMonitoriasDoMonitor(idMonitor) {
        fetch(`http://localhost:8080/api/monitoria/monitor/${idMonitor}`)
            .then(resposta => resposta.json())
            .then(listaDeMonitorias => {
                const container = document.getElementById('containerCards');
                container.innerHTML = ''; 

                if (listaDeMonitorias.length === 0) {
                    container.innerHTML = '<p class="text-muted">Você ainda não criou nenhuma monitoria.</p>';
                    return;
                }

                // Para cada monitoria na lista, cria um Card HTML
                listaDeMonitorias.forEach(monitoria => {
                    const cardHTML = `
                        <div class="col-md-6 col-lg-4">
                            <div class="card border-0 shadow-sm border-top border-primary border-4 h-100">
                                <div class="card-body">
                                    <h6 class="text-muted fw-bold mb-1">${monitoria.cursoNome}</h6>
                                    <h5 class="fw-bold text-primary mt-2">${monitoria.nome}</h5>
                                    <p class="small text-muted mb-3"><i class="bi bi-book"></i> ${monitoria.disciplinaNome}</p>
                                    
                                    <a href="gerenciar-monitoria.html?id=${monitoria.id}" class="btn btn-outline-primary btn-sm w-100 fw-bold">
                                        Gerenciar Sala
                                    </a>
                                </div>
                            </div>
                        </div>
                    `;
                    // Injeta o card na tela
                    container.innerHTML += cardHTML;
                });
            })
            .catch(erro => {
                console.error("Erro ao buscar monitorias:", erro);
                document.getElementById('containerCards').innerHTML = '<p class="text-danger">Erro ao carregar as salas. Verifique se o servidor Java está rodando.</p>';
            });
    }

    // FUNÇÃO INSCREVER EM MONITORIA
    function inscreverNaMonitoria(idMonitoria) {
    // 1. Pega o ID do aluno que está logado
    const usuarioString = localStorage.getItem('usuarioLogado');
    const usuario = JSON.parse(usuarioString);
    const idAluno = usuario.id;

    // 2. Chama a sua rota no Java!

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

// FUNÇÃO QUE LISTAR MONITORIAS
    function listarMonitoriasParaAluno() {
    // Bate no seu método listarMonitorias() do Java
    fetch('http://localhost:8080/api/monitoria') 
        .then(resposta => resposta.json())
        .then(listaDeMonitorias => {
            const container = document.getElementById('containerCards');
            container.innerHTML = ''; 

            if (listaDeMonitorias.length === 0) {
                container.innerHTML = '<p class="text-muted">Nenhuma monitoria disponível no momento.</p>';
                return;
            }

            // Para cada monitoria que o Java devolver, cria um card
            listaDeMonitorias.forEach(monitoria => {
                
                const cardHTML = `
                    <div class="col-md-6 col-lg-4">
        <div class="card h-100 shadow-sm border-0">
            <div class="card-body">
                <h5 class="card-title text-primary fw-bold">
                    ${monitoria.nome}
                </h5>
                <h6 class="card-subtitle mb-2 text-muted">
                    Disciplina: ${monitoria.disciplina || 'Geral'}
                </h6>
                <p class="card-text small">
                    <strong>Monitor:</strong> ${monitoria.monitor || 'A definir'}
                </p>
            </div>
            <div class="card-footer bg-white border-0 pb-3 text-center">
                <button class="btn btn-primary w-100 fw-semibold" onclick="inscreverNaMonitoria(${monitoria.id})">
                    Participar
                </button>
            </div>
        </div>
    </div>
                `;
                container.innerHTML += cardHTML;
            });
        })
        .catch(erro => console.error("Erro ao buscar monitorias:", erro));
    }
    function sair() {
        localStorage.removeItem('usuarioLogado');
        localStorage.clear();
        window.location.href = 'login.html';
    }


    // Essa função carrega todas as monitorias em que o aluno está inscrito
    function ListaDeMonitorias(){
        // 1. Pega o ID do aluno que está logado
        const usuarioString = localStorage.getItem('usuarioLogado');
        const usuario = JSON.parse(usuarioString);
        const idAluno = usuario.id;

        fetch(`http://localhost:8080/api/usuario/${idAluno}/monitorias`)
        .then(resposta => resposta.json())
        .then(listaDeMonitorias => {
            const container = document.getElementById('containerCards');
            container.innerHTML = ''; 

            if (listaDeMonitorias.length === 0) {
                container.innerHTML = '<p class="text-muted">Nenhuma monitoria encontrada.</p>';
                return;
            }
        // Para cada monitoria que o Java devolver, cria um card
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
                <button onclick="window.location.href='detalhes-monitoria.html?id=${monitoria.monitoriaId}'" class="btn btn-primary w-100 fw-semibold">
                    Acessar
                </button>
            </div>
        </div>
    </div>
                `;
                container.innerHTML += cardHTML;
            });
        })
        .catch(erro => console.error("Erro ao buscar monitorias:", erro));

    }