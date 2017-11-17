package com.example.tuanpham.pgcasestudy.data;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tuanpham on 11/7/17.
 */

public class HackerNewsServiceApiImpl implements HNItemsServiceApi {

    private static final String BASE_URL = "https://hacker-news.firebaseio.com/v0/";

    private HackerNewsServiceApiEndpoint apiEndpoint;

    public HackerNewsServiceApiImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

        apiEndpoint = retrofit.create(HackerNewsServiceApiEndpoint.class);
    }

    @Override
    public void getTopStories(final ItemsServiceCallback<List<Story>> callback) {
        apiEndpoint.getTopStories().enqueue(new Callback<int[]>() {
            @Override
            public void onResponse(Call<int[]> call, Response<int[]> response) {
                callback.onLoaded(toStoriesFromIds(response.body()));
            }

            @Override
            public void onFailure(Call<int[]> call, Throwable t) {
                Log.e("Get TOp Stories", t.toString());
            }
        });
    }

    private List<Story> toStoriesFromIds(int[] ids) {
        List<Story> stories = new ArrayList<>(ids.length);
        for (int id : ids) {
            Story story = new Story(id);
            stories.add(story);
        }
        return stories;
    }

    @Override
    public void getSingleStory(int storyId, final ItemsServiceCallback<Story> callback) {
        apiEndpoint.getStory(storyId).enqueue(new Callback<Story>() {
            @Override
            public void onResponse(Call<Story> call, Response<Story> response) {
                callback.onLoaded(response.body());
            }

            @Override
            public void onFailure(Call<Story> call, Throwable t) {

            }
        });
    }

    @Override
    public void getSingleComment(@NonNull int commentID, final ItemsServiceCallback<Comment> callback) {
        apiEndpoint.getComment(commentID).enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                callback.onLoaded(response.body());
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {

            }
        });
    }

}
