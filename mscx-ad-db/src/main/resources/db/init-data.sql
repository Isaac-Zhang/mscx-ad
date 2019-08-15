
--
-- init data for table `ad_user`
--

INSERT INTO `ad_user` VALUES (10,'Isaac','B2E56F2420D73FEC125D2D51641C5713',1,'2019-08-14 20:29:01','2019-08-14 20:29:01');

--
-- init data for table `ad_creative`
--

INSERT INTO `ad_creative` VALUES (10,'第一个创意',1,1,720,1080,1024,0,1,10,'https://www.life-runner.com','2019-08-14 21:31:31','2019-08-14 21:31:31');

--
-- init data for table `ad_plan`
--

INSERT INTO `ad_plan` VALUES (10,15,'推广计划名称',1,'2019-11-28 00:00:00','2019-11-20 00:00:00','2019-11-19 20:42:27','2019-08-14 20:57:12');

--
-- init data for table `ad_unit`
--

INSERT INTO `ad_unit` VALUES (10,10,'第一个推广单元',1,1,10000000,'2019-11-20 11:43:26','2019-11-20 11:43:26'),(12,10,'第二个推广单元',1,1,15000000,'2019-01-01 00:00:00','2019-01-01 00:00:00');

--
-- init data for table `ad_unit_district`
--

INSERT INTO `ad_unit_district` VALUES (10,10,'陕西省','西安市'),(11,10,'陕西省','西安市'),(12,10,'陕西省','西安市'),(14,10,'山西省','阳泉市');

--
-- init data for table `ad_unit_hobby`
--

INSERT INTO `ad_unit_hobby` VALUES (10,10,'爬山'),(11,10,'读书'),(12,10,'写代码');

--
-- init data for table `ad_unit_keyword`
--

INSERT INTO `ad_unit_keyword` VALUES (10,10,'汽车'),(11,10,'火车'),(12,10,'飞机');

--
-- init data for table `relationship_creative_unit`
--

INSERT INTO `relationship_creative_unit` VALUES (10,10,10);
