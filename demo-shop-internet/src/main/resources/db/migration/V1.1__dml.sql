SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO roles (name)
VALUES
('ROLE_USER'),('ROLE_ADMIN');

INSERT INTO users (username,password,first_name,last_name,email)
VALUES
('alex','$2a$10$vmkUHC45kZ4IVH./LlUH1e.jQnNZjVKss3uo3A6OChqKrl1SgoHZu','Alex','GeekBrains','alex@gb.com');


INSERT INTO users_roles (user_id, role_id)
VALUES
(1, 1),
(1, 2);

INSERT INTO brands (title)
VALUES
('NON NAME'),
('SAMSUNG'),
('PHILIPS'),
('SONY'),
('APLE'),
('BOSH'),
('VITEK'),
('Александр Дюма'),
('Лев Толстой'),
('Виктор Гюго');

