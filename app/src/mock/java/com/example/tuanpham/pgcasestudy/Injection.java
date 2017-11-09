package com.example.tuanpham.pgcasestudy;

import com.example.tuanpham.pgcasestudy.data.StoriesRepository;
import com.example.tuanpham.pgcasestudy.data.HackerNewsServiceApiImpl;
import com.example.tuanpham.pgcasestudy.data.StoryRepositories;

/**
 * Created by tuanpham on 11/4/17.
 */

public class Injection {

    public static StoriesRepository provideStoriesRepository() {
        return StoryRepositories.getInMemoryRepoInstance(new HackerNewsServiceApiImpl());
    }
}
