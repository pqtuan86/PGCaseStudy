package com.example.tuanpham.pgcasestudy.data;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by tuanpham on 11/4/17.
 */

public interface StoriesRepository {

    interface LoadItemsCallback {
        void onItemsLoaded(List<Story> stories);
    }

    interface GetItemCallback {
        void onItemLoaded(Story story);
    }

    interface GetTopStoryIdsCallback {
        void onTopStoryIdsLoaded(List<Story> stories);
    }

    void getItems(@NonNull LoadItemsCallback callback);

    void getTopStories(@NonNull GetTopStoryIdsCallback callback);

    void getStory(@NonNull int storyId, @NonNull GetItemCallback callback);

    void refreshData();
}
