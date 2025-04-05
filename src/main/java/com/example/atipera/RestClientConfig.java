package com.example.atipera;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    private static final String GITHUB_API_URL = "https://api.github.com";
    private static final String ACCEPT_HEADER = "application/vnd.github.v3+json";
    @Value("${github.token}")
    private String githubToken;


    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(GITHUB_API_URL)
                .defaultHeader(HttpHeaders.ACCEPT, ACCEPT_HEADER)
                .defaultHeader(HttpHeaders.USER_AGENT, "GithubAPIClient")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + githubToken)
                .build();
    }
}
