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
  escolaridade VARCHAR(255) NOT NULL,
  beneficio_familia BOOL NOT NULL,
  beneficio_especificado VARCHAR(255) NOT NULL,
  possui_filhos BOOL NOT NULL,
  quantos_filhos VARCHAR(255) NOT NULL,
  idade INT NOT NULL,
  possui_dependentes BOOL NOT NULL,
  quantos_dependentes INT(255) NOT NULL,
  ofertar_neeja BOOL NOT NULL,
  ofertar_assistencia_social BOOL NOT NULL,
  possui_deficiencia INT NOT NULL, --Atenção ao tipo
  qual_deficiencia VARCHAR(255) NOT NULL,
  possui_alergias INT NOT NULL, --Atenção ao tipo
  quais_alergias VARCHAR(255) NOT NULL,
  realizou_cirurgias INT NOT NULL,
  quais_cirurgias VARCHAR(255) NOT NULL,
  
  --Condições crônicas
  hipertencao BOOL,
  diabetes BOOL,
  hiv BOOL,
  autoimune BOOL, 
  outras_doencas_cronicas VARCHAR(255) NOT NULL,
  nao_sabe_responder_condicoes_cronicas BOOL NOT NULL, --Atenção 
  observacao_condicoes_cronicas VARCHAR(255) NOT NULL,
  
  --Historico doenças infecciosas
  sifilis BOOL,
  hpv BOOL,
  tuberculose BOOL,
  hepatite_b BOOL,
  hepatite_c BOOL,
  outras_doencas_infecciosas VARCHAR(255) NOT NULL,
  nao_sabe_responder_doencas_infecciosas BOOL NOT NULL, --Atenção 
  observacao_historico_doencas_infecciosas VARCHAR(255) NOT NULL,
  
  --Possui doença de pele
  doenca_pele BOOL, --Atenção ao tipo
  quais_doencas_pele VARCHAR(255) NOT NULL,
  nao_sabe_responder_doencas_pele BOOL NOT NULL, --Atenção 
  observacao_historico_doencas_pele VARCHAR(255) NOT NULL,

  medicamentos_continuos BOOL NOT NULL,
  quais_medicamentos VARCHAR(255) NOT NULL,
  
  ---- SAÚDE MENTAL E USO DE SUBSTANCIAS
  
  tipo_sanguineo INT NOT NULL, --Atenção várias opções
  encaminhamento VARCHAR(1000),
  
  vinculo_caps BOOL NOT NULL,
  nome_municio_caps VARCHAR(500),
  
  ansiedade BOOL,
  depressao BOOL,
  bipolaridade BOOL,
  esquizofrenia BOOL,
  autismo BOOL,
  outra_saude_mental VARCHAR(255),
  nao_sabe_responder_saude_mental BOOL NOT NULL
  
  medicamento_controlado BOOL,
  qual_medicamento_controlado VARCHAR(255),
  nao_sabe_responder_medicamento_controlado BOOL NOT NULL,
  
  acompanhamento_mental_momento_prisao BOOL NOT NULL,
  motivo_acompanhamento_mental VARCHAR(255),
  
  alcool BOOL NOT NULL,
  cigarro BOOL NOT NULL,
  maconha BOOL NOT NULL,
  crack BOOL NOT NULL,
  cocaina BOOL NOT NULL,
  anfetaminas BOOL NOT NULL,
  drogas BOOL NOT NULL,
  outras_drogas VARCHAR(255),
  
  tratamento_reabilitacao BOOL NOT NULL,
  tratamento_qual_substancia VARCHAR(255),
  
  quer_reabilitacao BOOL NOT NULL,
  reabilitacao_qual_substancia VARCHAR(255),
  
  ofertar_psicologa BOOL NOT NULL,
  ofertar_psiquiatra BOOL NOT NULL,
  encaminhar_receitas BOOL NOT NULL,
  encaminhar_grupo_apoio BOOL NOT NULL,
  
  
  --- SITUAÇÃO VACINAL
  vacina_covid INT NOT NULL, --Atenção ao tipo
  vacina_influenza INT NOT NULL,
  vacina_tetano INT NOT NULL,
  vacina_hepatite INT NOT NULL,
  
  ofertar_vacinas BOOL NOT NULL,
  febre_amarela BOOL NOT NULL,
  hepatite_b BOOL NOT NULL,
  covid_19 BOOL NOT NULL,
  influenza BOOL NOT NULL,
  dupla_adulto BOOL NOT NULL,
  triplice_viral BOOL NOT NULL,
  
  outra_vacina VARCHAR(255),
  ofertar_carteira_vacinacao BOOL NOT NULL,
  
  ----- PRIMEIRO ATENDIMENTO CLINICO
  peso INT NOT NULL,
  altura FLOAT NOT NULL,
  imc FLOAT NOT NULL,
  
  pa VARCHAR NOT NULL,
  fc FLOAT NOT NULL,
  sat FLOAT NOT NULL,
  temp FLOAT NOT NULL,
  
  tosse BOOL NOT NULL,
  coriza BOOL NOT NULL,
  espirros BOOL NOT NULL,
  febre BOOL NOT NULL,
  calafrios BOOL NOT NULL,
  
  outros_sistemas_respiratorios VARCHAR(255),
  data_sintomas DATE,
  
  apresenta_lesoes BOOL NOT NULL,
  local_lesoes VARCHAR(255),
);

CREATE TABLE atendimento_clinico(
	
)


CREATE TABLE saude_mulher(
	id INT NOT NULL PRIMARY KEY auto_increment,
	id_prisioneiro INT NOT NULL,
	gestacao INT NOT NULL,
	idade_gestacional INT,
	usa_contraceptivo BOOL NOT NULL,
	anticoncepcional_oral BOOL NOT NULL,
	diu_implante BOOL NOT NULL,
	anticoncepcional_injetavel BOOL NOT NULL,
	ligadura_trompas BOOL NOT NULL,
	histerectomia BOOL NOT NULL
	
	exame_preventivo_papanicolau BOOL NOT NULL,
	exame_preventivo_papanicolau_ano INT,
	
	--encaminhamentos
	ofertar_continuidade_contraceptivo BOOL NOT NULL,
	ofertar_consulta_preventivo BOOL NOT NULL,
	encaminhar_pre_natal BOOL NOT NULL,
	CONSTRAINT id_saude_mulher_fk foreign key (id_prisioneiro) references prisioneiro(id)
)

CREATE TABLE saude_homem(
	id INT NOT NULL PRIMARY KEY auto_increment,
	id_prisioneiro INT NOT NULL,
	realizou_exame_prostata BOOL NOT NULL,
	ano_exame_prostata INT,
	historico_cancer_familia BOOL NOT NULL,
	qual_familiar_cancer VARCHAR(255),
	realizou_vasectomia BOOL NOT NULL,
	parceira_gestante BOOL NOT NULL,
	participa_pre_natal BOOL NOT NULL,
	
	--encaminhamentos
	ofertar_encaminhamento_vasectomia BOOL NOT NULL,
	ofertar_encaminhamento_pre_natal BOOL NOT NULL,
	CONSTRAINT id_saude_homem_fk FOREIGN KEY (id_prisioneiro) REFERENCES prisioneiro(id)
)


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

