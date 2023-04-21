package ru.tinkoff.edu.java.scrapper.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.Chat;
import ru.tinkoff.edu.java.scrapper.dto.Link;
import ru.tinkoff.edu.java.scrapper.inteface.LinkService;
import ru.tinkoff.edu.java.scrapper.repository.JdbcChatToLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.JdbcLinkRepository;

import java.net.URI;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final JdbcLinkRepository jdbcLinkRepository;
    private final JdbcChatToLinkRepository jdbcChatToLinkRepository;

    @Override
    public Link add(long tgChatId, URI url) {
        Link linkToAdd = new Link();
        linkToAdd.setLink(url.toString());
        jdbcLinkRepository.addLink(linkToAdd);
        jdbcChatToLinkRepository.addRow(
                new Chat(String.valueOf(tgChatId)),
                linkToAdd);
        return linkToAdd;
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        Link linkToRemove = new Link();
        linkToRemove.setLink(url.toString());
        jdbcChatToLinkRepository.deleteRow(
                new Chat(String.valueOf(tgChatId)),
                linkToRemove);
        return linkToRemove;
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        return jdbcLinkRepository.getAllLinks();
    }

    @Override
    public int updateLink(Link link) {
        return jdbcLinkRepository.updateLink(link);
    }

    @Override
    public List<Link> getLinksBeforeLastCheckTime(Timestamp checkTime) {
        return jdbcLinkRepository.getLinksBeforeLastCheckTime(checkTime);
    }
}
