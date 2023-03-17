package ru.tinkoff.edu.java.linkParser;

import java.util.Optional;

public class GitHubParser implements LinkParser {

    @Override
    public Optional<String> parseLink (String[] link){

        if (!link[2].equals("github.com")) {
            return Optional.empty();
        }

        return (link[3] + " : " + link[4]).describeConstable();
    }
}
