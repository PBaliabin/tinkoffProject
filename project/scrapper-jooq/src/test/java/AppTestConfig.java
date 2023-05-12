import org.jooq.meta.jaxb.Jdbc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

@TestConfiguration
@Import(IntegrationEnvironment.Initializer.class)
public class AppTestConfig {

    @Bean
    @Primary
    public Jdbc testDataSource(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password,
            @Value("${spring.datasource.driver-class-name}") String driverName) {
        return new Jdbc()
                .withDriver(driverName)
                .withUrl(url)
                .withUsername(username)
                .withPassword(password);
    }
}
