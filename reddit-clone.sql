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
  `by` int(11) DEFAULT 0,
  `user_id` int(11) NOT NULL DEFAULT 0,
  `community_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `fk_banned_by` (`by`),
  KEY `fk_banned_user_id` (`user_id`),
  KEY `fk_banned_community_id` (`community_id`),
  CONSTRAINT `fk_banned_by` FOREIGN KEY (`by`) REFERENCES `moderator` (`id`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `fk_banned_community_id` FOREIGN KEY (`community_id`) REFERENCES `community` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_banned_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
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
  `by_id` int(11) DEFAULT 0,
  `post_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `fk_comment_by_id` (`by_id`),
  KEY `fk_comment_post_id` (`post_id`),
  KEY `FK_comment_replies_to` (`replies_to`),
  CONSTRAINT `fk_comment_by_id` FOREIGN KEY (`by_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_post_id` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_replies_to` FOREIGN KEY (`replies_to`) REFERENCES `comment` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table reddit-clone.community: ~0 rows (approximately)
DELETE FROM `community`;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table reddit-clone.moderator: ~0 rows (approximately)
DELETE FROM `moderator`;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table reddit-clone.post: ~0 rows (approximately)
DELETE FROM `post`;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table reddit-clone.reaction: ~0 rows (approximately)
DELETE FROM `reaction`;

-- Dumping structure for table reddit-clone.report
DROP TABLE IF EXISTS `report`;
CREATE TABLE IF NOT EXISTS `report` (
  `id` int(11) NOT NULL,
  `reason` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `timestamp` date NOT NULL DEFAULT current_timestamp(),
  `byUser` int(11) DEFAULT 0,
  `accepted` int(1) NOT NULL DEFAULT 0,
  `report_to` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `report_to_post_id` int(11) DEFAULT 0,
  `report_to_comment_id` int(11) DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `fk_report_byUser` (`byUser`),
  KEY `fk_report_to_post` (`report_to_post_id`) USING BTREE,
  KEY `fk_report_report_to_comment_id` (`report_to_comment_id`),
  CONSTRAINT `fk_report_byUser` FOREIGN KEY (`byUser`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `fk_report_report_to_comment_id` FOREIGN KEY (`report_to_comment_id`) REFERENCES `comment` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_report_report_to_post_id` FOREIGN KEY (`report_to_post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table reddit-clone.user: ~0 rows (approximately)
DELETE FROM `user`;
INSERT INTO `user` (`id`, `username`, `password`, `email`, `avatar`, `registration_date`, `description`, `display_name`) VALUES
	(1, 'admin', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec', 'admin@admin', NULL, '2022-06-14', NULL, 'admin');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
