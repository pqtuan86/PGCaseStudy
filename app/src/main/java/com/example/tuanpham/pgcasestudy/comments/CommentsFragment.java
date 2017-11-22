package com.example.tuanpham.pgcasestudy.comments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tuanpham.pgcasestudy.Injection;
import com.example.tuanpham.pgcasestudy.R;
import com.example.tuanpham.pgcasestudy.data.Comment;
import com.example.tuanpham.pgcasestudy.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuanpham on 11/14/17.
 */

public class CommentsFragment extends Fragment implements CommentsContract.View {
    private static final String LAST_VISIBLE_POS = "LAST_VISIBLE_POS";
    private static final String RECYCLER_VIEW_STATE = "RECYCLER_VIEW_STATE";

    private static final String ARGUMENT_COMMENT_ID_LIST = "COMMENT_ID_LIST";

    private CommentsContract.UserActionsListener userActionsListener;

    private RecyclerView recyclerView;

    private Parcelable recyclerViewState;

    private CommentsAdapter commentsAdapter;

    private ArrayList<Integer> commentIdList;

    public static CommentsFragment newInstance(ArrayList<Integer> commentIdList) {
        Bundle arguments = new Bundle();
        arguments.putIntegerArrayList(ARGUMENT_COMMENT_ID_LIST, commentIdList);
        CommentsFragment fragment = new CommentsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userActionsListener = new CommentsPresenter(Injection.provideStoriesRepository(), this);
        commentIdList = getArguments().getIntegerArrayList(ARGUMENT_COMMENT_ID_LIST);
        setRetainInstance(true);
        commentsAdapter = new CommentsAdapter();
        loadComments();
    }

    private void loadComments() {
        List<Comment> comments = new ArrayList<>();
        for (int commentId : commentIdList) {
            Comment comment = new Comment(commentId);
            comments.add(comment);
        }
        commentsAdapter.replaceData(comments);
        getComments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.stories_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(commentsAdapter);

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadComments();
            }
        });
        setProgressIndicator(true);
        return view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable(RECYCLER_VIEW_STATE));
            recyclerView.scrollToPosition(savedInstanceState.getInt(LAST_VISIBLE_POS));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(RECYCLER_VIEW_STATE, recyclerViewState);
        int lastScrollPos = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        outState.putInt(LAST_VISIBLE_POS, lastScrollPos);
    }


    private void getComments() {
        for (int commentId : commentIdList) {
            userActionsListener.getComment(commentId);
        }
        setProgressIndicator(false);
    }

    @Override
    public void setProgressIndicator(final boolean active) {
        if (getView() == null) {
            return;
        }

        final SwipeRefreshLayout srl =
                (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void populateCommentDetails(Comment comment) {
        Comment newCommentObj = new Comment(comment);
        commentsAdapter.populateCommentDetails(newCommentObj);

        if (comment.getKids() != null) {
            // Get the replies for this comment
            for (int replyId : newCommentObj.getKids()) {
                userActionsListener.getReply(newCommentObj, replyId);
            }
        }

    }

    @Override
    public void populateReplies(Comment ancestor, Comment reply) {
        ancestor.addReply(reply);
        commentsAdapter.populateCommentDetails(ancestor);
    }

    private static class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

        private static final int MAX_ROW_PER_LOAD = 40;
        private List<Comment> comments;
        private int cursor = 0;


        public CommentsAdapter() {
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View storyView = inflater.inflate(R.layout.item_comment, parent, false);

            return new ViewHolder(storyView);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            Comment comment = comments.get(position);
            if (!TextUtils.isEmpty(comment.getBy())) {
                viewHolder.by_and_update.setText(comment.getBy() + " " + UiUtils.getLatestUpdateTime(System.currentTimeMillis()/1000, comment.getTime
                        ()));
                viewHolder.content.setText(!TextUtils.isEmpty(comment.getText()) ? Html.fromHtml(comment.getText()) : "");
                viewHolder.addReplies(comment.getReplies());
            }
        }

        public void replaceData(List<Comment> comments) {
            setList(comments);
            notifyDataSetChanged();
        }

        public void populateCommentDetails(Comment comment) {
            int commentPos = comments.indexOf(comment);
            comments.set(commentPos, comment);
            notifyItemChanged(commentPos);
        }

        public Comment getSelectedComment(Comment comment) {
            return  comments.get(comments.indexOf(comment));
        }

        public int[] getNextCommentsIds() {
            int[] range = null;
            if (cursor < comments.size()) {
                if (cursor + MAX_ROW_PER_LOAD < comments.size()) {
                    range = new int[MAX_ROW_PER_LOAD];
                } else {
                    range = new int[comments.size() - cursor];
                }
                for (int i = 0; i < range.length; i++) {
                    range[i] = comments.get(cursor + i).getId();
                }
                cursor += MAX_ROW_PER_LOAD;
                if (cursor > comments.size()) {
                    cursor = comments.size() - 1;
                }
            }
            return range;
        }

        public int getCurrentCursor() {
            return cursor;
        }

        private void setList(List<Comment> comments) {
            if (comments == null) {
                throw new NullPointerException("storiesWithoutDetails can not be null");
            }
            cursor = 0;
            this.comments = comments;
        }

        @Override
        public int getItemCount() {
            return comments != null ? comments.size() : 0;
        }

        public Comment getItem(int position) {
            return comments.get(position);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView by_and_update;
            public TextView content;
            public LinearLayout replies_container;
            LayoutInflater inflater;

            public ViewHolder(View itemView) {
                super(itemView);
                inflater = (LayoutInflater) itemView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                by_and_update = (TextView) itemView.findViewById(R.id.tv_comment_by_and_updated);
                content = (TextView) itemView.findViewById(R.id.tv_comment_content);
                replies_container = (LinearLayout) itemView.findViewById(R.id.ll_replies_container);
            }

            public void addReplies(ArrayList<Comment> replies) {

                if (replies != null && replies.size() > 0) {
                    replies_container.removeAllViews();
                    for (Comment reply : replies) {
                        if (!TextUtils.isEmpty(reply.getBy())) {
                            TextView tvBy = new TextView(itemView.getContext());
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                                    .LayoutParams.WRAP_CONTENT);
                            tvBy.setLayoutParams(params);
                            tvBy.setText("\u25BA " + reply.getBy() + " " + UiUtils.getLatestUpdateTime
                                    (System.currentTimeMillis()/1000, reply.getTime()));
                            tvBy.setTextSize(TypedValue.COMPLEX_UNIT_PX, itemView.getResources().getDimension(R.dimen.text_s));
                            tvBy.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.grey));
                            replies_container.addView(tvBy);
                            TextView tvReplyContent = new TextView(itemView.getContext());
                            tvReplyContent.setLayoutParams(params);
                            tvReplyContent.setText(!TextUtils.isEmpty(reply.getText()) ? Html.fromHtml(reply.getText()) : "");
                            tvReplyContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, itemView.getResources().getDimension(R.dimen.text_m));
                            tvReplyContent.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.black));
                            replies_container.addView(tvReplyContent);
                        }

                    }
                }
            }

        }
    }
}
