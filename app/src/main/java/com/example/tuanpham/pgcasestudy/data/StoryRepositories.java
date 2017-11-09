package com.example.tuanpham.pgcasestudy.data;

import android.support.annotation.NonNull;

/**
 * Created by tuanpham on 11/4/17.
 */

public class StoryRepositories {

    private StoryRepositories() {

    }

    private static StoriesRepository repository = null;

    public synchronized static StoriesRepository getInMemoryRepoInstance(@NonNull StoriesServiceApi storiesServiceApi) {
        if (storiesServiceApi == null) {
            throw new NullPointerException("itemServiceApi must not null");
        }

        if (null == repository) {
            repository = new InMemoryStoriesRepository(storiesServiceApi);
        }
        return repository;
    }
}
