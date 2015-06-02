DROP DATABASE IF EXISTS `webanalitics`;

CREATE DATABASE `webanalitics`;

USE `webanalitics`;

CREATE TABLE `accounts`(
`id` bigint primary key auto_increment,
`login` varchar(50) not null unique,
`password` varchar(50) not null,
`email` varchar(50)
);

CREATE TABLE `sitecategories`(
`id` bigint primary key auto_increment,
`name` varchar(50) not null unique
);

CREATE TABLE `applications`(
`id` bigint primary key auto_increment,
`name` varchar(100) not null,
`domain` varchar(100) not null unique,
`sessionActiveMaxTimeMin` int not null,
`collectActivityOnPage` boolean not null,
`activitySendIntervalSec` int not null,
`cookieMaxTimeMin` int not null, 
`account_id` bigint not null,
`category_id` bigint,
foreign key (`account_id`) references `accounts`(`id`)
on update cascade on delete cascade,
foreign key (`category_id`) references `sitecategories`(`id`)
on update cascade on delete set null
);

CREATE TABLE `usersessions`(
`id` bigint primary key auto_increment,
`date` datetime not null,
`browser` varchar(50),
`cookieEnabled` boolean,
`screenHeight` int,
`screenWidth` int,
`application_id` bigint not null,
foreign key (`application_id`) references `applications`(`id`)
on update cascade on delete cascade
);

CREATE TABLE `activities`(
`id` bigint primary key auto_increment,
`type` varchar(20) not null,
`location` varchar(255) not null,
`referrer` varchar(255),
`responseTime` bigint,
`domTime` bigint,
`date` datetime not null,
`href` varchar(255),
`title` varchar(100),
`elementId` varchar(100),
`kind` varchar(100),
`session_id` bigint not null,
foreign key (`session_id`) references `usersessions`(`id`)
on update cascade on delete cascade
);