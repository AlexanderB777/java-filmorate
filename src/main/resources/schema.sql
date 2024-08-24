CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    login    VARCHAR(20),
    name     VARCHAR(30),
    email    VARCHAR(50),
    birthday DATE
);

CREATE TABLE IF NOT EXISTS mpa
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
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name         VARCHAR(40) NOT NULL,
    description  TEXT(1000),
    release_date DATE,
    duration     INTEGER,
    mpa_id       INTEGER,
    FOREIGN KEY (mpa_id) REFERENCES mpa (id)
);

CREATE TABLE IF NOT EXISTS film_genres
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    film_id  INTEGER NOT NULL,
    genre_id INTEGER NOT NULL,
    FOREIGN KEY (film_id) REFERENCES films (id),
    FOREIGN KEY (genre_id) REFERENCES genres (id),
    CONSTRAINT unique_film_genre_pair UNIQUE (film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS friendship
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user1_id BIGINT NOT NULL,
    user2_id BIGINT NOT NULL,
    CONSTRAINT unique_users_pair UNIQUE (user1_id, user2_id)
);

CREATE TABLE IF NOT EXISTS likes
(
    id      BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    film_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (film_id) REFERENCES films (id)
);

CREATE TABLE IF NOT EXISTS reviews
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    content    VARCHAR(1000),
    positive BOOLEAN,
    user_id     BIGINT NOT NULL,
    film_id     BIGINT NOT NULL,
    useful     INTEGER,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (film_id) REFERENCES films (id)
);

CREATE TABLE IF NOT EXISTS review_like
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    review_id BIGINT NOT NULL,
    user_id   BIGINT NOT NULL,
    CONSTRAINT unique_pairs_for_likes UNIQUE (review_id, user_id)
);

CREATE TABLE IF NOT EXISTS review_dislike
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    review_id BIGINT NOT NULL,
    user_id   BIGINT NOT NULL,
    CONSTRAINT unique_pairs_for_dislikes UNIQUE (review_id, user_id)
);