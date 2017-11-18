package com.example.tuanpham.pgcasestudy.comments;

import android.support.annotation.NonNull;

import com.example.tuanpham.pgcasestudy.data.Comment;
import com.example.tuanpham.pgcasestudy.data.ItemsRepository;

/**
 * Created by tuanpham on 11/14/17.
 */

public class CommentsPresenter implements CommentsContract.UserActionsListener {

    private final ItemsRepository itemsRepository;
    private final CommentsContract.View itemsView;

    public CommentsPresenter(@NonNull ItemsRepository itemsRepository, @NonNull CommentsContract.View itemsView) {
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
    public void getComment(@NonNull int commentId) {
        itemsRepository.getComment(commentId, new ItemsRepository.GetCommentCallback() {
            @Override
            public void onItemLoaded(Comment item) {
                itemsView.populateCommentDetails(item);
            }
        });
    }

    @Override
    public void getReply(@NonNull final Comment ancestor, @NonNull int replyId) {
        itemsRepository.getComment(replyId, new ItemsRepository.GetCommentCallback() {
            @Override
            public void onItemLoaded(Comment item) {
                itemsView.populateReplies(ancestor, item);
            }
        });
    }
}
