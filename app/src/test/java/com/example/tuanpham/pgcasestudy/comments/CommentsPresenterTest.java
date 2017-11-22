package com.example.tuanpham.pgcasestudy.comments;

import com.example.tuanpham.pgcasestudy.data.Comment;
import com.example.tuanpham.pgcasestudy.data.ItemsRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by tuanpham on 11/17/17.
 */

public class CommentsPresenterTest {
    private static Comment COMMENT = new Comment(12334);
    private static Comment REPLY = new Comment(334455);


    @Mock
    private CommentsContract.View commentsView;

    @Mock
    private ItemsRepository itemsRepository;

    @Captor
    private ArgumentCaptor<ItemsRepository.GetCommentCallback> getCommentCallbackCaptor;

    private CommentsPresenter commentsPresenter;

    @Before
    public void setupCommentsPresenter() {
        MockitoAnnotations.initMocks(this);

        commentsPresenter = new CommentsPresenter(itemsRepository, commentsView);
    }

    @Test
    public void testGetCommentAndLoadIntoView() {
        int commentId = 12345;
        commentsPresenter.getComment(commentId);

        verify(itemsRepository).getComment(eq(commentId), getCommentCallbackCaptor.capture());
        getCommentCallbackCaptor.getValue().onItemLoaded(COMMENT);

        verify(commentsView).populateCommentDetails(COMMENT);
    }

    @Test
    public void testGetReplyAndLoadIntoView() {
        // Given a stubbed story id
        int replyId = 112345;
        commentsPresenter.getReply(COMMENT, replyId);

        verify(itemsRepository).getComment(eq(replyId), getCommentCallbackCaptor.capture());
        getCommentCallbackCaptor.getValue().onItemLoaded(REPLY);

        verify(commentsView).populateReplies(COMMENT, REPLY);
    }
}
