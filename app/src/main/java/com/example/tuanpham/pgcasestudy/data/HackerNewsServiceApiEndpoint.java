package com.example.tuanpham.pgcasestudy.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by tuanpham on 11/8/17.
 */

public interface HackerNewsServiceApiEndpoint {

    @GET("topstories.json")
    Call<int[]> getTopStories();

    @GET("item/{storyId}.json")
    Call<Story> getStory(@Path("storyId") String storyId);

//    @GET("item/{commentId}.json")
//    Call<Comment> getComment(@Path("commentId") String commentId);

}