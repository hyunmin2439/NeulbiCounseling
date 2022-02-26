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
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `user` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category` int DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `report_user_id` bigint NOT NULL,
  `report_msg` varchar(200) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `report_list_user_fk1_idx` (`user_id`),
  CONSTRAINT `report_list_user_fk1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `report_list_user_fk2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


LOCK TABLES `report` WRITE;
UNLOCK TABLES;


DROP TABLE IF EXISTS `ban_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ban_list` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `ban_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ban_list_user_fk_idx` (`user_id`),
  CONSTRAINT `ban_list_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `ban_list_user_fk2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `ban_list` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `penalty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `penalty` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `penalty` varchar(100) DEFAULT NULL,
  `end_penalty_day` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `penalty_user_fk_idx` (`user_id`),
  CONSTRAINT `penalty_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `penalty` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `my_consultant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `my_consultant` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `consultant_id` bigint NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `my_consultant_user_fk_idx` (`user_id`),
  KEY `my_consultant_consultant_user_fk_idx` (`consultant_id`),
  CONSTRAINT `my_consultant_consultant_user_fk` FOREIGN KEY (`consultant_id`) REFERENCES `user` (`id`),
  CONSTRAINT `my_consultant_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


LOCK TABLES `my_consultant` WRITE;
UNLOCK TABLES;


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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `notice` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `community`;
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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `community` WRITE;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `comment` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `like_check`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `like_check` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `community_id` bigint NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_idx` (`user_id`),
  KEY `FKm99i80oyajp72mb4vsc84wolo` (`community_id`),
  CONSTRAINT `FKm99i80oyajp72mb4vsc84wolo` FOREIGN KEY (`community_id`) REFERENCES `community` (`id`),
  CONSTRAINT `like_check_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=199 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `like_check` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `meeting_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meeting_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


LOCK TABLES `meeting_category` WRITE;
INSERT INTO `meeting_category` VALUES (1,'confession',NULL,NULL),(2,'advice',NULL,NULL);
UNLOCK TABLES;

DROP TABLE IF EXISTS `topic_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topic_category` (
  `id` bigint NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


LOCK TABLES `topic_category` WRITE;
INSERT INTO `topic_category` VALUES (1,'학업',NULL,NULL),(2,'가정',NULL,NULL),(3,'취업',NULL,NULL),(4,'진로',NULL,NULL),(5,'연애',NULL,NULL),(6,'결혼',NULL,NULL);
UNLOCK TABLES;


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
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `meeting` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `user_meeting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_meeting` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `meeting_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6nin1u48wao16blvnqfil9t5f` (`meeting_id`),
  KEY `FKpll20e5ohols6k1iwt0snxl3n` (`user_id`),
  CONSTRAINT `FK6nin1u48wao16blvnqfil9t5f` FOREIGN KEY (`meeting_id`) REFERENCES `meeting` (`id`),
  CONSTRAINT `FKpll20e5ohols6k1iwt0snxl3n` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


LOCK TABLES `user_meeting` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `meeting_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meeting_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `action` varchar(255) DEFAULT NULL,
  `inserted_time` datetime(6) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `meeting_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK55skcckpe01o1ymy8yp0x8d6o` (`meeting_id`),
  KEY `FKmk82bhbb8nfq1fxxn1pawalhk` (`user_id`),
  CONSTRAINT `FK55skcckpe01o1ymy8yp0x8d6o` FOREIGN KEY (`meeting_id`) REFERENCES `meeting` (`id`),
  CONSTRAINT `FKmk82bhbb8nfq1fxxn1pawalhk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=540 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


LOCK TABLES `meeting_history` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `consultant_profile`;
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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `consultant_profile` WRITE;
UNLOCK TABLES;

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


LOCK TABLES `review` WRITE;
UNLOCK TABLES;


DROP TABLE IF EXISTS `chat_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_room` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `consultant_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `chat_room` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `chat_room_id` bigint DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `message` WRITE;
UNLOCK TABLES;


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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `qna` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `qna_id` bigint NOT NULL,
  `description` varchar(200) NOT NULL,
  `is_deleted` bit(1) DEFAULT b'0',
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `answer_user_fk_idx` (`user_id`),
  KEY `answer_qna_fk_idx` (`qna_id`),
  CONSTRAINT `answer_qna_fk` FOREIGN KEY (`qna_id`) REFERENCES `qna` (`id`),
  CONSTRAINT `answer_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



