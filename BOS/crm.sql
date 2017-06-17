/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50536
Source Host           : localhost:3306
Source Database       : crm

Target Server Type    : MYSQL
Target Server Version : 50536
File Encoding         : 65001

Date: 2017-06-17 13:52:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_customer
-- ----------------------------
DROP TABLE IF EXISTS `t_customer`;
CREATE TABLE `t_customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `station` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `decidedzone_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_customer
-- ----------------------------
INSERT INTO `t_customer` VALUES ('1', '张三', '苏州园区', '13612341234', '金鸡湖大道', null);
INSERT INTO `t_customer` VALUES ('2', '李四', '苏州吴江', '1361234456', '松陵镇', null);
INSERT INTO `t_customer` VALUES ('3', '王五', '苏州新区', '13612341235', '苏州乐园', 'DQ1111');
INSERT INTO `t_customer` VALUES ('4', '赵六', '苏州相城', '13612326234', '高铁站', 'DQ1111');
INSERT INTO `t_customer` VALUES ('5', '小明', '苏州吴中', '13612341634', '石湖', null);
INSERT INTO `t_customer` VALUES ('6', '大明', '苏州昆山', '13656341234', '千灯', null);
