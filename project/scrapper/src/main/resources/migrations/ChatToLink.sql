CREATE TABLE IF NOT EXISTS chat_to_link
(
    link character varying(200) NOT NULL,
    chat_id character varying(50) NOT NULL,
    CONSTRAINT chat_to_link_pkey PRIMARY KEY (link, chat_id),
    CONSTRAINT chat_to_link_chat_id_fkey FOREIGN KEY (chat_id)
        REFERENCES public.chat (chat_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT chat_to_link_link_fkey FOREIGN KEY (link)
        REFERENCES public.link (link) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)