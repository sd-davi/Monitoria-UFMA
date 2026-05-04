document.querySelector('form').addEventListener('submit', function(event) {
    event.preventDefault(); // Evita que a página recarregue

    // Pega os valores que o usuário digitou
    const emailDigitado = document.getElementById('email').value; // Ajuste o ID se o seu for diferente
    const senhaDigitada = document.getElementById('senha').value; // Ajuste o ID se o seu for diferente
    // Monta o JSON igualzinho o seu UsuarioDto de Java espera
    const dadosLogin = {
        email: emailDigitado,
        senha: senhaDigitada,
    };

    // Faz a chamada para o Spring Boot
    fetch('http://localhost:8080/api/usuario/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dadosLogin)
    })
    .then(resposta => {
        if (resposta.ok) {
            return resposta.json(); // Se deu 200 OK, pega o objeto do usuário
        } else {
            throw new Error('Email ou senha incorretos!'); // Cai no catch
        }
    })
    .then(usuarioLogado => {
        // DEU CERTO! O Java deixou entrar!
        
        // Vamos guardar o usuário inteiro no navegador. 
        // Assim, na tela Home, saberemos o nome dele e se ele é Aluno ou Monitor!
        localStorage.setItem('usuarioLogado', JSON.stringify(usuarioLogado));
        
        // Redireciona para a página principal
        window.location.href = 'home.html'; 
    })
    .catch(erro => {
        console.error("Erro:", erro);
        // Mostra a mensagem de erro (CORS, servidor desligado ou senha errada)
        alert("Erro ao entrar! Verifique se os dados estão corretos e se o CORS está ativado no Java.");
    });
});