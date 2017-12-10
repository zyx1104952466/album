-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.22-rc-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema ajaxalbum
--

CREATE DATABASE IF NOT EXISTS ajaxalbum;
USE ajaxalbum;

--
-- Definition of table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `admin`
--

/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` (`id`,`name`,`password`) VALUES 
 (2,'admin','admin123');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;


--
-- Definition of table `album`
--

DROP TABLE IF EXISTS `album`;
CREATE TABLE `album` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `introduce` varchar(255) DEFAULT NULL,
  `addtime` date DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT 'images/default/album.jpg',
  `open` int(11) DEFAULT '1',
  `skimnum` int(11) DEFAULT '0',
  `photonum` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `album_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `album`
--

/*!40000 ALTER TABLE `album` DISABLE KEYS */;
INSERT INTO `album` (`id`,`name`,`introduce`,`addtime`,`user_id`,`url`,`open`,`skimnum`,`photonum`) VALUES 
 (1,'搞笑集合','暂无介绍','2013-03-23',1,'Upload_Images/userid_1/albumid_1/1254920527281.jpg',1,79,5),
 (2,'小美女','祖国的花朵','2013-03-23',1,'Upload_Images/userid_1/albumid_2/1254917212625.jpg',1,11,5),
 (3,'可爱的baby','暂无介绍','2013-03-23',2,'Upload_Images/userid_2/albumid_3/1254917653687.jpg',1,2,2),
 (4,'葡萄院子','暂无介绍','2013-03-23',2,'Upload_Images/userid_2/albumid_4/1254917903312.jpg',1,6,10),
 (5,'迷一样','美女','2013-03-23',3,'Upload_Images/userid_3/albumid_5/1254918274125.jpg',1,9,6),
 (6,'平原风光','美丽的平原风光','2013-03-23',3,'Upload_Images/userid_3/albumid_6/1254920153218.jpg',1,5,10),
 (7,'国亲六十周年','暂无介绍','2013-03-23',4,'Upload_Images/userid_4/albumid_7/1254922161437.jpg',1,9,11),
 (8,'庆典来了','暂无介绍','2013-03-23',4,'Upload_Images/userid_4/albumid_8/1254922593468.jpg',1,2,5),
 (9,'收藏美女','暂无介绍','2013-03-23',4,'Upload_Images/userid_4/albumid_9/1254922953906.jpg',1,4,8),
 (10,'伟大的祖国','暂无介绍','2013-03-23',4,'Upload_Images/userid_4/albumid_10/1254923607218.jpg',1,11,14),
 (11,'xianyadan','暂无介绍','2013-03-23',5,'Upload_Images/userid_5/albumid_11/1254924179906.gif',1,12,3),
 (12,'我们的幸福时光','暂无介绍','2013-03-23',6,'Upload_Images/userid_6/albumid_12/1254923949765.jpg',1,18,2),
 (13,'hzs','暂无介绍','2013-03-23',7,'images/default/album.jpg',1,3,0),
 (14,'美好时光','校园生活照','2013-03-23',1,'Upload_Images/userid_1/albumid_14/1254926582921.JPG',0,24,4),
 (15,'test','暂无介绍','2013-03-23',2,'Upload_Images/userid_2/albumid_15/1254977681812.jpg',1,4,1),
 (16,'春节旅游季','旅游','2013-03-23',8,'images/default/album.jpg',1,2,1);
/*!40000 ALTER TABLE `album` ENABLE KEYS */;


--
-- Definition of table `comment`
--

DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(510) DEFAULT NULL,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fromuser` varchar(255) DEFAULT NULL,
  `photo_id` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT 'images/default/nopic.jpg',
  PRIMARY KEY (`id`),
  KEY `comment` (`photo_id`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`photo_id`) REFERENCES `photo` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `comment`
--

/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` (`id`,`content`,`addtime`,`fromuser`,`photo_id`,`url`) VALUES 
 (1,'猫咪眼神不大好','2013-03-23 00:00:00','fupeng',2,'Upload_Images/userid_1/person/1254916767921.jpg'),
 (3,'好可爱的小妹妹','2013-03-23 00:00:00','fupeng',10,'Upload_Images/userid_1/person/1254916767921.jpg'),
 (5,'神奇的硬气功','2013-03-23 00:00:00','游客',5,'images/default/nopic.jpg'),
 (6,'真漂亮','2013-03-23 00:00:00','章鱼',33,'images/default/nopic.jpg'),
 (7,'不景气啊，这年头难呐','2013-03-23 00:00:00','fupeng',44,'Upload_Images/userid_1/person/1254916767921.jpg'),
 (10,'我来灌水','2013-03-23 00:00:00','浪客007',89,'Upload_Images/userid_4/person/1254922654875.jpg'),
 (11,'晓兰童鞋','2013-03-23 00:00:00','fupeng',95,'Upload_Images/userid_1/person/1254916767921.jpg'),
 (12,'懒羊羊，便便头','2013-03-23 00:00:00','游客',96,'images/default/nopic.jpg'),
 (18,'美女','2013-03-23 00:00:00','fupeng',95,'Upload_Images/userid_1/person/1254916767921.jpg'),
 (21,'融化的可爱多','2013-03-23 00:00:00','游客',10,'images/default/nopic.jpg'),
 (22,'好一双清澈的大眼睛','2013-03-23 00:00:00','游客',12,'images/default/nopic.jpg'),
 (23,'英姿飒爽','2013-03-23 00:00:00','游客',75,'images/default/nopic.jpg'),
 (24,'国庆大阅兵，威武装甲！','2013-03-23 00:00:00','游客',73,'images/default/nopic.jpg'),
 (25,'好美的海滨城市','2013-03-23 00:00:00','fupeng',97,'Upload_Images/userid_1/person/1254916767921.jpg'),
 (26,'好无辜的懒羊羊哦','2013-03-23 00:00:00','游客',96,'images/default/nopic.jpg'),
 (27,'支持国产，支持懒羊羊！','2013-03-23 00:00:00','fupeng',96,'Upload_Images/userid_1/person/1254916767921.jpg'),
 (28,'肥肥小P孩儿~','2013-03-23 00:00:00','daishu',6,'Upload_Images/userid_2/person/1254917626406.jpg'),
 (30,'好新鲜的葡萄哇','2013-03-23 00:00:00','fupeng',18,'Upload_Images/userid_1/person/1254916767921.jpg'),
 (31,'好帅的国旗护卫队','2013-03-23 00:00:00','游客',75,'images/default/nopic.jpg'),
 (32,'广阔蓝天','2013-03-23 00:00:00','游客',41,'images/default/nopic.jpg'),
 (33,'自己做的图哦。','2013-03-23 10:36:32','hi2013',98,'images/default/nopic.jpg');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;


--
-- Definition of table `photo`
--

DROP TABLE IF EXISTS `photo`;
CREATE TABLE `photo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `addtime` date DEFAULT NULL,
  `album_id` int(11) DEFAULT NULL,
  `skimnum` int(11) DEFAULT '0',
  `fine` int(11) DEFAULT '0',
  `descr` varchar(255) DEFAULT 'has no description!',
  PRIMARY KEY (`id`),
  KEY `album_id` (`album_id`),
  CONSTRAINT `photo_ibfk_1` FOREIGN KEY (`album_id`) REFERENCES `album` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `photo`
--

/*!40000 ALTER TABLE `photo` DISABLE KEYS */;
INSERT INTO `photo` (`id`,`name`,`url`,`addtime`,`album_id`,`skimnum`,`fine`,`descr`) VALUES 
 (2,'猫咪眼神不好','Upload_Images/userid_1/albumid_1/1254916427921.gif','2013-03-23',1,12,1,'判断有点误差~.~！'),
 (3,'体操王子','Upload_Images/userid_1/albumid_1/1254916428046.gif','2013-03-23',1,11,0,'有体操天赋'),
 (5,'硬气功','Upload_Images/userid_1/albumid_1/1254916482718.jpg','2013-03-23',1,3,1,'气功有风险，锻炼需谨慎'),
 (6,'纠结的表情','Upload_Images/userid_1/albumid_1/1254916482937.jpg','2013-03-23',1,4,0,'偶很纠结'),
 (10,'小美女','Upload_Images/userid_1/albumid_2/1254917212625.jpg','2013-03-23',2,22,4,'has no description!'),
 (11,'小美女','Upload_Images/userid_1/albumid_2/1254917212812.jpg','2013-03-23',2,0,0,'has no description!'),
 (12,'小美女','Upload_Images/userid_1/albumid_2/1254917212984.jpg','2013-03-23',2,2,1,'has no description!'),
 (13,'小美女_4','Upload_Images/userid_1/albumid_2/1254917308906.jpg','2013-03-23',2,0,1,'has no description!'),
 (14,'小美女_5','Upload_Images/userid_1/albumid_2/1254917309078.jpg','2013-03-23',2,7,0,'has no description!'),
 (16,'宝宝','Upload_Images/userid_2/albumid_3/1254917653531.jpg','2013-03-23',3,1,0,'has no description!'),
 (17,'宝宝_2','Upload_Images/userid_2/albumid_3/1254917653687.jpg','2013-03-23',3,0,1,'has no description!'),
 (18,'好大一筐红葡萄','Upload_Images/userid_2/albumid_4/1254917903140.jpg','2013-03-23',4,12,0,'has no description!'),
 (19,'葡萄','Upload_Images/userid_2/albumid_4/1254917903312.jpg','2013-03-23',4,0,0,'has no description!'),
 (20,'葡萄树','Upload_Images/userid_2/albumid_4/1254917903484.jpg','2013-03-23',4,0,0,'has no description!'),
 (21,'葡萄树_2','Upload_Images/userid_2/albumid_4/1254917922859.jpg','2013-03-23',4,0,0,'has no description!'),
 (22,'葡萄叶子','Upload_Images/userid_2/albumid_4/1254917923031.jpg','2013-03-23',4,0,0,'has no description!'),
 (23,'葡萄叶子_2','Upload_Images/userid_2/albumid_4/1254917923203.jpg','2013-03-23',4,0,0,'has no description!'),
 (24,'葡萄园','Upload_Images/userid_2/albumid_4/1254917952328.jpg','2013-03-23',4,0,0,'has no description!'),
 (25,'葡萄园_2','Upload_Images/userid_2/albumid_4/1254917952484.jpg','2013-03-23',4,0,0,'has no description!'),
 (26,'葡萄园_4','Upload_Images/userid_2/albumid_4/1254917952656.jpg','2013-03-23',4,0,0,'has no description!'),
 (27,'野蘑菇','Upload_Images/userid_2/albumid_4/1254917959093.jpg','2013-03-23',4,1,0,'has no description!'),
 (28,'俏皮可爱','Upload_Images/userid_3/albumid_5/1254918224093.jpg','2013-03-23',5,0,0,'has no description!'),
 (29,'火辣美女','Upload_Images/userid_3/albumid_5/1254918224750.jpg','2013-03-23',5,2,0,'has no description!'),
 (30,'清纯','Upload_Images/userid_3/albumid_5/1254918225171.jpg','2013-03-23',5,0,0,'has no description!'),
 (31,'清新美丽','Upload_Images/userid_3/albumid_5/1254918274125.jpg','2013-03-23',5,1,1,'has no description!'),
 (32,'妩媚','Upload_Images/userid_3/albumid_5/1254918274765.jpg','2013-03-23',5,0,0,'has no description!'),
 (33,'妩媚_2','Upload_Images/userid_3/albumid_5/1254918275359.jpg','2013-03-23',5,1,1,'has no description!'),
 (34,'枯树','Upload_Images/userid_3/albumid_6/1254920135562.jpg','2013-03-23',6,0,0,'has no description!'),
 (35,'劳动者','Upload_Images/userid_3/albumid_6/1254920135734.jpg','2013-03-23',6,0,0,'has no description!'),
 (36,'收获','Upload_Images/userid_3/albumid_6/1254920135906.jpg','2013-03-23',6,0,0,'has no description!'),
 (37,'田园风光','Upload_Images/userid_3/albumid_6/1254920153078.jpg','2013-03-23',6,13,0,'has no description!'),
 (38,'田园风光_1','Upload_Images/userid_3/albumid_6/1254920153218.jpg','2013-03-23',6,0,0,'has no description!'),
 (39,'田园风光_2','Upload_Images/userid_3/albumid_6/1254920153359.jpg','2013-03-23',6,0,0,'has no description!'),
 (40,'田园风光_3','Upload_Images/userid_3/albumid_6/1254920170515.jpg','2013-03-23',6,0,0,'has no description!'),
 (41,'田园风光_4','Upload_Images/userid_3/albumid_6/1254920170671.jpg','2013-03-23',6,17,0,'has no description!'),
 (42,'田园风光_5','Upload_Images/userid_3/albumid_6/1254920170828.jpg','2013-03-23',6,0,0,'has no description!'),
 (43,'小路','Upload_Images/userid_3/albumid_6/1254920176281.jpg','2013-03-23',6,0,0,'has no description!'),
 (44,'工作难找啊','Upload_Images/userid_1/albumid_1/1254920527281.jpg','2013-03-23',1,10,1,'IT 行业不景气，他娘的'),
 (47,'喜庆_国庆','Upload_Images/userid_4/albumid_7/1254922132171.jpg','2013-03-23',7,7,0,'has no description!'),
 (48,'美丽的烟花_2','Upload_Images/userid_4/albumid_7/1254922132453.jpg','2013-03-23',7,0,0,'has no description!'),
 (49,'美丽的烟花_3','Upload_Images/userid_4/albumid_7/1254922132625.jpg','2013-03-23',7,0,0,'has no description!'),
 (50,'美丽的烟花_4','Upload_Images/userid_4/albumid_7/1254922146234.jpg','2013-03-23',7,5,0,'has no description!'),
 (51,'美丽的烟花_5','Upload_Images/userid_4/albumid_7/1254922146406.jpg','2013-03-23',7,0,0,'has no description!'),
 (52,'美丽的烟花_6','Upload_Images/userid_4/albumid_7/1254922146562.jpg','2013-03-23',7,0,0,'has no description!'),
 (53,'美丽的烟花_7','Upload_Images/userid_4/albumid_7/1254922161437.jpg','2013-03-23',7,0,0,'has no description!'),
 (54,'美丽的烟花_9','Upload_Images/userid_4/albumid_7/1254922161593.jpg','2013-03-23',7,10,0,'has no description!'),
 (55,'美丽的烟花_8','Upload_Images/userid_4/albumid_7/1254922161750.jpg','2013-03-23',7,0,0,'has no description!'),
 (56,'美丽的烟花_10','Upload_Images/userid_4/albumid_7/1254922177578.jpg','2013-03-23',7,0,0,'has no description!'),
 (57,'美丽的烟花_11','Upload_Images/userid_4/albumid_7/1254922177734.jpg','2013-03-23',7,7,0,'has no description!'),
 (59,'安保','Upload_Images/userid_4/albumid_8/1254922593468.jpg','2013-03-23',8,0,0,'has no description!'),
 (60,'老外看庆典','Upload_Images/userid_4/albumid_8/1254922593656.jpg','2013-03-23',8,0,0,'has no description!'),
 (61,'帅气','Upload_Images/userid_4/albumid_8/1254922593796.jpg','2013-03-23',8,0,0,'has no description!'),
 (62,'香港街头','Upload_Images/userid_4/albumid_8/1254922603703.jpg','2013-03-23',8,8,0,'has no description!'),
 (63,'行人匆匆','Upload_Images/userid_4/albumid_8/1254922603843.jpg','2013-03-23',8,1,0,'has no description!'),
 (64,'可爱','Upload_Images/userid_4/albumid_9/1254922953593.jpg','2013-03-23',9,0,0,'has no description!'),
 (65,'美丽','Upload_Images/userid_4/albumid_9/1254922953765.jpg','2013-03-23',9,0,0,'has no description!'),
 (66,'美女啊','Upload_Images/userid_4/albumid_9/1254922953906.jpg','2013-03-23',9,0,0,'has no description!'),
 (67,'美女自拍','Upload_Images/userid_4/albumid_9/1254922970234.jpg','2013-03-23',9,0,0,'has no description!'),
 (68,'清纯美女','Upload_Images/userid_4/albumid_9/1254922970390.jpg','2013-03-23',9,0,0,'has no description!'),
 (69,'清纯美女_2','Upload_Images/userid_4/albumid_9/1254922970515.jpg','2013-03-23',9,0,0,'has no description!'),
 (70,'清纯美女_6','Upload_Images/userid_4/albumid_9/1254922978859.jpg','2013-03-23',9,0,0,'has no description!'),
 (71,'清纯美女_3','Upload_Images/userid_4/albumid_9/1254922979203.jpg','2013-03-23',9,0,0,'has no description!'),
 (72,'tank','Upload_Images/userid_4/albumid_10/1254923594937.jpg','2013-03-23',10,0,0,'has no description!'),
 (73,'导弹发射车','Upload_Images/userid_4/albumid_10/1254923595093.jpg','2013-03-23',10,1,0,'has no description!'),
 (74,'飞行员','Upload_Images/userid_4/albumid_10/1254923595234.jpg','2013-03-23',10,0,0,'has no description!'),
 (75,'国旗护卫队','Upload_Images/userid_4/albumid_10/1254923607218.jpg','2013-03-23',10,4,1,'has no description!'),
 (76,'海军装备','Upload_Images/userid_4/albumid_10/1254923607375.jpg','2013-03-23',10,0,0,'has no description!'),
 (77,'空中加油机','Upload_Images/userid_4/albumid_10/1254923607531.jpg','2013-03-23',10,0,0,'has no description!'),
 (78,'礼炮打响了','Upload_Images/userid_4/albumid_10/1254923625921.jpg','2013-03-23',10,0,0,'has no description!'),
 (79,'美丽的女兵','Upload_Images/userid_4/albumid_10/1254923626109.jpg','2013-03-23',10,0,0,'has no description!'),
 (80,'美丽的女兵_2','Upload_Images/userid_4/albumid_10/1254923626281.jpg','2013-03-23',10,0,0,'has no description!'),
 (81,'帅','Upload_Images/userid_4/albumid_10/1254923638265.jpg','2013-03-23',10,0,0,'has no description!'),
 (82,'帅气的海军','Upload_Images/userid_4/albumid_10/1254923638468.jpg','2013-03-23',10,0,0,'has no description!'),
 (83,'整齐的方队','Upload_Images/userid_4/albumid_10/1254923638656.jpg','2013-03-23',10,0,0,'has no description!'),
 (84,'整齐的正步','Upload_Images/userid_4/albumid_10/1254923743171.jpg','2013-03-23',10,0,0,'has no description!'),
 (85,'装甲车','Upload_Images/userid_4/albumid_10/1254923743312.jpg','2013-03-23',10,0,0,'has no description!'),
 (86,'0006598863','Upload_Images/userid_6/albumid_12/1254923949765.jpg','2013-03-23',12,0,0,'has no description!'),
 (89,'眼镜哥','Upload_Images/userid_5/albumid_11/1254924179671.gif','2013-03-23',11,8,1,'has no description!'),
 (90,'唯美','Upload_Images/userid_5/albumid_11/1254924179765.gif','2013-03-23',11,1,0,'has no description!'),
 (91,'Jay Sky','Upload_Images/userid_5/albumid_11/1254924179906.gif','2013-03-23',11,5,2,'has no description!'),
 (92,'DSC08244','Upload_Images/userid_1/albumid_14/1254926522656.JPG','2013-03-23',14,8,1,'has no description!'),
 (93,'DSC08235','Upload_Images/userid_1/albumid_14/1254926579859.JPG','2013-03-23',14,1,0,'has no description!'),
 (94,'DSC08321','Upload_Images/userid_1/albumid_14/1254926581500.JPG','2013-03-23',14,3,0,'has no description!'),
 (95,'MyGirl','Upload_Images/userid_1/albumid_14/1254926582921.JPG','2013-03-23',14,22,1,'has no description!'),
 (96,'懒羊羊23','Upload_Images/userid_6/albumid_12/1254927967984.jpg','2013-03-23',12,15,3,'has no description!'),
 (97,'0','Upload_Images/userid_2/albumid_15/1254977681812.jpg','2013-03-23',15,4,1,'has no description!'),
 (98,'paper','Upload_Images/userid_8/albumid_16/1364006167312.jpg','2013-03-23',16,1,0,'has no description!');
/*!40000 ALTER TABLE `photo` ENABLE KEYS */;


--
-- Definition of table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `addtime` date DEFAULT NULL,
  `active` int(11) DEFAULT '50',
  `url` varchar(255) DEFAULT 'images/default/nopic.jpg',
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`,`username`,`password`,`nickname`,`addtime`,`active`,`url`,`email`) VALUES 
 (1,'fupeng','123456','防弹鼠标','2013-03-23',226,'Upload_Images/userid_1/person/1254916767921.jpg','52019743@qq.com'),
 (2,'daishu','123456','虫虫','2013-03-23',119,'Upload_Images/userid_2/person/1254917626406.jpg','daishu123.good@163.com'),
 (3,'章鱼','123456','fish','2013-03-23',116,'Upload_Images/userid_3/person/1254921762921.jpg','fishMO@123.com'),
 (4,'浪客007','123456','浪客007','2013-03-23',203,'Upload_Images/userid_4/person/1254922654875.jpg','lk007@126.com'),
 (5,'chenchen','881002','chenchen','2013-03-23',69,'Upload_Images/userid_5/person/1254924793203.gif','xuxiaoqing.25@163.com'),
 (6,'honey','123456','dearbaby','2013-03-23',78,'images/default/nopic.jpg','daishu123.good@163.com'),
 (7,'huzhaosai','13636102944','saisai','2013-03-23',58,'images/default/nopic.jpg','huzhaosai@163.com'),
 (8,'hi2013','123456','hello','2013-03-23',63,'images/default/nopic.jpg','hello@163.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
