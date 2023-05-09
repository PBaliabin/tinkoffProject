package ru.tinkoff.edu.java.scrapper.jooq.util.converter;

import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.StackoverflowLinkRecord;
import ru.tinkoff.edu.java.scrapper.dto.response.StackoverflowResponse;

import java.sql.Timestamp;

@Component
public class JooqStackoverflowLinkConverter {
    public StackoverflowLinkRecord makeStackoverflowLinkRecord(
            String link,
            StackoverflowResponse stackoverflowResponse) {
        return new StackoverflowLinkRecord(
                link,
                stackoverflowResponse.getQuotaMax(),
                stackoverflowResponse.getQuotaRemaining(),
                new Timestamp(Long.parseLong(stackoverflowResponse.getLastActivityDate())).toLocalDateTime(),
                stackoverflowResponse.getIsAnswered(),
                stackoverflowResponse.getAnswerCount(),
                new Timestamp(System.currentTimeMillis()).toLocalDateTime()
        );
    }

    public StackoverflowLink makeStackoverflowLink(
            StackoverflowLinkRecord stackoverflowLinkRecord) {
        return new StackoverflowLink(
                stackoverflowLinkRecord.getLink(),
                stackoverflowLinkRecord.getQuotaMax(),
                stackoverflowLinkRecord.getQuotaRemaining(),
                stackoverflowLinkRecord.getLastActivityTime(),
                stackoverflowLinkRecord.getIsAnswered(),
                stackoverflowLinkRecord.getAnswerCount(),
                stackoverflowLinkRecord.getLastCheckTime()
        );
    }

    public ru.tinkoff.edu.java.scrapper.dto.entity.StackoverflowLink convertJooqStackoverflowLinkToCustom(
            StackoverflowLink stackoverflowLink) {
        return new ru.tinkoff.edu.java.scrapper.dto.entity.StackoverflowLink(
                stackoverflowLink.getLink(),
                stackoverflowLink.getQuotaMax(),
                stackoverflowLink.getQuotaRemaining(),
                stackoverflowLink.getLastActivityTime(),
                stackoverflowLink.getIsAnswered(),
                stackoverflowLink.getAnswerCount(),
                stackoverflowLink.getLastCheckTime()
        );
    }
}
