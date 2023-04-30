package ru.tinkoff.edu.java.scrapper.jdbc.service;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.linkParser.Parser;
import ru.tinkoff.edu.java.scrapper.client.GitHubClientService;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClientService;
import ru.tinkoff.edu.java.scrapper.client.TgBotClientService;
import ru.tinkoff.edu.java.scrapper.dto.LinkChangeLog;
import ru.tinkoff.edu.java.scrapper.dto.entity.ChatToLink;
import ru.tinkoff.edu.java.scrapper.dto.entity.GithubLink;
import ru.tinkoff.edu.java.scrapper.dto.entity.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.StackoverflowResponse;
import ru.tinkoff.edu.java.scrapper.inteface.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.inteface.service.ChatToLinkService;
import ru.tinkoff.edu.java.scrapper.inteface.service.GithubLinkService;
import ru.tinkoff.edu.java.scrapper.inteface.service.StackoverflowLinkService;
import ru.tinkoff.edu.java.scrapper.jdbc.util.JdbcTypeConverter;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
public class JdbcLinkUpdater implements LinkUpdater {

    private final ChatToLinkService<ChatToLink> jdbcChatToLinkService;
    private final GithubLinkService<GithubLink> jdbcGithubLinkService;
    private final StackoverflowLinkService<StackoverflowLink> jdbcStackoverflowLinkService;

    private final GitHubClientService gitHubClientService;
    private final StackOverflowClientService stackOverflowClientService;
    private final TgBotClientService tgBotClientService;

    private final JdbcTypeConverter jdbcTypeConverter;

    @Override
    public int update(LocalDateTime checkTimeThreshold) {
        List<GithubLink> githubLinks = (List<GithubLink>) jdbcGithubLinkService.getLinksByLastCheckTime(checkTimeThreshold);
        List<StackoverflowLink> stackoverflowLinks = (List<StackoverflowLink>) jdbcStackoverflowLinkService.getLinksByLastCheckTime(checkTimeThreshold);

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

            GithubLink updatedGithubLink = jdbcTypeConverter.makeGithubLink(githubLink.getLink(), gitHubResponse);

            if (!Objects.equals(githubLink.getForksCount(), updatedGithubLink.getForksCount()) ||
                    !Objects.equals(githubLink.getOpenIssuesCount(), updatedGithubLink.getOpenIssuesCount())) {
                List<ChatToLink> chatsByLink = (List<ChatToLink>) jdbcChatToLinkService.getChatsByLink(githubLink.getLink());
                for (ChatToLink chatToLink : chatsByLink) {

                    List<GithubLink> outdatedGithubLinksList = outdatedGithubLinks.getOrDefault(chatToLink.getChatId(), new ArrayList<>());
                    outdatedGithubLinksList.add(githubLink);
                    outdatedGithubLinks.put(chatToLink.getChatId(), outdatedGithubLinksList);

                    List<GithubLink> updatedLinksList = updatedGithubLinks.getOrDefault(chatToLink.getChatId(), new ArrayList<>());
                    updatedLinksList.add(updatedGithubLink);
                    updatedGithubLinks.put(chatToLink.getChatId(), updatedLinksList);
                }
            }

            jdbcGithubLinkService.update(updatedGithubLink);
            linksWithUpdateCount++;
        }

        for (StackoverflowLink stackoverflowLink : stackoverflowLinks) {
            Map<String, String> parsedLink = Parser.parseLink(stackoverflowLink.getLink());
            StackoverflowResponse stackoverflowResponse = stackOverflowClientService.getQuestion(
                    parsedLink.get(StackoverflowLinkService.QUESTION_ID),
                    parsedLink.get(StackoverflowLinkService.SITE_STACKOVERFLOW)
            );

            StackoverflowLink updatedStackoverflowLink = jdbcTypeConverter.makeStackoverflowLink(stackoverflowLink.getLink(), stackoverflowResponse);

            if (!Objects.equals(stackoverflowLink.getIsAnswered(), updatedStackoverflowLink.getIsAnswered()) ||
                    !Objects.equals(stackoverflowLink.getAnswerCount(), updatedStackoverflowLink.getAnswerCount())) {
                List<ChatToLink> chatsByLink = (List<ChatToLink>) jdbcChatToLinkService.getChatsByLink(stackoverflowLink.getLink());
                for (ChatToLink chatToLink : chatsByLink) {

                    List<StackoverflowLink> outdatedStackoverflowLinksList = outdatedStackoverflowLinks.getOrDefault(chatToLink.getChatId(), new ArrayList<>());
                    outdatedStackoverflowLinksList.add(stackoverflowLink);
                    outdatedStackoverflowLinks.put(chatToLink.getChatId(), outdatedStackoverflowLinksList);

                    List<StackoverflowLink> updatedStackoverflowLinksList = updatedStackoverflowLinks.getOrDefault(chatToLink.getChatId(), new ArrayList<>());
                    updatedStackoverflowLinksList.add(updatedStackoverflowLink);
                    updatedStackoverflowLinks.put(chatToLink.getChatId(), updatedStackoverflowLinksList);
                }
            }

            jdbcStackoverflowLinkService.update(updatedStackoverflowLink);
            linksWithUpdateCount++;
        }

        Set<Long> tgChatIds = outdatedGithubLinks.keySet();
        List<LinkChangeLog> changeLogs = new ArrayList<>();
        for (Long tgChatId : tgChatIds) {
            changeLogs
                    .add(new LinkChangeLog(
                            tgChatId,
                            outdatedGithubLinks.get(tgChatId),
                            updatedGithubLinks.get(tgChatId),
                            outdatedStackoverflowLinks.get(tgChatId),
                            updatedStackoverflowLinks.get(tgChatId)
                    ));
        }
        tgBotClientService.sendUpdates(changeLogs);
        return linksWithUpdateCount;
    }
}
