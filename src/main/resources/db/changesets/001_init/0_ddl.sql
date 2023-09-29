create table artist
(
    uuid      uuid PRIMARY KEY,
    name      varchar,
    surname   varchar,
    pseudonym varchar,
    email     varchar
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
    contentType varchar
);

CREATE SEQUENCE track_sequence START 1 INCREMENT BY 1;
