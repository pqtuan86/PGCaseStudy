package com.example.tuanpham.pgcasestudy.data;

import android.support.annotation.NonNull;

/**
 * Created by tuanpham on 11/4/17.
 */

public class ItemRepositories {

    private ItemRepositories() {

    }

    private static ItemsRepository repository = null;

    public synchronized static ItemsRepository getInMemoryRepoInstance(@NonNull HNItemsServiceApi HNItemsServiceApi) {
        if (HNItemsServiceApi == null) {
            throw new NullPointerException("itemServiceApi must not null");
        }

        if (null == repository) {
            repository = new InMemoryItemsRepository(HNItemsServiceApi);
        }
        return repository;
    }
}
