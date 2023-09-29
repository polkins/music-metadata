grant all on schema music_metadata to music_metadata;
grant all on table artist to music_metadata;
grant all on table track to music_metadata;
grant all on sequence track_sequence to music_metadata;
grant select, insert, update on table artist to music_metadata;
grant select, insert, update on table track to music_metadata;