package com.ghost.githubviewer.model;

import java.io.Serializable;
import java.util.List;

public class OwnerRepositories implements Serializable{

    private Owner owner;
    private List<Repository> repositories;

    public OwnerRepositories(Owner owner, List<Repository> repositories) {
        this.owner = owner;
        this.repositories = repositories;
    }

    public Owner getOwner() {
        return owner;
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

}
