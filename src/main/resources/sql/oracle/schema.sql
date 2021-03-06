drop table ss_task;
drop table ss_user;
drop table ss_hospital;

create table ss_task (
	id number(19,0),
	title varchar2(128) not null,
	description varchar2(255),
	user_id number(19,0) not null,
	primary key (id)
);

create table ss_user (
	id number(19,0),
	login_name varchar2(64) not null unique,
	name varchar2(64) not null,
	password varchar2(255) not null,
	salt varchar2(64) not null,
	roles varchar2(255) not null,
	register_date date not null,
	primary key (id)
);

create table ss_hospital (
	id number(19,0),
	name varchar2(50) not null unique,
	address varchar2(200) not null,
	phone varcha2r(20) not null,
	level varchar2(10) not null,
	website varchar2(100) not null,
	user_id number(19,0) not null,
	primary key (id)
);

create sequence ss_seq_task start with 100 increment by 20;
create sequence ss_seq_user start with 100 increment by 20;
create sequence ss_seq_hospital start with 100 increment by 20;