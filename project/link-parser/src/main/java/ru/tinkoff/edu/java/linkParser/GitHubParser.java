package ru.tinkoff.edu.java.linkParser;

import java.util.Optional;

public class GitHubParser implements LinkParser {
    private static final String domain = "github.com";

    private static final int domainPlaceNum = 2;
    private static final int categoryPlaceNum = 3;
    private static final int questionIdPlaceNum = 4;
    @Override
    public Optional<String> parseLink (String[] link){

        if (!link[domainPlaceNum].equals(domain)) {
            return Optional.empty();
        }

        return (link[categoryPlaceNum] + " : " + link[questionIdPlaceNum]).describeConstable();
    }
}
