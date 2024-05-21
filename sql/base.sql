/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(32) DEFAULT NULL COMMENT '公司编码',
  `name` varchar(128) DEFAULT NULL COMMENT '公司名称',
  `contact_user` varchar(32) DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(32) DEFAULT NULL COMMENT '联系人电话',
  `email` varchar(64) DEFAULT NULL COMMENT '联系人邮箱',
  `post_code` varchar(16) DEFAULT NULL COMMENT '邮编',
  `province` varchar(64) DEFAULT NULL COMMENT '省',
  `city` varchar(64) DEFAULT NULL COMMENT '市',
  `county` varchar(64) DEFAULT NULL COMMENT '区/县',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '上级公司',
  `parent_ids` varchar(255) DEFAULT NULL COMMENT '祖级列表',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `company_pk` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='公司表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES ('E00000001','E00000001','测试','测试',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,101,'2023-06-30 11:28:58',0,'root','root');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gen_table`
--

DROP TABLE IF EXISTS `gen_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gen_table` (
  `table_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='代码生成业务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_table`
--

LOCK TABLES `gen_table` WRITE;
/*!40000 ALTER TABLE `gen_table` DISABLE KEYS */;
INSERT INTO `gen_table` VALUES (46,'wms_breakage_doc','报损单表',NULL,NULL,'WmsBreakageDoc','crud','com.ruoyi.project.warehouse','BreakageDoc','BreakageDoc','报损单','zhangyongheng','0','/','{}','admin','2023-05-20 14:07:07','','2023-05-20 14:10:21',NULL),(47,'wms_breakage_doc_detail','报损物料明细表',NULL,NULL,'WmsBreakageDocDetail','crud','com.ruoyi.project.warehouse','BreakageDocDetail','BreakageDocDetail','报损物料明细','zhangyongheng','0','/','{}','admin','2023-05-20 14:07:08','','2023-05-20 14:11:32',NULL),(48,'wms_inventory_detail','盘点详情表',NULL,NULL,'WmsInventoryDetail','crud','com.ruoyi.project.warehouse','inventoryDetail','inventoryDetail','盘点详情','zhangyongheng','0','/','{}','admin','2023-05-20 14:07:09','','2023-05-21 15:20:55',NULL),(49,'wms_inventory_plan','盘点计划表',NULL,NULL,'WmsInventoryPlan','crud','com.ruoyi.project.warehouse','inventoryPlan','inventoryPlan','盘点计划','zhangyongheng','0','/','{}','admin','2023-05-20 14:07:10','','2023-05-21 15:21:28',NULL),(50,'wms_month_check_out','月结表',NULL,NULL,'WmsMonthCheckOut','crud','com.ruoyi.project.warehouse','warehouse','monthCheckOut','月结','gaomian','0','/','{}','admin','2023-05-27 17:37:31','','2023-05-27 17:55:00',NULL),(51,'wms_month_check_out_detail','月结详情表',NULL,NULL,'WmsMonthCheckOutDetail','crud','com.ruoyi.project.system','system','detail','月结详情','ruoyi','0','/',NULL,'admin','2023-05-27 17:37:40','',NULL,NULL);
/*!40000 ALTER TABLE `gen_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gen_table_column`
--

DROP TABLE IF EXISTS `gen_table_column`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gen_table_column` (
  `column_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) DEFAULT '' COMMENT '字典类型',
  `sort` int DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=973 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='代码生成业务表字段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_blob_triggers`
--

DROP TABLE IF EXISTS `qrtz_blob_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_blob_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `blob_data` blob COMMENT '存放持久化Trigger对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='Blob类型的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_blob_triggers`
--

LOCK TABLES `qrtz_blob_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_blob_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_blob_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_calendars`
--

DROP TABLE IF EXISTS `qrtz_calendars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_calendars` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `calendar_name` varchar(200) NOT NULL COMMENT '日历名称',
  `calendar` blob NOT NULL COMMENT '存放持久化calendar对象',
  PRIMARY KEY (`sched_name`,`calendar_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='日历信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_calendars`
--

LOCK TABLES `qrtz_calendars` WRITE;
/*!40000 ALTER TABLE `qrtz_calendars` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_calendars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_cron_triggers`
--

DROP TABLE IF EXISTS `qrtz_cron_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_cron_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `cron_expression` varchar(200) NOT NULL COMMENT 'cron表达式',
  `time_zone_id` varchar(80) DEFAULT NULL COMMENT '时区',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='Cron类型的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_cron_triggers`
--

LOCK TABLES `qrtz_cron_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_cron_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_cron_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_fired_triggers`
--

DROP TABLE IF EXISTS `qrtz_fired_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_fired_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `entry_id` varchar(95) NOT NULL COMMENT '调度器实例id',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `instance_name` varchar(200) NOT NULL COMMENT '调度器实例名',
  `fired_time` bigint NOT NULL COMMENT '触发的时间',
  `sched_time` bigint NOT NULL COMMENT '定时器制定的时间',
  `priority` int NOT NULL COMMENT '优先级',
  `state` varchar(16) NOT NULL COMMENT '状态',
  `job_name` varchar(200) DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(200) DEFAULT NULL COMMENT '任务组名',
  `is_nonconcurrent` varchar(1) DEFAULT NULL COMMENT '是否并发',
  `requests_recovery` varchar(1) DEFAULT NULL COMMENT '是否接受恢复执行',
  PRIMARY KEY (`sched_name`,`entry_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='已触发的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_fired_triggers`
--

LOCK TABLES `qrtz_fired_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_fired_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_fired_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_job_details`
--

DROP TABLE IF EXISTS `qrtz_job_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_job_details` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `job_name` varchar(200) NOT NULL COMMENT '任务名称',
  `job_group` varchar(200) NOT NULL COMMENT '任务组名',
  `description` varchar(250) DEFAULT NULL COMMENT '相关介绍',
  `job_class_name` varchar(250) NOT NULL COMMENT '执行任务类名称',
  `is_durable` varchar(1) NOT NULL COMMENT '是否持久化',
  `is_nonconcurrent` varchar(1) NOT NULL COMMENT '是否并发',
  `is_update_data` varchar(1) NOT NULL COMMENT '是否更新数据',
  `requests_recovery` varchar(1) NOT NULL COMMENT '是否接受恢复执行',
  `job_data` blob COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='任务详细信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_job_details`
--

LOCK TABLES `qrtz_job_details` WRITE;
/*!40000 ALTER TABLE `qrtz_job_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_job_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_locks`
--

DROP TABLE IF EXISTS `qrtz_locks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_locks` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `lock_name` varchar(40) NOT NULL COMMENT '悲观锁名称',
  PRIMARY KEY (`sched_name`,`lock_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='存储的悲观锁信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_locks`
--

LOCK TABLES `qrtz_locks` WRITE;
/*!40000 ALTER TABLE `qrtz_locks` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_locks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_paused_trigger_grps`
--

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  PRIMARY KEY (`sched_name`,`trigger_group`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='暂停的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_paused_trigger_grps`
--

LOCK TABLES `qrtz_paused_trigger_grps` WRITE;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_scheduler_state`
--

DROP TABLE IF EXISTS `qrtz_scheduler_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_scheduler_state` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `instance_name` varchar(200) NOT NULL COMMENT '实例名称',
  `last_checkin_time` bigint NOT NULL COMMENT '上次检查时间',
  `checkin_interval` bigint NOT NULL COMMENT '检查间隔时间',
  PRIMARY KEY (`sched_name`,`instance_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='调度器状态表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_scheduler_state`
--

LOCK TABLES `qrtz_scheduler_state` WRITE;
/*!40000 ALTER TABLE `qrtz_scheduler_state` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_scheduler_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_simple_triggers`
--

DROP TABLE IF EXISTS `qrtz_simple_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_simple_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `repeat_count` bigint NOT NULL COMMENT '重复的次数统计',
  `repeat_interval` bigint NOT NULL COMMENT '重复的间隔时间',
  `times_triggered` bigint NOT NULL COMMENT '已经触发的次数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='简单触发器的信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_simple_triggers`
--

LOCK TABLES `qrtz_simple_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simple_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simple_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_simprop_triggers`
--

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_simprop_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `str_prop_1` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
  `str_prop_2` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
  `str_prop_3` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
  `int_prop_1` int DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
  `int_prop_2` int DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
  `long_prop_1` bigint DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
  `long_prop_2` bigint DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
  `dec_prop_1` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
  `dec_prop_2` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
  `bool_prop_1` varchar(1) DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
  `bool_prop_2` varchar(1) DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='同步机制的行锁表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_simprop_triggers`
--

LOCK TABLES `qrtz_simprop_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_triggers`
--

DROP TABLE IF EXISTS `qrtz_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT '触发器的名字',
  `trigger_group` varchar(200) NOT NULL COMMENT '触发器所属组的名字',
  `job_name` varchar(200) NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
  `job_group` varchar(200) NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
  `description` varchar(250) DEFAULT NULL COMMENT '相关介绍',
  `next_fire_time` bigint DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
  `prev_fire_time` bigint DEFAULT NULL COMMENT '下一次触发时间（默认为-1表示不触发）',
  `priority` int DEFAULT NULL COMMENT '优先级',
  `trigger_state` varchar(16) NOT NULL COMMENT '触发器状态',
  `trigger_type` varchar(8) NOT NULL COMMENT '触发器的类型',
  `start_time` bigint NOT NULL COMMENT '开始时间',
  `end_time` bigint DEFAULT NULL COMMENT '结束时间',
  `calendar_name` varchar(200) DEFAULT NULL COMMENT '日程表名称',
  `misfire_instr` smallint DEFAULT NULL COMMENT '补偿执行的策略',
  `job_data` blob COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`) USING BTREE,
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='触发器详细信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_triggers`
--

LOCK TABLES `qrtz_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_wh`
--

DROP TABLE IF EXISTS `role_wh`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_wh` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `wh_id` varchar(32) DEFAULT NULL COMMENT '仓库ID',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='角色仓库表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_wh`
--

LOCK TABLES `role_wh` WRITE;
/*!40000 ALTER TABLE `role_wh` DISABLE KEYS */;
INSERT INTO `role_wh` VALUES ('13cf432f53154e8b95852eea92334574','CPC0001',111,'E00000001'),('2e2e0504fcbf4163baa188d9a61be4c7','THC0001',111,'E00000001'),('43ccccf2e54e4e1ca7a060f7cde20345','KHC001',111,'E00000001'),('4a54c07c8b974496ae62e1e3f3849023','BCP0001',111,'E00000001'),('5c88c16bba91465f919b639b8f9a15a3','SJC0001',111,'E00000001'),('828af4b0bdf74faf99fe175d8889eb13','ZJBC0001',111,'E00000001'),('bcc824eb2b374077875b395408a18969','QTC0001',111,'E00000001'),('fcdd496a6cbc4bb3a6d0bae388d691be','c27effa93d264db5bb7aaba13b2c2964',111,'E00000001');
/*!40000 ALTER TABLE `role_wh` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storage_unit`
--

DROP TABLE IF EXISTS `storage_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `storage_unit` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
  `code` varchar(32) DEFAULT NULL COMMENT '编码',
  `storage_unit_type_code` varchar(32) DEFAULT NULL COMMENT '存储单元类型编码',
  `storage_unit_type_name` varchar(64) DEFAULT NULL COMMENT '存储单元类型名称',
  `warehouse_id` varchar(32) DEFAULT NULL COMMENT '仓库ID',
  `thermosphere` varchar(16) DEFAULT NULL COMMENT '温层',
  `description` varchar(256) DEFAULT NULL COMMENT '描述',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父级ID',
  `parent_ids` varchar(512) DEFAULT NULL COMMENT '单元路径',
  `is_smallest_storage_unit` tinyint(1) DEFAULT '0' COMMENT '是否最小存储单元',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  `create_user_name` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_by` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_name` varchar(32) DEFAULT NULL COMMENT '修改人名称',
  `update_by` bigint DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='存储单元';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `storage_unit_type`
--

DROP TABLE IF EXISTS `storage_unit_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `storage_unit_type` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
  `code` varchar(32) DEFAULT NULL COMMENT '编码',
  `description` varchar(256) DEFAULT NULL COMMENT '描述',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父级ID',
  `parent_ids` varchar(512) DEFAULT NULL COMMENT '单元路径',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  `create_user_name` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_by` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_name` varchar(32) DEFAULT NULL COMMENT '修改人名称',
  `update_by` bigint DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `electronic_code_type` int DEFAULT NULL COMMENT '电子编码类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='存储单元类型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storage_unit_type`
--

LOCK TABLES `storage_unit_type` WRITE;
/*!40000 ALTER TABLE `storage_unit_type` DISABLE KEYS */;
INSERT INTO `storage_unit_type` VALUES ('16745e8ad9a44414a55d8483dd98bd57','库区','KUQU','','24c8a4d7e39e4c1bb48a8506f551528b','root,24c8a4d7e39e4c1bb48a8506f551528b',0,'admin',1,'2023-03-30 11:31:11','Test',100,'2023-05-08 17:00:33',1),('24c8a4d7e39e4c1bb48a8506f551528b','库位','KUWEI',NULL,'root','root',0,'admin',1,'2023-03-30 11:30:57','admin',1,'2023-04-01 18:04:14',2);
/*!40000 ALTER TABLE `storage_unit_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_config` (
  `config_id` int NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='参数配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_config`
--

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` VALUES (1,'主框架页-默认皮肤样式名称','sys.index.skinName','skin-blue','Y','admin','2023-03-26 18:55:59','',NULL,'蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow'),(2,'用户管理-账号初始密码','sys.user.initPassword','123456','Y','admin','2023-03-26 18:55:59','',NULL,'初始化密码 123456'),(3,'主框架页-侧边栏主题','sys.index.sideTheme','theme-dark','Y','admin','2023-03-26 18:55:59','',NULL,'深色主题theme-dark，浅色主题theme-light'),(4,'账号自助-验证码开关','sys.account.captchaEnabled','false','Y','admin','2023-03-26 18:55:59','admin','2023-04-28 09:13:48','是否开启验证码功能（true开启，false关闭）'),(5,'账号自助-是否开启用户注册功能','sys.account.registerUser','false','Y','admin','2023-03-26 18:55:59','',NULL,'是否开启注册用户功能（true开启，false关闭）');
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept` (
  `dept_id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` char(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=210 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` VALUES (100,0,'0','测试',0,'','15888888888','ry@qq.com','0','0','admin','2023-03-26 18:55:59','admin','2023-05-06 09:37:05'),(101,100,'0,100','总经办',1,'','15888888888','ry@qq.com','0','0','admin','2023-03-26 18:55:59','admin','2023-05-06 09:37:38'),(200,100,'0,100','财务部',2,NULL,NULL,NULL,'0','0','admin','2023-05-06 09:39:29','',NULL),(201,100,'0,100','质量部',3,NULL,NULL,NULL,'0','0','admin','2023-05-06 09:39:38','',NULL),(202,100,'0,100','设备部',4,NULL,NULL,NULL,'0','0','admin','2023-05-06 09:39:52','',NULL),(203,100,'0,100','研发部',5,NULL,NULL,NULL,'0','0','admin','2023-05-06 09:40:01','',NULL),(204,100,'0,100','生产部',6,NULL,NULL,NULL,'0','0','admin','2023-05-06 09:40:11','',NULL),(205,100,'0,100','综合部',7,NULL,NULL,NULL,'0','0','admin','2023-05-06 09:40:21','',NULL),(206,100,'0,100','IT部',8,NULL,NULL,NULL,'0','0','admin','2023-05-06 09:40:34','',NULL),(207,100,'0,100','采购部',9,NULL,NULL,NULL,'0','0','admin','2023-05-06 09:40:44','admin','2023-05-06 09:41:05'),(208,100,'0,100','人事部',10,NULL,NULL,NULL,'0','0','admin','2023-05-06 09:40:58','',NULL),(209,100,'0,100','市场部',11,NULL,NULL,NULL,'0','0','admin','2023-05-06 09:41:15','',NULL);
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_data`
--

DROP TABLE IF EXISTS `sys_dict_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_data` (
  `dict_code` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=333 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='字典数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_data`
--

LOCK TABLES `sys_dict_data` WRITE;
/*!40000 ALTER TABLE `sys_dict_data` DISABLE KEYS */;
INSERT INTO `sys_dict_data` VALUES (1,1,'男','0','sys_user_sex','','','Y','0','admin','2023-03-26 18:55:59','',NULL,'性别男'),(2,2,'女','1','sys_user_sex','','','N','0','admin','2023-03-26 18:55:59','',NULL,'性别女'),(3,3,'未知','2','sys_user_sex','','','N','0','admin','2023-03-26 18:55:59','',NULL,'性别未知'),(4,1,'显示','0','sys_show_hide','','primary','Y','0','admin','2023-03-26 18:55:59','',NULL,'显示菜单'),(5,2,'隐藏','1','sys_show_hide','','danger','N','0','admin','2023-03-26 18:55:59','',NULL,'隐藏菜单'),(6,1,'正常','0','sys_normal_disable','','primary','Y','0','admin','2023-03-26 18:55:59','',NULL,'正常状态'),(7,2,'停用','1','sys_normal_disable','','danger','N','0','admin','2023-03-26 18:55:59','',NULL,'停用状态'),(8,1,'正常','0','sys_job_status','','primary','Y','0','admin','2023-03-26 18:55:59','',NULL,'正常状态'),(9,2,'暂停','1','sys_job_status','','danger','N','0','admin','2023-03-26 18:55:59','',NULL,'停用状态'),(10,1,'默认','DEFAULT','sys_job_group','','','Y','0','admin','2023-03-26 18:55:59','',NULL,'默认分组'),(11,2,'系统','SYSTEM','sys_job_group','','','N','0','admin','2023-03-26 18:55:59','',NULL,'系统分组'),(12,1,'是','Y','sys_yes_no','','primary','Y','0','admin','2023-03-26 18:55:59','',NULL,'系统默认是'),(13,2,'否','N','sys_yes_no','','danger','N','0','admin','2023-03-26 18:55:59','',NULL,'系统默认否'),(14,1,'通知','1','sys_notice_type','','warning','Y','0','admin','2023-03-26 18:55:59','',NULL,'通知'),(15,2,'公告','2','sys_notice_type','','success','N','0','admin','2023-03-26 18:55:59','',NULL,'公告'),(16,1,'正常','0','sys_notice_status','','primary','Y','0','admin','2023-03-26 18:55:59','',NULL,'正常状态'),(17,2,'关闭','1','sys_notice_status','','danger','N','0','admin','2023-03-26 18:55:59','',NULL,'关闭状态'),(18,99,'其他','0','sys_oper_type','','info','N','0','admin','2023-03-26 18:55:59','',NULL,'其他操作'),(19,1,'新增','1','sys_oper_type','','info','N','0','admin','2023-03-26 18:55:59','',NULL,'新增操作'),(20,2,'修改','2','sys_oper_type','','info','N','0','admin','2023-03-26 18:55:59','',NULL,'修改操作'),(21,3,'删除','3','sys_oper_type','','danger','N','0','admin','2023-03-26 18:55:59','',NULL,'删除操作'),(22,4,'授权','4','sys_oper_type','','primary','N','0','admin','2023-03-26 18:55:59','',NULL,'授权操作'),(23,5,'导出','5','sys_oper_type','','warning','N','0','admin','2023-03-26 18:55:59','',NULL,'导出操作'),(24,6,'导入','6','sys_oper_type','','warning','N','0','admin','2023-03-26 18:55:59','',NULL,'导入操作'),(25,7,'强退','7','sys_oper_type','','danger','N','0','admin','2023-03-26 18:55:59','',NULL,'强退操作'),(26,8,'生成代码','8','sys_oper_type','','warning','N','0','admin','2023-03-26 18:55:59','',NULL,'生成操作'),(27,9,'清空数据','9','sys_oper_type','','danger','N','0','admin','2023-03-26 18:55:59','',NULL,'清空操作'),(28,1,'成功','0','sys_common_status','','primary','N','0','admin','2023-03-26 18:55:59','',NULL,'正常状态'),(29,2,'失败','1','sys_common_status','','danger','N','0','admin','2023-03-26 18:55:59','',NULL,'停用状态'),(100,4,'退货召回仓','4','warehouse_type',NULL,'default','N','0','admin','2023-03-18 14:53:43','admin','2023-05-11 09:38:20',NULL),(101,9,'客户来料仓','9','warehouse_type',NULL,'default','N','0','admin','2023-03-18 14:53:55','',NULL,NULL),(102,7,'研发仓','7','warehouse_type',NULL,'default','N','0','admin','2023-03-18 14:54:03','admin','2023-05-11 09:38:51',NULL),(103,2,'原辅料仓','2','warehouse_type',NULL,'default','N','0','admin','2023-03-18 14:54:12','',NULL,NULL),(104,1,'成品仓','1','warehouse_type',NULL,'default','N','0','admin','2023-03-18 14:54:22','',NULL,NULL),(105,3,'半成品仓','3','warehouse_type',NULL,'default','N','0','admin','2023-03-18 14:54:30','',NULL,NULL),(107,5,'包材仓','5','warehouse_type',NULL,'default','N','0','admin','2023-03-18 14:54:45','',NULL,NULL),(108,4,'冷冻-80℃','LD80','thermosphere',NULL,'default','N','0','admin','2023-03-18 15:50:58','admin','2023-05-10 14:34:14',NULL),(111,1,'常温','CW','thermosphere',NULL,'default','N','0','admin','2023-03-18 15:51:35','admin','2023-05-10 14:33:52',NULL),(113,3,'冷冻-20℃','LD','thermosphere',NULL,'default','N','0','admin','2023-03-18 15:52:02','admin','2023-05-10 14:34:09',NULL),(114,6,'通过','6','wms_location_type',NULL,'default','N','0','admin','2023-03-18 16:43:30','',NULL,NULL),(115,2,'发货','2','wms_location_type',NULL,'default','N','0','admin','2023-03-18 16:43:44','',NULL,NULL),(116,3,'分拣','3','wms_location_type',NULL,'default','N','0','admin','2023-03-18 16:43:51','',NULL,NULL),(117,0,'储存','0','wms_location_type',NULL,'default','N','0','admin','2023-03-18 16:44:02','',NULL,NULL),(118,1,'收货','1','wms_location_type',NULL,'default','N','0','admin','2023-03-18 16:44:10','',NULL,NULL),(119,0,'混放','0','wms_location_mix_rule',NULL,'default','N','0','admin','2023-03-18 16:45:30','',NULL,NULL),(120,1,'不混放','1','wms_location_mix_rule',NULL,'default','N','0','admin','2023-03-18 16:45:37','',NULL,NULL),(121,2,'批次混放','2','wms_location_mix_rule',NULL,'default','N','0','admin','2023-03-18 16:45:42','',NULL,NULL),(122,3,'货品混放','3','wms_location_mix_rule',NULL,'default','N','0','admin','2023-03-18 16:45:48','',NULL,NULL),(123,0,'未锁定','0','wms_location_type_lock_rule',NULL,'default','N','0','admin','2023-03-18 16:50:11','',NULL,NULL),(124,1,'只出不入锁','1','wms_location_type_lock_rule',NULL,'default','N','0','admin','2023-03-18 16:50:19','',NULL,NULL),(125,2,'只入不出锁','2','wms_location_type_lock_rule',NULL,'default','N','0','admin','2023-03-18 16:50:26','',NULL,NULL),(126,3,'出入全锁定','3','wms_location_type_lock_rule',NULL,'default','N','0','admin','2023-03-18 16:50:33','',NULL,NULL),(127,1,'是','1','wms_location_inventory_lock',NULL,'default','N','0','admin','2023-03-18 16:53:23','',NULL,NULL),(128,0,'否','0','wms_location_inventory_lock',NULL,'default','N','0','admin','2023-03-18 16:53:32','',NULL,NULL),(129,0,'蓝牙','1','electronic_code_type',NULL,'default','N','0','admin','2023-04-01 17:53:08','',NULL,NULL),(130,0,'射频','2','electronic_code_type',NULL,'default','N','0','admin','2023-04-01 17:53:29','',NULL,NULL),(131,0,'待激活','0','recive_notice_status','','info','N','0','admin','2023-04-07 11:28:21','admin','2023-04-07 11:32:13',NULL),(132,1,'收货中','1','recive_notice_status',NULL,'primary','N','0','admin','2023-04-07 11:28:33','admin','2023-04-07 11:32:24',NULL),(133,8,'取消','8','recive_notice_status',NULL,'warning','N','0','admin','2023-04-07 11:28:46','admin','2023-04-07 11:32:56',NULL),(134,9,'交接完成','9','recive_notice_status',NULL,'success','N','0','admin','2023-04-07 11:28:57','admin','2023-04-07 11:32:34',NULL),(137,1,'已通知','1','examine_work_notice_status',NULL,'success','N','0','admin','2023-04-07 14:21:57','admin','2023-04-07 17:26:40',NULL),(138,0,'未通知','0','examine_work_notice_status',NULL,'warning','N','0','admin','2023-04-07 14:22:09','admin','2023-04-07 17:26:47',NULL),(139,0,'待收货','0','receipt_status',NULL,'info','N','0','admin','2023-04-10 16:29:59','admin','2023-04-28 17:12:20',NULL),(140,1,'收货中','1','receipt_status',NULL,'primary','N','0','admin','2023-04-10 16:30:24','',NULL,NULL),(141,2,'收货完成','2','receipt_status',NULL,'success','N','0','admin','2023-04-10 16:30:46','admin','2023-04-22 15:36:36',NULL),(142,3,'已取消','3','receipt_status',NULL,'warning','N','0','admin','2023-04-10 16:30:54','',NULL,NULL),(143,0,'待检验','0','recive_item_status',NULL,'primary','N','0','admin','2023-04-10 16:59:35','',NULL,NULL),(144,1,'质检中','1','recive_item_status',NULL,'warning','N','0','admin','2023-04-10 16:59:51','admin','2023-04-10 17:00:43',NULL),(145,2,'合格','2','recive_item_status',NULL,'success','N','0','admin','2023-04-10 17:00:09','admin','2023-04-10 17:00:48',NULL),(147,3,'已收货','3','recive_notice_status',NULL,'success','N','0','admin','2023-04-10 17:18:02','admin','2023-04-10 17:18:26',NULL),(148,0,'进料验收','0','item_type',NULL,'success','N','0','admin','2023-04-10 17:30:00','',NULL,NULL),(149,1,'进货验收','1','item_type',NULL,'primary','N','0','admin','2023-04-10 17:30:10','admin','2023-05-03 10:03:36',NULL),(150,2,'其他','2','item_type',NULL,'warning','N','0','admin','2023-04-10 17:30:18','admin','2023-05-03 10:03:33',NULL),(151,0,'待分配','0','shelf_work_notice_status',NULL,'primary','N','0','admin','2023-04-10 20:23:30','',NULL,NULL),(152,1,'分配中','1','shelf_work_notice_status',NULL,'warning','N','0','admin','2023-04-10 20:23:50','',NULL,NULL),(153,2,'作业完成','2','shelf_work_notice_status',NULL,'success','N','0','admin','2023-04-10 20:24:12','',NULL,NULL),(154,0,'上架作业','0','work_type',NULL,'default','N','0','admin','2023-04-10 20:32:33','',NULL,NULL),(155,1,'拣货作业','1','work_type',NULL,'default','N','0','admin','2023-04-10 20:32:40','',NULL,NULL),(156,0,'正常','0','work_status',NULL,'success','N','0','admin','2023-04-11 16:42:57','',NULL,NULL),(157,1,'作业中','1','work_status',NULL,'warning','N','0','admin','2023-04-11 16:43:06','',NULL,NULL),(158,0,'待激活','0','delivery_notice_status',NULL,'info','N','0','admin','2023-04-14 15:28:31','',NULL,NULL),(159,1,'作业中','1','delivery_notice_status',NULL,'primary','N','0','admin','2023-04-14 15:28:45','admin','2023-04-14 15:35:08',NULL),(160,2,'拣货中','2','delivery_notice_status',NULL,'primary','N','0','admin','2023-04-14 15:31:24','admin','2023-04-14 15:35:02',NULL),(161,3,'拒绝发运','3','delivery_notice_status',NULL,'danger','N','0','admin','2023-04-14 15:33:29','admin','2023-04-14 15:35:24',NULL),(162,4,'同意发运','4','delivery_notice_status',NULL,'success','N','0','admin','2023-04-14 15:33:49','',NULL,NULL),(163,5,'待发运','5','delivery_notice_status',NULL,'primary','N','0','admin','2023-04-14 15:34:08','admin','2023-04-14 15:35:17',NULL),(164,6,'已完成','6','delivery_notice_status',NULL,'success','N','0','admin','2023-04-14 15:35:51','',NULL,NULL),(165,0,'合格','0','storage_status',NULL,'success','N','0','admin','2023-04-15 20:03:41','',NULL,NULL),(166,1,'残次','1','storage_status',NULL,'danger','N','0','admin','2023-04-15 20:03:48','admin','2023-05-13 00:19:52',NULL),(168,0,'同意发运','1','delivery_result',NULL,'success','N','0','admin','2023-04-16 20:55:57','admin','2023-05-09 21:46:48',NULL),(169,1,'拒绝发运','0','delivery_result',NULL,'danger','N','0','admin','2023-04-16 20:56:07','admin','2023-05-09 21:46:53',NULL),(170,0,'待分配','0','pick_work_notice_status',NULL,'info','N','0','admin','2023-04-16 21:37:14','',NULL,NULL),(171,1,'登记中','1','pick_work_notice_status',NULL,'primary','N','0','admin','2023-04-16 21:37:25','admin','2023-05-04 15:05:01',NULL),(172,2,'作业完成','2','pick_work_notice_status',NULL,'success','N','0','admin','2023-04-16 21:37:34','',NULL,NULL),(173,3,'取消','3','pick_work_notice_status',NULL,'warning','N','0','admin','2023-04-16 21:39:40','',NULL,NULL),(175,0,'未关联','0','relate_type',NULL,'info','N','0','admin','2023-04-18 15:23:51','',NULL,NULL),(176,1,'上架中','1','relate_type',NULL,'primary','N','0','admin','2023-04-18 15:24:13','',NULL,NULL),(177,2,'待发货','2','relate_type',NULL,'primary','N','0','admin','2023-04-18 15:24:37','',NULL,NULL),(178,3,'分配中','3','relate_type',NULL,'primary','N','0','admin','2023-04-18 15:24:49','',NULL,NULL),(179,4,'拣货中','4','relate_type',NULL,'primary','N','0','admin','2023-04-18 15:25:02','',NULL,NULL),(180,0,'待发运','0','bill_of_loading_status',NULL,'primary','N','0','admin','2023-04-18 16:21:18','',NULL,NULL),(181,1,'已发运','1','bill_of_loading_status',NULL,'success','N','0','admin','2023-04-18 16:21:26','',NULL,NULL),(182,2,'异常','2','work_status',NULL,'danger','N','0','admin','2023-04-18 18:15:58','',NULL,NULL),(183,2,'退货作业','2','work_type',NULL,'default','N','0','admin','2023-04-21 18:06:42','admin','2023-04-21 18:06:49',NULL),(184,0,'待确认','0','refund_work_status',NULL,'primary','N','0','admin','2023-04-21 21:49:10','',NULL,NULL),(185,1,'已退货','1','refund_work_status',NULL,'success','N','0','admin','2023-04-21 21:49:25','',NULL,NULL),(186,0,'待分配','0','refund_work_notice_status',NULL,'info','N','0','admin','2023-04-21 21:56:58','admin','2023-05-02 11:11:55',NULL),(187,1,'分配中','1','refund_work_notice_status',NULL,'primary','N','0','admin','2023-04-21 21:57:07','admin','2023-05-02 11:12:06',NULL),(188,0,'待检验','0','examine_status',NULL,'info','N','0','admin','2023-04-22 16:18:17','',NULL,NULL),(189,1,'质检中','1','examine_status',NULL,'primary','N','0','admin','2023-04-22 16:18:27','admin','2023-04-22 16:18:55',NULL),(190,2,'合格','2','examine_status',NULL,'success','N','0','admin','2023-04-22 16:18:38','',NULL,NULL),(191,3,'不合格','3','examine_status',NULL,'danger','N','0','admin','2023-04-22 16:18:49','',NULL,NULL),(192,3,'不合格','3','recive_item_status',NULL,'danger','N','0','admin','2023-04-22 21:10:30','',NULL,NULL),(193,0,'待质检','0','receive_item_detail_work_status',NULL,'info','N','0','admin','2023-04-22 21:28:53','admin','2023-04-22 21:31:04',NULL),(194,1,'待上架','1','receive_item_detail_work_status',NULL,'primary','N','0','admin','2023-04-22 21:29:19','admin','2023-04-22 21:31:12',NULL),(195,2,'上架中','2','receive_item_detail_work_status',NULL,'warning','N','0','admin','2023-04-22 21:29:28','',NULL,NULL),(196,3,'已上架','3','receive_item_detail_work_status',NULL,'success','N','0','admin','2023-04-22 21:30:53','',NULL,NULL),(197,4,'质检中','4','receive_item_detail_work_status',NULL,'primary','N','0','admin','2023-04-22 21:38:47','',NULL,NULL),(198,0,'作业中','0','shelf_work_status',NULL,'primary','N','0','admin','2023-04-23 22:01:54','',NULL,NULL),(199,1,'作业完成','1','shelf_work_status',NULL,'success','N','0','admin','2023-04-23 22:02:02','',NULL,NULL),(200,0,'出库','0','storage_in_out_record_type',NULL,'primary','N','0','admin','2023-04-27 22:42:26','admin','2023-05-07 09:43:06',NULL),(201,1,'入库','1','storage_in_out_record_type',NULL,'success','N','0','admin','2023-04-27 22:42:34','admin','2023-05-07 09:43:12',NULL),(202,0,'采购入库','0101','receive_type',NULL,'default','N','0','admin','2023-04-28 11:11:23','',NULL,NULL),(203,0,'产成品入库','0102','receive_type',NULL,'default','N','0','admin','2023-04-28 11:12:00','',NULL,NULL),(204,0,'半成品入库','0103','receive_type',NULL,'default','N','0','admin','2023-04-28 11:12:21','',NULL,NULL),(205,0,'产成品退货入库','0104','receive_type',NULL,'default','N','0','admin','2023-04-28 11:13:19','',NULL,NULL),(206,0,'客户来料入库','0105','receive_type',NULL,'default','N','0','admin','2023-04-28 11:13:30','',NULL,NULL),(207,0,'生产退料入库','0106','receive_type',NULL,'default','N','0','admin','2023-04-28 11:13:38','',NULL,NULL),(208,0,'研发退料入库','0107','receive_type',NULL,'default','N','0','admin','2023-04-28 11:13:50','',NULL,NULL),(209,9,'其他入库','0199','receive_type',NULL,'default','N','0','admin','2023-04-28 11:14:05','admin','2023-07-27 14:21:29',NULL),(210,0,'产成品销售出库','0201','delivery_type',NULL,'default','N','0','admin','2023-04-28 11:16:17','admin','2023-05-05 17:22:33',NULL),(211,0,'生产领料出库','0202','delivery_type',NULL,'default','N','0','admin','2023-04-28 11:16:50','admin','2023-05-05 17:22:36',NULL),(212,0,'研发领料出库','0203','delivery_type',NULL,'default','N','0','admin','2023-04-28 11:16:58','admin','2023-05-05 17:22:39',NULL),(213,0,'原辅料检验留样出库','0204','delivery_type',NULL,'default','N','0','admin','2023-04-28 11:17:05','admin','2023-05-05 17:22:42',NULL),(214,0,'质检耗材出库','0205','delivery_type',NULL,'default','N','0','admin','2023-04-28 11:17:14','admin','2023-05-05 17:22:45',NULL),(215,0,'产成品留样出库','0206','delivery_type',NULL,'default','N','0','admin','2023-04-28 11:17:57','admin','2023-05-05 17:22:49',NULL),(216,0,'设备耗材出库','0207','delivery_type',NULL,'default','N','0','admin','2023-04-28 11:18:03','admin','2023-05-05 17:22:52',NULL),(217,0,'半成品领料出库','0208','delivery_type',NULL,'default','N','0','admin','2023-04-28 11:18:10','admin','2023-05-05 17:22:54',NULL),(218,0,'原辅料保质期到期报废出库','0209','delivery_type',NULL,'default','N','0','admin','2023-04-28 11:18:17','admin','2023-05-05 17:22:57',NULL),(219,0,'产成品保质期到期出库','0210','delivery_type',NULL,'default','N','0','admin','2023-04-28 11:18:31','admin','2023-05-05 17:23:00',NULL),(220,0,'退货产成品报废出库','0211','delivery_type',NULL,'default','N','0','admin','2023-04-28 11:18:39','admin','2023-05-05 17:23:05',NULL),(221,0,'原辅料临期复检报废出库','0212','delivery_type',NULL,'default','N','0','admin','2023-04-28 11:18:54','admin','2023-05-05 17:23:08',NULL),(222,0,'产成品领料出库','0213','delivery_type',NULL,'default','N','0','admin','2023-04-28 11:20:11','admin','2023-05-05 17:23:10',NULL),(223,0,'原辅料残次品报废出库','0214','delivery_type',NULL,'default','N','0','admin','2023-04-28 11:20:23','admin','2023-05-05 17:23:14',NULL),(224,0,'产成品残次品报废出库','0215','delivery_type',NULL,'default','N','0','admin','2023-04-28 11:20:32','admin','2023-05-05 17:23:17',NULL),(225,0,'行政领料出库','0216','delivery_type',NULL,'default','N','0','admin','2023-04-28 11:20:39','admin','2023-05-05 17:23:22',NULL),(226,0,'销售领料出库','0217','delivery_type',NULL,'default','N','0','admin','2023-04-28 11:20:46','admin','2023-05-05 17:23:26',NULL),(227,0,'其他出库','0299','delivery_type',NULL,'default','N','0','admin','2023-04-28 11:20:52','',NULL,NULL),(228,7,'已取消','7','delivery_notice_status',NULL,'danger','N','0','admin','2023-04-29 19:59:49','',NULL,NULL),(230,0,'销售','sales','belongs_type',NULL,'default','N','0','admin','2023-04-30 17:33:54','',NULL,NULL),(231,0,'采购','purchase','belongs_type',NULL,'default','N','0','admin','2023-04-30 17:34:03','',NULL,NULL),(232,0,'生产','manufacture','belongs_type',NULL,'default','N','0','admin','2023-04-30 17:34:16','',NULL,NULL),(233,0,'库存','warehouse','belongs_type',NULL,'default','N','0','admin','2023-04-30 17:34:26','',NULL,NULL),(234,2,'退货完成','2','refund_work_notice_status',NULL,'success','N','0','admin','2023-05-02 11:11:32','',NULL,NULL),(235,0,'产品销售出库','0201','delivery_check_bill_type',NULL,'default','N','0','admin','2023-05-04 12:09:11','',NULL,NULL),(238,0,'产成品销售','0201','generate_bill_of_loading_business_type',NULL,'default','N','0','admin','2023-05-04 16:29:50','',NULL,NULL),(242,0,'采购','0101','business_type_receive',NULL,'default','N','0','admin','2023-04-28 11:11:23','admin','2023-05-05 17:28:40',NULL),(243,0,'产成品','0102','business_type_receive',NULL,'default','N','0','admin','2023-04-28 11:12:00','admin','2023-05-05 17:29:09',NULL),(244,0,'半成品','0103','business_type_receive',NULL,'default','N','0','admin','2023-04-28 11:12:21','admin','2023-05-05 17:29:12',NULL),(245,0,'产成品退货','0104','business_type_receive',NULL,'default','N','0','admin','2023-04-28 11:13:19','admin','2023-05-05 17:29:17',NULL),(246,0,'客户来料','0105','business_type_receive',NULL,'default','N','0','admin','2023-04-28 11:13:30','admin','2023-05-05 17:29:21',NULL),(247,0,' 生产退料','0106','business_type_receive',NULL,'default','N','0','admin','2023-04-28 11:13:38','admin','2023-05-05 17:29:24',NULL),(248,0,'研发退料','0107','business_type_receive',NULL,'default','N','0','admin','2023-04-28 11:13:50','admin','2023-05-05 17:29:29',NULL),(249,0,'其他入库','0199','business_type_receive',NULL,'default','N','0','admin','2023-04-28 11:14:05','',NULL,NULL),(250,0,'产成品销售','0201','business_type_delivery',NULL,'default','N','0','admin','2023-04-28 11:16:17','',NULL,NULL),(251,0,'生产领料','0202','business_type_delivery',NULL,'default','N','0','admin','2023-04-28 11:16:50','',NULL,NULL),(252,0,'研发领料','0203','business_type_delivery',NULL,'default','N','0','admin','2023-04-28 11:16:58','',NULL,NULL),(253,0,'原辅料检验留样','0204','business_type_delivery',NULL,'default','N','0','admin','2023-04-28 11:17:05','',NULL,NULL),(254,0,'质检耗材','0205','business_type_delivery',NULL,'default','N','0','admin','2023-04-28 11:17:14','',NULL,NULL),(255,0,'产成品留样','0206','business_type_delivery',NULL,'default','N','0','admin','2023-04-28 11:17:57','',NULL,NULL),(256,0,'设备耗材','0207','business_type_delivery',NULL,'default','N','0','admin','2023-04-28 11:18:03','',NULL,NULL),(257,0,'半成品领料','0208','business_type_delivery',NULL,'default','N','0','admin','2023-04-28 11:18:10','',NULL,NULL),(258,0,'原辅料保质期到期报废','0209','business_type_delivery',NULL,'default','N','0','admin','2023-04-28 11:18:17','',NULL,NULL),(259,0,'产成品保质期到期','0210','business_type_delivery',NULL,'default','N','0','admin','2023-04-28 11:18:31','',NULL,NULL),(260,0,'退货产成品报废','0211','business_type_delivery',NULL,'default','N','0','admin','2023-04-28 11:18:39','',NULL,NULL),(261,0,'原辅料临期复检报废','0212','business_type_delivery',NULL,'default','N','0','admin','2023-04-28 11:18:54','',NULL,NULL),(262,0,'产成品领料','0213','business_type_delivery',NULL,'default','N','0','admin','2023-04-28 11:20:11','',NULL,NULL),(263,0,'原辅料残次品报废','0214','business_type_delivery',NULL,'default','N','0','admin','2023-04-28 11:20:23','',NULL,NULL),(264,0,'产成品残次品报废','0215','business_type_delivery',NULL,'default','N','0','admin','2023-04-28 11:20:32','',NULL,NULL),(265,0,'行政领料','0216','business_type_delivery',NULL,'default','N','0','admin','2023-04-28 11:20:39','',NULL,NULL),(266,0,'销售领料','0217','business_type_delivery',NULL,'default','N','0','admin','2023-04-28 11:20:46','',NULL,NULL),(267,0,'其他出库','0299','business_type_delivery',NULL,'default','N','0','admin','2023-04-28 11:20:52','',NULL,NULL),(268,0,'留样退料','010001','business_type_receive',NULL,'default','N','0','admin','2023-04-29 21:44:11','',NULL,NULL),(269,0,'耗材领料','020001','business_type_delivery',NULL,'default','N','0','admin','2023-05-04 21:22:13','',NULL,NULL),(270,0,'销售包材','020002','business_type_delivery',NULL,'default','N','0','admin','2023-05-05 16:29:50','admin','2023-05-05 17:29:00',NULL),(271,0,'客户来料','020003','business_type_delivery',NULL,'default','N','0','admin','2023-05-05 16:30:13','admin','2023-05-05 17:28:56',NULL),(272,0,'质检领料','020004','business_type_delivery',NULL,'default','N','0','admin','2023-05-05 16:30:37','',NULL,NULL),(273,0,'调拨出库','020005','business_type_delivery',NULL,'default','N','0','admin','2023-05-05 23:02:54','',NULL,NULL),(274,0,'调拨入库','010002','business_type_receive',NULL,'default','N','0','admin','2023-05-05 23:03:00','',NULL,NULL),(277,3,'采购报废','3','item_type',NULL,'warning','N','0','Test','2023-05-08 22:29:34','',NULL,NULL),(278,4,'特采','4','item_type',NULL,'danger','N','0','admin','2023-05-12 20:46:30','admin','2023-05-12 20:46:55',NULL),(279,2,'特采','2','storage_status',NULL,'primary','N','0','admin','2023-05-12 20:47:14','admin','2023-05-13 00:19:46',NULL),(282,0,'客户来料出库','020003','delivery_type',NULL,'default','N','0','admin','2023-05-18 22:10:18','admin','2023-05-18 22:11:03',NULL),(283,0,'原辅料保质期到期报废出库','0209','breakage_doc_type',NULL,'default','N','0','admin','2023-05-20 16:49:35','admin','2023-05-26 16:35:14',NULL),(284,0,'产成品保质期到期出库','0210','breakage_doc_type',NULL,'default','N','0','admin','2023-05-20 16:49:43','admin','2023-05-26 16:35:19',NULL),(285,0,'退货产成品报废出库','0211','breakage_doc_type',NULL,'default','N','0','admin','2023-05-20 16:49:52','admin','2023-05-26 16:35:22',NULL),(286,0,'原辅料临期复检报废出库','0212','breakage_doc_type',NULL,'default','N','0','admin','2023-05-20 16:50:07','admin','2023-05-26 16:35:26',NULL),(287,0,'原辅料残次品报废出库','0214','breakage_doc_type',NULL,'default','N','0','admin','2023-05-20 16:50:17','admin','2023-05-26 16:35:29',NULL),(288,0,'产成品残次品报废出库','0215','breakage_doc_type',NULL,'default','N','0','admin','2023-05-20 16:50:24','admin','2023-05-26 16:35:32',NULL),(289,0,'待提交审核','0','breakage_doc_status',NULL,'info','N','0','admin','2023-05-20 17:18:24','',NULL,NULL),(290,0,'审核中','1','breakage_doc_status',NULL,'primary','N','0','admin','2023-05-20 17:18:35','',NULL,NULL),(291,0,'审核通过','2','breakage_doc_status',NULL,'success','N','0','admin','2023-05-20 17:18:50','',NULL,NULL),(292,0,'审核不通过','3','breakage_doc_status',NULL,'danger','N','0','admin','2023-05-20 17:19:07','admin','2023-05-20 17:19:14',NULL),(293,0,'待盘点','0','inventory_plan_status',NULL,'info','N','0','admin','2023-05-21 15:32:33','',NULL,NULL),(294,0,'盘点中','1','inventory_plan_status',NULL,'primary','N','0','admin','2023-05-21 15:32:48','',NULL,NULL),(295,0,'审批中','2','inventory_plan_status',NULL,'warning','N','0','admin','2023-05-21 15:33:01','admin','2023-05-21 15:33:26',NULL),(296,0,'已通过','3','inventory_plan_status',NULL,'success','N','0','admin','2023-05-21 15:33:13','',NULL,NULL),(297,0,'未通过','4','inventory_plan_status',NULL,'danger','N','0','admin','2023-05-21 15:33:20','',NULL,NULL),(298,0,'明盘','0','inventory_method',NULL,'success','N','0','admin','2023-05-21 15:34:12','admin','2023-05-21 15:34:42',NULL),(299,0,'盲盘','1','inventory_method',NULL,'warning','N','0','admin','2023-05-21 15:34:30','admin','2023-05-21 15:34:53',NULL),(300,1,'产成品','1','breakage_doc_item_type',NULL,'primary','N','0','admin','2023-05-26 16:35:59','admin','2023-05-29 15:59:27',NULL),(301,2,'原材料','2','breakage_doc_item_type',NULL,'success','N','0','admin','2023-05-26 16:36:14','admin','2023-05-29 15:59:34',NULL),(302,0,'保质到期','0','scrap_reason',NULL,'default','N','0','admin','2023-05-26 16:37:39','admin','2023-06-07 16:48:45',NULL),(303,1,'物料变质','1','scrap_reason',NULL,'default','N','0','admin','2023-05-26 16:37:50','admin','2023-06-07 16:48:55',NULL),(304,2,'残次品报废','2','scrap_reason',NULL,'default','N','0','admin','2023-05-26 16:37:58','admin','2023-06-07 16:49:08',NULL),(305,4,'其他','4','scrap_reason',NULL,'default','N','0','admin','2023-05-26 16:38:02','admin','2023-06-07 16:49:00',NULL),(306,0,'初始月','2023-01','wms_month_check_out_init',NULL,'default','N','0','admin','2023-05-28 10:56:14','admin','2023-06-13 09:05:02',NULL),(307,0,'盘点中','5','relate_type',NULL,'primary','N','0','admin','2023-05-29 18:25:13','admin','2023-05-29 18:25:22',NULL),(308,0,'报损中','6','relate_type',NULL,'primary','N','0','admin','2023-05-29 18:25:36','',NULL,NULL),(309,4,'取消审批','4','breakage_doc_status',NULL,'danger','N','0','admin','2023-06-05 15:48:50','',NULL,NULL),(310,0,'产品','1','material_type',NULL,'default','N','0','admin','2023-06-05 17:48:45','',NULL,NULL),(311,0,'试剂','2','material_type',NULL,'default','N','0','admin','2023-06-05 17:49:04','',NULL,NULL),(312,0,'中间品','3','material_type',NULL,'default','N','0','admin','2023-06-05 17:49:13','',NULL,NULL),(313,0,'直接包材','4','material_type',NULL,'default','N','0','admin','2023-06-05 17:49:21','',NULL,NULL),(314,0,'间接包材','5','material_type',NULL,'default','N','0','admin','2023-06-05 17:49:31','',NULL,NULL),(315,0,'耗材','6','material_type',NULL,'default','N','0','admin','2023-06-05 17:49:39','',NULL,NULL),(316,0,'客户来料','9','material_type',NULL,'default','N','0','admin','2023-06-05 17:49:46','',NULL,NULL),(317,0,'其他物料','10','material_type',NULL,'default','N','0','admin','2023-06-05 17:49:54','',NULL,NULL),(318,3,'客户退货','3','scrap_reason',NULL,'default','N','0','admin','2023-06-07 16:47:38','admin','2023-06-07 16:49:04',NULL),(319,0,'销售包材出库','020002','delivery_type',NULL,'default','N','0','admin','2023-06-08 09:26:11','',NULL,NULL),(320,0,'耗材出库','020001','delivery_type',NULL,'default','N','0','admin','2023-06-08 09:26:36','',NULL,NULL),(321,0,'质检领料出库','020004','delivery_type',NULL,'default','N','0','admin','2023-06-08 09:26:54','',NULL,NULL),(322,2,'冷藏2-8℃','LC','thermosphere',NULL,'default','N','0','admin','2023-06-19 10:36:11','admin','2023-06-20 23:40:53',NULL),(323,10,'耗材仓','10','warehouse_type',NULL,'default','N','0','admin','2023-06-20 23:37:30','admin','2023-06-20 23:37:39',NULL),(324,0,'研发产品','010003','business_type_receive',NULL,'default','N','0','admin','2023-06-30 00:31:11','admin','2023-06-30 00:34:19',NULL),(325,8,'质量留样退料入库','010001','receive_type',NULL,'default','N','0','admin','2023-07-27 14:20:06','admin','2023-07-27 14:49:13',NULL),(326,1,'研发部','张晗','dept_manager',NULL,'default','N','0','admin','2023-11-27 12:40:00','',NULL,NULL),(327,2,'生产部','王红兵','dept_manager',NULL,'default','N','0','admin','2023-11-27 12:40:14','',NULL,NULL),(328,3,'质量部','张晗','dept_manager',NULL,'default','N','0','admin','2023-11-27 12:40:26','',NULL,NULL),(329,4,'市场部','王杭','dept_manager',NULL,'default','N','0','admin','2023-11-27 12:40:45','',NULL,NULL),(330,5,'财务部','王杭','dept_manager',NULL,'default','N','0','admin','2023-11-27 12:40:56','',NULL,NULL),(331,6,'采购部','苏杭','dept_manager',NULL,'default','N','0','admin','2023-11-27 12:41:11','',NULL,NULL),(332,7,'工程部','王红兵','dept_manager',NULL,'default','N','0','admin','2023-11-27 12:41:30','',NULL,NULL);
/*!40000 ALTER TABLE `sys_dict_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_type`
--

DROP TABLE IF EXISTS `sys_dict_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_type` (
  `dict_id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE KEY `dict_type` (`dict_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='字典类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_type`
--

LOCK TABLES `sys_dict_type` WRITE;
/*!40000 ALTER TABLE `sys_dict_type` DISABLE KEYS */;
INSERT INTO `sys_dict_type` VALUES (1,'用户性别','sys_user_sex','0','admin','2023-03-26 18:55:59','',NULL,'用户性别列表'),(2,'菜单状态','sys_show_hide','0','admin','2023-03-26 18:55:59','',NULL,'菜单状态列表'),(3,'系统开关','sys_normal_disable','0','admin','2023-03-26 18:55:59','',NULL,'系统开关列表'),(4,'任务状态','sys_job_status','0','admin','2023-03-26 18:55:59','',NULL,'任务状态列表'),(5,'任务分组','sys_job_group','0','admin','2023-03-26 18:55:59','',NULL,'任务分组列表'),(6,'系统是否','sys_yes_no','0','admin','2023-03-26 18:55:59','',NULL,'系统是否列表'),(7,'通知类型','sys_notice_type','0','admin','2023-03-26 18:55:59','',NULL,'通知类型列表'),(8,'通知状态','sys_notice_status','0','admin','2023-03-26 18:55:59','',NULL,'通知状态列表'),(9,'操作类型','sys_oper_type','0','admin','2023-03-26 18:55:59','',NULL,'操作类型列表'),(10,'系统状态','sys_common_status','0','admin','2023-03-26 18:55:59','',NULL,'登录状态列表'),(100,'仓库类型','warehouse_type','0','admin','2023-03-18 14:51:12','',NULL,NULL),(101,'温层','thermosphere','0','admin','2023-03-18 15:50:09','admin','2023-03-27 15:31:18',NULL),(102,'库位类型','wms_location_type','0','admin','2023-03-18 16:41:44','',NULL,NULL),(103,'库位混放策略','wms_location_mix_rule','0','admin','2023-03-18 16:45:05','',NULL,NULL),(104,'库位锁定策略','wms_location_type_lock_rule','0','admin','2023-03-18 16:49:56','',NULL,NULL),(105,'库位盘点锁','wms_location_inventory_lock','0','admin','2023-03-18 16:52:53','',NULL,NULL),(106,'电子编码类型','electronic_code_type','0','admin','2023-04-01 17:52:48','',NULL,NULL),(107,'入库通知状态','recive_notice_status','0','admin','2023-04-07 11:28:03','admin','2023-04-10 16:29:15',NULL),(108,'收货类别','receive_type','0','admin','2023-04-07 11:44:19','admin','2023-04-28 11:15:24',NULL),(109,'业务单据类型-出库','business_type_delivery','0','admin','2023-04-07 11:47:01','',NULL,NULL),(110,'质检通知状态','examine_work_notice_status','0','admin','2023-04-07 14:21:28','',NULL,NULL),(111,'收货状态','receipt_status','0','admin','2023-04-10 16:29:44','',NULL,NULL),(112,'收货物料状态','recive_item_status','0','admin','2023-04-10 16:59:07','',NULL,NULL),(113,'货品类型','item_type','0','admin','2023-04-10 17:29:26','',NULL,NULL),(114,'上架通知状态','shelf_work_notice_status','0','admin','2023-04-10 20:23:05','',NULL,NULL),(115,'作业类型','work_type','0','admin','2023-04-10 20:32:24','',NULL,NULL),(116,'作业状态','work_status','0','admin','2023-04-11 16:42:40','',NULL,NULL),(117,'出库通知状态','delivery_notice_status','0','admin','2023-04-14 15:28:12','',NULL,NULL),(118,'库存状态','storage_status','0','admin','2023-04-15 20:03:11','',NULL,NULL),(119,'发运结果','delivery_result','0','admin','2023-04-16 20:55:40','',NULL,NULL),(120,'拣货通知状态','pick_work_notice_status','0','admin','2023-04-16 21:36:37','',NULL,NULL),(121,'关联状态','relate_type','0','admin','2023-04-18 15:23:37','',NULL,NULL),(122,'发运状态','bill_of_loading_status','0','admin','2023-04-18 16:21:06','',NULL,NULL),(123,'退货状态','refund_work_status','0','admin','2023-04-21 21:48:44','',NULL,NULL),(124,'退货作业通知状态','refund_work_notice_status','0','admin','2023-04-21 21:56:21','',NULL,NULL),(125,'质检状态','examine_status','0','admin','2023-04-22 16:17:57','',NULL,NULL),(126,'收货物料作业状态','receive_item_detail_work_status','0','admin','2023-04-22 21:27:16','',NULL,NULL),(127,'上架作业明细状态','shelf_work_status','0','admin','2023-04-23 22:01:21','',NULL,NULL),(128,'出入库记录类型','storage_in_out_record_type','0','admin','2023-04-27 22:42:17','',NULL,NULL),(129,'发货类别','delivery_type','0','admin','2023-04-28 11:15:49','',NULL,NULL),(130,'所属单位类型','belongs_type','0','admin','2023-04-30 17:33:34','',NULL,NULL),(131,'出库检查项单据类型','delivery_check_bill_type','0','admin','2023-05-04 12:08:45','',NULL,NULL),(132,'需要生成发运通知的单据类型','generate_bill_of_loading_business_type','0','admin','2023-05-04 14:54:49','',NULL,NULL),(133,'业务单据类型-入库','business_type_receive','0','admin','2023-05-15 15:34:10','admin',NULL,NULL),(134,'报损类型','breakage_doc_type','0','admin','2023-05-20 16:48:56','',NULL,NULL),(135,'报损单状态','breakage_doc_status','0','admin','2023-05-20 17:18:02','',NULL,NULL),(136,'盘点计划状态','inventory_plan_status','0','admin','2023-05-21 15:32:12','',NULL,NULL),(137,'盘点方式','inventory_method','0','admin','2023-05-21 15:34:01','',NULL,NULL),(138,'报废原因','scrap_reason','0','admin','2023-05-26 16:37:17','',NULL,NULL),(139,'月结初始月份','wms_month_check_out_init','0','admin','2023-05-28 10:55:08','',NULL,NULL),(140,'报损物料类型','breakage_doc_item_type','0','admin','2023-05-29 15:34:14','',NULL,NULL),(141,'物料类型','material_type','0','admin','2023-06-05 17:47:16','',NULL,NULL),(142,'部门主管','dept_manager','0','admin','2023-11-27 12:39:36','',NULL,NULL);
/*!40000 ALTER TABLE `sys_dict_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job`
--

DROP TABLE IF EXISTS `sys_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_job` (
  `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='定时任务调度表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job`
--

LOCK TABLES `sys_job` WRITE;
/*!40000 ALTER TABLE `sys_job` DISABLE KEYS */;
INSERT INTO `sys_job` VALUES (1,'系统默认（无参）','DEFAULT','ryTask.ryNoParams','0/10 * * * * ?','3','1','1','admin','2023-03-26 18:55:59','',NULL,''),(2,'系统默认（有参）','DEFAULT','ryTask.ryParams(\'ry\')','0/15 * * * * ?','3','1','1','admin','2023-03-26 18:55:59','',NULL,''),(3,'系统默认（多参）','DEFAULT','ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)','0/20 * * * * ?','3','1','1','admin','2023-03-26 18:55:59','',NULL,''),(100,'临期检查','SYSTEM','storageTask.earlyWarning','0 0 23 * * ?','3','1','0','admin','2023-04-28 20:30:41','admin','2023-08-31 15:47:56',''),(101,'上下限预警','SYSTEM','storageTask.limitWarning','0 0 23 * * ?','3','1','0','admin','2023-04-28 20:31:22','','2023-05-15 14:20:05','');
/*!40000 ALTER TABLE `sys_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job_log`
--

DROP TABLE IF EXISTS `sys_job_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_job_log` (
  `job_log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) DEFAULT NULL COMMENT '日志信息',
  `status` char(1) DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) DEFAULT '' COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=766 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='定时任务调度日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job_log`
--

LOCK TABLES `sys_job_log` WRITE;
/*!40000 ALTER TABLE `sys_job_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_job_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_logininfor`
--

DROP TABLE IF EXISTS `sys_logininfor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_logininfor` (
  `info_id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` char(1) DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1171 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='系统访问记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_logininfor`
--

LOCK TABLES `sys_logininfor` WRITE;
/*!40000 ALTER TABLE `sys_logininfor` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_logininfor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) DEFAULT NULL COMMENT '路由参数',
  `is_frame` int DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `is_cache` int DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2127 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'系统设置',0,6,'system',NULL,'',1,0,'M','0','0','','system','admin','2023-03-26 18:55:59','admin','2023-03-28 15:34:33','系统管理目录'),(2,'系统监控',0,5,'monitor',NULL,'',1,0,'M','0','0','','monitor','admin','2023-03-26 18:55:59','admin','2023-03-28 15:34:57','系统监控目录'),(100,'用户管理',2000,1,'user','system/user/index','',1,0,'C','0','0','system:user:list','user','admin','2023-03-26 18:55:59','admin','2023-03-27 08:59:53','用户管理菜单'),(101,'角色管理',2000,2,'role','system/role/index','',1,0,'C','0','0','system:role:list','peoples','admin','2023-03-26 18:55:59','admin','2023-03-27 09:00:16','角色管理菜单'),(102,'菜单管理',1,5,'menu','system/menu/index','',1,0,'C','0','0','system:menu:list','tree-table','admin','2023-03-26 18:55:59','admin','2023-03-28 15:39:44','菜单管理菜单'),(103,'部门管理',2000,4,'dept','system/dept/index','',1,0,'C','0','0','system:dept:list','tree','admin','2023-03-26 18:55:59','admin','2023-03-27 08:59:18','部门管理菜单'),(104,'岗位管理',2000,6,'post','system/post/index','',1,0,'C','0','0','system:post:list','post','admin','2023-03-26 18:55:59','admin','2023-03-28 15:34:00','岗位管理菜单'),(105,'字典管理',2006,6,'dict','system/dict/index','',1,0,'C','0','0','system:dict:list','dict','admin','2023-03-26 18:55:59','admin','2023-03-28 15:32:38','字典管理菜单'),(106,'参数设置',2006,7,'config','system/config/index','',1,0,'C','0','0','system:config:list','edit','admin','2023-03-26 18:55:59','admin','2023-03-28 15:32:22','参数设置菜单'),(107,'通知公告',2006,8,'notice','system/notice/index','',1,0,'C','1','0','system:notice:list','message','admin','2023-03-26 18:55:59','admin','2023-03-28 15:32:32','通知公告菜单'),(108,'日志管理',2,9,'log','','',1,0,'M','1','0','','log','admin','2023-03-26 18:55:59','admin','2023-03-28 15:32:47','日志管理菜单'),(109,'在线用户',2,1,'online','monitor/online/index','',1,0,'C','1','0','monitor:online:list','online','admin','2023-03-26 18:55:59','',NULL,'在线用户菜单'),(110,'定时任务',2,2,'job','monitor/job/index','',1,0,'C','0','0','monitor:job:list','job','admin','2023-03-26 18:55:59','',NULL,'定时任务菜单'),(111,'数据监控',2,3,'druid','monitor/druid/index','',1,0,'C','1','0','monitor:druid:list','druid','admin','2023-03-26 18:55:59','',NULL,'数据监控菜单'),(112,'服务监控',2,4,'server','monitor/server/index','',1,0,'C','1','0','monitor:server:list','server','admin','2023-03-26 18:55:59','',NULL,'服务监控菜单'),(113,'缓存监控',2,5,'cache','monitor/cache/index','',1,0,'C','1','0','monitor:cache:list','redis','admin','2023-03-26 18:55:59','',NULL,'缓存监控菜单'),(114,'缓存列表',2,6,'cacheList','monitor/cache/list','',1,0,'C','1','0','monitor:cache:list','redis-list','admin','2023-03-26 18:55:59','',NULL,'缓存列表菜单'),(500,'操作日志',108,1,'operlog','monitor/operlog/index','',1,0,'C','0','0','monitor:operlog:list','form','admin','2023-03-26 18:55:59','',NULL,'操作日志菜单'),(501,'登录日志',108,2,'logininfor','monitor/logininfor/index','',1,0,'C','0','0','monitor:logininfor:list','logininfor','admin','2023-03-26 18:55:59','',NULL,'登录日志菜单'),(1000,'用户查询',100,1,'','','',1,0,'F','0','0','system:user:query','#','admin','2023-03-26 18:55:59','',NULL,''),(1001,'用户新增',100,2,'','','',1,0,'F','0','0','system:user:add','#','admin','2023-03-26 18:55:59','',NULL,''),(1002,'用户修改',100,3,'','','',1,0,'F','0','0','system:user:edit','#','admin','2023-03-26 18:55:59','',NULL,''),(1003,'用户删除',100,4,'','','',1,0,'F','0','0','system:user:remove','#','admin','2023-03-26 18:55:59','',NULL,''),(1004,'用户导出',100,5,'','','',1,0,'F','0','0','system:user:export','#','admin','2023-03-26 18:55:59','',NULL,''),(1005,'用户导入',100,6,'','','',1,0,'F','0','0','system:user:import','#','admin','2023-03-26 18:55:59','',NULL,''),(1006,'重置密码',100,7,'','','',1,0,'F','0','0','system:user:resetPwd','#','admin','2023-03-26 18:55:59','',NULL,''),(1007,'角色查询',101,1,'','','',1,0,'F','0','0','system:role:query','#','admin','2023-03-26 18:55:59','',NULL,''),(1008,'角色新增',101,2,'','','',1,0,'F','0','0','system:role:add','#','admin','2023-03-26 18:55:59','',NULL,''),(1009,'角色修改',101,3,'','','',1,0,'F','0','0','system:role:edit','#','admin','2023-03-26 18:55:59','',NULL,''),(1010,'角色删除',101,4,'','','',1,0,'F','0','0','system:role:remove','#','admin','2023-03-26 18:55:59','',NULL,''),(1011,'角色导出',101,5,'','','',1,0,'F','0','0','system:role:export','#','admin','2023-03-26 18:55:59','',NULL,''),(1012,'菜单查询',102,1,'','','',1,0,'F','0','0','system:menu:query','#','admin','2023-03-26 18:55:59','',NULL,''),(1013,'菜单新增',102,2,'','','',1,0,'F','0','0','system:menu:add','#','admin','2023-03-26 18:55:59','',NULL,''),(1014,'菜单修改',102,3,'','','',1,0,'F','0','0','system:menu:edit','#','admin','2023-03-26 18:55:59','',NULL,''),(1015,'菜单删除',102,4,'','','',1,0,'F','0','0','system:menu:remove','#','admin','2023-03-26 18:55:59','',NULL,''),(1016,'部门查询',103,1,'','','',1,0,'F','0','0','system:dept:query','#','admin','2023-03-26 18:55:59','',NULL,''),(1017,'部门新增',103,2,'','','',1,0,'F','0','0','system:dept:add','#','admin','2023-03-26 18:55:59','',NULL,''),(1018,'部门修改',103,3,'','','',1,0,'F','0','0','system:dept:edit','#','admin','2023-03-26 18:55:59','',NULL,''),(1019,'部门删除',103,4,'','','',1,0,'F','0','0','system:dept:remove','#','admin','2023-03-26 18:55:59','',NULL,''),(1020,'岗位查询',104,1,'','','',1,0,'F','0','0','system:post:query','#','admin','2023-03-26 18:55:59','',NULL,''),(1021,'岗位新增',104,2,'','','',1,0,'F','0','0','system:post:add','#','admin','2023-03-26 18:55:59','',NULL,''),(1022,'岗位修改',104,3,'','','',1,0,'F','0','0','system:post:edit','#','admin','2023-03-26 18:55:59','',NULL,''),(1023,'岗位删除',104,4,'','','',1,0,'F','0','0','system:post:remove','#','admin','2023-03-26 18:55:59','',NULL,''),(1024,'岗位导出',104,5,'','','',1,0,'F','0','0','system:post:export','#','admin','2023-03-26 18:55:59','',NULL,''),(1025,'字典查询',105,1,'#','','',1,0,'F','0','0','system:dict:query','#','admin','2023-03-26 18:55:59','',NULL,''),(1026,'字典新增',105,2,'#','','',1,0,'F','0','0','system:dict:add','#','admin','2023-03-26 18:55:59','',NULL,''),(1027,'字典修改',105,3,'#','','',1,0,'F','0','0','system:dict:edit','#','admin','2023-03-26 18:55:59','',NULL,''),(1028,'字典删除',105,4,'#','','',1,0,'F','0','0','system:dict:remove','#','admin','2023-03-26 18:55:59','',NULL,''),(1029,'字典导出',105,5,'#','','',1,0,'F','0','0','system:dict:export','#','admin','2023-03-26 18:55:59','',NULL,''),(1030,'参数查询',106,1,'#','','',1,0,'F','0','0','system:config:query','#','admin','2023-03-26 18:55:59','',NULL,''),(1031,'参数新增',106,2,'#','','',1,0,'F','0','0','system:config:add','#','admin','2023-03-26 18:55:59','',NULL,''),(1032,'参数修改',106,3,'#','','',1,0,'F','0','0','system:config:edit','#','admin','2023-03-26 18:55:59','',NULL,''),(1033,'参数删除',106,4,'#','','',1,0,'F','0','0','system:config:remove','#','admin','2023-03-26 18:55:59','',NULL,''),(1034,'参数导出',106,5,'#','','',1,0,'F','0','0','system:config:export','#','admin','2023-03-26 18:55:59','',NULL,''),(1035,'公告查询',107,1,'#','','',1,0,'F','0','0','system:notice:query','#','admin','2023-03-26 18:55:59','',NULL,''),(1036,'公告新增',107,2,'#','','',1,0,'F','0','0','system:notice:add','#','admin','2023-03-26 18:55:59','',NULL,''),(1037,'公告修改',107,3,'#','','',1,0,'F','0','0','system:notice:edit','#','admin','2023-03-26 18:55:59','',NULL,''),(1038,'公告删除',107,4,'#','','',1,0,'F','0','0','system:notice:remove','#','admin','2023-03-26 18:55:59','',NULL,''),(1039,'操作查询',500,1,'#','','',1,0,'F','0','0','monitor:operlog:query','#','admin','2023-03-26 18:55:59','',NULL,''),(1040,'操作删除',500,2,'#','','',1,0,'F','0','0','monitor:operlog:remove','#','admin','2023-03-26 18:55:59','',NULL,''),(1041,'日志导出',500,3,'#','','',1,0,'F','0','0','monitor:operlog:export','#','admin','2023-03-26 18:55:59','',NULL,''),(1042,'登录查询',501,1,'#','','',1,0,'F','0','0','monitor:logininfor:query','#','admin','2023-03-26 18:55:59','',NULL,''),(1043,'登录删除',501,2,'#','','',1,0,'F','0','0','monitor:logininfor:remove','#','admin','2023-03-26 18:55:59','',NULL,''),(1044,'日志导出',501,3,'#','','',1,0,'F','0','0','monitor:logininfor:export','#','admin','2023-03-26 18:55:59','',NULL,''),(1045,'账户解锁',501,4,'#','','',1,0,'F','0','0','monitor:logininfor:unlock','#','admin','2023-03-26 18:55:59','',NULL,''),(1046,'在线查询',109,1,'#','','',1,0,'F','0','0','monitor:online:query','#','admin','2023-03-26 18:55:59','',NULL,''),(1047,'批量强退',109,2,'#','','',1,0,'F','0','0','monitor:online:batchLogout','#','admin','2023-03-26 18:55:59','',NULL,''),(1048,'单条强退',109,3,'#','','',1,0,'F','0','0','monitor:online:forceLogout','#','admin','2023-03-26 18:55:59','',NULL,''),(1049,'任务查询',110,1,'#','','',1,0,'F','0','0','monitor:job:query','#','admin','2023-03-26 18:55:59','',NULL,''),(1050,'任务新增',110,2,'#','','',1,0,'F','0','0','monitor:job:add','#','admin','2023-03-26 18:55:59','',NULL,''),(1051,'任务修改',110,3,'#','','',1,0,'F','0','0','monitor:job:edit','#','admin','2023-03-26 18:55:59','',NULL,''),(1052,'任务删除',110,4,'#','','',1,0,'F','0','0','monitor:job:remove','#','admin','2023-03-26 18:55:59','',NULL,''),(1053,'状态修改',110,5,'#','','',1,0,'F','0','0','monitor:job:changeStatus','#','admin','2023-03-26 18:55:59','',NULL,''),(1054,'任务导出',110,6,'#','','',1,0,'F','0','0','monitor:job:export','#','admin','2023-03-26 18:55:59','',NULL,''),(1055,'生成查询',115,1,'#','','',1,0,'F','0','0','tool:gen:query','#','admin','2023-03-26 18:55:59','',NULL,''),(1056,'生成修改',115,2,'#','','',1,0,'F','0','0','tool:gen:edit','#','admin','2023-03-26 18:55:59','',NULL,''),(1057,'生成删除',115,3,'#','','',1,0,'F','0','0','tool:gen:remove','#','admin','2023-03-26 18:55:59','',NULL,''),(1058,'导入代码',115,2,'#','','',1,0,'F','0','0','tool:gen:import','#','admin','2023-03-26 18:55:59','',NULL,''),(1059,'预览代码',115,4,'#','','',1,0,'F','0','0','tool:gen:preview','#','admin','2023-03-26 18:55:59','',NULL,''),(1060,'生成代码',115,5,'#','','',1,0,'F','0','0','tool:gen:code','#','admin','2023-03-26 18:55:59','',NULL,''),(2000,'组织管理',1,2,'organization',NULL,NULL,1,0,'M','0','0','','peoples','admin','2023-03-27 08:58:47','admin','2023-03-28 15:33:01',''),(2001,'公司管理',2000,3,'company','system/company/index',NULL,1,0,'C','0','0','system:company:list','build','admin','2023-03-27 09:02:53','admin','2023-03-27 10:20:18',''),(2002,'仓库管理',2000,5,'warehouse','system/warehouse/index',NULL,1,0,'C','0','0','system:warehouse:list','tab','admin','2023-03-27 10:21:16','admin','2023-03-28 15:29:43',''),(2005,'库房管理',1,3,'warehouseManagement',NULL,NULL,1,0,'M','0','0','','list','admin','2023-03-28 15:29:03','admin','2023-03-28 15:33:26',''),(2006,'参数配置',1,1,'parameterConfiguration',NULL,NULL,1,0,'M','0','0',NULL,'code','admin','2023-03-28 15:32:13','',NULL,''),(2007,'库存管理',0,4,'inventoryManagement',NULL,NULL,1,0,'M','0','0','','inventoryManagement','admin','2023-03-28 15:36:07','admin','2023-03-28 15:53:07',''),(2009,'出库管理',0,2,'outboundManagement',NULL,NULL,1,0,'M','0','0','','depot','admin','2023-03-28 15:37:30','admin','2023-03-28 15:47:12',''),(2010,'入库管理',0,1,'inboundManagement',NULL,NULL,1,0,'M','0','0','','inbound','admin','2023-03-28 15:38:15','admin','2023-03-28 15:51:03',''),(2011,'物料管理',1,4,'materialManagement',NULL,NULL,1,0,'M','0','0','','materialManagement','admin','2023-03-28 15:39:35','admin','2023-03-28 15:56:17',''),(2012,'供应商管理',2011,1,'supplier','system/supplier/index',NULL,1,0,'C','0','0','system:supplier:list','post','admin','2023-03-28 16:19:10','',NULL,''),(2013,'物料主档',2011,2,'item','system/productInfo/index',NULL,1,0,'C','0','0','system:productInfo:list','documentation','admin','2023-03-28 16:55:13','admin','2023-04-11 17:53:20',''),(2014,'存储单元',2005,1,'storageUnit','system/storageUnit/index',NULL,1,0,'C','0','0','system:storageUnit:list','materialManagement','admin','2023-03-29 10:38:34','admin','2023-03-30 09:48:55',''),(2015,'存储单元类型',2005,2,'storageUnitType','system/storageUnitType/index',NULL,1,0,'C','0','0','system:storageUnitType:list','component','admin','2023-03-30 09:48:07','',NULL,''),(2016,'收货管理',2010,1,'receiptManager',NULL,NULL,1,0,'M','0','0',NULL,'receipt','admin','2023-03-31 10:55:27','',NULL,''),(2017,'入库通知',2016,1,'receiveNotice','warehouse/receiveNotice/index',NULL,1,0,'C','0','0','warehouse:receiveNotice:list','notice','admin','2023-03-31 11:03:15','',NULL,''),(2018,'通知历史',2016,2,'receiveNoticeHistory','warehouse/receiveNoticeHistory/index',NULL,1,0,'C','0','0','warehouse:receiveNotice:list','history','admin','2023-04-02 13:35:21','admin','2023-05-08 15:38:54',''),(2019,'上架管理',2010,2,'shelves',NULL,NULL,1,0,'M','0','0','','shelves','admin','2023-04-02 13:46:02','admin','2023-04-02 13:51:02',''),(2020,'上架作业通知',2019,1,'shelfWorkNotice','warehouse/shelfWorkNotice/index',NULL,1,0,'C','0','0','warehouse:shelfWorkNotice:list','notice','admin','2023-04-02 13:48:42','admin','2023-05-08 16:45:22',''),(2021,'上架作业历史',2019,2,'shelfWorkNoticeHistory','warehouse/shelfWorkNoticeHistory/index',NULL,1,0,'C','0','0','warehouse:shelfWorkNoticeHistory:list','history','admin','2023-04-02 13:49:54','',NULL,''),(2022,'作业任务',2010,3,'assignments',NULL,NULL,1,0,'M','0','0',NULL,'job','admin','2023-04-02 14:16:52','',NULL,''),(2023,'库存管理',2007,1,'ItemStorage','warehouse/itemStorage/index',NULL,1,0,'C','0','0','warehouse:itemStorage:list','inventoryManagement','admin','2023-04-02 17:49:45','admin','2023-04-02 17:50:58',''),(2024,'出库通知',2009,1,'outboundNotice',NULL,NULL,1,0,'M','0','0',NULL,'notice','admin','2023-04-04 12:14:45','',NULL,''),(2025,'出库通知',2024,1,'deliveryNotice','warehouse/deliveryNotice/index',NULL,1,0,'C','0','0','warehouse:deliveryNotice:list','notice','admin','2023-04-04 12:16:08','',NULL,''),(2026,'拣货作业',2024,2,'pickWork','warehouse/pickWork/index',NULL,1,0,'C','0','0','warehouse:pickWork:pickWork','job','admin','2023-04-04 16:24:45','',NULL,''),(2027,'任务列表',2022,1,'examineWork','warehouse/examineWork/index',NULL,1,0,'C','0','0','warehouse:examineWork:list','list','admin','2023-04-07 14:20:22','',NULL,''),(2028,'出入库记录',2007,2,'storageInOutRecord','warehouse/storageInOutRecord/index',NULL,1,0,'C','0','0','warehouse:storageInOutRecord:list','log','admin','2023-04-07 14:41:57','admin','2023-04-09 14:46:27',''),(2029,'客户管理',2011,3,'customer','system/customer/index',NULL,1,0,'C','0','0','system:customer:list','peoples','admin','2023-04-11 17:32:09','',NULL,''),(2030,'承运商管理',2011,4,'logistics','system/logistics/index',NULL,1,0,'C','0','0','system:logistics:list','peoples','admin','2023-04-11 17:43:18','',NULL,''),(2031,'出库确认项',2009,3,'deliverySureDict','system/deliverySureDict/index',NULL,1,0,'C','0','0','system:deliverySureDict:list','checkbox','admin','2023-04-12 15:25:25','',NULL,''),(2032,'作业历史',2022,2,'examineWorkHistory','warehouse/examineWorkHistory/index',NULL,1,0,'C','0','0','warehouse:examineWork:list','history','admin','2023-04-13 19:33:40','admin','2023-05-08 15:52:30',''),(2033,'拣货作业历史',2024,4,'pickWorkHistory','warehouse/pickWorkHistory/index',NULL,1,0,'C','0','0','warehosue:pickWorkHistory:list','history','admin','2023-04-16 17:10:28','admin','2023-04-16 17:11:24',''),(2034,'发运管理',2009,2,'shipmentManagement',NULL,NULL,1,0,'M','0','0',NULL,'shelves','admin','2023-04-16 21:11:05','',NULL,''),(2035,'发运通知',2034,0,'billOfLoading','warehouse/billOfLoading/index',NULL,1,0,'C','0','0','warehouse:billOfLoading:list','notice','admin','2023-04-16 21:12:19','',NULL,''),(2036,'出库通知历史',2024,3,'deliveryNoticeHistory','warehouse/deliveryNoticeHistory/index',NULL,1,0,'C','0','0','warehouse:deliveryNoticeHistory:list','history','admin','2023-04-18 14:15:33','',NULL,''),(2037,'发运历史',2034,2,'billOfLoadingHistory','warehouse/billOfLoadingHistory/index',NULL,1,0,'C','0','0','warehouse:billOfLoadingHistory:list','history','admin','2023-04-18 14:37:42','',NULL,''),(2039,'退货作业通知',2040,1,'refundWorkNotice','warehouse/refundWorkNotice/index',NULL,1,0,'C','0','0','','notice','admin','2023-04-21 18:00:12','admin','2023-04-21 21:54:55',''),(2040,'退货管理',2010,4,'refund',NULL,NULL,1,0,'M','0','0','','redis-list','admin','2023-04-21 21:51:19','admin','2023-04-21 21:52:13',''),(2041,'退货作业历史',2040,2,'refundWorkNoticeHistory','warehouse/refundWorkNoticeHistory/index',NULL,1,0,'C','0','0',NULL,'history','admin','2023-04-21 21:54:26','',NULL,''),(2042,'移位日志',2007,3,'displacementLog','warehouse/displacementLog/index',NULL,1,0,'C','0','0','warehouse:displacementLog:list','documentation','admin','2023-04-25 20:54:19','',NULL,''),(2044,'查询',2017,0,'',NULL,NULL,1,0,'F','0','0','warehouse:receiveNotice:query','#','admin','2023-05-08 14:59:57','',NULL,''),(2045,'激活',2017,1,'warehouse:receiveNotice:active',NULL,NULL,1,0,'F','0','0','warehouse:receiveNotice:active','#','admin','2023-05-08 15:00:17','admin','2023-05-08 15:00:26',''),(2046,'反激活',2017,2,'',NULL,NULL,1,0,'F','0','0','warehouse:receiveNotice:inactive','#','admin','2023-05-08 15:00:46','',NULL,''),(2047,'作废',2017,3,'',NULL,NULL,1,0,'F','0','0','warehouse:receiveNotice:invalid','#','admin','2023-05-08 15:01:01','',NULL,''),(2048,'收货确认',2017,4,'',NULL,NULL,1,0,'F','0','0','warehouse:receiveNotice:confirm','#','admin','2023-05-08 15:01:22','admin','2023-05-08 15:13:26',''),(2049,'上架',2017,5,'',NULL,NULL,1,0,'F','0','0','warehouse:receiveNotice:shelves','#','admin','2023-05-08 15:13:38','admin','2023-05-08 15:19:59',''),(2050,'收货',2017,6,'',NULL,NULL,1,0,'F','0','0','warehouse:receiveNotice:receipt','#','admin','2023-05-08 15:13:56','admin','2023-05-08 15:20:29',''),(2051,'详情',2017,0,'',NULL,NULL,1,0,'F','0','0','warehouse:receiveNotice:detail','#','admin','2023-05-08 15:21:05','admin','2023-05-08 15:32:16',''),(2052,'入库单打印',2018,0,'',NULL,NULL,1,0,'F','0','0','warehouse:receiveNotice:inStorageDoc','#','admin','2023-05-08 15:38:08','',NULL,''),(2053,'退料单打印',2018,1,'warehouse:receiveNotice:returnMaterial',NULL,NULL,1,0,'F','0','0','warehouse:receiveNotice:returnMaterial','#','admin','2023-05-08 15:38:27','',NULL,''),(2054,'详情',2020,0,'',NULL,NULL,1,0,'F','0','0','warehouse:shelfWorkNotice:detail','#','admin','2023-05-08 15:40:20','',NULL,''),(2055,'手动作业',2020,1,'',NULL,NULL,1,0,'F','0','0','warehouse:shelfWorkNotice:manualWork','#','admin','2023-05-08 15:40:36','',NULL,''),(2056,'详情',2021,0,'',NULL,NULL,1,0,'F','0','0','warehouse:shelfWorkNotice:detail','#','admin','2023-05-08 15:50:57','admin','2023-05-08 15:51:06',''),(2057,'通知质检',2027,0,'',NULL,NULL,1,0,'F','0','0','warehouse:examineWork:notice','#','admin','2023-05-08 15:52:57','admin','2023-05-08 16:47:50',''),(2058,'上架作业',2032,0,'',NULL,NULL,1,0,'F','0','0','warehouse:examineWork:shelves','#','admin','2023-05-08 15:55:04','admin','2023-05-08 15:58:07',''),(2059,'退货作业',2032,1,'',NULL,NULL,1,0,'F','0','0','warehouse:examineWork:refund','#','admin','2023-05-08 15:55:17','admin','2023-05-08 15:57:29',''),(2060,'填写结论',2032,2,'',NULL,NULL,1,0,'F','0','0','warehouse:examineConclusion:edit','#','admin','2023-05-08 15:55:29','admin','2023-05-08 15:57:14',''),(2061,'打印进料验收单',2032,3,'',NULL,NULL,1,0,'F','0','0','warehouse:examineWork:print','#','admin','2023-05-08 15:55:41','admin','2023-05-08 15:55:47',''),(2062,'退货完成',2039,0,'',NULL,NULL,1,0,'F','0','0','warehouse:refundWorkNotice:returnComplete','#','admin','2023-05-08 15:59:19','admin','2023-05-08 15:59:44',''),(2063,'详情',2039,1,'',NULL,NULL,1,0,'F','0','0','warehouse:refundWorkNotice:detail','#','admin','2023-05-08 16:02:48','admin','2023-05-08 16:03:01',''),(2064,'详情',2041,0,'',NULL,NULL,1,0,'F','0','0','warehouse:refundWorkNoticeHistory:detail','#','admin','2023-05-08 16:03:52','',NULL,''),(2065,'手动分配',2039,1,'',NULL,NULL,1,0,'F','0','0','warehouse:refundWorkNotice:manualWork','#','admin','2023-05-08 16:05:00','',NULL,''),(2066,'新增',2001,0,'',NULL,NULL,1,0,'F','0','0','system:company:add','#','admin','2023-05-08 16:07:24','admin','2023-05-08 16:08:10',''),(2067,'修改',2001,1,'',NULL,NULL,1,0,'F','0','0','system:company:edit','#','admin','2023-05-08 16:07:33','admin','2023-05-08 16:08:18',''),(2068,'删除',2001,2,'',NULL,NULL,1,0,'F','0','0','system:company:remove','#','admin','2023-05-08 16:07:42','admin','2023-05-08 16:08:26',''),(2069,'新增',2002,0,'',NULL,NULL,1,0,'F','0','0','system:warehouse:add','#','admin','2023-05-08 16:09:53','',NULL,''),(2070,'修改',2002,1,'',NULL,NULL,1,0,'F','0','0','system:warehouse:edit','#','admin','2023-05-08 16:10:06','',NULL,''),(2071,'删除',2002,2,'',NULL,NULL,1,0,'F','0','0','system:warehouse:remove','#','admin','2023-05-08 16:10:20','',NULL,''),(2072,'新增',2014,0,'',NULL,NULL,1,0,'F','0','0','system:storageUnit:add','#','admin','2023-05-08 16:11:05','',NULL,''),(2073,'修改',2014,1,'',NULL,NULL,1,0,'F','0','0','system:storageUnit:edit','#','admin','2023-05-08 16:11:22','',NULL,''),(2074,'删除',2014,2,'',NULL,NULL,1,0,'F','0','0','system:storageUnit:remove','#','admin','2023-05-08 16:11:39','',NULL,''),(2075,'新增',2015,0,'',NULL,NULL,1,0,'F','0','0','system:storageUnitType:add','#','admin','2023-05-08 16:12:07','',NULL,''),(2076,'修改',2015,1,'',NULL,NULL,1,0,'F','0','0','system:storageUnitType:edit','#','admin','2023-05-08 16:12:18','',NULL,''),(2077,'删除',2015,2,'',NULL,NULL,1,0,'F','0','0','system:storageUnitType:remove','#','admin','2023-05-08 16:12:32','',NULL,''),(2078,'查看单位换算',2013,0,'',NULL,NULL,1,0,'F','0','0','system:productInfo:query','#','admin','2023-05-08 16:14:48','',NULL,''),(2079,'修改仓库属性',2013,1,'',NULL,NULL,1,0,'F','0','0','system:productInfo:edit','#','admin','2023-05-08 16:15:19','',NULL,''),(2080,'信息调整',2023,0,'',NULL,NULL,1,0,'F','0','0','warehouse:itemStorage:infoEdit','#','admin','2023-05-08 16:16:44','',NULL,''),(2081,'状态调整',2023,1,'',NULL,NULL,1,0,'F','0','0','warehouse:itemStorage:statusEdit','#','admin','2023-05-08 16:16:58','',NULL,''),(2082,'单位换算',2023,2,'',NULL,NULL,1,0,'F','0','0','warehouse:itemStorage:unitConversion','#','admin','2023-05-08 16:17:15','',NULL,''),(2083,'移位',2023,3,'',NULL,NULL,1,0,'F','0','0','warehouse:itemStorageDetail:displacement','#','admin','2023-05-08 16:17:26','',NULL,''),(2084,'调拨',2023,4,'',NULL,NULL,1,0,'F','0','0','warehouse:itemStorageDetail:allocate','#','admin','2023-05-08 16:17:37','',NULL,''),(2085,'新增',2031,0,'',NULL,NULL,1,0,'F','0','0','system:deliverySureDict:add','#','admin','2023-05-08 16:23:37','',NULL,''),(2086,'修改',2031,1,'',NULL,NULL,1,0,'F','0','0','system:deliverySureDict:edit','#','admin','2023-05-08 16:23:52','',NULL,''),(2087,'删除',2031,2,'',NULL,NULL,1,0,'F','0','0','system:deliverySureDict:remove','#','admin','2023-05-08 16:24:08','',NULL,''),(2088,'查看详情',2035,0,'',NULL,NULL,1,0,'F','0','0','warehouse:billOfLoading:detail','#','admin','2023-05-08 16:25:45','',NULL,''),(2089,'发运完成',2035,1,'',NULL,NULL,1,0,'F','0','0','warehouse:billOfLoading:finish','#','admin','2023-05-08 16:26:40','',NULL,''),(2090,'查看详情',2037,1,'',NULL,NULL,1,0,'F','0','0','system:billOfLoadingHistory:detail','#','admin','2023-05-08 16:27:29','',NULL,''),(2091,'查看详情',2033,1,'',NULL,NULL,1,0,'F','0','0','warehouse:pickWorkHistory:detail','#','admin','2023-05-08 16:28:35','',NULL,''),(2092,'作业完成',2026,0,'',NULL,NULL,1,0,'F','0','0','warehouse:pickWork:workFinish','#','admin','2023-05-08 16:29:05','',NULL,''),(2093,'作业取消',2026,1,'',NULL,NULL,1,0,'F','0','0','warehouse:pickWork:workCancel','#','admin','2023-05-08 16:29:25','',NULL,''),(2094,'缺货登记',2026,2,'',NULL,NULL,1,0,'F','0','0','warehouse:pickWork:register','#','admin','2023-05-08 16:30:13','',NULL,''),(2095,'登记完成',2026,3,'',NULL,NULL,1,0,'F','0','0','warehouse:pickWork:registerComplete','#','admin','2023-05-08 16:30:23','',NULL,''),(2096,'激活',2025,0,'',NULL,NULL,1,0,'F','0','0','warehouse:deliveryNotice:active','#','admin','2023-05-08 16:32:56','',NULL,''),(2097,'反激活',2025,1,'',NULL,NULL,1,0,'F','0','0','warehouse:deliveryNotice:inactive','#','admin','2023-05-08 16:33:14','admin','2023-05-08 16:33:39',''),(2098,'作废',2025,2,'',NULL,NULL,1,0,'F','0','0','warehouse:deliveryNotice:invalid','#','admin','2023-05-08 16:34:03','',NULL,''),(2099,'详情',2025,3,'',NULL,NULL,1,0,'F','0','0','warehouse:deliveryNotice:detail','#','admin','2023-05-08 16:34:25','',NULL,''),(2100,'生成作业单',2025,4,'',NULL,NULL,1,0,'F','0','0','warehouse:deliveryNotice:pickWork','#','admin','2023-05-08 16:35:03','',NULL,''),(2101,'出库确认',2025,5,'',NULL,NULL,1,0,'F','0','0','warehouse:deliveryNotice:deliveryConfirm','#','admin','2023-05-08 16:35:17','',NULL,''),(2102,'手动分配',2025,6,'',NULL,NULL,1,0,'F','0','0','warehouse:deliveryNotice:manualAllocation','#','admin','2023-05-08 16:36:31','',NULL,''),(2103,'打印领料单',2036,0,'',NULL,NULL,1,0,'F','0','0','warehouse:deliveryNoticeHistory:pickListPrint','#','admin','2023-05-08 16:37:51','',NULL,''),(2104,'详情',2036,1,'',NULL,NULL,1,0,'F','0','0','warehouse:deliveryNoticeHistory:detail','#','admin','2023-05-08 16:38:20','',NULL,''),(2105,'库内作业',0,3,'work',NULL,NULL,1,0,'M','0','0',NULL,'work','admin','2023-05-19 18:40:36','',NULL,''),(2106,'仓库报损',2105,4,'breakageDoc','warehouse/breakageDoc/index',NULL,1,0,'C','0','0','warehouse:breakageDoc:list','upload','admin','2023-05-19 18:41:52','admin','2023-06-09 17:38:05',''),(2107,'库存盘点',2105,1,'inventoryPlan','warehouse/inventoryPlan/index',NULL,1,0,'C','0','0','warehouse:inventoryPlan:list','monitor','admin','2023-05-21 15:26:26','',NULL,''),(2108,'月结管理',2007,5,'monthCheckOut','warehouse/monthCheckOut/index',NULL,1,0,'C','0','0','warehouse:monthCheckOut:list','chart','admin','2023-05-27 18:17:58','admin','2023-05-27 18:19:57',''),(2109,'月结',2108,1,'',NULL,NULL,1,0,'F','0','0','warehouse:monthCheckOut:settlement','#','admin','2023-05-28 21:18:25','admin','2023-05-28 21:18:56',''),(2110,'反月结',2108,2,'',NULL,NULL,1,0,'F','0','0','warehouse:monthCheckOut:reverse','#','admin','2023-05-28 21:18:43','admin','2023-05-28 21:19:05',''),(2111,'详情',2107,3,'',NULL,NULL,1,0,'F','0','0','warehouse:inventoryPlan:query','#','admin','2023-06-09 17:31:31','admin','2023-06-09 17:43:04',''),(2112,'作业',2107,4,'warehouse:inventoryPlan:wrok',NULL,NULL,1,0,'F','0','0','warehouse:inventoryPlan:work','#','admin','2023-06-09 17:31:45','admin','2023-06-09 17:42:48',''),(2113,'登记',2107,5,'',NULL,NULL,1,0,'F','0','0','warehouse:inventoryPlan:register','#','admin','2023-06-09 17:32:20','admin','2023-06-09 17:42:54',''),(2114,'完成',2107,6,'',NULL,NULL,1,0,'F','0','0','warehouse:inventoryPlan:finish','#','admin','2023-06-09 17:32:46','admin','2023-06-09 17:42:59',''),(2115,'删除',2107,2,'',NULL,NULL,1,0,'F','0','0','warehouse:inventoryPlan:remove','#','admin','2023-06-09 17:33:05','admin','2023-06-09 17:42:28',''),(2116,'修改',2107,1,'',NULL,NULL,1,0,'F','0','0','warehouse:inventoryPlan:edit','#','admin','2023-06-09 17:33:23','admin','2023-06-09 17:42:34',''),(2117,'新增',2107,0,'',NULL,NULL,1,0,'F','0','0','warehouse:inventoryPlan:add','#','admin','2023-06-09 17:33:35','admin','2023-06-09 17:42:23',''),(2118,'新增',2106,0,'',NULL,NULL,1,0,'F','0','0','warehouse:breakageDoc:add','#','admin','2023-06-09 17:35:52','admin','2023-06-11 10:40:35',''),(2119,'修改',2106,1,'',NULL,NULL,1,0,'F','0','0','warehouse:breakageDoc:edit','#','admin','2023-06-09 17:36:05','',NULL,''),(2120,'删除',2106,2,'',NULL,NULL,1,0,'F','0','0','warehouse:breakageDoc:remove','#','admin','2023-06-09 17:36:18','',NULL,''),(2121,'查看明细',2106,3,'',NULL,NULL,1,0,'F','0','0','warehouse:breakageDoc:query','#','admin','2023-06-09 17:36:50','',NULL,''),(2122,'设置明细',2106,4,'',NULL,NULL,1,0,'F','0','0','warehouse:breakageDoc:addDetail','#','admin','2023-06-09 17:37:05','',NULL,''),(2123,'提交',2106,5,'',NULL,NULL,1,0,'F','0','0','warehouse:breakageDoc:submit','#','admin','2023-06-09 17:37:19','admin','2023-06-09 17:38:34',''),(2124,'打印',2106,6,'',NULL,NULL,1,0,'F','0','0','warehouse:breakageDoc:print','#','admin','2023-06-09 17:37:41','',NULL,''),(2125,'导出',2023,5,'',NULL,NULL,1,0,'F','0','0','warehouse:itemStorage:export','#','admin','2023-07-06 09:10:25','',NULL,''),(2126,'导出',2028,1,'',NULL,NULL,1,0,'F','0','0','warehouse:storageInOutRecord:export','#','admin','2023-08-16 08:09:04','',NULL,'');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_notice`
--

DROP TABLE IF EXISTS `sys_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_notice` (
  `notice_id` int NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) NOT NULL COMMENT '公告标题',
  `notice_type` char(1) NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob COMMENT '公告内容',
  `status` char(1) DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='通知公告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notice`
--

LOCK TABLES `sys_notice` WRITE;
/*!40000 ALTER TABLE `sys_notice` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_oper_log`
--

DROP TABLE IF EXISTS `sys_oper_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_oper_log` (
  `oper_id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) DEFAULT '' COMMENT '模块标题',
  `business_type` int DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) DEFAULT '' COMMENT '请求方式',
  `operator_type` int DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) DEFAULT '' COMMENT '返回参数',
  `status` int DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  `cost_time` bigint DEFAULT '0' COMMENT '消耗时间',
  PRIMARY KEY (`oper_id`) USING BTREE,
  KEY `idx_sys_oper_log_bt` (`business_type`) USING BTREE,
  KEY `idx_sys_oper_log_ot` (`oper_time`) USING BTREE,
  KEY `idx_sys_oper_log_s` (`status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15133 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_oper_log`
--

LOCK TABLES `sys_oper_log` WRITE;
/*!40000 ALTER TABLE `sys_oper_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_oper_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_post`
--

DROP TABLE IF EXISTS `sys_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_post` (
  `post_id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) NOT NULL COMMENT '岗位名称',
  `post_sort` int NOT NULL COMMENT '显示顺序',
  `status` char(1) NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='岗位信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_post`
--

LOCK TABLES `sys_post` WRITE;
/*!40000 ALTER TABLE `sys_post` DISABLE KEYS */;
INSERT INTO `sys_post` VALUES (1,'ceo','董事长',1,'0','admin','2023-03-26 18:55:59','',NULL,''),(2,'se','项目经理',2,'0','admin','2023-03-26 18:55:59','',NULL,''),(3,'hr','人力资源',3,'0','admin','2023-03-26 18:55:59','',NULL,''),(4,'user','普通员工',4,'0','admin','2023-03-26 18:55:59','',NULL,'');
/*!40000 ALTER TABLE `sys_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) DEFAULT '1' COMMENT '部门树选择项是否关联显示',
  `status` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'超级管理员','admin',1,'1',1,1,'0','0','admin','2023-03-26 18:55:59','',NULL,'超级管理员'),(2,'普通角色','common',2,'2',1,1,'0','0','admin','2023-03-26 18:55:59','admin','2023-05-10 14:13:59','普通角色'),(111,'仓管员','wms-manager',0,'1',1,1,'0','0','admin','2023-06-08 22:11:12','admin','2023-08-16 08:09:26',NULL);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_dept`
--

DROP TABLE IF EXISTS `sys_role_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_dept` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `dept_id` bigint NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`,`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='角色和部门关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_dept`
--

LOCK TABLES `sys_role_dept` WRITE;
/*!40000 ALTER TABLE `sys_role_dept` DISABLE KEYS */;
INSERT INTO `sys_role_dept` VALUES (2,100),(2,101),(2,105);
/*!40000 ALTER TABLE `sys_role_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='角色和菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (2,1),(2,2),(2,100),(2,101),(2,102),(2,103),(2,104),(2,108),(2,109),(2,110),(2,111),(2,112),(2,113),(2,114),(2,500),(2,501),(2,1000),(2,1001),(2,1002),(2,1003),(2,1004),(2,1005),(2,1006),(2,1007),(2,1008),(2,1009),(2,1010),(2,1011),(2,1012),(2,1013),(2,1014),(2,1015),(2,1016),(2,1017),(2,1018),(2,1019),(2,1020),(2,1021),(2,1022),(2,1023),(2,1024),(2,1039),(2,1040),(2,1041),(2,1042),(2,1043),(2,1044),(2,1045),(2,1046),(2,1047),(2,1048),(2,1049),(2,1050),(2,1051),(2,1052),(2,1053),(2,1054),(2,2000),(2,2001),(2,2002),(2,2005),(2,2007),(2,2009),(2,2010),(2,2011),(2,2012),(2,2013),(2,2014),(2,2015),(2,2016),(2,2017),(2,2018),(2,2019),(2,2020),(2,2021),(2,2022),(2,2023),(2,2024),(2,2025),(2,2026),(2,2027),(2,2028),(2,2029),(2,2030),(2,2031),(2,2032),(2,2033),(2,2034),(2,2035),(2,2036),(2,2037),(2,2039),(2,2040),(2,2041),(2,2042),(2,2044),(2,2045),(2,2046),(2,2047),(2,2048),(2,2049),(2,2050),(2,2051),(2,2052),(2,2053),(2,2054),(2,2055),(2,2056),(2,2057),(2,2058),(2,2059),(2,2060),(2,2061),(2,2062),(2,2063),(2,2064),(2,2065),(2,2066),(2,2067),(2,2068),(2,2069),(2,2070),(2,2071),(2,2072),(2,2073),(2,2074),(2,2075),(2,2076),(2,2077),(2,2078),(2,2079),(2,2080),(2,2081),(2,2082),(2,2083),(2,2084),(2,2085),(2,2086),(2,2087),(2,2088),(2,2089),(2,2090),(2,2091),(2,2092),(2,2093),(2,2094),(2,2095),(2,2096),(2,2097),(2,2098),(2,2099),(2,2100),(2,2101),(2,2102),(2,2103),(2,2104),(111,1),(111,2000),(111,2001),(111,2002),(111,2005),(111,2007),(111,2009),(111,2010),(111,2011),(111,2012),(111,2013),(111,2014),(111,2015),(111,2016),(111,2017),(111,2018),(111,2019),(111,2020),(111,2021),(111,2022),(111,2023),(111,2024),(111,2025),(111,2026),(111,2027),(111,2028),(111,2029),(111,2030),(111,2031),(111,2032),(111,2033),(111,2034),(111,2035),(111,2036),(111,2037),(111,2039),(111,2040),(111,2041),(111,2042),(111,2044),(111,2045),(111,2046),(111,2047),(111,2048),(111,2049),(111,2050),(111,2051),(111,2052),(111,2053),(111,2054),(111,2055),(111,2056),(111,2057),(111,2058),(111,2059),(111,2060),(111,2061),(111,2062),(111,2063),(111,2064),(111,2065),(111,2066),(111,2067),(111,2068),(111,2069),(111,2070),(111,2071),(111,2072),(111,2073),(111,2074),(111,2075),(111,2076),(111,2077),(111,2078),(111,2079),(111,2080),(111,2081),(111,2082),(111,2083),(111,2084),(111,2085),(111,2086),(111,2087),(111,2088),(111,2089),(111,2090),(111,2091),(111,2092),(111,2093),(111,2094),(111,2095),(111,2096),(111,2097),(111,2098),(111,2099),(111,2100),(111,2101),(111,2102),(111,2103),(111,2104),(111,2105),(111,2106),(111,2107),(111,2108),(111,2109),(111,2111),(111,2112),(111,2113),(111,2114),(111,2115),(111,2116),(111,2117),(111,2118),(111,2119),(111,2120),(111,2121),(111,2122),(111,2123),(111,2124),(111,2125),(111,2126);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) DEFAULT '' COMMENT '密码',
  `status` char(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,205,'admin','管理员','00','mangocoding@163.com','18717319780','0','/profile/avatar/2023/05/15/blob_20230515083245A001.png','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2','0','0','10.161.100.10','2023-12-01 08:54:24','admin','2023-03-26 18:55:59','','2023-12-01 08:54:23','管理员'),(100,100,'Test','测试','00','','13227941958','0','','$2a$10$XXy0puJhqzBf0IJ1.SqXLufTE9uhvuujYEMfhhc0G7uFYQbo/iJ9q','0','0','127.0.0.1','2023-06-12 20:47:03','admin','2023-05-08 14:48:08','admin','2023-06-12 20:47:02',NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_post`
--

DROP TABLE IF EXISTS `sys_user_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_post` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `post_id` bigint NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`,`post_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户与岗位关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_post`
--

LOCK TABLES `sys_user_post` WRITE;
/*!40000 ALTER TABLE `sys_user_post` DISABLE KEYS */;
INSERT INTO `sys_user_post` VALUES (1,1),(100,4),(101,4);
/*!40000 ALTER TABLE `sys_user_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户和角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1),(100,2),(101,111);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_ding_workflow`
--

DROP TABLE IF EXISTS `tb_ding_workflow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_ding_workflow` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `business_id` varchar(32) NOT NULL COMMENT '业务ID eg：合同ID',
  `business_code` varchar(32) DEFAULT NULL COMMENT '业务编码 eg：合同编码',
  `business_name` varchar(255) DEFAULT NULL COMMENT '业务名称 eg：合同名称',
  `business_tag` varchar(32) NOT NULL COMMENT '类型：agreement-合同；please-请购；refund-退货；payment-付款申请',
  `apply_user_id` varchar(32) DEFAULT NULL COMMENT '申请人ID',
  `apply_user_name` varchar(255) DEFAULT NULL COMMENT '申请人姓名',
  `apply_time` datetime NOT NULL COMMENT '申请日期',
  `dingding_id` varchar(255) NOT NULL COMMENT '钉钉-工作流ID',
  `dingding_name` varchar(255) DEFAULT NULL COMMENT '钉钉-工作流名称',
  `audit_final_status` varchar(32) DEFAULT NULL COMMENT '最终审核状态：0-审核中；refuse-拒绝；agree-同意',
  `audit_final_time` datetime DEFAULT NULL COMMENT '最终审核时间',
  `enterprise_key` varchar(32) NOT NULL COMMENT '企业key(E00002)',
  `is_deleted` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '是否已删除 0 - 未删除， 1 - 已删除',
  `title` varchar(255) DEFAULT NULL COMMENT '申请名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '审批意见',
  `type` varchar(255) DEFAULT NULL COMMENT '钉钉返回的type 标记流程节点eg:  结束',
  `process_code` varchar(255) DEFAULT NULL COMMENT '申请的钉钉流程编码',
  `ding_business_id` varchar(255) DEFAULT NULL COMMENT '钉钉返回的businessId ',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='钉钉工作流与业务ID关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_ding_workflow`
--

LOCK TABLES `tb_ding_workflow` WRITE;
/*!40000 ALTER TABLE `tb_ding_workflow` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_ding_workflow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_dose_unit`
--

DROP TABLE IF EXISTS `tb_dose_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_dose_unit` (
  `id` varchar(32) NOT NULL COMMENT '主键ID',
  `product_code` varchar(32) NOT NULL COMMENT '商品编码',
  `product_name` varchar(255) NOT NULL COMMENT '商品名称',
  `master_unit` varchar(32) NOT NULL COMMENT '主计量单位（eg:kg）',
  `conversion_rate` decimal(20,8) NOT NULL COMMENT '换算率（eg:5）',
  `belongs` varchar(32) NOT NULL COMMENT '所属：sales-销售 purchase-采购 manufacture-生产 warehouse-库存剂量单位',
  `dose_unit` varchar(255) NOT NULL COMMENT '采购/库存/生产/销售/剂量单位 =主剂量单位*换算率',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(32) NOT NULL COMMENT '创建人id',
  `create_user_name` varchar(255) NOT NULL COMMENT '创建人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `update_user_name` varchar(255) DEFAULT NULL COMMENT '修改人姓名',
  `enterprise_key` varchar(20) DEFAULT NULL COMMENT '企业唯一key ：E00001',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='剂量单位表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_logistics`
--

DROP TABLE IF EXISTS `tb_logistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_logistics` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT 'ID',
  `logistics_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '编码',
  `logistics_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '名称',
  `contacts` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '联系人',
  `contacts_tel` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '联系人电话',
  `contacts_email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '联系人邮箱',
  `country` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '国家',
  `province` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '省',
  `city` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '市',
  `region` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '区-中文',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '地址',
  `zip_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮编',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '描述',
  `status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '开通账户状态：0-正常 1-停用',
  `create_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人id',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人(对应sys_user表主键)',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '是否删除：0-未删除 1-已删除',
  `enterprise_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '企业信用统一编码',
  `province_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '省-code',
  `city_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '市-code',
  `region_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '区-code',
  `short_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '简称（6个字以内）',
  `enterprise_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '企业key(E00002) 创建人的企业Key',
  `audit_status` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '审核状态：0-待提交审核；1-拒绝；2-已通过；3-审核中',
  `manager_scope` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '经营范围',
  `type` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '类型',
  `apply_type` varchar(10) NOT NULL COMMENT '申请类型：add-新增；update-修改',
  `locked` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '证件过期锁定: 1- 证件过期锁定  2- 未锁定',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC COMMENT='物流公司表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_logistics`
--

LOCK TABLES `tb_logistics` WRITE;
/*!40000 ALTER TABLE `tb_logistics` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_logistics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_product_info`
--

DROP TABLE IF EXISTS `tb_product_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_product_info` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品编码',
  `product_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `create_enterprise_key` varchar(255) DEFAULT NULL,
  `material_code_enterprise` varchar(255) DEFAULT NULL,
  `category` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '类目',
  `barcode` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主条码',
  `part_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '图号',
  `unit` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '单位',
  `spec` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '规格型号',
  `unit_price` decimal(10,2) DEFAULT NULL COMMENT '单价',
  `length` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '长',
  `width` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '宽',
  `height` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '高',
  `volume` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '体积',
  `weight` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '重量',
  `quantity` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '88888' COMMENT '库存',
  `on_line` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '1' COMMENT '1-上架 2-下架',
  `remark` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '图文介绍(富文本编辑器)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `supplier_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人(对应 tb_sys_user表主键不是tb_sys_supplier)',
  `supplier_code` varchar(32) DEFAULT NULL COMMENT '供应商编码',
  `supplier_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '供应商名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人',
  `is_deleted` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '删除状态：0-正常 1-删除',
  `category_properties` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '类目属性',
  `create_user_id` varchar(32) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT '创建人ID(对应 tb_sys_user表主键）',
  `enterprise_key` varchar(32) DEFAULT NULL COMMENT '企业key',
  `locked` varchar(2) DEFAULT NULL COMMENT '证件过期锁定: 1- 证件过期锁定  2- 未锁定',
  `dosage_form` varchar(32) DEFAULT NULL COMMENT '剂型',
  `type` varchar(32) DEFAULT NULL COMMENT '商品类型',
  `approval_no` varchar(32) DEFAULT NULL COMMENT '批准文号',
  `spec_package` varchar(32) DEFAULT NULL COMMENT '包装规格',
  `manager_scope` varchar(32) DEFAULT NULL COMMENT '经营范围',
  `classify_two` varchar(32) DEFAULT NULL COMMENT '二级分类',
  `classify_clinical` varchar(32) DEFAULT NULL COMMENT '临床分类',
  `classify_industry` varchar(32) DEFAULT NULL COMMENT '行业分类',
  `min_package` varchar(32) DEFAULT NULL COMMENT '最小销售包装',
  `cg_price` decimal(10,0) DEFAULT NULL COMMENT '采购价',
  `tax_rate` decimal(10,0) DEFAULT NULL COMMENT '税率',
  `tax` decimal(10,0) DEFAULT NULL COMMENT '税额',
  `audit_status` varchar(32) DEFAULT NULL COMMENT '审核状态：0-待提交审核；1-拒绝；2-已通过；3-审核中',
  `material_type` varchar(32) DEFAULT NULL COMMENT '物料类型：1-商品；2-物料',
  `apply_type` varchar(32) DEFAULT NULL COMMENT '申请类型：add-新增；update-修改',
  `dosage_form_spec` varchar(255) DEFAULT NULL COMMENT '剂型规格',
  `product_number` varchar(32) DEFAULT NULL COMMENT '关系码',
  `effective_end_date` varchar(32) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT '有效期(单位天)',
  `material_code_supplier` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `prepare_day` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT '备货周期：30 天',
  `measurement_methods` varchar(2) DEFAULT NULL COMMENT '计量方式： eg:1-个数 2-重量',
  `min_unit` varchar(50) DEFAULT NULL COMMENT '最小单位：0.4ml',
  `big_class` varchar(2) DEFAULT NULL COMMENT '原料大类：1-试剂耗材；2-原辅料；3-直接包材；4-其他; 【商品大类：1-通用型培养基；2-定制型培养基；3-产品成本差异；4-其他】',
  `level_class` varchar(2) DEFAULT NULL COMMENT '物料级别：A类、B类、C类、D类',
  `notax_price` decimal(20,6) DEFAULT NULL COMMENT '未税单价=采购价格/(1+0.13)；10000',
  `short_name` varchar(255) DEFAULT NULL COMMENT '简称',
  `english_name` varchar(255) DEFAULT NULL COMMENT '英文名称',
  `stock_defined` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT '存货定义：1-外购、2-自制、3-研发购入、4-委托生产客/户提供(多个用逗号分隔)',
  `dose_belongs` varchar(32) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT '单位所属：sales-销售/商品存 purchase-采购/原材料存',
  `master_unit` varchar(32) DEFAULT 'g' COMMENT '主剂量单位',
  `if_group` varchar(2) DEFAULT '0' COMMENT '是否组合商品：0-否；1-是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1575 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='商品信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_product_info`
--

LOCK TABLES `tb_product_info` WRITE;
/*!40000 ALTER TABLE `tb_product_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_product_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_relation`
--

DROP TABLE IF EXISTS `tb_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_relation` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cust_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `cust_name` varchar(255) DEFAULT NULL COMMENT '客户名称',
  `supplier_id` varchar(32) DEFAULT NULL COMMENT '供应商ID',
  `supplier_name` varchar(255) DEFAULT NULL COMMENT '供应商名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `verify_status` varchar(2) DEFAULT '0' COMMENT '审核状态: 0- 待审核 1-拒绝；2-已通过 3-审核中',
  `audit_status` varchar(2) DEFAULT NULL COMMENT '关联状态: 0- 待关联 1-拒绝 2-同意',
  `locked` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0' COMMENT '证件过期锁定: 1- 证件过期锁定  2- 未锁定 ',
  `apply_type` varchar(10) NOT NULL DEFAULT 'add' COMMENT '申请类型：add-新增；update-修改',
  `create_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建用户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=804 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_relation`
--

LOCK TABLES `tb_relation` WRITE;
/*!40000 ALTER TABLE `tb_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_customer`
--

DROP TABLE IF EXISTS `tb_sys_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_sys_customer` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `customer_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '客户名称',
  `short_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '简称（6个字以内）',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '编码（C00001）',
  `concat_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '联系人',
  `tel` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '电话',
  `email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮箱',
  `country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '国家',
  `province` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '省-中文',
  `city` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '市-中文',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '地址',
  `zip_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮编',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '描述',
  `enterprise_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '企业key(E00002)',
  `enterprise_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '企业信用统一编码',
  `update_user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT ' 是否删除：0-未删除 1-已删除',
  `open_account` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '开通账号',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '开通账户状态：1-未开通 2-已开通',
  `province_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '省-code',
  `city_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '市-code',
  `create_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `region` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '区-中文',
  `region_code` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '区-code',
  `type_id` varchar(255) DEFAULT NULL COMMENT '类型id',
  `type_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '类型名称',
  `customer_level` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '客户类型：大客户、经销商、零售客户、科研机构（从字典取）',
  `credit_period` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '授信期，eg：30',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC COMMENT='客户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_customer`
--

LOCK TABLES `tb_sys_customer` WRITE;
/*!40000 ALTER TABLE `tb_sys_customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(32) DEFAULT NULL COMMENT '仓库编码',
  `name` varchar(32) DEFAULT NULL COMMENT '仓库名称',
  `belong_company` varchar(32) DEFAULT NULL COMMENT '所属公司',
  `contract_user` varchar(32) DEFAULT NULL COMMENT '联系人',
  `contract_phone` varchar(32) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `type` tinyint(1) DEFAULT NULL COMMENT '仓库类型',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='仓库表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse`
--

LOCK TABLES `warehouse` WRITE;
/*!40000 ALTER TABLE `warehouse` DISABLE KEYS */;
INSERT INTO `warehouse` VALUES ('BCP0001','BCP','半成品仓','E00000001',NULL,NULL,NULL,3,NULL,0,1,'2023-04-22 15:16:59',1,'2023-04-22 15:18:09'),('c27effa93d264db5bb7aaba13b2c2964','HC','耗材仓','E00000001',NULL,NULL,NULL,10,NULL,0,101,'2023-06-20 23:34:48',1,'2023-06-20 23:38:41'),('CPC0001','C','成品仓','E00000001',NULL,NULL,NULL,1,NULL,0,1,'2023-04-22 15:17:35',NULL,NULL),('KHC001','KH','客户来料仓','E00000001',NULL,NULL,NULL,9,NULL,0,1,'2023-04-22 15:18:50',1,'2023-05-08 09:57:03'),('QTC0001','YF','研发仓','E00000001',NULL,NULL,NULL,7,NULL,0,1,'2023-04-22 15:18:32',NULL,NULL),('SJC0001','Y','原辅料仓','E00000001',NULL,NULL,NULL,2,NULL,0,1,'2023-04-22 15:17:19',NULL,NULL),('THC0001','TH','退货召回仓','E00000001',NULL,NULL,NULL,4,NULL,0,1,'2023-04-22 15:17:52',1,'2023-05-01 11:44:28'),('ZJBC0001','B','包材仓','E00000001',NULL,NULL,NULL,5,NULL,0,1,'2023-04-22 15:18:04',100,'2023-05-08 17:00:21');
/*!40000 ALTER TABLE `warehouse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_bill_of_loading`
--

DROP TABLE IF EXISTS `wms_bill_of_loading`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_bill_of_loading` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `delivery_notice_id` varchar(32) DEFAULT NULL COMMENT '发货通知单号',
  `business_id` varchar(32) DEFAULT NULL COMMENT '业务单号',
  `business_type` varchar(32) DEFAULT NULL COMMENT '单据类型',
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司ID',
  `plan_quantity` decimal(20,6) DEFAULT NULL COMMENT '计划数量',
  `quantity` decimal(20,6) DEFAULT NULL COMMENT '实际数量',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户id',
  `customer_code` varchar(255) DEFAULT NULL COMMENT '客户编码',
  `customer_name` varchar(255) DEFAULT NULL COMMENT '客户名称',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `carriers_id` varchar(32) DEFAULT NULL COMMENT '发运承运商id',
  `carriers_concat` varchar(32) DEFAULT NULL COMMENT '承运人信息',
  `car_info` varchar(32) DEFAULT NULL COMMENT '车辆信息',
  `logistics_no` varchar(32) DEFAULT NULL COMMENT '物流单号信息',
  `fare` decimal(20,6) DEFAULT NULL COMMENT '运费',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `delivery_date` datetime DEFAULT NULL COMMENT '发运时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='发运单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_bill_of_loading`
--

LOCK TABLES `wms_bill_of_loading` WRITE;
/*!40000 ALTER TABLE `wms_bill_of_loading` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_bill_of_loading` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_breakage_approval`
--

DROP TABLE IF EXISTS `wms_breakage_approval`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_breakage_approval` (
  `id` varchar(32) DEFAULT NULL,
  `breakage_doc_id` varchar(32) DEFAULT NULL COMMENT '报损单ID',
  `dept_supervisor` varchar(256) DEFAULT NULL COMMENT '部门主管',
  `quality_supervisor` varchar(32) DEFAULT NULL COMMENT '品管',
  `quantity_dept_supervisor` varchar(256) DEFAULT NULL COMMENT '质量部主管'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='报损单审批实例';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_breakage_approval`
--

LOCK TABLES `wms_breakage_approval` WRITE;
/*!40000 ALTER TABLE `wms_breakage_approval` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_breakage_approval` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_breakage_doc`
--

DROP TABLE IF EXISTS `wms_breakage_doc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_breakage_doc` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司ID',
  `office_id` bigint DEFAULT NULL COMMENT '申请部门ID',
  `office_name` varchar(32) DEFAULT NULL COMMENT '申请部门名称',
  `applicant` varchar(32) DEFAULT NULL COMMENT '申请人',
  `applicant_id` bigint DEFAULT NULL COMMENT '申请人ID',
  `type` varchar(32) DEFAULT NULL COMMENT '报损类型',
  `receive_send_type` varchar(32) DEFAULT NULL COMMENT '收发类型',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态(待提交审核、审核中、已通过、未通过)',
  `reason` varchar(128) DEFAULT NULL COMMENT '报损原因',
  `description` varchar(256) DEFAULT NULL COMMENT '报损说明',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `occ_time` datetime DEFAULT NULL COMMENT '发生时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='报损单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_breakage_doc`
--

LOCK TABLES `wms_breakage_doc` WRITE;
/*!40000 ALTER TABLE `wms_breakage_doc` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_breakage_doc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_breakage_doc_detail`
--

DROP TABLE IF EXISTS `wms_breakage_doc_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_breakage_doc_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `breakage_doc_id` varchar(32) DEFAULT NULL COMMENT '报损单号',
  `item_code` varchar(32) DEFAULT NULL COMMENT '物料编码',
  `item_name` varchar(128) DEFAULT NULL COMMENT '物料名称',
  `batch_no` varchar(32) DEFAULT NULL COMMENT '批次',
  `quantity` decimal(20,6) DEFAULT NULL COMMENT '数量',
  `total_quantity` decimal(20,6) DEFAULT NULL COMMENT '物料总数',
  `unit` varchar(32) DEFAULT NULL COMMENT '单位',
  `price` decimal(20,6) DEFAULT NULL COMMENT '单价',
  `spec` varchar(256) DEFAULT NULL COMMENT '规格',
  `bad_causes` varchar(256) DEFAULT NULL COMMENT '不良原因',
  `warehouse_id` varchar(32) DEFAULT NULL COMMENT '仓库ID',
  `location_id` varchar(32) DEFAULT NULL COMMENT '货位ID',
  `product_date` datetime DEFAULT NULL COMMENT '生产日期',
  `expire_date` datetime DEFAULT NULL COMMENT '过期日期',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='报损物料明细表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_breakage_doc_detail`
--

LOCK TABLES `wms_breakage_doc_detail` WRITE;
/*!40000 ALTER TABLE `wms_breakage_doc_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_breakage_doc_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_delivery_notice`
--

DROP TABLE IF EXISTS `wms_delivery_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_delivery_notice` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `business_id` varchar(32) DEFAULT NULL COMMENT '业务系统单号',
  `business_type` varchar(32) DEFAULT NULL COMMENT '单据类型（销售发货单、生产领料出库...）',
  `location_id` varchar(32) DEFAULT NULL COMMENT '发货货位',
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司ID',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态',
  `delivery_start_time` datetime DEFAULT NULL COMMENT '发货开始时间',
  `delivery_end_time` datetime DEFAULT NULL COMMENT '发货完成时间',
  `notice_user_id` varchar(32) DEFAULT NULL COMMENT '通知人ID',
  `notice_user` varchar(32) DEFAULT NULL COMMENT '通知人名称',
  `to_code` varchar(32) DEFAULT NULL COMMENT '收货方编码',
  `to_name` varchar(32) DEFAULT NULL COMMENT '收货方名称',
  `to_contacts` varchar(32) DEFAULT NULL COMMENT '收货方联系人',
  `to_contacts_tel` varchar(32) DEFAULT NULL COMMENT '联系电话',
  `to_address` varchar(128) DEFAULT NULL COMMENT '联系地址',
  `dept_name` varchar(32) DEFAULT NULL COMMENT '通知部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `thermosphere` varchar(16) DEFAULT NULL COMMENT '温层',
  `receive_send_type` varchar(16) DEFAULT NULL COMMENT '收发类别',
  `notice_desc` varchar(255) DEFAULT NULL COMMENT '通知说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='出库通知表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_delivery_notice`
--

LOCK TABLES `wms_delivery_notice` WRITE;
/*!40000 ALTER TABLE `wms_delivery_notice` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_delivery_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_delivery_notice_detail`
--

DROP TABLE IF EXISTS `wms_delivery_notice_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_delivery_notice_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `delivery_notice_id` varchar(32) DEFAULT NULL COMMENT '出库通知id',
  `quantity` decimal(20,6) DEFAULT NULL COMMENT '通知数量',
  `allot_quantity` decimal(20,6) DEFAULT NULL COMMENT '分配中数量',
  `work_quantity` decimal(20,6) DEFAULT NULL COMMENT '作业数量',
  `work_finish_quantity` decimal(20,6) DEFAULT NULL COMMENT '作业完成数量',
  `delivery_quantity` decimal(20,6) DEFAULT NULL COMMENT '发货数量',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态',
  `item_code` varchar(32) DEFAULT NULL COMMENT '物料编码',
  `item_name` varchar(128) DEFAULT NULL COMMENT '物料名称',
  `item_barcode` varchar(32) DEFAULT NULL COMMENT '物料条码',
  `item_unit` varchar(16) DEFAULT NULL COMMENT '单位',
  `item_spec` varchar(32) DEFAULT NULL COMMENT '规格',
  `business_quantity` decimal(20,6) DEFAULT NULL COMMENT '业务单位数量',
  `business_unit` varchar(16) DEFAULT NULL COMMENT '业务单位',
  `belongs` varchar(16) DEFAULT NULL COMMENT '所属单位类型',
  `conversion_rate` decimal(20,8) DEFAULT NULL COMMENT '换算率',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `business_no` varchar(32) DEFAULT NULL COMMENT '业务编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='出库通知明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_delivery_notice_detail`
--

LOCK TABLES `wms_delivery_notice_detail` WRITE;
/*!40000 ALTER TABLE `wms_delivery_notice_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_delivery_notice_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_delivery_sure`
--

DROP TABLE IF EXISTS `wms_delivery_sure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_delivery_sure` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `delivery_notice_id` varchar(32) DEFAULT NULL COMMENT '出通知单ID',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_by` bigint DEFAULT NULL COMMENT '确认人',
  `create_time` datetime DEFAULT NULL COMMENT '确认时间',
  `result` tinyint(1) DEFAULT NULL COMMENT '结果 0 拒绝发运 1 同意发运',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='出库确认记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wms_delivery_sure_detail`
--

DROP TABLE IF EXISTS `wms_delivery_sure_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_delivery_sure_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `sure_id` varchar(32) DEFAULT NULL COMMENT '确认ID',
  `check_id` varchar(32) DEFAULT NULL COMMENT '检查项ID',
  `check_item` varchar(128) DEFAULT NULL COMMENT '检查项目',
  `check_requirement` varchar(128) DEFAULT NULL COMMENT '要求',
  `result` tinyint(1) DEFAULT NULL COMMENT '是否合格：是/否',
  `sort` smallint DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='出库确认记录详情表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_delivery_sure_detail`
--

LOCK TABLES `wms_delivery_sure_detail` WRITE;
/*!40000 ALTER TABLE `wms_delivery_sure_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_delivery_sure_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_delivery_sure_dict`
--

DROP TABLE IF EXISTS `wms_delivery_sure_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_delivery_sure_dict` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司ID',
  `check_requirement` varchar(128) DEFAULT NULL COMMENT '要求',
  `check_item` varchar(32) DEFAULT NULL COMMENT '检查项',
  `sort` smallint DEFAULT NULL COMMENT '排序',
  `thermosphere` varchar(32) DEFAULT NULL COMMENT '温层',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='出库确认项表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_delivery_sure_dict`
--

LOCK TABLES `wms_delivery_sure_dict` WRITE;
/*!40000 ALTER TABLE `wms_delivery_sure_dict` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_delivery_sure_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_displacement_log`
--

DROP TABLE IF EXISTS `wms_displacement_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_displacement_log` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `warehouse_id` varchar(32) DEFAULT NULL COMMENT '仓库ID',
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司ID',
  `item_code` varchar(32) DEFAULT NULL COMMENT '物料编码',
  `item_name` varchar(128) DEFAULT NULL COMMENT '物料名称',
  `batch_no` varchar(32) DEFAULT NULL COMMENT '批次号',
  `quantity` decimal(20,6) DEFAULT NULL COMMENT '数量',
  `unit` varchar(32) DEFAULT NULL COMMENT '单位',
  `spec` varchar(32) DEFAULT NULL COMMENT '规格',
  `original_location` varchar(32) DEFAULT NULL COMMENT '原库位',
  `target_location` varchar(32) DEFAULT NULL COMMENT '目标库位',
  `create_user` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='移位日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_displacement_log`
--

LOCK TABLES `wms_displacement_log` WRITE;
/*!40000 ALTER TABLE `wms_displacement_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_displacement_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_examine_conclusion`
--

DROP TABLE IF EXISTS `wms_examine_conclusion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_examine_conclusion` (
  `id` varchar(32) NOT NULL,
  `examine_id` varchar(32) DEFAULT NULL COMMENT '质检任务ID',
  `receive_status` tinyint(1) DEFAULT NULL COMMENT '收货状态 （全收、全退、部分收）',
  `receive_quantity` decimal(20,6) DEFAULT NULL COMMENT '接收数量',
  `refund_quantity` decimal(20,6) DEFAULT NULL COMMENT '退还数量',
  `special_no` varchar(32) DEFAULT NULL COMMENT '特采编号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_examine_conclusion`
--

LOCK TABLES `wms_examine_conclusion` WRITE;
/*!40000 ALTER TABLE `wms_examine_conclusion` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_examine_conclusion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_examine_work`
--

DROP TABLE IF EXISTS `wms_examine_work`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_examine_work` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `receive_item_detail_id` varchar(32) DEFAULT NULL COMMENT '通知明细ID',
  `receive_notice_id` varchar(32) DEFAULT NULL COMMENT '入库通知ID',
  `item_code` varchar(32) DEFAULT NULL COMMENT '物料编码',
  `batch_no` varchar(32) DEFAULT NULL COMMENT '物料批次',
  `status` tinyint(1) DEFAULT NULL COMMENT '质检状态',
  `sample_quantity` decimal(20,6) DEFAULT NULL COMMENT '抽样数量',
  `sample_refund_quantity` decimal(20,6) DEFAULT NULL COMMENT '抽样退回数量',
  `keep_quantity` decimal(20,6) DEFAULT NULL COMMENT '留样数量',
  `fail_quantity` decimal(20,6) DEFAULT NULL COMMENT '不合格数',
  `scrap_quantity` decimal(20,6) DEFAULT NULL COMMENT '报废数量',
  `pass_quantity` decimal(20,6) DEFAULT NULL COMMENT '合格数量',
  `all_quantity` decimal(20,6) DEFAULT NULL COMMENT '质检总数',
  `examine_by` varchar(32) DEFAULT NULL COMMENT '检验员',
  `examine_manager` varchar(32) DEFAULT NULL COMMENT '检验主管',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_up` bigint DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `operate_status` tinyint(1) DEFAULT NULL COMMENT '通知状态 (0:未通知，1：已通知)',
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司ID',
  `check_type` tinyint(1) DEFAULT NULL COMMENT '质检类型',
  `notice_detail_id` varchar(32) DEFAULT NULL COMMENT '通知详情ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='质检作业表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_examine_work`
--

LOCK TABLES `wms_examine_work` WRITE;
/*!40000 ALTER TABLE `wms_examine_work` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_examine_work` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_inventory_detail`
--

DROP TABLE IF EXISTS `wms_inventory_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_inventory_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `inventory_id` varchar(32) DEFAULT NULL COMMENT '盘点计划ID',
  `storage_detail_id` varchar(32) DEFAULT NULL COMMENT '库存详情ID',
  `warehouse_id` varchar(32) DEFAULT NULL COMMENT '仓库ID',
  `location_id` varchar(32) DEFAULT NULL COMMENT '货位ID',
  `item_code` varchar(32) DEFAULT NULL COMMENT '物料编码',
  `item_name` varchar(128) DEFAULT NULL COMMENT '物料名称',
  `unit` varchar(32) DEFAULT NULL COMMENT '单位',
  `spec` varchar(32) DEFAULT NULL COMMENT '规格',
  `price` decimal(20,6) DEFAULT NULL COMMENT '单价',
  `batch_no` varchar(32) DEFAULT NULL COMMENT '批次',
  `quantity` decimal(20,6) DEFAULT NULL COMMENT '数量',
  `inventory_quantity` decimal(20,6) DEFAULT NULL COMMENT '盘点数量',
  `diff_quantity` decimal(20,6) DEFAULT NULL COMMENT '差异数量',
  `storage_status` tinyint(1) DEFAULT NULL COMMENT '库存状态',
  `product_date` datetime DEFAULT NULL COMMENT '生产日期',
  `is_save` tinyint(1) DEFAULT '0' COMMENT '是否登记   0：否 1：是',
  `is_add` tinyint(1) DEFAULT '0' COMMENT '是否增补   0：否 1：是',
  `diff_analysis` varchar(255) DEFAULT NULL COMMENT '差异分析',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='盘点详情表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_inventory_detail`
--

LOCK TABLES `wms_inventory_detail` WRITE;
/*!40000 ALTER TABLE `wms_inventory_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_inventory_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_inventory_plan`
--

DROP TABLE IF EXISTS `wms_inventory_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_inventory_plan` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `plan_name` varchar(64) DEFAULT NULL COMMENT '盘点名称',
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司id',
  `warehouse_ids` varchar(512) DEFAULT NULL COMMENT '仓库ids',
  `inventory_method` tinyint(1) DEFAULT NULL COMMENT '盘点方法 (明盘、盲盘)',
  `storage_status` varchar(32) DEFAULT NULL COMMENT '库存状态（合格、残次、特采）',
  `plan_time` datetime DEFAULT NULL COMMENT '计划时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态(待盘点、盘点中、审批中、已通过、未通过)',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `reviewer_id` varchar(32) DEFAULT NULL COMMENT '审核人ID',
  `reviewed_by` varchar(32) DEFAULT NULL COMMENT '审核人',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='盘点计划表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_inventory_plan`
--

LOCK TABLES `wms_inventory_plan` WRITE;
/*!40000 ALTER TABLE `wms_inventory_plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_inventory_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_item_attribute`
--

DROP TABLE IF EXISTS `wms_item_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_item_attribute` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司ID',
  `item_code` varchar(32) DEFAULT NULL COMMENT '物料CODE',
  `up_quality` decimal(9,0) DEFAULT NULL COMMENT '上限数量',
  `down_quality` decimal(9,0) DEFAULT NULL COMMENT '下限数量',
  `remind_day` smallint DEFAULT '0' COMMENT '提前预警天数',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `limited_status` tinyint(1) DEFAULT NULL COMMENT '限量状态（0-正常 1-超过上限 2-超过下限）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='物料仓储属性表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_item_attribute`
--

LOCK TABLES `wms_item_attribute` WRITE;
/*!40000 ALTER TABLE `wms_item_attribute` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_item_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_item_storage`
--

DROP TABLE IF EXISTS `wms_item_storage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_item_storage` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司ID',
  `warehouse_id` varchar(32) DEFAULT NULL COMMENT '仓库ID',
  `item_code` varchar(32) DEFAULT NULL COMMENT '物料编码',
  `item_name` varchar(128) DEFAULT NULL COMMENT '物料名称',
  `unit` varchar(32) DEFAULT NULL COMMENT '单位',
  `spec` varchar(128) DEFAULT NULL COMMENT '规格型号',
  `quantity` decimal(20,6) DEFAULT NULL COMMENT '总数量',
  `actual_price` decimal(20,6) DEFAULT NULL COMMENT '实时单价',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `storage_price` decimal(20,6) DEFAULT NULL COMMENT '库存金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='库存汇总表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_item_storage`
--

LOCK TABLES `wms_item_storage` WRITE;
/*!40000 ALTER TABLE `wms_item_storage` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_item_storage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_item_storage_detail`
--

DROP TABLE IF EXISTS `wms_item_storage_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_item_storage_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `storage_id` varchar(32) DEFAULT NULL COMMENT '库存ID',
  `batch_no` varchar(32) DEFAULT NULL COMMENT '物料批号',
  `quantity` decimal(20,6) DEFAULT NULL COMMENT '数量',
  `location_id` varchar(32) DEFAULT NULL COMMENT '货位ID',
  `storage_status` tinyint(1) DEFAULT NULL COMMENT '库存状态（0-合格，1-残次）''',
  `work_status` tinyint(1) DEFAULT NULL COMMENT '作业状态（0-正常，1-作业中）',
  `product_date` datetime DEFAULT NULL COMMENT '生产日期',
  `expire_date` datetime DEFAULT NULL COMMENT '过期日期',
  `thermosphere` varchar(16) DEFAULT NULL COMMENT '温层',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `relate_type` tinyint(1) DEFAULT NULL COMMENT '关联业务类型（未关联、上架中、待发货...）',
  `relate_id` varchar(32) DEFAULT NULL COMMENT '关联业务ID',
  `shelf_life_status` tinyint(1) DEFAULT '0' COMMENT '保质期状态 （0-正常 1-临期 2-过期）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='库存详情表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_item_storage_detail`
--

LOCK TABLES `wms_item_storage_detail` WRITE;
/*!40000 ALTER TABLE `wms_item_storage_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_item_storage_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_month_check_out`
--

DROP TABLE IF EXISTS `wms_month_check_out`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_month_check_out` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司id',
  `year_months` varchar(32) DEFAULT NULL COMMENT '年份-月份',
  `year_str` varchar(8) DEFAULT NULL COMMENT '年份',
  `month_str` varchar(8) DEFAULT NULL COMMENT '月份',
  `item_code` varchar(32) DEFAULT NULL COMMENT '物料编码',
  `item_name` varchar(100) DEFAULT NULL COMMENT '物料名称',
  `item_spec` varchar(32) DEFAULT NULL COMMENT '规格',
  `item_unit` varchar(32) DEFAULT NULL COMMENT '单位',
  `is_delete` tinyint(1) DEFAULT NULL COMMENT '是否删除 （0-正常 1-已经删除）',
  `opening_quantity` decimal(20,6) DEFAULT NULL COMMENT '期初数量',
  `opening_price` decimal(20,6) DEFAULT NULL COMMENT '期初价格',
  `opening_total` decimal(20,6) DEFAULT NULL COMMENT '期初总额',
  `ending_quantity` decimal(20,6) DEFAULT NULL COMMENT '期末库存',
  `ending_price` decimal(20,6) DEFAULT NULL COMMENT '期末价格',
  `ending_total` decimal(20,6) DEFAULT NULL COMMENT '期末总额',
  `in_quantity` decimal(20,6) DEFAULT NULL COMMENT '本月入库数量',
  `in_price` decimal(20,6) DEFAULT NULL COMMENT '入库平均价格',
  `in_total` decimal(20,6) DEFAULT NULL COMMENT '入库总额',
  `out_quantity` decimal(20,6) DEFAULT NULL COMMENT '本月出库数量',
  `out_price` decimal(20,6) DEFAULT NULL COMMENT '出库平均价格',
  `out_total` decimal(20,6) DEFAULT NULL COMMENT '出库总额',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='月结表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_month_check_out`
--

LOCK TABLES `wms_month_check_out` WRITE;
/*!40000 ALTER TABLE `wms_month_check_out` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_month_check_out` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_picking_work_notice_detail`
--

DROP TABLE IF EXISTS `wms_picking_work_notice_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_picking_work_notice_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `work_notice_id` varchar(32) DEFAULT NULL COMMENT '作业通知id',
  `notice_detail_id` varchar(32) DEFAULT NULL COMMENT '出库通知明细ID',
  `item_code` varchar(32) DEFAULT NULL COMMENT '物料编码',
  `batch_no` varchar(32) DEFAULT NULL COMMENT '物料批次',
  `source_warehouse_id` varchar(32) DEFAULT NULL COMMENT '来源仓库ID',
  `source_location_id` varchar(32) DEFAULT NULL COMMENT '来源货位id',
  `quantity` decimal(20,6) DEFAULT NULL COMMENT '通知数量',
  `allot_quantity` decimal(20,6) DEFAULT NULL COMMENT '完成数量',
  `lack_quantity` decimal(20,6) DEFAULT NULL COMMENT '缺货数量',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='拣货作业通知明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_picking_work_notice_detail`
--

LOCK TABLES `wms_picking_work_notice_detail` WRITE;
/*!40000 ALTER TABLE `wms_picking_work_notice_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_picking_work_notice_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_receive_item_detail`
--

DROP TABLE IF EXISTS `wms_receive_item_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_receive_item_detail` (
  `id` varchar(32) NOT NULL,
  `receive_notice_detail_id` varchar(32) DEFAULT NULL COMMENT '入库通知货品ID ',
  `receive_notice_id` varchar(32) DEFAULT NULL COMMENT '入库通知ID',
  `item_code` varchar(32) DEFAULT NULL COMMENT '货品编码',
  `item_name` varchar(128) DEFAULT NULL COMMENT '货品名称',
  `unit` varchar(32) DEFAULT NULL COMMENT '单位',
  `quantity` decimal(20,6) DEFAULT NULL COMMENT '分配数量',
  `pass_quantity` decimal(20,6) DEFAULT NULL COMMENT '合格数量',
  `fail_quantity` decimal(20,6) DEFAULT NULL COMMENT '不合格数量',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态',
  `product_date` datetime DEFAULT NULL COMMENT '生产日期',
  `batch_no` varchar(32) DEFAULT NULL COMMENT '批次',
  `price` decimal(20,6) DEFAULT NULL COMMENT '货品单价',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `work_status` tinyint(1) DEFAULT NULL COMMENT '作业状态',
  `expire_time` datetime DEFAULT NULL COMMENT '过期日期',
  `refund_quantity` decimal(20,6) DEFAULT NULL COMMENT '退货数量',
  `deposit_quantity` decimal(20,6) DEFAULT NULL COMMENT '上架数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='入库货物明细表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_receive_item_detail`
--

LOCK TABLES `wms_receive_item_detail` WRITE;
/*!40000 ALTER TABLE `wms_receive_item_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_receive_item_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_receive_notice`
--

DROP TABLE IF EXISTS `wms_receive_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_receive_notice` (
  `id` varchar(32) NOT NULL,
  `business_id` varchar(32) DEFAULT NULL COMMENT '业务系统单号',
  `business_type` varchar(32) DEFAULT NULL COMMENT '业务单据类型',
  `notice_desc` varchar(255) DEFAULT NULL COMMENT '单据说明',
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司ID',
  `status` tinyint(1) DEFAULT NULL COMMENT '通知状态',
  `start_time` datetime DEFAULT NULL COMMENT '收货开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '收货完成时间',
  `notice_user` varchar(32) DEFAULT NULL COMMENT '通知人',
  `notice_time` datetime DEFAULT NULL COMMENT '通知时间',
  `from_code` varchar(32) DEFAULT NULL COMMENT '发出编码',
  `from_name` varchar(128) DEFAULT NULL COMMENT '发出方名称',
  `dept_name` varchar(32) DEFAULT NULL COMMENT '通知部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `receive_send_type` varchar(16) DEFAULT NULL COMMENT '收发类型',
  `task_id` varchar(32) DEFAULT NULL COMMENT '任务ID',
  `scrap_special_flag` tinyint(1) DEFAULT NULL COMMENT '报废/ 特采',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='入库通知表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_receive_notice`
--

LOCK TABLES `wms_receive_notice` WRITE;
/*!40000 ALTER TABLE `wms_receive_notice` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_receive_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_receive_notice_detail`
--

DROP TABLE IF EXISTS `wms_receive_notice_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_receive_notice_detail` (
  `id` varchar(32) NOT NULL,
  `receive_notice_id` varchar(32) DEFAULT NULL COMMENT '入库通知ID',
  `quantity` decimal(20,6) DEFAULT NULL COMMENT '通知数量',
  `receive_quantity` decimal(20,6) DEFAULT NULL COMMENT '收货数量',
  `allot_quantity` decimal(20,6) DEFAULT NULL COMMENT '分配数量',
  `product_date` datetime DEFAULT NULL COMMENT '生产日期',
  `item_code` varchar(32) DEFAULT NULL COMMENT '货品编码',
  `item_name` varchar(128) DEFAULT NULL COMMENT '货品名称',
  `unit` varchar(32) DEFAULT NULL COMMENT '单位',
  `spec` varchar(32) DEFAULT NULL COMMENT '货品规格',
  `batch_no` varchar(32) DEFAULT NULL COMMENT '货品批次',
  `business_quantity` decimal(20,6) DEFAULT NULL COMMENT '业务数量',
  `busines_unit` varchar(32) DEFAULT NULL COMMENT '业务单位',
  `belongs` varchar(32) DEFAULT NULL COMMENT '所属单位类型 ',
  `conversion_rate` decimal(20,8) DEFAULT NULL COMMENT '换算率',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `location_id` varchar(32) DEFAULT NULL COMMENT '待检位置',
  `item_type` tinyint(1) DEFAULT NULL COMMENT '货品类型',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态',
  `check_accept_id` varchar(32) DEFAULT NULL COMMENT '验收单ID',
  `item_price` decimal(20,6) DEFAULT NULL COMMENT '物料单价',
  `expire_time` datetime DEFAULT NULL COMMENT '过期日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='入库通知明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_receive_notice_detail`
--

LOCK TABLES `wms_receive_notice_detail` WRITE;
/*!40000 ALTER TABLE `wms_receive_notice_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_receive_notice_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_storage_in_out_detail`
--

DROP TABLE IF EXISTS `wms_storage_in_out_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_storage_in_out_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `recode_id` varchar(32) DEFAULT NULL COMMENT '记录ID',
  `location_id` varchar(32) DEFAULT NULL COMMENT '库位ID',
  `warehouse_id` varchar(32) DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(32) DEFAULT NULL COMMENT '库别（仓库编码）',
  `item_code` varchar(32) DEFAULT NULL COMMENT '物料编码',
  `quantity` decimal(20,6) DEFAULT NULL COMMENT '数量',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='出入库记录详情';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_storage_in_out_detail`
--

LOCK TABLES `wms_storage_in_out_detail` WRITE;
/*!40000 ALTER TABLE `wms_storage_in_out_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_storage_in_out_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_storage_in_out_record`
--

DROP TABLE IF EXISTS `wms_storage_in_out_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_storage_in_out_record` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司ID',
  `item_code` varchar(32) DEFAULT NULL COMMENT '物料编码',
  `item_name` varchar(128) DEFAULT NULL COMMENT '物料名称',
  `batch_no` varchar(32) DEFAULT NULL COMMENT '物料批次',
  `quantity` decimal(20,6) DEFAULT NULL COMMENT '数量',
  `type` tinyint(1) DEFAULT NULL COMMENT '类型（入库、出库）',
  `unit` varchar(32) DEFAULT NULL COMMENT '单位',
  `spec` varchar(32) DEFAULT NULL COMMENT '规格型号',
  `before_rolling_price` decimal(20,6) DEFAULT NULL COMMENT '滚算前单价',
  `before_rolling_quantity` decimal(20,6) DEFAULT NULL COMMENT '滚算前库存数',
  `before_rolling_total_price` decimal(20,6) DEFAULT NULL COMMENT '滚算前库存金额',
  `opening_quantity` decimal(20,6) DEFAULT NULL COMMENT '期初数量（物料批次原库存数量）',
  `opening_amount` decimal(20,6) DEFAULT NULL COMMENT '期初金额 （物料批次原库存金额）',
  `occ_price` decimal(20,6) DEFAULT NULL COMMENT '发生时单价',
  `occ_quantity` decimal(20,6) DEFAULT NULL COMMENT '发生时数量',
  `occ_total_price` decimal(20,6) DEFAULT NULL COMMENT '发生时总价',
  `after_rolling_price` decimal(20,6) DEFAULT NULL COMMENT '滚算后单价',
  `after_rolling_quantity` decimal(20,6) DEFAULT NULL COMMENT '滚算后库存数量',
  `after_rolling_total_price` decimal(20,6) DEFAULT NULL COMMENT '滚算后库存金额',
  `surplus_quantity` decimal(20,6) DEFAULT NULL COMMENT '结存数量（物料批次最新数量）',
  `surplus_amount` decimal(20,6) DEFAULT NULL COMMENT '结存金额（物料批次最新金额）',
  `business_no` varchar(32) DEFAULT NULL COMMENT '业务编号',
  `notice_id` varchar(32) DEFAULT NULL COMMENT '通知ID',
  `occ_time` datetime DEFAULT NULL COMMENT '发生时间（出/入库选择时间）',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `bill_type` tinyint(1) DEFAULT NULL COMMENT '单据类型(入库、退料、领料）',
  `receive_send_type` varchar(16) DEFAULT NULL COMMENT '收发类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='出入库记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_storage_in_out_record`
--

LOCK TABLES `wms_storage_in_out_record` WRITE;
/*!40000 ALTER TABLE `wms_storage_in_out_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_storage_in_out_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_work_notice`
--

DROP TABLE IF EXISTS `wms_work_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_work_notice` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `notice_item_detail_id` varchar(32) DEFAULT NULL COMMENT '入库通知明细ID',
  `notice_id` varchar(32) DEFAULT NULL COMMENT '入库通知ID',
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司ID',
  `type` tinyint(1) DEFAULT NULL COMMENT '类型(上架、拣货)',
  `receive_send_type` varchar(16) DEFAULT NULL COMMENT '收发类型',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态',
  `work_start_time` datetime DEFAULT NULL COMMENT '作业开始时间',
  `work_end_time` datetime DEFAULT NULL COMMENT '作业完成时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='作业通知';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_work_notice`
--

LOCK TABLES `wms_work_notice` WRITE;
/*!40000 ALTER TABLE `wms_work_notice` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_work_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_work_notice_allot`
--

DROP TABLE IF EXISTS `wms_work_notice_allot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_work_notice_allot` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `work_notice_id` varchar(32) DEFAULT NULL COMMENT '作业通知ID',
  `work_notice_detail_id` varchar(32) DEFAULT NULL COMMENT '作业通知明细ID',
  `item_code` varchar(32) DEFAULT NULL COMMENT '物料编码',
  `batch_no` varchar(32) DEFAULT NULL COMMENT '批次',
  `source_location_id` varchar(32) DEFAULT NULL COMMENT '来源货位id',
  `target_location_id` varchar(32) DEFAULT NULL COMMENT '目标货位id',
  `warehouse_id` varchar(32) DEFAULT NULL COMMENT '目标仓库ID',
  `quantity` decimal(20,6) DEFAULT NULL COMMENT '分配数量',
  `status` tinyint(1) DEFAULT NULL COMMENT '作业状态',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='作业通知明细分配';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_work_notice_allot`
--

LOCK TABLES `wms_work_notice_allot` WRITE;
/*!40000 ALTER TABLE `wms_work_notice_allot` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_work_notice_allot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_work_notice_detail`
--

DROP TABLE IF EXISTS `wms_work_notice_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_work_notice_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `work_notice_id` varchar(32) DEFAULT NULL COMMENT '作业通知id',
  `notice_detail_id` varchar(32) DEFAULT NULL COMMENT '入库通知明细ID',
  `notice_item_detail_id` varchar(32) DEFAULT NULL COMMENT '入库通知分配明细ID',
  `item_code` varchar(32) DEFAULT NULL COMMENT '物料编码',
  `batch_no` varchar(32) DEFAULT NULL COMMENT '物料批次',
  `source_location_id` varchar(32) DEFAULT NULL COMMENT '来源货位id',
  `quantity` decimal(20,6) DEFAULT NULL COMMENT '通知数量',
  `allot_quantity` decimal(20,6) DEFAULT NULL COMMENT '已分配数量',
  `examine_less_num` decimal(20,6) DEFAULT NULL COMMENT '质检扣除数量',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='作业通知明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_work_notice_detail`
--

LOCK TABLES `wms_work_notice_detail` WRITE;
/*!40000 ALTER TABLE `wms_work_notice_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_work_notice_detail` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-21 14:32:47
