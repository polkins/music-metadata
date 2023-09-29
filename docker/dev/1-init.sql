drop schema if exists music_metadata cascade;

drop user if exists music_metadata;

-- schema owner
CREATE USER music_metadata WITH password 'music_metadata';

-- create schema
CREATE SCHEMA music_metadata AUTHORIZATION music_metadata;

GRANT USAGE ON SCHEMA music_metadata TO music_metadata;

ALTER DEFAULT PRIVILEGES FOR USER music_metadata IN SCHEMA music_metadata GRANT SELECT,INSERT,UPDATE,DELETE,TRUNCATE ON TABLES TO music_metadata;
ALTER DEFAULT PRIVILEGES FOR USER music_metadata IN SCHEMA music_metadata GRANT USAGE ON SEQUENCES TO music_metadata;
ALTER DEFAULT PRIVILEGES FOR USER music_metadata IN SCHEMA music_metadata GRANT EXECUTE ON FUNCTIONS TO music_metadata;
