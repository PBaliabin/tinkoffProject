package ru.tinkoff.edu.java.scrapper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.GithubLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.StackoverflowLink;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LinkChangeLog {
    private String tgChatId;
    private List<GithubLink> outdatedGithubLinks;
    private List<GithubLink> updatedGithubLinks;
    private List<StackoverflowLink> outdatedStackoverflowLinks;
    private List<StackoverflowLink> updatedStackoverflowLinks;
}
