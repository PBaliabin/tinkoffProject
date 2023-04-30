package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.tinkoff.edu.java.scrapper.client.GitHubClientService;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClientService;
import ru.tinkoff.edu.java.scrapper.client.TgBotClientService;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.dto.entity.ChatToLink;
import ru.tinkoff.edu.java.scrapper.dto.entity.GithubLink;
import ru.tinkoff.edu.java.scrapper.dto.entity.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.inteface.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.inteface.service.ChatService;
import ru.tinkoff.edu.java.scrapper.inteface.service.ChatToLinkService;
import ru.tinkoff.edu.java.scrapper.inteface.service.GithubLinkService;
import ru.tinkoff.edu.java.scrapper.inteface.service.StackoverflowLinkService;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcChatRepository;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcChatToLinkRepository;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcGithubLinkRepository;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcStackoverflowLinkRepository;
import ru.tinkoff.edu.java.scrapper.jdbc.service.*;
import ru.tinkoff.edu.java.scrapper.jdbc.util.JdbcTypeConverter;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public JdbcTypeConverter jdbcTypeConverter() {
        return new JdbcTypeConverter();
    }

    @Bean
    public JdbcChatRepository jdbcChatRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new JdbcChatRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public JdbcChatToLinkRepository jdbcChatToLinkRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new JdbcChatToLinkRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public JdbcGithubLinkRepository jdbcGithubLinkRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                                             JdbcTypeConverter jdbcTypeConverter) {
        return new JdbcGithubLinkRepository(namedParameterJdbcTemplate, jdbcTypeConverter);
    }

    @Bean
    public JdbcStackoverflowLinkRepository jdbcStackoverflowLinkRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                                                           JdbcTypeConverter jdbcTypeConverter) {
        return new JdbcStackoverflowLinkRepository(namedParameterJdbcTemplate, jdbcTypeConverter);
    }

    @Bean
    public ChatService<Chat> chatService(JdbcChatRepository jdbcChatRepository) {
        return new JdbcChatService(jdbcChatRepository);
    }

    @Bean
    public ChatToLinkService<ChatToLink> chatToLinkService(JdbcChatToLinkRepository jdbcChatToLinkRepository) {
        return new JdbcChatToLinkService(jdbcChatToLinkRepository);
    }

    @Bean
    public GithubLinkService<GithubLink> githubLinkService(JdbcGithubLinkRepository jdbcGithubLinkRepository,
                                                           GitHubClientService gitHubClientService,
                                                           JdbcTypeConverter jdbcTypeConverter) {
        return new JdbcGithubLinkService(jdbcGithubLinkRepository, gitHubClientService, jdbcTypeConverter);
    }

    @Bean
    public StackoverflowLinkService<StackoverflowLink> stackoverflowLinkService(JdbcStackoverflowLinkRepository jdbcStackoverflowLinkRepository,
                                                                                StackOverflowClientService stackOverflowClientService,
                                                                                JdbcTypeConverter jdbcTypeConverter) {
        return new JdbcStackoverflowLinkService(jdbcStackoverflowLinkRepository, stackOverflowClientService, jdbcTypeConverter);
    }

    @Bean
    public LinkUpdater linkUpdater(ChatToLinkService<ChatToLink> jdbcChatToLinkService,
                                   GithubLinkService<GithubLink> githubLinkService,
                                   StackoverflowLinkService<StackoverflowLink> stackoverflowLinkService,
                                   GitHubClientService gitHubClientService,
                                   StackOverflowClientService stackOverflowClientService,
                                   TgBotClientService tgBotClientService,
                                   JdbcTypeConverter jdbcTypeConverter) {
        return new JdbcLinkUpdater(
                jdbcChatToLinkService,
                githubLinkService,
                stackoverflowLinkService,
                gitHubClientService,
                stackOverflowClientService,
                tgBotClientService,
                jdbcTypeConverter);
    }
}
