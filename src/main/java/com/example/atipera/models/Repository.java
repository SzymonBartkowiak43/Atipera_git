package com.example.atipera.models;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Repository {
    private String name;
    private Owner owner;
    @JsonProperty("fork")
    private boolean fork;

    public Repository() {
    }

    public Repository(String name, Owner owner, boolean fork) {
        this.name = name;
        this.owner = owner;
        this.fork = fork;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public boolean isFork() {
        return fork;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }
}