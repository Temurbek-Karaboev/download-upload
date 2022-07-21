create table person
(
    id            serial,
    username      varchar not null,
    year_of_birth integer not null,
    role          integer not null
        constraint person_pk
            primary key,
    password      varchar not null
);

alter table person
    owner to postgres;

create unique index person_id_uindex
    on person (id);

