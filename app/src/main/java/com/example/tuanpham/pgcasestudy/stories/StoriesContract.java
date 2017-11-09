package com.example.tuanpham.pgcasestudy.stories;

import android.support.annotation.Nullable;

import com.example.tuanpham.pgcasestudy.data.Story;

import java.util.List;

/**
 * Created by tuanpham on 11/1/17.
 */

public interface StoriesContract {

    interface View {
        void setProgressIndicator(boolean active);

        void showItems(List<Story> stories);

        void showItemDetail(String itemId);
    }

    interface UserActionsListener {

        void getTopStories();

        void loadItems(boolean forceUpdate);

        void refreshItems();

        void openItem(@Nullable Story selectedStory);
    }
}
