package com.example.tuanpham.pgcasestudy.data;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by tuanpham on 11/4/17.
 */

public class InMemoryItemsRepository implements ItemsRepository {


    private final HNItemsServiceApi HNItemsServiceApi;


    public InMemoryItemsRepository(@NonNull HNItemsServiceApi HNItemsServiceApi) {
        this.HNItemsServiceApi = HNItemsServiceApi;
    }

    @Override
    public void getItems(@NonNull LoadItemsCallback callback) {

    }

    @Override
    public void getTopStories(@NonNull final GetTopStoryIdsCallback callback) {
        HNItemsServiceApi.getTopStories(new HNItemsServiceApi.ItemsServiceCallback<List<Story>>() {
            @Override
            public void onLoaded(List<Story> items) {
                callback.onTopStoryIdsLoaded(items);
            }
        });
    }

    @Override
    public void getStory(@NonNull int storyId, @NonNull final GetStoryCallback callback) {
        HNItemsServiceApi.getSingleStory(storyId, new HNItemsServiceApi.ItemsServiceCallback<Story>() {
            @Override
            public void onLoaded(Story item) {
                callback.onItemLoaded(item);
            }
        });
    }

    @Override
    public void getComment(@NonNull int commentID, @NonNull final GetCommentCallback callback) {
        HNItemsServiceApi.getSingleComment(commentID, new HNItemsServiceApi.ItemsServiceCallback<Comment>() {
            @Override
            public void onLoaded(Comment item) {
                callback.onItemLoaded(item);
            }
        });
    }

    @Override
    public void refreshData() {

    }
}
