package com.example.tuanpham.pgcasestudy.stories;

import com.example.tuanpham.pgcasestudy.data.ItemsRepository;
import com.example.tuanpham.pgcasestudy.data.Story;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by tuanpham on 11/17/17.
 */

public class StoriesPresenterTest {
    private static Story STORY = new Story(12334);
    private static List<Story> STORIES = Arrays.asList(new Story(12345), new Story(6789));


    @Mock
    private StoriesContract.View storiesView;

    @Mock
    private ItemsRepository itemsRepository;

    @Captor
    private ArgumentCaptor<ItemsRepository.GetTopStoryIdsCallback> getTopStoryIdsCallbackCaptor;

    @Captor
    private ArgumentCaptor<ItemsRepository.GetStoryCallback> getItemCallbackCaptor;

    private StoriesPresenter storiesPresenter;

    @Before
    public void setupStoriesPresenter() {
        MockitoAnnotations.initMocks(this);

        storiesPresenter = new StoriesPresenter(itemsRepository, storiesView);
    }

    @Test
    public void testGetStoriesAndLoadIntoView() {
        storiesPresenter.getTopStories();

        verify(itemsRepository).getTopStories(getTopStoryIdsCallbackCaptor.capture());
        getTopStoryIdsCallbackCaptor.getValue().onTopStoryIdsLoaded(STORIES);

        verify(storiesView).setProgressIndicator(false);
        verify(storiesView).showItems(STORIES);

        ArgumentCaptor<List> showStoriesArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(storiesView).showItems(showStoriesArgumentCaptor.capture());
        assertTrue(showStoriesArgumentCaptor.getValue().size() == 2);
    }

    @Test
    public void testGetStoryAndLoadIntoView() {
        // Given a stubbed story id
        int storyId = 112345;
        storiesPresenter.getStory(storyId);

        verify(itemsRepository).getStory(eq(storyId), getItemCallbackCaptor.capture());
        getItemCallbackCaptor.getValue().onItemLoaded(STORY);

        verify(storiesView).populateStoryDetails(STORY);
    }

    @Test
    public void testClickOnItem_ShowsCommentsUi() {
        // Given a stubbed story
        Story requestedStory = new Story(12345);

        storiesPresenter.openItem(requestedStory);

        verify(storiesView).showItemDetail(requestedStory);

    }
}
