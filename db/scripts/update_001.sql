CREATE TABLE IF NOT EXISTS advertiser
(
    id    SERIAL,
    name  VARCHAR(100) NOT NULL,
    phone BIGINT       NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS reg_advertiser
(
    id            SERIAL,
    login         VARCHAR(100) NOT NULL UNIQUE,
    password      VARCHAR(100) NOT NULL,
    advertiser_id INTEGER      NOT NULL UNIQUE,
    PRIMARY KEY (id),
    FOREIGN KEY (advertiser_id) REFERENCES advertiser (id)
);

CREATE TABLE IF NOT EXISTS manufacturer
(
    id   SERIAL,
    name VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS model
(
    id              SERIAL,
    name            VARCHAR(100) NOT NULL UNIQUE,
    manufacturer_id INTEGER      NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (manufacturer_id) REFERENCES manufacturer (id)
);

CREATE TABLE IF NOT EXISTS body_type
(
    id   SERIAL,
    name VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS model_body_type
(
    model_id     INTEGER NOT NULL,
    body_type_id INTEGER NOT NULL,
    PRIMARY KEY (model_id, body_type_id),
    FOREIGN KEY (model_id) REFERENCES model (id),
    FOREIGN KEY (body_type_id) REFERENCES body_type (id)
);

CREATE TABLE IF NOT EXISTS advert
(
    id            SERIAL,
    vin           VARCHAR(20) NOT NULL UNIQUE,
    mileage       INTEGER     NOT NULL,
    photo_name    VARCHAR(100) UNIQUE,
    price         BIGINT      NOT NULL,
    status        BOOLEAN DEFAULT true,
    model_id      INTEGER     NOT NULL,
    body_type_id  INTEGER     NOT NULL,
    advertiser_id INTEGER     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (model_id) REFERENCES model (id),
    FOREIGN KEY (body_type_id) REFERENCES body_type (id),
    FOREIGN KEY (advertiser_id) REFERENCES advertiser (id)
);

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

