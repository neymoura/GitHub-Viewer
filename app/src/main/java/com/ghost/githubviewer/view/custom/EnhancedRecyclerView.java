package com.ghost.githubviewer.view.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class EnhancedRecyclerView extends RecyclerView {

    private View mEmptyView;

    private EmptyViewCallback callback;

    private int itemsWhenEmpty;

    private AdapterDataObserver mDataObserver = new AdapterDataObserver() {

        @Override
        public void onChanged() {

            super.onChanged();

            updateEmptyView();
        }
    };

    public EnhancedRecyclerView(Context context) {
        super(context);
    }

    public EnhancedRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EnhancedRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setmEmptyView(View mEmptyView) {
        this.mEmptyView = mEmptyView;
    }

    public void setCallback(EmptyViewCallback callback) {
        this.callback = callback;
    }

    public void setItemsWhenEmpty(int itemsWhenEmpty) {
        this.itemsWhenEmpty = itemsWhenEmpty;
    }

    @Override
    public void setAdapter(Adapter adapter) {

        if (!adapter.hasObservers()) {

            adapter.registerAdapterDataObserver(mDataObserver);

            super.setAdapter(adapter);

            updateEmptyView();

        }
    }

    private void updateEmptyView() {

        if (getAdapter() != null) {

            boolean showEmptyView = getAdapter().getItemCount() <= itemsWhenEmpty;

            if (mEmptyView != null) {
                mEmptyView.setVisibility(showEmptyView ? VISIBLE : GONE);
            }

            setVisibility(showEmptyView ? GONE : VISIBLE);

            if(callback != null){
                if(showEmptyView){
                    callback.onShowEmptyView();
                }else{
                    callback.onHideEmptyView();
                }
            }

        }
    }

    public interface EmptyViewCallback{
        void onShowEmptyView();
        void onHideEmptyView();
    }

}
