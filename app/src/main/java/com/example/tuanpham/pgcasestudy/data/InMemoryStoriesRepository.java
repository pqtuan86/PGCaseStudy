package com.example.tuanpham.pgcasestudy.data;

import android.support.annotation.NonNull;

/**
 * Created by tuanpham on 11/4/17.
 */

public class InMemoryStoriesRepository implements StoriesRepository {


    private final StoriesServiceApi storiesServiceApi;


    public InMemoryStoriesRepository(@NonNull StoriesServiceApi storiesServiceApi) {
        this.storiesServiceApi = storiesServiceApi;
    }
    @Override
    public void getItems(@NonNull LoadItemsCallback callback) {

    }

    @Override
    public void getTopStories(@NonNull final GetTopStoryIdsCallback callback) {
        storiesServiceApi.getTopStories(new StoriesServiceApi.ItemsServiceCallback<int[]>() {
            @Override
            public void onLoaded(int[] items) {
                callback.onTopStoryIdsLoaded(items);
            }
        });
    }

    @Override
    public void getItem(@NonNull String itemId, @NonNull GetItemCallback callback) {

    }

    @Override
    public void refreshData() {

    }
}
