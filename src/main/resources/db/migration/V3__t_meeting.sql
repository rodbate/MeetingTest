

create table if not exists t_meeting (
    id int auto_increment primary key,
    name varchar(20) unique not null,
    startTime bigint not null,
    endTime bigint not null,
    meetingRoomId int,
    hostId int,
    foreign key(meetingRoomId) references t_meeting_room(id),
    foreign key(hostId) references t_employee(id)
);

insert into t_meeting values (null, "测试会议1", 1468292400, 1468310400, 1, 1);
insert into t_meeting values (null, "测试会议2", 1468292400, 1468310400, 2, 2);
insert into t_meeting values (null, "测试会议3", 1468292400, 1468310400, 3, 3);
insert into t_meeting values (null, "测试会议4", 1468396855, 1468397055, 4, 4);
insert into t_meeting values (null, "测试会议5", 1468396855, 1468399055, 5, 5);
insert into t_meeting values (null, "测试会议6", 1468396855, 1468398055, 3, 6);