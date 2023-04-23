package ru.tinkoff.edu.java.scrapper.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.GithubLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.GithubLinkRecord;
import ru.tinkoff.edu.java.scrapper.inteface.GithubLinkService;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class JooqGitHubLinkService implements GithubLinkService {

    private final DSLContext dslContext;
    private final ru.tinkoff.edu.java.scrapper.domain.jooq.tables.GithubLink githubLinkTable;

    @Override
    public int add(GithubLink githubLink) {
        return dslContext.insertInto(githubLinkTable).values(githubLink).execute();
    }

    @Override
    public int remove(URI uri) {
        return dslContext
                .deleteFrom(githubLinkTable)
                .where(githubLinkTable.LINK.eq(uri.toString()))
                .execute();
    }

    @Override
    public int update(GithubLink githubLink) {
        return dslContext.update(githubLinkTable).set(new GithubLinkRecord(githubLink)).execute();
    }

    @Override
    public Collection<GithubLinkRecord> getLinksByLastCheckTime(LocalDateTime threshold) {
        return dslContext
                .selectFrom(githubLinkTable)
                .where(githubLinkTable.LAST_CHECK_TIME.lessOrEqual(threshold))
                .fetch();
    }


}
