package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.tinkoff.edu.java.scrapper.dto.entity.ChatToLink;
import ru.tinkoff.edu.java.scrapper.dto.entity.GithubLink;
import ru.tinkoff.edu.java.scrapper.dto.entity.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.inteface.service.ChatToLinkService;
import ru.tinkoff.edu.java.scrapper.inteface.service.GithubLinkService;
import ru.tinkoff.edu.java.scrapper.inteface.service.StackoverflowLinkService;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcChatRepository;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcChatToLinkRepository;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcGithubLinkRepository;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcStackoverflowLinkRepository;
import ru.tinkoff.edu.java.scrapper.jdbc.service.JdbcChatService;
import ru.tinkoff.edu.java.scrapper.jdbc.service.JdbcChatToLinkService;
import ru.tinkoff.edu.java.scrapper.jdbc.service.JdbcGithubLinkService;
import ru.tinkoff.edu.java.scrapper.jdbc.service.JdbcLinkUpdater;
import ru.tinkoff.edu.java.scrapper.jdbc.service.JdbcStackoverflowLinkService;
import ru.tinkoff.edu.java.scrapper.jdbc.util.converter.JdbcGithubLinkConverter;
import ru.tinkoff.edu.java.scrapper.jdbc.util.converter.JdbcStackoverflowLinkConverter;
import ru.tinkoff.edu.java.scrapper.service.GitHubClientService;
import ru.tinkoff.edu.java.scrapper.service.StackOverflowClientService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {

    @Bean
    public JdbcChatRepository jdbcChatRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new JdbcChatRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public JdbcChatToLinkRepository jdbcChatToLinkRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new JdbcChatToLinkRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public JdbcGithubLinkRepository jdbcGithubLinkRepository(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            JdbcGithubLinkConverter jdbcGithubLinkConverter) {
        return new JdbcGithubLinkRepository(namedParameterJdbcTemplate, jdbcGithubLinkConverter);
    }

    @Bean
    public JdbcStackoverflowLinkRepository jdbcStackoverflowLinkRepository(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            JdbcStackoverflowLinkConverter jdbcStackoverflowLinkConverter) {
        return new JdbcStackoverflowLinkRepository(namedParameterJdbcTemplate, jdbcStackoverflowLinkConverter);
    }

    @Bean
    public JdbcChatService chatService(JdbcChatRepository jdbcChatRepository) {
        return new JdbcChatService(jdbcChatRepository);
    }

    @Bean
    public JdbcChatToLinkService chatToLinkService(JdbcChatToLinkRepository jdbcChatToLinkRepository) {
        return new JdbcChatToLinkService(jdbcChatToLinkRepository);
    }

    @Bean
    public JdbcGithubLinkService githubLinkService(
            JdbcGithubLinkRepository jdbcGithubLinkRepository,
            GitHubClientService gitHubClientService,
            JdbcGithubLinkConverter jdbcGithubLinkConverter) {
        return new JdbcGithubLinkService(jdbcGithubLinkRepository, gitHubClientService, jdbcGithubLinkConverter);
    }

    @Bean
    public JdbcStackoverflowLinkService stackoverflowLinkService(
            JdbcStackoverflowLinkRepository jdbcStackoverflowLinkRepository,
            StackOverflowClientService stackOverflowClientService,
            JdbcStackoverflowLinkConverter jdbcStackoverflowLinkConverter) {
        return new JdbcStackoverflowLinkService(
                jdbcStackoverflowLinkRepository,
                stackOverflowClientService,
                jdbcStackoverflowLinkConverter);
    }

    @Bean
    public JdbcLinkUpdater linkUpdater(
            ChatToLinkService<ChatToLink> jdbcChatToLinkService,
            GithubLinkService<GithubLink> githubLinkService,
            StackoverflowLinkService<StackoverflowLink> stackoverflowLinkService,
            GitHubClientService gitHubClientService,
            StackOverflowClientService stackOverflowClientService,
            JdbcGithubLinkConverter jdbcGithubLinkConverter,
            JdbcStackoverflowLinkConverter jdbcStackoverflowLinkConverter) {
        return new JdbcLinkUpdater(
                jdbcChatToLinkService,
                githubLinkService,
                stackoverflowLinkService,
                gitHubClientService,
                stackOverflowClientService,
                jdbcGithubLinkConverter,
                jdbcStackoverflowLinkConverter);
    }
}
