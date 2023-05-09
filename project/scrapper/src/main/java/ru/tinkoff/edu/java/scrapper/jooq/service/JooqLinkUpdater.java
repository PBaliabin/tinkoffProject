package ru.tinkoff.edu.java.scrapper.jooq.service;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.linkParser.Parser;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.GithubLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatToLinkRecord;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.GithubLinkRecord;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.StackoverflowLinkRecord;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.StackoverflowResponse;
import ru.tinkoff.edu.java.scrapper.inteface.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.jooq.util.converter.JooqGithubLinkConverter;
import ru.tinkoff.edu.java.scrapper.jooq.util.converter.JooqStackoverflowLinkConverter;
import ru.tinkoff.edu.java.scrapper.service.GitHubClientService;
import ru.tinkoff.edu.java.scrapper.service.StackOverflowClientService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
public class JooqLinkUpdater implements LinkUpdater {

    private final JooqChatToLinkService jooqChatToLinkService;
    private final JooqGitHubLinkService jooqGitHubLinkService;
    private final JooqStackoverflowLinkService jooqStackoverflowLinkService;

    private final GitHubClientService gitHubClientService;
    private final StackOverflowClientService stackOverflowClientService;

    private final JooqGithubLinkConverter jooqGithubLinkConverter;
    private final JooqStackoverflowLinkConverter jooqStackoverflowLinkConverter;

    @Override
    public Collection<LinkUpdate> update(LocalDateTime checkTimeThreshold) {
        List<GithubLinkRecord> githubLinks = (List<GithubLinkRecord>) jooqGitHubLinkService.getLinksByLastCheckTime(
                checkTimeThreshold);
        List<StackoverflowLinkRecord> stackoverflowLinks =
                (List<StackoverflowLinkRecord>) jooqStackoverflowLinkService.getLinksByLastCheckTime(
                        checkTimeThreshold);

        Map<Long, List<GithubLink>> outdatedGithubLinks = new HashMap<>();
        Map<Long, List<GithubLink>> updatedGithubLinks = new HashMap<>();
        Map<Long, List<StackoverflowLink>> updatedStackoverflowLinks = new HashMap<>();
        Map<Long, List<StackoverflowLink>> outdatedStackoverflowLinks = new HashMap<>();

        for (GithubLinkRecord githubLink : githubLinks) {
            Map<String, String> parsedLink = Parser.parseLink(githubLink.getLink());
            GitHubResponse gitHubResponse = gitHubClientService.getRepo(
                    parsedLink.get("owner"),
                    parsedLink.get("repository"));

            GithubLinkRecord updatedGithubLink = jooqGithubLinkConverter.makeGithubLinkRecord(
                    githubLink.getLink(),
                    gitHubResponse);

            if (!Objects.equals(githubLink.getForksCount(), updatedGithubLink.getForksCount())
                    ||
                    !Objects.equals(githubLink.getOpenIssuesCount(), updatedGithubLink.getOpenIssuesCount())) {
                List<ChatToLinkRecord> chatsByLink = (List<ChatToLinkRecord>) jooqChatToLinkService.getChatsByLink(
                        githubLink.getLink());
                for (ChatToLinkRecord chatToLink : chatsByLink) {

                    List<GithubLink> outdatedGithubLinksList = outdatedGithubLinks.getOrDefault(
                            chatToLink.getChatId(),
                            new ArrayList<>());
                    outdatedGithubLinksList.add(jooqGithubLinkConverter.makeGithubLink(githubLink));
                    outdatedGithubLinks.put(chatToLink.getChatId(), outdatedGithubLinksList);

                    List<GithubLink> updatedLinksList = updatedGithubLinks.getOrDefault(
                            chatToLink.getChatId(),
                            new ArrayList<>());
                    updatedLinksList.add(jooqGithubLinkConverter.makeGithubLink(updatedGithubLink));
                    updatedGithubLinks.put(chatToLink.getChatId(), updatedLinksList);
                }
            }

            jooqGitHubLinkService.update(updatedGithubLink);
        }

        for (StackoverflowLinkRecord stackoverflowLink : stackoverflowLinks) {
            Map<String, String> parsedLink = Parser.parseLink(stackoverflowLink.getLink());
            StackoverflowResponse stackoverflowResponse = stackOverflowClientService.getQuestion(parsedLink.get(
                    "questionId"), "stackoverflow");

            StackoverflowLinkRecord updatedStackoverflowLink =
                    jooqStackoverflowLinkConverter.makeStackoverflowLinkRecord(
                            stackoverflowLink.getLink(),
                            stackoverflowResponse);

            if (!Objects.equals(stackoverflowLink.getIsAnswered(), updatedStackoverflowLink.getIsAnswered())
                    ||
                    !Objects.equals(stackoverflowLink.getAnswerCount(), updatedStackoverflowLink.getAnswerCount())) {
                List<ChatToLinkRecord> chatsByLink = (List<ChatToLinkRecord>) jooqChatToLinkService.getChatsByLink(
                        stackoverflowLink.getLink());
                for (ChatToLinkRecord chatToLink : chatsByLink) {
                    List<StackoverflowLink> outdatedStackoverflowLinksList = outdatedStackoverflowLinks.getOrDefault(
                            chatToLink.getChatId(),
                            new ArrayList<>());
                    outdatedStackoverflowLinksList.add(jooqStackoverflowLinkConverter.makeStackoverflowLink(
                            stackoverflowLink));
                    outdatedStackoverflowLinks.put(chatToLink.getChatId(), outdatedStackoverflowLinksList);

                    List<StackoverflowLink> updatedStackoverflowLinksList = updatedStackoverflowLinks.getOrDefault(
                            chatToLink.getChatId(),
                            new ArrayList<>());
                    updatedStackoverflowLinksList.add(jooqStackoverflowLinkConverter.makeStackoverflowLink(
                            updatedStackoverflowLink));
                    updatedStackoverflowLinks.put(chatToLink.getChatId(), updatedStackoverflowLinksList);
                }

                jooqStackoverflowLinkService.update(updatedStackoverflowLink);
            }
        }

        Set<Long> tgChatIds = outdatedGithubLinks.keySet();
        List<LinkUpdate> linkUpdates = new ArrayList<>();
        for (Long tgChatId : tgChatIds) {
            linkUpdates.add(new LinkUpdate(
                    tgChatId,
                    outdatedGithubLinks
                            .get(tgChatId)
                            .stream()
                            .map(jooqGithubLinkConverter::convertJooqGithubLinkToCustom)
                            .toList(),
                    updatedGithubLinks
                            .get(tgChatId)
                            .stream()
                            .map(jooqGithubLinkConverter::convertJooqGithubLinkToCustom)
                            .toList(),
                    outdatedStackoverflowLinks
                            .get(tgChatId)
                            .stream()
                            .map(jooqStackoverflowLinkConverter::convertJooqStackoverflowLinkToCustom)
                            .toList(),
                    updatedStackoverflowLinks
                            .get(tgChatId)
                            .stream()
                            .map(jooqStackoverflowLinkConverter::convertJooqStackoverflowLinkToCustom)
                            .toList()
            ));
        }
        return linkUpdates;
    }
}
