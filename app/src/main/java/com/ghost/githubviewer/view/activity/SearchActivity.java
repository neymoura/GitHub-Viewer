package com.ghost.githubviewer.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ghost.githubviewer.R;
import com.ghost.githubviewer.event.GitHubServiceEvent;
import com.ghost.githubviewer.model.OwnerRepositories;
import com.ghost.githubviewer.service.GitHubService;

import org.greenrobot.eventbus.Subscribe;

public class SearchActivity extends BaseActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    private static final String TAG = "SearchAct";

    private ProgressBar progress;
    private EditText profileInput;
    private Button searchButton;

    private GitHubService gitHubService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_search);

        progress = (ProgressBar) findViewById(R.id.progress);

        profileInput = (EditText) findViewById(R.id.profile_input);
        profileInput.setOnEditorActionListener(this);

        searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(this);

        gitHubService = new GitHubService();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.search_button:
                searchProfile();
                break;

            default:
                Log.w(TAG, getString(R.string.unknow_action_warn) + view.getId());
            
        }

    }

    private void searchProfile(){

        String username = profileInput.getText().toString().trim();

        if (!username.isEmpty()) {
            showProgress();
            gitHubService.requestProfile(username);
            searchButton.setEnabled(false);
        }

    }

    @Subscribe
    public void onEvent(GitHubServiceEvent event){

        searchButton.setEnabled(true);
        hideProgress();

        switch (event.getType()) {

            case REQUEST_SUCCESS:

                OwnerRepositories ownerRepositories = (OwnerRepositories) event.getData();

                Intent profileActivity = new Intent(this, ProfileActivity.class);
                profileActivity.putExtra(ProfileActivity.OWNER_REPO_EXTRA, ownerRepositories);
                startActivity(profileActivity);

                break;

            case REQUEST_FAILURE:

                showAlert((Integer) event.getData());

                break;

            default:
                Log.w(TAG, getString(R.string.unknow_event_warn));

        }

    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

        switch (actionId) {

            case EditorInfo.IME_ACTION_SEND:
                searchProfile();
                return true;

        }

        return false;

    }

    private void showProgress(){
        progress.setVisibility(View.VISIBLE);
    }

    private void hideProgress(){
        progress.setVisibility(View.GONE);
    }

}
