
-- 导出  表task_fire_log 结构
DROP TABLE IF EXISTS `task_fire_log`;
CREATE TABLE `task_fire_log` (
  `id` VARCHAR(30) NOT NULL,
  `group_name` VARCHAR(50) NOT NULL,
  `task_name` VARCHAR(50) NOT NULL,
  `start_time` DATETIME NOT NULL,
  `end_time` DATETIME DEFAULT NULL,
  `status` VARCHAR(1) NOT NULL DEFAULT 'I',
  `server_host` VARCHAR(50) DEFAULT NULL COMMENT '服务器名',
  `server_duid` VARCHAR(50) DEFAULT NULL COMMENT '服务器网卡序列号',
  `fire_info` TEXT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_name_task_name_start_time` (`group_name`,`task_name`,`start_time`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

