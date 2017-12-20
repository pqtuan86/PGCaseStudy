package com.example.tuanpham.pgcasestudy.comments;

import android.support.annotation.NonNull;

import com.example.tuanpham.pgcasestudy.data.Item;

/**
 * Created by tuanpham on 11/14/17.
 */

public interface CommentsContract {

    interface View {
        void setProgressIndicator(boolean active);

        void populateCommentDetails(Item comment);

        void populateReplies(Item ancestor, Item reply);
    }

    interface UserActionsListener {

        void getComment(@NonNull int commentId);

        void getReply(@NonNull Item ancestor, @NonNull int replyId);
    }
}
