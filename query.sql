create database sistema_carcerario;

use sistema_carcerario;

create table atendente (
  id int not null primary key auto_increment,
  nome varchar(255) not null
);

create table atendimento (
  id int not null primary key auto_increment,
  id_atendente int not null,
  data_hora datetime not null,
  data_entrada_unidade date not null,
  is_transferencia bool not null,
  procedencia varchar(255) not null,
  constraint id_atendente_fk foreign key (id) references atendente(id)
);

CREATE TABLE prisioneiro (
  id_prisioneiro INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  data_nascimento DATE NOT NULL,
  cpf VARCHAR(255),
  uf VARCHAR(255) NOT NULL,
  orientacao VARCHAR(255) NOT NULL,
  genero VARCHAR(255) NOT NULL,
  sexo VARCHAR(255) NOT NULL,
  raca VARCHAR(255) NOT NULL,
  nacionalidade VARCHAR(255) NOT NULL,
  estado_civil VARCHAR(255) NOT NULL,
  escolaridade VARCHAR(255) NOT NULL
);
