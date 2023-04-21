CREATE TABLE IF NOT EXISTS link
(
    link varchar(200),
    last_activity_time timestamp without time zone NOT NULL,
    last_check_time timestamp without time zone NOT NULL DEFAULT now(),
    CONSTRAINT link_pkey PRIMARY KEY (link)
)