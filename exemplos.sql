-- Inserir atendentes
INSERT INTO atendente (nome) VALUES
('Dra. Amanda Ribeiro Silva'),
('Dr. Felipe Costa Santos'),
('Dra. Renata Almeida Oliveira'),
('Dr. Carlos Eduardo Mendes'),
('Dra. Juliana Pereira Lima');

-- Inserir prisioneiros com dados completos
INSERT INTO prisioneiro (
  nome_completo, nome_social, data_nascimento, idade, cpf, nacionalidade,
  nome_mae, estado_civil, raca, orientacao, genero, sexo,
  escolaridade, beneficio_familia, beneficio_especificado, possui_filhos, quantos_filhos,
  possui_dependentes, quantos_dependentes, ofertar_neeja, ofertar_assistencia_social,
  possui_deficiencia, qual_deficiencia, possui_alergias, quais_alergias,
  realizou_cirurgias, quais_cirurgias, nao_sabe_responder_cirurgias,
  hipertencao, diabetes, hiv, autoimune, outras_doencas_cronicas, nao_sabe_responder_condicoes_cronicas, observacao_condicoes_cronicas,
  sifilis, hpv, tuberculose, hepatite_b, hepatite_c, outras_doencas_infecciosas, nao_sabe_responder_doencas_infecciosas, observacao_historico_doencas_infecciosas,
  doenca_pele, quais_doencas_pele, nao_sabe_responder_doencas_pele, observacao_historico_doencas_pele,
  medicamentos_continuos, quais_medicamentos, tipo_sanguineo,
  vinculo_caps, nome_municio_caps, ansiedade, depressao, bipolaridade, esquizofrenia, autismo, outra_saude_mental, nao_sabe_responder_saude_mental,
  medicamento_controlado, qual_medicamento_controlado, acompanhamento_mental_momento_prisao, motivo_acompanhamento_mental,
  alcool, cigarro, maconha, crack, cocaina, anfetaminas, drogas, outras_drogas,
  tratamento_reabilitacao, tratamento_qual_substancia, quer_reabilitacao, reabilitacao_qual_substancia,
  ofertar_psicologa, ofertar_psiquiatra, encaminhar_receitas, encaminhar_grupo_apoio,
  vacina_covid, vacina_influenza, vacina_tetano, vacina_hepatite,
  ofertar_vacinas, ofertar_vacina_febre_amarela, ofertar_vacina_hepatite_b, ofertar_vacina_covid_19, ofertar_vacina_influenza, ofertar_vacina_dupla_adulto, ofertar_vacina_triplice_viral,
  outra_vacina, ofertar_carteira_vacinacao, encaminhamentos_finais
) VALUES
-- Prisioneiro 1: André Luiz (Homem)
('André Luiz de Souza', 'André', '1986-04-20', 39, '123.456.789-00', 'Brasileira',
 'Maria das Dores Souza', 'Solteiro', 'Pardo', 'Heterossexual', 'Homem', 'Masculino',
 'Ensino Médio Completo', true, 'Bolsa Família', true, 2,
 true, 1, true, true,
 0, 'Nenhuma', 1, 'Pólen, poeira',
 true, 'Apendicectomia (2010)', false,
 false, false, false, false, 'Nenhuma', false, 'Sem observações',
 false, false, false, false, false, 'Nenhuma', false, 'Sem histórico',
 false, 'Nenhuma', false, 'Sem observações',
 false, 'Nenhum', 'O+',
 false, NULL, false, false, false, false, false, NULL, false,
 0, NULL, false, NULL,
 true, true, false, false, false, false, false, NULL,
 false, NULL, true, 'Álcool',
 true, true, true, true,
 1, 1, 1, 1,
 true, true, true, true, true, true, true,
 'Nenhuma', true, 'Encaminhamento para psicólogo e psiquiatra'),

-- Prisioneiro 2: Bruna Karla (Mulher)
('Bruna Karla Mendes', 'Bruna', '1991-07-11', 34, '987.654.321-00', 'Brasileira',
 'Luciana Karla Mendes', 'Casada', 'Branca', 'Homossexual', 'Mulher', 'Feminino',
 'Ensino Superior Completo', false, 'Nenhum', true, 1,
 false, 0, true, true,
 0, 'Nenhuma', 0, 'Nenhuma',
 false, 'Nenhuma', false,
 true, false, false, false, 'Hipertensão controlada', false, 'Usa medicamento diário',
 false, false, false, false, false, 'Nenhuma', false, 'Sem histórico',
 false, 'Nenhuma', false, 'Sem observações',
 true, 'Losartana 50mg', 'A+',
 false, NULL, true, true, false, false, false, NULL, false,
 1, 'Sertralina 50mg', true, 'Ansiedade e depressão',
 false, true, false, false, false, false, false, NULL,
 false, NULL, false, NULL,
 true, true, true, true,
 1, 0, 1, 1,
 true, true, true, true, true, true, true,
 'Nenhuma', true, 'Encaminhamento para psiquiatra e acompanhamento medicamentoso'),

-- Prisioneiro 3: João Marcos (Homem)
('João Marcos Lima', 'João', '1973-12-05', 51, '321.987.654-00', 'Brasileira',
 'Sebastiana Lima', 'Divorciado', 'Negra', 'Bissexual', 'Homem', 'Masculino',
 'Fundamental Incompleto', true, 'Aposentadoria por invalidez', true, 3,
 true, 2, true, true,
 1, 'Deficiência visual (usa óculos)', 2, 'Não sabe responder',
 true, 'Herniorrafia (2005)', false,
 false, true, false, false, 'Diabetes tipo 2', false, 'Usa insulina',
 false, false, true, false, false, 'Tuberculose tratada em 2010', false, 'Tratamento completo',
 false, 'Nenhuma', false, 'Sem observações',
 true, 'Insulina NPH, Metformina', 'B-',
 false, NULL, false, false, false, false, false, NULL, false,
 0, NULL, false, NULL,
 true, true, true, true, true, false, true, 'Crack, cocaína',
 true, 'Crack e cocaína', true, 'Crack e cocaína',
 true, true, true, true,
 2, 1, 0, 1,
 true, true, true, true, true, true, true,
 'Nenhuma', true, 'Encaminhamento para reabilitação e acompanhamento médico'),

-- Prisioneiro 4: Maria Fernanda (Mulher)
('Maria Fernanda Santos', 'Maria', '1988-03-15', 37, '456.789.123-00', 'Brasileira',
 'Ana Paula Santos', 'Solteira', 'Parda', 'Heterossexual', 'Mulher', 'Feminino',
 'Ensino Médio Incompleto', true, 'Auxílio Brasil', false, 0,
 false, 0, true, true,
 0, 'Nenhuma', 1, 'Penicilina',
 false, 'Nenhuma', false,
 false, false, false, false, 'Nenhuma', false, 'Sem observações',
 false, false, false, false, false, 'Nenhuma', false, 'Sem histórico',
 false, 'Nenhuma', false, 'Sem observações',
 false, 'Nenhum', 'AB+',
 false, NULL, false, false, false, false, false, NULL, false,
 0, NULL, false, NULL,
 false, true, false, false, false, false, false, NULL,
 false, NULL, false, NULL,
 true, true, true, true,
 0, 1, 1, 0,
 true, true, true, true, true, true, true,
 'Nenhuma', true, 'Encaminhamento para psicólogo'),

-- Prisioneiro 5: Pedro Henrique (Homem)
('Pedro Henrique Oliveira', 'Pedro', '1995-09-28', 29, '789.123.456-00', 'Brasileira',
 'Rosa Maria Oliveira', 'Solteiro', 'Branca', 'Heterossexual', 'Homem', 'Masculino',
 'Ensino Superior Incompleto', false, 'Nenhum', false, 0,
 false, 0, true, true,
 0, 'Nenhuma', 0, 'Nenhuma',
 false, 'Nenhuma', false,
 false, false, false, false, 'Nenhuma', false, 'Sem observações',
 false, false, false, false, false, 'Nenhuma', false, 'Sem histórico',
 false, 'Nenhuma', false, 'Sem observações',
 false, 'Nenhum', 'O-',
 false, NULL, false, false, false, false, false, NULL, false,
 0, NULL, false, NULL,
 true, false, false, false, false, false, false, NULL,
 false, NULL, false, NULL,
 true, true, true, true,
 1, 1, 1, 1,
 true, true, true, true, true, true, true,
 'Nenhuma', true, 'Encaminhamento para psicólogo');

-- Inserir dados de saúde da mulher
INSERT INTO saude_mulher (
  id_prisioneiro, gestacao, idade_gestacional, qual_contraceptivo,
  exame_preventivo_papanicolau, exame_preventivo_papanicolau_ano,
  ofertar_continuidade_contraceptivo, ofertar_consulta_preventivo, encaminhar_pre_natal
) VALUES
(2, 0, NULL, 'Pílula anticoncepcional', true, 2024, true, true, false),
(4, 0, NULL, 'DIU', true, 2023, true, true, false);

-- Inserir dados de saúde do homem
INSERT INTO saude_homem (
  id_prisioneiro, realizou_exame_prostata, ano_exame_prostata, historico_cancer_prostata_familia,
  qual_familiar_cancer_prostata, realizou_vasectomia, parceira_gestante, participa_pre_natal,
  ofertar_encaminhamento_vasectomia, ofertar_encaminhamento_pre_natal
) VALUES
(1, false, NULL, false, NULL, false, false, false, true, false),
(3, true, 2023, true, 'Pai', false, false, false, false, false),
(5, false, NULL, false, NULL, false, false, false, true, false);

-- Inserir atendimentos
INSERT INTO atendimento (
  id_atendente, id_prisioneiro, data_hora, data_entrada_unidade, is_transferencia, procedencia,
  peso, altura, imc, pa, fc, sat, temp,
  tosse, coriza, espirros, febre, calafrios, outros_sistemas_respiratorios, data_sintomas,
  apresenta_lesoes, local_lesoes, conduta_lesoes_clinica,
  hiv_1_2_lote, hiv_1_2_validade, hiv_1_2_reativo, hiv_2_2_lote, hiv_2_2_validade, hiv_2_2_reativo,
  sifilis_lote, sifilis_validade, sifilis_reativo, hepatite_b_lote, hepatite_b_validade, hepatite_b_reativo,
  hepatite_c_lote, hepatite_c_validade, hepatite_c_reativo, covid_lote, covid_validade, covid_reativo,
  teste_gravidez, coleta_escarro, apresenta_queixas_teste_rapido, queixa_teste_rapido, conduta_teste_rapido, conduta_clinica,
  tem_queixa_odontologica, queixa_odontologica, necessita_dentista, conduta_odontologica, encaminhamentos_finais
) VALUES
-- Atendimento 1: André Luiz
(1, 1, '2025-01-24 08:45:00', '2025-01-23', false, 'Delegacia de Santo Amaro',
 75, 1.75, 24.5, '120/80', 72, 98, 36.5,
   false, false, false, false, false, '', NULL,
  false, 'Nenhum', 'Sem lesões',
  'HIV001', '2025-12-31', false, 'HIV002', '2025-12-31', false,
  'SIF001', '2025-12-31', false, 'HEPB001', '2025-12-31', false,
  'HEPC001', '2025-12-31', false, 'COVID001', '2025-12-31', false,
  NULL, false, false, '', '', 'Avaliação clínica normal',
  false, '', false, 'Sem queixas odontológicas', 'Sem encaminhamentos finais'),

-- Atendimento 2: Bruna Karla
(2, 2, '2025-01-24 10:30:00', '2025-01-22', true, 'Presídio Feminino de Benfica',
 62, 1.65, 22.8, '130/85', 78, 97, 36.8,
   false, false, false, false, false, '', NULL,
  false, 'Nenhum', 'Sem lesões',
  'HIV003', '2025-12-31', false, 'HIV004', '2025-12-31', false,
  'SIF002', '2025-12-31', false, 'HEPB002', '2025-12-31', false,
  'HEPC002', '2025-12-31', false, 'COVID002', '2025-12-31', false,
  false, false, false, '', '', 'Avaliação clínica normal, encaminhamento para psiquiatra',
  false, '', false, 'Sem queixas odontológicas', 'Sem encaminhamentos finais'),

-- Atendimento 3: João Marcos
(3, 3, '2025-01-24 13:15:00', '2025-01-20', false, 'Comarca de Uberlândia',
 68, 1.70, 23.5, '140/90', 85, 95, 37.2,
   false, false, false, false, false, '', NULL,
  false, 'Nenhum', 'Sem lesões',
  'HIV005', '2025-12-31', false, 'HIV006', '2025-12-31', false,
  'SIF003', '2025-12-31', false, 'HEPB003', '2025-12-31', false,
  'HEPC003', '2025-12-31', false, 'COVID003', '2025-12-31', false,
  NULL, false, false, '', '', 'Avaliação clínica normal, encaminhamento para reabilitação',
  false, '', false, 'Sem queixas odontológicas', 'Sem encaminhamentos finais'),

-- Atendimento 4: Maria Fernanda
(4, 4, '2025-01-24 14:20:00', '2025-01-21', false, 'Delegacia Central',
 58, 1.60, 22.7, '110/70', 70, 99, 36.3,
   false, false, false, false, false, '', NULL,
  false, 'Nenhum', 'Sem lesões',
  'HIV007', '2025-12-31', false, 'HIV008', '2025-12-31', false,
  'SIF004', '2025-12-31', false, 'HEPB004', '2025-12-31', false,
  'HEPC004', '2025-12-31', false, 'COVID004', '2025-12-31', false,
  false, false, false, '', '', 'Avaliação clínica normal',
  false, '', false, 'Sem queixas odontológicas', 'Sem encaminhamentos finais'),

-- Atendimento 5: Pedro Henrique
(5, 5, '2025-01-24 16:00:00', '2025-01-20', false, 'Delegacia Norte',
 80, 1.78, 25.3, '125/82', 75, 98, 36.6,
   false, false, false, false, false, '', NULL,
  false, 'Nenhum', 'Sem lesões',
  'HIV009', '2025-12-31', false, 'HIV010', '2025-12-31', false,
  'SIF005', '2025-12-31', false, 'HEPB005', '2025-12-31', false,
  'HEPC005', '2025-12-31', false, 'COVID005', '2025-12-31', false,
  NULL, false, false, '', '', 'Avaliação clínica normal',
  false, '', false, 'Sem queixas odontológicas', 'Sem encaminhamentos finais');
  
  -- Segundo atendimento
  -- Atendimento 2: André Luiz
-- Inserir atendimentos
INSERT INTO atendimento (
  id_atendente, id_prisioneiro, data_hora, data_entrada_unidade, is_transferencia, procedencia,
  peso, altura, imc, pa, fc, sat, temp,
  tosse, coriza, espirros, febre, calafrios, outros_sistemas_respiratorios, data_sintomas,
  apresenta_lesoes, local_lesoes, conduta_lesoes_clinica,
  hiv_1_2_lote, hiv_1_2_validade, hiv_1_2_reativo, hiv_2_2_lote, hiv_2_2_validade, hiv_2_2_reativo,
  sifilis_lote, sifilis_validade, sifilis_reativo, hepatite_b_lote, hepatite_b_validade, hepatite_b_reativo,
  hepatite_c_lote, hepatite_c_validade, hepatite_c_reativo, covid_lote, covid_validade, covid_reativo,
  teste_gravidez, coleta_escarro, apresenta_queixas_teste_rapido, queixa_teste_rapido, conduta_teste_rapido, conduta_clinica,
  tem_queixa_odontologica, queixa_odontologica, necessita_dentista, conduta_odontologica, encaminhamentos_finais
) VALUES
(1, 1, '2025-08-24 10:45:00', '2025-08-24 ', false, 'Delegacia de Santo Amaro',
 75, 1.75, 24.5, '120/80', 72, 98, 36.5,
   false, false, false, false, false, '', NULL,
  false, 'Nenhum', 'Sem lesões',
  'HIV001', '2025-12-31', false, 'HIV002', '2025-05-15', false,
  'SIF001', '2025-12-31', false, 'HEPB001', '2025-05-31', false,
  'HEPC001', '2025-12-31', false, 'COVID001', '2025-05-31', false,
  NULL, false, false, '', '', 'Avaliação clínica normal',
  false, '', false, 'Sem queixas odontológicas', 'Sem encaminhamentos finais');