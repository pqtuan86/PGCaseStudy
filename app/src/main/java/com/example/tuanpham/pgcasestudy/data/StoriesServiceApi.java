package com.example.tuanpham.pgcasestudy.data;

import java.util.List;

/**
 * Created by tuanpham on 11/4/17.
 */

public interface StoriesServiceApi {

    interface ItemsServiceCallback<T> {
        void onLoaded(T items);
    }

    void getTopStories(ItemsServiceCallback<List<Story>> callback);

    void getAllItems(ItemsServiceCallback<List<Story>> callback);

    void getSingleStory(int storyId, ItemsServiceCallback<Story> callback);

}
