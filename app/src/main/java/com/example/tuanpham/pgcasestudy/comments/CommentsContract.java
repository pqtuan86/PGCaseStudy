package com.example.tuanpham.pgcasestudy.comments;

import android.support.annotation.NonNull;

import com.example.tuanpham.pgcasestudy.data.Comment;

/**
 * Created by tuanpham on 11/14/17.
 */

public interface CommentsContract {

    interface View {
        void setProgressIndicator(boolean active);

        void populateCommentDetails(Comment comment);

        void populateReplies(Comment ancestor, Comment reply);
    }

    interface UserActionsListener {

        void getComment(@NonNull int commentId);

        void getReply(@NonNull Comment ancestor, @NonNull int replyId);
    }
}
