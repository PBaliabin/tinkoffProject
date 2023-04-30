package ru.tinkoff.edu.java.scrapper.configuration;

import org.jooq.DSLContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.client.GitHubClientService;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClientService;
import ru.tinkoff.edu.java.scrapper.client.TgBotClientService;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chat;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.ChatToLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.GithubLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.inteface.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.jooq.service.*;
import ru.tinkoff.edu.java.scrapper.jooq.util.converter.JooqGithubLinkConverter;
import ru.tinkoff.edu.java.scrapper.jooq.util.converter.JooqStackoverflowLinkConverter;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfiguration {

    @Bean
    public JooqChatService jooqChatService(DSLContext dslContext, Chat chatTable) {
        return new JooqChatService(dslContext, chatTable);
    }

    @Bean
    public JooqChatToLinkService jooqChatToLinkService(DSLContext dslContext, ChatToLink chatToLinkTable) {
        return new JooqChatToLinkService(dslContext, chatToLinkTable);
    }

    @Bean
    public JooqGitHubLinkService jooqGitHubLinkService(DSLContext dslContext,
                                                       GitHubClientService githubLinkService,
                                                       JooqGithubLinkConverter jooqGithubLinkConverter,
                                                       GithubLink githubLinkTable) {
        return new JooqGitHubLinkService(dslContext, githubLinkService, jooqGithubLinkConverter, githubLinkTable);
    }

    @Bean
    public JooqStackoverflowLinkService jooqStackoverflowLinkService(DSLContext dslContext,
                                                                     StackOverflowClientService stackOverflowClientService,
                                                                     JooqStackoverflowLinkConverter jooqStackoverflowLinkConverter,
                                                                     StackoverflowLink stackoverflowLinkTable) {
        return new JooqStackoverflowLinkService(
                dslContext,
                stackOverflowClientService,
                jooqStackoverflowLinkConverter,
                stackoverflowLinkTable);
    }

    @Bean
    public LinkUpdater linkUpdater(JooqChatToLinkService jooqChatToLinkService,
                                   JooqGitHubLinkService jooqGitHubLinkService,
                                   JooqStackoverflowLinkService jooqStackoverflowLinkService,
                                   GitHubClientService gitHubClientService,
                                   StackOverflowClientService stackOverflowClientService,
                                   TgBotClientService tgBotClientService,
                                   JooqGithubLinkConverter jooqGithubLinkConverter,
                                   JooqStackoverflowLinkConverter jooqStackoverflowLinkConverter) {
        return new JooqLinkUpdater(
                jooqChatToLinkService,
                jooqGitHubLinkService,
                jooqStackoverflowLinkService,
                gitHubClientService,
                stackOverflowClientService,
                tgBotClientService,
                jooqGithubLinkConverter,
                jooqStackoverflowLinkConverter);
    }
}