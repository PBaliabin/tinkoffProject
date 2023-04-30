package ru.tinkoff.edu.java.scrapper.configuration;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chat;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.ChatToLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.GithubLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.StackoverflowLink;

import java.sql.SQLException;

@Configuration
public class DataSourceConfiguration {
    @Bean
    public DSLContext dslContext(DataSourceProperties dataSource) throws SQLException {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(dataSource.getDriverClassName());
        driverManagerDataSource.setUrl(dataSource.getUrl());
        driverManagerDataSource.setUsername(dataSource.getUsername());
        driverManagerDataSource.setPassword(dataSource.getPassword());

        return DSL.using(driverManagerDataSource.getConnection(), SQLDialect.POSTGRES, postgresJooqCustomizer());
    }

    @Bean
    public Chat chatTable() {
        return Chat.CHAT;
    }

    @Bean
    public ChatToLink chatToLinkTable() {
        return ChatToLink.CHAT_TO_LINK;
    }

    @Bean
    public GithubLink githubLinkTable() {
        return GithubLink.GITHUB_LINK;
    }

    @Bean
    public StackoverflowLink stackoverflowLinkTable() {
        return StackoverflowLink.STACKOVERFLOW_LINK;
    }

    @Bean
    public Settings postgresJooqCustomizer() {
        return new Settings()
                .withRenderSchema(false)
                .withRenderFormatted(true)
                .withRenderQuotedNames(RenderQuotedNames.NEVER);
    }
}
