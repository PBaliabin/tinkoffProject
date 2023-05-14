import app.ApplicationConfig;
import app.codegen.JooqCodegen;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@SpringBootTest(classes = {ApplicationConfig.class,
                           AppTestConfig.class,
                           JooqCodegen.class})
public class LiquibaseIntegrationTest extends IntegrationEnvironment {

    @Autowired
    private JooqCodegen jooqCodegen;

    @BeforeAll
    public static void beforeAll() {
        POSTGRE_SQL_CONTAINER.start();
    }

    @Test
    public void generateClassesTest() throws LiquibaseException, SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection connection = POSTGRE_SQL_CONTAINER.createConnection("");

        Database database =
                DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new Liquibase("master.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.update(new Contexts(), new LabelExpression());

        DatabaseMetaData md = connection.getMetaData();
        ResultSet rs = md.getTables(connection.getCatalog(), "", null, new String[]{"TABLE"});
        HashSet<String> stringsReceived = new HashSet<>();
        while (rs.next()) {
            stringsReceived.add(rs.getString("TABLE_NAME"));
        }

        try {
            jooqCodegen.generateClasses();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        Assertions
                .assertThat(stringsReceived)
                .hasSameElementsAs(
                        Set.of(
                                "chat",
                                "chat_to_link",
                                "databasechangelog",
                                "databasechangeloglock",
                                "github_link",
                                "stackoverflow_link"));
    }
}
