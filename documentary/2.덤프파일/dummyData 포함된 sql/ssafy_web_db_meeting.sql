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
-- Table structure for table `meeting`
--

DROP TABLE IF EXISTS `meeting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meeting` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `call_end_time` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_active` bit(1) NOT NULL,
  `participants` int NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `meeting_category_id` bigint DEFAULT NULL,
  `topic_category_id` bigint DEFAULT NULL,
  `owner_id` bigint DEFAULT NULL,
  `call_start_time` datetime(6) DEFAULT NULL,
  `chat_room_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe6dkaudy7c9cub0bwrids40vw` (`meeting_category_id`),
  KEY `FKq01ohlj5n095jsieuw57ffd60` (`topic_category_id`),
  KEY `FKbfqt1aw0yrvne5wjontkps4uu` (`owner_id`),
  CONSTRAINT `FKbfqt1aw0yrvne5wjontkps4uu` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKe6dkaudy7c9cub0bwrids40vw` FOREIGN KEY (`meeting_category_id`) REFERENCES `meeting_category` (`id`),
  CONSTRAINT `FKq01ohlj5n095jsieuw57ffd60` FOREIGN KEY (`topic_category_id`) REFERENCES `topic_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meeting`
--

LOCK TABLES `meeting` WRITE;
/*!40000 ALTER TABLE `meeting` DISABLE KEYS */;
INSERT INTO `meeting` VALUES (2,'2022-02-17 01:29:48.834000','고민성사 오픈비두 성공기원합니다!!!',_binary '\0',6,'고민성사가 되기를....','2022-02-17 01:28:51.986000','2022-02-17 01:29:48.834000',1,1,3,NULL,0),(4,'2022-02-17 11:08:27.576000','Advice Start',_binary '\0',2,'Advice','2022-02-17 11:02:04.296000','2022-02-17 11:09:32.819000',2,6,24,NULL,2),(5,NULL,'진로를 고민하고 있는 사람들끼리 서로의 속마음을 터놓아보아요',_binary '',3,'어디로 가야할지 모르겠나요? 함께 나눠요','2022-02-17 11:02:46.339000',NULL,1,4,26,NULL,0),(6,'2022-02-17 11:04:08.115000','진로에 고민이 있는 사람끼리 나눠봐요',_binary '\0',3,'어디로 가야할지 모르겠나요?','2022-02-17 11:04:00.814000','2022-02-17 11:04:08.115000',1,4,26,NULL,0),(7,NULL,'팀장이었던 썰 풉니다!!!',_binary '',5,'공통 프로젝트 ','2022-02-17 11:05:27.868000',NULL,1,1,33,NULL,0),(8,'2022-02-17 11:13:17.033000','연애 기술 알려줄 사람만 들어오세요!! 급구급구!!',_binary '',3,'모쏠이라니...','2022-02-17 11:13:11.332000','2022-02-17 11:13:17.033000',1,5,20,NULL,0),(9,'2022-02-17 14:02:10.154000','집안일 하기 힘든 사람 오세요',_binary '\0',5,'집안일이 힘든 사람!','2022-02-17 11:13:54.764000','2022-02-17 14:02:10.154000',1,2,21,NULL,0),(10,'2022-02-17 11:18:35.801000','취준 고민 떠들면서 스트레스 풀자!',_binary '',5,'취준생들 모여라!','2022-02-17 11:18:19.340000','2022-02-17 11:18:35.801000',1,3,13,NULL,0),(11,NULL,'모델부터 뮤지컬까지 모두 다 경험한 얘기 풉니다!!',_binary '',6,'나의 인생 얘기 푼다','2022-02-17 11:19:06.585000',NULL,1,4,41,NULL,0),(12,'2022-02-17 11:44:33.763000','결혼 상대가 맘에 드는 듯 안 드는 듯...',_binary '\0',4,'이 결혼 해도 될까??','2022-02-17 11:19:56.340000','2022-02-17 11:44:33.763000',1,6,31,NULL,0),(13,NULL,'ssafy생들이여 나와 함께 네카라쿠배로 가즈아~~',_binary '',4,'네카라쿠배','2022-02-17 11:20:32.365000',NULL,1,3,11,NULL,0),(14,NULL,'공부 잘 하는 사람들 같이 얘기 좀 해줘요 ㅠㅠ',_binary '',3,'수능 한달 남았는데 너무 걱정돼요','2022-02-17 11:21:32.264000',NULL,1,1,23,NULL,0),(15,NULL,'네카라쿠배당토에 대한 설명 나만 믿고 따라오세요!!!',_binary '',4,'요새는 네카라쿠배당토!!','2022-02-17 11:21:45.864000',NULL,1,3,27,NULL,0),(16,'2022-02-17 21:42:19.154242','솔직히 네가 잘못했잖아',_binary '\0',3,'여친이랑 싸웠다...','2022-02-17 11:22:47.965000','2022-02-17 21:42:19.154270',1,5,32,NULL,0),(17,'2022-02-17 13:48:33.963592','Advice Start',_binary '\0',2,'Advice','2022-02-17 11:51:00.145000','2022-02-17 13:48:33.963592',2,1,9,NULL,13),(18,NULL,'Advice Start',_binary '',2,'Advice','2022-02-17 13:13:17.335000',NULL,2,6,28,NULL,15),(19,'2022-02-17 13:25:59.393000','Advice Start',_binary '\0',2,'Advice','2022-02-17 13:22:12.665000','2022-02-17 13:25:59.393000',2,2,38,NULL,16),(20,'2022-02-17 13:28:46.402000','Advice Start',_binary '\0',2,'Advice','2022-02-17 13:28:31.100000','2022-02-17 13:28:46.402000',2,6,24,NULL,17),(21,'2022-02-17 13:30:06.534000','Advice Start',_binary '\0',2,'Advice','2022-02-17 13:29:03.841000','2022-02-17 13:30:06.534000',2,6,24,NULL,18),(22,'2022-02-17 13:30:49.279000','Advice Start',_binary '\0',2,'Advice','2022-02-17 13:30:06.099000','2022-02-17 13:30:49.279000',2,6,28,NULL,19),(23,'2022-02-17 13:39:01.518625','Advice Start',_binary '\0',2,'Advice','2022-02-17 13:34:02.826000','2022-02-17 13:39:01.518625',2,1,9,NULL,21),(24,'2022-02-17 13:38:23.545000','Advice Start',_binary '\0',2,'Advice','2022-02-17 13:36:25.919000','2022-02-17 13:38:23.545000',2,1,9,NULL,6),(25,'2022-02-17 20:54:12.233000','Advice Start',_binary '\0',2,'Advice','2022-02-17 13:54:54.053000','2022-02-17 20:54:12.233000',2,1,2,NULL,22),(26,'2022-02-17 13:56:04.052000','Advice Start',_binary '\0',2,'Advice','2022-02-17 13:55:02.570000','2022-02-17 13:59:22.255000',2,3,17,NULL,27),(27,NULL,'내가 꿀팁 다 전수한다!!!!',_binary '',6,'네카라쿠배 가고 싶은 사람 다 모여라!!!!!','2022-02-17 15:29:57.275000',NULL,1,3,17,NULL,0),(28,'2022-02-17 16:36:49.744000','Advice Start',_binary '\0',2,'Advice','2022-02-17 16:07:38.639000','2022-02-17 16:36:49.744000',2,5,17,NULL,32),(29,'2022-02-17 16:12:44.676261','Advice Start',_binary '\0',2,'Advice','2022-02-17 16:12:27.860750','2022-02-17 16:12:44.676261',2,1,9,NULL,31),(30,'2022-02-17 21:46:27.272556','Advice Start',_binary '\0',2,'Advice','2022-02-17 21:44:48.907292','2022-02-17 21:46:27.272582',2,1,20,NULL,34),(31,'2022-02-18 10:27:27.420000','파이팅!',_binary '\0',3,'모두 힘내서 마무리합시다','2022-02-18 10:09:40.301000','2022-02-18 10:34:17.982000',1,1,2,NULL,0),(32,'2022-02-18 10:57:00.551000','Advice Start',_binary '\0',2,'Advice','2022-02-18 10:27:42.132000','2022-02-18 10:57:00.551000',2,5,6,NULL,39);
/*!40000 ALTER TABLE `meeting` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-18 11:19:51
