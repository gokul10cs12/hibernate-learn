
drop table if exists author;
drop table if exists hibernate_sequence;
create table author
(
    id  bigint not null auto_increment,
    first_name varchar(255),
    last_name varchar(255),
    primary key (id)
) engine = InnoDB;

create table book
(
    id        bigint not null auto_increment primary key,
    isbn      varchar(255),
    publisher varchar(255),
    title     varchar(255),
    author_id bigint
) engine = InnoDB;
