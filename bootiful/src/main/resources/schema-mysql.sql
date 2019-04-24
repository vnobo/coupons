/***********************************用户资源管理表**********************************/
DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;

/**用户信息表**/
CREATE TABLE `users`
(
    `id`           BIGINT AUTO_INCREMENT PRIMARY KEY,
    `username`     VARCHAR(64) NOT NULL UNIQUE,
    `password`     VARCHAR(128),
    `enabled`      CHAR(1)     NOT NULL DEFAULT 0,
    `image_url`    VARCHAR(128),
    `tao_bao_id`   VARCHAR(32),
    `tao_bao_name` VARCHAR(32),
    `extend`       JSON
);

/**用户权限表**/
CREATE TABLE `authorities`
(
    `username`  VARCHAR(64) NOT NULL,
    `authority` VARCHAR(64) NOT NULL,
    UNIQUE (`username`, `authority`)
);

