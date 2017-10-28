package com.ghost.githubviewer.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ghost.githubviewer.R;
import com.ghost.githubviewer.model.Repository;

public class RepositoryViewHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView language;

    public RepositoryViewHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.repository_name);
        language = itemView.findViewById(R.id.repository_language);

    }

    public void bind(Repository repository) {

        name.setText(repository.getName());
        language.setText(repository.getLanguage());

    }

}
