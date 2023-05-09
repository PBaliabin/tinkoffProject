CREATE TABLE IF NOT EXISTS chat
(
    chat_id bigint,
    CONSTRAINT chat_pkey PRIMARY KEY (chat_id)
);

CREATE TABLE IF NOT EXISTS github_link
(
    link               varchar(200),
    repository_id      varchar(50),
    name               varchar(200),
    full_name          varchar(200),
    last_activity_time timestamp without time zone NOT NULL,
    forks_count        integer,
    open_issues_count  integer,
    last_check_time    timestamp without time zone NOT NULL DEFAULT now(),
    CONSTRAINT github_link_pkey PRIMARY KEY (link)
);

CREATE TABLE IF NOT EXISTS stackoverflow_link
(
    link               varchar(200),
    quota_max          integer,
    quota_remaining    integer,
    last_activity_time timestamp without time zone NOT NULL,
    is_answered        boolean,
    answer_count       integer,
    last_check_time    timestamp without time zone NOT NULL DEFAULT now(),
    CONSTRAINT stackoverflow_link_pkey PRIMARY KEY (link)
);

CREATE TABLE IF NOT EXISTS chat_to_link
(
    link    character varying(200),
    chat_id bigint,
    CONSTRAINT chat_to_link_pkey PRIMARY KEY (link, chat_id)
);

CREATE OR REPLACE FUNCTION updateGithubTableFunction() RETURNS TRIGGER AS
$$
BEGIN
    DELETE
    FROM github_link
    WHERE (SELECT count(*) FROM chat_to_link WHERE link = old.link) = 0
      AND link = old.link;
    RETURN null;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION updateStackoverflowTableFunction() RETURNS TRIGGER AS
$$
BEGIN
    DELETE
    FROM stackoverflow_link
    WHERE (SELECT count(*) FROM chat_to_link WHERE link = old.link) = 0
      AND link = old.link;
    RETURN null;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS updateGithubTable ON chat_to_link;
CREATE TRIGGER updateGithubTable
    AFTER DELETE
    ON chat_to_link
    FOR EACH ROW
EXECUTE PROCEDURE updateGithubTableFunction();

DROP TRIGGER IF EXISTS updateStackoverflowTable ON chat_to_link;
CREATE TRIGGER updateStackoverflowTable
    AFTER DELETE
    ON chat_to_link
    FOR EACH ROW
EXECUTE PROCEDURE updateStackoverflowTableFunction();
