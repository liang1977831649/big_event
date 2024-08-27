/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : big_event

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 20/07/2024 12:59:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章标题',
  `content` varchar(10000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章内容',
  `cover_img` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章封面',
  `state` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '草稿' COMMENT '文章状态: 只能是[已发布] 或者 [草稿]',
  `category_id` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '文章分类ID',
  `create_user` int(10) UNSIGNED NOT NULL COMMENT '创建人ID',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_article_category`(`category_id`) USING BTREE,
  INDEX `fk_article_user`(`create_user`) USING BTREE,
  CONSTRAINT `fk_article_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_article_user` FOREIGN KEY (`create_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, '陕西旅游攻略', '兵马俑,华清池,法门寺,华山...爱去哪去哪...', 'https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png', '草稿', 2, 2, '2024-07-11 16:18:48', '2024-07-11 16:18:48');
INSERT INTO `article` VALUES (2, '西安旅游攻略', '.爱去哪去哪...', 'https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png', '草稿', 2, 2, '2024-07-11 17:47:03', '2024-07-11 17:47:03');
INSERT INTO `article` VALUES (4, '北京旅游攻略', '天安门,颐和园,鸟巢,长城...爱去哪去哪...', 'https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png', '已发布', 2, 2, '2024-07-11 17:49:06', '2024-07-11 17:49:06');
INSERT INTO `article` VALUES (5, '广西美食', '螺蛳粉，馍饼', 'https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png', '草稿', 6, 2, '2024-07-11 18:45:11', '2024-07-11 18:45:11');
INSERT INTO `article` VALUES (6, '福建舰', '开往山东沿海', 'https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png', '草稿', 2, 2, '2024-07-11 18:46:11', '2024-07-11 18:46:11');
INSERT INTO `article` VALUES (8, '嫦娥六号', '嫦娥六号31马赫速度回到中国国土，实现实现精准落地', 'https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png', '草稿', 7, 2, '2024-07-12 13:04:45', '2024-07-12 13:04:45');
INSERT INTO `article` VALUES (10, '不弄了你发', '<p>二五反弹核污染分得清</p>', 'https://baise212lz.oss-cn-chengdu.aliyuncs.com/big-event/3b6aa40a8e9c4c9bb610f9710782b668.jpg', '草稿', 6, 2, '2024-07-17 11:40:02', '2024-07-17 11:40:02');
INSERT INTO `article` VALUES (11, 'u的声卡素芬', '<p>trhgrtgfqwefwqefdfq</p>', 'https://baise212lz.oss-cn-chengdu.aliyuncs.com/big-event/ab0adc83828c4debb5781d3e5b7f22c8.jpg', '草稿', 9, 2, '2024-07-17 11:43:45', '2024-07-17 11:43:45');
INSERT INTO `article` VALUES (13, '收到请回答cos你', '<p>fwhfiwhdiqkwhdohew</p>', 'https://baise212lz.oss-cn-chengdu.aliyuncs.com/big-event/7a865bfe98e04e55b350dda180d6d6a4.jpg', '已发布', 1, 2, '2024-07-17 11:53:39', '2024-07-17 11:53:39');
INSERT INTO `article` VALUES (16, '暗恋的臭脚脚', '<p>分为而撒打算</p>', 'https://baise212lz.oss-cn-chengdu.aliyuncs.com/big-event/d86ffe9fc1184abf8881d2f1942ea5da.jpg', '已发布', 9, 2, '2024-07-17 12:01:58', '2024-07-17 12:01:58');
INSERT INTO `article` VALUES (20, '马云卸任阿里巴巴', '<p>马云卸任阿里巴巴</p>', 'https://baise212lz.oss-cn-chengdu.aliyuncs.com/big-event/4c0c120e40cc4958a194ae5fcbd9d28b.jpg', '已发布', 27, 2, '2024-07-17 17:47:25', '2024-07-17 17:47:25');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `category_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称',
  `category_alias` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类别名',
  `create_user` int(10) UNSIGNED NOT NULL COMMENT '创建人ID',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_category_user`(`create_user`) USING BTREE,
  CONSTRAINT `fk_category_user` FOREIGN KEY (`create_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '人文', 'rw', 2, '2024-07-11 13:12:11', '2024-07-11 13:12:11');
INSERT INTO `category` VALUES (2, '军事', 'js', 2, '2024-07-11 13:25:07', '2024-07-11 13:25:07');
INSERT INTO `category` VALUES (6, '旅游', 'ly', 2, '2024-07-11 17:48:49', '2024-07-11 17:48:49');
INSERT INTO `category` VALUES (7, '航天', 'ht', 2, '2024-07-11 18:44:17', '2024-07-11 18:44:17');
INSERT INTO `category` VALUES (9, '文化', 'wh', 2, '2024-07-16 16:22:09', '2024-07-16 16:22:09');
INSERT INTO `category` VALUES (27, '金融', 'jr', 2, '2024-07-17 17:46:58', '2024-07-17 17:46:58');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '昵称',
  `email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '邮箱',
  `user_pic` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '头像',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, 'smith', 'e10adc3949ba59abbe56e057f20f883e', '小白袁', '1977831649@qq.com', 'https://baise212lz.oss-cn-chengdu.aliyuncs.com/big-event/aad8944b64fb46ac97b34c637c4c3441.jpg', '2024-07-10 15:53:49', '2024-07-18 16:03:14');
INSERT INTO `user` VALUES (4, 'mary', 'e10adc3949ba59abbe56e057f20f883e', 'marySSA', 'lankou@qq.com', '', '2024-07-10 16:30:38', '2024-07-10 16:30:38');
INSERT INTO `user` VALUES (7, 'jackson', 'e10adc3949ba59abbe56e057f20f883e', 'jackson2', '1977123@sohu.com', '', '2024-07-11 09:28:29', '2024-07-11 09:42:49');
INSERT INTO `user` VALUES (8, 'doman', 'e10adc3949ba59abbe56e057f20f883e', '', '', '', '2024-07-13 16:21:54', '2024-07-13 16:21:54');
INSERT INTO `user` VALUES (9, 'zhangwuji', 'e10adc3949ba59abbe56e057f20f883e', '', '', '', '2024-07-15 23:14:29', '2024-07-15 23:14:29');
INSERT INTO `user` VALUES (10, 'liushishi', 'e10adc3949ba59abbe56e057f20f883e', '', '', '', '2024-07-16 08:44:42', '2024-07-16 08:44:42');
INSERT INTO `user` VALUES (11, 'jack_where', 'e10adc3949ba59abbe56e057f20f883e', '', '', '', '2024-07-16 08:57:14', '2024-07-16 08:57:14');
INSERT INTO `user` VALUES (12, 'jack-kai', 'e10adc3949ba59abbe56e057f20f883e', '', '', '', '2024-07-16 09:09:12', '2024-07-16 09:09:12');
INSERT INTO `user` VALUES (13, 'smith-son', 'e10adc3949ba59abbe56e057f20f883e', '', '', '', '2024-07-16 09:12:21', '2024-07-16 09:12:21');
INSERT INTO `user` VALUES (14, 'liangzheng', 'e10adc3949ba59abbe56e057f20f883e', '', '', '', '2024-07-16 09:13:20', '2024-07-16 09:13:20');
INSERT INTO `user` VALUES (15, 'liubei', 'e10adc3949ba59abbe56e057f20f883e', '', '', '', '2024-07-16 09:17:34', '2024-07-16 09:17:34');
INSERT INTO `user` VALUES (16, 'zhangquandan', 'e10adc3949ba59abbe56e057f20f883e', '', '', '', '2024-07-16 09:18:13', '2024-07-16 09:18:13');

SET FOREIGN_KEY_CHECKS = 1;
