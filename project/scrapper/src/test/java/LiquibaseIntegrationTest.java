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

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class LiquibaseIntegrationTest extends IntegrationEnvironment{

    @Test
    public void testingLiquibase() throws LiquibaseException, SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection connection = POSTGRE_SQL_CONTAINER.createConnection("");

        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new liquibase.Liquibase("master.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.update(new Contexts(), new LabelExpression());

        DatabaseMetaData md = connection.getMetaData();
        ResultSet rs = md.getTables(connection.getCatalog(), "", null, new String[]{"TABLE"});
        HashSet<String> stringsReceived = new HashSet<>();
        while(rs.next()) {
            stringsReceived.add(rs.getString("TABLE_NAME"));
        }

        Assertions.assertThat(stringsReceived).hasSameElementsAs(Set.of("chat",
                "chat_to_link",
                "databasechangelog",
                "databasechangeloglock",
                "link"));
    }

    @BeforeAll
    public static void beforeAll() {
        POSTGRE_SQL_CONTAINER.start();
    }
}
