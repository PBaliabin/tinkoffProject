package ru.tinkoff.edu.java.linkParser;

import java.util.Map;
import java.util.Optional;

public interface LinkParser {
    Map<String, String> parseLink(String[] link);
}
