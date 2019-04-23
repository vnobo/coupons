INSERT INTO `users` (`username`, `password`, `enabled`)VALUES
('admin', '058fcf50b58382ae2a8ce1933f99751c931f7d88d2d04fcdab94bd232248bd9f02d971d9667cb596', 1),
('user', '058fcf50b58382ae2a8ce1933f99751c931f7d88d2d04fcdab94bd232248bd9f02d971d9667cb596', 1);

INSERT INTO `authorities` (`username`, `authority`) VALUES ('admin', 'ROLE_ADMIN'),('admin', 'ROLE_USER'), ('user', 'ROLE_USER');
