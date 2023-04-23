package ru.tinkoff.edu.java.bot.service.wrapper;

import ru.tinkoff.edu.java.bot.model.dto.StackoverflowLink;

import java.util.List;

public class StackoverflowLinkWrapper {
    public static String makeMessage(List<StackoverflowLink> outdatedStackoverflowLinks, List<StackoverflowLink> updatedStackoverflowLinks) {
        StringBuilder stringBuilder = new StringBuilder("\tfor Stackoverflow links:\n");
        for (int i = 0; i < outdatedStackoverflowLinks.size(); i++) {
            stringBuilder.append("\t\tlink ").append(outdatedStackoverflowLinks.get(i).getLink()).append(" has changes\n");
            stringBuilder
                    .append("\t\t\tforks count: ")
                    .append(outdatedStackoverflowLinks.get(i).getIsAnswered())
                    .append(" -> ")
                    .append(updatedStackoverflowLinks.get(i).getIsAnswered());
            stringBuilder
                    .append("\t\t\topen issues count: ")
                    .append(outdatedStackoverflowLinks.get(i).getAnswerCount())
                    .append(" -> ")
                    .append(updatedStackoverflowLinks.get(i).getAnswerCount());
        }
        return stringBuilder.toString();
    }
}
