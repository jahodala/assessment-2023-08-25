create table SUSERS
(
    user_id   int        not null,
    user_guid varchar(?) not null,
    user_name varchar(?) not null,
    primary key (user_id, user_guid)
);
CREATE INDEX IDX_SUSERS_USER_ID ON SUSERS (user_id);
CREATE INDEX IDX_SUSERS_USER_GUID ON SUSERS (user_guid);