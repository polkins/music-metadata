create table artist
(
    uuid      uuid PRIMARY KEY,
    name      varchar,
    surname   varchar,
    pseudonym varchar unique,
    email     varchar,
    is_daily boolean default false
);

create table track
(
    id          bigint PRIMARY KEY,
    artist_uuid uuid REFERENCES artist (uuid),
    link        varchar,
    title       varchar,
    created     timestamp without time zone,
    genre       varchar,
    length      bigint,
    duration    bigint,
    content_type varchar
);

create table artist_of_the_day
(
    id          bigint PRIMARY KEY,
    artist_uuid uuid REFERENCES artist (uuid),
    date        date
);

CREATE SEQUENCE track_sequence START 1 INCREMENT BY 1;
CREATE SEQUENCE artist_of_the_day_sequence START 1 INCREMENT BY 1;
