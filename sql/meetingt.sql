
create table if not exists meetingt (
    id int auto_increment primary key,
    name varchar(30) not null,
    status int default 0
)