-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.24-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.0.0.6468
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for reddit-clone
DROP DATABASE IF EXISTS `reddit-clone`;
CREATE DATABASE IF NOT EXISTS `reddit-clone` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `reddit-clone`;

-- Dumping structure for table reddit-clone.admin
DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_admin_user_id` (`user_id`),
  CONSTRAINT `fk_admin_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table reddit-clone.admin: ~0 rows (approximately)
DELETE FROM `admin`;
INSERT INTO `admin` (`id`, `user_id`) VALUES
	(1, 1);

-- Dumping structure for table reddit-clone.banned
DROP TABLE IF EXISTS `banned`;
CREATE TABLE IF NOT EXISTS `banned` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` date NOT NULL DEFAULT current_timestamp(),
  `by` int(11) NOT NULL DEFAULT 0,
  `user_id` int(11) NOT NULL DEFAULT 0,
  `community_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `fk_banned_by` (`by`),
  KEY `fk_banned_user_id` (`user_id`),
  KEY `fk_banned_community_id` (`community_id`),
  CONSTRAINT `fk_banned_by` FOREIGN KEY (`by`) REFERENCES `moderator` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_banned_community_id` FOREIGN KEY (`community_id`) REFERENCES `community` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_banned_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table reddit-clone.banned: ~0 rows (approximately)
DELETE FROM `banned`;

-- Dumping structure for table reddit-clone.comment
DROP TABLE IF EXISTS `comment`;
CREATE TABLE IF NOT EXISTS `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` text COLLATE utf8_unicode_ci NOT NULL,
  `timestamp` date NOT NULL DEFAULT current_timestamp(),
  `isDeleted` int(1) NOT NULL DEFAULT 0,
  `replies_to` int(11) DEFAULT 0,
  `by_id` int(11) NOT NULL DEFAULT 0,
  `post_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `fk_comment_by_id` (`by_id`),
  KEY `fk_comment_post_id` (`post_id`),
  KEY `FK_comment_replies_to` (`replies_to`),
  CONSTRAINT `FK_comment_replies_to` FOREIGN KEY (`replies_to`) REFERENCES `comment` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table reddit-clone.comment: ~0 rows (approximately)
DELETE FROM `comment`;

-- Dumping structure for table reddit-clone.community
DROP TABLE IF EXISTS `community`;
CREATE TABLE IF NOT EXISTS `community` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci NOT NULL,
  `creation_date` date NOT NULL DEFAULT current_timestamp(),
  `is_suspended` smallint(1) NOT NULL DEFAULT 0,
  `suspended_reason` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table reddit-clone.community: ~5 rows (approximately)
DELETE FROM `community`;
INSERT INTO `community` (`id`, `name`, `description`, `creation_date`, `is_suspended`, `suspended_reason`) VALUES
	(2, 'ftn56', 'test1', '2022-05-25', 0, ''),
	(3, 'ftn12', 'proba22', '2022-06-08', 0, ''),
	(4, 'NoviKom', 'Novi komuniti', '2022-06-13', 0, ''),
	(5, 'test', 'test1', '2022-06-13', 0, '');

-- Dumping structure for table reddit-clone.flair
DROP TABLE IF EXISTS `flair`;
CREATE TABLE IF NOT EXISTS `flair` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table reddit-clone.flair: ~0 rows (approximately)
DELETE FROM `flair`;

-- Dumping structure for table reddit-clone.flair/community connection
DROP TABLE IF EXISTS `flair/community connection`;
CREATE TABLE IF NOT EXISTS `flair/community connection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `community_id` int(11) NOT NULL,
  `flair_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_fc_connection_community_id` (`community_id`),
  KEY `fk_fc_connection_flair_id` (`flair_id`),
  CONSTRAINT `fk_fc_connection_community_id` FOREIGN KEY (`community_id`) REFERENCES `community` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_fc_connection_flair_id` FOREIGN KEY (`flair_id`) REFERENCES `flair` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table reddit-clone.flair/community connection: ~0 rows (approximately)
DELETE FROM `flair/community connection`;

-- Dumping structure for table reddit-clone.moderator
DROP TABLE IF EXISTS `moderator`;
CREATE TABLE IF NOT EXISTS `moderator` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT 0,
  `community_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `fk_moderator_community_id` (`community_id`),
  KEY `fk_moderator_user_id` (`user_id`),
  CONSTRAINT `fk_moderator_community_id` FOREIGN KEY (`community_id`) REFERENCES `community` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_moderator_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table reddit-clone.moderator: ~4 rows (approximately)
DELETE FROM `moderator`;
INSERT INTO `moderator` (`id`, `user_id`, `community_id`) VALUES
	(2, 4, 2),
	(3, 4, 3),
	(4, 10, 4),
	(5, 3, 5);

-- Dumping structure for table reddit-clone.post
DROP TABLE IF EXISTS `post`;
CREATE TABLE IF NOT EXISTS `post` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `text` text COLLATE utf8_unicode_ci NOT NULL,
  `creation_date` date NOT NULL DEFAULT current_timestamp(),
  `image_path` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `flair_id` int(11) DEFAULT NULL,
  `posted_by` int(11) NOT NULL,
  `community_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_post_community_id` (`community_id`),
  KEY `fk_post_posted_by` (`posted_by`),
  KEY `fk_post_flair_id` (`flair_id`),
  CONSTRAINT `fk_post_community_id` FOREIGN KEY (`community_id`) REFERENCES `community` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_post_flair_id` FOREIGN KEY (`flair_id`) REFERENCES `flair` (`id`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `fk_post_posted_by` FOREIGN KEY (`posted_by`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table reddit-clone.post: ~4 rows (approximately)
DELETE FROM `post`;
INSERT INTO `post` (`id`, `title`, `text`, `creation_date`, `image_path`, `flair_id`, `posted_by`, `community_id`) VALUES
	(1, 'test', 'test1', '2022-05-25', '', NULL, 1, 2),
	(2, 'test', 'test1', '2022-05-25', '', NULL, 1, 2),
	(4, 'test33', 'test1', '2022-06-07', '', NULL, 1, 2),
	(5, 'test34', 'test1', '2022-06-08', '', NULL, 1, 3);

-- Dumping structure for table reddit-clone.reaction
DROP TABLE IF EXISTS `reaction`;
CREATE TABLE IF NOT EXISTS `reaction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL DEFAULT 0,
  `timestamp` date NOT NULL DEFAULT current_timestamp(),
  `reaction_by` int(11) NOT NULL DEFAULT 0,
  `reaction_to` int(11) NOT NULL DEFAULT 0,
  `reaction_to_post_id` int(11) DEFAULT 0,
  `reaction_to_comment_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_reaction_reaction_by` (`reaction_by`),
  KEY `fk_reaction_to_post_id` (`reaction_to_post_id`) USING BTREE,
  KEY `fk_reaction_to_comment_id` (`reaction_to_comment_id`),
  CONSTRAINT `fk_reaction_reaction_by` FOREIGN KEY (`reaction_by`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_reaction_to_comment_id` FOREIGN KEY (`reaction_to_comment_id`) REFERENCES `comment` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_reaction_to_post_id` FOREIGN KEY (`reaction_to_post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table reddit-clone.reaction: ~0 rows (approximately)
DELETE FROM `reaction`;
INSERT INTO `reaction` (`id`, `type`, `timestamp`, `reaction_by`, `reaction_to`, `reaction_to_post_id`, `reaction_to_comment_id`) VALUES
	(9, 1, '2022-06-13', 1, 0, 2, NULL);

-- Dumping structure for table reddit-clone.report
DROP TABLE IF EXISTS `report`;
CREATE TABLE IF NOT EXISTS `report` (
  `id` int(11) NOT NULL,
  `reason` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `timestamp` date NOT NULL DEFAULT current_timestamp(),
  `byUser` int(11) NOT NULL DEFAULT 0,
  `accepted` int(1) NOT NULL DEFAULT 0,
  `report_to` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `report_to_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `fk_report_byUser` (`byUser`),
  KEY `fk_report_to_post` (`report_to_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table reddit-clone.report: ~0 rows (approximately)
DELETE FROM `report`;

-- Dumping structure for table reddit-clone.rule
DROP TABLE IF EXISTS `rule`;
CREATE TABLE IF NOT EXISTS `rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` text COLLATE utf8_unicode_ci NOT NULL,
  `community_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `fk_rule_community_id` (`community_id`),
  CONSTRAINT `fk_rule_community_id` FOREIGN KEY (`community_id`) REFERENCES `community` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table reddit-clone.rule: ~0 rows (approximately)
DELETE FROM `rule`;

-- Dumping structure for table reddit-clone.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(512) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `avatar` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `registration_date` date NOT NULL DEFAULT current_timestamp(),
  `description` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `display_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table reddit-clone.user: ~7 rows (approximately)
DELETE FROM `user`;
INSERT INTO `user` (`id`, `username`, `password`, `email`, `avatar`, `registration_date`, `description`, `display_name`) VALUES
	(1, 'proba1', '8f00d1ba2ed0bc5799b0b8e7a276117cd04572314cf607195e51a756b7e0879ad6d7ba26bfd4cab1ab22c936dd8bd191db29acc37dadff90abc8c8af1e62bc47', 'email1', 'avatar', '2022-05-23', 'test', 'test'),
	(3, 'dinic44', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec', 'dinic@mail.com', 'avatar', '2022-05-25', 'test', 'test'),
	(4, 'novikori', '8f00d1ba2ed0bc5799b0b8e7a276117cd04572314cf607195e51a756b7e0879ad6d7ba26bfd4cab1ab22c936dd8bd191db29acc37dadff90abc8c8af1e62bc47', 'korisnik', 'avatar', '2022-05-25', 'test', 'test'),
	(6, 'test123', 'daef4953b9783365cad6615223720506cc46c5167cd16ab500fa597aa08ff964eb24fb19687f34d7665f778fcb6c5358fc0a5b81e1662cf90f73a2671c53f991', 'test123', 'avatar', '2022-06-03', 'test', 'test'),
	(8, 'test1234', 'daef4953b9783365cad6615223720506cc46c5167cd16ab500fa597aa08ff964eb24fb19687f34d7665f778fcb6c5358fc0a5b81e1662cf90f73a2671c53f991', 'test1234', 'avatar', '2022-06-05', 'test', 'test'),
	(9, 'testtest1', 'ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff', 'test@test.test', NULL, '2022-06-05', NULL, 'testtest1'),
	(10, 'test', 'ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff', 'testtest1@test.test', NULL, '2022-06-05', NULL, 'test'),
	(14, 'vezbe', '78bf905c6c3d1ada6b7d872cdcf4a1faf9465a3a5da048daf241360b2d0eb50eedfbfd3f61deba6ccd04972976ee1a24f38581b9b063be46bbd98c2240b1f9f3', 'vezbe@we', NULL, '2022-06-08', NULL, 'vezbe');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
