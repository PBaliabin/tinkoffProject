package ru.tinkoff.edu.java.scrapper.jdbc.util.converter;

import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.dto.entity.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.dto.response.StackoverflowResponse;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Component
public class JdbcStackoverflowLinkConverter {
    public StackoverflowLink makeStackoverflowLink(String link, StackoverflowResponse stackoverflowResponse) {
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

    public Map<String, Object> makeStackoverflowTableQueryMap(StackoverflowLink stackoverflowLink) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("link", stackoverflowLink.getLink());
        queryMap.put("quotaMax", stackoverflowLink.getQuotaMax());
        queryMap.put("quotaRemaining", stackoverflowLink.getQuotaRemaining());
        queryMap.put("lastActivityTime", stackoverflowLink.getLastActivityTime());
        queryMap.put("isAnswered", stackoverflowLink.getIsAnswered());
        queryMap.put("answerCount", stackoverflowLink.getAnswerCount());
        queryMap.put("lastCheckTime", stackoverflowLink.getLastCheckTime());
        return queryMap;
    }
}
