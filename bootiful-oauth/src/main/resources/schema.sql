drop table if exists se_authority_dict;
create table se_authority_dict
(
    id           serial primary key,
    authority    varchar(128) not null,
    path         varchar(2048),
    roles        json,
    created_time timestamp default current_timestamp,
    updated_time timestamp default current_timestamp,
    unique (authority)
);

drop table if exists se_users;
create table se_users
(
    id           serial primary key,
    tenant_id    int       default 0,
    username     varchar(64)   not null unique,
    password     varchar(1024) not null,
    enabled      boolean       not null,
    created_time timestamp default current_timestamp,
    updated_time timestamp default current_timestamp
);

drop table if exists se_authorities;
create table se_authorities
(
    id           serial primary key,
    username     varchar(64)  not null,
    authority    varchar(128) not null,
    created_time timestamp default current_timestamp,
    updated_time timestamp default current_timestamp,
    unique (username, authority)
);

drop table if exists se_groups;
create table se_groups
(
    id           serial primary key,
    name         varchar(64) not null unique,
    created_time timestamp default current_timestamp,
    updated_time timestamp default current_timestamp
);

drop table if exists se_group_authorities;
create table se_group_authorities
(
    id           serial primary key,
    tenant_id    int          not null,
    authority    varchar(128) not null,
    created_time timestamp default current_timestamp,
    updated_time timestamp default current_timestamp
);

drop table if exists se_group_members;
create table se_group_members
(
    id           serial primary key,
    tenant_id    int         not null,
    user_id      int         not null,
    username     varchar(64) not null,
    created_time timestamp default current_timestamp,
    updated_time timestamp default current_timestamp
);