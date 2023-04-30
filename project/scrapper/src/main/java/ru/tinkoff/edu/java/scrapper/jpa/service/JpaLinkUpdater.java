package ru.tinkoff.edu.java.scrapper.jpa.service;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.linkParser.Parser;
import ru.tinkoff.edu.java.scrapper.client.GitHubClientService;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClientService;
import ru.tinkoff.edu.java.scrapper.client.TgBotClientService;
import ru.tinkoff.edu.java.scrapper.dto.LinkChangeLog;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.StackoverflowResponse;
import ru.tinkoff.edu.java.scrapper.inteface.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.inteface.service.ChatToLinkService;
import ru.tinkoff.edu.java.scrapper.inteface.service.GithubLinkService;
import ru.tinkoff.edu.java.scrapper.inteface.service.StackoverflowLinkService;
import ru.tinkoff.edu.java.scrapper.jpa.entity.ChatToLink;
import ru.tinkoff.edu.java.scrapper.jpa.entity.GithubLink;
import ru.tinkoff.edu.java.scrapper.jpa.entity.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.jpa.util.JpaTypeConverter;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
public class JpaLinkUpdater implements LinkUpdater {

    private final ChatToLinkService<ChatToLink> jpaChatToLinkService;
    private final GithubLinkService<GithubLink> jpaGithubLinkService;
    private final StackoverflowLinkService<StackoverflowLink> jpaStackoverflowLinkService;

    private final GitHubClientService gitHubClientService;
    private final StackOverflowClientService stackOverflowClientService;
    private final TgBotClientService tgBotClientService;

    private final JpaTypeConverter jpaTypeConverter;

    @Override
    public int update(LocalDateTime checkTimeThreshold) {
        List<GithubLink> githubLinks = (List<GithubLink>) jpaGithubLinkService.getLinksByLastCheckTime(checkTimeThreshold);
        List<StackoverflowLink> stackoverflowLinks = (List<StackoverflowLink>) jpaStackoverflowLinkService.getLinksByLastCheckTime(checkTimeThreshold);

        int linksWithUpdateCount = 0;
        Map<Long, List<GithubLink>> outdatedGithubLinks = new HashMap<>();
        Map<Long, List<GithubLink>> updatedGithubLinks = new HashMap<>();
        Map<Long, List<StackoverflowLink>> updatedStackoverflowLinks = new HashMap<>();
        Map<Long, List<StackoverflowLink>> outdatedStackoverflowLinks = new HashMap<>();

        for (GithubLink githubLink : githubLinks) {
            Map<String, String> parsedLink = Parser.parseLink(githubLink.getLink());
            GitHubResponse gitHubResponse = gitHubClientService.getRepo(
                    parsedLink.get(GithubLinkService.OWNER),
                    parsedLink.get(GithubLinkService.REPOSITORY));

            GithubLink updatedGithubLink = jpaTypeConverter.makeGithubLink(githubLink.getLink(), gitHubResponse);

            if (!Objects.equals(githubLink.getForksCount(), updatedGithubLink.getForksCount()) ||
                    !Objects.equals(githubLink.getOpenIssuesCount(), updatedGithubLink.getOpenIssuesCount())) {
                List<ChatToLink> chatsByLink = (List<ChatToLink>) jpaChatToLinkService.getChatsByLink(githubLink.getLink());
                for (ChatToLink chatToLink : chatsByLink) {

                    List<GithubLink> outdatedGithubLinksList = outdatedGithubLinks.getOrDefault(chatToLink.getChatId(), new ArrayList<>());
                    outdatedGithubLinksList.add(githubLink);
                    outdatedGithubLinks.put(chatToLink.getChatId(), outdatedGithubLinksList);

                    List<GithubLink> updatedLinksList = updatedGithubLinks.getOrDefault(chatToLink.getChatId(), new ArrayList<>());
                    updatedLinksList.add(updatedGithubLink);
                    updatedGithubLinks.put(chatToLink.getChatId(), updatedLinksList);
                }
            }

            jpaGithubLinkService.update(updatedGithubLink);
            linksWithUpdateCount++;
        }

        for (StackoverflowLink stackoverflowLink : stackoverflowLinks) {
            Map<String, String> parsedLink = Parser.parseLink(stackoverflowLink.getLink());
            StackoverflowResponse stackoverflowResponse = stackOverflowClientService.getQuestion(
                    parsedLink.get(StackoverflowLinkService.QUESTION_ID),
                    parsedLink.get(StackoverflowLinkService.SITE_STACKOVERFLOW)
            );

            StackoverflowLink updatedStackoverflowLink = jpaTypeConverter.makeStackoverflowLink(stackoverflowLink.getLink(), stackoverflowResponse);

            if (!Objects.equals(stackoverflowLink.getIsAnswered(), updatedStackoverflowLink.getIsAnswered()) ||
                    !Objects.equals(stackoverflowLink.getAnswerCount(), updatedStackoverflowLink.getAnswerCount())) {
                List<ChatToLink> chatsByLink = (List<ChatToLink>) jpaChatToLinkService.getChatsByLink(stackoverflowLink.getLink());
                for (ChatToLink chatToLink : chatsByLink) {

                    List<StackoverflowLink> outdatedStackoverflowLinksList = outdatedStackoverflowLinks.getOrDefault(chatToLink.getChatId(), new ArrayList<>());
                    outdatedStackoverflowLinksList.add(stackoverflowLink);
                    outdatedStackoverflowLinks.put(chatToLink.getChatId(), outdatedStackoverflowLinksList);

                    List<StackoverflowLink> updatedStackoverflowLinksList = updatedStackoverflowLinks.getOrDefault(chatToLink.getChatId(), new ArrayList<>());
                    updatedStackoverflowLinksList.add(updatedStackoverflowLink);
                    updatedStackoverflowLinks.put(chatToLink.getChatId(), updatedStackoverflowLinksList);
                }
            }

            jpaStackoverflowLinkService.update(updatedStackoverflowLink);
            linksWithUpdateCount++;
        }

        Set<Long> tgChatIds = outdatedGithubLinks.keySet();
        List<LinkChangeLog> changeLogs = new ArrayList<>();
        for (Long tgChatId : tgChatIds) {
            changeLogs
                    .add(new LinkChangeLog(
                            tgChatId,
                            outdatedGithubLinks.get(tgChatId).stream().map(jpaTypeConverter::convertJpaGithubLinkToCustom).toList(),
                            updatedGithubLinks.get(tgChatId).stream().map(jpaTypeConverter::convertJpaGithubLinkToCustom).toList(),
                            outdatedStackoverflowLinks.get(tgChatId).stream().map(jpaTypeConverter::convertJpaStackoverflowLinkToCustom).toList(),
                            updatedStackoverflowLinks.get(tgChatId).stream().map(jpaTypeConverter::convertJpaStackoverflowLinkToCustom).toList()
                    ));
        }
        tgBotClientService.sendUpdates(changeLogs);
        return linksWithUpdateCount;
    }
}
