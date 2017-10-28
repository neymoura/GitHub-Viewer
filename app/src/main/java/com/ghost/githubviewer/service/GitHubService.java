package com.ghost.githubviewer.service;

import com.ghost.githubviewer.R;
import com.ghost.githubviewer.event.GitHubServiceEvent;
import com.ghost.githubviewer.model.Owner;
import com.ghost.githubviewer.model.OwnerRepositories;
import com.ghost.githubviewer.model.Repository;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class GitHubService {

    private GitHubAPI api;

    public GitHubService(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitHubAPI.BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        api = retrofit.create(GitHubAPI.class);

    }

    private static void postEvent(GitHubServiceEvent.Type type, Object data) {
        EventBus.getDefault().post(new GitHubServiceEvent(type, data));
    }

    public void requestProfile(final String username){

        api.getOwner(username).enqueue(new Callback<Owner>() {

            @Override
            public void onResponse(Call<Owner> call, Response<Owner> response) {

                if (response.isSuccessful()){
                    requestRepositories(response.body());
                }else if (response.code() == 404){
                    postEvent(GitHubServiceEvent.Type.REQUEST_FAILURE, R.string.api_user_not_found);
                }else{
                    postEvent(GitHubServiceEvent.Type.REQUEST_FAILURE, R.string.api_error);
                }

            }

            @Override
            public void onFailure(Call<Owner> call, Throwable t) {
                postEvent(GitHubServiceEvent.Type.REQUEST_FAILURE, R.string.api_error);
            }

        });

    }

    private void requestRepositories(final Owner owner){

        api.getRepositories(owner.getLogin()).enqueue(new Callback<List<Repository>>() {

            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {

                if (response.isSuccessful()){

                    OwnerRepositories ownerRepositories = new OwnerRepositories(owner, response.body());
                    postEvent(GitHubServiceEvent.Type.REQUEST_SUCCESS, ownerRepositories);

                }else{
                    postEvent(GitHubServiceEvent.Type.REQUEST_FAILURE, R.string.api_error);
                }

            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                postEvent(GitHubServiceEvent.Type.REQUEST_FAILURE, R.string.api_error);
            }

        });

    }

}
