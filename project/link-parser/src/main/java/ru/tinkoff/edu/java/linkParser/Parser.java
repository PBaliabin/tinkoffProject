package ru.tinkoff.edu.java.linkParser;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public final class Parser {

    private Parser() {}

    public static Map<String, String> parseLink(String link) {

        List<LinkParser> linkParsers = List.of(new GitHubParser(), new StackOverflowParser());
        String[] linkSplit = link.split("/");

        return linkParsers
                .stream()
                .map(linkParser -> linkParser.parseLink(linkSplit))
                .filter(Predicate.not(Map::isEmpty))
                .findAny()
                .orElse(null);
    }
}
