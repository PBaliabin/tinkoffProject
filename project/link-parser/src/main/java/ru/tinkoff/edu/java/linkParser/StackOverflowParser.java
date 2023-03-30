package ru.tinkoff.edu.java.linkParser;

import java.util.Optional;

public class StackOverflowParser implements LinkParser {
    private static final String DOMAIN = "stackoverflow.com";
    private static final String CATEGORY = "questions";

    private static final int DOMAIN_PLACE_NUM = 2;
    private static final int CATEGORY_PLACE_NUM = 3;
    private static final int QUESTION_ID_PLACE_NUM = 4;
    @Override
    public Optional<String> parseLink (String[] link){

        if (!link[DOMAIN_PLACE_NUM].equals(DOMAIN) || !link[CATEGORY_PLACE_NUM].equals(CATEGORY)) {
            return Optional.empty();
        }

        return link[QUESTION_ID_PLACE_NUM].describeConstable();
    }
}
