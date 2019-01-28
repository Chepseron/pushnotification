-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.39-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema pushnotification
--

CREATE DATABASE IF NOT EXISTS pushnotification;
USE pushnotification;

--
-- Definition of table `audit`
--

DROP TABLE IF EXISTS `audit`;
CREATE TABLE `audit` (
  `idaudit` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `timer` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createdby` int(10) unsigned NOT NULL,
  `action` varchar(200) NOT NULL,
  PRIMARY KEY (`idaudit`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `audit`
--

/*!40000 ALTER TABLE `audit` DISABLE KEYS */;
/*!40000 ALTER TABLE `audit` ENABLE KEYS */;


--
-- Definition of table `outlet`
--

DROP TABLE IF EXISTS `outlet`;
CREATE TABLE `outlet` (
  `idoutlet` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `createdBy` int(10) unsigned NOT NULL,
  `createdOn` datetime NOT NULL,
  `outletname` varchar(50) NOT NULL,
  `address` varchar(100) NOT NULL,
  PRIMARY KEY (`idoutlet`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `outlet`
--

/*!40000 ALTER TABLE `outlet` DISABLE KEYS */;
INSERT INTO `outlet` (`idoutlet`,`createdBy`,`createdOn`,`outletname`,`address`) VALUES 
 (1,1,'2019-01-11 16:19:19','ARTCAFFE EATERY','3662 NAIROBI');
/*!40000 ALTER TABLE `outlet` ENABLE KEYS */;


--
-- Definition of table `status`
--

DROP TABLE IF EXISTS `status`;
CREATE TABLE `status` (
  `idstatus` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `createdBy` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`idstatus`),
  KEY `FK_status_1` (`createdBy`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `status`
--

/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` (`idstatus`,`name`,`description`,`createdBy`) VALUES 
 (1,'Active','the user is active',NULL),
 (2,'Unsold','transaction incomplete',NULL),
 (3,'Sold','transaction has been completed',NULL);
/*!40000 ALTER TABLE `status` ENABLE KEYS */;


--
-- Definition of table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
CREATE TABLE `transactions` (
  `idtransactions` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `outletID` int(10) unsigned NOT NULL,
  `createdOn` datetime NOT NULL,
  `statusID` int(10) unsigned NOT NULL,
  `amount` int(10) unsigned NOT NULL,
  `mpesastatement` varchar(200) NOT NULL,
  PRIMARY KEY (`idtransactions`),
  KEY `FK_transactions_2` (`statusID`),
  KEY `FK_transactions_3` (`outletID`),
  CONSTRAINT `FK_transactions_2` FOREIGN KEY (`statusID`) REFERENCES `status` (`idstatus`),
  CONSTRAINT `FK_transactions_3` FOREIGN KEY (`outletID`) REFERENCES `outlet` (`idoutlet`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transactions`
--

/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;


--
-- Definition of table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `idusers` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `pword` varchar(45) DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL,
  `lastLogin` datetime DEFAULT NULL,
  `department` int(10) unsigned NOT NULL,
  `groupID` int(10) unsigned DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` int(10) unsigned NOT NULL,
  `staffID` int(10) unsigned DEFAULT NULL,
  `statusID` int(10) unsigned DEFAULT NULL,
  `createdBy` int(10) unsigned DEFAULT NULL,
  `sessionid` int(10) unsigned DEFAULT NULL,
  `outlet` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`idusers`) USING BTREE,
  KEY `FK_user_1` (`createdBy`),
  KEY `FK_user_2` (`statusID`),
  KEY `FK_user_6` (`outlet`),
  CONSTRAINT `FK_user_6` FOREIGN KEY (`outlet`) REFERENCES `outlet` (`idoutlet`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`idusers`,`username`,`pword`,`createdAt`,`lastLogin`,`department`,`groupID`,`name`,`email`,`phone`,`staffID`,`statusID`,`createdBy`,`sessionid`,`outlet`) VALUES 
 (1,'admin','1234','2019-01-17 16:03:19','2019-01-17 16:03:19',1,1,'Amon','chepseron@gmail.com',728140544,1,1,1,1,1),
 (2,'seller','1234','2019-01-17 16:04:01','2019-01-17 16:04:01',1,3,'Amon','chepseron@gmail.com',728140544,1,1,1,1,1),
 (3,'acc','1234','2019-01-17 16:04:13','2019-01-17 16:04:13',1,4,'Amon','chepseron@gmail.com',728140544,1,1,1,1,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


--
-- Definition of table `usergroup`
--

DROP TABLE IF EXISTS `usergroup`;
CREATE TABLE `usergroup` (
  `idgroups` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL,
  `createdBy` int(10) unsigned DEFAULT NULL,
  `statusID` int(10) unsigned DEFAULT NULL,
  `responsibilities` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idgroups`),
  KEY `FK_groups_1` (`statusID`),
  KEY `FK_groups_2` (`createdBy`),
  CONSTRAINT `FK_group_1` FOREIGN KEY (`createdBy`) REFERENCES `user` (`idusers`),
  CONSTRAINT `FK_group_2` FOREIGN KEY (`statusID`) REFERENCES `status` (`idstatus`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `usergroup`
--

/*!40000 ALTER TABLE `usergroup` DISABLE KEYS */;
INSERT INTO `usergroup` (`idgroups`,`name`,`createdAt`,`createdBy`,`statusID`,`responsibilities`) VALUES 
 (1,'Admin','1000-01-01 00:00:00',NULL,1,'ALL'),
 (2,'Seller','2019-01-11 16:20:01',1,1,'ALL'),
 (3,'SALES','2019-01-17 16:02:54',1,1,'ALL'),
 (4,'ACCOUNTANT','2019-01-17 16:02:59',1,1,'ALL');
/*!40000 ALTER TABLE `usergroup` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
