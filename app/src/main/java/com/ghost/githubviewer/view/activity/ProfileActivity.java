package com.ghost.githubviewer.view.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghost.githubviewer.R;
import com.ghost.githubviewer.adapter.RepositoriesAdapter;
import com.ghost.githubviewer.model.OwnerRepositories;
import com.ghost.githubviewer.view.transformation.BorderedCircleTransform;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "PrflAct";

    public final static String OWNER_REPO_EXTRA = "OWNER_REPO_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        View homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(this);

        ImageView profilePicture = (ImageView) findViewById(R.id.profile_picture);
        TextView profileName = (TextView) findViewById(R.id.profile_name);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.repository_list);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        RepositoriesAdapter repositoriesAdapter = new RepositoriesAdapter();

        OwnerRepositories ownerRepositories = (OwnerRepositories) getIntent()
                .getSerializableExtra(OWNER_REPO_EXTRA);

        profileName.setText(ownerRepositories.getOwner().getName());

        Picasso.with(this)
                .load(ownerRepositories.getOwner().getAvatarUrl())
                .placeholder(R.drawable.ph_profile_picture_200dp)
                .transform(new BorderedCircleTransform())
                .into(profilePicture);

        recyclerView.setAdapter(repositoriesAdapter);
        repositoriesAdapter.setItens(ownerRepositories.getRepositories());

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.home_button:
                super.onBackPressed();
                break;

            default:
                Log.w(TAG, getString(R.string.unknow_event_warn));
        }

    }
}