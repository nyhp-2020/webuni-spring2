create sequence hibernate_sequence start 1 increment 1;
create table course (id int8 not null, name varchar(255), primary key (id));
create table student (id int8 not null, birthdate date, name varchar(255), semester int4 not null, course_id int8, primary key (id));
create table teacher (id int8 not null, birthdate date, name varchar(255), course_id int8, primary key (id));
alter table if exists student add constraint FKdfypyqt0stgfc0aij9kcxm99s foreign key (course_id) references course;
alter table if exists teacher add constraint FKf75wvk4ch3gnhje998pq0lcid foreign key (course_id) references course;
