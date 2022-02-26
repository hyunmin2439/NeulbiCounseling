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
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `community_id` bigint NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `group_num` int DEFAULT NULL,
  `layer` int DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT b'0',
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `comment_user_fk_idx` (`user_id`),
  KEY `comment_community_fk_idx` (`community_id`),
  CONSTRAINT `comment_community_fk` FOREIGN KEY (`community_id`) REFERENCES `community` (`id`),
  CONSTRAINT `comment_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (2,2,1,'김현민 님이 상담 잘해줘요',2,0,_binary '\0','2022-02-17 02:00:42.788000',NULL),(3,4,1,'오호 감사합니다!!',2,1,_binary '','2022-02-17 02:38:06.133000',NULL),(4,47,1,'학업은 최영진님이 상담 정말 잘해주세요!!',4,0,_binary '\0','2022-02-17 02:42:16.179000',NULL),(5,38,5,'상담가로 만났으면 좋겠어요~!! 곧 만나요',5,0,_binary '\0','2022-02-17 11:11:44.619000',NULL),(6,28,5,'반갑습니다~',6,0,_binary '\0','2022-02-17 11:12:40.325000',NULL),(7,40,7,'좋아하는 것을 선택해야죠!! 해피해피',7,0,_binary '\0','2022-02-17 11:14:16.213000',NULL),(8,25,9,'과거죱!! 아기때로 돌아가서 먹고 자고 먹고 자고 칭찬받을거예요.',8,0,_binary '\0','2022-02-17 11:17:15.874000',NULL),(9,7,10,'맞아요~ 저도 큰 도움을 받은 기억이 있네요',9,0,_binary '\0','2022-02-17 11:17:17.902000',NULL),(10,14,9,'저도요~',8,1,_binary '\0','2022-02-17 11:19:05.129000',NULL),(11,25,6,'맞아요! 한결 편하게 이야기할 수 있어요',11,0,_binary '\0','2022-02-17 11:21:43.218000',NULL),(12,25,8,'이야기를 들어주는 것만으로도 심란한 마음이 위로가 되는 것 같아요. ',12,0,_binary '\0','2022-02-17 11:25:12.349000',NULL),(13,8,9,'제 앞날이 너무 궁금해서 미래로 가보고 싶네용',13,0,_binary '\0','2022-02-17 11:25:31.855000',NULL),(14,8,10,'여긴 모든 상담가님들이 친절하고 좋더라구요',14,0,_binary '\0','2022-02-17 11:27:34.241000',NULL),(15,22,4,'이 글 보니 닭발이 먹고 싶어졌네요ㅠ',15,0,_binary '\0','2022-02-17 11:34:32.547000',NULL),(16,38,11,'따땃한 군고구마',16,0,_binary '\0','2022-02-17 11:39:02.108000',NULL),(17,8,12,'저는 처음 시작의 목표를 다시 되새기는 것 같아요! 그럼 동기부여가 되더라구요',17,0,_binary '\0','2022-02-17 11:39:55.991000',NULL),(18,38,7,'좋아하는 것과 잘하는 것 사이 고민이 많죠ㅜㅜ',18,0,_binary '\0','2022-02-17 11:40:13.225000',NULL),(19,30,12,'8개월동안 온힘을 다하느라 고생했어 지훈아!! 천천히 하나씩 앞으로 가보자',19,0,_binary '\0','2022-02-17 11:45:04.512000',NULL),(20,39,11,'따땃한 붕어빵',20,0,_binary '\0','2022-02-17 11:45:19.346000',NULL),(21,30,14,'올리브영~~ 찬호님 추천템은 뭔가요??',21,0,_binary '\0','2022-02-17 11:45:36.277000',NULL),(22,30,9,'아기때는 먹고자고싸기만 해도 칭찬을 받았죠.. 부러운 친구들..',8,1,_binary '\0','2022-02-17 11:46:37.071000',NULL),(23,28,11,'3. 뜨끈뜨끈 어묵은 어떠신가요?',23,0,_binary '\0','2022-02-17 11:46:43.303000',NULL),(24,10,12,'저는 바다를 보러가요. 넓은 바다를 보고 있으면 마음이 한결 편안해지는 것 같아요.',24,0,_binary '\0','2022-02-17 11:48:05.798000',NULL),(25,10,11,'어묵탕 좋죠!! 국물 한 모금 마시면 온몸이 따땃해집니당',23,1,_binary '\0','2022-02-17 11:51:00.748000',NULL),(26,9,11,'둘다 먹죠!!',26,0,_binary '\0','2022-02-17 11:59:25.529000',NULL),(27,10,15,'아유 시간이 약입니다 화이팅하세요ㅜㅜ',27,0,_binary '\0','2022-02-17 13:32:36.989000',NULL),(28,28,13,'같이 산다는 것은 어쩌면 서로 양보를 할 수 밖에 없는 거 같아요~ ',28,0,_binary '\0','2022-02-17 13:32:37.662000',NULL),(29,2,15,'그러게요',27,1,_binary '','2022-02-17 13:49:08.572000',NULL),(30,2,15,'그러게요',27,1,_binary '','2022-02-17 13:49:29.672000',NULL),(31,2,12,'그런가요?',19,1,_binary '','2022-02-17 13:49:46.630000',NULL),(32,17,11,'나도 붕어빵 먹고 싶다',20,1,_binary '\0','2022-02-17 13:49:49.254000',NULL),(33,24,11,'최고의 안주!',23,1,_binary '\0','2022-02-17 13:49:51.483000',NULL),(34,24,11,'군고구마 좋죠',26,1,_binary '\0','2022-02-17 13:50:44.988000',NULL),(35,28,15,'감자합니다!!',27,1,_binary '\0','2022-02-17 14:06:49.249193',NULL),(36,13,15,'화이팅입니다!!',27,1,_binary '\0','2022-02-17 14:17:34.284164',NULL),(37,1,12,'저도 바다 보는거 좋아합니다!',24,1,_binary '','2022-02-17 18:22:21.575000',NULL),(38,17,12,'저도 바다 보는 거 좋아해요. 밤바다를 멍하니 보고 있으면 마음이 저절로 홀가분해져요!',24,1,_binary '\0','2022-02-17 18:23:38.349000',NULL);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-18 11:19:50
