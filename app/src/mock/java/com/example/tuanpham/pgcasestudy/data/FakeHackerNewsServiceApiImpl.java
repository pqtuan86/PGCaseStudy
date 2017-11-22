package com.example.tuanpham.pgcasestudy.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tuanpham on 11/7/17.
 */

public class FakeHackerNewsServiceApiImpl implements HNItemsServiceApi {

    // TODO replace this with a new test specific data set.
    private static final ArrayList<Story> STORIES;
    private static final ArrayList<Comment> COMMENTS;
    private static final ArrayList<Comment> REPLIES;

    static {
        STORIES = new ArrayList<>(2);
        addStory(15709418,
                "rbcgerard",
                113,
                Arrays.asList(15709763, 15709530),
                250,
                1510793447,
                "New Orleans man locked up nearly 8 years awaiting trial, then case gets tossed",
                "story",
                "http://www.theadvocate.com/new_orleans/news/courts/article_d76e9496-c995-11e7-bd8b-4bb8d14cacbf.html?utm_content=buffera1a9c&utm_medium=social&utm_source=twitter.com&utm_campaign=buffer");

        addStory(15745441,
                "benpink",
                228,
                Arrays.asList(15745532, 15746019, 15745687, 15745937, 15746129, 15745569, 15746739, 15745686, 15746573, 15745828, 15746332, 15745479, 15745683, 15746036, 15745675, 15745562, 15745492, 15745796, 15746066, 15745912, 15745887, 15745920, 15746309, 15745671, 15746119, 15746064, 15745557),
                383,
                1511237313,
                "Tether Critical Announcement",
                "story",
                "https://tether.to/tether-critical-announcement/");

        COMMENTS = new ArrayList<>(2);
        addComment(15709763, "flightrisk",
                Arrays.asList(15709875, 15709787),
                15709418,
                1510797723,
                "Comment no. 1 content",
                "comment");

        addComment(15709530, "gok",
                null,
                15709418,
                1510794659,
                "Comment no. 2 content",
                "comment");

        REPLIES = new ArrayList<>(2);
        addReply(15709875, "drawkbox",
                null,
                15709763,
                1510799651,
                "Reply 1 of comment 1 content",
                "comment");

        addReply(15709787, "dleslie",
                null,
                15709763,
                1510798034,
                "Reply 2 of comment 1 content",
                "comment");
    }

    private static void addStory(int id, String by, int descendants, List<Integer> kids, int score, long time, String title, String type, String url) {
        Story story = new Story(id);
        story.setBy(by);
        story.setDescendants(descendants);
        story.setKids(new ArrayList<>(kids));
        story.setScore(score);
        story.setTime(time);
        story.setTitle(title);
        story.setType(type);
        story.setUrl(url);
        STORIES.add(story);
    }

    private static void addComment(int id, String by, List<Integer> kids, int parent, long time, String text, String type) {
        Comment comment = new Comment(id);
        comment.setBy(by);
        comment.setKids(kids != null ? new ArrayList<>(kids) : null);
        comment.setParent(parent);
        comment.setTime(time);
        comment.setText(text);
        comment.setType(type);
        COMMENTS.add(comment);
    }

    private static void addReply(int id, String by, List<Integer> kids, int parent, long time, String text, String type) {
        Comment comment = new Comment(id);
        comment.setBy(by);
        comment.setKids(kids != null ? new ArrayList<>(kids) : null);
        comment.setParent(parent);
        comment.setTime(time);
        comment.setText(text);
        comment.setType(type);
        REPLIES.add(comment);
    }

    @Override
    public void getTopStories(final ItemsServiceCallback<List<Story>> callback) {
        callback.onLoaded(STORIES);
    }

    @Override
    public void getSingleStory(int storyId, final ItemsServiceCallback<Story> callback) {
        callback.onLoaded(STORIES.get(STORIES.indexOf(new Story(storyId))));
    }

    @Override
    public void getSingleComment(@NonNull int commentID, final ItemsServiceCallback<Comment> callback) {
        Comment comment = new Comment(commentID);
        if (COMMENTS.contains(comment)) {
            callback.onLoaded(COMMENTS.get(COMMENTS.indexOf(comment)));
        } else if (REPLIES.contains(comment)) {
            callback.onLoaded(REPLIES.get(REPLIES.indexOf(comment)));
        } else {
            callback.onLoaded(null);
        }
    }

}
