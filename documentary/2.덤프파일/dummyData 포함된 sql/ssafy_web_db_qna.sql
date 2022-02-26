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
-- Table structure for table `qna`
--

DROP TABLE IF EXISTS `qna`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qna` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `title` varchar(50) NOT NULL,
  `description` varchar(5000) DEFAULT 'null',
  `is_rocked` bit(1) DEFAULT b'0',
  `is_deleted` bit(1) DEFAULT b'0',
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `is_answered` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `qna_user_fk_idx` (`user_id`),
  CONSTRAINT `qna_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qna`
--

LOCK TABLES `qna` WRITE;
/*!40000 ALTER TABLE `qna` DISABLE KEYS */;
INSERT INTO `qna` VALUES (1,4,'궁금합니다!!','현재 새벽1시인데 정말 자다가도 답변 달아주시나요??',_binary '\0',_binary '\0','2022-02-17 01:33:50.542000',NULL,_binary ''),(2,4,'서비스 이용관련 질문있습니다.','지금은 주무시겠죠??!! ㅎㅎ',_binary '\0',_binary '\0','2022-02-17 02:35:49.265000','2022-02-17 02:36:45.928000',_binary ''),(3,17,'사이트 이용법 질문있어요!','사이트 이용법 같은 것은 어디서 볼 수 있나요???',_binary '\0',_binary '\0','2022-02-17 18:32:35.130000',NULL,_binary '\0'),(4,9,'문의드립니다!','저 왜 프사가 고릴라였죠?',_binary '\0',_binary '\0','2022-02-17 18:35:15.784027',NULL,_binary ''),(5,15,'안녕하세요! 이번에 새로 가입한 늘비입니다!','상담가가 되기 위한 특별한 조건 같은게 있나요?!',_binary '\0',_binary '\0','2022-02-17 23:39:12.486000','2022-02-17 23:39:52.546000',_binary ''),(6,15,'안녕하세요 질문이 있습니다!','게시판은 무슨 내용을 담아서 작성해야 하나요?',_binary '',_binary '\0','2022-02-17 23:42:54.860000',NULL,_binary '\0');
/*!40000 ALTER TABLE `qna` ENABLE KEYS */;
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
