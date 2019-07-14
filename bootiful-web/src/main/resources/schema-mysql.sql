DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users`
(
    `username` VARCHAR(64) NOT NULL PRIMARY KEY COMMENT '登录用户',
    `password` VARCHAR(256) COMMENT '用户密码',
    `enabled`  CHAR(1) DEFAULT 0 COMMENT '是否启用'
);

CREATE TABLE authorities
(
    username  varchar(64) NOT NULL,
    authority varchar(64) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
);
CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);