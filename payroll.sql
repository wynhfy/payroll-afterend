/*
 Navicat MySQL Data Transfer

 Source Server         : demo
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : wyn

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 20/05/2020 00:13:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` char(19) NOT NULL COMMENT 'id',
  `openid` varchar(128) DEFAULT NULL COMMENT '微信openid',
  `mobile` varchar(11) DEFAULT '' COMMENT '手机号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `employee_id` char(19) NOT NULL COMMENT '工号',
  `is_disabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用 1(true)已经禁用，0(false)未禁用',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='会员表';

-- ----------------------------
-- Records of account
-- ----------------------------
BEGIN;
INSERT INTO `account` VALUES ('1261200443363078145', NULL, '17306692310', 'e10adc3949ba59abbe56e057f20f883e', '1259146195972427778', 0, '2020-05-15 15:43:12', '2020-05-18 21:10:50');
INSERT INTO `account` VALUES ('1261676072760012801', NULL, '15521072883', 'e10adc3949ba59abbe56e057f20f883e', '1258678598583754754', 0, '2020-05-16 23:13:11', '2020-05-18 21:11:00');
INSERT INTO `account` VALUES ('1261930313164554241', NULL, '15766518913', 'e10adc3949ba59abbe56e057f20f883e', '1258678808663859202', 0, '2020-05-17 16:03:27', '2020-05-18 20:53:29');
COMMIT;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `id` char(19) NOT NULL COMMENT '地址ID',
  `province` varchar(20) DEFAULT NULL COMMENT '省',
  `city` varchar(20) DEFAULT NULL COMMENT '市',
  `area` varchar(20) DEFAULT NULL COMMENT '区',
  `detail` varchar(30) DEFAULT NULL COMMENT '详细地址',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='地址';

-- ----------------------------
-- Records of address
-- ----------------------------
BEGIN;
INSERT INTO `address` VALUES ('1258678598583754754', '广东省', '佛山市', '顺德区', '四季山水豪华别墅101', '2020-05-09 15:31:18', '2020-05-10 11:44:23');
INSERT INTO `address` VALUES ('1258678808663859202', '广东省', '湛江市', '富人区', '汇景新城别墅区', '2020-05-09 15:32:01', '2020-05-10 11:46:15');
INSERT INTO `address` VALUES ('1258679195546460162', '广东省', '深圳市', '宝安区', '腾讯大厦', '2020-05-09 15:32:41', '2020-05-10 11:44:50');
INSERT INTO `address` VALUES ('1259146195972427778', '广东省', '深圳市', '福田区', '世外桃源101', '2020-05-09 23:40:22', '2020-05-19 17:37:11');
INSERT INTO `address` VALUES ('1259329290419888129', '广东省', '深圳市', '福田区', '世外桃源102', '2020-05-10 11:47:55', '2020-05-10 11:47:55');
COMMIT;

-- ----------------------------
-- Table structure for attendance
-- ----------------------------
DROP TABLE IF EXISTS `attendance`;
CREATE TABLE `attendance` (
  `id` char(19) NOT NULL COMMENT 'id',
  `status` varchar(10) NOT NULL DEFAULT '已到' COMMENT '已到)/迟到)',
  `employee_id` char(19) NOT NULL COMMENT '工号',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='考勤表';

-- ----------------------------
-- Records of attendance
-- ----------------------------
BEGIN;
INSERT INTO `attendance` VALUES ('1262396687628374017', '迟到', '1259146195972427778', '2020-05-18 22:56:39', '2020-05-18 22:56:39');
INSERT INTO `attendance` VALUES ('1262397146619449345', '迟到', '1258678598583754754', '2020-05-18 22:58:29', '2020-05-18 22:58:29');
INSERT INTO `attendance` VALUES ('1262397240152428545', '迟到', '1258678808663859202', '2020-05-18 22:58:51', '2020-05-18 22:58:51');
COMMIT;

-- ----------------------------
-- Table structure for benefit
-- ----------------------------
DROP TABLE IF EXISTS `benefit`;
CREATE TABLE `benefit` (
  `id` char(19) NOT NULL COMMENT '津贴id',
  `employee_id` char(19) NOT NULL COMMENT '员工工号',
  `over_time` double NOT NULL DEFAULT '0' COMMENT '加班总时长，按小时算',
  `over_day` int(10) NOT NULL DEFAULT '0' COMMENT '加班天数',
  `benefit_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '总津贴',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='津贴表';

-- ----------------------------
-- Records of benefit
-- ----------------------------
BEGIN;
INSERT INTO `benefit` VALUES ('1262045997215322114', '1258678808663859202', 1, 1, 12.50, '2020-05-17 23:43:08', '2020-05-17 23:56:33');
INSERT INTO `benefit` VALUES ('1262679084064362497', '1259146195972427778', 18.5, 1, 277.50, '2020-05-19 17:38:48', '2020-05-19 17:38:48');
COMMIT;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `dept_id` char(19) NOT NULL COMMENT '部门ID',
  `dept_name` varchar(20) NOT NULL COMMENT '部门名称',
  `dept_address` varchar(40) DEFAULT NULL COMMENT '部门地址',
  `total_num` int(10) DEFAULT '0' COMMENT '部门人数',
  `employee_id` char(19) DEFAULT NULL COMMENT '部门负责人ID',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='部门';

-- ----------------------------
-- Records of department
-- ----------------------------
BEGIN;
INSERT INTO `department` VALUES ('1258062897997238273', '秘书部', '三楼404', 0, '', '2020-05-06 23:55:43', '2020-05-06 23:55:43');
INSERT INTO `department` VALUES ('1258063066524372993', '项目部', '四楼404', 2, '', '2020-05-06 23:56:23', '2020-05-10 11:47:55');
INSERT INTO `department` VALUES ('1258323857731223554', '清洁部', '负一楼102', 0, NULL, '2020-05-07 17:12:41', '2020-05-07 17:12:41');
INSERT INTO `department` VALUES ('1258676424285892609', '董事长', '五楼101', 1, NULL, '2020-05-08 16:33:39', '2020-05-08 16:33:39');
INSERT INTO `department` VALUES ('1258676498697039873', '总经理', '4楼101', 1, NULL, '2020-05-08 16:33:57', '2020-05-08 16:33:57');
INSERT INTO `department` VALUES ('1258676568771276801', '保安大队', '1楼101', 1, NULL, '2020-05-08 16:34:14', '2020-05-08 16:34:14');
INSERT INTO `department` VALUES ('1259148131136184321', '设计部', '三楼604', 0, NULL, '2020-05-09 23:48:03', '2020-05-09 23:48:03');
COMMIT;

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` char(19) NOT NULL COMMENT '员工ID',
  `name` varchar(20) NOT NULL COMMENT '员工姓名',
  `sex` varchar(10) DEFAULT NULL COMMENT '员工性别',
  `age` int(10) DEFAULT NULL COMMENT '员工年龄',
  `nation` varchar(20) DEFAULT NULL COMMENT '民族',
  `telephone` varchar(20) DEFAULT NULL COMMENT '电话',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `academic` varchar(20) DEFAULT NULL COMMENT '学历',
  `dept_id` char(19) NOT NULL COMMENT '所属部门',
  `work_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '工种',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `is_active` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否激活(是否进行注册)',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='员工';

-- ----------------------------
-- Records of employee
-- ----------------------------
BEGIN;
INSERT INTO `employee` VALUES ('1258678598583754754', 'QTJ', '男', 22, '汉族', '110421', '123@qq.com', '博士', '1258676424285892609', '1259326957258567681', 'https://wyn-payroll.oss-cn-shenzhen.aliyuncs.com/2020/05/08/2a343752800f44ebb6b398e15f14a9bbQTJ.jpeg', 1, '2020-05-08 16:42:18', '2020-05-16 23:13:11');
INSERT INTO `employee` VALUES ('1258678808663859202', 'JIAOPI', '男', 22, '汉族', '23214', '321@qq.com', '硕士', '1258676498697039873', '1259328786591703041', 'https://wyn-payroll.oss-cn-shenzhen.aliyuncs.com/2020/05/08/63c23e10da074518a75e2159d2d9b7f4JIAOPI.jpeg', 1, '2020-05-08 16:43:08', '2020-05-17 16:03:27');
INSERT INTO `employee` VALUES ('1258679195546460162', 'BAO', '男', 22, '汉族', '14115', '666@qq.com', '本科', '1258676568771276801', '1259326957359230977', 'https://wyn-payroll.oss-cn-shenzhen.aliyuncs.com/2020/05/08/170232087be54f0eb8f4a1ca4fa2d5c4BAO.jpeg', 0, '2020-05-08 16:44:40', '2020-05-10 11:44:50');
INSERT INTO `employee` VALUES ('1259146195972427778', 'WYN', '男', 21, '汉', '17306692310', '457968028@qq.com', '本科', '1258063066524372993', '1258385147153682433', 'https://wyn-payroll.oss-cn-shenzhen.aliyuncs.com/2020/05/09/58dc6201cf61472f833d0c5c39d0c2b11.jpeg', 1, '2020-05-09 23:40:22', '2020-05-19 17:37:11');
INSERT INTO `employee` VALUES ('1259329290419888129', 'CZY', '男', 21, '新疆维吾尔族', '123', '432fnl@qq.com', '本科', '1258063066524372993', '1258385147094962177', '/static/默认头像.jpg', 0, '2020-05-10 11:47:55', '2020-05-10 11:47:55');
COMMIT;

-- ----------------------------
-- Table structure for month_salary
-- ----------------------------
DROP TABLE IF EXISTS `month_salary`;
CREATE TABLE `month_salary` (
  `id` char(19) NOT NULL COMMENT 'id',
  `employee_id` char(19) NOT NULL COMMENT '工号',
  `name` varchar(20) DEFAULT NULL COMMENT '员工姓名',
  `base_salary` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '基础工资',
  `benefit` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '津贴(加班费)',
  `deduct_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '扣钱',
  `final_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '最终工资',
  `year` int(10) NOT NULL COMMENT '年',
  `month` int(10) NOT NULL COMMENT '月',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='月工资表';

-- ----------------------------
-- Records of month_salary
-- ----------------------------
BEGIN;
INSERT INTO `month_salary` VALUES ('1262775477441519617', '1258678598583754754', 'QTJ', 100000.00, 0.00, 660.00, 99340.00, 2020, 5, '2020-05-20 00:01:50', '2020-05-20 00:05:28');
INSERT INTO `month_salary` VALUES ('1262775700893065218', '1259146195972427778', 'WYN', 15000.00, 277.50, 660.00, 14617.50, 2020, 5, '2020-05-20 00:02:43', '2020-05-20 00:02:43');
INSERT INTO `month_salary` VALUES ('1262777754164256770', '1258678808663859202', 'JIAOPI', 50000.00, 12.50, 660.00, 49352.50, 2020, 5, '2020-05-20 00:10:53', '2020-05-20 00:10:53');
COMMIT;

-- ----------------------------
-- Table structure for over_work
-- ----------------------------
DROP TABLE IF EXISTS `over_work`;
CREATE TABLE `over_work` (
  `id` char(19) NOT NULL COMMENT '加班记录id',
  `employee_id` char(19) NOT NULL COMMENT '加班员工工号',
  `type_id` char(19) NOT NULL COMMENT '加班类别id',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `status` varchar(10) NOT NULL COMMENT '开始加班和结束加班',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='加班记录表';

-- ----------------------------
-- Records of over_work
-- ----------------------------
BEGIN;
INSERT INTO `over_work` VALUES ('1261678066782547970', '1258678598583754754', '1261550866863247362', '节假日加班，三倍工资', '结束加班', '2020-05-16 23:21:07', '2020-05-16 23:56:05');
INSERT INTO `over_work` VALUES ('1262399694294224897', '1259146195972427778', '1261551019208757250', '高温加班', '结束加班', '2020-05-18 23:08:36', '2020-05-19 17:38:48');
COMMIT;

-- ----------------------------
-- Table structure for over_work_info
-- ----------------------------
DROP TABLE IF EXISTS `over_work_info`;
CREATE TABLE `over_work_info` (
  `id` char(19) NOT NULL COMMENT '加班类别id',
  `description` text COMMENT '加班类别信息描述',
  `fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '加班费,按小时算',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='加班类别信息表';

-- ----------------------------
-- Records of over_work_info
-- ----------------------------
BEGIN;
INSERT INTO `over_work_info` VALUES ('1261550866863247362', '节假日加班，三倍工资', 10.00, '2020-05-16 14:55:40', '2020-05-16 14:55:40');
INSERT INTO `over_work_info` VALUES ('1261551019208757250', '高温加班', 15.00, '2020-05-16 14:56:16', '2020-05-16 14:56:16');
COMMIT;

-- ----------------------------
-- Table structure for work
-- ----------------------------
DROP TABLE IF EXISTS `work`;
CREATE TABLE `work` (
  `id` char(19) NOT NULL COMMENT '工种id',
  `name` char(19) NOT NULL COMMENT '工种名称',
  `grade` int(10) DEFAULT NULL COMMENT '等级',
  `base_salary` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '基础工资',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='工种表';

-- ----------------------------
-- Records of work
-- ----------------------------
BEGIN;
INSERT INTO `work` VALUES ('1258385147094962177', '前端开发工程师', 3, 10000.00, '2020-05-07 21:16:13', '2020-05-07 21:16:13');
INSERT INTO `work` VALUES ('1258385147153682433', '全栈开发工程师', 4, 15000.00, '2020-05-07 21:16:13', '2020-05-07 21:16:13');
INSERT INTO `work` VALUES ('1259326956713308161', '设计师', 2, 5000.00, '2020-05-10 11:38:38', '2020-05-10 11:38:38');
INSERT INTO `work` VALUES ('1259326957258567681', '董事长', 6, 100000.00, '2020-05-10 11:38:38', '2020-05-10 11:38:38');
INSERT INTO `work` VALUES ('1259326957359230977', '保安', 1, 3000.00, '2020-05-10 11:38:38', '2020-05-10 11:38:38');
INSERT INTO `work` VALUES ('1259328786591703041', '总经理', 5, 50000.00, '2020-05-10 11:45:55', '2020-05-10 11:45:55');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
