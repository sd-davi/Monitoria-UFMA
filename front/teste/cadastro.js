
// Função para esconder todas as etapas e mostrar apenas a desejada
function irParaPasso(numeroDoPasso) {
    document.getElementById('passo-1').classList.add('d-none');
    document.getElementById('passo-2').classList.add('d-none');
    document.getElementById('passo-3').classList.add('d-none');

    document.getElementById('passo-' + numeroDoPasso).classList.remove('d-none');
}

// Função para mostrar/esconder o campo de código do monitor
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


function carregarCursos() {
    // Atenção: Substitua pela URL exata do seu Controller (ex: /api/cursos ou /cursos)
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

// Essa linha garante que a função carregarCursos() vai rodar sozinha 
// assim que a página HTML terminar de carregar na tela do usuário.
document.addEventListener('DOMContentLoaded', carregarCursos);

// -----------===============-------------------===================----------------

const selectCurso = document.getElementById('cursoId');
const selectTipo = document.getElementById('tipoUsuario');
const containerDisc = document.getElementById('containerDisciplina');
const selectDisc = document.getElementById('disciplinaId');

// --- Lógica 1: Mostrar/Esconder campo de Disciplina ---
selectTipo.addEventListener('change', () => {
    if (selectTipo.value === 'MONITOR') {
        containerDisc.classList.remove('d-none');
    } else {
        containerDisc.classList.add('d-none');
        selectDisc.value = ''; // Limpa a seleção se voltar para Aluno
    }
});

// --- Lógica 2: Carregar Disciplinas quando o Curso mudar ---

/*
selectCurso.addEventListener('change', () => {
    const cursoId = selectCurso.value;
    
    if (!cursoId) return; // Se não tiver curso selecionado, não faz nada

    // Chamando seu endpoint bonitão: /api/curso/{id}/disciplinas
    fetch(`http://localhost:8080/api/curso/${cursoId}/disciplinas`)
        .then(res => res.json())
        .then(disciplinas => {
            // Limpa as opções antigas (mantendo a primeira)
            selectDisc.innerHTML = '<option value="">Selecione a disciplina</option>';

            // Preenche com as novas disciplinas do curso selecionado
            disciplinas.forEach(disc => {
                const opt = document.createElement('option');
                opt.value = disc.disciplinaId; // Use o nome do atributo do seu DTO
                opt.textContent = disc.disciplinaNome;
                selectDisc.appendChild(opt);
            });
        });
});
 */

// ====================================================================================== 
// ====================================================================================== 
// ====================================================================================== 



// Pega o formulário pelo ID (Lembre-se de colocar id="formCadastro" na tag <form> do seu HTML)
const formCadastro = document.getElementById('form-cadastro');

formCadastro.addEventListener('submit', function (event) {
    // Impede a página de recarregar quando clica no botão de submit
    event.preventDefault();

    // Coleta os valores digitados nos inputs (verifique se os IDs batem com seu HTML)
    const nome = document.getElementById('nome').value;
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;
    const dataNascimento = document.getElementById('dataNascimento').value;
    const matricula = document.getElementById('matricula').value;
    const tipo = document.getElementById('tipoUsuario').value; // ALUNO ou MONITOR
    const idCurso = document.getElementById('cursoId').value;
    
    // Pega a disciplina (se ele for monitor), senão manda null
    const idDisciplinaStr = document.getElementById('disciplinaId').value;
    const idDisciplina = idDisciplinaStr ? parseInt(idDisciplinaStr) : null;

    // Monta o objeto JSON exatamente como você fez no Postman
    const usuarioParaSalvar = {
        nome: nome,
        email: email,
        senha: senha,
        dataNascimento: dataNascimento,
        matricula: matricula,
        tipo: tipo,
        idCurso: parseInt(idCurso),
        idDisciplina: idDisciplina
    };

    let urlDaApi = 'http://localhost:8080/api/usuario/salvar';
    if (tipo === 'MONITOR') {
        urlDaApi += '?tokenMonitor=MONITOR123'; // Use o token exato que o seu Java espera
    }

    // Faz a requisição POST para o back-end
    fetch(urlDaApi, { // Ajuste a URL se precisar
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(usuarioParaSalvar)
    })
    .then(resposta => {
        if (!resposta.ok) {
            // se o token estiver errado
            return resposta.text().then(texto => { throw new Error(texto) });
        }
        return resposta.json();
    })
    .then(resposta => {
        if (!resposta.ok) {
            // Se der erro => lança para o catch
            return resposta.text().then(texto => { throw new Error(texto) });
        }
        return resposta.json();
    })
    .then(dadosSalvos => {
        // Sucesso!
        console.log("Usuário salvo com sucesso:", dadosSalvos);
        alert("Cadastro realizado com sucesso!");
        
        // Limpa o formulário após o sucesso
        formCadastro.reset(); 
    })
    .catch(erro => {
        console.error("Erro ao salvar:", erro);
        alert("Erro ao realizar cadastro: " + erro.message);
    });
});


// ======================================================================================
// ====================================================================================== 
// ====================================================================================== 
