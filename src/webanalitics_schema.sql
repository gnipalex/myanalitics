DROP DATABASE IF EXISTS `webanalitics`;

CREATE DATABASE `webanalitics`;

USE `webanalitics`;

CREATE TABLE `users`(
`id` bigint primary key auto_increment,
`login` varchar(100) not null unique,
`password` varchar(100) not null
);

CREATE TABLE `sites`(
`id` bigint primary key auto_increment,
`name` varchar(100) not null,
`domain` varchar(100) not null unique,
`user_id` bigint,
foreign key (`user_id`) references `users`(`id`)
on update cascade on delete cascade
);

CREATE TABLE `analitics`(
`id` bigint primary key auto_increment,
`location` varchar(255) not null,
`referrer` varchar(255),
`clientDate` datetime,
`responseTime` bigint,
`domTime` bigint,
`resourcesTime` bigint,
`cookieEnabled` boolean,
`sessionId` varchar(50),
`date` datetime not null,
`userAgent` varchar(50),
`site_id` bigint,
foreign key (`site_id`) references `sites`(`id`)
on update cascade on delete cascade
);