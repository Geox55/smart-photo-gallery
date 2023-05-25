create table users
(
    email     VARCHAR(256) PRIMARY KEY,
    name      VARCHAR(256),
    password  VARCHAR(256),
    enabled   boolean,
    player_id VARCHAR
);

create table user_roles
(
    email VARCHAR(256) REFERENCES users (email) ON DELETE CASCADE,
    role  VARCHAR(256),
    PRIMARY KEY (email, role)
);

INSERT INTO users (email, name, password, enabled, player_id)
VALUES ('user@example.com', 'user', '$2a$10$5MS5X.YIftZ4xpI9bPituOTLA9GaGUIcPWcnBgLbPnBofCJZaj.UO', true, 'b4b51de1-e6c4-4fdb-bb77-55f8f3c12f9e'),
       ('admin@example.com', 'admin', '$2a$10$JXgnlWSXcb4JTaXgXA6tD.DPJSyHtmxoYcYEvc0Q4pjeH4thLVVjW', true, 'b3b0b0c0-a2de-476a-80d8-0ac935deeeb5');

INSERT INTO user_roles (email, role)
VALUES ('user@example.com', 'USER'),
       ('admin@example.com', 'ADMIN');