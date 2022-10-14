
create sequence hibernate_sequence start 1 increment 1;
create table course (id int8 not null, name varchar(255), primary key (id));
create table course_students (courses_id int8 not null, students_id int8 not null, primary key (courses_id, students_id));
create table course_teachers (courses_id int8 not null, teachers_id int8 not null, primary key (courses_id, teachers_id));
create table student (id int8 not null, birthdate date, name varchar(255), semester int4 not null, primary key (id));
create table teacher (id int8 not null, birthdate date, name varchar(255), primary key (id));
alter table if exists course_students add constraint FK532dg5ikp3dvbrbiiqefdoe6m foreign key (students_id) references student;
alter table if exists course_students add constraint FKh6irfl8rj4jgb3xrlyxsr2bdk foreign key (courses_id) references course;
alter table if exists course_teachers add constraint FKe3n62rwx3uc1yucgkmw6gjkm5 foreign key (teachers_id) references teacher;
alter table if exists course_teachers add constraint FK5ntgqv47cbgq8s0la1myeg5ly foreign key (courses_id) references course;
