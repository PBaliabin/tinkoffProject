package ru.tinkoff.edu.java.scrapper.jooq.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.StackoverflowLinkRecord;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;

@RequiredArgsConstructor
public class JooqStackoverflowLinkRepository {
    private final DSLContext dslContext;
    private final ru.tinkoff.edu.java.scrapper.domain.jooq.tables.StackoverflowLink stackoverflowLinkTable;

    public void add(StackoverflowLinkRecord stackoverflowLinkRecord) {
        dslContext
                .insertInto(stackoverflowLinkTable)
                .values(
                        stackoverflowLinkRecord.value1(),
                        stackoverflowLinkRecord.value2(),
                        stackoverflowLinkRecord.value3(),
                        stackoverflowLinkRecord.value4(),
                        stackoverflowLinkRecord.value5(),
                        stackoverflowLinkRecord.value6(),
                        stackoverflowLinkRecord.value7())
                .execute();
    }

    public void remove(URI uri) {
        dslContext
                .deleteFrom(stackoverflowLinkTable)
                .where(stackoverflowLinkTable.LINK.eq(uri.toString()))
                .execute();
    }

    public void update(StackoverflowLinkRecord stackoverflowLinkRecord) {
        dslContext.update(stackoverflowLinkTable).set(stackoverflowLinkRecord).execute();
    }

    public Collection<StackoverflowLinkRecord> getLinksByLastCheckTime(LocalDateTime threshold) {
        return dslContext
                .selectFrom(stackoverflowLinkTable)
                .where(stackoverflowLinkTable.LAST_CHECK_TIME.lessOrEqual(threshold))
                .fetch();
    }
}
