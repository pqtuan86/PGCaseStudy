package com.example.tuanpham.pgcasestudy.stories;

import android.support.annotation.Nullable;

import com.example.tuanpham.pgcasestudy.data.Item;

import java.util.List;

/**
 * Created by tuanpham on 11/1/17.
 */

public interface StoriesContract {

    interface View {
        void setProgressIndicator(boolean active);

        void showItems(List<Item> stories);

        void showItemDetail(Item story);

        void populateStoryDetails(Item story);
    }

    interface UserActionsListener {

        void getTopStories();

        void openItem(@Nullable Item selectedStory);

        void getStory(int storyId);
    }
}
