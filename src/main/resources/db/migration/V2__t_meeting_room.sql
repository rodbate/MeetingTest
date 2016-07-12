

create table if not exists t_meeting_room (
    id int auto_increment primary key,
    name varchar(20) not null unique
);


INSERT INTO t_meeting_room VALUES (1,"会议室一"),(2,"会议室二"),(3,"会议室三"),(4,"会议室四"),(5,"会议室五");