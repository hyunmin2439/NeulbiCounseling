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
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `title` varchar(50) DEFAULT NULL,
  `description` varchar(5000) DEFAULT NULL,
  `view_cnt` int DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `notice_user_fk_idx` (`user_id`),
  CONSTRAINT `notice_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` VALUES (1,1,'늘비상담 공지사항','안녕하세요. 운영자 입니다.\n아직 사이트가 생긴지 얼마 되지 않아서 부족함이 많죠? ㅠㅠ\n곧 내용이 많이 추가될거에요!\n조금만 인내심을 갖고 기다려주세요!!\n궁금하신 사항이 있으시면 QnA에 글 남겨주세요!\n자다가도 벌떡 일어나서 답변 달겠습니다 ㅋㅋ',18,'2022-02-17 01:29:56.069000','2022-02-17 01:30:02.443000',_binary '\0'),(2,1,'[긴급] 서버 긴급점검 안내','안녕하세요! 늘비상담 운영자 입니다^^\n내일(17일) 서버 점검으로 인해 00:10 ~ 00:30 까지 서비스 이용이 어렵습니다.\n많은 양해 부탁드리겠습니다.\n',18,'2022-02-17 02:18:11.500000','2022-02-17 02:22:16.383000',_binary '\0'),(3,1,'[필독] 운영자 사칭 주의부탁드립니다.','안녕하세요. 늘비상담 여러분 :)\n항상 늘비상담을 찾아주시는 여러분들께 감사의 말씀을 드립니다.\n운영자 사칭 조심하세요!\n',16,'2022-02-17 02:29:39.272000',NULL,_binary '\0'),(4,1,'[필독] 늘비상담 이용법','안녕하세요 늘비상담 여러분 :)\n\n간단한 사이트 이용법과 지켜야 할 예절들을 알려드릴게요!\n\n사용방법\n\n1. 회원가입 후 로그인을 한다!\n\n2. 프로필로 들어가 원하는 프로필과 자신만의 캐릭터를 만든다!\n\n3. 고해성사로 들어가 원하는 방에 들어간다!\n\n4. 늘비님들간에 자유로운 고충토로 즐거운 얘기를 나눈다!\n\n5. 자신의 힘든 고민이 있을 경우 고민상담으로 간다!\n\n6. 자신이 마음에 드는 상담가를 선택하여 채팅으로 약속을 잡는다!\n\n7. 상담가와 고충토로 및 조언을 듣는다!\n\n8. 상담가에 대한 만족도를 표시한다!\n\n9. 게시판에서 자유로운 얘기를 나눈다!\n\n이용 예절\n\n1. 늘비님들 간에 예의를 지켜 이용해주시길 바래요!\n\n2. 늘비님들 간에 폭언,폭설, 이유없는 비난등은 절대 안돼요!\n\n3. 말에는 마음이 담겨 있답니다. 따뜻한 말로 서로를 격려해주시길 바랍니다.\n\n4. 상담사와의 약속을 지켜주세요. 노쇼를 하게 되면 상담사분의 귀중한 시간이 낭비돼요ㅠㅠ\n\n5. 사이트 이용에 문제점이 있을 경우 QnA에 남겨주세요!!! 늘비님들 항상 감사합니다!',46,'2022-02-17 02:30:40.021000','2022-02-17 18:08:26.465000',_binary '\0'),(5,1,'[이벤트] 기프티콘 이벤트 실시합니다.','안녕하세요 늘비상담 여러분 :) 운영자 입니다.\n금일 17일 ~ 18일 양일간 활동이 가장 우수한 회원에게는 원하는 기프티콘 선물 이벤트가 실시될 예정이니 많은 참여 부탁드리겠습니다!!\n활동량 선정 기준은 고해성사 및 고민상담에 참여한 내역으로 판단합니다.',46,'2022-02-17 02:34:38.473000',NULL,_binary '\0');
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-18 11:19:56
