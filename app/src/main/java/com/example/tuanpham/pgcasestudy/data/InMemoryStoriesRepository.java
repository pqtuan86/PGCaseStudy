package com.example.tuanpham.pgcasestudy.data;

import android.support.annotation.NonNull;

import java.util.List;

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
        storiesServiceApi.getTopStories(new StoriesServiceApi.ItemsServiceCallback<List<Story>>() {
            @Override
            public void onLoaded(List<Story> items) {
                callback.onTopStoryIdsLoaded(items);
            }
        });
    }

    @Override
    public void getStory(@NonNull int storyId, @NonNull final GetItemCallback callback) {
        storiesServiceApi.getSingleStory(storyId, new StoriesServiceApi.ItemsServiceCallback<Story>() {
            @Override
            public void onLoaded(Story item) {
                callback.onItemLoaded(item);
            }
        });
    }

    @Override
    public void refreshData() {

    }
}
