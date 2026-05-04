document.addEventListener("DOMContentLoaded", () => {

    // 1. PUXAR DO LOCAL STORAGE
    const usuarioString = localStorage.getItem('usuarioLogado');

    if (!usuarioString) {
        alert("Você precisa estar logado para ver o perfil!");
        window.location.href = "login.html"; // Manda o penetra de volta pro login
        return;
    }

    const usuarioLogado = JSON.parse(usuarioString);

    // 2. PREENCHER O FORMULÁRIO COM OS DADOS ATUAIS
    document.getElementById('nome').value = usuarioLogado.nome;
    document.getElementById('email').value = usuarioLogado.email || '';
    document.getElementById('matricula').value = usuarioLogado.matricula || '';
    document.getElementById('dataNascimento').value = usuarioLogado.dataNascimento || '';

    // 3. ENVIAR A ATUALIZAÇÃO (O SEU ENDPOINT PUT)
    const formPerfil = document.getElementById('formPerfil');

    formPerfil.addEventListener('submit', (evento) => {
        evento.preventDefault(); // Impede a página de recarregar sozinha

        

        // Montamos o objeto com os dados novos da tela + os dados antigos que não mudam
        const dadosAtualizados = {
            nome: document.getElementById('nome').value,
            email: document.getElementById('email').value,
            senha: document.getElementById('senha').value,
            dataNascimento: document.getElementById('dataNascimento').value || usuarioLogado.dataNascimento,
            tipo: usuarioLogado.tipo,
        };
        console.log(dadosAtualizados);

        // Troque 'usuarios' pelo caminho correto do seu controller (ex: /api/usuario)
        const urlApi = `http://localhost:8080/api/usuario/${usuarioLogado.id}`;

        fetch(urlApi, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dadosAtualizados)
        })
            .then(resposta => {
                if (!resposta.ok) {
                    throw new Error("Erro no servidor ao atualizar");
                }
                return resposta.json();
            })
            .then(dadosSalvos => {
                alert("Perfil atualizado com sucesso!");

                // DICA DE OURO: Precisamos atualizar o Local Storage também!
                // Se o utilizador mudar de nome, o sistema tem que lembrar do nome novo sem ele precisar relogar.
                usuarioLogado.nome = dadosAtualizados.nome;
                usuarioLogado.email = dadosAtualizados.email;

                localStorage.setItem('usuarioLogado', JSON.stringify(usuarioLogado));
            })
            .catch(erro => {
                console.error("Erro:", erro);
                alert("Falha ao atualizar o perfil. Tente novamente.");
            });
    });
});