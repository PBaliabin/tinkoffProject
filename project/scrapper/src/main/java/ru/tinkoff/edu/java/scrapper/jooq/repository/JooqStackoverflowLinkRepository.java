package ru.tinkoff.edu.java.scrapper.jooq.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.tinkoff.edu.java.linkParser.Parser;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClientService;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.StackoverflowLinkRecord;
import ru.tinkoff.edu.java.scrapper.dto.entity.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.dto.response.StackoverflowResponse;
import ru.tinkoff.edu.java.scrapper.inteface.service.StackoverflowLinkService;
import ru.tinkoff.edu.java.scrapper.jdbc.mapper.StackoverflowLinkRowMapper;
import ru.tinkoff.edu.java.scrapper.jdbc.util.JdbcTypeConverter;
import ru.tinkoff.edu.java.scrapper.jooq.util.JooqTypeConverter;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JooqStackoverflowLinkRepository {
    private final DSLContext dslContext;
    private final ru.tinkoff.edu.java.scrapper.domain.jooq.tables.StackoverflowLink stackoverflowLinkTable;

    public int add(StackoverflowLinkRecord stackoverflowLinkRecord) {
        return dslContext
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

    public int remove(URI uri) {
        return dslContext
                .deleteFrom(stackoverflowLinkTable)
                .where(stackoverflowLinkTable.LINK.eq(uri.toString()))
                .execute();
    }

    public int update(StackoverflowLinkRecord stackoverflowLinkRecord) {
        return dslContext.update(stackoverflowLinkTable).set(stackoverflowLinkRecord).execute();
    }

    public Collection<StackoverflowLinkRecord> getLinksByLastCheckTime(LocalDateTime threshold) {
        return dslContext
                .selectFrom(stackoverflowLinkTable)
                .where(stackoverflowLinkTable.LAST_CHECK_TIME.lessOrEqual(threshold))
                .fetch();
    }
}
