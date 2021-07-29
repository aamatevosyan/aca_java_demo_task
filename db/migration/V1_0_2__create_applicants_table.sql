-- auto-generated definition
create table applicants
(
    id        bigserial
        constraint applicants_pk
            primary key,
    name      varchar(40) not null,
    email     varchar(50) not null,
    phone     varchar(20) not null,
    address   varchar(80) not null,
    status    smallint,
    course_id bigserial
        constraint applicants_courses_id_fk
            references courses
);

alter table applicants
    owner to "default";

create index applicants_name_index
    on applicants (name);

create index applicants_email_index
    on applicants (email);

