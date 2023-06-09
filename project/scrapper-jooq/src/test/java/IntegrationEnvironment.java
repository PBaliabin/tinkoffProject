import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {IntegrationEnvironment.Initializer.class})
abstract class IntegrationEnvironment {

    @Container
    public static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:15"))
                    .withDatabaseName("scrapper")
                    .withUsername("postgres")
                    .withPassword("postgres")
                    .withExposedPorts(5432);

    @Configuration
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(@NotNull ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    configurableApplicationContext,
                    "spring.datasource.url="
                            + POSTGRE_SQL_CONTAINER.getJdbcUrl(),
                    "spring.datasource.username="
                            + POSTGRE_SQL_CONTAINER.getUsername(),
                    "spring.datasource.password="
                            + POSTGRE_SQL_CONTAINER.getPassword(),
                    "spring.datasource.driver-class-name="
                            + POSTGRE_SQL_CONTAINER.getDriverClassName(),
                    "spring.liquibase.enabled="
                            + "false");
        }
    }

}
