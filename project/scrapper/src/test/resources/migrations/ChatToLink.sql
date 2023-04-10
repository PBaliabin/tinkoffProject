CREATE TABLE IF NOT EXISTS chat_to_link
(
    id bigint NOT NULL,
    link varchar(200) NOT NULL,
    chat_id varchar(50) NOT NULL,
    CONSTRAINT chat_to_link_pkey PRIMARY KEY (id),
    CONSTRAINT chat_id_fk FOREIGN KEY (chat_id)
        REFERENCES chat (chat_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT link_fk FOREIGN KEY (link)
        REFERENCES link (link) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)