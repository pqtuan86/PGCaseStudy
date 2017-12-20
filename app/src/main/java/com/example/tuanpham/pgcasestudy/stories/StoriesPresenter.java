package com.example.tuanpham.pgcasestudy.stories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.tuanpham.pgcasestudy.data.Item;
import com.example.tuanpham.pgcasestudy.data.ItemsRepository;

import java.util.List;

/**
 * Created by tuanpham on 11/1/17.
 */

public class StoriesPresenter implements StoriesContract.UserActionsListener {


    private final ItemsRepository itemsRepository;
    private final StoriesContract.View itemsView;

    public StoriesPresenter(@NonNull ItemsRepository itemsRepository, @NonNull StoriesContract.View itemsView) {
        if (itemsRepository == null) {
            throw new NullPointerException("itemsRepository can not be null");
        } else {
            this.itemsRepository = itemsRepository;
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
        itemsRepository.getTopStories(new ItemsRepository.GetTopStoryIdsCallback() {
            @Override
            public void onTopStoryIdsLoaded(List<Item> stories) {
                itemsView.showItems(stories);
                itemsView.setProgressIndicator(false);
            }
        });
    }

    @Override
    public void openItem(@Nullable Item selectedStory) {
        itemsView.showItemDetail(selectedStory);
    }

    @Override
    public void getStory(int storyId) {
        itemsRepository.getStory(storyId, new ItemsRepository.GetStoryCallback() {
            @Override
            public void onItemLoaded(Item story) {
                itemsView.populateStoryDetails(story);
            }
        });
    }
}
