/*
SQLyog Ultimate v9.20 
MySQL - 5.0.24a-community-nt-log : Database - default_data
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`default_data` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `default_data`;

/*Table structure for table `crm_account` */

DROP TABLE IF EXISTS `crm_account`;

CREATE TABLE `crm_account` (
  `objuid` varchar(32) NOT NULL COMMENT 'objuid',
  `owner` varchar(255) default NULL COMMENT '客户所有者',
  `Rating` varchar(255) default NULL COMMENT '等级(已获得,已获得;有效的,有效的;市场失败,市场失败;项目取消,项目取消;关闭,关闭;)',
  `name` varchar(255) default NULL COMMENT '客户名',
  `phone` varchar(255) default NULL COMMENT '电话',
  `location` varchar(255) default NULL COMMENT '客户所在地',
  `fax` varchar(255) default NULL COMMENT '传真',
  `Parent_Account` varchar(255) default NULL COMMENT '父客户',
  `website` varchar(255) default NULL COMMENT '网站',
  `account_number` varchar(255) default NULL COMMENT '客户编号',
  `Ticker_Symbol` varchar(255) default NULL COMMENT '股票代码',
  `Account_Type` varchar(255) default NULL COMMENT '客户类型(分析师,分析师;竞争者,竞争者;客户,客户;经销商,经销商;集成商,集成商;投资者,投资者;其它,其它;合作伙伴,合作伙伴;大众媒体,大众媒体;潜在客户,潜在客户;分销商,分销商;供应商,供应商;供货商,供货商;)',
  `Ownership` varchar(255) default NULL COMMENT '公司属性(其它,其它;私有,私有;公共,公共;附属,附属;)',
  `Industry` varchar(255) default NULL COMMENT '行业(应用服务提供商,应用服务提供商;数据/电信/OEM,数据/电信/OEM;企业资源管理,企业资源管理;政府/军队,政府/军队;大企业,大企业;管理软件提供商,管理软件提供商;MSP(管理服务提供商),MSP(管理服务提供商);网络设备 (企业),网络设备 (企业);非管理软件提供商,非管理软件提供商;光网络,光网络;服务提供商,服务提供商;中小企业,中小企业;存储设备,存储设备;存储服务提供商,存储服务提供商;系统集成,系统集成;无线企业,无线企业;)',
  `Employees` varchar(255) default NULL COMMENT '员工数',
  `Annual Revenue` varchar(255) default NULL COMMENT '年收入',
  `SIC_Code` int(11) default NULL COMMENT 'SIC代码',
  `Billing_country` varchar(255) default NULL COMMENT '开单地址-国家/地区',
  `Billing_province` varchar(255) default NULL COMMENT '开单地址-省/市',
  `Billing_city` varchar(255) default NULL COMMENT '开单地址-城市',
  `Billing_street` varchar(255) default NULL COMMENT '开单地址-详细地址',
  `Billing_zipcode` int(11) default NULL COMMENT '开单地址-邮编',
  `Shipping_country` varchar(255) default NULL COMMENT '发货地址-国家/地区',
  `Shipping_province` varchar(255) default NULL COMMENT '发货地址-省/市',
  `Shipping_city` varchar(255) default NULL COMMENT '发货地址-城市',
  `Shipping_street` varchar(255) default NULL COMMENT '发货地址-详细地址',
  `Shipping_zipcode` int(11) default NULL COMMENT '发货地址-邮编',
  `description` text COMMENT '描述',
  `writer` varchar(255) default NULL COMMENT '录入人',
  `writedate` datetime default NULL COMMENT '录入时间',
  `modifier` varchar(255) default NULL COMMENT '修改人',
  `modificationDate` datetime default NULL COMMENT '最后修改时间',
  PRIMARY KEY  (`objuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='客户信息';

/*Table structure for table `crm_contact` */

DROP TABLE IF EXISTS `crm_contact`;

CREATE TABLE `crm_contact` (
  `objuid` varchar(32) NOT NULL COMMENT 'objuid',
  `owner` varchar(255) default NULL COMMENT '联系人所有者',
  `source` varchar(255) default NULL COMMENT '线索来源(广告,广告;推销电话,推销电话;员工介绍,员工介绍;外部介绍,外部介绍;在线商场,在线商场;合作伙伴,合作伙伴;公开媒介,公开媒介;销售邮件,销售邮件;合作伙伴研讨会,合作伙伴研讨会;内部研讨会,内部研讨会;交易会,交易会;Web下载,Web下载;Web调研,Web调研;Web服务支持,Web服务支持;Web邮件,Web邮件;聊天,聊天)',
  `name` varchar(255) default NULL COMMENT '姓名',
  `account_uid` varchar(255) default NULL COMMENT '客户名',
  `dept` varchar(255) default NULL COMMENT '部门',
  `mail` varchar(255) default NULL COMMENT '邮箱',
  `homephone` varchar(255) default NULL COMMENT '住宅电话',
  `position` varchar(255) default NULL COMMENT '职位',
  `fax` varchar(255) default NULL COMMENT '传真',
  `telephone` varchar(255) default NULL COMMENT '电话',
  `otherphone` varchar(255) default NULL COMMENT '其它电话',
  `birthday` date default NULL COMMENT '生日',
  `mobilephone` varchar(255) default NULL COMMENT '手机',
  `assistant` varchar(255) default NULL COMMENT '助理',
  `assistantphone` varchar(255) default NULL COMMENT '助理电话',
  `nomarketing` varchar(1) default NULL COMMENT '不发营销邮件',
  `standby_mail` varchar(255) default NULL COMMENT '备用邮箱',
  `report` varchar(255) default NULL COMMENT '汇报对象',
  `QQ` varchar(255) default NULL COMMENT 'QQ',
  `SkypeID` varchar(255) default NULL COMMENT 'SkypeID',
  `goods_country` varchar(255) default NULL COMMENT '邮寄地址-国家/地区',
  `goods_province` varchar(255) default NULL COMMENT '邮寄地址-省/市',
  `goods_city` varchar(255) default NULL COMMENT '邮寄地址-城市',
  `goods_street` varchar(255) default NULL COMMENT '邮寄地址-详细地址',
  `goods_zipcode` int(11) default NULL COMMENT '邮寄地址-邮编',
  `other_country` varchar(255) default NULL COMMENT '其它国家/地区',
  `other_province` varchar(255) default NULL COMMENT '其它省/市',
  `other_city` varchar(255) default NULL COMMENT '其它城市',
  `other_street` varchar(255) default NULL COMMENT '其它详细地址',
  `other_zipcode` int(11) default NULL COMMENT '其它邮编',
  `description` text COMMENT '描述',
  `writer` varchar(255) default NULL COMMENT '录入人',
  `writedate` datetime default NULL COMMENT '录入时间',
  `modifier` varchar(255) default NULL COMMENT '修改人',
  `modificationDate` datetime default NULL COMMENT '最后修改时间',
  PRIMARY KEY  (`objuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='联系人信息';

/*Data for the table `crm_contact` */
