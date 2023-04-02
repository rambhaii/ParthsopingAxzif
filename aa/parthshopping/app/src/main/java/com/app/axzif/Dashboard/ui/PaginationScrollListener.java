package com.app.axzif.Dashboard.ui;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    StaggeredGridLayoutManager layoutManager;
     int SpanCount=2;
    private int visibleThreshold = 2;
    private int currentPage = 0;
    private int startingPageIndex = 0;
    private int previousTotalItemCount = 0;

    private boolean loading = true;
    public PaginationScrollListener(StaggeredGridLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int lastVisibleItemPosition = 0;
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int[] firstVisibleItemPosition = layoutManager.findFirstVisibleItemPositions(null);
        int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
        // get maximum element within the list
        lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        if (loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount
                && recyclerView.getAdapter().getItemCount() > visibleThreshold) {// This condition will useful when recyclerview has less than visibleThreshold items
            currentPage++;
            Log.d( "jcbksjbsjvs: ","csnnlsbvls");
            onLoadMore(currentPage, totalItemCount, recyclerView);
            loading = true;
        }
    }
    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }
    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);
    public void resetState() {
        this.currentPage = this.startingPageIndex;
        this.previousTotalItemCount = 0;
        this.loading = true;
    }



    protected abstract void loadMoreItems();

    public abstract int getTotalPageCount();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();
}