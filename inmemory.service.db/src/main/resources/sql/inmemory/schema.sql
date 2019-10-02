CREATE TABLE monitor (
    id        bigint auto_increment primary key,
    before    varchar(40),
    after     varchar(40)
);

CREATE TABLE users (
    id        bigint auto_increment primary key,
    name    varchar(40),
    surname     varchar(40)
);

CREATE TABLE roles (
    id        bigint auto_increment primary key,
    role varchar(10),
    user_id  bigint,
    FOREIGN KEY (user_id) REFERENCES users(id)
);