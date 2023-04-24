package ru.tinkoff.edu.java.scrapper.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.StackoverflowLinkRecord;
import ru.tinkoff.edu.java.scrapper.inteface.StackoverflowLinkService;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class JooqStackoverflowLinkService implements StackoverflowLinkService {

    private final DSLContext dslContext;
    private final ru.tinkoff.edu.java.scrapper.domain.jooq.tables.StackoverflowLink stackoverflowLinkTable;

    @Override
    public int add(StackoverflowLink stackoverflowLink) {
        return dslContext.insertInto(stackoverflowLinkTable).values(stackoverflowLink).execute();
    }

    @Override
    public int remove(URI uri) {
        return dslContext
                .deleteFrom(stackoverflowLinkTable)
                .where(stackoverflowLinkTable.LINK.eq(uri.toString()))
                .execute();
    }

    @Override
    public int update(StackoverflowLink stackoverflowLink) {
        return dslContext.update(stackoverflowLinkTable).set(new StackoverflowLinkRecord(stackoverflowLink)).execute();
    }

    @Override
    public Collection<StackoverflowLinkRecord> getLinksByLastCheckTime(LocalDateTime threshold) {
        return dslContext
                .selectFrom(stackoverflowLinkTable)
                .where(stackoverflowLinkTable.LAST_CHECK_TIME.lessOrEqual(threshold))
                .fetch();
    }


}
