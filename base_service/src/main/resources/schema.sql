drop table if exists compilation_event, users, categories, locations, compilations, events, requests cascade;

create table if not exists users (
    id bigint generated by default as identity not null,
    name  varchar(255) not null,
    email varchar(255) not null,
    constraint pk_user primary key (id),
    constraint uq_user_email unique (email)
);

create table if not exists categories (
    id   bigint generated by default as identity not null,
    name varchar(255) not null,
    constraint pk_category primary key (id),
    constraint uq_category_name unique (name)
);

create table if not exists compilations (
    id bigint generated by default as identity not null,
    title varchar(120) not null,
    pinned boolean not null,
    constraint pk_compilation primary key (id)
);

create table if not exists events (
    id bigint generated by default as identity not null,
    annotation varchar(2000) not null,
    category_id bigint not null,
    confirmed_requests int not null,
    created_on timestamp without time zone not null,
    description varchar(7000) not null,
    event_date timestamp without time zone not null,
    initiator_id bigint not null,
    paid boolean not null,
    participant_limit int not null,
    published_on timestamp without time zone,
    request_moderation boolean not null,
    state varchar(20) not null,
    title varchar(120) not null,
    views int not null,
    lat double precision not null,
    lon double precision not null,
    boolean available not null,
    constraint pk_event primary key (id),
    constraint fk_user_id foreign key (initiator_id) references users (id),
    constraint fk_category_id foreign key (category_id) references categories (id)
);

create table if not exists requests (
    id bigint generated by default as identity not null,
    event_id bigint not null,
    created timestamp without time zone not null,
    requester_id bigint not null,
    status varchar(20) not null,
    constraint pk_request primary key (id),
    constraint fk_event_id foreign key (event_id) references events (id),
    constraint fk_requester_id foreign key (requester_id) references users (id)
);

create table if not exists compilation_event (
    id bigint generated by default as identity not null,
    compilation_id bigint not null,
    event_id bigint not null,
    constraint pk_compilations_events primary key (id),
    constraint fk_compilation_id foreign key (compilation_id) references compilations (id),
    constraint fk_event_in_comp_id foreign key (event_id) references events (id)
)