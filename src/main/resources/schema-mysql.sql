/***********************************用户资源管理表**********************************/
DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;

/**用户信息表**/
CREATE TABLE `users` (
  `username`                VARCHAR(64) PRIMARY KEY,
  `password`                VARCHAR(512)        NOT NULL,
  `enabled`                 char(1)             NOT NULL DEFAULT 0,
  `extend`                  JSON
);

/**用户权限表**/
CREATE TABLE `authorities` (
  `username`  VARCHAR(64) NOT NULL,
  `authority` VARCHAR(64) NOT NULL,
  UNIQUE (`username`, `authority`)
);

