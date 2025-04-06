package com.example.atipera.service;

import com.example.atipera.response.RepositoryResponse;

import java.util.List;

public interface GithubService {

    List<RepositoryResponse> getNonForkRepositories(String username);
}
