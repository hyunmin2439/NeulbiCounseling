-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: i6E202.p.ssafy.io    Database: ssafy_web_db
-- ------------------------------------------------------
-- Server version	8.0.28-0ubuntu0.20.04.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` int DEFAULT '1',
  `mask_id` int DEFAULT '1',
  `mask_back` int DEFAULT '1',
  `nickname` varchar(50) DEFAULT NULL,
  `social_id` varchar(60) DEFAULT '0',
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(60) DEFAULT NULL,
  `report_cnt` int DEFAULT '0',
  `point_tot` double DEFAULT '0',
  `profile_img` varchar(255) DEFAULT NULL,
  `is_consultant` bit(1) DEFAULT b'0',
  `is_penalty` bit(1) DEFAULT b'0',
  `is_deleted` bit(1) DEFAULT b'0',
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nickname_UNIQUE` (`nickname`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,2,4,4,'운영자','0','admin@admin.com','$2a$10$By9h0RRwk5khLcrOH2965eNxs8YNRZ15HDPpO35xSLhbZveOonYmu',0,0,'a3e0121b5a594196bfb62ee76d489414.png',_binary '\0',_binary '\0',_binary '\0','2022-02-17 01:25:44.964000',NULL),(2,1,4,4,'박영찬','0','qkrdudcks@naver.com','$2a$10$7xkOxxluZFbHvujzKdGKoOit8IvhKueLLZYHvwpBBd9BjU4cQDWQG',0,0,'e441b9b8589b41e3918de23982c04dca.jpg',_binary '',_binary '\0',_binary '\0','2022-02-17 01:25:48.528000',NULL),(3,1,1,4,'2조팀장최고','0','wjdwogh@naver.com','$2a$10$DOT/ohpz9DhtXoQBJm6DS.z0Uwhcmdb2HS7axDUdlQdN0CAXDjusK',0,48,'39a893940d9f41328b59a89529feeb98.PNG',_binary '',_binary '\0',_binary '\0','2022-02-17 01:25:54.988000',NULL),(4,1,1,1,'테스트용','0','test@test.com','$2a$10$.ovGdmw1A2INmqVez4mkYesWpxs9DkI2j/MWws9L4yzHYBtznVZm.',0,0,'default-profile-image.jpg',_binary '\0',_binary '\0',_binary '\0','2022-02-17 01:26:24.155000',NULL),(5,1,2,3,'강동옥','0','rkdehddhr@naver.com','$2a$10$FbzSU2SG5n/kz5As17mj4O0KZDH28DkkDJtBB1lxK4HVf1Nz4d1v6',0,0,'68d88334ab9d41bda4fb5dfa12274738.jpg',_binary '\0',_binary '\0',_binary '\0','2022-02-17 01:26:34.623000',NULL),(6,1,5,2,'강소현','0','rkdthgus@naver.com','$2a$10$LgblPOLABZLoyEw75m9eGuvIopFzHbqzwPiN0Yk3kkvbdlVhc0KLy',0,0,'30f65f85e8ed4016b99d76f2cb3b69ef.jpg',_binary '',_binary '\0',_binary '\0','2022-02-17 01:27:30.233000',NULL),(7,1,4,4,'김영기','0','rladudrl@naver.com','$2a$10$nM65UVowleW6RkvE/4iqseknWSYmoV3UVBTF2y/QyerLqEMBBXpzC',0,0,'256061cc295c4833ae94baf0baf46868.jpg',_binary '\0',_binary '\0',_binary '\0','2022-02-17 01:28:25.920000',NULL),(8,1,4,4,'이정원','0','dlwjddnjs@naver.com','$2a$10$rf5wyIUAyrAAVSeB0ynWc.g44bwQ2jbi/52.52MmFLXMByWxM92p6',0,0,'0028a133c1ab455998583f9a6777853e.jpg',_binary '\0',_binary '\0',_binary '\0','2022-02-17 01:28:30.938000',NULL),(9,1,5,2,'최영진','0','chldudwls@naver.com','$2a$10$KwZ49RETGtRhbw7kgE3s5uiRKktMuVYTRpdY6yg/VDSAzA/leJOVC',0,26,'986653bdf0744c5e9f9ea593b4a74d7e.jpg',_binary '',_binary '\0',_binary '\0','2022-02-17 01:28:56.853000',NULL),(10,1,9,3,'김윤지','0','rladbswl@naver.com','$2a$10$nqCLNAhdrcbISMs2wOq20.9/38q0kWpGnN7CihWFf23BFvftA/L76',0,0,'3a9dafea4a3f4ca18351a4cf38d6f55a.jpg',_binary '\0',_binary '\0',_binary '\0','2022-02-17 01:29:22.449000',NULL),(11,1,5,1,'이정현','0','dlwjdgus@naver.com','$2a$10$tfOgxKIb/6nfE6wbTBjR9OPQY8sVcDJD/uSqM/lGgryXvL7LsV/de',0,0,'34379bf3aec243aab0542f61b80fef96.jpg',_binary '',_binary '\0',_binary '\0','2022-02-17 01:29:33.302000',NULL),(12,1,6,2,'김지슬','0','rlawltmf@naver.com','$2a$10$iwkz3dT2lo5vh2glKyFlnO7fAPokY1VYZrlnlOcfU.9xnW2LxCQmi',0,0,'aa305be1b5bf4cb79ff260aee7cff844.jpg',_binary '',_binary '\0',_binary '\0','2022-02-17 01:30:33.399000',NULL),(13,1,5,2,'임경훈','0','dlarudgns@naver.com','$2a$10$lyyqKX09Db0zeakZWPotS.C1wwTk8h3t/Zw.nG7mIVXtsPu7QSrrG',0,0,'b5790481e3fe441598232a714158e8a0.jpg',_binary '',_binary '\0',_binary '\0','2022-02-17 01:30:42.105000',NULL),(14,1,2,2,'김창민','0','rlackdals@naver.com','$2a$10$s.PA6O8eISc8wxCP5ol4Se6n.1zVtDVZEZ0Eqy/N4QMUlrEJ8Qr66',0,0,'6f6ec99d2af74a189a2f072c8197c8b8.jpg',_binary '\0',_binary '\0',_binary '\0','2022-02-17 01:31:10.219000',NULL),(15,1,6,4,'임창현','0','dlackdgus@naver.com','$2a$10$2k.8zWaOWjibY74oKzPy8OJu8cEGc8kGcuoZ2UZsEx9eRfwPktiry',0,0,'f9fa8fc101b247f08771976d8750ba62.jpg',_binary '',_binary '\0',_binary '\0','2022-02-17 01:32:33.217000',NULL),(16,1,6,3,'김태현','0','rlaxogus@naver.com','$2a$10$0hCwnTjIvaGfW73pqPw2qe75uwumcVafkbjP8kh71.0UhS0R0IKku',0,0,'735e69a31a30498cbd8c90197af3617b.jpg',_binary '',_binary '\0',_binary '\0','2022-02-17 02:04:19.907000',NULL),(17,1,8,2,'김현민','0','rlagusals@naver.com','$2a$10$9flH2fixJ/UPhAx7Am.6nOjaAicN5Ra20UPiJ2bOJMURQKPRQj0iG',0,20,'a067039c0c074b6caeea337497632faf.jpg',_binary '',_binary '\0',_binary '\0','2022-02-17 02:04:56.610000',NULL),(18,1,3,2,'박동준','0','qkrehdwns@naver.com','$2a$10$8FuBuhv0.aiwbZRmXqyxLO/FFgiThHZY/lrH7001EWqs42H9EeXQy',0,0,'1d94ee84e08341a5a44d196f0da8c93a.png',_binary '',_binary '\0',_binary '\0','2022-02-17 02:12:39.849000',NULL),(19,1,6,4,'박상준','0','qkrtkdwns@naver.com','$2a$10$SGjLRBb5W2UVJOrxt1P7zuUTbZH2Ctg3SdYovOACxpF46N8nZaMy2',0,0,'980d67691b804332b64c1f3e2a070c64.png',_binary '',_binary '\0',_binary '\0','2022-02-17 02:14:36.898000',NULL),(20,1,5,2,'장영남','0','wkddudska@naver.com','$2a$10$lESvoaLKJhP.q2SLGirJfu7w4210rgtmgL.g9Fem1BbQoQjEuKzfe',0,0,'c0fa1921376e470583d28b7002125309.jpg',_binary '',_binary '\0',_binary '\0','2022-02-17 02:14:45.111000',NULL),(21,1,8,2,'전호정','0','wjsghwjd@naver.com','$2a$10$wlOHoSGgG15Jx2vlk437q.UcaegyPOn1RkygNtYQuekkDO7eubXLG',0,0,'36b5c7902a8e4f08ba7552d4a950dba4.jpg',_binary '\0',_binary '\0',_binary '\0','2022-02-17 02:15:40.454000','2022-02-17 14:00:51.730000'),(22,1,4,2,'반형동','0','qksgudehd@naver.com','$2a$10$jPlGVl2OnWYpp9yt/TBL1u1ZSovjOrf5td4zeQZhybqZcr5J4AfEu',0,0,'928e71e2f3464034bf900ebd030e962c.jpg',_binary '',_binary '\0',_binary '\0','2022-02-17 02:15:57.593000',NULL),(23,1,4,2,'정순일','0','wjdtnsdlf@naver.com','$2a$10$JCtgmrlcC3BZKQbPNySSf.UQfgjfMkoSTvWfxjtRvCJW5xo.TM1km',0,0,'6fe38671321f4414a3bdbe2bf8cb01de.jpg',_binary '',_binary '\0',_binary '\0','2022-02-17 02:16:51.235000',NULL),(24,1,7,2,'손은성','0','thsdmstjd@naver.com','$2a$10$uX20/qorMeN2CJwhIrWvYOZEfd5OmJ4P7ncdARKnig986YW8cvqR.',0,5,'52b535c1f845480b80786165bd14ad13.jpg',_binary '',_binary '\0',_binary '\0','2022-02-17 02:17:51.157000',NULL),(25,1,5,2,'정인수','0','wjddlstn@naver.com','$2a$10$VnGpICSPhiHfGFdqfehw5eV/GyStXkLk62hSSjavVXJZWn8Refm/a',0,0,'303e8d6633e847b8a772bd55d1e7ecb7.jpg',_binary '\0',_binary '\0',_binary '\0','2022-02-17 02:17:59.143000',NULL),(26,1,7,2,'조은누리','0','whdmssnfl@naver.com','$2a$10$bWECB3mv1uFjye0aUHZZROdQyfSXvud4BQJ/LGoyAloDWU66ka.uO',0,0,'f467813de2d441e3bd025ab7a6239d15.jpg',_binary '',_binary '\0',_binary '\0','2022-02-17 02:19:42.034000',NULL),(27,1,3,3,'조현아','0','whgusdk@naver.com','$2a$10$RoYDe575Y4kK.bLTWGj0AeREzobNHPa2UDj38f0eAtjukVKg3K7Li',0,0,'e6f139cb00154500a5743265107b4409.jpg',_binary '',_binary '\0',_binary '\0','2022-02-17 02:20:10.005000',NULL),(28,1,1,1,'손한기','0','thsgksrl@naver.com','$2a$10$eh1oaWd3yhRaAQLBdarkheifH0AW8UTV5vEQHaQmUBHXX7BAz4WIy',1,4,'default-profile-image.jpg',_binary '',_binary '\0',_binary '\0','2022-02-17 02:20:14.372000',NULL),(29,1,9,2,'지수민','0','wltnals@naver.com','$2a$10$hYVisyp72NoxPs6c4JrgAOgHpX9juZurnlXAxwkDYzG9ahPwsnm0S',0,0,'e8c140c9cb9a4d99b8567832eede0dfd.jpg',_binary '',_binary '\0',_binary '\0','2022-02-17 02:20:46.312000',NULL),(30,1,4,2,'차상훈','0','cktkdgns@naver.com','$2a$10$cK91TXkD8pw4tK6RWjNXiOxH5yy.EcBTQfdR.qh878/5RpUkpj.Hu',0,0,'9a7aacce441641fdaa342e7613fc3e0e.jpg',_binary '',_binary '\0',_binary '\0','2022-02-17 02:21:36.611000',NULL),(31,1,8,3,'최명재','0','chlaudwo@naver.com','$2a$10$Ny5POAI3cItz1nl68lgcUeI0lmREz1bl5/ssFlRQpq8SR544.KgTu',0,0,'0775eadfefe94bc68295e83c340a5bd6.jpg',_binary '',_binary '\0',_binary '\0','2022-02-17 02:22:22.113000',NULL),(32,1,3,4,'최상후','0','chltkdgn@naver.com','$2a$10$JODI0iJ9TSWNNnjVppxli.n3oQy8L.umfBcwsktv4HrwI4EEs5fZu',1,0,'8510994c818f4cfbb991f05495d1af6b.jpg',_binary '',_binary '\0',_binary '\0','2022-02-17 02:23:18.782000',NULL),(33,1,9,2,'최소희','0','chlthgml@naver.com','$2a$10$5F3oX5kk3uSUT9RFgNy8Femoeb/fTNRl085c3umd7iSjBawvBZmzK',0,0,'c37844a094954af5a55649933c80f0fb.jpg',_binary '',_binary '\0',_binary '\0','2022-02-17 02:23:46.255000',NULL),(35,1,5,2,'최이삭','0','chldltkr@naver.com','$2a$10$v8SSV47jXeZfLS8b2jIFH.ZyZ8GDXf9ipelML75Rvpg2jL6I7BnYi',0,0,'70822234d2df4d67a8668a0df70b1b1a.jpg',_binary '\0',_binary '\0',_binary '\0','2022-02-17 02:24:50.316000',NULL),(37,1,2,3,'안영원','0','dksduddnjs@naver.com','$2a$10$YieU4/8XuIi5E7avV3F3ZOTC0rWBtW/EE9Lf/VDdBmS/xbUUqvAqi',0,0,'3997736db16a45979bd32c17dbd45872.jpg',_binary '',_binary '\0',_binary '\0','2022-02-17 02:27:49.428000',NULL),(38,1,7,2,'양지훈','0','didwlgns@naver.com','$2a$10$Cb1wMV/Xsb7h1lxVs7X6ceKjd6.hkTP2Xm2cm/WJeF8tHRvbI6Lhi',0,2,'02452e29cbc34782ad4aec4372de61ac.jpg',_binary '\0',_binary '\0',_binary '\0','2022-02-17 02:28:41.888000',NULL),(39,1,4,4,'윤찬호','0','dbscksgh@naver.com','$2a$10$4cmk4Olg4cGvErR5F79VbO1B0T5i.m7BU51XMhVE70qmDE2Draam.',0,0,'71dfe2d8feda4bc4a9651e5aeb3c954f.jpg',_binary '\0',_binary '\0',_binary '\0','2022-02-17 02:29:27.486000',NULL),(40,1,4,3,'이승훈','0','dltmdgns@naver.com','$2a$10$8yRooIYvS4ZUA1yF8jqj6uRm7UZdqkQbunJa1/9jJkZhzBZ.lxhIa',0,0,'702d796d1c8a408dbe08e397058757ef.jpg',_binary '\0',_binary '\0',_binary '\0','2022-02-17 02:30:03.186000',NULL),(41,1,3,2,'이우철','0','dldncjf@naver.com','$2a$10$G4kOmP9qJwKHEHsqBxVbBOoAnTv93Tn8Mkc/mHxnRxXnbWX5Ffij.',0,0,'e38f8bc045be4fe695f08ab68330fe3e.jpg',_binary '',_binary '\0',_binary '\0','2022-02-17 02:30:43.659000',NULL),(43,1,4,3,'김컨설턴트','0','rlatlsdlf@naver.com','$2a$10$UVToJ8JhAvweXoY0I424XucjEsqmwQzlah2sCsmNR2ohAbRN/eg3W',0,0,'dc0cc17639e74d08a1e15a9dc6a4682a.jpg',_binary '\0',_binary '\0',_binary '\0','2022-02-17 02:32:27.119145',NULL),(44,1,8,4,'정코치','0','wjddndud@naver.com','$2a$10$CJMdCQgKc03DmyrlnYrjXOwFOfczdWQqT/1KFQdepXLVxgH2VnsqO',0,0,'b724899cf3794a2787c09249a8da6805.jpg',_binary '\0',_binary '\0',_binary '\0','2022-02-17 02:32:30.499000',NULL),(45,1,1,1,'이코치','0','dlekdms@naver.com','$2a$10$Idru8XoZq1LBdjqlfQOsKeL6NT9kAyM/hfmVJMe6AFDSqMJ1j3c3i',0,0,'default-profile-image.jpg',_binary '\0',_binary '\0',_binary '\0','2022-02-17 02:32:48.160610',NULL),(46,1,1,1,'박프로','0','qkrguswjd@naver.com','$2a$10$gbHyiMZBZFT4aIQjEWjkYuEJui0zYnPHzIe5mwIdi0frt6P/qNHeK',0,0,'291dedc0330643bca07d18b445818793.jpg',_binary '\0',_binary '\0',_binary '\0','2022-02-17 02:33:04.933834',NULL),(47,1,2,4,'어서빈대떡','0','djtjqls@naver.com','$2a$10$rGE3fFEPpQv1YsbXKXlp2e5HHt5d907JlINPZT4iM2/6y3/DNAZeS',0,0,'d3780278ee724e518fd861e72f57d850.jpg',_binary '\0',_binary '\0',_binary '\0','2022-02-17 02:39:53.901000',NULL),(48,1,7,1,'김동현','0','rlaehdgus@naver.com','$2a$10$BOvfbWDxcIE6cmpiPXuuR.OM.gb49ejab3ZvJyZzs2QyVoG0M4l62',0,0,'default-profile-image.jpg',_binary '',_binary '\0',_binary '\0','2022-02-17 10:06:07.412000',NULL),(50,1,1,1,'aa','0','aa@ssafy.com','$2a$10$/MR5FMmRVcH4ATH13q70IedzjX0yWRclPvC.Ss7FANSEgjvkfmlW2',0,0,'default-profile-image.jpg',_binary '\0',_binary '\0',_binary '\0','2022-02-18 06:00:55.033000',NULL),(51,1,1,1,'김현민5137','101513614115071019685','ml2439@naver.com','$2a$10$GTxrE0O6FHU/QonPsGh1UePGTlKbPnjCvHMhoq4ITxP.xnwqu97BO',0,0,'default-profile-image.jpg',_binary '\0',_binary '\0',_binary '\0','2022-02-18 06:11:02.916000',NULL),(52,1,1,1,'ssafy','0','test@ssafy.com','$2a$10$nYxCqslLHbFLVaOlQtdEC.kv1sf2u6NyHFkcllP4NeYynNM6sDpsO',0,0,'default-profile-image.jpg',_binary '\0',_binary '\0',_binary '\0','2022-02-18 09:19:32.862000',NULL),(53,1,1,1,'오일남','0','zz@zzz.zzz','$2a$10$6H4IQ0UhvT9ZLWPo09B.oOpSakXvzmnIQZ0FRXJDAfn9DxRUZg4.m',0,0,'default-profile-image.jpg',_binary '\0',_binary '\0',_binary '\0','2022-02-18 09:45:48.817000',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-18 11:19:53
