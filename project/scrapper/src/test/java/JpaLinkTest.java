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
import ru.tinkoff.edu.java.scrapper.jpa.entity.Chat;
import ru.tinkoff.edu.java.scrapper.jpa.entity.GithubLink;
import ru.tinkoff.edu.java.scrapper.jpa.entity.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.jpa.repository.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.jpa.repository.JpaGithubLinkRepository;
import ru.tinkoff.edu.java.scrapper.jpa.repository.JpaStackoverflowLinkRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@SpringBootTest(classes = {
        ScrapperApplication.class,
        AppTestConfig.class})
public class JpaLinkTest extends IntegrationEnvironment {

    @Autowired
    private JpaChatRepository jpaChatRepository;
    @Autowired
    private JpaGithubLinkRepository jpaGithubLinkRepository;
    @Autowired
    private JpaStackoverflowLinkRepository jpaStackoverflowLinkRepository;

    @Test
    @Transactional
    @Rollback
    public void addChatTest() {
        Chat chat = new Chat();
        chat.setChatId(1L);
        Assertions.assertThat(jpaChatRepository.save(chat)).isEqualTo(chat);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteChatTest() {
        Chat chat = new Chat();
        chat.setChatId(1L);
        jpaChatRepository.save(chat);
        jpaChatRepository.delete(chat);
    }

    @Test
    @Transactional
    @Rollback
    public void getAllChatsTest() {
        Chat chat1 = new Chat();
        chat1.setChatId(1L);
        jpaChatRepository.save(chat1);

        Chat chat2 = new Chat();
        chat2.setChatId(2L);
        jpaChatRepository.save(chat2);

        List<Chat> chats = (List<Chat>) jpaChatRepository.findAll();
        Assertions.assertThat(chats).hasSameElementsAs(List.of(chat1, chat2));
    }

    @Test
    @Transactional
    @Rollback
    public void addGithubLinkTest() {
        long time = System.currentTimeMillis();
        GithubLink githubLink = new GithubLink();
        githubLink.setLink("url1");
        githubLink.setLastActivityTime(new Timestamp(time).toLocalDateTime());
        githubLink.setLastCheckTime(new Timestamp(time).toLocalDateTime());
        Assertions.assertThat(jpaGithubLinkRepository.save(githubLink)).isEqualTo(githubLink);
    }

    @Test
    @Transactional
    @Rollback
    public void updateGithubLinkTest() {
        long time = System.currentTimeMillis();
        GithubLink githubLink = new GithubLink();
        githubLink.setLink("url1");
        githubLink.setLastActivityTime(new Timestamp(time).toLocalDateTime());
        githubLink.setLastCheckTime(new Timestamp(time).toLocalDateTime());
        jpaGithubLinkRepository.save(githubLink);
        githubLink.setLastActivityTime(new Timestamp(time + 1000).toLocalDateTime());
        Assertions.assertThat(jpaGithubLinkRepository.save(githubLink)).isEqualTo(githubLink);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteGithubLinkTest() {
        long time = System.currentTimeMillis();
        GithubLink githubLink = new GithubLink();
        githubLink.setLink("url1");
        githubLink.setLastActivityTime(new Timestamp(time).toLocalDateTime());
        githubLink.setLastCheckTime(new Timestamp(time).toLocalDateTime());
        jpaGithubLinkRepository.save(githubLink);
        jpaGithubLinkRepository.delete(githubLink);
    }

    @Test
    @Transactional
    @Rollback
    public void getAllGithubLinksTest() {
        long time = System.currentTimeMillis();

        GithubLink githubLink1 = new GithubLink();
        githubLink1.setLink("url1");
        githubLink1.setLastActivityTime(new Timestamp(time).toLocalDateTime());
        githubLink1.setLastCheckTime(new Timestamp(time).toLocalDateTime());
        jpaGithubLinkRepository.save(githubLink1);

        GithubLink githubLink2 = new GithubLink();
        githubLink2.setLink("url2");
        githubLink2.setLastActivityTime(new Timestamp(time).toLocalDateTime());
        githubLink2.setLastCheckTime(new Timestamp(time).toLocalDateTime());
        jpaGithubLinkRepository.save(githubLink2);

        List<GithubLink> githubLinks = (List<GithubLink>) jpaGithubLinkRepository.findAll();

        Assertions.assertThat(githubLinks).hasSameElementsAs(List.of(githubLink1, githubLink2));
    }

    @Test
    @Transactional
    @Rollback
    public void getGithubLinksByLastCheckTimeTest() {
        long time = System.currentTimeMillis();

        GithubLink githubLink1 = new GithubLink();
        githubLink1.setLink("url1");
        githubLink1.setLastActivityTime(new Timestamp(time).toLocalDateTime());
        githubLink1.setLastCheckTime(new Timestamp(time).toLocalDateTime());
        jpaGithubLinkRepository.save(githubLink1);

        GithubLink githubLink2 = new GithubLink();
        githubLink2.setLink("url2");
        githubLink2.setLastActivityTime(new Timestamp(time).toLocalDateTime());
        githubLink2.setLastCheckTime(new Timestamp(time).toLocalDateTime());
        jpaGithubLinkRepository.save(githubLink2);

        List<GithubLink> githubLinks = (List<GithubLink>) jpaGithubLinkRepository
                .getGithubLinksByLastCheckTimeBefore(new Timestamp(time).toLocalDateTime());

        Assertions.assertThat(githubLinks).hasSameElementsAs(List.of(githubLink1, githubLink2));
    }

    @Test
    @Transactional
    @Rollback
    public void addStackoverflowLinkTest() {
        long time = System.currentTimeMillis();
        StackoverflowLink stackoverflowLink = new StackoverflowLink();
        stackoverflowLink.setLink("url1");
        stackoverflowLink.setLastActivityTime(new Timestamp(time).toLocalDateTime());
        stackoverflowLink.setLastCheckTime(new Timestamp(time).toLocalDateTime());
        Assertions.assertThat(jpaStackoverflowLinkRepository.save(stackoverflowLink)).isEqualTo(stackoverflowLink);
    }

    @Test
    @Transactional
    @Rollback
    public void updateStackoverflowLinkTest() {
        long time = System.currentTimeMillis();
        StackoverflowLink stackoverflowLink = new StackoverflowLink();
        stackoverflowLink.setLink("url1");
        stackoverflowLink.setLastActivityTime(new Timestamp(time).toLocalDateTime());
        stackoverflowLink.setLastCheckTime(new Timestamp(time).toLocalDateTime());
        jpaStackoverflowLinkRepository.save(stackoverflowLink);
        stackoverflowLink.setLastActivityTime(new Timestamp(time + 1000).toLocalDateTime());
        Assertions.assertThat(jpaStackoverflowLinkRepository.save(stackoverflowLink)).isEqualTo(stackoverflowLink);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteStackoverflowLinkTest() {
        long time = System.currentTimeMillis();
        StackoverflowLink stackoverflowLink = new StackoverflowLink();
        stackoverflowLink.setLink("url1");
        stackoverflowLink.setLastActivityTime(new Timestamp(time).toLocalDateTime());
        stackoverflowLink.setLastCheckTime(new Timestamp(time).toLocalDateTime());
        jpaStackoverflowLinkRepository.save(stackoverflowLink);
        jpaStackoverflowLinkRepository.delete(stackoverflowLink);
    }

    @Test
    @Transactional
    @Rollback
    public void getAllStackoverflowLinksTest() {
        long time = System.currentTimeMillis();

        StackoverflowLink stackoverflowLink1 = new StackoverflowLink();
        stackoverflowLink1.setLink("url1");
        stackoverflowLink1.setLastActivityTime(new Timestamp(time).toLocalDateTime());
        stackoverflowLink1.setLastCheckTime(new Timestamp(time).toLocalDateTime());
        jpaStackoverflowLinkRepository.save(stackoverflowLink1);

        StackoverflowLink stackoverflowLink2 = new StackoverflowLink();
        stackoverflowLink2.setLink("url2");
        stackoverflowLink2.setLastActivityTime(new Timestamp(time).toLocalDateTime());
        stackoverflowLink2.setLastCheckTime(new Timestamp(time).toLocalDateTime());
        jpaStackoverflowLinkRepository.save(stackoverflowLink2);

        List<StackoverflowLink> stackoverflowLinks = (List<StackoverflowLink>) jpaStackoverflowLinkRepository.findAll();

        Assertions.assertThat(stackoverflowLinks).hasSameElementsAs(List.of(stackoverflowLink1, stackoverflowLink2));
    }

    @Test
    @Transactional
    @Rollback
    public void getStackoverflowLinksByLastCheckTimeTest() {
        long time = System.currentTimeMillis();

        StackoverflowLink stackoverflowLink1 = new StackoverflowLink();
        stackoverflowLink1.setLink("url1");
        stackoverflowLink1.setLastActivityTime(new Timestamp(time).toLocalDateTime());
        stackoverflowLink1.setLastCheckTime(new Timestamp(time).toLocalDateTime());
        jpaStackoverflowLinkRepository.save(stackoverflowLink1);

        StackoverflowLink stackoverflowLink2 = new StackoverflowLink();
        stackoverflowLink2.setLink("url2");
        stackoverflowLink2.setLastActivityTime(new Timestamp(time).toLocalDateTime());
        stackoverflowLink2.setLastCheckTime(new Timestamp(time).toLocalDateTime());
        jpaStackoverflowLinkRepository.save(stackoverflowLink2);

        List<StackoverflowLink> stackoverflowLinks = (List<StackoverflowLink>) jpaStackoverflowLinkRepository
                .getStackoverflowLinksByLastCheckTimeBefore(new Timestamp(time).toLocalDateTime());
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

        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new Liquibase("master.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.update(new Contexts(), new LabelExpression());
    }
}
