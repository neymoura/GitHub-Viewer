package com.ghost.githubviewer.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ghost.githubviewer.R;
import com.ghost.githubviewer.model.Repository;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoryViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.repository_name)
    protected TextView name;

    @BindView(R.id.repository_language)
    protected TextView language;

    public RepositoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Repository repository) {
        name.setText(repository.getName());
        language.setText(repository.getLanguage());
    }

}
