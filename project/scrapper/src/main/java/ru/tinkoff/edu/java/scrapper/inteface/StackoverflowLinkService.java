package ru.tinkoff.edu.java.scrapper.inteface;


import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.StackoverflowLinkRecord;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;

public interface StackoverflowLinkService {
    int add(StackoverflowLink stackoverflowLink);

    int remove(URI uri);

    int update(StackoverflowLink stackoverflowLink);

    Collection<StackoverflowLinkRecord> getLinksByLastCheckTime(LocalDateTime threshold);
}
