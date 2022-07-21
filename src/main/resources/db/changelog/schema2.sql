create table role
(
    id        serial
        constraint role_pk
            primary key,
    role_name varchar not null
);

alter table role
    owner to postgres;

create unique index role_id_uindex
    on role (id);

