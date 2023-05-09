package codegen;

import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.Configuration;
import org.jooq.meta.jaxb.Database;
import org.jooq.meta.jaxb.Generate;
import org.jooq.meta.jaxb.Generator;
import org.jooq.meta.jaxb.Jdbc;
import org.jooq.meta.jaxb.Property;
import org.jooq.meta.jaxb.Target;

public final class JooqCodegen {

    private JooqCodegen() {
    }

    public static void main(String[] args) throws Exception {

        Database database = new Database()
                .withName("org.jooq.meta.postgres.PostgresDatabase")
                .withInputSchema("public")
                .withProperties(
                        new Property().withKey("rootPath")
                                      .withValue("scrapper/src/main/resources"),
                        new Property().withKey("scripts").withValue("master.xml"),
                        new Property().withKey("includeLiquibaseTables").withValue("false"));

        Generate options = new Generate().withGeneratedAnnotation(true)
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

        Target target = new Target().withPackageName("ru.tinkoff.edu.java.scrapper.domain.jooq")
                                    .withDirectory("scrapper/src/main/java");

        Configuration configuration = new Configuration()
                .withJdbc(
                        new Jdbc()
                                .withDriver("org.postgresql.Driver")
                                .withUrl("jdbc:postgresql://localhost:5432/scrapper")
                                .withUsername("postgres")
                                .withPassword("postgres"))
                .withGenerator(
                        new Generator()
                                .withDatabase(database)
                                .withGenerate(options)
                                .withTarget(target));

        GenerationTool.generate(configuration);
    }
}