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
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `consultant_profile_id` bigint NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `point` int DEFAULT '1',
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `review_user_fk_idx` (`user_id`),
  KEY `review_consultant_profile_fk_idx` (`consultant_profile_id`),
  CONSTRAINT `review_consultant_profile_fk` FOREIGN KEY (`consultant_profile_id`) REFERENCES `consultant_profile` (`id`),
  CONSTRAINT `review_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (1,18,5,'너무 상담을 잘해주셔서 감동 먹었습니다!',10,'2022-02-17 11:03:38.909000',NULL),(2,35,18,'상담 너무 잟 해주셔서 감사합니다!',8,'2022-02-17 11:52:48.839000',NULL),(3,28,28,'별 하나드립니다',2,'2022-02-17 13:26:23.862000',NULL),(4,17,5,'상담이 너무 불성실했어요.... 평가가 좋은 이유는 잘 모르겠습니다.... 조금 더 친절한 분에게 상담 받으면 좋을 것 같아요....',6,'2022-02-17 13:29:22.108000',NULL),(5,31,5,'이 사람 정말 착하다!',10,'2022-02-17 13:30:13.562000',NULL),(6,31,5,'이 사람 너무 착하다!',10,'2022-02-17 13:30:20.087000',NULL),(7,31,5,'점수가 중복으로 들어가진 않나??',10,'2022-02-17 13:30:36.723000',NULL),(8,10,25,'전배우자 얘기를 하시더라고요;;;',4,'2022-02-17 13:30:58.566000',NULL),(9,14,18,'상담을 너무 친절하게 잘해주셔요! 큰 도움이 됐습니다!',8,'2022-02-17 13:35:15.151000',NULL),(10,12,18,'너무 좋은 상담이었습니다!!!!',10,'2022-02-17 13:38:31.129000',NULL),(11,2,14,'잘 해요',10,'2022-02-17 13:56:08.304000',NULL),(12,47,14,'이별의 아픔을 극복할 수 있었습니다!!',10,'2022-02-17 16:37:26.516000',NULL);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-18 11:19:54
