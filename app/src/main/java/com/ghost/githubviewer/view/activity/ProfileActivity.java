package com.ghost.githubviewer.view.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghost.githubviewer.R;
import com.ghost.githubviewer.adapter.RepositoriesAdapter;
import com.ghost.githubviewer.model.OwnerRepositories;
import com.ghost.githubviewer.view.custom.EnhancedRecyclerView;
import com.ghost.githubviewer.view.transformation.BorderedCircleTransform;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends BaseActivity{

    private static final String TAG = "PrflAct";

    public final static String OWNER_REPO_EXTRA = "OWNER_REPO_EXTRA";

    @BindView(R.id.profile_picture)
    protected ImageView profilePicture;

    @BindView(R.id.profile_name)
    protected TextView profileName;

    @BindView(R.id.repository_list)
    protected EnhancedRecyclerView recyclerView;

    @BindView(R.id.empty_view)
    protected View emptyView;

    private RepositoriesAdapter repositoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        recyclerView.setmEmptyView(emptyView);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);

        recyclerView.addItemDecoration(dividerItemDecoration);

        repositoriesAdapter = new RepositoriesAdapter();

        OwnerRepositories ownerRepositories = (OwnerRepositories) getIntent()
                .getSerializableExtra(OWNER_REPO_EXTRA);

        bind(ownerRepositories);

    }

    private void bind(OwnerRepositories ownerRepositories) {

        String profileString = ownerRepositories.getOwner().getName();

        if(profileString == null || profileString.isEmpty()){
            profileString = ownerRepositories.getOwner().getLogin();
        }

        profileName.setText(profileString);

        Picasso.with(this)
                .load(ownerRepositories.getOwner().getAvatarUrl())
                .placeholder(R.drawable.ph_profile_picture_200dp)
                .transform(new BorderedCircleTransform())
                .into(profilePicture);

        recyclerView.setAdapter(repositoriesAdapter);
        repositoriesAdapter.setItens(ownerRepositories.getRepositories());

    }

    @OnClick(R.id.home_button)
    protected void onClick(View view) {
        super.onBackPressed();
    }

}