package ru.tinkoff.edu.java.scrapper.configuration;

import org.jooq.DSLContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chat;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.ChatToLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.GithubLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.inteface.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.jooq.repository.JooqChatRepository;
import ru.tinkoff.edu.java.scrapper.jooq.repository.JooqChatToLinkRepository;
import ru.tinkoff.edu.java.scrapper.jooq.repository.JooqGithubLinkRepository;
import ru.tinkoff.edu.java.scrapper.jooq.repository.JooqStackoverflowLinkRepository;
import ru.tinkoff.edu.java.scrapper.jooq.service.JooqChatService;
import ru.tinkoff.edu.java.scrapper.jooq.service.JooqChatToLinkService;
import ru.tinkoff.edu.java.scrapper.jooq.service.JooqGitHubLinkService;
import ru.tinkoff.edu.java.scrapper.jooq.service.JooqLinkUpdater;
import ru.tinkoff.edu.java.scrapper.jooq.service.JooqStackoverflowLinkService;
import ru.tinkoff.edu.java.scrapper.jooq.util.converter.JooqGithubLinkConverter;
import ru.tinkoff.edu.java.scrapper.jooq.util.converter.JooqStackoverflowLinkConverter;
import ru.tinkoff.edu.java.scrapper.service.GitHubClientService;
import ru.tinkoff.edu.java.scrapper.service.StackOverflowClientService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfiguration {

    @Bean
    public JooqChatRepository jooqChatRepository(DSLContext dslContext, Chat chatTable) {
        return new JooqChatRepository(dslContext, chatTable);
    }

    @Bean
    public JooqChatToLinkRepository jooqChatToLinkRepository(DSLContext dslContext, ChatToLink chatToLinkTable) {
        return new JooqChatToLinkRepository(dslContext, chatToLinkTable);
    }

    @Bean
    public JooqGithubLinkRepository jooqGithubLinkRepository(DSLContext dslContext, GithubLink githubLinkTable) {
        return new JooqGithubLinkRepository(dslContext, githubLinkTable);
    }

    @Bean
    public JooqStackoverflowLinkRepository jooqStackoverflowLinkRepository(
            DSLContext dslContext,
            StackoverflowLink stackoverflowLinkTable) {
        return new JooqStackoverflowLinkRepository(dslContext, stackoverflowLinkTable);
    }

    @Bean
    public JooqChatService jooqChatService(JooqChatRepository jooqChatRepository) {
        return new JooqChatService(jooqChatRepository);
    }

    @Bean
    public JooqChatToLinkService jooqChatToLinkService(JooqChatToLinkRepository jooqChatToLinkRepository) {
        return new JooqChatToLinkService(jooqChatToLinkRepository);
    }

    @Bean
    public JooqGitHubLinkService jooqGitHubLinkService(
            JooqGithubLinkRepository jooqGithubLinkRepository,
            GitHubClientService githubLinkService,
            JooqGithubLinkConverter jooqGithubLinkConverter) {
        return new JooqGitHubLinkService(jooqGithubLinkRepository, githubLinkService, jooqGithubLinkConverter);
    }

    @Bean
    public JooqStackoverflowLinkService jooqStackoverflowLinkService(
            JooqStackoverflowLinkRepository jooqStackoverflowLinkRepository,
            StackOverflowClientService stackOverflowClientService,
            JooqStackoverflowLinkConverter jooqStackoverflowLinkConverter) {
        return new JooqStackoverflowLinkService(
                jooqStackoverflowLinkRepository,
                stackOverflowClientService,
                jooqStackoverflowLinkConverter);
    }

    @Bean
    public LinkUpdater linkUpdater(
            JooqChatToLinkService jooqChatToLinkService,
            JooqGitHubLinkService jooqGitHubLinkService,
            JooqStackoverflowLinkService jooqStackoverflowLinkService,
            GitHubClientService gitHubClientService,
            StackOverflowClientService stackOverflowClientService,
            JooqGithubLinkConverter jooqGithubLinkConverter,
            JooqStackoverflowLinkConverter jooqStackoverflowLinkConverter) {
        return new JooqLinkUpdater(
                jooqChatToLinkService,
                jooqGitHubLinkService,
                jooqStackoverflowLinkService,
                gitHubClientService,
                stackOverflowClientService,
                jooqGithubLinkConverter,
                jooqStackoverflowLinkConverter);
    }
}
