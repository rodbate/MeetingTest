

create table if not exists meetingt (
    id int auto_increment primary key,
    name varchar(30) not null,
    status int default 0
)

INSERT INTO `meetingt` VALUES (1,'测试会议',1),(2,'测试会议2',1),(3,'测试会议3',1);