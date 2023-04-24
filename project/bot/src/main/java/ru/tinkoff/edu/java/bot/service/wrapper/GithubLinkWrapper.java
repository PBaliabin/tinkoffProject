package ru.tinkoff.edu.java.bot.service.wrapper;

import ru.tinkoff.edu.java.bot.model.dto.GithubLink;

import java.util.List;

public class GithubLinkWrapper {
    public static String makeMessage(List<GithubLink> outdatedGithubLinks, List<GithubLink> updatedGithubLinks) {
        StringBuilder stringBuilder = new StringBuilder("\tfor Github links:\n");
        for (int i = 0; i < outdatedGithubLinks.size(); i++) {
            stringBuilder.append("\t\tlink ").append(outdatedGithubLinks.get(i).getLink()).append(" has changes:\n");
            stringBuilder
                    .append("\t\t\tforks count: ")
                    .append(outdatedGithubLinks.get(i).getForksCount())
                    .append(" -> ")
                    .append(updatedGithubLinks.get(i).getForksCount());
            stringBuilder
                    .append("\t\t\topen issues count: ")
                    .append(outdatedGithubLinks.get(i).getOpenIssuesCount())
                    .append(" -> ")
                    .append(updatedGithubLinks.get(i).getOpenIssuesCount());
        }
        return stringBuilder.toString();
    }
}
