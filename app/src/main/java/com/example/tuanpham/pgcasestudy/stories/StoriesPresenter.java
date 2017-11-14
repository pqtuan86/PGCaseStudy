package com.example.tuanpham.pgcasestudy.stories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.tuanpham.pgcasestudy.data.StoriesRepository;
import com.example.tuanpham.pgcasestudy.data.Story;

import java.util.List;

/**
 * Created by tuanpham on 11/1/17.
 */

public class StoriesPresenter implements StoriesContract.UserActionsListener {


    private final StoriesRepository storiesRepository;
    private final StoriesContract.View itemsView;

    public StoriesPresenter(@NonNull StoriesRepository storiesRepository, @NonNull StoriesContract.View itemsView) {
        if (storiesRepository == null) {
            throw new NullPointerException("storiesRepository can not be null");
        } else {
            this.storiesRepository = storiesRepository;
        }

        if (itemsView == null) {
            throw new NullPointerException("newsView can not be null");
        } else {
            this.itemsView = itemsView;
        }
    }

    @Override
    public void getTopStories() {
        itemsView.setProgressIndicator(true);
        storiesRepository.getTopStories(new StoriesRepository.GetTopStoryIdsCallback() {
            @Override
            public void onTopStoryIdsLoaded(List<Story> stories) {
                itemsView.showItems(stories);
                itemsView.setProgressIndicator(false);
            }
        });
    }

    @Override
    public void loadItems(boolean forceUpdate) {
        itemsView.setProgressIndicator(true);

        storiesRepository.getItems(new StoriesRepository.LoadItemsCallback() {
            @Override
            public void onItemsLoaded(List<Story> stories) {
                itemsView.setProgressIndicator(false);
                itemsView.showItems(stories);
            }
        });
    }

    @Override
    public void refreshItems() {

    }

    @Override
    public void openItem(@Nullable Story selectedStory) {
        itemsView.showItemDetail(selectedStory.getId() + "");
    }

    @Override
    public void getStory(int storyId) {
        storiesRepository.getStory(storyId, new StoriesRepository.GetItemCallback() {
            @Override
            public void onItemLoaded(Story story) {
                itemsView.populateStoryDetails(story);
            }
        });
    }
}
