create table "Periodicity"
(
  per_id    serial      not null
    constraint periodicity_pk
    primary key,
  frequency varchar(20) not null,
  value     integer     not null
);

create table "Person"
(
  person_id    serial      not null
    constraint person_pk
    primary key,
  name         varchar(30) not null,
  surname      varchar(50) not null,
  email        varchar(50) not null,
  password     text        not null,
  birthday     date,
  phone        varchar(20),
  photo        text,
  notification integer
    constraint "Person_fk0"
    references "Periodicity",
  role         text        not null
);

create table "Friend"
(
  sender       integer not null
    constraint "Friend_fk0"
    references "Person",
  recipient    integer not null
    constraint "Friend_fk1"
    references "Person",
  "isAccepted" boolean not null
);

create table "Type"
(
  type_id serial      not null
    constraint type_pk
    primary key,
  value   varchar(20) not null
);

create table "Item"
(
  item_id     serial  not null
    constraint item_pk
    primary key,
  person      integer not null
    constraint "Item_fk0"
    references "Person",
  booker      integer
    constraint "Item_fk1"
    references "Person",
  name        text    not null,
  description text    not null,
  image       text,
  link        text,
  due_date    date,
  priority    integer,
  root        integer
    constraint "Item_fk2"
    references "Item"
);

create table "Tag"
(
  name text not null
    constraint tag_pk
    primary key
);

create table "ItemTag"
(
  item integer not null
    constraint "ItemTag_fk0"
    references "Item",
  tag  text    not null
    constraint "ItemTag_fk1"
    references "Tag"
);

create table "Folder"
(
  folder_id serial  not null
    constraint folder_pk
    primary key,
  name      text    not null,
  creator   integer not null
    constraint "Folder_fk0"
    references "Person"
);

create table "Event"
(
  event_id      serial  not null
    constraint event_pk
    primary key,
  name          text    not null,
  description   text    not null,
  creator       integer not null
    constraint "Event_fk0"
    references "Person",
  start_date    date,
  end_date      date,
  latitude      double precision,
  longitude     double precision,
  place_address text,
  periodicity   integer
    constraint "Event_fk1"
    references "Periodicity",
  type          bigint  not null
    constraint "Event_fk2"
    references "Type",
  is_draft      boolean,
  folder        integer
    constraint "Event_fk3"
    references "Folder",
  photo         text
);

create table "Participant"
(
  person      integer not null
    constraint "Participant_fk0"
    references "Person",
  event       integer not null
    constraint "Participant_fk1"
    references "Event",
  priority    integer,
  countdown   boolean,
  is_accepted boolean not null
);

create table "PersonFolder"
(
  person_id integer not null
    constraint "PersonFolder_fk0"
    references "Person",
  folder_id integer not null
    constraint "PersonFolder_fk1"
    references "Folder"
);

create table "Chat"
(
  chat_id  serial  not null
    constraint chat_pk
    primary key,
  name     text    not null,
  event_id integer not null
    constraint "Chat_fk0"
    references "Event"
);

create table "Message"
(
  message_id serial  not null
    constraint message_pk
    primary key,
  text       text    not null,
  date       date    not null,
  sender     integer not null,
  chat       integer not null
);

create table "PersonChat"
(
  person_id         integer not null
    constraint "PersonChat_fk0"
    references "Person",
  chat_id           integer not null
    constraint "PersonChat_fk1"
    references "Chat",
  last_read_message integer not null
    constraint "PersonChat_fk2"
    references "Message"
);

create table "Verif_token"
(
  token_id text not null
    constraint verif_token_pk
    primary key,
  user_id  text   not null,
  name     text   not null,
  surname  text   not null,
  email    text   not null,
  password text   not null,
  role     text   not null,
  birthday date,
  phone    text
);
