create table temp_users
as select * from users
where 1=2;

alter table temp_users
drop column user_id;