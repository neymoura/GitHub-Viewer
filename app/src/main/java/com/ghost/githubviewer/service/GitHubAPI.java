package com.ghost.githubviewer.service;

import com.ghost.githubviewer.model.Owner;
import com.ghost.githubviewer.model.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface GitHubAPI {

    String BASE_URL = "https://api.github.com/";

    @GET("users/{user}")
    Call<Owner> getOwner(@Path("user") String user);

    @GET("users/{user}/repos")
    Call<List<Repository>> getRepositories(@Path("user") String user);

}