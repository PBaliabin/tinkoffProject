package ru.tinkoff.edu.java.scrapper.inteface;

import ru.tinkoff.edu.java.scrapper.dto.Link;

import java.net.URI;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

public interface LinkService {
    Link add(long tgChatId, URI url);

    Link remove(long tgChatId, URI url);

    Collection<Link> listAll(long tgChatId);

    int updateLink(Link link);

    List<Link> getLinksBeforeLastCheckTime(Timestamp checkTime);
}
