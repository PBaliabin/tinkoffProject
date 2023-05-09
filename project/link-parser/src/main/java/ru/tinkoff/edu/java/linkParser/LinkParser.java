package ru.tinkoff.edu.java.linkParser;

import java.util.Map;

public interface LinkParser {
    Map<String, String> parseLink(String[] link);
}
