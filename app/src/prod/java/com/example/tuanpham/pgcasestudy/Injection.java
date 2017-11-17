package com.example.tuanpham.pgcasestudy;

import com.example.tuanpham.pgcasestudy.data.ItemsRepository;
import com.example.tuanpham.pgcasestudy.data.HackerNewsServiceApiImpl;
import com.example.tuanpham.pgcasestudy.data.ItemRepositories;

/**
 * Created by tuanpham on 11/4/17.
 */

public class Injection {

    public static ItemsRepository provideStoriesRepository() {
        return ItemRepositories.getInMemoryRepoInstance(new HackerNewsServiceApiImpl());
    }
}
