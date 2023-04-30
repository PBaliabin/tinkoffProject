package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.tinkoff.edu.java.scrapper.client.GitHubClientService;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClientService;
import ru.tinkoff.edu.java.scrapper.client.TgBotClientService;
import ru.tinkoff.edu.java.scrapper.inteface.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.jpa.repository.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.jpa.repository.JpaChatToLinkRepository;
import ru.tinkoff.edu.java.scrapper.jpa.repository.JpaGithubLinkRepository;
import ru.tinkoff.edu.java.scrapper.jpa.repository.JpaStackoverflowLinkRepository;
import ru.tinkoff.edu.java.scrapper.jpa.service.*;
import ru.tinkoff.edu.java.scrapper.jpa.util.JpaTypeConverter;

@Configuration
@EnableJpaRepositories
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {

    @Bean
    public JpaTypeConverter jpaTypeConverter() {
        return new JpaTypeConverter();
    }

    @Bean
    public JpaChatService jpaChatService(JpaChatRepository jpaChatRepository) {
        return new JpaChatService(jpaChatRepository);
    }

    @Bean
    public JpaChatToLinkService jpaChatToLinkService(JpaChatToLinkRepository jpaChatToLinkRepository) {
        return new JpaChatToLinkService(jpaChatToLinkRepository);
    }

    @Bean
    public JpaGithubLinkService jpaGithubLinkService(JpaGithubLinkRepository jpaGithubLinkRepository,
                                                     GitHubClientService githubLinkService,
                                                     JpaTypeConverter jpaTypeConverter) {
        return new JpaGithubLinkService(jpaGithubLinkRepository, githubLinkService, jpaTypeConverter);
    }

    @Bean
    public JpaStackoverflowLinkService jpaStackoverflowLinkService(JpaStackoverflowLinkRepository jpaStackoverflowLinkRepository,
                                                                   StackOverflowClientService stackOverflowClientService,
                                                                   JpaTypeConverter jpaTypeConverter) {
        return new JpaStackoverflowLinkService(jpaStackoverflowLinkRepository, stackOverflowClientService, jpaTypeConverter);
    }

    @Bean
    public LinkUpdater linkUpdater(JpaChatToLinkService jpaChatToLinkService,
                                   JpaGithubLinkService jpaGithubLinkService,
                                   JpaStackoverflowLinkService jpaStackoverflowLinkService,
                                   GitHubClientService gitHubClientService,
                                   StackOverflowClientService stackOverflowClientService,
                                   TgBotClientService tgBotClientService,
                                   JpaTypeConverter jpaTypeConverter) {
        return new JpaLinkUpdater(
                jpaChatToLinkService,
                jpaGithubLinkService,
                jpaStackoverflowLinkService,
                gitHubClientService,
                stackOverflowClientService,
                tgBotClientService,
                jpaTypeConverter);
    }
}
