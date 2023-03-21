package ru.tinkoff.edu.java.linkParser;

import java.util.Optional;

public class GitHubParser implements LinkParser {
    private static final String DOMAIN = "github.com";

    private static final int DOMAIN_PLACE_NUM = 2;
    private static final int CATEGORY_PLACE_NUM = 3;
    private static final int QUESTION_ID_PLACE_NUM = 4;
    @Override
    public Optional<String> parseLink (String[] link){

        if (!link[DOMAIN_PLACE_NUM].equals(DOMAIN)) {
            return Optional.empty();
        }

        return (link[CATEGORY_PLACE_NUM] + " : " + link[QUESTION_ID_PLACE_NUM]).describeConstable();
    }
}
