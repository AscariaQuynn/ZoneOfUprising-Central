/*
Navicat MySQL Data Transfer

Source Server         : ZoneOfUprising local
Source Server Version : 50620
Source Host           : localhost:3306
Source Database       : zoneofuprising

Target Server Type    : MYSQL
Target Server Version : 50620
File Encoding         : 65001

Date: 2014-12-14 12:41:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `entity`
-- ----------------------------
DROP TABLE IF EXISTS `entity`;
CREATE TABLE `entity` (
  `idEntity` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `path` varchar(45) NOT NULL,
  `mass` float(7,2) NOT NULL,
  `hitPoints` float(7,2) NOT NULL,
  `gunSlots` tinyint(4) NOT NULL DEFAULT '0',
  `missileSlots` tinyint(4) NOT NULL DEFAULT '0',
  `containerName` varchar(45) NOT NULL,
  `spawnRadius` float(6,2) NOT NULL,
  `created` datetime NOT NULL,
  PRIMARY KEY (`idEntity`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of entity
-- ----------------------------
INSERT INTO `entity` VALUES ('1', 'Dark Fighter 6', 'SpaceShips/dark_fighter_6', '21.28', '2347.21', '2', '6', 'shipsNode', '11.64', '2014-10-11 20:50:24');
INSERT INTO `entity` VALUES ('2', 'SciFi Fighter MK', 'SpaceShips/scifi_fighter_mk', '48.32', '5860.58', '3', '8', 'shipsNode', '25.78', '2014-10-11 20:50:25');
INSERT INTO `entity` VALUES ('3', 'Space Frigate 6', 'SpaceShips/space_frigate_6', '17.79', '1781.92', '2', '4', 'shipsNode', '10.19', '2014-10-19 17:57:50');
INSERT INTO `entity` VALUES ('4', 'Normandy SR-2', 'SpaceShips/Normandy', '12073.64', '34254.18', '0', '0', 'shipsNode', '102.57', '2014-12-11 14:40:24');

-- ----------------------------
-- Table structure for `entitycargobay`
-- ----------------------------
DROP TABLE IF EXISTS `entitycargobay`;
CREATE TABLE `entitycargobay` (
  `idEntityCargoBay` int(11) NOT NULL,
  `idEntityProfile` int(11) NOT NULL,
  `idItem` int(11) NOT NULL,
  `properties` text,
  `quantity` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idEntityCargoBay`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of entitycargobay
-- ----------------------------

-- ----------------------------
-- Table structure for `entityitem`
-- ----------------------------
DROP TABLE IF EXISTS `entityitem`;
CREATE TABLE `entityitem` (
  `idEntityItem` int(11) NOT NULL,
  `idEntityProfile` int(11) NOT NULL,
  `idItem` int(11) NOT NULL,
  `properties` text NOT NULL,
  PRIMARY KEY (`idEntityItem`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of entityitem
-- ----------------------------
INSERT INTO `entityitem` VALUES ('1', '1', '4', '');
INSERT INTO `entityitem` VALUES ('2', '1', '4', '');
INSERT INTO `entityitem` VALUES ('3', '1', '8', '{\"linearPower\":32.28,\"angularPower\":26.32,\"pid\":[1,0,1.3]}');
INSERT INTO `entityitem` VALUES ('4', '2', '9', '{\"linearPower\":64.72,\"angularPower\":73.42,\"pid\":[1,0,2.2]}');
INSERT INTO `entityitem` VALUES ('5', '3', '8', '{\"linearPower\":32.28,\"angularPower\":26.32,\"pid\":[1,0,1.3]}');
INSERT INTO `entityitem` VALUES ('6', '4', '9', '{\"linearPower\":64.72,\"angularPower\":73.42,\"pid\":[1,0,2.2]}');
INSERT INTO `entityitem` VALUES ('7', '5', '8', '{\"linearPower\":32.28,\"angularPower\":26.32,\"pid\":[1,0,1.3]}');
INSERT INTO `entityitem` VALUES ('8', '6', '9', '{\"linearPower\":32.28,\"angularPower\":26.32,\"pid\":[1,0,1.3]}');
INSERT INTO `entityitem` VALUES ('9', '7', '8', '{\"linearPower\":32.28,\"angularPower\":26.32,\"pid\":[1,0,1.3]}');
INSERT INTO `entityitem` VALUES ('10', '8', '8', '{\"linearPower\":64.72,\"angularPower\":73.42,\"pid\":[1,0,2.2]}');
INSERT INTO `entityitem` VALUES ('11', '9', '8', '{\"linearPower\":32.28,\"angularPower\":26.32,\"pid\":[1,0,1.3]}');
INSERT INTO `entityitem` VALUES ('12', '10', '8', '{\"linearPower\":32.28,\"angularPower\":26.32,\"pid\":[1,0,1.3]}');
INSERT INTO `entityitem` VALUES ('13', '11', '8', '{\"linearPower\":32.28,\"angularPower\":26.32,\"pid\":[1,0,1.3]}');
INSERT INTO `entityitem` VALUES ('14', '12', '8', '{\"linearPower\":32.28,\"angularPower\":26.32,\"pid\":[1,0,1.3]}');
INSERT INTO `entityitem` VALUES ('15', '13', '8', '{\"linearPower\":64.72,\"angularPower\":73.42,\"pid\":[1,0,2.2]}');
INSERT INTO `entityitem` VALUES ('16', '14', '8', '{\"linearPower\":32.28,\"angularPower\":26.32,\"pid\":[1,0,1.3]}');
INSERT INTO `entityitem` VALUES ('17', '15', '9', '{\"linearPower\":4653.18,\"angularPower\":112541.32,\"pid\":[1,0,4.2]}');

-- ----------------------------
-- Table structure for `entityprofile`
-- ----------------------------
DROP TABLE IF EXISTS `entityprofile`;
CREATE TABLE `entityprofile` (
  `idEntityProfile` int(11) NOT NULL,
  `idUserProfile` int(11) NOT NULL,
  `idWorldProfile` int(11) NOT NULL,
  `idEntity` int(11) NOT NULL,
  `isSelected` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `experience` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`idEntityProfile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of entityprofile
-- ----------------------------
INSERT INTO `entityprofile` VALUES ('1', '1', '0', '1', '', 'Asca Dark Fighter', '2014-10-11 20:50:41', '454153');
INSERT INTO `entityprofile` VALUES ('2', '1', '0', '2', '', 'Asca SciFi Fighter MK', '2014-10-11 20:50:43', '54214');
INSERT INTO `entityprofile` VALUES ('3', '2', '0', '1', '', 'Kakika Dark Fighter', '2014-10-11 20:50:45', '0');
INSERT INTO `entityprofile` VALUES ('4', '2', '0', '2', '', 'Kakika SciFi Fighter MK', '2014-10-11 20:50:48', '0');
INSERT INTO `entityprofile` VALUES ('5', '1', '0', '3', '', 'Asca Space Frigate', '2014-10-19 17:58:33', '25028');
INSERT INTO `entityprofile` VALUES ('6', '1', '0', '1', '', 'Asca Dark 3', '2014-10-17 21:43:57', '0');
INSERT INTO `entityprofile` VALUES ('7', '1', '0', '1', '', 'Asca Dark 4', '2014-10-17 21:44:03', '0');
INSERT INTO `entityprofile` VALUES ('8', '1', '0', '2', '', 'Asca MK 2', '2014-10-17 21:44:07', '0');
INSERT INTO `entityprofile` VALUES ('9', '0', '1', '1', '', 'Eagle I', '2014-10-17 21:44:10', '0');
INSERT INTO `entityprofile` VALUES ('10', '0', '1', '1', '', 'Eagle II', '2014-10-17 21:44:14', '0');
INSERT INTO `entityprofile` VALUES ('11', '0', '2', '1', '', 'Eagle I', '2014-11-16 18:55:25', '0');
INSERT INTO `entityprofile` VALUES ('12', '0', '3', '1', '', 'Eagle I', '2014-11-16 18:55:26', '0');
INSERT INTO `entityprofile` VALUES ('13', '0', '3', '2', '', 'Hawk I', '2014-11-17 18:54:17', '0');
INSERT INTO `entityprofile` VALUES ('14', '0', '3', '3', '', 'Falcon I', '2014-11-17 18:54:48', '0');
INSERT INTO `entityprofile` VALUES ('15', '1', '0', '4', '', 'Normandy SR-2', '2014-12-11 14:41:31', '0');

-- ----------------------------
-- Table structure for `item`
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `idItem` int(11) NOT NULL,
  `idItemCategory` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` enum('SpaceShip','Engine','Gun','Missile','Light') NOT NULL,
  `class` varchar(255) DEFAULT NULL,
  `mass` float(7,2) NOT NULL,
  `price` double NOT NULL DEFAULT '0',
  `properties` text,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idItem`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES ('1', '1', 'Dark Fighter 6', 'SpaceShip', null, '0.00', '99.99', null, '2014-10-11 17:57:07');
INSERT INTO `item` VALUES ('2', '1', 'SciFi Fighter MK', 'SpaceShip', null, '0.00', '199.98', null, '2014-10-11 17:57:19');
INSERT INTO `item` VALUES ('3', '3', 'Turret2', 'Gun', null, '4.22', '10.95', null, '2014-10-11 17:57:47');
INSERT INTO `item` VALUES ('4', '3', 'Dark Fighter 6 Wing Gun', 'Gun', null, '3.21', '15.2', null, '2014-10-11 18:01:56');
INSERT INTO `item` VALUES ('5', '4', 'Asca Missile', 'Missile', null, '1.01', '29.15', null, '2014-10-11 18:02:36');
INSERT INTO `item` VALUES ('6', '5', 'Point Light', 'Light', null, '0.32', '0.99', null, '2014-10-11 18:12:04');
INSERT INTO `item` VALUES ('7', '5', 'Spot Light', 'Light', null, '0.23', '0.99', null, '2014-10-11 18:12:16');
INSERT INTO `item` VALUES ('8', '2', 'Standard Fighter Engine', 'Engine', null, '6.21', '35.48', '{\"linearPower\":32.28,\"angularPower\":26.32,\"pid\":[1,0,1.3]}', '2014-12-11 16:21:49');
INSERT INTO `item` VALUES ('9', '2', 'Standard Cruiser Engine', 'Engine', null, '22.11', '172.55', '{\"linearPower\":64.72,\"angularPower\":73.42,\"pid\":[1,0,2.2]}', '2014-12-11 21:30:32');
INSERT INTO `item` VALUES ('10', '2', 'Standard Heavy Engine', 'Engine', null, '480.66', '318.48', '{\"linearPower\":4653.18,\"angularPower\":112541.32,\"pid\":[1,0,1.7]}', '2014-12-14 00:49:41');

-- ----------------------------
-- Table structure for `itemcategory`
-- ----------------------------
DROP TABLE IF EXISTS `itemcategory`;
CREATE TABLE `itemcategory` (
  `idItemCategory` int(11) NOT NULL,
  `parent` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`idItemCategory`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of itemcategory
-- ----------------------------
INSERT INTO `itemcategory` VALUES ('1', null, 'Space Ships');
INSERT INTO `itemcategory` VALUES ('2', null, 'Engines');
INSERT INTO `itemcategory` VALUES ('3', null, 'Guns');
INSERT INTO `itemcategory` VALUES ('4', null, 'Missiles');
INSERT INTO `itemcategory` VALUES ('5', null, 'Lights');

-- ----------------------------
-- Table structure for `serverprofile`
-- ----------------------------
DROP TABLE IF EXISTS `serverprofile`;
CREATE TABLE `serverprofile` (
  `idServerProfile` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `state` enum('Restricted','Trusted','Deleted') NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastLogin` datetime DEFAULT NULL,
  PRIMARY KEY (`idServerProfile`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of serverprofile
-- ----------------------------
INSERT INTO `serverprofile` VALUES ('1', 'Ascaria Server One', 'ascasrvone', 'Trusted', '2014-10-11 16:02:09', null);

-- ----------------------------
-- Table structure for `userinventory`
-- ----------------------------
DROP TABLE IF EXISTS `userinventory`;
CREATE TABLE `userinventory` (
  `idUserInventory` int(11) NOT NULL,
  `idUserProfile` int(11) NOT NULL,
  `idItem` int(11) NOT NULL,
  `properties` text,
  `quantity` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idUserInventory`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinventory
-- ----------------------------
INSERT INTO `userinventory` VALUES ('1', '1', '1', null, '1');
INSERT INTO `userinventory` VALUES ('2', '2', '2', null, '1');

-- ----------------------------
-- Table structure for `userprofile`
-- ----------------------------
DROP TABLE IF EXISTS `userprofile`;
CREATE TABLE `userprofile` (
  `idUserProfile` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `money` double NOT NULL DEFAULT '0',
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `state` enum('Unconfirmed','Active','Deleted') NOT NULL DEFAULT 'Unconfirmed',
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastLogin` datetime DEFAULT NULL,
  PRIMARY KEY (`idUserProfile`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `email` (`email`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userprofile
-- ----------------------------
INSERT INTO `userprofile` VALUES ('1', 'Ascaria', '100000', 'jarmil.sv@volny.cz', '12345', 'Active', '2014-10-11 15:57:13', null);
INSERT INTO `userprofile` VALUES ('2', 'Kakika', '100000', 'kajinkaf@seznam.cz', '12345', 'Active', '2014-10-11 16:03:22', null);
INSERT INTO `userprofile` VALUES ('3', 'Pavel', '100000', 'ppavel@volny.cz', '12345', 'Active', '2014-10-28 11:38:15', null);

-- ----------------------------
-- Table structure for `world`
-- ----------------------------
DROP TABLE IF EXISTS `world`;
CREATE TABLE `world` (
  `idWorld` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `path` varchar(255) NOT NULL,
  `type` enum('Gameplay','Hangar') NOT NULL DEFAULT 'Gameplay',
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idWorld`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of world
-- ----------------------------
INSERT INTO `world` VALUES ('1', 'Scene1', 'Scenes/Scene1/Scene1.j3o', 'Gameplay', '2014-10-11 16:37:59');
INSERT INTO `world` VALUES ('2', 'Level2', 'Scenes/Uvod/level2.j3o', 'Gameplay', '2014-10-11 16:38:37');
INSERT INTO `world` VALUES ('3', 'SpaceStation', 'Scenes/SpaceStation/SpaceStation.j3o', 'Gameplay', '2014-10-11 16:39:03');
INSERT INTO `world` VALUES ('4', 'PatternStation', 'Scenes/PatternStation/PatternStation.j3o', 'Gameplay', '2014-10-11 16:39:25');

-- ----------------------------
-- Table structure for `worldprofile`
-- ----------------------------
DROP TABLE IF EXISTS `worldprofile`;
CREATE TABLE `worldprofile` (
  `idWorldProfile` int(11) NOT NULL,
  `idServerProfile` int(11) NOT NULL,
  `idWorld` int(11) NOT NULL,
  `isSelected` bit(1) NOT NULL DEFAULT b'0',
  `name` varchar(255) NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idWorldProfile`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of worldprofile
-- ----------------------------
INSERT INTO `worldprofile` VALUES ('1', '1', '1', '', 'Asca Scene1 World', '2014-10-11 16:41:15');
INSERT INTO `worldprofile` VALUES ('2', '1', '2', '', 'Asca Level2 World', '2014-10-11 16:41:45');
INSERT INTO `worldprofile` VALUES ('3', '1', '3', '', 'Asca SpaceStation World', '2014-10-11 16:42:13');
