package com.example.tuanpham.pgcasestudy.stories;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tuanpham.pgcasestudy.Injection;
import com.example.tuanpham.pgcasestudy.R;
import com.example.tuanpham.pgcasestudy.data.Story;
import com.example.tuanpham.pgcasestudy.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link StoryItemListener}
 * interface.
 */
public class StoriesFragment extends Fragment implements StoriesContract.View {

    private static final String LAST_VISIBLE_POS = "LAST_VISIBLE_POS";
    private static final String RECYCLER_VIEW_STATE = "RECYCLER_VIEW_STATE";
    private StoriesContract.UserActionsListener userActionsListener;

    private StoriesAdapter storiesAdapter;

    private RecyclerView recyclerView;

    private Parcelable recyclerViewState;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StoriesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static StoriesFragment newInstance() {
        return new StoriesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userActionsListener = new StoriesPresenter(Injection.provideStoriesRepository(), this);
        storiesAdapter = new StoriesAdapter(new ArrayList<Story>(0), listener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stories, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.stories_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(storiesAdapter);
        setListStoriesScrollChangedListener();

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                userActionsListener.getTopStories();
            }
        });
        return view;
    }

    @SuppressWarnings("deprecation")
    private void setListStoriesScrollChangedListener() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    onScroll((RecyclerView) view, storiesAdapter.getCurrentCursor());
                }
            });
        } else {
            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    onScroll(recyclerView, storiesAdapter.getCurrentCursor());
                }
            });
        }
    }

    private void onScroll(RecyclerView recyclerView, int currentCursor) {
        if (recyclerView.getVisibility() == View.GONE) {
            return;
        }
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
        if (lastVisibleItem != -1) {
            if ((currentCursor - lastVisibleItem) < 25) {
                getStoriesDetails();
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
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

    @Override
    public void onResume() {
        super.onResume();
        if (storiesAdapter.getItemCount() == 0) {
            userActionsListener.getTopStories();
        }
    }

    private StoryItemListener listener = new StoryItemListener() {
        @Override
        public void onStoryClick(Story clickedStory) {
            // Open story detail screen
        }
    };

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
    public void showItems(List<Story> stories) {
        storiesAdapter.replaceData(stories);

        getStoriesDetails();
    }

    private void getStoriesDetails() {
        int[] ids = storiesAdapter.getNextStoriesIds();
        for (int storyId : ids) {
            userActionsListener.getStory(storyId);
        }
    }

    @Override
    public void showItemDetail(String itemid) {
        // Open story detail
    }

    @Override
    public void populateStoryDetails(Story story) {
        storiesAdapter.populateStoryDetails(story);
    }

    private static class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder> {

        private static final int MAX_ROW_PER_LOAD = 40;
        private List<Story> stories;
        private StoryItemListener itemListener;
        private int cursor = 0;


        public StoriesAdapter(List<Story> stories, StoryItemListener itemListener) {
            setList(stories);
            this.itemListener = itemListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View storyView = inflater.inflate(R.layout.item_story, parent, false);

            return new ViewHolder(storyView, itemListener);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            Story story = stories.get(position);
            viewHolder.index.setText(position + ".");
            if (!TextUtils.isEmpty(story.getTitle())) {
                viewHolder.title.setText(TextUtils.isEmpty(story.getTitle()) ? "" : story.getTitle());
                String infos = (story.getScore() != null ? story.getScore() : "0") + " points"
                        + " by " + story.getBy() + " "
                        + UiUtils.getLatesUpdateTime(story.getTime() != null ? story.getTime() : 0) + " | "
                        + (story.getDescendants() != null ? story.getDescendants() : 0) + " comments";
                viewHolder.description.setText(infos);
            }
        }

        public void replaceData(List<Story> stories) {
            setList(stories);
            notifyDataSetChanged();
        }

        public void populateStoryDetails(Story story) {
            int storyPos = stories.indexOf(story);
            stories.set(storyPos, story);
            notifyItemChanged(storyPos);
        }

        public int[] getNextStoriesIds() {
            int[] range = null;
            if (cursor < stories.size()) {
                if (cursor + MAX_ROW_PER_LOAD < stories.size()) {
                    range = new int[MAX_ROW_PER_LOAD];
                } else {
                    range = new int[stories.size() - cursor];
                }
                for (int i = 0; i < range.length; i++) {
                    range[i] = stories.get(cursor + i).getId();
                }
                cursor += MAX_ROW_PER_LOAD;
                if (cursor > stories.size()) {
                    cursor = stories.size() - 1;
                }
            }
            return range;
        }

        public int getCurrentCursor() {
            return cursor;
        }

        private void setList(List<Story> stories) {
            if (stories == null) {
                throw new NullPointerException("storiesWithoutDetails can not be null");
            }
            cursor = 0;
            this.stories = stories;
        }

        @Override
        public int getItemCount() {
            return stories != null ? stories.size() : 0;
        }

        public Story getItem(int position) {
            return stories.get(position);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView index;
            public TextView title;
            public TextView description;
            private StoryItemListener mItemListener;

            public ViewHolder(View itemView, StoryItemListener listener) {
                super(itemView);
                mItemListener = listener;
                index = (TextView) itemView.findViewById(R.id.tv_story_index);
                title = (TextView) itemView.findViewById(R.id.tv_story_title);
                description = (TextView) itemView.findViewById(R.id.tv_story_additional_infos);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Story story = getItem(position);
                mItemListener.onStoryClick(story);

            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface StoryItemListener {
        // TODO: Update argument type and name
        void onStoryClick(Story clickedStory);
    }
}
