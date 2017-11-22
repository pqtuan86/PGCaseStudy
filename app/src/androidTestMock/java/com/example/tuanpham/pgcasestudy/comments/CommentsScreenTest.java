package com.example.tuanpham.pgcasestudy.comments;

import android.content.Intent;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.example.tuanpham.pgcasestudy.R;
import com.example.tuanpham.pgcasestudy.TestUtils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.tuanpham.pgcasestudy.TestUtils.getCurrentActivity;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by tuanpham on 11/20/17.
 */


@RunWith(AndroidJUnit4.class)
@LargeTest
public class CommentsScreenTest {

    private static final ArrayList<Integer> commentIds = new ArrayList<>(Arrays.asList(15709763, 15709530));


    private Matcher<View> withItemText(final String itemText) {
        checkArgument(!TextUtils.isEmpty(itemText), "itemText cannot be null or empty");
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View item) {
                return Matchers.allOf(
                        isDescendantOfA(isAssignableFrom(RecyclerView.class)),
                        withText(itemText)).matches(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is isDescendantOfA RV with text " + itemText);
            }
        };
    }

    @Rule
    public ActivityTestRule<CommentsActivity> commentsActivityTestRule = new ActivityTestRule<>(CommentsActivity.class, true, false);

    @Before
    public void intentWithStubbedCommentIds() {
        Intent startIntent = new Intent();
        startIntent.putIntegerArrayListExtra(CommentsActivity.EXTRA_COMMENT_LIST, commentIds);
        commentsActivityTestRule.launchActivity(startIntent);
    }

    @Test
    public void testShowComments() throws Exception {
        String comment1Content = "Comment no. 1 content";
        String comment2Content = "Comment no. 2 content";
        String reply1Content = "Reply 1 of comment 1 content";

        // check comment 1 is displayed on screen
        onView(ViewMatchers.withId(R.id.stories_list)).perform(scrollTo(hasDescendant(withText(comment1Content))));
        onView(withItemText(comment1Content)).check(matches(isDisplayed()));

        // check story 2 is displayed on screen
        onView(withId(R.id.stories_list)).perform(scrollTo(hasDescendant(withText(comment2Content))));
        onView(withItemText(comment2Content)).check(matches(isDisplayed()));

        // check reply 1 of comment 1 is displayed on screen
        onView(withId(R.id.stories_list)).perform(scrollToPosition(1));
        onView(withItemText(reply1Content)).check(matches(isDisplayed()));
    }

    @Test
    @SdkSuppress(minSdkVersion = 21) // Blinking cursor after rotation breaks this in API 19
    public void testOrientationChange_StoriesPersist() throws Exception {

        // Rotate the screen
        TestUtils.rotateOrientation(getCurrentActivity());
        testShowComments();
    }


}
