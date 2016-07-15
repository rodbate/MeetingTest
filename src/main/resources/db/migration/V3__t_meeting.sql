

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

INSERT INTO t_meeting VALUES (1,'测试会议1',1468396800,1468400400,1,1),(2,'测试会议2',1468396800,1468400400,2,2),(3,'测试会议3',1468396800,1468400400,3,3),(4,'测试会议4',1468288800,1468292400,4,4),(5,'测试会议5',1468288800,1468292400,5,5),(6,'测试会议6',1468292400,1468296000,3,6);