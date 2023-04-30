import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.client.GitHubClientService;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClientService;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatRecord;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.GithubLinkRecord;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.StackoverflowLinkRecord;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.StackoverflowResponse;
import ru.tinkoff.edu.java.scrapper.jooq.service.JooqChatService;
import ru.tinkoff.edu.java.scrapper.jooq.service.JooqGitHubLinkService;
import ru.tinkoff.edu.java.scrapper.jooq.service.JooqStackoverflowLinkService;
import ru.tinkoff.edu.java.scrapper.jooq.util.converter.JooqGithubLinkConverter;
import ru.tinkoff.edu.java.scrapper.jooq.util.converter.JooqStackoverflowLinkConverter;

import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@SpringBootTest(classes = {
        ScrapperApplication.class,
        AppTestConfig.class})
public class JooqLinkTest extends IntegrationEnvironment {

    @Autowired
    private JooqChatService jooqChatService;
    @Autowired
    private JooqGitHubLinkService jooqGitHubLinkService;
    @Autowired
    private JooqStackoverflowLinkService jooqStackoverflowLinkService;
    @Autowired
    private JooqGithubLinkConverter jooqGithubLinkConverter;
    @Autowired
    private JooqStackoverflowLinkConverter jooqStackoverflowLinkConverter;
    @Autowired
    private GitHubClientService gitHubClientService;
    @Autowired
    private StackOverflowClientService stackOverflowClientService;

    @Test
    @Transactional
    @Rollback
    public void addStackoverflowLinkTest() {
        jooqStackoverflowLinkService.add(URI.create("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c"));
    }

    @Test
    @Transactional
    @Rollback
    public void updateStackoverflowTest() {
        jooqStackoverflowLinkService.add(URI.create("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c"));
        StackoverflowResponse stackoverflowResponse = stackOverflowClientService.getQuestion("1642028", "stackoverflow");
        StackoverflowLinkRecord stackoverflowLinkRecord = jooqStackoverflowLinkConverter.makeStackoverflowLinkRecord(
                "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c",
                stackoverflowResponse);
        jooqStackoverflowLinkService.update(stackoverflowLinkRecord);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteStackoverflowTest() {
        jooqStackoverflowLinkService.add(URI.create("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c"));
        jooqGitHubLinkService.remove(URI.create("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c"));
    }

    @Test
    @Transactional
    @Rollback
    public void getStackoverflowLinkByLastCheckTimeTest() {
        jooqStackoverflowLinkService.add(URI.create("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c"));
        StackoverflowResponse stackoverflowResponse = stackOverflowClientService.getQuestion("1642028", "stackoverflow");
        StackoverflowLinkRecord stackoverflowLinkRecord = jooqStackoverflowLinkConverter.makeStackoverflowLinkRecord(
                "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c",
                stackoverflowResponse);

        List<StackoverflowLinkRecord> links = (List<StackoverflowLinkRecord>) jooqStackoverflowLinkService
                .getLinksByLastCheckTime(new Timestamp(System.currentTimeMillis()).toLocalDateTime());

        Assertions.assertThat(links).hasSameElementsAs(List.of(stackoverflowLinkRecord));
    }

    @Test
    @Transactional
    @Rollback
    public void addGithubLinkTest() {
        jooqGitHubLinkService.add(URI.create("https://github.com/sanyarnd/tinkoff-java-course-2022/"));
    }

    @Test
    @Transactional
    @Rollback
    public void updateGithubLinkTest() {
        jooqGitHubLinkService.add(URI.create("https://github.com/sanyarnd/tinkoff-java-course-2022/"));
        GitHubResponse gitHubResponse = gitHubClientService.getRepo("sanyarnd", "tinkoff-java-course-2022");
        GithubLinkRecord githubLinkRecord = jooqGithubLinkConverter.makeGithubLinkRecord(
                "https://github.com/sanyarnd/tinkoff-java-course-2022/",
                gitHubResponse);
        jooqGitHubLinkService.update(githubLinkRecord);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteGithubLinkTest() {
        jooqGitHubLinkService.add(URI.create("https://github.com/sanyarnd/tinkoff-java-course-2022/"));
        jooqGitHubLinkService.remove(URI.create("https://github.com/sanyarnd/tinkoff-java-course-2022/"));
    }

    @Test
    @Transactional
    @Rollback
    public void getGithubLinkByLastCheckTimeTest() {
        jooqGitHubLinkService.add(URI.create("https://github.com/sanyarnd/tinkoff-java-course-2022/"));
        GitHubResponse gitHubResponse = gitHubClientService.getRepo("sanyarnd", "tinkoff-java-course-2022");
        GithubLinkRecord githubLinkRecord = jooqGithubLinkConverter.makeGithubLinkRecord(
                "https://github.com/sanyarnd/tinkoff-java-course-2022/",
                gitHubResponse);

        List<GithubLinkRecord> links = (List<GithubLinkRecord>) jooqGitHubLinkService
                .getLinksByLastCheckTime(new Timestamp(System.currentTimeMillis()).toLocalDateTime());

        Assertions.assertThat(links).hasSameElementsAs(List.of(githubLinkRecord));
    }

    @Test
    @Transactional
    @Rollback
    public void addChatTest() {
        jooqChatService.register(1L);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteChatTest() {
        jooqChatService.register(1L);
        jooqChatService.unregister(1L);
    }

    @Test
    @Transactional
    @Rollback
    public void getAllChatsTest() {
        ChatRecord chat1 = new ChatRecord(1L);
        ChatRecord chat2 = new ChatRecord(2L);
        jooqChatService.register(chat1.getChatId());
        jooqChatService.register(chat2.getChatId());

        List<ChatRecord> chats = (List<ChatRecord>) jooqChatService.getAll();

        Assertions.assertThat(chats).hasSameElementsAs(List.of(chat1, chat2));
    }

    @BeforeAll
    public static void beforeAll() throws SQLException, LiquibaseException {
        POSTGRE_SQL_CONTAINER.start();

        try {
            Class.forName(POSTGRE_SQL_CONTAINER.getDriverClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection connection = POSTGRE_SQL_CONTAINER.createConnection("");

        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new liquibase.Liquibase("master.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.update(new Contexts(), new LabelExpression());
    }
}
