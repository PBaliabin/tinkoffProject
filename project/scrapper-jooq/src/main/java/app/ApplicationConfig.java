package app;

import org.jooq.meta.jaxb.Configuration;
import org.jooq.meta.jaxb.Database;
import org.jooq.meta.jaxb.Generate;
import org.jooq.meta.jaxb.Generator;
import org.jooq.meta.jaxb.Jdbc;
import org.jooq.meta.jaxb.Property;
import org.jooq.meta.jaxb.Target;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;


@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig() {

    @Bean
    public Database database() {
        return new Database()
                .withName("org.jooq.meta.postgres.PostgresDatabase")
                .withInputSchema("public")
                .withProperties(
                        new Property().withKey("rootPath")
                                      .withValue("scrapper/src/main/resources"),
                        new Property().withKey("scripts").withValue("master.xml"),
                        new Property().withKey("includeLiquibaseTables").withValue("false"));
    }

    @Bean
    public Generate generate() {
        return new Generate().withGeneratedAnnotation(true)
                             .withGeneratedAnnotationDate(false)
                             .withNullableAnnotation(true)
                             .withNullableAnnotationType("org.jetbrains.annotations.Nullable")
                             .withNonnullAnnotation(true)
                             .withNonnullAnnotationType("org.jetbrains.annotations.NotNull")
                             .withJpaAnnotations(false)
                             .withValidationAnnotations(true)
                             .withSpringAnnotations(true)
                             .withConstructorPropertiesAnnotation(true)
                             .withConstructorPropertiesAnnotationOnPojos(true)
                             .withConstructorPropertiesAnnotationOnRecords(true)
                             .withFluentSetters(false)
                             .withDaos(false)
                             .withPojos(true);
    }

    @Bean
    public Target target() {
        return new Target().withPackageName("ru.tinkoff.edu.java.scrapper.domain.jooq")
                           .withDirectory("../scrapper/src/main/java");
    }

    @Bean
    public Jdbc jdbc() {
        return new Jdbc()
                .withDriver("org.postgresql.Driver")
                .withUrl("jdbc:postgresql://localhost:5432/scrapper")
                .withUsername("postgres")
                .withPassword("postgres");
    }

    @Bean
    public Generator generator(Database database, Generate options, Target target) {
        return new Generator()
                .withDatabase(database)
                .withGenerate(options)
                .withTarget(target);
    }

    @Bean
    public Configuration configuration(Jdbc jdbc, Generator generator) {
        return new Configuration()
                .withJdbc(jdbc)
                .withGenerator(generator);
    }
}
