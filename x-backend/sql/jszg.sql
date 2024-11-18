create database if not exists jszg_db;
use jszg_db;

-- 战车id表 熊熙
drop table if exists chariot_id;
create table chariot_id (
                            chariot_id  int auto_increment primary key comment '战车id',
                            chariot_name varchar(64) comment '战车名',
                            ip_address varchar(64) comment '战车ip',
                            create_time datetime default CURRENT_TIMESTAMP comment '创建时间'
) comment '战车id表';

-- 用户信息表 秦艺恺
drop table if exists user_info;
create table user_info (
                           user_id int auto_increment primary key comment '用户ID，自增主键',
                           username varchar(64) not null comment '用户名',
                           password varchar(64) not null comment '用户密码',
                           user_type varchar(64) not null comment '用户类型，可能值为开发者、指挥员、操作员等',
                           status varchar(64) not null comment '用户状态，正常或禁用',
                           last_login_time datetime comment '最后登录时间',
                           login_ip varchar(64) comment '登录设备IP'
) comment '用户信息表';

-- 故障类型表 刘虹
drop table if exists fault_type;
create table fault_type(
                           auto_id int auto_increment primary key comment '自增主键id',
                           fault_id varchar(64) not null comment '故障类型ID',
                           fault_name varchar(64) not null comment '故障类型名称',
                           fault_description varchar(2048) comment '故障类型描述'
) comment '故障类型表';


-- 历史故障统计 刘虹
drop table if exists history_fault_list;
create table history_fault_list (
                                    auto_id int auto_increment primary key comment '自增主键id',
                                    chariot_id int not null comment '战车id ',
                                    fault_id varchar(64) not null comment '发生的故障类型',
                                    fault_name varchar(64) not null comment '故障发生的时间',
                                    is_solved varchar(64) comment '故障问题是否解决',
                                    FOREIGN KEY (chariot_id) REFERENCES chariot_id(chariot_id) ON DELETE CASCADE
) comment '历史故障统计';

-- 故障预测概率 刘虹
drop table if exists predict_fault_prob;
create table predict_fault_prob (
                                    auto_id int auto_increment primary key comment '自增主键id',
                                    chariot_id int not null comment '战车id ',
                                    fault_id varchar(64) not null comment '故障类型',
                                    fault_prob varchar(64) comment '预测的故障概率',
                                    FOREIGN KEY (chariot_id) REFERENCES chariot_id(chariot_id) ON DELETE CASCADE
) comment '故障预测概率';

-- 故障解决方案总结 刘虹
drop table if exists fault_plan_summary;
create table fault_plan_summary (
                                    auto_id int auto_increment primary key comment '自增主键id',
                                    fault_id varchar(64) not null comment '故障类型',
                                    plan_category int not null comment '0代表专家方案，1代表历史经验',
                                    problem_description varchar(2048) not null comment '问题描述',
                                    resolution text not null comment '解决建议',
                                    attn_id int not null comment '经办人ID ',
                                    FOREIGN KEY (attn_id) REFERENCES user_info(user_id) ON DELETE CASCADE
) comment '故障方案总结';

-- 战车信息表 张晨冉
drop table if exists ally_chariot_card;
create table ally_chariot_card (
                                   auto_id int auto_increment primary key comment '自增id主键',
                                   chariot_id int comment '战车id',
                                   chariot_name varchar(64) comment '战车名',
                                   chariot_desc varchar(512) comment '战车状态描述',
                                   oil_usage int comment '油量',
                                   bullet_usage int comment '弹药量',
                                   water_usage int comment '水量',
                                   temperature int comment '温度',
                                   speed int comment '速度',
                                   electricity int comment '电量',
                                   ip_address varchar(64) comment '战车ip',
                                   chariot_status int comment '战车故障状态描述 0：状态正常，1：轻微故障，2：严重故障',
                                   record_time datetime default CURRENT_TIMESTAMP comment '记录时间',
                                   FOREIGN KEY (chariot_id) REFERENCES chariot_id(chariot_id) ON DELETE CASCADE
) comment '我方战车信息卡';

-- 敌军战车信息卡 熊熙
drop table if exists enemy_chariot_card;
create table enemy_chariot_card (
                                    auto_id int auto_increment primary key comment '自增id主键',
                                    chariot_id int comment '战车id',
                                    chariot_desc varchar(512) comment '战车状态描述',
                                    chariot_longitude decimal(10, 5) comment '战车经度',
                                    chariot_latitude decimal(10, 5) comment '战车纬度',
                                    record_time datetime default CURRENT_TIMESTAMP comment '记录时间'
) comment '敌军战车信息卡';

-- 战车坐标 熊熙
drop table if exists ally_chariot_coord;
create table ally_chariot_coord (
                                    auto_id int auto_increment primary key comment '自增id主键',
                                    chariot_id int comment '战车id',
                                    chariot_longitude decimal(10, 5) comment '战车经度',
                                    chariot_latitude decimal(10, 5) comment '战车纬度',
                                    record_time datetime default CURRENT_TIMESTAMP comment '记录时间',
                                    FOREIGN KEY (chariot_id) REFERENCES chariot_id(chariot_id) ON DELETE CASCADE
) comment '我方战车坐标';

-- 其余状态 张晨冉
drop table if exists other_status;
create table other_status (
                              auto_id int auto_increment primary key comment '自增主键ID',
                              chariot_id int not null comment '战车ID',
                              main_status varchar(64) not null comment '主状态名',
                              attach_status varchar(64) not null comment '附状态名',
                              status_value varchar(64) not null comment '附状态值',
                              FOREIGN KEY (chariot_id) REFERENCES chariot_id(chariot_id) ON DELETE CASCADE
) comment '其余状态';

-- 战车硬件表 吴家霖
drop table if exists cluster_hardware_info;
create table cluster_hardware_info (
                                       auto_id int auto_increment primary key comment '自增主键id',
                                       chariot_id int not null comment '战车id',
                                       machine_status int not null comment '战车物理机状态(0表示运行，1表示停止，2表示故障)',
                                       cpu_usage decimal(5,2) comment '战车CPU占用率',
                                       memory_usage decimal(5,2) comment '战车内存占用率',
                                       disk_usage decimal(5,2) comment '战车硬盘占用率',
                                       FOREIGN KEY (chariot_id) REFERENCES chariot_id(chariot_id) ON DELETE CASCADE
) comment '战车硬件表';

-- 应用程序管理表 秦艺恺
create table application_management (
                                        app_id int auto_increment primary key comment '应用程序ID',
                                        app_path varchar(64) not null comment '安装包路径',
                                        app_name varchar(64) not null comment '软件名称',
                                        deployment_time datetime not null comment '部署时间',
                                        version_number varchar(10) not null comment '版本号',
                                        status varchar(64) comment '应用程序状态'
)comment '应用程序管理表';

-- 审计日志表 王颖璨
drop table if exists audit_log;
create table audit_log (
                           auto_id int auto_increment primary key comment '⾃增主键id',
                           log_level int not null comment '⽇志级别标识符（0：info 健康、1：warn 警告、2：error 报错、3：fatal 故障）',
                           log_time datetime not null comment '⽇志⽣成时间 (yyyy-mm-dd hh:mm:ss)',
                           log_message varchar(2048) comment '⽇志详情，描述⽇志内容',
                           user_id int comment '操作⽤户ID，系统内操作的发起⽤户信息',
                           user_name varchar(2048) comment '操作⽤户名',
                           user_ip varchar(256) comment '操作⽤户IP',
                           event_module varchar(2048) comment '系统内操作的发⽣功能模块',
                           event_operation varchar(2048) comment '操作名称',
                           event_result varchar(256) comment '操作结果',
                           chariot_id int comment '战⻋ID',
                           FOREIGN KEY (chariot_id) REFERENCES chariot_id(chariot_id) ON DELETE CASCADE,
                           FOREIGN KEY (user_id) REFERENCES user_info(user_id) ON DELETE CASCADE
) comment='审计⽇志表';

-- 异常日志表 王颖璨
drop table if exists exception_log;
create table exception_log (
                               auto_id int auto_increment primary key comment '⾃增主键id',
                               log_level int not null comment '⽇志级别标识符（0：info 健康、1：warn 警告、2：error 报错、3：fatal 故障）',
                               log_time datetime not null comment '⽇志⽣成时间 (yyyy-mm-dd hh:mm:ss)',
                               log_message varchar(2048) comment '⽇志详情，描述⽇志内容',
                               exception_id int comment '异常类型标识符',
                               chariot_id int comment '战⻋ID',
                               FOREIGN KEY (chariot_id) REFERENCES chariot_id(chariot_id) ON DELETE CASCADE
) comment='异常⽇志表';




-- 模拟数据
-- 战车id表
INSERT INTO chariot_id (chariot_id, chariot_name, ip_address, create_time) VALUES
                                                                               (1, '战车001', '192.168.1.1', '2024-01-01 08:00:00'),
                                                                               (2, '战车002', '192.168.1.2', '2024-01-02 09:00:00'),
                                                                               (3, '战车003', '192.168.1.3', '2024-01-03 10:00:00'),
                                                                               (4, '战车004', '192.168.1.4', '2024-01-04 11:00:00'),
                                                                               (5, '战车005', '192.168.1.5', '2024-01-05 12:00:00'),
                                                                               (6, '战车006', '192.168.1.6', '2024-01-06 13:00:00'),
                                                                               (7, '战车007', '192.168.1.7', '2024-01-07 14:00:00'),
                                                                               (8, '战车008', '192.168.1.8', '2024-01-08 15:00:00'),
                                                                               (9, '战车009', '192.168.1.9', '2024-01-09 16:00:00'),
                                                                               (10, '战车010', '192.168.1.10', '2024-01-10 17:00:00'),
                                                                               (11, '战车011', '192.168.1.11', '2024-01-11 18:00:00'),
                                                                               (12, '战车012', '192.168.1.12', '2024-01-12 19:00:00'),
                                                                               (13, '战车013', '192.168.1.13', '2024-01-13 20:00:00'),
                                                                               (14, '战车014', '192.168.1.14', '2024-01-14 21:00:00'),
                                                                               (15, '战车015', '192.168.1.15', '2024-01-15 22:00:00'),
                                                                               (16, '战车016', '192.168.1.16', '2024-01-16 23:00:00'),
                                                                               (17, '战车017', '192.168.1.17', '2024-01-17 00:00:00'),
                                                                               (18, '战车018', '192.168.1.18', '2024-01-18 01:00:00'),
                                                                               (19, '战车019', '192.168.1.19', '2024-01-19 02:00:00'),
                                                                               (20, '战车020', '192.168.1.20', '2024-01-20 03:00:00');

-- 用户数据表
INSERT INTO user_info (user_id, username, password, user_type, status, last_login_time, login_ip) VALUES
                                                                                                      (1, '熊熙', 'password001', '开发者', '正常', '2024-01-01 08:00:00', '192.168.1.1'),
                                                                                                      (2, '王颖璨', 'password002', '开发者', '正常', '2024-01-02 09:00:00', '192.168.1.2'),
                                                                                                      (3, '刘虹', 'password003', '开发者', '正常', '2024-01-03 10:00:00', '192.168.1.3'),
                                                                                                      (4, '张晨冉', 'password004', '开发者', '正常', '2024-01-04 11:00:00', '192.168.1.4'),
                                                                                                      (5, '吴家霖', 'password005', '开发者', '正常', '2024-01-05 12:00:00', '192.168.1.5'),
                                                                                                      (6, '秦艺恺', 'password006', '开发者', '正常', '2024-01-06 13:00:00', '192.168.1.6'),
                                                                                                      (7, '罗舒', 'password007', '开发者', '正常', '2024-01-07 14:00:00', '192.168.1.7'),
                                                                                                      (8, '秦琪钰', 'password008', '开发者', '正常', '2024-01-08 15:00:00', '192.168.1.8');

INSERT INTO ally_chariot_coord (chariot_id, chariot_longitude, chariot_latitude, record_time) VALUES
                                                                                                  (1, 116.39743, 39.90923, '2024-01-01 08:00:00'),
                                                                                                  (2, 116.40043, 39.91923, '2024-01-02 09:00:00'),
                                                                                                  (3, 116.40543, 39.91923, '2024-01-03 10:00:00'),
                                                                                                  (4, 116.41043, 39.91923, '2024-01-04 11:00:00'),
                                                                                                  (5, 116.41543, 39.91923, '2024-01-05 12:00:00'),
                                                                                                  (6, 116.42043, 39.90923, '2024-01-06 13:00:00'),
                                                                                                  (7, 116.42543, 39.89923, '2024-01-07 14:00:00'),
                                                                                                  (8, 116.43043, 39.88923, '2024-01-08 15:00:00'),
                                                                                                  (9, 116.43543, 39.87923, '2024-01-09 16:00:00'),
                                                                                                  (10, 116.44043, 39.86923, '2024-01-10 17:00:00'),
                                                                                                  (11, 116.44543, 39.85923, '2024-01-11 18:00:00'),
                                                                                                  (12, 116.45043, 39.84923, '2024-01-12 19:00:00'),
                                                                                                  (13, 116.45543, 39.83923, '2024-01-13 20:00:00'),
                                                                                                  (14, 116.46043, 39.82923, '2024-01-14 21:00:00'),
                                                                                                  (15, 116.46543, 39.81923, '2024-01-15 22:00:00'),
                                                                                                  (16, 116.47043, 39.80923, '2024-01-16 23:00:00'),
                                                                                                  (17, 116.47543, 39.79923, '2024-01-17 00:00:00'),
                                                                                                  (18, 116.48043, 39.78923, '2024-01-18 01:00:00'),
                                                                                                  (19, 116.48543, 39.77923, '2024-01-19 02:00:00'),
                                                                                                  (20, 116.49043, 39.76923, '2024-01-20 03:00:00');
INSERT INTO enemy_chariot_card (chariot_id, chariot_desc, chariot_longitude, chariot_latitude) VALUES
                                                                                                  (1, '战车行进速度较快', 116.39743, 39.90923),
                                                                                                  (2, '战车已经受损', 116.40043, 39.91923),
                                                                                                  (3, '战车防御能力极强', 116.40543, 39.91923),
                                                                                                  (4, '战车威胁程度高', 116.41043, 39.91923);