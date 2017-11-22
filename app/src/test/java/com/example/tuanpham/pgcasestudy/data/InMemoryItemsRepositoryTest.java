package com.example.tuanpham.pgcasestudy.data;

import com.example.tuanpham.pgcasestudy.data.HNItemsServiceApi.ItemsServiceCallback;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by tuanpham on 11/22/17.
 */

public class InMemoryItemsRepositoryTest {

    private static Story STORY = new Story(12334);
    private static Comment COMMENT = new Comment(12334);
    private static List<Story> STORIES = Arrays.asList(new Story(12345), new Story(6789));
    private InMemoryItemsRepository itemsRepository;

    @Mock
    HNItemsServiceApi hnItemsServiceApi;

    @Mock
    private ItemsRepository.GetTopStoryIdsCallback getTopStoryIdsCallback;

    @Mock
    private ItemsRepository.GetStoryCallback getStoryCallback;

    @Mock
    private ItemsRepository.GetCommentCallback getCommentCallback;

    @Captor
    private ArgumentCaptor<ItemsServiceCallback> itemsServiceCallbackCaptor;

    @Before
    public void setUpItemsRepository() {
        MockitoAnnotations.initMocks(this);

        itemsRepository = new InMemoryItemsRepository(hnItemsServiceApi);
    }

    @Test
    public void testGetTopStories() throws Exception {
        itemsRepository.getTopStories(getTopStoryIdsCallback);

        verify(hnItemsServiceApi).getTopStories(any(HNItemsServiceApi.ItemsServiceCallback.class));

        verify(hnItemsServiceApi).getTopStories(itemsServiceCallbackCaptor.capture());
        itemsServiceCallbackCaptor.getValue().onLoaded(STORIES);
    }

    @Test
    public void testGetStory() throws Exception {
        itemsRepository.getStory(12345, getStoryCallback);

        verify(hnItemsServiceApi).getSingleStory(eq(12345), any(HNItemsServiceApi.ItemsServiceCallback.class));

        verify(hnItemsServiceApi).getSingleStory(eq(12345), itemsServiceCallbackCaptor.capture());
        itemsServiceCallbackCaptor.getValue().onLoaded(STORY);
    }

    @Test
    public void testGetComment() throws Exception {
        itemsRepository.getComment(12345, getCommentCallback);

        verify(hnItemsServiceApi).getSingleComment(eq(12345), any(HNItemsServiceApi.ItemsServiceCallback.class));

        verify(hnItemsServiceApi).getSingleComment(eq(12345), itemsServiceCallbackCaptor.capture());
        itemsServiceCallbackCaptor.getValue().onLoaded(COMMENT);
    }
}
