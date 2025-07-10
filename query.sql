-- Alguns bools foram substítuidos por 0 -> não, 1 -> sim - 2 -> não sabe responder
DROP DATABASE IF EXISTS sistema_carcerario;

create database sistema_carcerario;

use sistema_carcerario;

create table atendente (
  id int not null primary key auto_increment,
  nome varchar(255) not null
);

CREATE TABLE prisioneiro (
  -- DADOS PRIMARIOS
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  nome_completo VARCHAR(255) NOT NULL,
  nome_social VARCHAR(255) NOT NULL,
  data_nascimento DATE NOT NULL,
  idade INT NOT NULL,
  cpf VARCHAR(255) NOT NULL,
  nacionalidade VARCHAR(255) NOT NULL,

  nome_mae VARCHAR(255) NOT NULL,
  estado_civil VARCHAR(255) NOT NULL,
  raca VARCHAR(255) NOT NULL,
  orientacao VARCHAR(255) NOT NULL,
  genero VARCHAR(255) NOT NULL,
  sexo VARCHAR(255) NOT NULL,

  -- DADOS SOCIAIS
  escolaridade VARCHAR(255) NOT NULL, 
  beneficio_familia BOOL NOT NULL,
  beneficio_especificado VARCHAR(255) NOT NULL,
  possui_filhos BOOL NOT NULL,
  quantos_filhos INT NOT NULL,
  possui_dependentes BOOL NOT NULL,
  quantos_dependentes INT NOT NULL,
  ofertar_neeja BOOL NOT NULL,
  ofertar_assistencia_social BOOL NOT NULL,
  possui_deficiencia INT NOT NULL, -- Atenção ao tipo, 0 -> não, 1 -> sim - 2 -> não sabe responder
  qual_deficiencia VARCHAR(255) NOT NULL,
  possui_alergias INT NOT NULL, -- Atenção ao tipo
  quais_alergias VARCHAR(255) NOT NULL,
  
  -- Condições crônicas
  hipertencao BOOL,
  diabetes BOOL,
  hiv BOOL,
  autoimune BOOL, 
  outras_doencas_cronicas VARCHAR(255) NOT NULL,
  nao_sabe_responder_condicoes_cronicas BOOL NOT NULL,
  observacao_condicoes_cronicas VARCHAR(255) NOT NULL,
  
  -- Historico doenças infecciosas
  sifilis BOOL,
  hpv BOOL,
  tuberculose BOOL,
  hepatite_b BOOL,
  hepatite_c BOOL,
  outras_doencas_infecciosas VARCHAR(255) NOT NULL,
  nao_sabe_responder_doencas_infecciosas BOOL NOT NULL,
  observacao_historico_doencas_infecciosas VARCHAR(255) NOT NULL,
  
  -- Realizou cirurgias
  realizou_cirurgias BOOL NOT NULL,
  quais_cirurgias VARCHAR(255) NOT NULL,
  nao_sabe_responder_cirurgias BOOL NOT NULL,
  
  -- Possui doença de pele
  doenca_pele BOOL,
  quais_doencas_pele VARCHAR(255) NOT NULL,
  nao_sabe_responder_doencas_pele BOOL NOT NULL, -- Atenção 
  observacao_historico_doencas_pele VARCHAR(255) NOT NULL,

  medicamentos_continuos BOOL NOT NULL,
  quais_medicamentos VARCHAR(255) NOT NULL,
  
  tipo_sanguineo VARCHAR(255) NOT NULL,

  -- SAÚDE MENTAL E USO DE SUBSTANCIAS
  vinculo_caps BOOL NOT NULL,
  nome_municio_caps VARCHAR(500),
  
  ansiedade BOOL,
  depressao BOOL,
  bipolaridade BOOL,
  esquizofrenia BOOL,
  autismo BOOL,
  outra_saude_mental VARCHAR(255),
  nao_sabe_responder_saude_mental BOOL NOT NULL,  
  medicamento_controlado INT NOT NULL, -- Atenção ao tipo
  qual_medicamento_controlado VARCHAR(255),

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
  
  
  -- SITUAÇÃO VACINAL
  vacina_covid INT NOT NULL, -- Atenção ao tipo
  vacina_influenza INT NOT NULL,
  vacina_tetano INT NOT NULL,
  vacina_hepatite INT NOT NULL,
  
  ofertar_vacinas BOOL NOT NULL,
  ofertar_vacina_febre_amarela BOOL NOT NULL,
  ofertar_vacina_hepatite_b BOOL NOT NULL,
  ofertar_vacina_covid_19 BOOL NOT NULL,
  ofertar_vacina_influenza BOOL NOT NULL,
  ofertar_vacina_dupla_adulto BOOL NOT NULL,
  ofertar_vacina_triplice_viral BOOL NOT NULL,
  
  outra_vacina VARCHAR(255),
  ofertar_carteira_vacinacao BOOL NOT NULL,
  
  encaminhamentos_finais VARCHAR(255) NOT NULL
);

CREATE TABLE saude_mulher(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_prisioneiro INT NOT NULL,

  -- DADOS SOBRE A SAÚDE MULHER
	gestacao INT NOT NULL,
	idade_gestacional INT,
	qual_contraceptivo VARCHAR(255) NOT NULL,
	exame_preventivo_papanicolau BOOL NOT NULL,
	exame_preventivo_papanicolau_ano INT,
	
	-- Encaminhamentos
	ofertar_continuidade_contraceptivo BOOL NOT NULL,
	ofertar_consulta_preventivo BOOL NOT NULL,
	encaminhar_pre_natal BOOL NOT NULL,
	CONSTRAINT id_saude_mulher_fk FOREIGN KEY (id_prisioneiro) REFERENCES prisioneiro(id)
);

CREATE TABLE saude_homem(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_prisioneiro INT NOT NULL,

  -- DADOS SOBRE A SAÚDE HOMEM
	realizou_exame_prostata BOOL NOT NULL,
	ano_exame_prostata INT,
	historico_cancer_prostata_familia BOOL NOT NULL,
	qual_familiar_cancer_prostata VARCHAR(255),
	realizou_vasectomia BOOL NOT NULL,
	parceira_gestante BOOL NOT NULL,
	participa_pre_natal BOOL NOT NULL,
	
	-- encaminhamentos
	ofertar_encaminhamento_vasectomia BOOL NOT NULL,
	ofertar_encaminhamento_pre_natal BOOL NOT NULL,
	CONSTRAINT id_saude_homem_fk FOREIGN KEY (id_prisioneiro) REFERENCES prisioneiro(id)
);

create table atendimento (
  -- DADOS DO ATENDIMENTO
  id int not null primary key auto_increment,
  id_atendente int not null,
  id_prisioneiro int not null,
  data_hora datetime not null,
  data_entrada_unidade date not null,
  is_transferencia bool not null,
  procedencia varchar(255) not null,


  -- ATENDIMENTO CLINICO
  peso INT NOT NULL,
  altura FLOAT NOT NULL,
  imc FLOAT NOT NULL,
  
  pa VARCHAR(255) NOT NULL,
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
  local_lesoes VARCHAR(255) NOT NULL,

  conduta_lesoes_clinica VARCHAR(255) NOT NULL,
  hiv_1_2_lote VARCHAR(255) NOT NULL,
  hiv_1_2_validade DATE NOT NULL, 
  hiv_1_2_reativo BOOL, 
  hiv_2_2_lote VARCHAR(255) NOT NULL,
  hiv_2_2_validade DATE NOT NULL,
  hiv_2_2_reativo BOOL,
  sifilis_lote VARCHAR(255) NOT NULL,
  sifilis_validade DATE NOT NULL,
  sifilis_reativo BOOL,
  hepatite_b_lote VARCHAR(255) NOT NULL,
  hepatite_b_validade DATE NOT NULL,
  hepatite_b_reativo BOOL,
  hepatite_c_lote VARCHAR(255) NOT NULL,
  hepatite_c_validade DATE NOT NULL,
  hepatite_c_reativo BOOL,
  covid_lote VARCHAR(255) NOT NULL,
  covid_validade DATE NOT NULL,
  covid_reativo BOOL,
  
  teste_gravidez BOOL,
  coleta_escarro BOOL NOT NULL,

  apresenta_queixas_teste_rapido BOOL NOT NULL,
  queixa_teste_rapido VARCHAR(255) NOT NULL,
  conduta_teste_rapido VARCHAR(255) NOT NULL,
  conduta_clinica VARCHAR(255) NOT NULL,

  tem_queixa_odontologica BOOL NOT NULL,
  queixa_odontologica VARCHAR(255) NOT NULL,
  necessita_dentista BOOL NOT NULL,
  conduta_odontologica VARCHAR(255) NOT NULL,

  constraint id_atendente_fk foreign key (id_atendente) references atendente(id),
  constraint id_prisioneiro_fk foreign key (id_prisioneiro) references prisioneiro(id)
);

