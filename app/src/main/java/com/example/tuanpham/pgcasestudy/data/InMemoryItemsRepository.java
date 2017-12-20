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
    public void getTopStories(@NonNull final GetTopStoryIdsCallback callback) {
        HNItemsServiceApi.getTopStories(new HNItemsServiceApi.ItemsServiceCallback<List<Item>>() {
            @Override
            public void onLoaded(List<Item> items) {
                callback.onTopStoryIdsLoaded(items);
            }
        });
    }

    @Override
    public void getStory(@NonNull int storyId, @NonNull final GetStoryCallback callback) {
        HNItemsServiceApi.getSingleStory(storyId, new HNItemsServiceApi.ItemsServiceCallback<Item>() {
            @Override
            public void onLoaded(Item item) {
                callback.onItemLoaded(item);
            }
        });
    }

    @Override
    public void getComment(@NonNull int commentID, @NonNull final GetCommentCallback callback) {
        HNItemsServiceApi.getSingleComment(commentID, new HNItemsServiceApi.ItemsServiceCallback<Item>() {
            @Override
            public void onLoaded(Item item) {
                callback.onItemLoaded(item);
            }
        });
    }

}
