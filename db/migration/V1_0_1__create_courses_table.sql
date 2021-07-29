-- auto-generated definition
create table courses
(
    id           bigserial
        constraint courses_pk
            primary key,
    name         varchar(40) not null,
    start_date   timestamp   not null,
    end_date     timestamp   not null,
    teacher_name varchar(40),
    description  text
);

alter table courses
    owner to "default";