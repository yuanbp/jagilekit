/*
 Navicat Premium Data Transfer

 Source Server         : mysql-vmware
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : 192.168.2.151:3306
 Source Schema         : jagilekit

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 08/08/2018 14:44:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` varchar(36) NOT NULL,
  `name` varchar(30) DEFAULT NULL COMMENT '名称',
  `name_en` varchar(50) DEFAULT NULL COMMENT '英文',
  `bianma` varchar(50) DEFAULT NULL COMMENT '编码',
  `parent_id` varchar(100) DEFAULT NULL COMMENT '上级ID',
  `bz` varchar(255) DEFAULT NULL COMMENT '备注',
  `headman` varchar(30) DEFAULT NULL COMMENT '负责人',
  `tel` varchar(50) DEFAULT NULL COMMENT '电话',
  `functions` varchar(255) DEFAULT NULL COMMENT '部门职能',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menuId` varchar(36) NOT NULL,
  `menuName` varchar(100) NOT NULL COMMENT '栏目名',
  `parentId` int(11) DEFAULT '0' COMMENT '权限类型（所属父级，0本身）',
  `menuUrl` varchar(200) NOT NULL COMMENT '链接地址',
  `menuNodeType` int(11) DEFAULT '0' COMMENT '权限节点类型(1代表导航（父），2代表一级菜单，3代表二级菜单)',
  `menuState` int(11) DEFAULT '0' COMMENT '状态 0 显示 1 禁用',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `updater` varchar(36) DEFAULT NULL COMMENT '编辑人',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`menuId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台菜单栏表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES ('1', '系统管理', 0, '/menu/list', 0, 0, 999, '', '', '2018-05-28 17:28:04', '2018-05-28 17:28:04');
INSERT INTO `sys_menu` VALUES ('2', '用户管理', 1, 'javascript:;', 0, 0, 0, '', '', '2018-05-28 17:29:24', '2018-05-28 17:29:24');
INSERT INTO `sys_menu` VALUES ('3', '菜单管理', 1, 'javascript:;', 0, 0, 1, '', '', '2018-05-28 17:29:24', '2018-05-28 17:29:24');
INSERT INTO `sys_menu` VALUES ('4', '权限管理', 1, 'javascript:;', 0, 0, 2, '', '', '2018-05-28 17:29:24', '2018-05-28 17:29:24');
COMMIT;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `menuId` varchar(36) NOT NULL,
  `menuName` varchar(100) NOT NULL COMMENT '栏目名',
  `type` varchar(50) NOT NULL DEFAULT '0' COMMENT '类型 0.菜单 1.按钮 2.权限标识',
  `permission` varchar(50) DEFAULT NULL COMMENT '权限标识',
  `parentId` varchar(36) DEFAULT NULL COMMENT '权限类型（所属父级，0本身）',
  `parentIds` varchar(360) DEFAULT NULL COMMENT '所有父级',
  `level` varchar(5) NOT NULL DEFAULT '0' COMMENT '0 父菜单 1 子菜单',
  `menuUrl` varchar(200) NOT NULL COMMENT '链接地址',
  `menuNodeType` int(11) DEFAULT '0' COMMENT '权限节点类型(1代表导航（父），2代表一级菜单，3代表二级菜单)',
  `menuState` int(11) DEFAULT '0' COMMENT '状态 0 显示 1 禁用',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `updater` varchar(36) DEFAULT NULL COMMENT '编辑人',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`menuId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台菜单栏表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` VALUES ('1', '系统管理', '0', NULL, NULL, '0', '0', 'javascript:;', 0, 0, 999, '', '', '2018-05-28 17:28:04', '2018-05-28 17:28:04');
INSERT INTO `sys_permission` VALUES ('2', '用户管理', '0', NULL, NULL, '1', '1', '/sysuser/list', 0, 0, 0, '', '', '2018-05-28 17:29:24', '2018-05-28 17:29:24');
INSERT INTO `sys_permission` VALUES ('3', '菜单管理', '0', NULL, NULL, '1', '1', '/menu/list', 0, 0, 1, '', '', '2018-05-28 17:29:24', '2018-05-28 17:29:24');
INSERT INTO `sys_permission` VALUES ('4', '权限管理', '0', NULL, NULL, '1', '1', '/auth/list', 0, 0, 2, '', '', '2018-05-28 17:29:24', '2018-05-28 17:29:24');
INSERT INTO `sys_permission` VALUES ('5', '测试菜单', '0', NULL, NULL, '1', '0', 'javascript:;', 0, 0, 3, NULL, NULL, '2018-06-06 14:28:43', '2018-06-06 14:28:45');
INSERT INTO `sys_permission` VALUES ('6', '测试子菜单', '0', NULL, NULL, '5', '1', 'javascript:;', 0, 0, 5, NULL, NULL, '2018-06-06 14:28:58', '2018-06-06 14:28:56');
INSERT INTO `sys_permission` VALUES ('7', '测试菜单2', '0', NULL, NULL, '0', '0', 'javascript:;', 0, 0, 6, NULL, NULL, '2018-06-06 16:34:51', '2018-06-06 16:34:53');
INSERT INTO `sys_permission` VALUES ('8', '测试子菜单2', '0', NULL, NULL, '7', '1', 'javascript:;', 0, 0, 7, NULL, NULL, '2018-06-06 16:35:37', '2018-06-06 16:35:39');
INSERT INTO `sys_permission` VALUES ('9', '部门管理', '0', NULL, NULL, '1', '1', 'javascript:;', 0, 0, 8, NULL, NULL, '2018-06-06 16:59:14', '2018-06-06 16:59:16');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `roleId` varchar(36) NOT NULL,
  `roleName` varchar(64) NOT NULL COMMENT '角色名',
  `flag` int(11) DEFAULT '0' COMMENT '0 正常，1 禁用',
  `menuId` varchar(100) DEFAULT NULL COMMENT '权限ID',
  `roleContent` text COMMENT '描述',
  `updater` varchar(36) DEFAULT NULL COMMENT '编辑人',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台角色权限表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('1', '系统初始化超级管理员', 0, '', '', '', '2018-06-04 11:19:26', '2018-06-04 11:19:26');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `roleid` varchar(36) NOT NULL COMMENT '角色ID',
  `menuid` varchar(36) NOT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色资源关联表';

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `roleid` varchar(36) NOT NULL COMMENT '角色ID',
  `menuid` varchar(36) NOT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色资源关联表';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_permission` VALUES ('0', '1', '1');
INSERT INTO `sys_role_permission` VALUES ('1', '1', '2');
INSERT INTO `sys_role_permission` VALUES ('2', '1', '3');
INSERT INTO `sys_role_permission` VALUES ('3', '1', '4');
INSERT INTO `sys_role_permission` VALUES ('4', '1', '5');
INSERT INTO `sys_role_permission` VALUES ('5', '1', '6');
INSERT INTO `sys_role_permission` VALUES ('6', '1', '7');
INSERT INTO `sys_role_permission` VALUES ('7', '1', '8');
INSERT INTO `sys_role_permission` VALUES ('8', '1', '9');
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(36) NOT NULL,
  `loginName` varchar(64) DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `userName` varchar(64) DEFAULT NULL COMMENT '真实名字',
  `userType` int(11) DEFAULT '0' COMMENT '0普通用户，1系统管理员(超级用户)',
  `userState` int(11) DEFAULT '0' COMMENT '用户状态：0：创建未认证（比如没有激活，没有输入验证码等） 等待验证的用户，1：正常状态，2：用户被锁定。3：删除',
  `mobile` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `loginCount` int(11) DEFAULT '0' COMMENT '登录次数',
  `updater` varchar(36) DEFAULT NULL COMMENT '编辑者',
  `lastLoginDate` datetime DEFAULT NULL COMMENT '最近登录时间',
  `passUpdateTime` datetime DEFAULT NULL COMMENT '用户密码修改时间',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `roleId` varchar(100) DEFAULT NULL COMMENT '角色ID',
  `pwd` varchar(100) DEFAULT NULL COMMENT '明文',
  `salt` varchar(50) DEFAULT NULL COMMENT '加密密码盐',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1', '1', 'e756d27bdbca9de24d67e424549561f8', '1', 1, 1, '1', '1', 1, '1', '2018-05-15 10:50:00', '2018-05-15 10:50:02', '2018-05-15 10:50:04', '2018-05-15 10:50:06', '1', '1', '1');
INSERT INTO `sys_user` VALUES ('2', 'admin', '9aa75c4d70930277f59d117ce19188b0', '超级管理员', 1, 1, '1', '1@163.com', 1, '1', '2018-07-05 09:07:18', '2018-07-05 09:07:21', '2018-07-05 09:07:23', '2018-07-05 09:07:26', '1', '123456', 'admin');
INSERT INTO `sys_user` VALUES ('e911e78dec0f4332b37c5a7e4b6b2b75', 'test', '9afcd3271d7449cdbb61923c8e89ea91', 'test', 0, 1, '12312312312', '123@123.cn', 0, NULL, NULL, NULL, '2018-07-06 04:44:02', NULL, NULL, '123456', 'test');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_dept`;
CREATE TABLE `sys_user_dept` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `userid` varchar(36) NOT NULL COMMENT '用户ID',
  `deptid` varchar(36) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户部门关联表';

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `userid` varchar(36) NOT NULL COMMENT '用户ID',
  `roleid` varchar(36) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES ('1', '1', '1');
INSERT INTO `sys_user_role` VALUES ('2', '2', '1');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_test
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_test`;
CREATE TABLE `sys_user_test` (
  `id` varchar(36) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_test
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_test` VALUES ('1', '1', 1);
INSERT INTO `sys_user_test` VALUES ('2', '1', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
