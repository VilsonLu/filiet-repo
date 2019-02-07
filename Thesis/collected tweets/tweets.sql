SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema filiet
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `filiet` ;
CREATE SCHEMA IF NOT EXISTS `filiet` DEFAULT CHARACTER SET utf8 ;
USE `filiet` ;

-- -----------------------------------------------------
-- Table `filiet`.`tweets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `filiet`.`tweets` (
  `TweetID` BIGINT(20) NOT NULL,
  `User` VARCHAR(45) NULL DEFAULT NULL,
  `Tweet` VARCHAR(140) NULL DEFAULT NULL,
  `Latitude` DOUBLE NULL DEFAULT NULL,
  `Longitude` DOUBLE NULL DEFAULT NULL,
  `IsHashtag` TINYINT(4) NULL DEFAULT NULL,
  `IsURL` TINYINT(4) NULL DEFAULT NULL,
  `IsRetweet` TINYINT(4) NULL DEFAULT NULL,
  `Language` VARCHAR(5) NULL DEFAULT NULL,
  PRIMARY KEY (`TweetID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
