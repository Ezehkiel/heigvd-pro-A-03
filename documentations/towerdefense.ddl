-- *********************************************
-- * SQL PostgreSQL generation                 
-- *--------------------------------------------
-- * DB-MAIN version: 10.0.3              
-- * Generator date: Aug 17 2017              
-- * Generation date: Tue Mar 26 10:49:19 2019 
-- * LUN file: D:\Nas Brownies\0.3 HEIG\Semestre 4\PRO\projet_semestre\documentations\towerdefense.lun 
-- * Schema: Relationnel/1 
-- ********************************************* 


-- Database Section
-- ________________ 

create database Relationnel;


-- Tables Section
-- _____________ 

create table user (
     id numeric(3) not null,
     username varchar(20) not null,
     password varchar(50) not null,
     mail varchar(50) not null,
     constraint ID_user primary key (id));


-- Constraints Section
-- ___________________ 


-- Index Section
-- _____________ 

