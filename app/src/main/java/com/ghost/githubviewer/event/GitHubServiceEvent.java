package com.ghost.githubviewer.event;

public class GitHubServiceEvent {

    public enum Type{
        REQUEST_SUCCESS,
        REQUEST_FAILURE,
    }

    private Type type;
    private Object data;

    public GitHubServiceEvent(Type type, Object data) {
        this.type = type;
        this.data = data;
    }

    public Type getType() {
        return type;
    }

    public Object getData() {
        return data;
    }

}
