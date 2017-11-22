package com.example.tuanpham.pgcasestudy.util;

import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by tuanpham on 11/23/17.
 */

public class UiUtilsTest {

    private final String LATEST_UPDATE = "12 minutes ago";
    private final long CURRENT_TIME_IN_SECOND = 1511374224;
    private final long ITEM_TIME = 1511373456;

    @Test
    public void testGetLatestUpdateTime() throws Exception {
        String latestUpdateTime = UiUtils.getLatestUpdateTime(CURRENT_TIME_IN_SECOND, ITEM_TIME);
        assertEquals(LATEST_UPDATE, latestUpdateTime);
    }
}
