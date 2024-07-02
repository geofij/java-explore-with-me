drop table if exists statistic;

create table if not exists statistic (
    id bigint generated by default as identity not null,
    app varchar(255) not null,
    uri varchar(255) not null,
    ip varchar(255) not null,
    timestamp timestamp without time zone not null,
    constraint pk_booking primary key (id)
);