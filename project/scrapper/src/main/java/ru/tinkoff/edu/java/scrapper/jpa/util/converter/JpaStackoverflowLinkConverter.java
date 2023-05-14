package ru.tinkoff.edu.java.scrapper.jpa.util.converter;

import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.dto.response.StackoverflowResponse;
import ru.tinkoff.edu.java.scrapper.jpa.entity.StackoverflowLink;

import java.sql.Timestamp;

@Component
public class JpaStackoverflowLinkConverter {
    public StackoverflowLink makeStackoverflowLink(
            String link,
            StackoverflowResponse stackoverflowResponse) {
        return new StackoverflowLink(
                link,
                stackoverflowResponse.getQuotaMax(),
                stackoverflowResponse.getQuotaRemaining(),
                new Timestamp(Long.parseLong(stackoverflowResponse.getLastActivityDate())).toLocalDateTime(),
                stackoverflowResponse.getIsAnswered(),
                stackoverflowResponse.getAnswerCount(),
                new Timestamp(System.currentTimeMillis()).toLocalDateTime()
        );
    }

    public ru.tinkoff.edu.java.scrapper.dto.entity.StackoverflowLink convertJpaStackoverflowLinkToCustom(
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
