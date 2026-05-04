// ======================================================================================
// =============================== CONTROLADOR ==========================================
// ======================================================================================
const urlParams = new URLSearchParams(window.location.search);
const idMonitoria = urlParams.get('id');

document.addEventListener('DOMContentLoaded', async () => {

    const titulo = document.getElementById('titulo-pagina');
    const botaoSalvar = document.getElementById('botao-salvar');


    // VERIFICA SE VAI CRIAR OU EDITAR
    if (idMonitoria) {
        if (titulo) titulo.innerText = "Editar Monitoria";
        if (botaoSalvar) botaoSalvar.innerText = "Atualizar Monitoria";

        await carregarDadosMonitoria(idMonitoria);
    }
    else {
        if (titulo) titulo.innerText = "Nova Monitoria";
        if (botaoSalvar) botaoSalvar.innerText = "Criar Monitoria";

        carregarCursos();
        configurarMudancaDeCurso();

    }

});
// ======================================================================================
// ======================================================================================
// ======================================================================================



// ====================================================================================== 
// ============================ CARREGAR DADOS MONITORIA ================================ 
// ======================================================================================

async function carregarDadosMonitoria(idmonitoria) {
    try {
        const resposta = await fetch(`http://localhost:8080/api/monitoria/${idmonitoria}`);
        console.log(resposta);
        if (!resposta.ok) throw new Error("Erro ao carregar os dados.");

        const monitoria = await resposta.json();

        console.log(monitoria);

        document.getElementById('nome').value = monitoria.nome;
        document.getElementById('descricao').value = monitoria.descricao;
        document.getElementById('diasDaSemana').value = monitoria.diasDaSemana;
        document.getElementById('horario').value = monitoria.horario;
        document.getElementById('link').value = monitoria.link;

    //===========================================================================
        const selectDisciplina = document.getElementById('idDisciplina');
        const option = document.createElement('option');
        option.value = monitoria.id;
        option.text = monitoria.nome;

        selectDisciplina.innerHTML = ''; 
        selectDisciplina.appendChild(option);
        selectDisciplina.value = option.value;

        selectDisciplina.disabled = true;
        selectDisciplina.classList.add('bg-light');
    //===========================================================================

        const selectCurso = document.getElementById('cursoId');
        const optionCurso = document.createElement('option');
        optionCurso.text = monitoria.curso; // Baseado no JSON que você mostrou na imagem
        selectCurso.innerHTML = '';
        selectCurso.appendChild(optionCurso);
        selectCurso.disabled = true;
        selectCurso.classList.add('bg-light');

    //===========================================================================


    } catch (erro) {
        console.error(erro);
        alert("Erro ao buscar a monitoria para edição.");
    }
}
// ======================================================================================
// ======================================================================================
// ======================================================================================




// ====================================================================================== 
// ============================ CARREGAR CURSOS ========================================= 
// ======================================================================================

function carregarCursos(){
    fetch('http://localhost:8080/api/curso')
        .then(resposta => {
            if (!resposta.ok) {
                throw new Error("Erro na rede ao buscar os cursos");
            }
            return resposta.json();
        })
        .then(listaDeCursos => {
            const selectCurso = document.getElementById('cursoId');

            listaDeCursos.forEach(curso => {
                // Cria um novo elemento <option> do zero
                const novaOpcao = document.createElement('option');

                // Define o 'value' como o ID do curso
                novaOpcao.value = curso.id;

                // Define o texto que o usuário vai ler na tela
                novaOpcao.textContent = curso.nome;

                // Pega essa nova opção e "encaixa" dentro do <select>
                selectCurso.appendChild(novaOpcao);
            });
        })
        .catch(erro => console.error("Erro ao carregar a lista de cursos:", erro));
}
// ======================================================================================
// ======================================================================================
// ======================================================================================


// ====================================================================================== 
// ============================ CARREGAR DISCIPLINAS ==================================== 
// ======================================================================================

function configurarMudancaDeCurso() {
    const selectCurso = document.getElementById('cursoId');
    const selectDisc =  document.getElementById('idDisciplina');
    
    selectCurso.addEventListener('change', () => {
        const cursoId = selectCurso.value;
        const urlDaApi = `http://localhost:8080/api/curso/${cursoId}/disciplinas`;
        
        fetch(urlDaApi)
            .then(resposta => {
                if (!resposta.ok) throw new Error("Erro ao buscar disciplinas");
                return resposta.json();
            })
            .then(disciplinas => {
                // Limpa o select antes de preencher as novas
                selectDisc.innerHTML = '<option value="" selected disabled>Selecione a disciplina</option>';
                
                disciplinas.forEach(disc => {
                    const opt = document.createElement('option');
                    opt.value = disc.disciplinaId; 
                    opt.textContent = disc.disciplinaNome;
                    selectDisc.appendChild(opt);
                });
            })
            .catch(erro => console.error("Erro ao carregar disciplinas:", erro));
    });
}

// ======================================================================================
// ======================================================================================
// ======================================================================================




// ====================================================================================== 
// ========================== CADASTRAR E ATUALIZAR MONITORIA =========================== 
// ======================================================================================

const formCadastro = document.getElementById('formCriarMonitoria');


formCadastro.addEventListener('submit', async function(event) {
    event.preventDefault();

    const usuarioString = localStorage.getItem('usuarioLogado');
    const usuarioLogado = JSON.parse(usuarioString);

    // Monta o objeto com os dados da tela
    const monitoriaParaSalvar = {
        nome: document.getElementById('nome').value,
        descricao: document.getElementById('descricao').value,
        diasDaSemana: document.getElementById('diasDaSemana').value,
        horario: document.getElementById('horario').value, 
        link: document.getElementById('link').value,
        
        // Em caso de edição, esses selects estarão 'disabled'.
        // Se der problema no back-end por envia-los vazios na edição, o Spring Boot ignora.
        idCurso: parseInt(document.getElementById('cursoId').value, 10), 
        idDisciplina: parseInt(document.getElementById('idDisciplina').value, 10), 
        idMonitor: usuarioLogado.id
    };

    // A MÁGICA DE DECISÃO DE ROTAS:
    const urlApi = idMonitoria 
        ? `http://localhost:8080/api/monitoria/${idMonitoria}` // Se tem ID, vai para rota de PUT
        : `http://localhost:8080/api/monitoria/salvar`;        // Se não tem, vai para rota de POST
        
    const metodoHttp = idMonitoria ? 'PUT' : 'POST'; // Muda o verbo da requisição

    try {
        const resposta = await fetch(urlApi, {
            method: metodoHttp,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(monitoriaParaSalvar)
        });

        if (!resposta.ok) {
            const texto = await resposta.text(); 
            throw new Error(texto);
        }

        const dadosSalvos = await resposta.json();
        
        // Mensagem dinâmica!
        if (idMonitoria) {
            alert("Monitoria atualizada com sucesso!");
            // window.location.href = "gerenciar-monitorias.html"; // Descomente para redirecionar após editar
        } else {
            alert("Cadastro realizado com sucesso!");
            formCadastro.reset(); // Só limpa a tela se for criação
        }

    } catch (erro) {
        console.error("Erro ao salvar:", erro);
        alert("Erro ao realizar operação: " + erro.message);
    }
});

// ======================================================================================
// ======================================================================================
// ======================================================================================