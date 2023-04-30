package ru.tinkoff.edu.java.scrapper.jdbc.service;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.linkParser.Parser;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClientService;
import ru.tinkoff.edu.java.scrapper.dto.entity.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.dto.response.StackoverflowResponse;
import ru.tinkoff.edu.java.scrapper.inteface.service.StackoverflowLinkService;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcStackoverflowLinkRepository;
import ru.tinkoff.edu.java.scrapper.jdbc.util.JdbcTypeConverter;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class JdbcStackoverflowLinkService implements StackoverflowLinkService<StackoverflowLink> {

    private final JdbcStackoverflowLinkRepository jdbcStackoverflowLinkRepository;
    private final StackOverflowClientService stackOverflowClientService;
    private final JdbcTypeConverter jdbcTypeConverter;

    @Override
    public int add(URI url) {
        Map<String, String> parsedLink = Parser.parseLink(url.toString());
        StackoverflowResponse stackoverflowResponse = stackOverflowClientService.getQuestion(parsedLink.get(
                        StackoverflowLinkService.QUESTION_ID),
                StackoverflowLinkService.SITE_STACKOVERFLOW);
        StackoverflowLink stackoverflowLink = jdbcTypeConverter.makeStackoverflowLink(url.toString(), stackoverflowResponse);
        return jdbcStackoverflowLinkRepository.add(stackoverflowLink);
    }

    @Override
    public int remove(URI url) {
        return jdbcStackoverflowLinkRepository.remove(url);
    }

    @Override
    public int update(StackoverflowLink stackoverflowLink) {
        return jdbcStackoverflowLinkRepository.update(stackoverflowLink);
    }

    @Override
    public Collection<StackoverflowLink> getLinksByLastCheckTime(LocalDateTime threshold) {
        return jdbcStackoverflowLinkRepository.getLinksBeforeLastCheckTime(threshold);
    }
}
