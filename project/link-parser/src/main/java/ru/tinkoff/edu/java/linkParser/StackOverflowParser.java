package ru.tinkoff.edu.java.linkParser;

import java.util.Optional;

public class StackOverflowParser implements LinkParser {
    private static final String domain = "stackoverflow.com";
    private static final String category = "questions";

    private static final int domainPlaceNum = 2;
    private static final int categoryPlaceNum = 3;
    private static final int questionIdPlaceNum = 4;
    @Override
    public Optional<String> parseLink (String[] link){

        if (!link[domainPlaceNum].equals(domain) || !link[categoryPlaceNum].equals(category)) {
            return Optional.empty();
        }

        return link[questionIdPlaceNum].describeConstable();
    }
}
