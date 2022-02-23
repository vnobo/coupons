DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id          serial primary key,
    username    VARCHAR(64) unique deferrable initially immediate,
    password    VARCHAR(256),
    enabled     bool  DEFAULT true,
    authorities jsonb default '[
      "ROLE_USER"
    ]'
);

DROP TABLE IF EXISTS orders;
CREATE TABLE orders
(
    id           VARCHAR(64) PRIMARY KEY,
    openid       VARCHAR(64),
    price        money     DEFAULT 0,
    pay_price    money     DEFAULT 0,
    created_time timestamp DEFAULT current_timestamp,
    updated_time timestamp DEFAULT current_timestamp,
    sync_time    timestamp DEFAULT current_timestamp,
    extend       JSONB
);
