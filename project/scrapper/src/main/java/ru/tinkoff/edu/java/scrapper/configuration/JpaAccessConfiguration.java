package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.tinkoff.edu.java.scrapper.inteface.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.jpa.repository.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.jpa.repository.JpaChatToLinkRepository;
import ru.tinkoff.edu.java.scrapper.jpa.repository.JpaGithubLinkRepository;
import ru.tinkoff.edu.java.scrapper.jpa.repository.JpaStackoverflowLinkRepository;
import ru.tinkoff.edu.java.scrapper.jpa.service.JpaChatService;
import ru.tinkoff.edu.java.scrapper.jpa.service.JpaChatToLinkService;
import ru.tinkoff.edu.java.scrapper.jpa.service.JpaGithubLinkService;
import ru.tinkoff.edu.java.scrapper.jpa.service.JpaLinkUpdater;
import ru.tinkoff.edu.java.scrapper.jpa.service.JpaStackoverflowLinkService;
import ru.tinkoff.edu.java.scrapper.jpa.util.converter.JpaGithubLinkConverter;
import ru.tinkoff.edu.java.scrapper.jpa.util.converter.JpaStackoverflowLinkConverter;
import ru.tinkoff.edu.java.scrapper.service.GitHubClientService;
import ru.tinkoff.edu.java.scrapper.service.StackOverflowClientService;

@Configuration
@EnableJpaRepositories(basePackages = "ru.tinkoff.edu.java.scrapper.jpa.repository")
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {

    @Bean
    public JpaChatService jpaChatService(JpaChatRepository jpaChatRepository) {
        return new JpaChatService(jpaChatRepository);
    }

    @Bean
    public JpaChatToLinkService jpaChatToLinkService(JpaChatToLinkRepository jpaChatToLinkRepository) {
        return new JpaChatToLinkService(jpaChatToLinkRepository);
    }

    @Bean
    public JpaGithubLinkService jpaGithubLinkService(
            JpaGithubLinkRepository jpaGithubLinkRepository,
            GitHubClientService githubLinkService,
            JpaGithubLinkConverter jpaGithubLinkConverter) {
        return new JpaGithubLinkService(jpaGithubLinkRepository, githubLinkService, jpaGithubLinkConverter);
    }

    @Bean
    public JpaStackoverflowLinkService jpaStackoverflowLinkService(
            JpaStackoverflowLinkRepository jpaStackoverflowLinkRepository,
            StackOverflowClientService stackOverflowClientService,
            JpaStackoverflowLinkConverter jpaStackoverflowLinkConverter) {
        return new JpaStackoverflowLinkService(
                jpaStackoverflowLinkRepository,
                stackOverflowClientService,
                jpaStackoverflowLinkConverter);
    }

    @Bean
    public LinkUpdater linkUpdater(
            JpaChatToLinkService jpaChatToLinkService,
            JpaGithubLinkService jpaGithubLinkService,
            JpaStackoverflowLinkService jpaStackoverflowLinkService,
            GitHubClientService gitHubClientService,
            StackOverflowClientService stackOverflowClientService,
            JpaGithubLinkConverter jpaGithubLinkConverter,
            JpaStackoverflowLinkConverter jpaStackoverflowLinkConverter) {
        return new JpaLinkUpdater(
                jpaChatToLinkService,
                jpaGithubLinkService,
                jpaStackoverflowLinkService,
                gitHubClientService,
                stackOverflowClientService,
                jpaGithubLinkConverter,
                jpaStackoverflowLinkConverter);
    }
}
