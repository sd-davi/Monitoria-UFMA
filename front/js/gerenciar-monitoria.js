const usuarioString = localStorage.getItem('usuarioLogado');
if (!usuarioString) {
    window.location.href = 'login.html';
}

const urlParams = new URLSearchParams(window.location.search);
const idMonitoria = urlParams.get('id');

if (!idMonitoria) {
    alert("Nenhuma monitoria selecionada!");
    window.location.href = 'home.html';
} else {
    carregarDadosDaMonitoria(idMonitoria);
}
// ====================================================================================== 
// ================================ CARREGAR MONITORIA ================================== 
// ======================================================================================

function carregarDadosDaMonitoria(id) {
    fetch(`http://localhost:8080/api/monitoria/${id}`)
        .then(resposta => {
            if (!resposta.ok) {
                throw new Error("Erro ao buscar dados da monitoria");
            }
            return resposta.json();
        })
        .then(monitoria => {
            console.log(monitoria);
            document.getElementById('tituloMonitoria').innerText = monitoria.nome;

            // 2. AQUI ESTÁ O PULO DO GATO: Pega a quantidade de alunos na sua lista!
            // Se monitoria.alunos existir, pega o tamanho (.length), senão é 0.
            const qtdAlunos = monitoria.alunos ? monitoria.alunos.length : 0;
            document.getElementById('qtdAlunos').innerText = qtdAlunos;

            // 3. Injeta Horários
            const dias = monitoria.diasDaSemana ? monitoria.diasDaSemana : "Dias não definidos";
            const hora = monitoria.horario ? monitoria.horario : "Horário não definido";
            document.getElementById('horarioMonitoria').innerText = `${dias}, às ${hora}`;

            // 4. Injeta Link
            if (monitoria.link) {
                document.getElementById('linkMeet').value = monitoria.link;
            } else {
                document.getElementById('linkMeet').value = "Nenhum link cadastrado.";
            }
        })
        .catch(erro => {
            console.error("Erro:", erro);
            alert("Falha ao carregar as informações desta sala.");
        });
}


// ====================================================================================== 
// =================================== EDIÇÃO =========================================== 
// ======================================================================================

const btnEditar = document.getElementById('btn-editar-monitoria');

console.log(idMonitoria);
if (btnEditar) {
    btnEditar.addEventListener('click', () => {

        window.location.href = `criar-monitoria.html?id=${idMonitoria}`;
    });
}

// ====================================================================================== 
// =================================== DELETAR ========================================== 
// ======================================================================================

const btnDelete = document.getElementById('encerrarMonitoria');
if (btnDelete) {
    btnDelete.addEventListener('click', async () => {
        const urlParams = new URLSearchParams(window.location.search);
        const idMonitoria = urlParams.get('id');

        const confirmacao = confirm("Tem certeza que deseja excluir esta monitoria?");
        if (!confirmacao) return;

        try {
        const urlApi = `http://localhost:8080/api/monitoria/deletar/${idMonitoria}`;
        
        const resposta = await fetch(urlApi, {
            method: 'DELETE'
        });

        if (resposta.ok) {
            alert("Monitoria excluida com sucesso!");
            window.location.href = "home.html"; // Manda o aluno de volta pra home
        } else {
            // Se o Spring Boot retornar algum erro (ex: 400 Bad Request)
            const erroMsg = await resposta.text();
            throw new Error(erroMsg);
        }

    } catch (erro) {
        console.error("Erro:", erro);
        alert("Não foi possível excluir a monitoria.");
    }
    });
}
