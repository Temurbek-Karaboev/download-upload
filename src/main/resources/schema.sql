CREATE TABLE client
(
    id       serial
        CONSTRAINT id PRIMARY KEY,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    role     varchar(255) NOT NULL,
);