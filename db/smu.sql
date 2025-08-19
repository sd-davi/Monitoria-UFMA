CREATE TYPE tipo_usuario_enum AS ENUM ('ALUNO', 'MONITOR');

CREATE TABLE usuario (
  id_usuario SERIAL PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  senha VARCHAR(255) NOT NULL,
  matricula VARCHAR(30) UNIQUE NOT NULL,
  data_nascimento TIMESTAMP NOT NULL,
  tipo tipo_usuario_enum NOT NULL,
  curso_id INTEGER REFERENCES curso(id_curso),
  disciplina_id INTEGER REFERENCES disciplina(id_disciplina)
);

CREATE TABLE curso (
  id_curso SERIAL PRIMARY KEY,
  codigo VARCHAR(20) NOT NULL,
  nome VARCHAR(100) NOT NULL
);
CREATE TABLE disciplina (
  id_disciplina SERIAL PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  codigo VARCHAR(20) UNIQUE NOT NULL,
  curso_id INTEGER REFERENCES curso(id_curso)
);

CREATE TABLE aluno_disciplina (
  aluno_id INTEGER REFERENCES usuario(id_usuario),
  disciplina_id INTEGER REFERENCES disciplina(id_disciplina),
  PRIMARY KEY (aluno_id, disciplina_id)
);

CREATE TABLE monitoria (
  id_monitoria SERIAL PRIMARY KEY,
  titulo VARCHAR(255),
  descricao TEXT,
  observacoes TEXT,
  link TEXT,
  monitor_id INTEGER REFERENCES usuario(id_monitor),
  curso_id INTEGER REFERENCES curso(id_curso),
  disciplina_id INTEGER REFERENCES disciplina(id_disciplina)
);

CREATE TABLE monitoria_aluno (
  monitoria_id INTEGER REFERENCES monitoria(id_monitoria),
  aluno_id INTEGER REFERENCES usuario(id_usuario),
  PRIMARY KEY (monitoria_id, aluno_id)
);

CREATE TABLE sessao (
  id_sessao SERIAL PRIMARY KEY,
  horario TIMESTAMP NOT NULL,
  link TEXT,
  status VARCHAR(20) DEFAULT 'agendada',
  monitoria_id INTEGER REFERENCES monitoria(id_monitoria),
  aluno_id INTEGER REFERENCES usuario(id_usuario)
);
CREATE TABLE horario_monitoria (
  id_horario SERIAL PRIMARY KEY,
  dia_semana INTEGER CHECK (dia_semana BETWEEN 0 AND 6),
  hora_inicio TIME,
  hora_fim TIME,
  monitoria_id INTEGER REFERENCES monitoria(id_monitoria)
);


CREATE TABLE material (
  id_material SERIAL PRIMARY KEY,
  titulo VARCHAR(255) NOT NULL,
  descricao TEXT,
  tipo VARCHAR(10) CHECK (tipo IN ('arquivo', 'link')),
  link TEXT,
  caminho_arquivo TEXT,
  monitoria_id INTEGER REFERENCES monitoria(id_monitoria)
);


CREATE TABLE atividade (
  id_atividade SERIAL PRIMARY KEY,
  titulo VARCHAR(255),
  descricao TEXT,
  link_formulario TEXT NOT NULL,
  monitoria_id INTEGER REFERENCES monitoria(id_monitoria)
);

CREATE TABLE atividade_resposta (
  atividade_id INTEGER REFERENCES atividade(id_atividade),
  aluno_id INTEGER REFERENCES usuario(id_usuario),
  data_resposta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  link_resposta TEXT,
  PRIMARY KEY (atividade_id, aluno_id)
);


CREATE TABLE comentario (
  id_comentario SERIAL PRIMARY KEY,
  texto TEXT NOT NULL,
  nota INTEGER CHECK (nota BETWEEN 0 AND 5),
  data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  autor_id INTEGER REFERENCES usuario(id_usuario),
  monitoria_id INTEGER REFERENCES monitoria(id_monitoria)
);

CREATE TABLE notificacao (
  id_notificacao SERIAL PRIMARY KEY,
  mensagem TEXT NOT NULL,
  lida BOOLEAN DEFAULT FALSE,
  data_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  autor_id INTEGER REFERENCES usuario(id_usuario),
  destinatario_id INTEGER REFERENCES usuario(id_usuario)
);


CREATE TABLE mensagem (
  id_mensagem SERIAL PRIMARY KEY,
  conteudo TEXT NOT NULL,
  data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  autor_id INTEGER REFERENCES usuario(id_usuario),
  sessao_id INTEGER REFERENCES sessao(id_sessao)
);

CREATE TABLE avaliacao (
  id_avaliacao SERIAL PRIMARY KEY,
  nota INTEGER CHECK (nota BETWEEN 1 AND 5),
  comentario TEXT,
  data_avaliacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  sessao_id INTEGER REFERENCES sessao(id_sessao),
  aluno_id INTEGER REFERENCES usuario(id_usuario)
);
