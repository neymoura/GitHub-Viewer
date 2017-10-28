package com.ghost.githubviewer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ghost.githubviewer.R;
import com.ghost.githubviewer.model.Repository;
import com.ghost.githubviewer.view.holder.RepositoryViewHolder;

import java.util.ArrayList;
import java.util.List;


public class RepositoriesAdapter extends RecyclerView.Adapter<RepositoryViewHolder> {

    private ArrayList<Repository> itens = new ArrayList<>();

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_repository, parent, false);

        return new RepositoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        holder.bind(itens.get(position));
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    public void setItens(List<Repository> itens) {
        this.itens.clear();
        this.itens.addAll(itens);
        notifyDataSetChanged();
    }
}
