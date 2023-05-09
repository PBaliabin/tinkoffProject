package ru.tinkoff.edu.java.linkParser;

import java.util.HashMap;
import java.util.Map;

public class StackOverflowParser implements LinkParser {
    private static final String DOMAIN = "stackoverflow.com";
    private static final String CATEGORY = "questions";

    private static final int DOMAIN_PLACE_NUM = 2;
    private static final int CATEGORY_PLACE_NUM = 3;
    private static final int QUESTION_ID_PLACE_NUM = 4;

    @Override
    public Map<String, String> parseLink(String[] link) {

        if (!link[DOMAIN_PLACE_NUM].equals(DOMAIN) || !link[CATEGORY_PLACE_NUM].equals(CATEGORY)) {
            return new HashMap<>();
        }

        return Map.of(
                "domain", DOMAIN,
                "category", CATEGORY,
                "questionId", link[QUESTION_ID_PLACE_NUM]);
    }
}
