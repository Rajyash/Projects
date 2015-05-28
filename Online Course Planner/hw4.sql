use cs320stu08;
drop table if exists courses;
drop table if exists users;
create table courses (
    id integer auto_increment primary key,
    course_code varchar(255),
    course_name varchar(255),
    prerequisites varchar(255)
);

insert into courses values (1, 'CS120', 'Introduction to Web Site Development', '');
insert into courses values (2, 'CS122', 'Using Relational Databases and SQL' , '');
insert into courses values (3, 'CS201', 'Introduction to Programming', '');
insert into courses values (4, 'CS202', 'Introduction to Object Oriented Programming', 'CS201');
insert into courses values (5, 'CS203', 'Programming with Data Structures', 'CS202');
insert into courses values (6, 'CS320', 'Web and Internet Programming', 'CS120 CS122 CS203');

create table users(
    id integer auto_increment primary key,
    user_name varchar(255) NOT NULL UNIQUE,
    password varchar(255),
    retype_password varchar(255),
    first_name varchar(255),
    last_name varchar(255)
);

insert into users (user_name, password, retype_password, first_name, last_name) values ('cysun','abcd','abcd','chengyu','sun');
insert into users (user_name, password, retype_password, first_name, last_name) values ('cs320stu31', 'abcd', 'abcd', '', '');

