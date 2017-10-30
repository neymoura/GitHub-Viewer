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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity implements TextView.OnEditorActionListener {

    private static final String TAG = "SearchAct";

    @BindView(R.id.progress)
    protected ProgressBar progress;

    @BindView(R.id.profile_input)
    protected EditText profileInput;

    @BindView(R.id.search_button)
    protected Button searchButton;

    private GitHubService gitHubService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        profileInput.setOnEditorActionListener(this);

        gitHubService = new GitHubService();

    }

    @OnClick(R.id.search_button)
    protected void searchProfile(){

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
