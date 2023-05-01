package ru.tinkoff.edu.java.scrapper.jooq.service;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.linkParser.Parser;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClientService;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.StackoverflowLinkRecord;
import ru.tinkoff.edu.java.scrapper.dto.response.StackoverflowResponse;
import ru.tinkoff.edu.java.scrapper.inteface.service.StackoverflowLinkService;
import ru.tinkoff.edu.java.scrapper.jooq.repository.JooqStackoverflowLinkRepository;
import ru.tinkoff.edu.java.scrapper.jooq.util.converter.JooqStackoverflowLinkConverter;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class JooqStackoverflowLinkService implements StackoverflowLinkService<StackoverflowLinkRecord> {

    private final JooqStackoverflowLinkRepository jooqStackoverflowLinkRepository;
    private final StackOverflowClientService stackOverflowClientService;
    private final JooqStackoverflowLinkConverter jooqStackoverflowLinkConverter;

    @Override
    public void add(URI url) {
        Map<String, String> parsedLink = Parser.parseLink(url.toString());
        StackoverflowResponse stackoverflowResponse = stackOverflowClientService.getQuestion(
                parsedLink.get(StackoverflowLinkService.QUESTION_ID),
                StackoverflowLinkService.SITE_STACKOVERFLOW
        );
        jooqStackoverflowLinkRepository.add(
                jooqStackoverflowLinkConverter.makeStackoverflowLinkRecord(url.toString(), stackoverflowResponse));
    }

    @Override
    public void remove(URI uri) {
        jooqStackoverflowLinkRepository.remove(uri);
    }

    @Override
    public void update(StackoverflowLinkRecord stackoverflowLinkRecord) {
        jooqStackoverflowLinkRepository.update(stackoverflowLinkRecord);
    }

    @Override
    public Collection<StackoverflowLinkRecord> getLinksByLastCheckTime(LocalDateTime threshold) {
        return jooqStackoverflowLinkRepository.getLinksByLastCheckTime(threshold);
    }
}
