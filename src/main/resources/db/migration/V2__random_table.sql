
drop table if exists myTable;
create table myTable (
    id bigint,
    name varchar(255) not null,
    address varchar(255),
    age bigint not null,
    primary key (id)

)engine=InnoDB;