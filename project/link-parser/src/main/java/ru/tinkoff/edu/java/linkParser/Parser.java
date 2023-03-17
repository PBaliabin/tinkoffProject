package ru.tinkoff.edu.java.linkParser;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class Parser {
    public static void main(String[] args) {
        System.out.println(new Parser().parseLink("https://github.com/sanyarnd/tinkoff-java-course-2022/\n"));
        System.out.println(new Parser().parseLink("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c\n"));
        System.out.println(new Parser().parseLink("https://stackoverflow.com/search?q=unsupported%20link\n"));
    }

    /**
     * Не знал куда имплементировать класс, чтобы показать, что он работает
     *
     * @param link
     * @return
     */
    public String parseLink (String link){

        List<LinkParser> linkParsers = List.of(new GitHubParser(), new StackOverflowParser());
        String[] linkSplit = link.split("/");

        return linkParsers
                .stream()
                .map(linkParser -> linkParser.parseLink(linkSplit))
                .filter(Optional::isPresent)
                .findAny()
                .flatMap(Function.identity())
                .orElse(null);
    }
}
