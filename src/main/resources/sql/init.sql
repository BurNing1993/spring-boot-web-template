DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username`    varchar(30) NOT NULL COMMENT '用户名',
    `password`    varchar(30) NOT NULL COMMENT '用户名',
    `nickname`    varchar(30) NOT NULL COMMENT '昵称',
    `role`        int(2) NOT NULL COMMENT '角色',
    `deleted`     tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1-已删除 0-未删除)',
    `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_username` (`username`) USING BTREE COMMENT 'username 唯一'
);

INSERT INTO `boot`.`user`(`id`, `username`, `password`, `nickname`, `role`, `deleted`, `create_time`, `update_time`)
VALUES (1, 'admin', 'admin', 'admin', 0, 0, '2021-03-11 15:08:36', '2021-03-11 15:08:36');