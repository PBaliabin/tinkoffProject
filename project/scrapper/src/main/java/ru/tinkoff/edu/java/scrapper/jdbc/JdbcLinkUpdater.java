package ru.tinkoff.edu.java.scrapper.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.linkParser.Parser;
import ru.tinkoff.edu.java.scrapper.client.GitHubClientService;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClientService;
import ru.tinkoff.edu.java.scrapper.client.TgBotClientService;
import ru.tinkoff.edu.java.scrapper.dto.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.dto.Link;
import ru.tinkoff.edu.java.scrapper.dto.LinkChangeLog;
import ru.tinkoff.edu.java.scrapper.dto.StackoverflowResponse;
import ru.tinkoff.edu.java.scrapper.inteface.DateUtil;
import ru.tinkoff.edu.java.scrapper.inteface.LinkService;
import ru.tinkoff.edu.java.scrapper.inteface.LinkUpdater;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JdbcLinkUpdater implements LinkUpdater {

    private final LinkService linkService;
    private final GitHubClientService gitHubClientService;
    private final TgBotClientService tgBotClientService;
    private final StackOverflowClientService stackOverflowClientService;

    @Override
    public int update(long checkTimeThreshold) {
        List<Link> links = linkService.getLinksBeforeLastCheckTime(new Timestamp(checkTimeThreshold));
        List<LinkChangeLog> changeLogs = new ArrayList<>();
        int linksWithUpdateCount = 0;

        for (Link link : links) {

            Map<String, String> parsedLink = Parser.parseLink(link.getLink());
            Link newLink = new Link();

            long newUpdateTime = 0L;

            switch (parsedLink.get("domain")) {
                case "github.com" -> {
                    GitHubResponse gitHubResponse = gitHubClientService.getRepo(parsedLink.get("owner"), parsedLink.get("repository"));
                    try {
                        newUpdateTime = DateUtil.provideDateFormat().parse(gitHubResponse.getUpdatedAt()).getTime();
                    } catch (Exception e) {
                        System.out.println("Error while parsing GitHub Timestamp");
                    }
                }
                case "stackoverflow.com" -> {
                    StackoverflowResponse stackoverflowResponse = stackOverflowClientService.getQuestion(parsedLink.get("questionId"), parsedLink.get("repository"));
                    newUpdateTime = stackoverflowResponse.getLastActivityDate();
                }
            }

            newLink.setLink(link.getLink());
            newLink.setLastActivityTime(new Timestamp(newUpdateTime));
            newLink.setLastCheckTime(new Timestamp(System.currentTimeMillis()));

            linkService.updateLink(newLink);
            if (link.getLastActivityTime().getTime() != newUpdateTime) {
                changeLogs.add(new LinkChangeLog(link, newLink));
            }
            linksWithUpdateCount++;
        }
        tgBotClientService.sendUpdates(changeLogs);
        return linksWithUpdateCount;
    }
}
