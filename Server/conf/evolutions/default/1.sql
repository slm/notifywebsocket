# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table notification (
  id                        varchar(255) not null,
  title                     varchar(255),
  message                   varchar(255),
  constraint pk_notification primary key (id))
;

create sequence notification_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists notification;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists notification_seq;

