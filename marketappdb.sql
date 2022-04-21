-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: marketapp
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill` (
  `billID` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `branchID` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cusID` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `paymentState` tinyint DEFAULT '0',
  `percentDiscount` double DEFAULT '0',
  `totalPrice` double DEFAULT '0',
  `createdDate` datetime DEFAULT NULL,
  PRIMARY KEY (`billID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES ('2702c54c-af7a-4464-a552-441b2ec149d2','115dd543-06f0-417e-941a-1ec75fde5f64','',1,0,0,NULL),('726469b3-4441-4f92-af3c-bf60244378c4','dd36ef61-c077-4649-8d41-4a323681074f','',1,0,0,NULL),('f4e7968b-5b87-4ca2-96d9-17bb7708b561','dd36ef61-c077-4649-8d41-4a323681074f','',1,0,0,NULL);
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `branch`
--

DROP TABLE IF EXISTS `branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branch` (
  `branchID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`branchID`),
  UNIQUE KEY `banchID_UNIQUE` (`branchID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch`
--

LOCK TABLES `branch` WRITE;
/*!40000 ALTER TABLE `branch` DISABLE KEYS */;
INSERT INTO `branch` VALUES ('115dd543-06f0-417e-941a-1ec75fde5f64','Cửa hàng 371 Nguyễn Kiệm, Gò Vấp thành phố Hồ Chí Minh, Việt Nam'),('480c2e87-73f7-489f-ba28-3996b760b422','Cửa hàng 97 Võ Văn Tần, Quận 3 thành phố Hồ Chí Minh, Việt Nam');
/*!40000 ALTER TABLE `branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `cusID` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gender` int DEFAULT NULL,
  `phone` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dob` date NOT NULL,
  PRIMARY KEY (`cusID`),
  KEY `fk_gen_idx` (`gender`),
  CONSTRAINT `fk_gen` FOREIGN KEY (`gender`) REFERENCES `gender` (`genderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('6cb718fd-e3be-4691-937e-a35141cd9d24','Nguyen Thu',1,'0123456789','2022-04-06'),('a27a1a63-456d-49cb-a34e-88f1d11e6f74','Huỳnh',1,'0123456787','2021-02-15');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gender`
--

DROP TABLE IF EXISTS `gender`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gender` (
  `genderID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`genderID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gender`
--

LOCK TABLES `gender` WRITE;
/*!40000 ALTER TABLE `gender` DISABLE KEYS */;
INSERT INTO `gender` VALUES (1,'Nam'),(2,'Nữ'),(3,'Không muốn trả lời');
/*!40000 ALTER TABLE `gender` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `orderID` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `quantity` double DEFAULT '0',
  `billID` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `productID` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`orderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES ('1acaeb5b-67bb-448e-8bf7-d4bcba05c1f4',5,'2702c54c-af7a-4464-a552-441b2ec149d2','1dfd7ce7-4c4d-467b-9824-478f805004f2'),('3478eca5-7ce8-40a3-a857-c541cf00f12f',69,'f4e7968b-5b87-4ca2-96d9-17bb7708b561','1689662d-cad8-43c5-8c0a-34562fa36475'),('383278d0-fd4a-41d5-bbd7-9cf7d65c5f78',69,'726469b3-4441-4f92-af3c-bf60244378c4','u55a1a63-456d-49cb-a34e-88f1d11e6f74');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `productID` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `productOrigin` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `unitPrice` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`productID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES ('1689662d-cad8-43c5-8c0a-34562fa36475','Mì Hảo Hảo (Gói)','Việt Nam',4500),('1dfd7ce7-4c4d-467b-9824-478f805004f2','Bình nước','Việt Nam',500000),('2a02ee39-2aff-4ee4-8dc3-e915287e1f19','Rau cải (KG)','Tiền Giang - Việt Nam',25000),('329f4877-2935-4988-8973-9f38c90d7685','Cà rốt (KG)','Đà Lạt - Việt Nam',40000),('5679662d-cad8-43c5-8c0a-34562fa36475','Mì Omachi (Hộp)','USA',17000),('609163cb-0f6b-44e1-9ab8-073594a47409','Trà sữa (Chai)','Tàu khựa',15000),('68bbde7b-ac38-4d66-a0b7-6cc66f2a4f70','Gạo (Bao)','Việt Nam',40000),('7a2c95d1-9763-4908-b1ba-cd031d0cce43','Thịt heo (KG)','Lào',80000),('810db9ec-9dc6-4a6f-aa96-11ae684ec475','Hồng trà (Hộp)','Thái Nguyên - Việt Nam',20000),('839c54c8-784b-464f-8963-c7be78f92f0f','Gạo (Túi 5kg)','Việt Nam',50000),('9476b9ce-8dcf-4816-8a33-109762135cbe','Cá hồi (KG)','Nhật',160000),('9b0a0b4e-d5a5-4a8f-b8b4-352db12dcf93','Mì Omachi (Gói)','Việt Nam',7000),('9fd314ef-d7bd-40e1-8f2a-92f7c99216ca','Cá diêu hồng (KG)','Việt Nam',50000),('a27a1a63-456d-49cb-a34e-88f1d11e6f74','Cocacola (Lon)','USA',8000),('b47a1a63-456d-49cb-a34e-88f1d11e6f74','7UP (Lon)','USA',7000),('b877c168-4c90-4028-80bc-cb54526e6f9e','Cá ngừ đại dương (100gr)','Nhật',500000),('c62fcb12-db10-41bc-a541-e00d1e4ce227','Mì Hảo Hảo (Thùng)','Việt Nam',140000),('cg7a1a63-456d-49cb-a34e-88f1d11e6f74','Thịt Heo (KG)','Thái Lan',60000),('d93a1a63-456d-49cb-a34e-88f1d11e6f74','Thịt Bò (KG)','Úc',120000),('f4a6b88b-d395-4c03-a056-031219dcc08b','Bình giữ nhiệt','Việt Nam',50000),('g25a1a63-456d-49cb-a34e-88f1d11e6f74','Trà Olong (chai)','Úc',5000),('j25a1a63-456d-49cb-a34e-88f1d11e6f74','Thuốc chuột (can 10 lít))','Việt Nam',15000),('t65a1a63-456d-49cb-a34e-88f1d11e6f74','Nước rửa chén Sunlight (Chai 750ml)','Việt Nam',24000),('u55a1a63-456d-49cb-a34e-88f1d11e6f74','Bánh Quy (Hộp)','Việt Nam',25000);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_branch`
--

DROP TABLE IF EXISTS `product_branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_branch` (
  `productID` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `branchID` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`productID`,`branchID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_branch`
--

LOCK TABLES `product_branch` WRITE;
/*!40000 ALTER TABLE `product_branch` DISABLE KEYS */;
INSERT INTO `product_branch` VALUES ('1689662d-cad8-43c5-8c0a-34562fa36475','115dd543-06f0-417e-941a-1ec75fde5f64'),('1dfd7ce7-4c4d-467b-9824-478f805004f2','115dd543-06f0-417e-941a-1ec75fde5f64'),('2a02ee39-2aff-4ee4-8dc3-e915287e1f19','115dd543-06f0-417e-941a-1ec75fde5f64'),('329f4877-2935-4988-8973-9f38c90d7685','115dd543-06f0-417e-941a-1ec75fde5f64'),('5679662d-cad8-43c5-8c0a-34562fa36475','115dd543-06f0-417e-941a-1ec75fde5f64'),('5679662d-cad8-43c5-8c0a-34562fa36475','480c2e87-73f7-489f-ba28-3996b760b422'),('609163cb-0f6b-44e1-9ab8-073594a47409','115dd543-06f0-417e-941a-1ec75fde5f64'),('609163cb-0f6b-44e1-9ab8-073594a47409','480c2e87-73f7-489f-ba28-3996b760b422'),('68bbde7b-ac38-4d66-a0b7-6cc66f2a4f70','115dd543-06f0-417e-941a-1ec75fde5f64'),('7a2c95d1-9763-4908-b1ba-cd031d0cce43','115dd543-06f0-417e-941a-1ec75fde5f64'),('7a2c95d1-9763-4908-b1ba-cd031d0cce43','480c2e87-73f7-489f-ba28-3996b760b422'),('810db9ec-9dc6-4a6f-aa96-11ae684ec475','115dd543-06f0-417e-941a-1ec75fde5f64'),('839c54c8-784b-464f-8963-c7be78f92f0f','115dd543-06f0-417e-941a-1ec75fde5f64'),('9476b9ce-8dcf-4816-8a33-109762135cbe','480c2e87-73f7-489f-ba28-3996b760b422'),('9b0a0b4e-d5a5-4a8f-b8b4-352db12dcf93','115dd543-06f0-417e-941a-1ec75fde5f64'),('9fd314ef-d7bd-40e1-8f2a-92f7c99216ca','115dd543-06f0-417e-941a-1ec75fde5f64'),('a27a1a63-456d-49cb-a34e-88f1d11e6f74','115dd543-06f0-417e-941a-1ec75fde5f64'),('a27a1a63-456d-49cb-a34e-88f1d11e6f74','480c2e87-73f7-489f-ba28-3996b760b422'),('b47a1a63-456d-49cb-a34e-88f1d11e6f74','115dd543-06f0-417e-941a-1ec75fde5f64'),('b47a1a63-456d-49cb-a34e-88f1d11e6f74','480c2e87-73f7-489f-ba28-3996b760b422'),('b877c168-4c90-4028-80bc-cb54526e6f9e','115dd543-06f0-417e-941a-1ec75fde5f64'),('b877c168-4c90-4028-80bc-cb54526e6f9e','480c2e87-73f7-489f-ba28-3996b760b422'),('c62fcb12-db10-41bc-a541-e00d1e4ce227','115dd543-06f0-417e-941a-1ec75fde5f64'),('cg7a1a63-456d-49cb-a34e-88f1d11e6f74','115dd543-06f0-417e-941a-1ec75fde5f64'),('cg7a1a63-456d-49cb-a34e-88f1d11e6f74','480c2e87-73f7-489f-ba28-3996b760b422'),('d93a1a63-456d-49cb-a34e-88f1d11e6f74','115dd543-06f0-417e-941a-1ec75fde5f64'),('d93a1a63-456d-49cb-a34e-88f1d11e6f74','480c2e87-73f7-489f-ba28-3996b760b422'),('f4a6b88b-d395-4c03-a056-031219dcc08b','115dd543-06f0-417e-941a-1ec75fde5f64'),('g25a1a63-456d-49cb-a34e-88f1d11e6f74','115dd543-06f0-417e-941a-1ec75fde5f64'),('g25a1a63-456d-49cb-a34e-88f1d11e6f74','480c2e87-73f7-489f-ba28-3996b760b422'),('j25a1a63-456d-49cb-a34e-88f1d11e6f74','115dd543-06f0-417e-941a-1ec75fde5f64'),('t65a1a63-456d-49cb-a34e-88f1d11e6f74','115dd543-06f0-417e-941a-1ec75fde5f64'),('u55a1a63-456d-49cb-a34e-88f1d11e6f74','115dd543-06f0-417e-941a-1ec75fde5f64');
/*!40000 ALTER TABLE `product_branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promotion`
--

DROP TABLE IF EXISTS `promotion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotion` (
  `promoID` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `productID` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `pecentDiscount` double DEFAULT '0',
  PRIMARY KEY (`promoID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotion`
--

LOCK TABLES `promotion` WRITE;
/*!40000 ALTER TABLE `promotion` DISABLE KEYS */;
INSERT INTO `promotion` VALUES ('2d6e2697-8b05-4161-8ba7-76482d1c6ea9','1dfd7ce7-4c4d-467b-9824-478f805004f2','2022-05-02','2022-05-06',0.25),('45e7f67e-3caf-4115-a082-f49da11d2085','1dfd7ce7-4c4d-467b-9824-478f805004f2','2022-05-07','2022-05-15',0.25),('5c5fcdc7-c5c5-4ccc-8756-d33c5542df75','1dfd7ce7-4c4d-467b-9824-478f805004f2','2022-04-15','2022-04-22',0.25),('d76e225e-bfdd-48b5-94a4-67e29accd01c','1dfd7ce7-4c4d-467b-9824-478f805004f2','2022-04-22','2022-04-30',0.25);
/*!40000 ALTER TABLE `promotion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `roleID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`roleID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Admin'),(2,'Regular');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
  `staffID` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `gender` int NOT NULL,
  `name` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `homeTown` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `userName` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `passWord` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` int NOT NULL,
  `branchID` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`staffID`),
  KEY `fk_role_idx` (`role`),
  KEY `fk_gender_idx` (`gender`),
  CONSTRAINT `fk_gender` FOREIGN KEY (`gender`) REFERENCES `gender` (`genderID`),
  CONSTRAINT `fk_role` FOREIGN KEY (`role`) REFERENCES `role` (`roleID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES ('10gc95d1-9763-4908-b1ba-cd031d0cce43',1,'Nguyễn Văn I','Canada','I@mail.com','0123456789','i','123',2,'480c2e87-73f7-489f-ba28-3996b760b422'),('12cfee9d-95f2-4336-8617-53578b202376',3,'Phạm Văn C','Đồng Tháp','C@mail.com','0123456789','cc','123',2,'480c2e87-73f7-489f-ba28-3996b760b422'),('37gc95d1-9763-4908-b1ba-cd031d0cce43',3,'Phạm Văn C','Đồng Tháp','C@mail.com','0123456789','c','123',2,'480c2e87-73f7-489f-ba28-3996b760b422'),('56gc95d1-9763-4908-b1ba-cd031d0cce43',1,'Phạm E','Hà Nội','E@mail.com','0123456789','e','123',2,'115dd543-06f0-417e-941a-1ec75fde5f64'),('5ea27bcf-b990-446e-a786-f0304469987f',3,'Phạm Văn X','Đồng Tháp','X@mail.com','0123456789','x','123',2,'480c2e87-73f7-489f-ba28-3996b760b422'),('65gc95d1-9763-4908-b1ba-cd031d0cce43',2,'Phạm Thị Duyên','Vũng Tàu','D@mail.com','0123456789','duyen','123',2,'115dd543-06f0-417e-941a-1ec75fde5f64'),('7931af0b-1350-4b44-b3c9-f9b611f59e0b',3,'Phạm Văn K','Đông Timo','K@mail.com','0123456789','K','123',2,'115dd543-06f0-417e-941a-1ec75fde5f64'),('7a2c95d1-9763-4908-b1ba-cd031d0cce43',1,'Nguyễn Huỳnh','HCM','huỳnh@mail.com','.0987654321','huynh','123',2,'115dd543-06f0-417e-941a-1ec75fde5f64'),('8e1440bf-5c74-4a2e-b23c-3d6d750c0789',2,'Nguyễn Thi Y','Gia Lai','Y@mail.com','0123456789','Y','123',2,'115dd543-06f0-417e-941a-1ec75fde5f64'),('9a1e8a20-fc70-42ae-8c9e-fea5a675df73',1,'Phạm Z','Campuchia','Z@mail.com','0123456789','Z','123',2,'480c2e87-73f7-489f-ba28-3996b760b422'),('a27a1a63-456d-49cb-a34e-88f1d11e6f74',1,'Nguyễn Văn Thu','Khánh Hòa','thunguyenvan2512@gmail.com','0123456789','admin','123',1,'480c2e87-73f7-489f-ba28-3996b760b422'),('fb497680-41c2-4404-82e4-c93053ccb7ac',3,'Phạm Văn C','Đồng Tháp','C@mail.com','0123456789','ccc','123',2,'115dd543-06f0-417e-941a-1ec75fde5f64'),('hfgc95d1-9763-4908-b1ba-cd031d0cce43',3,'Nguyễn F','Huế','F@mail.com','0123456789','f','123',2,'115dd543-06f0-417e-941a-1ec75fde5f64'),('j5ga1a63-456d-49cb-a34e-88f1d11e6f74',2,'Nguyễn Thị B','Đồng Nai','bb@mail.com','0123456789','b','123',2,'480c2e87-73f7-489f-ba28-3996b760b422'),('jtlc95d1-9763-4908-b1ba-cd031d0cce43',1,'Zidane','Campuchia','G@mail.com','0123456789','zidane','123',2,'115dd543-06f0-417e-941a-1ec75fde5f64'),('osgc95d1-9763-4908-b1ba-cd031d0cce43',1,'Christiano Ronaldo','Lào','J@mail.com','0123456789','ronaldo','123',2,'480c2e87-73f7-489f-ba28-3996b760b422'),('reyc95d1-9763-4908-b1ba-cd031d0cce43',2,'Phạm Thị H','Mỹ','H@mail.com','0123456789','h','123',2,'480c2e87-73f7-489f-ba28-3996b760b422');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-21 15:54:30
