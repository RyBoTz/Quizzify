CREATE DATABASE  IF NOT EXISTS `quizzifydb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `quizzifydb`;
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: quizzifydb
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `course_id` int NOT NULL AUTO_INCREMENT,
  `course_name` varchar(255) NOT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`course_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `courses_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (15,'Java Programming',4),(16,'Python Basics',4),(17,'Web Development',4),(18,'General Education',4);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `question_id` int NOT NULL AUTO_INCREMENT,
  `quiz_id` int DEFAULT NULL,
  `question_text` text NOT NULL,
  `option_a` varchar(255) NOT NULL,
  `option_b` varchar(255) NOT NULL,
  `option_c` varchar(255) NOT NULL,
  `option_d` varchar(255) NOT NULL,
  `correct_answer` char(1) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`question_id`),
  KEY `quiz_id` (`quiz_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `questions_ibfk_1` FOREIGN KEY (`quiz_id`) REFERENCES `quizzes` (`quiz_id`),
  CONSTRAINT `questions_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `questions_chk_1` CHECK ((`correct_answer` in (_utf8mb4'A',_utf8mb4'B',_utf8mb4'C',_utf8mb4'D')))
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (3,9,'What is the output of the following code snippet?  System.out.println(5 + 3 * 2);','16','11','10','21','B',4),(4,9,'System.out.println(\"Hello\" + \"World\");','HelloWorld','Hello World','\"HelloWorld\"','\"Hello\" + \"World\"','A',4),(5,9,'What is the purpose of the main method in a Java program?','It is the entry point of the program.','maybe','yes','phyton is the best!','A',4),(6,9,'Which keyword is used to create a new object in Java?','new','news','create','createObj','A',4),(7,9,'Which operator is used to compare two values for equality?','==','=','===','=+==','A',4),(12,13,'What is the capital of France?','Berlin','Madrid','Paris (Correct Answer)','Rome','C',4),(13,13,'What is 2 + 2?','3','4 (Correct Answer)','5','6','B',4),(14,13,'Which animal is known as \"Man\'s Best Friend\"?','Dog (Correct Answer)','Cat','Parrot','Goldfish','A',4),(15,13,'Which color is made by mixing blue and yellow?','Red','Orange','Purple','Green (Correct Answer)','D',4),(16,13,'What planet is known as the \"Red Planet\"?','Jupiter','Earth','Mars (Correct Answer)','Earth','C',4),(17,13,'Papasa po ba kami?','yes','sinko','depende','wheel of grades','A',4),(20,12,'What does HTML stand for?','Hyperlinks and Text Markup Language ','Hyper Text Markup Language ✅','Home Tool Markup Language ','Hyper Transfer Markup Language','D',NULL),(21,12,'Which is the correct HTML tag for the largest heading?',' <heading>','<h6>','<h1> ✅','<head>','C',NULL),(22,12,'How can you create a hyperlink in HTML?','<link href=\"url\">','<a>url</a>','<a href=\"url\">Link Text</a> ✅','<url href=\"link\">Link Text</url>','C',NULL),(23,12,'Which tag is used to display an image in HTML?','<pic>','<image>','<src>','<img> ✅','D',NULL),(24,12,'What is the correct way to add a background color in HTML?','<body style=\"background-color: blue;\"> ✅',' <background=\"blue\">','<body bg=\"blue\">',' <body style=\"color: blue;\">','A',NULL),(25,12,' Which HTML tag is used to define an unordered list?','<ol>','<ul> ✅','<list>','<u-list>  ','B',NULL),(26,12,'What is the purpose of the <title> tag in HTML?','To define the title of the webpage shown in the browser tab ✅','To add a caption to an image','To style the text of the page','To create a heading on the page','A',NULL),(27,12,'How do you create a line break in HTML?','<br /> ✅','<break>','<lb>','<newline>','A',NULL),(33,16,'what is html','html123','ht ml','htm','hypertext markup language','D',10),(34,16,'what is html','html','thml','hypertext markup language','ht ml1','C',10),(35,17,'What is the purpose of the main method in a Java program?','It is the entry point of the program.','maybe','yes','phyton is the best!','A',NULL),(36,17,'What is the correct way to add a background color in HTML?','<body style=\"background-color: blue;\"> ✅',' <background=\"blue\">','<body bg=\"blue\">',' <body style=\"color: blue;\">','A',NULL),(37,17,'How do you create a line break in HTML?','<br /> ✅','<break>','<lb>','<newline>','A',NULL),(38,17,'What is the purpose of the <title> tag in HTML?','To define the title of the webpage shown in the browser tab ✅','To add a caption to an image','To style the text of the page','To create a heading on the page','A',NULL);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_results`
--

DROP TABLE IF EXISTS `quiz_results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz_results` (
  `result_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `quiz_id` int NOT NULL,
  `total_questions` int NOT NULL,
  `correct_answers` int NOT NULL,
  `incorrect_answers` int NOT NULL,
  `score` double NOT NULL,
  `percentage` double NOT NULL,
  `attempt_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `time_taken` int NOT NULL,
  PRIMARY KEY (`result_id`),
  KEY `user_id` (`user_id`),
  KEY `quiz_id` (`quiz_id`),
  CONSTRAINT `quiz_results_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `quiz_results_ibfk_2` FOREIGN KEY (`quiz_id`) REFERENCES `quizzes` (`quiz_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_results`
--

LOCK TABLES `quiz_results` WRITE;
/*!40000 ALTER TABLE `quiz_results` DISABLE KEYS */;
INSERT INTO `quiz_results` VALUES (26,5,9,5,2,3,40,40,'2024-12-01 19:59:49',14),(27,5,9,5,2,3,0,0,'2024-12-01 20:22:16',2),(28,5,12,1,2,3,0,0,'2024-12-01 20:28:13',1),(29,5,9,5,2,3,0,0,'2024-12-01 20:59:16',8),(30,5,13,6,5,1,83.33333333333333,83.33333333333333,'2024-12-02 16:34:03',15),(31,5,13,6,5,1,83.33333333333333,83.33333333333333,'2024-12-03 08:56:17',17),(33,5,9,5,5,0,100,100,'2024-12-05 14:13:34',14),(34,5,9,5,6,-1,120,120,'2024-12-05 14:49:11',19),(35,5,9,5,5,0,100,100,'2024-12-05 22:57:49',42),(36,9,9,5,4,1,80,80,'2024-12-06 10:44:13',33),(37,9,17,4,2,2,50,50,'2024-12-06 10:56:30',26);
/*!40000 ALTER TABLE `quiz_results` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quizzes`
--

DROP TABLE IF EXISTS `quizzes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quizzes` (
  `quiz_id` int NOT NULL AUTO_INCREMENT,
  `quiz_name` varchar(255) NOT NULL,
  `course_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `quiz_description` text,
  PRIMARY KEY (`quiz_id`),
  KEY `course_id` (`course_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `quizzes_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`),
  CONSTRAINT `quizzes_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quizzes`
--

LOCK TABLES `quizzes` WRITE;
/*!40000 ALTER TABLE `quizzes` DISABLE KEYS */;
INSERT INTO `quizzes` VALUES (9,'Programming 1',15,4,'this is a sample description for this quiz.'),(12,'HTML',17,4,'This a sample HTML instruction.'),(13,'The more you know',18,4,'Sample quiz description for the more you know.'),(16,'quiz1',17,10,'answer the following questions'),(17,'Lawrenze',16,NULL,NULL);
/*!40000 ALTER TABLE `quizzes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `number` varchar(15) NOT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `role` enum('admin','user') NOT NULL DEFAULT 'user',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (4,'admin','admin','admin@example.com','1234567890',NULL,'admin'),(5,'ryan','ryan','ryan@example.com','0987654321',NULL,'user'),(7,'sam','sam','sam@gmail.com','987654321',NULL,'user'),(8,'admin2','admin2','aadmin2@gmail.com','192837465',NULL,'admin'),(9,'admin3','pangitako','jee123@gmail.com','09212828145',NULL,'user'),(10,'admin5','123456','zee@gmail.com','09212828145',NULL,'admin');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-28 20:11:17
