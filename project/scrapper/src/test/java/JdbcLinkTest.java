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
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.dto.entity.GithubLink;
import ru.tinkoff.edu.java.scrapper.dto.entity.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcChatRepository;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcGithubLinkRepository;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcStackoverflowLinkRepository;

import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@SpringBootTest(classes = {
        ScrapperApplication.class,
        AppTestConfig.class})
public class JdbcLinkTest extends IntegrationEnvironment {

    @Autowired
    private JdbcChatRepository jdbcChatRepository;
    @Autowired
    private JdbcGithubLinkRepository jdbcGithubLinkRepository;
    @Autowired
    private JdbcStackoverflowLinkRepository jdbcStackoverflowLinkRepository;

    @Test
    @Transactional
    @Rollback
    public void addChatTest() {
        Chat chat = new Chat();
        chat.setChatId(1L);
        jdbcChatRepository.register(chat);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteChatTest() {
        Chat chat = new Chat();
        chat.setChatId(1L);
        jdbcChatRepository.register(chat);
        jdbcChatRepository.unregister(chat);
    }

    @Test
    @Transactional
    @Rollback
    public void getAllChatsTest() {
        Chat chat1 = new Chat();
        chat1.setChatId(1L);
        jdbcChatRepository.register(chat1);

        Chat chat2 = new Chat();
        chat2.setChatId(2L);
        jdbcChatRepository.register(chat2);

        List<Chat> chats = jdbcChatRepository.getAll();
        Assertions.assertThat(chats).hasSameElementsAs(List.of(chat1, chat2));
    }

    @Test
    @Transactional
    @Rollback
    public void addGithubLinkTest() {
        GithubLink githubLink = makeGithubLinkWithId(1);
        jdbcGithubLinkRepository.add(githubLink);
    }

    @Test
    @Transactional
    @Rollback
    public void updateGithubLinkTest() {
        GithubLink githubLink = makeGithubLinkWithId(1);
        jdbcGithubLinkRepository.add(githubLink);
        githubLink.setLastActivityTime(new Timestamp(System.currentTimeMillis()
                                                             + 1000).toLocalDateTime());
        jdbcGithubLinkRepository.update(githubLink);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteGithubLinkTest() {
        GithubLink githubLink = makeGithubLinkWithId(1);
        jdbcGithubLinkRepository.add(githubLink);
        jdbcGithubLinkRepository.remove(URI.create(githubLink.getLink()));
    }

    @Test
    @Transactional
    @Rollback
    public void getAllGithubLinksTest() {
        GithubLink githubLink1 = makeGithubLinkWithId(1);
        jdbcGithubLinkRepository.add(githubLink1);

        GithubLink githubLink2 = makeGithubLinkWithId(2);
        jdbcGithubLinkRepository.add(githubLink2);

        List<GithubLink> githubLinks = jdbcGithubLinkRepository.getAll();

        Assertions.assertThat(githubLinks).hasSameElementsAs(List.of(githubLink1, githubLink2));
    }

    @Test
    @Transactional
    @Rollback
    public void getGithubLinksByLastCheckTimeTest() {
        GithubLink githubLink1 = makeGithubLinkWithId(1);
        jdbcGithubLinkRepository.add(githubLink1);

        GithubLink githubLink2 = makeGithubLinkWithId(2);
        jdbcGithubLinkRepository.add(githubLink2);

        List<GithubLink> githubLinks =
                jdbcGithubLinkRepository.getLinksBeforeLastCheckTime(new Timestamp(System.currentTimeMillis()).toLocalDateTime());

        Assertions.assertThat(githubLinks).hasSameElementsAs(List.of(githubLink1, githubLink2));
    }

    @Test
    @Transactional
    @Rollback
    public void addStackoverflowLinkTest() {
        StackoverflowLink stackoverflowLink = makeStackoverflowLinkWithId(1);
        jdbcStackoverflowLinkRepository.add(stackoverflowLink);
    }

    @Test
    @Transactional
    @Rollback
    public void updateStackoverflowLinkTest() {
        StackoverflowLink stackoverflowLink = makeStackoverflowLinkWithId(1);
        jdbcStackoverflowLinkRepository.add(stackoverflowLink);
        stackoverflowLink.setLastActivityTime(new Timestamp(System.currentTimeMillis()
                                                                    + 1000).toLocalDateTime());
        jdbcStackoverflowLinkRepository.update(stackoverflowLink);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteStackoverflowLinkTest() {
        StackoverflowLink stackoverflowLink = makeStackoverflowLinkWithId(1);
        jdbcStackoverflowLinkRepository.add(stackoverflowLink);
        jdbcStackoverflowLinkRepository.remove(URI.create(stackoverflowLink.getLink()));
    }

    @Test
    @Transactional
    @Rollback
    public void getAllStackoverflowLinksTest() {
        StackoverflowLink stackoverflowLink1 = makeStackoverflowLinkWithId(1);
        jdbcStackoverflowLinkRepository.add(stackoverflowLink1);

        StackoverflowLink stackoverflowLink2 = makeStackoverflowLinkWithId(2);
        jdbcStackoverflowLinkRepository.add(stackoverflowLink2);

        List<StackoverflowLink> stackoverflowLinks = jdbcStackoverflowLinkRepository.getAll();

        Assertions.assertThat(stackoverflowLinks).hasSameElementsAs(List.of(stackoverflowLink1, stackoverflowLink2));
    }

    @Test
    @Transactional
    @Rollback
    public void getStackoverflowLinksByLastCheckTimeTest() {
        StackoverflowLink stackoverflowLink1 = makeStackoverflowLinkWithId(1);
        jdbcStackoverflowLinkRepository.add(stackoverflowLink1);

        StackoverflowLink stackoverflowLink2 = makeStackoverflowLinkWithId(2);
        jdbcStackoverflowLinkRepository.add(stackoverflowLink2);

        List<StackoverflowLink> stackoverflowLinks =
                jdbcStackoverflowLinkRepository.getLinksBeforeLastCheckTime(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
        Assertions.assertThat(stackoverflowLinks).hasSameElementsAs(List.of(stackoverflowLink1, stackoverflowLink2));
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

        Database database =
                DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new Liquibase("master.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.update(new Contexts(), new LabelExpression());
    }

    private GithubLink makeGithubLinkWithId(int id) {
        return new GithubLink(
                "url"
                        + id,
                "repo"
                        + id,
                "name"
                        + id,
                "fullName"
                        + id,
                new Timestamp(System.currentTimeMillis()).toLocalDateTime(),
                3
                        * id,
                4
                        * id,
                new Timestamp(System.currentTimeMillis()).toLocalDateTime()
        );
    }

    private StackoverflowLink makeStackoverflowLinkWithId(int id) {
        return new StackoverflowLink(
                "url"
                        + id,
                100
                        + id,
                101
                        + id,
                new Timestamp(System.currentTimeMillis()).toLocalDateTime(),
                false,
                3
                        * id,
                new Timestamp(System.currentTimeMillis()).toLocalDateTime()
        );
    }
}
