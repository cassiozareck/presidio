
INSERT INTO atendente (nome) VALUES
('Amanda Ribeiro'),
('Felipe Costa'),
('Renata Almeida');

INSERT INTO prisioneiro (
  data_nascimento, nome, nome_mae, cpf, uf, orientacao, genero, sexo, raca, nacionalidade, estado_civil, escolaridade
) VALUES
('1986-04-20', 'André Luiz de Souza', 'Maria das Dores Souza', '123.456.789-00', 'SP', 'Heterossexual', 'Masculino', 'Masculino', 'Pardo', 'Brasileira', 'Solteiro', 'Ensino Médio Completo'),
('1991-07-11', 'Bruna Karla Mendes', 'Luciana Karla Mendes', '987.654.321-00', 'RJ', 'Homossexual', 'Feminino', 'Feminino', 'Branca', 'Brasileira', 'Casada', 'Ensino Superior Completo'),
('1973-12-05', 'João Marcos Lima', 'Sebastiana Lima', '321.987.654-00', 'MG', 'Bissexual', 'Masculino', 'Masculino', 'Negra', 'Brasileira', 'Divorciado', 'Fundamental Incompleto');

INSERT INTO atendimento (
  id_atendente, id_prisioneiro, data_hora, data_entrada_unidade, is_transferencia, procedencia
) VALUES
(1, 1, '2025-06-24 08:45:00', '2025-06-23', false, 'Delegacia de Santo Amaro'),
(2, 2, '2025-06-24 10:30:00', '2025-06-22', true, 'Presídio Feminino de Benfica'),
(3, 3, '2025-06-24 13:15:00', '2025-06-20', false, 'Comarca de Uberlândia');
