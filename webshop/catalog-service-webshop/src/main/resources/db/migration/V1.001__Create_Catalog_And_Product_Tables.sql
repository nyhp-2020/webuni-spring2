create table category (id int8 not null, name varchar(255), primary key (id));
create table product (id int8 not null, name varchar(255), price float8 not null, category_id int8, primary key (id));
alter table if exists product add constraint FK1mtsbur82frn64de7balymq9s foreign key (category_id) references category;
