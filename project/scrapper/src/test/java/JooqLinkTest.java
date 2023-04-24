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
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.GithubLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatRecord;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.GithubLinkRecord;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.StackoverflowLinkRecord;
import ru.tinkoff.edu.java.scrapper.dto.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.dto.StackoverflowResponse;
import ru.tinkoff.edu.java.scrapper.jooq.JooqChatService;
import ru.tinkoff.edu.java.scrapper.jooq.JooqGitHubLinkService;
import ru.tinkoff.edu.java.scrapper.jooq.JooqStackoverflowLinkService;
import ru.tinkoff.edu.java.scrapper.service.TypeConverter;

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
    private TypeConverter typeConverter;
    @Autowired
    private GitHubClientService gitHubClientService;
    @Autowired
    private StackOverflowClientService stackOverflowClientService;

    @Test
    @Transactional
    @Rollback
    public void addStackoverflowLinkTest() {
        StackoverflowResponse stackoverflowResponse = stackOverflowClientService.getQuestion("1642028", "stackoverflow");
        Assertions.assertThat(
                jooqStackoverflowLinkService.add(
                        typeConverter.makeStackoverflowLink(
                                typeConverter.makeStackoverflowLinkRecord(
                                        "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c",
                                        stackoverflowResponse))))
                .isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    public void updateStackoverflowTest() {
        StackoverflowResponse stackoverflowResponse = stackOverflowClientService.getQuestion("1642028", "stackoverflow");
        StackoverflowLink stackoverflowLink = typeConverter.makeStackoverflowLink(
                typeConverter.makeStackoverflowLinkRecord(
                        "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c",
                        stackoverflowResponse));
        jooqStackoverflowLinkService.add(stackoverflowLink);
        stackoverflowLink.setAnswerCount(3);
        jooqStackoverflowLinkService.add(stackoverflowLink);
        Assertions.assertThat(jooqStackoverflowLinkService.update(stackoverflowLink)).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteStackoverflowTest() {
        StackoverflowResponse stackoverflowResponse = stackOverflowClientService.getQuestion("1642028", "stackoverflow");
        StackoverflowLink stackoverflowLink = typeConverter.makeStackoverflowLink(
                typeConverter.makeStackoverflowLinkRecord(
                        "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c",
                        stackoverflowResponse));
        jooqStackoverflowLinkService.add(stackoverflowLink);
        Assertions.assertThat(jooqGitHubLinkService.remove(URI.create("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c"))).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    public void getStackoverflowLinkByLastCheckTimeTest() {
        StackoverflowResponse stackoverflowResponse = stackOverflowClientService.getQuestion("1642028", "stackoverflow");
        StackoverflowLink stackoverflowLink = typeConverter.makeStackoverflowLink(
                typeConverter.makeStackoverflowLinkRecord(
                        "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c",
                        stackoverflowResponse));
        jooqStackoverflowLinkService.add(stackoverflowLink);

        List<StackoverflowLinkRecord> links = (List<StackoverflowLinkRecord>) jooqStackoverflowLinkService
                .getLinksByLastCheckTime(new Timestamp(System.currentTimeMillis()).toLocalDateTime());

        Assertions.assertThat(links).hasSameElementsAs(List.of(new StackoverflowLinkRecord(stackoverflowLink)));
    }

    @Test
    @Transactional
    @Rollback
    public void addGithubLinkTest() {
        GitHubResponse gitHubResponse = gitHubClientService.getRepo("sanyarnd","tinkoff-java-course-2022");;
        Assertions.assertThat(
                        jooqGitHubLinkService.add(
                                typeConverter.makeGithubLink(
                                        typeConverter.makeGithubLinkRecord(
                                                "https://github.com/sanyarnd/tinkoff-java-course-2022/",
                                                gitHubResponse))))
                .isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    public void updateGithubLinkTest() {
        GitHubResponse gitHubResponse = gitHubClientService.getRepo("sanyarnd","tinkoff-java-course-2022");
        GithubLink githubLink = typeConverter.makeGithubLink(
                typeConverter.makeGithubLinkRecord(
                        "https://github.com/sanyarnd/tinkoff-java-course-2022/",
                        gitHubResponse));
        jooqGitHubLinkService.add(githubLink);
        githubLink.setForksCount(3);
        jooqGitHubLinkService.add(githubLink);
        Assertions.assertThat(jooqGitHubLinkService.update(githubLink)).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteGithubLinkTest() {
        GitHubResponse gitHubResponse = gitHubClientService.getRepo("sanyarnd","tinkoff-java-course-2022");
        GithubLink githubLink = typeConverter.makeGithubLink(
                typeConverter.makeGithubLinkRecord(
                        "https://github.com/sanyarnd/tinkoff-java-course-2022/",
                        gitHubResponse));
        jooqGitHubLinkService.add(githubLink);
        Assertions.assertThat(jooqGitHubLinkService.remove(URI.create("https://github.com/sanyarnd/tinkoff-java-course-2022/"))).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    public void getGithubLinkByLastCheckTimeTest() {
        GitHubResponse gitHubResponse = gitHubClientService.getRepo("sanyarnd","tinkoff-java-course-2022");
        GithubLink githubLink = typeConverter.makeGithubLink(
                typeConverter.makeGithubLinkRecord(
                        "https://github.com/sanyarnd/tinkoff-java-course-2022/",
                        gitHubResponse));
        jooqGitHubLinkService.add(githubLink);

        List<GithubLinkRecord> links = (List<GithubLinkRecord>) jooqGitHubLinkService
                .getLinksByLastCheckTime(new Timestamp(System.currentTimeMillis()).toLocalDateTime());

        Assertions.assertThat(links).hasSameElementsAs(List.of(new GithubLinkRecord(githubLink)));
    }

    @Test
    @Transactional
    @Rollback
    public void addChatTest() {
        jooqChatService.register("1");
    }

    @Test
    @Transactional
    @Rollback
    public void deleteChatTest() {
        jooqChatService.register("1");
        jooqChatService.unregister("1");
    }

    @Test
    @Transactional
    @Rollback
    public void getAllChatsTest() {
        ChatRecord chat1 = new ChatRecord("1");
        ChatRecord chat2 = new ChatRecord("2");
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
