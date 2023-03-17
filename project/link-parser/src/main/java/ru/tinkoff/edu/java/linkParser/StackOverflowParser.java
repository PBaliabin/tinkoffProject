package ru.tinkoff.edu.java.linkParser;

import java.util.Optional;

public class StackOverflowParser implements LinkParser {

    @Override
    public Optional<String> parseLink (String[] link){

        if (!link[2].equals("stackoverflow.com") || !link[3].equals("questions")) {
            return Optional.empty();
        }

        return link[4].describeConstable();
    }
}
