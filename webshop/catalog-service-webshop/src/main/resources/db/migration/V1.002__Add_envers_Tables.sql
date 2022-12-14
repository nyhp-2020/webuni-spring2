create table category_aud (id int8 not null, rev int4 not null, revtype int2, name varchar(255), primary key (id, rev));
create table product_aud (id int8 not null, rev int4 not null, revtype int2, name varchar(255), price float8, category_id int8, primary key (id, rev));
create table revinfo (rev int4 not null, revtstmp int8, primary key (rev));

alter table if exists category_aud add constraint FKc9m640crhsib2ws80um6xuk1w foreign key (rev) references revinfo;
alter table if exists product_aud add constraint FK9vwllld6jlw5xys1ay911oh1x foreign key (rev) references revinfo;