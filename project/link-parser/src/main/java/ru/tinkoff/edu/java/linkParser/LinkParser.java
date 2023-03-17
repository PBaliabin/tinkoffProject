package ru.tinkoff.edu.java.linkParser;

import java.util.Optional;

public interface LinkParser {
    Optional<String> parseLink(String[] link);
}
