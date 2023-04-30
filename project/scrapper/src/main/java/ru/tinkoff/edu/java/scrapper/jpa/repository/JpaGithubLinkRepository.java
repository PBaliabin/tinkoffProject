package ru.tinkoff.edu.java.scrapper.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import ru.tinkoff.edu.java.scrapper.jpa.entity.GithubLink;

import java.time.LocalDateTime;
import java.util.Collection;

public interface JpaGithubLinkRepository extends CrudRepository<GithubLink, String> {
    Collection<GithubLink> getGithubLinksByLastCheckTimeBefore(LocalDateTime threshold);
}
