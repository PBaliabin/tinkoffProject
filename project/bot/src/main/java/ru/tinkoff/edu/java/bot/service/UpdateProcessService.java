package ru.tinkoff.edu.java.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.model.dto.LinkUpdate;
import ru.tinkoff.edu.java.bot.wrapper.GithubLinkWrapper;
import ru.tinkoff.edu.java.bot.wrapper.StackoverflowLinkWrapper;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UpdateProcessService {

    public static final String TG_CHAT_ID = "tgChatId";
    public static final String MESSAGE = "message";

    private final GithubLinkWrapper githubLinkWrapper;
    private final StackoverflowLinkWrapper stackoverflowLinkWrapper;

    public Map<String, String> processUpdate(LinkUpdate linkUpdate) {
        String githubLinksChanges = githubLinkWrapper.makeMessage(
                linkUpdate.getOutdatedGithubLinks(),
                linkUpdate.getUpdatedGithubLinks());
        String stackoverflowLinksChanges = stackoverflowLinkWrapper.makeMessage(
                linkUpdate.getOutdatedStackoverflowLinks(),
                linkUpdate.getUpdatedStackoverflowLinks());

        return Map.of(
                "tgChatId",
                linkUpdate.getTgChatId().toString(),
                "message",
                githubLinksChanges
                        + stackoverflowLinksChanges);
    }
}
