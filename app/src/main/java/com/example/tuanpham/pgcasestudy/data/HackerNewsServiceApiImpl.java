package com.example.tuanpham.pgcasestudy.data;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tuanpham on 11/7/17.
 */

public class HackerNewsServiceApiImpl implements StoriesServiceApi {

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
    public void getTopStories(final ItemsServiceCallback<int[]> callback) {
        apiEndpoint.getTopStories().enqueue(new Callback<int[]>() {
            @Override
            public void onResponse(Call<int[]> call, Response<int[]> response) {
                callback.onLoaded(response.body());
            }

            @Override
            public void onFailure(Call<int[]> call, Throwable t) {
                Log.e("Get TOp Stories", t.toString());
            }
        });
    }

    @Override
    public void getAllItems(final ItemsServiceCallback<List<Story>> callback) {
    }

    @Override
    public void getItem(String itemId, ItemsServiceCallback<Story> callback) {

    }
}
