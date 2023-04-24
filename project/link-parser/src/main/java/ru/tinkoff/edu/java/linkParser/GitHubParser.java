package ru.tinkoff.edu.java.linkParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GitHubParser implements LinkParser {
    private static final String DOMAIN = "github.com";

    private static final int DOMAIN_PLACE_NUM = 2;
    private static final int OWNER_PLACE_NUM = 3;
    private static final int REPOSITORY_ID_PLACE_NUM = 4;

    @Override
    public Map<String, String> parseLink(String[] link) {

        if (!link[DOMAIN_PLACE_NUM].equals(DOMAIN)) {
            return new HashMap<>();
        }

        return Map.of(
                "domain", DOMAIN,
                "owner", link[OWNER_PLACE_NUM],
                "repository", link[REPOSITORY_ID_PLACE_NUM]);
    }
}
