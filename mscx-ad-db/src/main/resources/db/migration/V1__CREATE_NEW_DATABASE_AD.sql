CREATE TABLE `ad_user`(
  `user_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `user_name` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '用户名',
  `token` VARCHAR(256) NOT NULL DEFAULT '' COMMENT '为用户生成的token信息',
  `user_status` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '用户状态',
  `create_time` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`)
)ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

CREATE TABLE `ad_plan`(
  `plan_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '标记当前计划所属用户',
  `plan_name` VARCHAR (48) NOT NULL COMMENT '计划名称',
  `plan_status` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '推广计划状态',
  `start_date` DATETIME NOT NULL COMMENT '推广计划开始时间',
  `end_date` DATETIME NOT NULL COMMENT '推广计划结束时间',
  `create_time` DATETIME NOT NULL COMMENT '推广计划创建时间',
  `update_time` DATETIME NOT NULL COMMENT '推广计划修改时间',
  PRIMARY KEY(`plan_id`)
)ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='推广计划表';

CREATE TABLE `ad_unit`(
  `unit_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `plan_id` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '关联推广计划id',
  `unit_name` VARCHAR (48) NOT NULL COMMENT '推广单元名称',
  `unit_status` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '推广单元状态',
  `position_type` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '广告位类型（开屏，贴片，中贴，暂停贴，后贴）',
  `budget_fee` DECIMAL(18,4) NOT NULL COMMENT '预算',
  `create_time` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`unit_id`)
)ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='推广单元表';

CREATE TABLE `ad_creative`(
  `creative_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` VARCHAR(48) NOT NULL COMMENT '创意名称',
  `type` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '创意类型（图片/视频等）',
  `material_type` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '物料类型（图片:bmp,jpg等）',
  `height` INT(10) NOT NULL DEFAULT '0' COMMENT '高度',
  `width` INT(10) NOT NULL DEFAULT '0' COMMENT '宽度',
  `size` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '物料大小, 单位是 KB',
  `duration` INT(10) NOT NULL DEFAULT '0' COMMENT '持续时长, 只有视频才不为 0',
  `audit_status` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '审核状态',
  `user_id` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '标记当前记录所属用户',
  `url` varchar(256) NOT NULL COMMENT '物料地址',
  `create_time` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`creative_id`)
)ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='创意表';

CREATE TABLE `relationship_creative_unit` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `creative_id` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '创意 id',
  `unit_id` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '推广单元 id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='创意和推广单元关联表';

CREATE TABLE `ad_unit_keyword` (
  `keyword_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '推广关键词主键ID',
  `unit_id` INT(11) NOT NULL COMMENT '推广单元 id',
  `keyword` VARCHAR(30) NOT NULL COMMENT '关键词',
  PRIMARY KEY (`keyword_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='推广单元关键词 Feature';

-- 推广单元兴趣 Feature
CREATE TABLE `ad_unit_hobby` (
  `hobby_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `unit_id` INT(11) NOT NULL COMMENT '推广单元 id',
  `hobby_tag` VARCHAR(30) NOT NULL COMMENT '兴趣标签',
  PRIMARY KEY (`hobby_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='推广单元兴趣 Feature';

-- 推广单元地域 Feature
CREATE TABLE `ad_unit_district` (
  `district_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `unit_id` INT(11) NOT NULL COMMENT '推广单元 id',
  `province` VARCHAR(30) NOT NULL COMMENT '省',
  `city` VARCHAR(30) NOT NULL COMMENT '市',
  PRIMARY KEY (`district_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='推广单元地域 Feature';