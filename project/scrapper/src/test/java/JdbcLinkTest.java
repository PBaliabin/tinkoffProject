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
import ru.tinkoff.edu.java.scrapper.dto.Chat;
import ru.tinkoff.edu.java.scrapper.dto.Link;
import ru.tinkoff.edu.java.scrapper.repository.JdbcChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.JdbcLinkRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@SpringBootTest(classes = {
        ScrapperApplication.class,
        AppTestConfig.class})
public class JdbcLinkTest extends IntegrationEnvironment {

    @Autowired
    private JdbcLinkRepository jdbcLinkRepository;
    @Autowired
    private JdbcChatRepository jdbcChatRepository;

    @Test
    @Transactional
    @Rollback
    public void addLinkTest() {
        Link link = new Link();
        link.setLink("url1");
        link.setLastActivityTime(new Timestamp(System.currentTimeMillis()));
        link.setLastCheckTime(new Timestamp(System.currentTimeMillis()));
        Assertions.assertThat(jdbcLinkRepository.addLink(link)).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    public void updateLinkTest() {
        Link link = new Link();
        link.setLink("url1");
        link.setLastActivityTime(new Timestamp(System.currentTimeMillis()));
        link.setLastCheckTime(new Timestamp(System.currentTimeMillis()));
        jdbcLinkRepository.addLink(link);
        link.setLastActivityTime(new Timestamp(System.currentTimeMillis() + 1000));
        Assertions.assertThat(jdbcLinkRepository.updateLink(link)).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteLinkTest() {
        Link link = new Link();
        link.setLink("url1");
        link.setLastActivityTime(new Timestamp(System.currentTimeMillis()));
        link.setLastCheckTime(new Timestamp(System.currentTimeMillis()));
        jdbcLinkRepository.addLink(link);
        Assertions.assertThat(jdbcLinkRepository.deleteLink(link)).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    public void getAllLinksTest() {
        long time = System.currentTimeMillis();

        Link link1 = new Link();
        link1.setLink("url1");
        link1.setLastActivityTime(new Timestamp(time));
        link1.setLastCheckTime(new Timestamp(System.currentTimeMillis()));
        jdbcLinkRepository.addLink(link1);

        Link link2 = new Link();
        link2.setLink("url2");
        link2.setLastActivityTime(new Timestamp(time));
        link2.setLastCheckTime(new Timestamp(System.currentTimeMillis()));
        jdbcLinkRepository.addLink(link2);

        List<Link> links = jdbcLinkRepository.getAllLinks();

        Assertions.assertThat(links).hasSameElementsAs(List.of(link1, link2));
    }

    @Test
    @Transactional
    @Rollback
    public void getLinkByLastCheckTimeTest() {
        long time = System.currentTimeMillis();

        Link link1 = new Link();
        link1.setLink("url1");
        link1.setLastActivityTime(new Timestamp(time));
        link1.setLastCheckTime(new Timestamp(time));
        jdbcLinkRepository.addLink(link1);

        Link link2 = new Link();
        link2.setLink("url2");
        link2.setLastActivityTime(new Timestamp(time));
        link2.setLastCheckTime(new Timestamp(time));
        jdbcLinkRepository.addLink(link2);

        List<Link> links = jdbcLinkRepository.getLinksBeforeLastCheckTime(new Timestamp(System.currentTimeMillis()));

        Assertions.assertThat(links).hasSameElementsAs(List.of(link1, link2));
    }

    @Test
    @Transactional
    @Rollback
    public void addChatTest() {
        Chat chat = new Chat();
        chat.setChatId("1");
        Assertions.assertThat(jdbcChatRepository.addChat(chat)).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteChatTest() {
        Chat chat = new Chat();
        chat.setChatId("1");
        jdbcChatRepository.addChat(chat);
        Assertions.assertThat(jdbcChatRepository.deleteChat(chat)).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    public void getAllChatsTest() {
        Chat chat1 = new Chat();
        chat1.setChatId("1");
        jdbcChatRepository.addChat(chat1);

        Chat chat2 = new Chat();
        chat2.setChatId("2");
        jdbcChatRepository.addChat(chat2);

        List<Chat> chats = jdbcChatRepository.getAllChats();

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
