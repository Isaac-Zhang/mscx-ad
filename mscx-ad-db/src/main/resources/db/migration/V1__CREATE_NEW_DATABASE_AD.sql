-- 用户表
CREATE TABLE `ad_user`(
  `user_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `user_name` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '用户名',
  `token` VARCHAR(256) NOT NULL DEFAULT '' COMMENT '为用户生成的token信息',
  `user_status` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '用户状态',
  `create_time` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间'
  PRIMARY KEY ('user_id'),
  UNIQUE KEY ('user_name')
)ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- 推广计划表
