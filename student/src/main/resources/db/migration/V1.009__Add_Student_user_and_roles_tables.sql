create table student_user (username varchar(255) not null, facebook_id varchar(255), password varchar(255), primary key (username));
create table student_user_roles (student_user_username varchar(255) not null, roles varchar(255));
alter table if exists student_user_roles add constraint FKciq0uu7ofpj8a3ax40eghkj9v foreign key (student_user_username) references student_user;