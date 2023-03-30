package ru.tinkoff.edu.java.scrapper.dto;

import lombok.Data;

@Data
public class GitHubResponse {
    private long id;
    private String name;

    private String full_name;
    private String html_url;
    private String created_at;

}
