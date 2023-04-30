package ru.tinkoff.edu.java.scrapper.jdbc.service;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.linkParser.Parser;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClientService;
import ru.tinkoff.edu.java.scrapper.dto.entity.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.dto.response.StackoverflowResponse;
import ru.tinkoff.edu.java.scrapper.inteface.service.StackoverflowLinkService;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcStackoverflowLinkRepository;
import ru.tinkoff.edu.java.scrapper.jdbc.util.converter.JdbcStackoverflowLinkConverter;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class JdbcStackoverflowLinkService implements StackoverflowLinkService<StackoverflowLink> {

    private final JdbcStackoverflowLinkRepository jdbcStackoverflowLinkRepository;
    private final StackOverflowClientService stackOverflowClientService;
    private final JdbcStackoverflowLinkConverter jdbcStackoverflowLinkConverter;

    @Override
    public void add(URI url) {
        Map<String, String> parsedLink = Parser.parseLink(url.toString());
        StackoverflowResponse stackoverflowResponse = stackOverflowClientService.getQuestion(parsedLink.get(
                        StackoverflowLinkService.QUESTION_ID),
                StackoverflowLinkService.SITE_STACKOVERFLOW);
        StackoverflowLink stackoverflowLink = jdbcStackoverflowLinkConverter.makeStackoverflowLink(url.toString(), stackoverflowResponse);
        jdbcStackoverflowLinkRepository.add(stackoverflowLink);
    }

    @Override
    public void remove(URI url) {
        jdbcStackoverflowLinkRepository.remove(url);
    }

    @Override
    public void update(StackoverflowLink stackoverflowLink) {
        jdbcStackoverflowLinkRepository.update(stackoverflowLink);
    }

    @Override
    public Collection<StackoverflowLink> getLinksByLastCheckTime(LocalDateTime threshold) {
        return jdbcStackoverflowLinkRepository.getLinksBeforeLastCheckTime(threshold);
    }
}
