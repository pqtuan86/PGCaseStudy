package com.example.tuanpham.pgcasestudy;

import com.example.tuanpham.pgcasestudy.data.FakeHackerNewsServiceApiImpl;
import com.example.tuanpham.pgcasestudy.data.ItemRepositories;
import com.example.tuanpham.pgcasestudy.data.ItemsRepository;

/**
 * Created by tuanpham on 11/4/17.
 */

public class Injection {

    public static ItemsRepository provideStoriesRepository() {
        return ItemRepositories.getInMemoryRepoInstance(new FakeHackerNewsServiceApiImpl());
    }
}
