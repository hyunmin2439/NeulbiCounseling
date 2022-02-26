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
-- Table structure for table `community`
--

DROP TABLE IF EXISTS `community`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `community` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `title` varchar(50) NOT NULL,
  `description` varchar(5000) DEFAULT NULL,
  `like_cnt` int DEFAULT '0',
  `view_cnt` int DEFAULT '0',
  `is_deleted` bit(1) DEFAULT b'0',
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `community_user_fk` (`user_id`),
  CONSTRAINT `community_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `community`
--

LOCK TABLES `community` WRITE;
/*!40000 ALTER TABLE `community` DISABLE KEYS */;
INSERT INTO `community` VALUES (1,4,'학업 관련해서 고민이 있습니다!','혹시 학업 분야 좋은 상담가분 추천 해주실분 계실까요??!!',1,47,_binary '\0','2022-02-17 01:31:15.216000',NULL),(2,2,'이 글은 괜찮나요?','잘 읽어지나요?',1,15,_binary '\0','2022-02-17 02:01:25.083000',NULL),(3,47,'안녕하세요!!','혹시 오늘(17)일 15시에 같이 고해성사 하실분 계실까요??!!',0,5,_binary '\0','2022-02-17 02:40:37.706000',NULL),(4,9,'오늘 저녁 메뉴 고민되네요!!','떡볶이 vs 닭발 어떤게 좋을까요ㅠㅠㅠㅠ 추천 해주세요',1,17,_binary '\0','2022-02-17 02:44:01.443000',NULL),(5,8,'우연히 들어와본 사이트인데, 정말 좋네요!','반갑습니다! 앞으로 열심히 활동하겠습니다!',3,21,_binary '\0','2022-02-17 11:08:18.978000',NULL),(6,38,'터넣고 이야기할 수 있는 곳','고해성사 서비스를 이용하니 익명성이 보장되어 마음 편히 이야기할 수 있었어요.\n',1,11,_binary '\0','2022-02-17 11:10:42.971000',NULL),(7,28,'어떻게 살아야할까요?','좋아하는 것과 잘하는 것 사이에 고민이 많습니다...\n여러분의 의견을 주세요!',4,24,_binary '\0','2022-02-17 11:11:58.910000',NULL),(8,40,'들어줄 사람이 필요했는데 감사해요','이야기 들어줄 사람 찾기가 쉽지 않더라고요.. 여기서 많은 사람들을 만나서 좋았습니당',2,12,_binary '\0','2022-02-17 11:13:49.237000',NULL),(9,7,'미래로 가기 vs 과거로 가기','둘 중 하나를 선택할 수 있을 때, 여러분은 어떤 선택을 하실건가요?',3,26,_binary '\0','2022-02-17 11:15:56.724000',NULL),(10,25,'상담가 추천','박프로 상담가님 최고예요!! 가만히 있는데 마음이 편안해지는 느낌 추천합니다!!',2,23,_binary '\0','2022-02-17 11:16:14.203000',NULL),(11,22,'요즘 날씨가 쌀쌀하네요 군고구마 VS 붕어빵 여러분의 선택은?','1. 군고구마\n2. 붕어빵',2,40,_binary '\0','2022-02-17 11:32:07.202000',NULL),(12,38,'번아웃 대책이 있을까요??','싸피 교육만 어느새 8개월째.. 온힘을 쏟아부었더니 번아웃이 오는 것 같습니다. 다들 이럴 때 본인만의 해결방법이나 해소할 수 있었던 경험이 있으신가요??',5,52,_binary '\0','2022-02-17 11:36:00.097000',NULL),(13,30,'남편을 더 이해할 수 있었어요','남편과 같이 산 지 어연 25년.. 25년동안 이해할 수 없었던 남편을 이제야 조금 이해해보려고 합니다. 제 남편도 절 이해 못했겠죠..??ㅎㅎㅎ 세상 모든 남편, 아내들 화이팅!!',1,13,_binary '\0','2022-02-17 11:43:39.685000',NULL),(14,39,'올리브영 세일 시작하네요','늘비님들 챙겨가세요~',2,20,_binary '\0','2022-02-17 11:43:43.473000',NULL),(15,28,'미운 5살이네요','5살배기 울아드램, 말을 안듣기 시작하네용ㅠㅠ',0,39,_binary '\0','2022-02-17 11:51:53.875000',NULL),(16,47,'이별의 아픔을 극복하고 싶습니다.','혹시 추천해주실 방법 있을까요?',1,43,_binary '\0','2022-02-17 16:39:35.085000',NULL);
/*!40000 ALTER TABLE `community` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-18 11:19:55
