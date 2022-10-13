create table course (id int8 not null, name varchar(255), primary key (id));
alter table student add column courses_id int8;
alter table teacher add column courses_id int8;
