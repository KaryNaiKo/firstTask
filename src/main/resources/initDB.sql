DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE users
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  login            VARCHAR                 NOT NULL           UNIQUE,
  password         VARCHAR                 NOT NULL
);

CREATE TABLE user_roles
(
  user_login        VARCHAR NOT NULL,
  role              VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_login, role),
  FOREIGN KEY (user_login) REFERENCES users (login) ON DELETE CASCADE
);

INSERT INTO users (login, password) VALUES
  ('user', 'password'),
  ('admin', 'password');

INSERT INTO user_roles (role, user_login) VALUES
  ('ROLE_USER', 'user'),
  ('ROLE_ADMIN', 'admin'),
  ('ROLE_USER', 'admin');

