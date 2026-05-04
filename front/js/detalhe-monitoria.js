// 1. Pega toda a URL atual da página
const parametros = new URLSearchParams(window.location.search);

// 2. Extrai apenas o valor que está na chave 'id'
const idMonitoria = parametros.get('id');


function desenharTela(idMonitoria){
    fetch(`http://localhost:8080/api/monitoria/${idMonitoria}`)
    .then(resposta => resposta.json())
    .then(dadosDaMonitoria =>{

        const nomeMonitoria = document.getElementById('nomeMonitoria');
        nomeMonitoria.innerText = dadosDaMonitoria.nome;

        const cursoEMonitor = document.getElementById('cursoMonitor');
        cursoEMonitor.innerText = dadosDaMonitoria.curso + ` • Monitor(a):` + dadosDaMonitoria.monitor

        const descricao = document.getElementById('descricao'); 
        descricao.innerText = dadosDaMonitoria.descricao; 
        console.log(dadosDaMonitoria);

        //
        document.getElementById('tituloEncontro').innerText = dadosDaMonitoria.nome; // ou dadosDaMonitoria.descricao
        
        // Horário formatado (corta os segundos)
        const horaFormatada = dadosDaMonitoria.horario.substring(0, 5);
        document.getElementById('horarioEncontro').innerText = `🕒 ${horaFormatada}`; 
        
        const numeroDoDia = calcularProximaData(dadosDaMonitoria.diasDaSemana);
        document.getElementById('diaNumeroEncontro').innerText = numeroDoDia;

        const diaAbreviado = dadosDaMonitoria.diasDaSemana.substring(0, 3).toUpperCase();
        document.getElementById('diaTextoEncontro').innerText = diaAbreviado;
        //

        document.getElementById('linkEncontro').href = dadosDaMonitoria.link;
    })
}



// 3. Verifica se o ID realmente chegou
if (idMonitoria) {
    console.log("ID capturado com sucesso:", idMonitoria);
    desenharTela(idMonitoria);
    
} else {
    // Se o usuário tentar acessar a página direto sem clicar no botão
    alert("Nenhuma monitoria selecionada!");
    window.location.href = 'home.html'; // Manda de volta pra Home
}

function calcularProximaData(diaDaSemanaTexto) {
    // 1. Dicionário para traduzir o texto do banco para o padrão do JS (0 a 6)
    const mapaDias = {
        "domingo": 0, "segunda": 1, "terça": 2, "terca": 2, // cobrindo com e sem cedilha
        "quarta": 3, "quinta": 4, "sexta": 5, "sábado": 6, "sabado": 6
    };

    // Pega o texto do banco, tira espaços e deixa minúsculo para não dar erro
    const diaAlvo = mapaDias[diaDaSemanaTexto.toLowerCase().trim()];
    
    // Se vier um texto estranho do banco, retorna um traço
    if (diaAlvo === undefined) return "--"; 

    // 2. Pega a data exata de hoje
    const dataCalculada = new Date(); 
    const diaAtual = dataCalculada.getDay(); // Retorna de 0 a 6

    // 3. Calcula a diferença de dias
    let diferenca = diaAlvo - diaAtual;

    // Se o dia da aula já passou nesta semana (ex: hoje é terça e a aula é segunda),
    // a diferença dá negativa. Então somamos 7 para jogar para a próxima semana!
    if (diferenca < 0) {
        diferenca += 7;
    }

    // O JS é inteligente: se hoje é dia 30 e somamos +3, ele vira o mês sozinho para o dia 2!
    dataCalculada.setDate(dataCalculada.getDate() + diferenca);

    // Retorna apenas o número do dia. 
    // O padStart(2, '0') garante que dia 5 apareça como "05" para ficar bonito.
    return String(dataCalculada.getDate()).padStart(2, '0'); 
}

// ====================================================================================== 
// =================================== DESMATRICULAR ==================================== 
// ======================================================================================

const btnSair = document.getElementById("btnSairMonitoria");
btnSair.addEventListener('click', async () => {

    const urlParams = new URLSearchParams(window.location.search);
    const idMonitoria = urlParams.get('id');

    const usuarioString = localStorage.getItem('usuarioLogado');
    const usuario = JSON.parse(usuarioString);
    const idAluno = usuario.id;

    const confirmacao = confirm("Tem certeza que deseja sair desta monitoria?");
    if (!confirmacao) return; // Se o aluno clicar em "Cancelar", o código para aqui.

    // 3. O Gran Finale: Enviar o DELETE para o Spring Boot
    try {
        const urlApi = `http://localhost:8080/api/monitoria/${idMonitoria}/aluno/${idAluno}`;
        
        const resposta = await fetch(urlApi, {
            method: 'DELETE'
        });

        if (resposta.ok) {
            alert("Inscrição cancelada com sucesso!");
            window.location.href = "home.html"; // Manda o aluno de volta pra home
        } else {
            // Se o Spring Boot retornar algum erro (ex: 400 Bad Request)
            const erroMsg = await resposta.text();
            throw new Error(erroMsg);
        }

    } catch (erro) {
        console.error("Erro:", erro);
        alert("Não foi possível sair da monitoria.");
    }


});