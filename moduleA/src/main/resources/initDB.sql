DROP TABLE IF EXISTS data;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE users
(
    id       INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    login    VARCHAR NOT NULL,
    password VARCHAR NOT NULL
);

CREATE TABLE user_roles
(
    user_id VARCHAR NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE data
(
    id    INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    data1 VARCHAR NOT NULL,
    data2 VARCHAR NOT NULL
);

INSERT INTO users (login, password)
VALUES ('user', 'password'),
       ('admin', 'password');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001),
       ('ROLE_USER', 100001);

insert INTO data(data1, data2)
VALUES ('1', '1'),
       ('2', '2'),
       ('3', '3'),
       ('4', '4');

