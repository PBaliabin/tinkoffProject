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
import ru.tinkoff.edu.java.scrapper.jdbc.util.converter.JdbcGithubLinkConverter;
import ru.tinkoff.edu.java.scrapper.jdbc.util.converter.JdbcStackoverflowLinkConverter;

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
    public JdbcGithubLinkRepository jdbcGithubLinkRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                                             JdbcGithubLinkConverter jdbcGithubLinkConverter) {
        return new JdbcGithubLinkRepository(namedParameterJdbcTemplate, jdbcGithubLinkConverter);
    }

    @Bean
    public JdbcStackoverflowLinkRepository jdbcStackoverflowLinkRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                                                           JdbcStackoverflowLinkConverter jdbcStackoverflowLinkConverter) {
        return new JdbcStackoverflowLinkRepository(namedParameterJdbcTemplate, jdbcStackoverflowLinkConverter);
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
                                                           JdbcGithubLinkConverter jdbcGithubLinkConverter) {
        return new JdbcGithubLinkService(jdbcGithubLinkRepository, gitHubClientService, jdbcGithubLinkConverter);
    }

    @Bean
    public StackoverflowLinkService<StackoverflowLink> stackoverflowLinkService(JdbcStackoverflowLinkRepository jdbcStackoverflowLinkRepository,
                                                                                StackOverflowClientService stackOverflowClientService,
                                                                                JdbcStackoverflowLinkConverter jdbcStackoverflowLinkConverter) {
        return new JdbcStackoverflowLinkService(jdbcStackoverflowLinkRepository, stackOverflowClientService, jdbcStackoverflowLinkConverter);
    }

    @Bean
    public LinkUpdater linkUpdater(ChatToLinkService<ChatToLink> jdbcChatToLinkService,
                                   GithubLinkService<GithubLink> githubLinkService,
                                   StackoverflowLinkService<StackoverflowLink> stackoverflowLinkService,
                                   GitHubClientService gitHubClientService,
                                   StackOverflowClientService stackOverflowClientService,
                                   TgBotClientService tgBotClientService,
                                   JdbcGithubLinkConverter jdbcGithubLinkConverter,
                                   JdbcStackoverflowLinkConverter jdbcStackoverflowLinkConverter) {
        return new JdbcLinkUpdater(
                jdbcChatToLinkService,
                githubLinkService,
                stackoverflowLinkService,
                gitHubClientService,
                stackOverflowClientService,
                tgBotClientService,
                jdbcGithubLinkConverter,
                jdbcStackoverflowLinkConverter);
    }
}
