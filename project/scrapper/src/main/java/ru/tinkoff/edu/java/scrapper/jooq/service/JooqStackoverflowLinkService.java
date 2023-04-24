package ru.tinkoff.edu.java.scrapper.jooq.service;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.linkParser.Parser;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClientService;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.StackoverflowLinkRecord;
import ru.tinkoff.edu.java.scrapper.dto.response.StackoverflowResponse;
import ru.tinkoff.edu.java.scrapper.inteface.service.StackoverflowLinkService;
import ru.tinkoff.edu.java.scrapper.jooq.util.JooqTypeConverter;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JooqStackoverflowLinkService implements StackoverflowLinkService<StackoverflowLinkRecord> {

    private final DSLContext dslContext;
    private final StackOverflowClientService stackOverflowClientService;
    private final JooqTypeConverter jooqTypeConverter;
    private final ru.tinkoff.edu.java.scrapper.domain.jooq.tables.StackoverflowLink stackoverflowLinkTable;

    @Override
    public int add(URI url) {
        Map<String, String> parsedLink = Parser.parseLink(url.toString());
        StackoverflowResponse stackoverflowResponse = stackOverflowClientService.getQuestion(
                parsedLink.get(StackoverflowLinkService.QUESTION_ID),
                StackoverflowLinkService.SITE_STACKOVERFLOW
        );
        StackoverflowLinkRecord stackoverflowLinkRecord = jooqTypeConverter.makeStackoverflowLinkRecord(url.toString(), stackoverflowResponse);
        return dslContext.insertInto(stackoverflowLinkTable).values(stackoverflowLinkRecord).execute();
    }

    @Override
    public int remove(URI uri) {
        return dslContext
                .deleteFrom(stackoverflowLinkTable)
                .where(stackoverflowLinkTable.LINK.eq(uri.toString()))
                .execute();
    }

    @Override
    public int update(StackoverflowLinkRecord stackoverflowLinkRecord) {
        return dslContext.update(stackoverflowLinkTable).set(stackoverflowLinkRecord).execute();
    }

    @Override
    public Collection<StackoverflowLinkRecord> getLinksByLastCheckTime(LocalDateTime threshold) {
        return dslContext
                .selectFrom(stackoverflowLinkTable)
                .where(stackoverflowLinkTable.LAST_CHECK_TIME.lessOrEqual(threshold))
                .fetch();
    }
}
