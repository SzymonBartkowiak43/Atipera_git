package com.example.atipera;


import com.example.atipera.exception.UserNotFoundException;
import com.example.atipera.models.Branch;
import com.example.atipera.models.Repository;
import com.example.atipera.response.BranchResponse;
import com.example.atipera.response.RepositoryResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class GithubService {

    private final RestClient restClient;

    public GithubService(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<RepositoryResponse> getNonForkRepositories(String username) {
        return getRepositories(username).stream()
                .filter(repo -> !repo.isFork())
                .map(repo -> new RepositoryResponse(
                        repo.getName(),
                        repo.getOwner().getLogin(),
                        getBranches(username, repo.getName()).stream()
                                .map(branch -> new BranchResponse(
                                        branch.getName(),
                                        branch.getCommit().getSha()))
                                .toList()))
                .toList();
    }

    private List<Repository> getRepositories(String username) {
        return restClient.get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        (request, response) -> {
                            throw new UserNotFoundException();
                        }
                )
                .body(new ParameterizedTypeReference<>() {
                });
    }

    private List<Branch> getBranches(String username, String repositoryName) {
        return restClient.get()
                .uri("/repos/{username}/{repo}/branches", username, repositoryName)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

}