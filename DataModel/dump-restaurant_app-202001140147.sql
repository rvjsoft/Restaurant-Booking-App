-- MySQL dump 10.13  Distrib 5.7.28, for Linux (x86_64)
--
-- Host: localhost    Database: restaurant_app
-- ------------------------------------------------------
-- Server version	5.7.28-0ubuntu0.19.04.2

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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `user_id` int(5) NOT NULL,
  `address1` varchar(100) NOT NULL,
  `address2` varchar(100) DEFAULT NULL,
  `landmark` varchar(20) NOT NULL,
  `city` varchar(20) NOT NULL,
  `state` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `address_to_customer` (`user_id`),
  CONSTRAINT `address_to_customer` FOREIGN KEY (`user_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` int(5) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `phone` int(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`,`phone`),
  UNIQUE KEY `email_2` (`email`),
  UNIQUE KEY `phone` (`phone`),
  CONSTRAINT `customer_login` FOREIGN KEY (`id`) REFERENCES `user_login` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (5,'firstname','lastname','emailid@domain.com',1234567890),(10,'firstname','lastname','emailifd@domain.com',12345678);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `food`
--

DROP TABLE IF EXISTS `food`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `food` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `res_id` int(5) NOT NULL,
  `name` varchar(20) NOT NULL,
  `price` float(4,2) NOT NULL,
  `type` char(1) NOT NULL,
  `status` char(1) NOT NULL,
  `category` varchar(10) NOT NULL,
  `image_id` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `food_to_restaurant` (`res_id`),
  CONSTRAINT `food_to_restaurant` FOREIGN KEY (`res_id`) REFERENCES `restaurant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food`
--

LOCK TABLES `food` WRITE;
/*!40000 ALTER TABLE `food` DISABLE KEYS */;
INSERT INTO `food` VALUES (1,13,'idly',10.00,'V','A','DOSA_IDLY',NULL),(2,13,'South Meals',70.00,'V','A','RICE',NULL),(3,13,'vada',15.00,'V','A','DOSA_IDLY','someId'),(4,13,'idly',10.00,'V','U','DOSA_IDLY','image id data'),(5,13,'South Meals',70.00,'V','U','RICE',NULL);
/*!40000 ALTER TABLE `food` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `images` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `image_id` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `images`
--

LOCK TABLES `images` WRITE;
/*!40000 ALTER TABLE `images` DISABLE KEYS */;
INSERT INTO `images` VALUES (1,'resta17ViafrrJ'),(2,'resta1bpCc7b+n'),(3,'resta1IlWrU5f5'),(4,'resta1mqlU0itY'),(5,'resta1p90LXnpG'),(6,'resta1hLfkT7Wo'),(7,'resta1fUrNk6ip');
/*!40000 ALTER TABLE `images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_items` (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `order_id` int(10) NOT NULL,
  `food_id` int(6) NOT NULL,
  `quantity` int(2) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `orderitem_to_order` (`order_id`),
  KEY `orderitem_to_food` (`food_id`),
  CONSTRAINT `orderitem_to_food` FOREIGN KEY (`food_id`) REFERENCES `food` (`id`),
  CONSTRAINT `orderitem_to_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (1,2,3,10),(2,3,3,10),(3,4,1,1),(4,4,2,1),(5,4,3,10),(6,5,1,1),(7,5,2,1),(8,5,3,10),(9,6,1,1),(10,6,2,1),(11,6,3,10),(12,7,1,3),(13,7,2,3),(14,7,3,3),(15,8,1,3),(16,8,2,3),(17,8,3,3),(18,9,3,3),(19,10,3,3),(20,11,3,1);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `order_id` int(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(5) NOT NULL,
  `ordered_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` char(1) NOT NULL,
  `res_id` int(5) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `order_to_restaurant` (`res_id`),
  KEY `order_to_customer` (`user_id`),
  CONSTRAINT `order_to_customer` FOREIGN KEY (`user_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `order_to_restaurant` FOREIGN KEY (`res_id`) REFERENCES `restaurant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (2,5,'2020-01-02 18:30:00','A',13),(3,5,'2020-01-02 19:52:34','O',13),(4,5,'2020-01-02 19:55:19','O',13),(5,5,'2020-01-05 05:40:31','O',13),(6,5,'2020-01-05 05:40:31','O',13),(7,5,'2020-01-05 05:42:00','O',13),(8,5,'2020-01-05 05:42:00','O',13),(9,5,'2020-01-05 05:42:58','O',13),(10,5,'2020-01-05 05:42:58','O',13),(11,5,'2020-01-05 05:47:08','O',13);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `res_table`
--

DROP TABLE IF EXISTS `res_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `res_table` (
  `id` int(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `res_table`
--

LOCK TABLES `res_table` WRITE;
/*!40000 ALTER TABLE `res_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `res_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant`
--

DROP TABLE IF EXISTS `restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `restaurant` (
  `id` int(5) NOT NULL,
  `name` varchar(20) NOT NULL,
  `type` char(1) NOT NULL,
  `address1` varchar(100) NOT NULL,
  `address2` varchar(100) DEFAULT NULL,
  `landmark` varchar(20) NOT NULL,
  `city` varchar(20) NOT NULL,
  `state` varchar(20) NOT NULL,
  `status` char(1) NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `base_tables` int(3) DEFAULT '0',
  `image_id` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `phone` (`phone`),
  CONSTRAINT `restaurant_login` FOREIGN KEY (`id`) REFERENCES `user_login` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant`
--

LOCK TABLES `restaurant` WRITE;
/*!40000 ALTER TABLE `restaurant` DISABLE KEYS */;
INSERT INTO `restaurant` VALUES (13,'some name','V','sadfasfsadf','asdfwwfsdf','sfsfasf','sdfasf','asdfasdf','A','hoho@gmail.com','4634523',0,NULL),(15,'some name','V','sadfasfsadf','asdfwwfsdf','sfsfasf','sdfasf','asdfasdf','U','hohod@gmail.com','463434523',0,NULL),(16,'test restaurant 1','V','sadfasfsadf','asdfwwfsdf','sfsfasf','sdfasf','asdfasdf','U','test1@gmail.com','398752342',NULL,NULL),(17,'test restaurant 2','V','sadfasfsadf','asdfwwfsdf','sfsfasf','sdfasf','asdfasdf','U','test2@gmail.com','3987523642',20,NULL),(18,'test restaurant 3','V','sadfasfsadf','asdfwwfsdf','sfsfasf','sdfasf','asdfasdf','U','test3@gmail.com','23642',20,NULL),(19,'test restaurant 3','V','sadfasfsadf','asdfwwfsdf','sfsfasf','sdfasf','asdfasdf','U','testy@gmail.com','236432',123,NULL),(21,'test restaurant 3','V','sadfasfsadf','asdfwwfsdf','sfsfasf','sdfasf','asdfasdf','U','testys@gmail.com','2364232',123,NULL),(22,'test restaurant 3','V','sadfasfsadf','asdfwwfsdf','sfsfasf','sdfasf','asdfasdf','U','testdys@gmail.com','23634232',123,'someId'),(23,'test restaurant 3','V','sadfasfsadf','asdfwwfsdf','sfsfasf','sdfasf','asdfasdf','U','testdysds@gmail.com','236343232',123,'someId');
/*!40000 ALTER TABLE `restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_availability`
--

DROP TABLE IF EXISTS `table_availability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `table_availability` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `res_id` int(5) NOT NULL,
  `booking_date` date NOT NULL,
  `total` int(3) NOT NULL,
  `booked` int(3) NOT NULL DEFAULT '0',
  `part` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `res_id` (`res_id`,`booking_date`,`part`),
  CONSTRAINT `available_of_restaurant` FOREIGN KEY (`res_id`) REFERENCES `restaurant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_availability`
--

LOCK TABLES `table_availability` WRITE;
/*!40000 ALTER TABLE `table_availability` DISABLE KEYS */;
INSERT INTO `table_availability` VALUES (5,13,'2020-01-05',0,0,'B'),(6,13,'2020-01-05',50,30,'L'),(7,13,'2020-01-05',10,10,'D'),(8,18,'2020-01-06',20,0,'B'),(9,18,'2020-01-06',20,6,'L'),(10,18,'2020-01-06',20,2,'D'),(11,13,'2020-01-12',0,0,'B'),(12,13,'2020-01-12',0,0,'L'),(13,13,'2020-01-12',0,2,'D'),(14,13,'2020-01-17',0,1,'B'),(15,13,'2020-01-17',0,0,'L'),(16,13,'2020-01-17',0,0,'D');
/*!40000 ALTER TABLE `table_availability` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_bookings`
--

DROP TABLE IF EXISTS `table_bookings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `table_bookings` (
  `booking_id` int(15) NOT NULL AUTO_INCREMENT,
  `tables_id` int(6) NOT NULL,
  `count` int(3) NOT NULL,
  `time` char(1) NOT NULL,
  `booking_date` date NOT NULL,
  `user_id` int(5) NOT NULL,
  PRIMARY KEY (`booking_id`),
  KEY `booking_to_customer` (`user_id`),
  KEY `booking_to_restaurant` (`tables_id`),
  CONSTRAINT `booking_to_customer` FOREIGN KEY (`user_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `booking_to_restaurant` FOREIGN KEY (`tables_id`) REFERENCES `restaurant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_bookings`
--

LOCK TABLES `table_bookings` WRITE;
/*!40000 ALTER TABLE `table_bookings` DISABLE KEYS */;
INSERT INTO `table_bookings` VALUES (1,13,10,'L','2020-01-05',5),(2,13,10,'L','2020-01-05',5),(3,13,10,'L','2020-01-05',5),(4,13,10,'L','2020-01-05',5),(5,13,10,'L','2020-01-05',5),(6,13,10,'L','2020-01-05',5),(7,13,10,'D','2020-01-05',5),(8,13,10,'D','2020-01-05',5),(12,18,1,'L','2020-01-06',5),(13,18,1,'L','2020-01-06',5),(14,18,2,'L','2020-01-06',10),(15,18,2,'L','2020-01-06',10),(16,18,3,'L','2020-01-06',10),(17,18,3,'L','2020-01-06',10),(18,18,2,'D','2020-01-06',10),(19,13,2,'D','2020-01-12',10),(20,13,1,'B','2020-01-17',10);
/*!40000 ALTER TABLE `table_bookings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_login`
--

DROP TABLE IF EXISTS `user_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_login` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `usr` varchar(20) NOT NULL,
  `pwd` varchar(20) NOT NULL,
  `usr_level` char(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `usr` (`usr`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_login`
--

LOCK TABLES `user_login` WRITE;
/*!40000 ALTER TABLE `user_login` DISABLE KEYS */;
INSERT INTO `user_login` VALUES (4,'admin','NoPassword','C'),(5,'noname','noname','C'),(10,'noname3','noname','C'),(13,'resta1','noname','R'),(15,'restaf1','noname123','R'),(16,'test1','noname123','R'),(17,'test2','noname123','R'),(18,'test3','noname123','R'),(19,'testdf','noname123','R'),(21,'testfdf','noname123','R'),(22,'tesftfdf','noname123','R'),(23,'testuser','noname123','R');
/*!40000 ALTER TABLE `user_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'restaurant_app'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-14  1:47:33
