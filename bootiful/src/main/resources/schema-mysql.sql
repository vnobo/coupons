/***********************************用户资源管理表**********************************/
DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;

/**用户信息表**/
CREATE TABLE `users`
(
    `id`                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    `username`            VARCHAR(64) UNIQUE  NOT NULL,
    `password`            VARCHAR(2048),
    `enabled`             CHAR(1)     NOT NULL DEFAULT 0,
    `email`               VARCHAR(64),
    `email_verified`      CHAR(1)     NOT NULL DEFAULT 0,
    `image_url`           VARCHAR(2048),
    `tao_bao_id`          VARCHAR(32),
    `tao_bao_name`        VARCHAR(32),
    `tao_bao_relation_id` BIGINT,
    `provider`            VARCHAR(10) NOT NULL DEFAULT 'local',
    `provider_id`         VARCHAR(10),
    `extend`              JSON
);

/**用户权限表**/
CREATE TABLE `authorities`
(
    `username`  VARCHAR(64) NOT NULL,
    `authority` VARCHAR(64) NOT NULL,
    UNIQUE (`username`, `authority`)
);

