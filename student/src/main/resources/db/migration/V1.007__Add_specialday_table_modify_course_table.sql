create table special_day_aud (id int4 not null, rev int4 not null, revtype int2, source_day date, target_day date, primary key (id, rev));
create table special_day (id int4 not null, source_day date, target_day date, primary key (id));

alter table course add column semester_type int4 default 0, add column year int4 default 2022;
alter table course_aud add column semester_type int4 default 0, add column year int4 default 2022;

alter table if exists special_day_aud add constraint FKke1jl33xwvveiw7wvsmupf177 foreign key (rev) references revinfo;