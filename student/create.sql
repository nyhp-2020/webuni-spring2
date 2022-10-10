create sequence hibernate_sequence start 1 increment 1;
create table student (id int8 not null, birthdate date, name varchar(255), semester int4 not null, primary key (id));
create table teacher (id int8 not null, birthdate date, name varchar(255), primary key (id));
