-- ----------------------------------------------------------------------------
-- Datos iniciales para la base de datos "paproject".
-- ----------------------------------------------------------------------------

SET NAMES utf8mb4;

-- Usuarios viewer (espectador) y ticketseller (taquillero) con contraseña pa2526.
-- Role: 0 = SPECTATOR, 1 = TICKET_SELLER (ordinal del enum).
INSERT INTO User (userName, password, firstName, lastName, email, role) VALUES
    ('viewer', '$2a$10$v.js2jCaX3xoKvkR6E2pbugMmZDBPlCAz2gA7EOIZhbkvsPFew/5u', 'Viewer', 'User', 'viewer@test.com', 0),
    ('ticketseller', '$2a$10$v.js2jCaX3xoKvkR6E2pbugMmZDBPlCAz2gA7EOIZhbkvsPFew/5u', 'Ticket', 'Seller', 'ticketseller@test.com', 1);

-- Películas reales: Torrente Presidente e Interstellar
INSERT INTO Movie (title, summary, runtime) VALUES
    ('Torrente Presidente',
     'Comedia satírica en la que el inepto José Luis Torrente da el salto a la política y lidera un disparatado partido populista, rodeado de asesores incompetentes y campañas electorales absurdas.',
     103),
    ('Interstellar',
     'En un futuro en el que la Tierra se muere, un ex piloto de la NASA se une a una misión a través de un agujero de gusano para encontrar un nuevo hogar para la humanidad, enfrentándose a decisiones imposibles y a los efectos del viaje espacial en el tiempo.',
     169);

-- Salas: una de 9 localidades (para sesión 2 a las 23:55) y otra de más de 10
INSERT INTO Room (name, capacity) VALUES
    ('Sala 1', 15),
    ('Sala 2', 9);

-- Sesión 1: hoy 00:05 en Sala 1 (15 plazas), Película 1
INSERT INTO Session (movieId, roomId, date, price, freeSeats) VALUES
    (1, 1, CONCAT(DATE(NOW()), ' 00:05:00'), 5.00, 15);

-- Sesión 2: hoy 23:55 en Sala 2 (9 plazas), Película 2
INSERT INTO Session (movieId, roomId, date, price, freeSeats) VALUES
    (2, 2, CONCAT(DATE(NOW()), ' 23:55:00'), 6.00, 9);

-- Sesiones para los 6 días siguientes (2 por día: 17:00 en Sala 1 y 21:00 en Sala 2)
INSERT INTO Session (movieId, roomId, date, price, freeSeats)
SELECT 1, 1, CONCAT(DATE_ADD(DATE(NOW()), INTERVAL d DAY), ' 17:00:00'), 5.00, 15
FROM (SELECT 1 AS d UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6) AS days;

INSERT INTO Session (movieId, roomId, date, price, freeSeats)
SELECT 2, 2, CONCAT(DATE_ADD(DATE(NOW()), INTERVAL d DAY), ' 21:00:00'), 6.00, 9
FROM (SELECT 1 AS d UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6) AS days;

-- 2 compras para la sesión 1 (viewer userId=1, 2 entradas cada una → 4 entradas vendidas)
-- freeSeats de sesión 1 pasa de 15 a 11
INSERT INTO Compra (userId, sessionId, numTickets, bankCard, purchaseDate, delivered) VALUES
    (1, 1, 2, '1234567890123456', NOW(), FALSE),
    (1, 1, 2, '1234567890123456', NOW(), FALSE);

UPDATE Session SET freeSeats = 11 WHERE id = 1;

-- Datos para pruebas E2E (Trabajo Tutelado - Tarea 0)
INSERT INTO User (userName, password, firstName, lastName, email, role) VALUES
    ('testviewer', '$2a$10$v.js2jCaX3xoKvkR6E2pbugMmZDBPlCAz2gA7EOIZhbkvsPFew/5u', 'Test', 'Viewer', 'testviewer@test.com', 0),
    ('testticketseller', '$2a$10$v.js2jCaX3xoKvkR6E2pbugMmZDBPlCAz2gA7EOIZhbkvsPFew/5u', 'Test', 'TicketSeller', 'testticketseller@test.com', 1);

-- Sesión para mañana a las 01:00 con al menos 2 entradas libres (id=15)
INSERT INTO Session (movieId, roomId, date, price, freeSeats) VALUES
    (1, 1, CONCAT(DATE_ADD(DATE(NOW()), INTERVAL 1 DAY), ' 01:00:00'), 5.00, 15);

-- Compra del usuario testviewer (userId=3) para la sesión anterior
INSERT INTO Compra (userId, sessionId, numTickets, bankCard, purchaseDate, delivered) VALUES
    (3, 15, 2, '9876543210987654', NOW(), FALSE);

-- Compra para el test de entrega de entradas para el test
INSERT INTO Compra (userId, sessionId, numTickets, bankCard, purchaseDate, delivered) VALUES
    ((SELECT id FROM User WHERE userName='testviewer'),
     15, 2, '9876543210987654', NOW(), FALSE);

UPDATE Session SET freeSeats = 13 WHERE id = 15;