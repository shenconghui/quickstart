drop table if exists ss_task;
drop table if exists ss_user;
drop table if exists ss_hospital;

create table ss_task (
	id bigint auto_increment,
	title varchar(128) not null,
	description varchar(255),
	user_id bigint not null,
    primary key (id)
) engine=InnoDB;

create table ss_user (
	id bigint auto_increment,
	login_name varchar(64) not null unique,
	name varchar(64) not null,
	password varchar(255) not null,
	salt varchar(64) not null,
	roles varchar(255) not null,
	register_date timestamp not null default 0,
	primary key (id)
) engine=InnoDB;


create table ss_hospital (
	id bigint  auto_increment,
	name varchar(50) not null unique,
	address varchar(200) not null,
	phone varchar(20) not null,
	level varchar(10) not null,
	website varchar(100) not null,
	user_id bigint not null,
	primary key (id)
)engine=InnoDB;