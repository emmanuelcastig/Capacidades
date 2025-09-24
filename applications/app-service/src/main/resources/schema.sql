CREATE TABLE IF NOT EXISTS capacidades (
                                           id SERIAL PRIMARY KEY,
                                           nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(90) NOT NULL
    );

CREATE TABLE IF NOT EXISTS capacidad_tecnologias (
                                                     id SERIAL PRIMARY KEY,
                                                     id_capacidad BIGINT NOT NULL,
                                                     id_tecnologia BIGINT NOT NULL,
                                                     CONSTRAINT fk_capacidad FOREIGN KEY (id_capacidad) REFERENCES capacidades(id) ON DELETE CASCADE
    );