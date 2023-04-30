package ru.tinkoff.edu.java.scrapper.jpa.service;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.linkParser.Parser;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClientService;
import ru.tinkoff.edu.java.scrapper.dto.response.StackoverflowResponse;
import ru.tinkoff.edu.java.scrapper.inteface.service.StackoverflowLinkService;
import ru.tinkoff.edu.java.scrapper.jpa.entity.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.jpa.repository.JpaStackoverflowLinkRepository;
import ru.tinkoff.edu.java.scrapper.jpa.util.JpaTypeConverter;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JpaStackoverflowLinkService implements StackoverflowLinkService<StackoverflowLink> {
    private final JpaStackoverflowLinkRepository jpaStackoverflowLinkRepository;
    private final StackOverflowClientService stackOverflowClientService;
    private final JpaTypeConverter jpaTypeConverter;

    @Override
    public int add(URI url) {
        Map<String, String> parsedLink = Parser.parseLink(url.toString());
        StackoverflowResponse stackoverflowResponse = stackOverflowClientService.getQuestion(parsedLink.get(
                        StackoverflowLinkService.QUESTION_ID),
                StackoverflowLinkService.SITE_STACKOVERFLOW);
        StackoverflowLink stackoverflowLink = jpaTypeConverter.makeStackoverflowLink(url.toString(), stackoverflowResponse);
        return List.of(jpaStackoverflowLinkRepository.save(stackoverflowLink)).size();
    }

    @Override
    public int remove(URI url) {
        jpaStackoverflowLinkRepository.deleteById(url.toString());
        return 1;
    }

    @Override
    public int update(StackoverflowLink stackoverflowLink) {
        return List.of(jpaStackoverflowLinkRepository.save(stackoverflowLink)).size();
    }

    @Override
    public Collection<StackoverflowLink> getLinksByLastCheckTime(LocalDateTime threshold) {
        return jpaStackoverflowLinkRepository.getStackoverflowLinksByLastCheckTimeBefore(threshold);
    }
}
