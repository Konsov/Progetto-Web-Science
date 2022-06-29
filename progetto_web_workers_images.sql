-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: progetto_web
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `workers_images`
--

DROP TABLE IF EXISTS `workers_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workers_images` (
  `idworkers_images` int(11) NOT NULL AUTO_INCREMENT,
  `name_image` varchar(45) NOT NULL,
  `worker` varchar(45) NOT NULL,
  `selected` int(11) NOT NULL,
  `campaign` varchar(45) NOT NULL,
  `annotated` int(11) NOT NULL,
  PRIMARY KEY (`idworkers_images`)
) ENGINE=InnoDB AUTO_INCREMENT=2330 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workers_images`
--

LOCK TABLES `workers_images` WRITE;
/*!40000 ALTER TABLE `workers_images` DISABLE KEYS */;
INSERT INTO `workers_images` VALUES (2320,'Cervino_Gestore3_-2106922094.png','Lavoratore1',1,'Cervino',1),(2321,'Cervino_Gestore3_-2106922094.png','Lavoratore2',1,'Cervino',1),(2324,'Cervino_Gestore3_-577929422.png','Lavoratore1',1,'Cervino',1),(2325,'Cervino_Gestore3_-577929422.png','Lavoratore2',1,'Cervino',1),(2328,'Cervino_Gestore3_234077668.png','Lavoratore1',2,'Cervino',0),(2329,'Cervino_Gestore3_234077668.png','Lavoratore2',1,'Cervino',0);
/*!40000 ALTER TABLE `workers_images` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-09-17 16:00:11
