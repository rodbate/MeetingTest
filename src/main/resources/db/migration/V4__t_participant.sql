

create table if not exists t_participant(
    id int auto_increment primary key,
    name varchar(10) not null,
    meetingId int
);

insert into t_participant values (null, "jack", 1),(null, "lily", 1),(null, "lucy", 2);