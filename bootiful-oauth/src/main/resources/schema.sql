drop table if exists se_authority_dict;
create table se_authority_dict
(
    id           serial primary key,
    authority    varchar(128) not null,
    description  varchar(2048),
    path         varchar(2048),
    permissions  json,
    created_time timestamp default current_timestamp,
    updated_time timestamp default current_timestamp,
    unique (authority)
);
comment on column se_authority_dict.authority is '定义权限,如: ROLE_USER';
comment on column se_authority_dict.description is '描述';
comment on column se_authority_dict.path is '权限控制路径,如: /user';
comment on column se_authority_dict.permissions is '定义权限子权限,比如按钮权限,读写,如: ROLE_USER_ADD,ROLE_USER_READ,ROLE_USER_DELETE';
comment on table se_authority_dict is '权限字典,主要生成用户认证权限的基础表,所有权限在这里定义.';

drop table if exists se_users;
create table se_users
(
    id              serial primary key,
    username        varchar(128)  not null unique,
    password        varchar(1024) not null,
    enabled         boolean       not null,
    last_login_time timestamp default current_timestamp,
    created_time    timestamp default current_timestamp,
    updated_time    timestamp default current_timestamp
);
comment on column se_users.username is '用户登录名,系统认证唯一标识';
comment on column se_users.password is '用户密码';
comment on column se_users.enabled is '是否启用';
comment on column se_users.last_login_time is '最后登录时间';
comment on table se_users is '认证用户基础信息表.';

drop table if exists se_authorities;
create table se_authorities
(
    id           serial primary key,
    username     varchar(128) not null,
    authority    varchar(256) not null,
    created_time timestamp default current_timestamp,
    updated_time timestamp default current_timestamp,
    unique (username, authority)
);
comment on column se_authorities.username is '用户登录名';
comment on column se_authorities.authority is '权限';
comment on table se_authorities is '权限认证表.';

drop table if exists se_groups;
create table se_groups
(
    id           serial primary key,
    name         varchar(128) not null unique,
    description  varchar(2048),
    created_time timestamp default current_timestamp,
    updated_time timestamp default current_timestamp
);
comment on column se_groups.name is '组名称';
comment on column se_groups.description is '描述';
comment on table se_groups is '权限组基础表.';

drop table if exists se_group_authorities;
create table se_group_authorities
(
    id           serial primary key,
    tenant_id    int          not null,
    authority    varchar(256) not null,
    created_time timestamp default current_timestamp,
    updated_time timestamp default current_timestamp,
    unique (tenant_id, authority)
);
comment on column se_groups.name is '组ID';
comment on column se_groups.description is '组的权限';
comment on table se_groups is '权限组权限认证表.';

drop table if exists se_group_members;
create table se_group_members
(
    id           serial primary key,
    tenant_id    int          not null,
    username     varchar(128) not null,
    created_time timestamp default current_timestamp,
    updated_time timestamp default current_timestamp
);
comment on column se_group_members.tenant_id is '组id';
comment on column se_group_members.username is '登录用户名';
comment on table se_group_members is '权限组用户关系表.';