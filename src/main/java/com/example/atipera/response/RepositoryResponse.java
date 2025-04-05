package com.example.atipera.response;

import java.util.List;

public class RepositoryResponse {
    private String repositoryName;
    private String ownerName;
    private List<BranchResponse> branches;

    public RepositoryResponse(String repositoryName, String ownerName, List<BranchResponse> branches) {
        this.repositoryName = repositoryName;
        this.ownerName = ownerName;
        this.branches = branches;
    }

    public RepositoryResponse() {
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public List<BranchResponse> getBranches() {
        return branches;
    }

    public void setBranches(List<BranchResponse> branches) {
        this.branches = branches;
    }
}