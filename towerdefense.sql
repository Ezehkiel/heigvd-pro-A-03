CREATE ROLE toweruser LOGIN
  ENCRYPTED PASSWORD 'md5a5d8633fcd701defa2d28e89b9c012cf'
  SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION;

CREATE DATABASE towerdefense
  WITH OWNER = toweruser;

\connect towerdefense

  CREATE TABLE public.towerdefense_user
  (
      id SERIAL,
      username character varying(20) NOT NULL,
      password character varying(64) NOT NULL,
      nbpartiejoue integer DEFAULT 0,
      nbpartiegagne integer DEFAULT 0,
      lastLogin timestamp without time zone,
      CONSTRAINT towerdefense_user_pkey PRIMARY KEY (id),
      CONSTRAINT towerdefense_user_username_key UNIQUE (username)

  )
  WITH (
      OIDS = FALSE
  );
