package com.example.tuanpham.pgcasestudy.data;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by tuanpham on 11/4/17.
 */

public interface ItemsRepository {

    interface LoadItemsCallback {
        void onItemsLoaded(List<Story> stories);
    }

    interface GetItemCallback<T> {
        void onItemLoaded(T item);
    }

    interface GetTopStoryIdsCallback {
        void onTopStoryIdsLoaded(List<Story> stories);
    }

    void getItems(@NonNull LoadItemsCallback callback);

    void getTopStories(@NonNull GetTopStoryIdsCallback callback);

    void getStory(@NonNull int storyId, @NonNull GetItemCallback<Story> callback);

    void getComment(@NonNull int commentID, @NonNull GetItemCallback<Comment> callback);

    void refreshData();
}
