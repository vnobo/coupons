DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `id`       INT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
    `username` VARCHAR(64) NOT NULL UNIQUE COMMENT '登录用户',
    `password` VARCHAR(256) COMMENT '用户密码',
    `enable`   CHAR(1) DEFAULT 0 COMMENT '是否启用'
);
