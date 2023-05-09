package ru.tinkoff.edu.java.scrapper.jpa.service;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.linkParser.Parser;
import ru.tinkoff.edu.java.scrapper.service.StackOverflowClientService;
import ru.tinkoff.edu.java.scrapper.dto.response.StackoverflowResponse;
import ru.tinkoff.edu.java.scrapper.inteface.service.StackoverflowLinkService;
import ru.tinkoff.edu.java.scrapper.jpa.entity.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.jpa.repository.JpaStackoverflowLinkRepository;
import ru.tinkoff.edu.java.scrapper.jpa.util.converter.JpaStackoverflowLinkConverter;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class JpaStackoverflowLinkService implements StackoverflowLinkService<StackoverflowLink> {
    private final JpaStackoverflowLinkRepository jpaStackoverflowLinkRepository;
    private final StackOverflowClientService stackOverflowClientService;
    private final JpaStackoverflowLinkConverter jpaStackoverflowLinkConverter;

    @Override
    public void add(URI url) {
        Map<String, String> parsedLink = Parser.parseLink(url.toString());
        StackoverflowResponse stackoverflowResponse = stackOverflowClientService.getQuestion(parsedLink.get(
                        StackoverflowLinkService.QUESTION_ID),
                StackoverflowLinkService.SITE_STACKOVERFLOW);
        StackoverflowLink stackoverflowLink = jpaStackoverflowLinkConverter.makeStackoverflowLink(url.toString(), stackoverflowResponse);
        jpaStackoverflowLinkRepository.save(stackoverflowLink);
    }

    @Override
    public void remove(URI url) {
        jpaStackoverflowLinkRepository.deleteById(url.toString());
    }

    @Override
    public void update(StackoverflowLink stackoverflowLink) {
        jpaStackoverflowLinkRepository.save(stackoverflowLink);
    }

    @Override
    public Collection<StackoverflowLink> getLinksByLastCheckTime(LocalDateTime threshold) {
        return jpaStackoverflowLinkRepository.getStackoverflowLinksByLastCheckTimeBefore(threshold);
    }
}
