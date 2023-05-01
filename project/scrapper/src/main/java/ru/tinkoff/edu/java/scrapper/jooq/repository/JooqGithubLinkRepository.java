package ru.tinkoff.edu.java.scrapper.jooq.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.tinkoff.edu.java.linkParser.Parser;
import ru.tinkoff.edu.java.scrapper.client.GitHubClientService;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.GithubLinkRecord;
import ru.tinkoff.edu.java.scrapper.dto.entity.GithubLink;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.inteface.service.GithubLinkService;
import ru.tinkoff.edu.java.scrapper.jdbc.mapper.GithubLinkRowMapper;
import ru.tinkoff.edu.java.scrapper.jdbc.util.JdbcTypeConverter;
import ru.tinkoff.edu.java.scrapper.jooq.util.JooqTypeConverter;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JooqGithubLinkRepository {
    private final DSLContext dslContext;
    private final ru.tinkoff.edu.java.scrapper.domain.jooq.tables.GithubLink githubLinkTable;

    public int add(GithubLinkRecord githubLinkRecord) {
        return dslContext
                .insertInto(githubLinkTable)
                .values(
                        githubLinkRecord.value1(),
                        githubLinkRecord.value2(),
                        githubLinkRecord.value3(),
                        githubLinkRecord.value4(),
                        githubLinkRecord.value5(),
                        githubLinkRecord.value6(),
                        githubLinkRecord.value7(),
                        githubLinkRecord.value8())
                .execute();
    }

    public int remove(URI uri) {
        return dslContext
                .deleteFrom(githubLinkTable)
                .where(githubLinkTable.LINK.eq(uri.toString()))
                .execute();
    }

    public int update(GithubLinkRecord githubLinkRecord) {
        return dslContext.update(githubLinkTable).set(githubLinkRecord).execute();
    }

    public Collection<GithubLinkRecord> getLinksByLastCheckTime(LocalDateTime threshold) {
        return dslContext
                .selectFrom(githubLinkTable)
                .where(githubLinkTable.LAST_CHECK_TIME.lessOrEqual(threshold))
                .fetch();
    }
}
