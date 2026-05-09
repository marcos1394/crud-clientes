-- Datos iniciales que Spring Boot ejecuta al arrancar

-- Estados de Mexico
INSERT INTO estado (nombre) VALUES ('Aguascalientes');
INSERT INTO estado (nombre) VALUES ('Baja California');
INSERT INTO estado (nombre) VALUES ('Chihuahua');
INSERT INTO estado (nombre) VALUES ('Ciudad de Mexico');
INSERT INTO estado (nombre) VALUES ('Jalisco');
INSERT INTO estado (nombre) VALUES ('Nuevo Leon');
INSERT INTO estado (nombre) VALUES ('Sinaloa');
INSERT INTO estado (nombre) VALUES ('Sonora');

-- Clientes de ejemplo
INSERT INTO cliente (nombre, ap_pat, ap_mat, fecha_alta, fecha_nacimiento, status, estado_id)
VALUES ('Juan', 'Garcia', 'Lopez', CURRENT_DATE, '1990-05-15', 1, 4);

INSERT INTO cliente (nombre, ap_pat, ap_mat, fecha_alta, fecha_nacimiento, status, estado_id)
VALUES ('Maria', 'Martinez', 'Hernandez', CURRENT_DATE, '1985-11-22', 1, 5);

INSERT INTO cliente (nombre, ap_pat, ap_mat, fecha_alta, fecha_nacimiento, status, estado_id)
VALUES ('Carlos', 'Rodriguez', 'Perez', CURRENT_DATE, '1995-03-08', 1, 7);