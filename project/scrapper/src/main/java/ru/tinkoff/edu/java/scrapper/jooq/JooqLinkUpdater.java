package ru.tinkoff.edu.java.scrapper.jooq;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.linkParser.Parser;
import ru.tinkoff.edu.java.scrapper.client.GitHubClientService;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClientService;
import ru.tinkoff.edu.java.scrapper.client.TgBotClientService;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.GithubLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatToLinkRecord;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.GithubLinkRecord;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.StackoverflowLinkRecord;
import ru.tinkoff.edu.java.scrapper.dto.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.dto.LinkChangeLog;
import ru.tinkoff.edu.java.scrapper.dto.StackoverflowResponse;
import ru.tinkoff.edu.java.scrapper.inteface.*;
import ru.tinkoff.edu.java.scrapper.service.TypeConverter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JooqLinkUpdater implements LinkUpdater {

    private final ChatToLinkService chatToLinkService;
    private final GithubLinkService githubLinkService;
    private final StackoverflowLinkService stackoverflowLinkService;
    private final GitHubClientService gitHubClientService;
    private final TgBotClientService tgBotClientService;
    private final StackOverflowClientService stackOverflowClientService;
    private final TypeConverter typeConverter;

    @Override
    public int update(LocalDateTime checkTimeThreshold) {
        List<GithubLinkRecord> githubLinks = (List<GithubLinkRecord>) githubLinkService.getLinksByLastCheckTime(checkTimeThreshold);
        List<StackoverflowLinkRecord> stackoverflowLinks = (List<StackoverflowLinkRecord>) stackoverflowLinkService.getLinksByLastCheckTime(checkTimeThreshold);

        int linksWithUpdateCount = 0;
        Map<String, List<GithubLink>> outdatedGithubLinks = new HashMap<>();
        Map<String, List<GithubLink>> updatedGithubLinks = new HashMap<>();
        Map<String, List<StackoverflowLink>> updatedStackoverflowLinks = new HashMap<>();
        Map<String, List<StackoverflowLink>> outdatedStackoverflowLinks = new HashMap<>();

        for (GithubLinkRecord githubLink : githubLinks) {
            Map<String, String> parsedLink = Parser.parseLink(githubLink.getLink());
            GitHubResponse gitHubResponse = gitHubClientService.getRepo(parsedLink.get("owner"), parsedLink.get("repository"));

            GithubLinkRecord updatedGithubLink = typeConverter.makeGithubLinkRecord(githubLink.getLink(), gitHubResponse);

            if (!Objects.equals(githubLink.getForksCount(), updatedGithubLink.getForksCount()) ||
                    !Objects.equals(githubLink.getOpenIssuesCount(), updatedGithubLink.getOpenIssuesCount())) {
                List<ChatToLinkRecord> chatsByLink = (List<ChatToLinkRecord>) chatToLinkService.getChatsByLink(githubLink.getLink());
                for (ChatToLinkRecord chatToLink : chatsByLink) {

                    List<GithubLink> outdatedGithubLinksList = outdatedGithubLinks.getOrDefault(chatToLink.getChatId(), new ArrayList<>());
                    outdatedGithubLinksList.add(typeConverter.makeGithubLink(githubLink));
                    outdatedGithubLinks.put(chatToLink.getChatId(), outdatedGithubLinksList);

                    List<GithubLink> updatedLinksList = updatedGithubLinks.getOrDefault(chatToLink.getChatId(), new ArrayList<>());
                    updatedLinksList.add(typeConverter.makeGithubLink(updatedGithubLink));
                    updatedGithubLinks.put(chatToLink.getChatId(), updatedLinksList);
                }
            }

            githubLinkService.update(typeConverter.makeGithubLink(updatedGithubLink));
            linksWithUpdateCount++;
        }

        for (StackoverflowLinkRecord stackoverflowLink : stackoverflowLinks) {
            Map<String, String> parsedLink = Parser.parseLink(stackoverflowLink.getLink());
            StackoverflowResponse stackoverflowResponse = stackOverflowClientService.getQuestion(parsedLink.get("questionId"), "stackoverflow");

            StackoverflowLinkRecord updatedStackoverflowLink = typeConverter.makeStackoverflowLinkRecord(stackoverflowLink.getLink(), stackoverflowResponse);

            if (!Objects.equals(stackoverflowLink.getIsAnswered(), updatedStackoverflowLink.getIsAnswered()) ||
                    !Objects.equals(stackoverflowLink.getAnswerCount(), updatedStackoverflowLink.getAnswerCount())) {
                List<ChatToLinkRecord> chatsByLink = (List<ChatToLinkRecord>) chatToLinkService.getChatsByLink(stackoverflowLink.getLink());
                for (ChatToLinkRecord chatToLink : chatsByLink) {
                    List<StackoverflowLink> outdatedStackoverflowLinksList = outdatedStackoverflowLinks.getOrDefault(chatToLink.getChatId(), new ArrayList<>());
                    outdatedStackoverflowLinksList.add(typeConverter.makeStackoverflowLink(stackoverflowLink));
                    outdatedStackoverflowLinks.put(chatToLink.getChatId(), outdatedStackoverflowLinksList);

                    List<StackoverflowLink> updatedStackoverflowLinksList = updatedStackoverflowLinks.getOrDefault(chatToLink.getChatId(), new ArrayList<>());
                    updatedStackoverflowLinksList.add(typeConverter.makeStackoverflowLink(updatedStackoverflowLink));
                    updatedStackoverflowLinks.put(chatToLink.getChatId(), updatedStackoverflowLinksList);
                }

                stackoverflowLinkService.update(typeConverter.makeStackoverflowLink(updatedStackoverflowLink));
                linksWithUpdateCount++;
            }
        }

        Set<String> tgChatIds = outdatedGithubLinks.keySet();
        List<LinkChangeLog> changeLogs = new ArrayList<>();
        for (String tgChatId : tgChatIds) {
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
