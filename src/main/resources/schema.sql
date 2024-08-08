CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT NOT NULL PRIMARY KEY,
    email    VARCHAR(30),
    login    VARCHAR(20),
    name     VARCHAR(30),
    birthday DATE
);

CREATE TABLE IF NOT EXISTS ratings
(
    id   INTEGER     NOT NULL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS genres
(
    id   INTEGER     NOT NULL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS films
(
    id           BIGINT NOT NULL PRIMARY KEY,
    name         VARCHAR(40),
    description  TEXT,
    release_date DATE,
    duration     INTEGER,
    genre_id     INTEGER,
    rating_id    INTEGER
);

CREATE TABLE IF NOT EXISTS friendship
(
    id        BIGINT  NOT NULL PRIMARY KEY,
    user1_id  BIGINT  NOT NULL,
    user2_id  BIGINT  NOT NULL,
    confirmed BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS likes
(
    id      BIGINT NOT NULL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    film_id BIGINT NOT NULL
);

