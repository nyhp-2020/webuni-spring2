create table timetable (id int8 not null, day_of_week int4, end_time time, start_time time, course_id int8, primary key (id));
create table timetable_aud (id int8 not null, rev int4 not null, revtype int2, day_of_week int4, end_time time, start_time time, course_id int8, primary key (id, rev));
alter table if exists timetable add constraint FKrdljqbhvm52vnxoy3xapbkho0 foreign key (course_id) references course;
alter table if exists timetable_aud add constraint FK2dn7jd07tn6j62sygvaba54k0 foreign key (rev) references revinfo;