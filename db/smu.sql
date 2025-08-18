CREATE TABLE avaliacao (
    id SERIAL PRIMARY KEY,
    avaliacaoId BIGINT,
    estrelas INT,
    comentario VARCHAR(255),
    sessao_id INT REFERENCES sessao(id),
    aluno_id INT REFERENCES usuario(id)
);

CREATE TABLE curso (
    id SERIAL PRIMARY KEY
    nome character varying(100) NOT NULL
);

CREATE TABLE disciplina (
    id SERIAL PRIMARY KEY
);

CREATE TABLE material (
    id_material SERIAL PRIMARY KEY,
    dataEnvio TIMESTAMP
    arquivo character varying(255),
    link_externo character varying(255),
    titulo character varying(100) NOT NULL,
    monitoria_id bigint NOT NULL
);

CREATE TABLE mensagem (,
    mensagemId  SERIAL PRIMARY KEY,
    horario TIMESTAMP,
    conteudo VARCHAR(255),
    autor_id INT REFERENCES usuario(id),
    sessao_id INT REFERENCES sessao(id)
);

CREATE TABLE monitoria (
    id_monitoria SERIAL PRIMARY KEY,
    dias_da_semana VARCHAR(255),
    horario TIMESTAMP,
    link VARCHAR(255)
);

CREATE TABLE notificacao (
    id SERIAL PRIMARY KEY,
    Notificacaoid INT,
    mensagem VARCHAR(255),
    horario TIMESTAMP,
    destinatario_id INT REFERENCES usuario(id)
);

CREATE TABLE sessao (
    id SERIAL PRIMARY KEY,
    status VARCHAR(255)
);

CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    curso_id INT REFERENCES curso(id)
);
