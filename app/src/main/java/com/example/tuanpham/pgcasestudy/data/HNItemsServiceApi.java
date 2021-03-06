package com.example.tuanpham.pgcasestudy.data;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by tuanpham on 11/4/17.
 */

public interface HNItemsServiceApi {

    interface ItemsServiceCallback<T> {
        void onLoaded(T items);
    }

    void getTopStories(ItemsServiceCallback<List<Item>> callback);

    void getSingleStory(int storyId, ItemsServiceCallback<Item> callback);

    void getSingleComment(@NonNull int commentID, ItemsServiceCallback<Item> callback);

}
