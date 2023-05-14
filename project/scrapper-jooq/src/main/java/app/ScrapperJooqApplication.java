package app;

import app.codegen.JooqCodegen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(ApplicationConfig.class)
public class ScrapperJooqApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(ScrapperJooqApplication.class, args);
//        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);
//        log.info(config.toString());
        JooqCodegen jooqCodegen = ctx.getBean(JooqCodegen.class);
        try {
            jooqCodegen.generateClasses();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
