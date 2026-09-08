-- Dados de exemplo carregados pela configuração padrão. Marcadores negativos não consomem os IDs da API.
INSERT INTO hackathon (id, nome, data_inicio, data_termino, max_equipes)
VALUES (-1, 'Hackathon de exemplo', CURRENT_DATE + 7, CURRENT_DATE + 9, 3);

INSERT INTO usuario (id, nome, email, senha) VALUES
(-1, 'Ana Exemplo', 'ana@example.com', 'senha1234'), 
(-2, 'Bruno Exemplo', 'bruno@example.com', 'senha1234');

-- Torna os usuários acima em participantes
INSERT INTO participante (id)
SELECT id
FROM usuario
WHERE id IN (-1, -2);
