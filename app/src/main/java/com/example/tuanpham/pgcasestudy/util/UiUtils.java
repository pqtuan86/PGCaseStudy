package com.example.tuanpham.pgcasestudy.util;

/**
 * Created by tuanpham on 11/7/17.
 */

public class UiUtils {

    public static String getLatesUpdateTime(long time) {
        long elapsedTime = System.currentTimeMillis() / 1000 - time;
        int dates = (int) elapsedTime / (24*60*60);

        if (dates > 0) {
            if (dates > 1) {
                return String.valueOf(dates) + " days ago";
            }
            return "1 day ago";
        }
        int hours = (int) elapsedTime / (60*60);
        if (hours > 0) {
            if (hours > 1) {
                return String.valueOf(hours) + " hours ago";
            }
            return "1 hour ago";
        }
        int minutes = (int) elapsedTime / 60;
        if (minutes > 1) {
            return String.valueOf(minutes) + " minutes ago";
        }
        return "1 minute ago";
    }

}
