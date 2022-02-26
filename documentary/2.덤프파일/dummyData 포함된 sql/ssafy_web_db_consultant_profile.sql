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
-- Table structure for table `consultant_profile`
--

DROP TABLE IF EXISTS `consultant_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consultant_profile` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `topic_category_id` bigint DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `consulting_cnt` int DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `consultant_profile_user_fk` (`user_id`),
  KEY `consultant_profile_topic_categoty_fk` (`topic_category_id`),
  CONSTRAINT `consultant_profile_topic_categoty_fk` FOREIGN KEY (`topic_category_id`) REFERENCES `topic_category` (`id`),
  CONSTRAINT `consultant_profile_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consultant_profile`
--

LOCK TABLES `consultant_profile` WRITE;
/*!40000 ALTER TABLE `consultant_profile` DISABLE KEYS */;
INSERT INTO `consultant_profile` VALUES (1,16,5,'연애 전문가로서 상담을 아주 잘 해드릴 수 있습니다. 믿고 맡겨주세요.',0,'2022-02-17 02:06:10.361000',NULL),(2,20,1,'전교 1등 놓친 적 없어요',0,'2022-02-17 02:15:21.103000',NULL),(3,22,3,'네카라쿠배 다 가게 해드리겠습니다. 믿고 상당을 맡겨주시지요.',0,'2022-02-17 02:16:58.430000',NULL),(5,24,6,'결혼 컨설턴트입니다. 상담 주세요~ 미혼 / 돌싱남 / 돌싱녀 다 상담해드립니다.',0,'2022-02-17 02:19:23.706000',NULL),(8,18,4,'진로와 관련해서 어떠한 고민이든 다 해결할 수 있도록 도와드리겠습니다!!',0,'2022-02-17 11:00:23.215000',NULL),(9,12,1,'학업에 고민이 많으신분들 와서 상담 많이 많이 받으세요!',0,'2022-02-17 11:18:51.815000',NULL),(10,37,3,'취업의 신!! 자소서 합격률 100% 노하우 알려드립니다!',0,'2022-02-17 11:19:05.667000',NULL),(11,29,2,'가족문제로 고민이신분은 편안하게 저한테 말씀해주시면 상담 해드리겠습니다!!',0,'2022-02-17 11:21:59.695000',NULL),(12,6,5,'남친 / 여친 쉽게 일주일 안에 만드는 법 알려드립니다!',0,'2022-02-17 11:23:22.161000',NULL),(13,26,2,'부모님 혹은 형제, 자매와 관련해서 고민있으신분 언제든 채팅보내주세요!! ',0,'2022-02-17 11:27:09.120000',NULL),(14,17,5,'\"연애의 신입니다! 모든 고민 다 듣고 해결해드립니다!\"',0,'2022-02-17 11:27:48.541000',NULL),(15,13,4,'앞길이 막막하다, 보이지 않는다? 바로 길을 알려드립니다!!',0,'2022-02-17 11:28:29.559000',NULL),(16,48,6,'집안사, 가족관계, 부부관계 여러가지 고민 다 들어드립니다. 편하게 상담 신청해주세요',0,'2022-02-17 11:29:27.704000',NULL),(17,31,6,'결혼생활 22년차입니다. 결혼 관련해서 어떠한 상담이든 다 좋아요!',0,'2022-02-17 11:32:47.123000',NULL),(18,9,1,'전교 꼴등에서 전국 수석까지 달성할 수 있었던 비결 알려드립니다!!',0,'2022-02-17 11:36:23.906000',NULL),(19,23,4,'진로, 미래에 대한 고민이 있으시다면 언제든 상담 해드리겠습니다!!',0,'2022-02-17 11:54:25.397000',NULL),(20,32,5,'연애문제, 이별극복법 등등 다양하게 상담해드려요~',0,'2022-02-17 11:55:45.445000',NULL),(21,33,4,'진로에 대한 고민이 있으신분들은 제가 다 해결할 수 있도록 도와드리겠습니다!',0,'2022-02-17 13:02:21.603000',NULL),(22,41,5,'연애문제 전지적 남자시점으로 해결해드립니다!!',0,'2022-02-17 13:06:02.211000',NULL),(23,11,1,'학업 관련한 모든 고민들은 저한테 털어놓으세요~',0,'2022-02-17 13:07:10.255000',NULL),(24,27,3,'취업의 신 입니다. 취업 관련 모든 고민,궁금증 해결 해드리겠습니다!',0,'2022-02-17 13:10:32.955000',NULL),(25,28,6,'결혼을 준비하는 신혼부부들을 위해',0,'2022-02-17 13:11:00.943000',NULL),(26,15,5,'연애 고민 있는 모든 고민 다 해결해드리겠습니다.',0,'2022-02-17 13:13:23.517000',NULL),(27,19,5,'연애고민 10분만에 해결 해드립니다.',0,'2022-02-17 13:13:52.949000',NULL),(28,38,2,'가정사를 함께 나눠봐요',0,'2022-02-17 13:21:27.140000',NULL),(29,2,1,'공부 1등 항상 놓쳤습니다',0,'2022-02-17 13:40:33.792000',NULL),(32,30,4,'프론트엔드와 백엔드 사이의 갈림길...',0,'2022-02-17 14:47:08.199961',NULL),(33,3,5,'\"연애 어렵지만 제가 알려드릴게요!\"',0,'2022-02-17 17:32:47.542000',NULL);
/*!40000 ALTER TABLE `consultant_profile` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-18 11:19:49
