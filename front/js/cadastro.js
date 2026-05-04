
// ====================================================================================== 
// ============================= PAGINAS FORMULARIO ===================================== 
// ======================================================================================

function irParaPasso(numeroDoPasso) {
    document.getElementById('passo-1').classList.add('d-none');
    document.getElementById('passo-2').classList.add('d-none');
    document.getElementById('passo-3').classList.add('d-none');

    document.getElementById('passo-' + numeroDoPasso).classList.remove('d-none');
}

// ====================================================================================== 
// ============================ CARREGAR CURSOS ========================================= 
// ======================================================================================

function carregarCursos() {
    fetch('http://localhost:8080/api/curso')
        .then(resposta => {
            if (!resposta.ok) {
                throw new Error("Erro na rede ao buscar os cursos");
            }
            return resposta.json();
        })
        .then(listaDeCursos => {
            const selectCurso = document.getElementById('cursoId');

            // Passa por cada curso que veio do banco de dados
            listaDeCursos.forEach(curso => {
                // Cria um novo elemento <option> do zero
                const novaOpcao = document.createElement('option');

                // Define o 'value' como o ID do curso (é isso que vai pro banco no POST)
                novaOpcao.value = curso.id;

                // Define o texto que o usuário vai ler na tela
                novaOpcao.textContent = curso.nome;

                // Pega essa nova opção e "encaixa" dentro do <select>
                selectCurso.appendChild(novaOpcao);
            });
        })
        .catch(erro => console.error("Erro ao carregar a lista de cursos:", erro));
}
document.addEventListener('DOMContentLoaded', carregarCursos);
// ====================================================================================== 
// =============================== VERIFICAR TIPO USUÁRIO =============================== 
// ======================================================================================

function verificarTipoMonitor() {
    const tipo = document.getElementById('tipoUsuario').value;
    const divCodigo = document.getElementById('div-codigo-monitor');

    if (tipo === 'MONITOR') {
        divCodigo.classList.remove('d-none');
    } else {
        divCodigo.classList.add('d-none');
        document.getElementById('codigoDeValidacao').value = ''; // Limpa o campo
    }
}

// ====================================================================================== 
// =============================== CADASTRAR USUÁRIO ==================================== 
// ======================================================================================

const formCadastro = document.getElementById('form-cadastro');

formCadastro.addEventListener('submit', function (event) {
    event.preventDefault();

    // Dados do formulário
    const tipo = document.getElementById('tipoUsuario').value;
    
    const usuarioParaSalvar = {
        nome: document.getElementById('nome').value,
        email: document.getElementById('email').value,
        senha: document.getElementById('senha').value,
        dataNascimento: document.getElementById('dataNascimento').value,
        matricula: document.getElementById('matricula').value,
        tipo: tipo,
        idCurso: parseInt(document.getElementById('cursoId').value)
    };

    // URL baseada no Tipo 
    let urlDaApi = 'http://localhost:8080/api/usuario/salvar';
    if (tipo === 'MONITOR') {
        urlDaApi += '?tokenMonitor=MONITOR123';
    }

    // Chamada da API p/ criar o usuário
    fetch(urlDaApi, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(usuarioParaSalvar)
    })
    .then(resposta => {
        if (!resposta.ok) {
            return resposta.text().then(texto => { throw new Error(texto) });
        }
        return resposta.json();
    })
    .then(dadosDoUsuarioSalvo => {
        alert("Cadastro realizado com sucesso!");
        localStorage.setItem('usuarioLogadoId', dadosDoUsuarioSalvo.id);
        localStorage.setItem('cursoUsuarioId', document.getElementById('cursoId').value);
        formCadastro.reset();
        window.location.href = 'lista-de-monitorias.html'; 
    })
    .catch(erro => {
        console.error("Erro durante o processo:", erro);
        alert("Erro: " + erro.message);
    });
});

// ====================================================================================== 
// ====================================================================================== 
// ======================================================================================