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
        storiesRepository.getTopStories(new StoriesRepository.GetTopStoryIdsCallback() {
            @Override
            public void onTopStoryIdsLoaded(int[] ids) {
                Log.i("Top stories ids", String.valueOf(ids));
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
}
