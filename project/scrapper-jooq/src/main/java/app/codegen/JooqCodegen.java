package app.codegen;

import lombok.AllArgsConstructor;
import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.Configuration;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JooqCodegen {

    private final Configuration configuration;

    public void generateClasses() throws Exception {
        GenerationTool.generate(configuration);
    }
}
