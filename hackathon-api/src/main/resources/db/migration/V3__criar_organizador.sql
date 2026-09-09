CREATE TABLE organizador (
    id BIGINT PRIMARY KEY,

    CONSTRAINT fk_organizador_usuario
        FOREIGN KEY (id)
        REFERENCES usuario(id)
        ON DELETE CASCADE
);