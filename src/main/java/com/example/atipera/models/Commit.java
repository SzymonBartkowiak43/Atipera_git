package com.example.atipera.models;

public class Commit {
    private String sha;

    public Commit() {
    }

    public Commit(String sha) {
        this.sha = sha;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }
}