package ru.tinkoff.edu.java.bot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LinkUpdate {
    private Long tgChatId;
    private List<GithubLink> outdatedGithubLinks;
    private List<GithubLink> updatedGithubLinks;
    private List<StackoverflowLink> outdatedStackoverflowLinks;
    private List<StackoverflowLink> updatedStackoverflowLinks;
}
