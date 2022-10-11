create table course (id int8 not null, name varchar(255), primary key (id));
alter table student add column course_id int8;
alter table teacher add column course_id int8;
alter table if exists student add constraint FKdfypyqt0stgfc0aij9kcxm99s foreign key (course_id) references course;
alter table if exists teacher add constraint FKf75wvk4ch3gnhje998pq0lcid foreign key (course_id) references course;