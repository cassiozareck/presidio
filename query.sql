create database sistema_carcerario;

use sistema_carcerario;

create table atendente (
  id int not null primary key auto_increment,
  nome varchar(255) not null
);

CREATE TABLE prisioneiro (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  data_nascimento DATE NOT NULL,
  nome VARCHAR(255) NOT NULL,
  nome_mae VARCHAR(255) NOT NULL,
  cpf VARCHAR(255) NOT NULL,
  uf VARCHAR(255) NOT NULL,
  orientacao VARCHAR(255) NOT NULL,
  genero VARCHAR(255) NOT NULL,
  sexo VARCHAR(255) NOT NULL,
  raca VARCHAR(255) NOT NULL,
  nacionalidade VARCHAR(255) NOT NULL,
  estado_civil VARCHAR(255) NOT NULL,
  escolaridade VARCHAR(255) NOT NULL
);

create table atendimento (
  id int not null primary key auto_increment,
  id_atendente int not null,
  id_prisioneiro int not null,
  data_hora datetime not null,
  data_entrada_unidade date not null,
  is_transferencia bool not null,
  procedencia varchar(255) not null,
  constraint id_atendente_fk foreign key (id_atendente) references atendente(id),
  constraint id_prisioneiro_fk foreign key (id_prisioneiro) references prisioneiro(id)
);

