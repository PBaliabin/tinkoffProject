package ru.tinkoff.edu.java.scrapper.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import ru.tinkoff.edu.java.scrapper.jpa.entity.StackoverflowLink;

import java.time.LocalDateTime;
import java.util.Collection;

public interface JpaStackoverflowLinkRepository extends CrudRepository<StackoverflowLink, String> {
    Collection<StackoverflowLink> getStackoverflowLinksByLastCheckTimeBefore(LocalDateTime threshold);
}
