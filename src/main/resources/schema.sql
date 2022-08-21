create table if not exists accumulations
(
    id   bigint auto_increment
    primary key,
    name varchar(255) null
    );

create table if not exists publications
(
    id              bigint auto_increment
    primary key,
    description     longtext    null,
    price           bigint      not null,
    title           varchar(45) not null,
    topic           varchar(45) not null,
    accumulation_id bigint      null,
    constraint FK5m6jg54onlse8ai6tf5jp5tx2
    foreign key (accumulation_id) references accumulations (id)
    );

create table if not exists roles
(
    id   bigint auto_increment
    primary key,
    name varchar(255) null
    );

create table if not exists users
(
    id              bigint auto_increment
    primary key,
    account         int          null,
    active          bit          null,
    email           varchar(45)  not null,
    password        varchar(255) null,
    repeat_password varchar(255) null,
    role_id         bigint       null,
    first_name      varchar(255) null,
    last_name       varchar(255) null,
    constraint UK_6dotkott2kjsp8vw4d0m25fb7
    unique (email),
    constraint FKp56c1712k691lhsyewcssf40f
    foreign key (role_id) references roles (id)
    );

create table if not exists subscriptions
(
    id             bigint auto_increment
    primary key,
    date           datetime null,
    publication_id bigint   not null,
    user_id        bigint   not null,
    constraint FKf6wu6wbgeib9l2j6rdvunorjw
    foreign key (publication_id) references publications (id),
    constraint FKhro52ohfqfbay9774bev0qinr
    foreign key (user_id) references users (id)
    );