package ru.tinkoff.edu.java.scrapper.jooq.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.GithubLinkRecord;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;

@RequiredArgsConstructor
public class JooqGithubLinkRepository {
    private final DSLContext dslContext;
    private final ru.tinkoff.edu.java.scrapper.domain.jooq.tables.GithubLink githubLinkTable;

    public void add(GithubLinkRecord githubLinkRecord) {
        dslContext
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

    public void remove(URI uri) {
        dslContext
                .deleteFrom(githubLinkTable)
                .where(githubLinkTable.LINK.eq(uri.toString()))
                .execute();
    }

    public void update(GithubLinkRecord githubLinkRecord) {
        dslContext.update(githubLinkTable).set(githubLinkRecord).execute();
    }

    public Collection<GithubLinkRecord> getLinksByLastCheckTime(LocalDateTime threshold) {
        return dslContext
                .selectFrom(githubLinkTable)
                .where(githubLinkTable.LAST_CHECK_TIME.lessOrEqual(threshold))
                .fetch();
    }
}
