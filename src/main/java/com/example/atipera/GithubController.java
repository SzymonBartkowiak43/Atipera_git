package com.example.atipera;



import com.example.atipera.response.RepositoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public class GithubController {

    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/{username}/repos")
    public ResponseEntity<List<RepositoryResponse>> getUserRepositories(@PathVariable String username) {
            List<RepositoryResponse> repositories = githubService.getNonForkRepositories(username);
            return ResponseEntity.ok(repositories);
    }
}

