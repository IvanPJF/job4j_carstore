INSERT INTO manufacturer (name)
VALUES ('Kia'),
       ('Volkswagen'),
       ('Lada');

INSERT INTO model (name, manufacturer_id)
VALUES ('Ceed', 1),
       ('Passat', 2),
       ('Granta', 3);

INSERT INTO body_type (name)
VALUES ('Хэтчбек'),
       ('Универсал'),
       ('Седан'),
       ('Лифтбек');

INSERT INTO model_body_type (model_id, body_type_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (2, 2),
       (2, 3),
       (3, 3),
       (3, 4);
