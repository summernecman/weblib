/*
Navicat MySQL Data Transfer

Source Server         : server
Source Server Version : 50715
Source Host           : localhost:3306
Source Database       : record

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2018-03-27 13:41:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `record`
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `locpath` varchar(255) DEFAULT '' COMMENT '手机路径',
  `netpath` varchar(255) DEFAULT '' COMMENT '服务器路径',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `utime` datetime DEFAULT NULL COMMENT '更新时间',
  `content` text COMMENT '文字内容',
  `atype` varchar(255) NOT NULL COMMENT '数据类型',
  `btype` varchar(255) DEFAULT '' COMMENT '文字类型',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of record
-- ----------------------------

-- ----------------------------
-- Table structure for `tip`
-- ----------------------------
DROP TABLE IF EXISTS `tip`;
CREATE TABLE `tip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `recordid` int(11) NOT NULL,
  `tipid` int(11) NOT NULL,
  `ctime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tip
-- ----------------------------

-- ----------------------------
-- Table structure for `tiplab`
-- ----------------------------
DROP TABLE IF EXISTS `tiplab`;
CREATE TABLE `tiplab` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL COMMENT '描述内容',
  `enable` int(11) DEFAULT NULL COMMENT '是否启用',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tiplab
-- ----------------------------
