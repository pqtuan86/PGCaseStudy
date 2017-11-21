package com.example.tuanpham.pgcasestudy.data;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tuanpham on 11/7/17.
 */

public class FakeHackerNewsServiceApiImpl implements HNItemsServiceApi {

    // TODO replace this with a new test specific data set.
    private static final ArrayList<Story> DATA;

    static {
        DATA = new ArrayList<>(2);
        addStory(15709418,
                "rbcgerard",
                113,
                Arrays.asList(15709763, 15709530, 15709531, 15709638, 15709668, 15710055, 15709949, 15709458,
                15711574, 15709940, 15709921, 15710162, 15712094, 15710432, 15710178, 15709726),
                250,
                1510793447,
                "New Orleans man locked up nearly 8 years awaiting trial, then case gets tossed",
                "story",
                "http://www.theadvocate.com/new_orleans/news/courts/article_d76e9496-c995-11e7-bd8b-4bb8d14cacbf.html?utm_content=buffera1a9c&utm_medium=social&utm_source=twitter.com&utm_campaign=buffer");

        addStory(15745441,
                "benpink",
                228,
                Arrays.asList(15745532, 15746019, 15745687, 15745937, 15746129, 15745569, 15746739, 15745686, 15746573, 15745828, 15746332, 15745479, 15745683, 15746036, 15745675, 15745562, 15745492, 15745796, 15746066, 15745912, 15745887, 15745920, 15746309, 15745671, 15746119, 15746064, 15745557),
                383,
                1511237313,
                "Tether Critical Announcement",
                "story",
                "https://tether.to/tether-critical-announcement/");
    }

    private static void addStory(int id, String by, int descendants, List<Integer> kids, int score, long time, String title, String type, String url) {
        Story story = new Story(id);
        story.setBy(by);
        story.setDescendants(descendants);
        story.setKids(new ArrayList<>(kids));
        story.setScore(score);
        story.setTime(time);
        story.setTitle(title);
        story.setType(type);
        story.setUrl(url);
        DATA.add(story);
    }

    @Override
    public void getTopStories(final ItemsServiceCallback<List<Story>> callback) {
        callback.onLoaded(DATA);
    }

    @Override
    public void getSingleStory(int storyId, final ItemsServiceCallback<Story> callback) {
        callback.onLoaded(DATA.get(DATA.indexOf(new Story(storyId))));
    }

    @Override
    public void getSingleComment(@NonNull int commentID, final ItemsServiceCallback<Comment> callback) {
//        apiEndpoint.getComment(commentID).enqueue(new Callback<Comment>() {
//            @Override
//            public void onResponse(Call<Comment> call, Response<Comment> response) {
//                callback.onLoaded(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<Comment> call, Throwable t) {
//
//            }
//        });
    }

}
