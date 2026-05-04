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
document.addEventListener('DOMContentLoaded', carregarCursos);


// ====================================================================================== 
// ============================ CARREGAR DISCIPLINAS ==================================== 
// ======================================================================================
const selectCurso = document.getElementById('cursoId');
const selectDisc =  document.getElementById('idDisciplina');
selectCurso.addEventListener('change', ()=>{
        const cursoId = selectCurso.value;
        console.log(cursoId);

        const urlDaApi = `http://localhost:8080/api/curso/${cursoId}/disciplinas`;
        fetch(urlDaApi)
        .then(resposta => {
            if (!resposta.ok) {
                throw new Error("Erro na rede ao buscar as discplinas");
            }
            return resposta.json();
        })
        .then(disciplinas => {
            disciplinas.forEach(disc => {
                const opt = document.createElement('option');
                opt.value = disc.disciplinaId; // Use o nome do atributo do seu DTO
                opt.textContent = disc.disciplinaNome;
                selectDisc.appendChild(opt);
            });
        })

    })



// ====================================================================================== 
// =============================== CADASTRAR MONITORIA ================================== 
// ======================================================================================

const formCadastro = document.getElementById('formCriarMonitoria');
formCadastro.addEventListener('submit', function(event) {
    event.preventDefault();

    const usuarioString = localStorage.getItem('usuarioLogado');
    const usuarioLogado = JSON.parse(usuarioString);

    const monitoriaParaSalvar ={
        nome: document.getElementById('nome').value,
        descricao: document.getElementById('descricao').value,
        diasDaSemana: document.getElementById('diasDaSemana').value,
        horario: document.getElementById('horario').value, 
        link: document.getElementById('link').value,
        
        idCurso: parseInt(document.getElementById('cursoId').value, 10), 
        idDisciplina: parseInt(document.getElementById('idDisciplina').value, 10), // Aquele select que arrumamos!
        idMonitor: usuarioLogado.id

    }

    console.log("Objeto pronto para envio:", monitoriaParaSalvar);

    const urlApi = `http://localhost:8080/api/monitoria/salvar`;
    fetch(urlApi, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(monitoriaParaSalvar)
    })
    .then(resposta => {
        if (!resposta.ok) {
            return resposta.text().then(texto => { throw new Error(texto) });
        }
        return resposta.json();
    })
    .then(dadosSalvos => {
        // Sucesso!
        console.log("Monitoria cadastrada com sucesso:", dadosSalvos);
        alert("Cadastro realizado com sucesso!");
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