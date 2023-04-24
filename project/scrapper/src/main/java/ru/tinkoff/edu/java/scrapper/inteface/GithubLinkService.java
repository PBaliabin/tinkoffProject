package ru.tinkoff.edu.java.scrapper.inteface;


import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.GithubLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.GithubLinkRecord;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;

public interface GithubLinkService {
    int add(GithubLink githubLink);

    int remove(URI uri);

    int update(GithubLink githubLink);

    Collection<GithubLinkRecord> getLinksByLastCheckTime(LocalDateTime threshold);
}
