package com.example.tuanpham.pgcasestudy.stories;

import android.content.Context;
import android.os.Bundle;
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

    private StoriesContract.UserActionsListener userActionsListener;

    private StoriesAdapter storiesAdapter;

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

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.stories_list);
        recyclerView.setAdapter(storiesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                userActionsListener.loadItems(true);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        userActionsListener.getTopStories();
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
    }

    @Override
    public void showItemDetail(String itemid) {
        // Open story detail
    }

    private static class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder> {

        private List<Story> stories;
        private StoryItemListener itemListener;

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
            viewHolder.title.setText(TextUtils.isEmpty(story.getTitle()) ? "missing title" : story.getTitle());
            String infos = (story.getScore() != null ? story.getScore() : "0") + " points"
                    + " by " + story.getBy()
                    + UiUtils.getLatesUpdateTime(story.getTime()) + " | "
                    + (story.getDescendants() != null ? story.getDescendants() : 0) + "comments";
            viewHolder.description.setText(infos);
        }

        public void replaceData(List<Story> stories) {
            setList(stories);
            notifyDataSetChanged();
        }

        private void setList(List<Story> stories) {
            if (stories == null) {
                throw new NullPointerException("stories can not be null");
            }
            this.stories = stories;
        }

        @Override
        public int getItemCount() {
            return stories.size();
        }

        public Story getItem(int position) {
            return stories.get(position);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView title;

            public TextView description;
            private StoryItemListener mItemListener;

            public ViewHolder(View itemView, StoryItemListener listener) {
                super(itemView);
                mItemListener = listener;
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
